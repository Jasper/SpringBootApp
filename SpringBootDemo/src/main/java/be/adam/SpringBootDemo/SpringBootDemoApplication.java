package be.adam.SpringBootDemo;

import be.adam.SpringBootDemo.models.Category;
import be.adam.SpringBootDemo.models.Product;
import be.adam.SpringBootDemo.repositories.CategoryRepository;
import be.adam.SpringBootDemo.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableRabbit
@EnableScheduling
public class SpringBootDemoApplication implements RabbitListenerConfigurer {

	private static final Logger log = LoggerFactory.getLogger(SpringBootDemoApplication.class);
	static final String topicExchangeName = "rabbitmq.exchange.productviews";
	static final String queueName = "rabbitmq.queue";

	@Bean
	public Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	public Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("productviews");
	}


	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}


	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}


	@Override
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ProductRepository productRepository, CategoryRepository categoryRepository) {
		return (args) -> {

			if(categoryRepository.count() == 0 && productRepository.count() == 0) {
				Category cat1 = new Category();
				cat1.setName("Category One");

				Category cat2 = new Category();
				cat2.setName("Category Two");

				Product prod1 = new Product();
				prod1.setName("TestProduct");
				prod1.setDescription("Test product.");
				prod1.setPrice(10000);
				prod1.setStock(10);

				prod1.setCategory(cat1);
				cat1.getProducts().add(prod1);

				categoryRepository.save(cat1);
				categoryRepository.save(cat2);
			}
		};

	}
}

package be.adam.controllers;

import be.adam.models.Product;
import be.adam.models.ProductView;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import be.adam.repositories.ProductViewRepository;

import java.util.Date;

@Component
public class ProductViewController {

    @Autowired
    private ProductViewRepository productViewRepository;

    public ProductViewController() {
    }

    @RabbitListener(queues="rabbitmq.queue", containerFactory = "simpleRabbitListenerContainerFactory")
    public void recievedMessage(Product prod) {
        System.out.println("Received Message: " + prod.getId());

        ProductView productView = new ProductView();
        productView.setProductId(prod.getId());
        productView.setTimestamp(new Date());

        productViewRepository.save(productView);
    }
}

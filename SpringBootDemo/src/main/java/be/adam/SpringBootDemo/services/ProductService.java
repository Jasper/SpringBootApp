package be.adam.SpringBootDemo.services;

import be.adam.SpringBootDemo.models.Category;
import be.adam.SpringBootDemo.models.Product;
import be.adam.SpringBootDemo.repositories.CategoryRepository;
import be.adam.SpringBootDemo.repositories.ProductRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.faces.bean.ManagedBean;
import java.util.Optional;

/**
 * Created by Jasper on 22/04/2018.
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    public ProductService() {
    }

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    public Product find(String productId) {
        try {
            Optional<Product> prod = productRepository.findById(Long.valueOf(productId));

            if(prod.isPresent()) {

                Product product = prod.get();
                rabbitTemplate.convertAndSend(exchange, "productviews", product);
                System.out.println("Product view: " + product.getName());

                return prod.get();
            }

            return null;
        }
        catch(Exception ex) {

        }

        return null;
    }

    public Iterable<Product> findProductsByCategory(String categoryId) {
        try {

            long id = Long.valueOf(categoryId);
            Optional<Category> category = categoryRepository.findById(id);

            if(category.isPresent()) {
                Category cat = category.get();

                return cat.getProducts();
            }
        }
        catch(Exception ex) {

        }

        return null;
    }
}

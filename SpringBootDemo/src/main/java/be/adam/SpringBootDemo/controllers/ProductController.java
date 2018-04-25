package be.adam.SpringBootDemo.controllers;

import be.adam.SpringBootDemo.models.Product;
import be.adam.SpringBootDemo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController() {
    }

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }


    @RequestMapping("/products")
    @ResponseBody
    public Iterable<Product> products() {
        return productService.findAll();
    }

    @RequestMapping("/products/{productid}")
    @ResponseBody
    public Product product(@PathVariable(name="productid") String productId) {
        return productService.find(productId);
    }

    @RequestMapping("/products/category/{categoryid}")
    public Iterable<Product> productsByCategory(@PathVariable(name="categoryid") String categoryId) {
        return productService.findProductsByCategory(categoryId);
    }
}

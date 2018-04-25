package be.adam.SpringBootDemo.repositories;

import be.adam.SpringBootDemo.models.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Jasper on 21/04/2018.
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
}

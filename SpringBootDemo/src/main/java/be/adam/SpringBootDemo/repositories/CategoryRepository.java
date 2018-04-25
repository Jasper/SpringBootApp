package be.adam.SpringBootDemo.repositories;

import be.adam.SpringBootDemo.models.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Jasper on 22/04/2018.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
}

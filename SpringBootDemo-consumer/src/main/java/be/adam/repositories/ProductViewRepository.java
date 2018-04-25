package be.adam.repositories;

import be.adam.models.ProductView;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductViewRepository extends CrudRepository<ProductView, Long> {
}

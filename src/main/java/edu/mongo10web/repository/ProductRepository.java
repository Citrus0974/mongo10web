package edu.mongo10web.repository;

import edu.mongo10web.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    public Iterable<Product> findByName(String name);
}

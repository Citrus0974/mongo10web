package edu.mongo10web.repository;

import edu.mongo10web.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
    public Iterable<Category> findByName(String name);
}

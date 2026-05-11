package edu.mongo10web.repository;

import edu.mongo10web.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {
    public List<Category> findByName(String name);
}

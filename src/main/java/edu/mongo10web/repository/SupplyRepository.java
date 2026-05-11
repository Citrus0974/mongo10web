package edu.mongo10web.repository;

import edu.mongo10web.entity.Supply;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupplyRepository extends MongoRepository<Supply, String> {
    public Iterable<Supply> findByName(String name);
}

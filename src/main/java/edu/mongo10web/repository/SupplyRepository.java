package edu.mongo10web.repository;

import edu.mongo10web.entity.Supply;
import edu.mongo10web.entity.SupplyStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SupplyRepository extends MongoRepository<Supply, String> {
    public List<Supply> findByStatus(SupplyStatus status);

    public List<Supply> findByProductName(String productName);
}

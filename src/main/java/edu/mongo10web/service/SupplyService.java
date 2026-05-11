package edu.mongo10web.service;

import edu.mongo10web.entity.Supply;
import edu.mongo10web.entity.SupplyStatus;
import edu.mongo10web.exception.InvalidDataFormatException;
import edu.mongo10web.exception.NotFoundInRepositoryException;
import edu.mongo10web.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyService {
    private final SupplyRepository supplyRepository;

    @Autowired
    public SupplyService(SupplyRepository repository) {
        this.supplyRepository = repository;
    }

    public Supply create(Supply supply){
        if(supply.getProduct() == null || supply.getStatus() == null) throw new InvalidDataFormatException();
        if(supply.getQuantity() == null || supply.getQuantity() <= 0) throw new InvalidDataFormatException();
        return this.supplyRepository.save(supply);
    }

    public List<Supply> getAll() {
        return supplyRepository.findAll();
    }

    public Supply getById(String id){
        if (id==null || id.isBlank()) throw new InvalidDataFormatException();
        return  supplyRepository.findById(id).orElseThrow(NotFoundInRepositoryException::new);
    }

    public List<Supply> getByStatus(SupplyStatus status){
        return supplyRepository.findByStatus(status);
    }

    public List<Supply> getByProductName(String productName){
        return supplyRepository.findByProductName(productName);
    }

    public Supply update(String id, Supply updatedSupply){
        if(updatedSupply.getProduct() == null || updatedSupply.getStatus() == null) throw new InvalidDataFormatException();
        if(updatedSupply.getQuantity() == null || updatedSupply.getQuantity() <= 0) throw new InvalidDataFormatException();
        Supply supply = getById(id);
        supply.setProduct(updatedSupply.getProduct());
        supply.setStatus(updatedSupply.getStatus());
        supply.setQuantity(updatedSupply.getQuantity());
        supply.setSendDateTime(updatedSupply.getSendDateTime());
        supply.setArriveDateTime(updatedSupply.getArriveDateTime());
        return supplyRepository.save(supply);
    }

    public void delete(String id){
        supplyRepository.deleteById(id);
    }
}

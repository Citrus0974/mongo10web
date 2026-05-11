package edu.mongo10web.service;

import edu.mongo10web.entity.Supply;
import edu.mongo10web.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SupplyService {
    private final SupplyRepository supplyRepository;

    @Autowired
    public SupplyService(SupplyRepository repository) {
        this.supplyRepository = repository;
    }

    public Supply create(Supply supply){
        return this.supplyRepository.save(supply);
    }

    public List<Supply> getAll() {
        return supplyRepository.findAll();
    }

    public Supply getById(String id){
        return  supplyRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
    }

    public Supply update(String id, Supply updatedSupply){
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

package edu.mongo10web.controller;

import edu.mongo10web.entity.Supply;
import edu.mongo10web.service.SupplyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supply")
public class SupplyController {
    private final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @PostMapping
    public Supply create(@RequestBody Supply supply) {
        return supplyService.create(supply);
    }

    @GetMapping
    public List<Supply> getAll() {
        return supplyService.getAll();
    }

    @GetMapping("/{id}")
    public Supply getById(@PathVariable String id) {
        return supplyService.getById(id);
    }

    @PutMapping("/{id}")
    public Supply update(@PathVariable String id, @RequestBody Supply supply) {
        return supplyService.update(id, supply);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        supplyService.delete(id);
    }
}

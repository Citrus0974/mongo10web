package edu.mongo10web.service;

import edu.mongo10web.entity.Product;
import edu.mongo10web.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.productRepository = repository;
    }

    public Product create(Product product){
        return this.productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(String id){
        return  productRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
    }

    public Product update(String id, Product updatedProduct){
        Product product = getById(id);
        product.setName(updatedProduct.getName());
        product.setCost(updatedProduct.getCost());
        product.setCategory(updatedProduct.getCategory());
        product.setManufacturer(updatedProduct.getManufacturer());
        return productRepository.save(product);
    }

    public void delete(String id){
        productRepository.deleteById(id);
    }
}

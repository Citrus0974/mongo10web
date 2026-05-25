package edu.mongo10web.service;

import edu.mongo10web.entity.Product;
import edu.mongo10web.exception.ConflictDataException;
import edu.mongo10web.exception.InvalidDataFormatException;
import edu.mongo10web.exception.NotFoundInRepositoryException;
import edu.mongo10web.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.productRepository = repository;
    }

    public Product create(Product product) {
        String name = product.getName();
        if (name==null || name.isBlank()) throw new InvalidDataFormatException();
        if (product.getCost() == null || product.getCost() <= 0) throw new InvalidDataFormatException();
        if (!getByName(name).isEmpty()) throw new ConflictDataException();
        return this.productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(String id) {
        if (id==null || id.isBlank()) throw new InvalidDataFormatException();
        return productRepository.findById(id).orElseThrow(NotFoundInRepositoryException::new);
    }

    public List<Product> getByName(String name) {
        if (name==null || name.isBlank()) throw new InvalidDataFormatException();
        return productRepository.findByName(name);
    }

    public Product update(String id, Product updatedProduct) {
        if (id==null || id.isBlank()) throw new InvalidDataFormatException();
        if (updatedProduct.getCost() == null || updatedProduct.getCost() <= 0) throw new InvalidDataFormatException();
        String name = updatedProduct.getName();
        if (name==null || name.isBlank()) throw new InvalidDataFormatException();
        Product product = getById(id);
        if(!product.getName().equals(updatedProduct.getName())){
            List<Product> namedCategory = getByName(name);
            if(!namedCategory.isEmpty()) throw new ConflictDataException();
        }
        product.setName(name);
        product.setCost(updatedProduct.getCost());
        product.setCategory(updatedProduct.getCategory());
        product.setManufacturer(updatedProduct.getManufacturer());
        return productRepository.save(product);
    }

    public void delete(String id) {
        if (id==null || id.isBlank()) throw new InvalidDataFormatException();
        productRepository.deleteById(id);
    }
}

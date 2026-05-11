package edu.mongo10web.service;

import edu.mongo10web.entity.Category;
import edu.mongo10web.exception.NotFoundInRepositoryException;
import edu.mongo10web.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.categoryRepository = repository;
    }

    public Category create(Category category){
        return this.categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(String id){
        return  categoryRepository.findById(id).orElseThrow(NotFoundInRepositoryException::new);
    }

    public Category update(String id, Category updatedCategory){
        Category category = getById(id);
        category.setName(updatedCategory.getName());
        category.setDescription(updatedCategory.getDescription());
        category.setCriteries(updatedCategory.getCriteries());
        return categoryRepository.save(category);
    }

    public void delete(String id){
        categoryRepository.deleteById(id);
    }
}

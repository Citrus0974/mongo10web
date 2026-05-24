package edu.mongo10web.service;

import edu.mongo10web.entity.Category;
import edu.mongo10web.exception.ConflictDataException;
import edu.mongo10web.exception.InvalidDataFormatException;
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
        String name = category.getName();
        if (name==null || name.isBlank()) throw new InvalidDataFormatException();
        if (!getByName(name).isEmpty()) throw new ConflictDataException();
        return this.categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(String id){
        if (id==null || id.isBlank()) throw new InvalidDataFormatException();
        return  categoryRepository.findById(id).orElseThrow(NotFoundInRepositoryException::new);
    }

    public List<Category> getByName(String name){
        if (name==null || name.isBlank()) throw new InvalidDataFormatException();
        return  categoryRepository.findByName(name);
    }

    public Category update(String id, Category updatedCategory){
        if (id==null || id.isBlank()) throw new InvalidDataFormatException();
        String name = updatedCategory.getName();
        if (name==null || name.isBlank()) throw new InvalidDataFormatException();
        Category category = getById(id);
        if(!category.getName().equals(updatedCategory.getName())){
            List<Category> namedCategory = getByName(name);
            if(!namedCategory.isEmpty()) throw new ConflictDataException();
        }
        category.setName(name);
        category.setDescription(updatedCategory.getDescription());
        category.setCriteries(updatedCategory.getCriteries());
        updatedCategory.setId(category.getId());
        return categoryRepository.save(category);
    }

    public void delete(String id){
        if (id==null || id.isBlank()) throw new InvalidDataFormatException();
        categoryRepository.deleteById(id);
    }
}

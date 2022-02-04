package com.foodstore.services;

import com.foodstore.exceptions.InvalidDeleteException;
import com.foodstore.exceptions.NotFoundException;
import com.foodstore.models.Category;
import com.foodstore.repositories.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ModifiableRegister<Category> { // клас който обслужва бизнес логика за категориите

    private final CategoryRepo categoryRepo;
    private final ProductService productService;

    public CategoryService(CategoryRepo categoryRepo, ProductService productService) {
        this.categoryRepo = categoryRepo;
        this.productService = productService;
    }

    @Override
    public List<Category> findAllRecords() { // взима всички категории
        return this.categoryRepo.findAll();
    }

    @Override
    public Category addRecord(Category record) {
        return this.categoryRepo.saveAndFlush(record);
    }

    @Override
    public Category updateRecord(Category record) {
        if(this.categoryRepo.findById(record.getId()).isEmpty()) {
            throw new NotFoundException(String.format("Не съществува такава категория id: %s."
                    , record.getCategoryName()));
        }
        return this.categoryRepo.saveAndFlush(record);
    }

    public Optional<Category> getCategoryById(long id) { // взима категория по id
        if(this.categoryRepo.findById(id).isEmpty()) {
            throw new NotFoundException(String.format("Не съществува такава категория id: %d.", id));
        }
        return this.categoryRepo.findById(id);
    }

    public void deleteCategory(Category category) { // изтрива категория ако няма продукти от дадената категория
        if (this.productService.getAllProductsByCategory(category).isEmpty()) {
            this.categoryRepo.delete(category);
        } else {
            throw new InvalidDeleteException(String.format(
                    "Категория: %s не може да бъде изтрита. Съществуват продукти от дадената категория."
                    , category.getCategoryName()));
        }
    }

}

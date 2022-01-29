package com.foodstore.repositories;

import com.foodstore.models.Category;
import com.foodstore.models.Manufacturer;
import com.foodstore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// интерфейс за продуктите за връзка с базата и изпълнение на заявки към нея
@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory(Category category); // взима всички продукти от дадена категория
    List<Product> findAllByManufacturer(Manufacturer manufacturer); // взима всички продукти от даден производител

}

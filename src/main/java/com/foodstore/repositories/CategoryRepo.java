package com.foodstore.repositories;


import com.foodstore.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// интерфейс за категориите за връзка с базата и изпълнение на заявки към нея
@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

}

package com.foodstore.controllers;

import com.foodstore.models.Category;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Контролер за страницата с категориите продукти

@Component
public class CategoryController extends ModifiableTablePageController<Category> {
    // Колони на таблицата
    @FXML
    private TableColumn<Category, Long> colCategoryId;

    @FXML
    private TableColumn<Category, String> colCategoryName;

    @Override
    protected void setColumnProperties() {
        // Link-ваме колонките
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
    }

    @Override
    protected Optional<Category> showSelectedRecord(Optional<Category> record) {
        return Optional.empty();
    }
}

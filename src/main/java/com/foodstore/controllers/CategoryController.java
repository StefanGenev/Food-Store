package com.foodstore.controllers;

import com.foodstore.models.Category;
import com.foodstore.models.Load;
import com.foodstore.services.CategoryService;
import com.foodstore.utils.ProductTableCell;
import com.foodstore.utils.StringDateConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

// Контролер за страницата с категориите продукти

@Component
public class CategoryController extends BaseTablePageController<Category> {
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
}

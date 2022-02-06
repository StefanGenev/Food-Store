package com.foodstore.controllers;

import com.foodstore.models.Load;
import com.foodstore.models.Product;
import com.foodstore.utils.ProductTableCell;
import com.foodstore.utils.StringDateConverter;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// Контролер за страница със зареждания

@Component
public class LoadController extends BaseTablePageController<Load> {
    // Колони на таблицата

    @FXML
    private TableColumn<Load, Product> colProductName;

    @FXML
    private TableColumn<Load, Long> colQuantity;

    @FXML
    private TableColumn<Load, LocalDateTime> colDateOfLoading;

    @Override
    protected void setColumnProperties() {
        colDateOfLoading.setCellFactory(TextFieldTableCell.forTableColumn(new StringDateConverter()));

        // Попълване на данни за продукт
        colProductName.setCellFactory(col -> new ProductTableCell<>());

        // Задаване от къде в табличния клас четем данните
        colProductName.setCellValueFactory(new PropertyValueFactory<>("product"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDateOfLoading.setCellValueFactory(new PropertyValueFactory<>("dateOfLoading"));
    }
}

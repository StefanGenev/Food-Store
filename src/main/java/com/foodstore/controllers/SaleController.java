package com.foodstore.controllers;

import com.foodstore.models.Product;
import com.foodstore.models.Sale;
import com.foodstore.utils.ProductTableCell;
import com.foodstore.utils.converters.StringDateConverter;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

// Контролер за страница Продажби

@Component
public class SaleController extends BaseTablePageController<Sale> {

    // Колони на таблицата
    @FXML
    private TableColumn<Sale, Product> colProductName;

    @FXML
    private TableColumn<Sale, Long> colQuantity;

    @FXML
    private TableColumn<Sale, LocalDate> colDateOfSale;

    @Override
    protected void setColumnProperties() {
        // Попълване на дата
        colDateOfSale.setCellFactory(TextFieldTableCell.forTableColumn(new StringDateConverter()));

        // Попълване на данни за продукт
        colProductName.setCellFactory(col -> new ProductTableCell<>());

        // Задаване от къде в табличния клас четем данните
        colProductName.setCellValueFactory(new PropertyValueFactory<>("product"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDateOfSale.setCellValueFactory(new PropertyValueFactory<>("dateOfSale"));
    }
}
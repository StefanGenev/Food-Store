package com.foodstore.controllers;

import com.foodstore.models.*;
import com.foodstore.services.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

// Контролер за страницата с категориите продукти

@Component
public class ProductController extends BaseTablePageController<Product> {
    // Колони на таблицата
    @FXML
    private TableColumn<Product, Long> colProductId;

    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private TableColumn<Product, Manufacturer> colManufacturer;

    @FXML
    private TableColumn<Product, Unit> colUnit;

    @FXML
    private TableColumn<Product, Category> colCategory;

    @FXML
    private TableColumn<Product, Double> colLoadPrice;

    @FXML
    private TableColumn<Product, Double> colSellPrice;

    @Override
    protected void setColumnProperties() {
        // Link-ване на колонките
        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colLoadPrice.setCellValueFactory(new PropertyValueFactory<>("loadPrice"));
        colSellPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));

        // Задаване от къде в табличния клас четем данните
        colManufacturer.setCellFactory(col -> new TableCell<Product, Manufacturer>() {
            @Override
            protected void updateItem(Manufacturer manufacturer, boolean empty) {
                super.updateItem(manufacturer, empty);
                if (empty || manufacturer == null) {
                    setText(null);
                } else {
                    setText(manufacturer.getManufacturerName());
                }
            }
        });

        colUnit.setCellFactory(col -> new TableCell<Product, Unit>() {
            @Override
            protected void updateItem(Unit unit, boolean empty) {
                super.updateItem(unit, empty);
                if (empty || unit == null) {
                    setText(null);
                } else {
                    setText(unit.toString());
                }
            }
        });

        colCategory.setCellFactory(col -> new TableCell<Product, Category>() {
            @Override
            protected void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                if (empty || category == null) {
                    setText(null);
                } else {
                    setText(category.getCategoryName());
                }
            }
        });
    }
}

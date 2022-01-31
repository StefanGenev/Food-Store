package com.foodstore.controllers;

// Контролер за страница Магазин - наличност на продукти

import com.foodstore.models.Product;
import com.foodstore.models.StoreStock;
import com.foodstore.services.StoreStockService;
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

// Контролер за страница с наличност на продукти

@Component
public class ProductAvailabilityPageController implements Initializable {
    // Колони на таблицата
    @FXML
    private TableView<StoreStock> storeStockTable;

    @FXML
    private TableColumn<StoreStock, Long> colStoreStockId;

    @FXML
    private TableColumn<StoreStock, Product> colProductName;

    @FXML
    private TableColumn<StoreStock, String> colQuantity;

    @Autowired
    private StoreStockService storeStockService;

    // Списък на наличните продукти в магазина
    private ObservableList<StoreStock> storeStockList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Задаваме селекция на множество колони
        storeStockTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Задаваме как да се пълнят редовете
        setColumnProperties();

        // Зареждаме редовете в таблицата
        loadSalesDetails();
    }

    private void setColumnProperties() {
        // Попълване на данни за продукт
        colProductName.setCellFactory(col -> new TableCell<StoreStock, Product>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(product.getProductName());
                }
            }
        });

        // Задаване от къде в табличния клас четем данните
        colStoreStockId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("product"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    private void loadSalesDetails() {
        storeStockList.clear();
        storeStockList.addAll(storeStockService.findAllStoreStocks());
        storeStockTable.setItems(storeStockList);
    }
}

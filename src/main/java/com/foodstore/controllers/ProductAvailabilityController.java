package com.foodstore.controllers;

import com.foodstore.models.Product;
import com.foodstore.models.StoreStock;
import com.foodstore.utils.ProductTableCell;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

// Контролер за страница с наличност на продукти

@Component
public class ProductAvailabilityController extends BaseTablePageController<StoreStock> {
    // Колони на таблицата
    @FXML
    private TableColumn<StoreStock, Long> colStoreStockId;

    @FXML
    private TableColumn<StoreStock, Product> colProductName;

    @FXML
    private TableColumn<StoreStock, String> colQuantity;

    @FXML
    private TableColumn<StoreStock, Product> colCategory;

    @FXML
    private TableColumn<StoreStock, String> colAvailabilityDate;

    @Override
    protected void setColumnProperties() {
        // Попълване на данни за продукт
        colProductName.setCellFactory(col -> new ProductTableCell<>());

        colCategory.setCellFactory(col -> new TableCell<StoreStock, Product>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null || product.getCategory() == null) {
                    setText(null);
                } else {
                    setText(product.getCategory().getCategoryName());
                }
            }
        });

        // Задаване от къде в табличния клас четем данните
        colStoreStockId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("product"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colAvailabilityDate.setCellValueFactory(new PropertyValueFactory<>("availabilityDate"));
    }
}

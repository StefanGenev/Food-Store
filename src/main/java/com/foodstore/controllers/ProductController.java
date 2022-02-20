package com.foodstore.controllers;

import com.foodstore.controllers.dialogs.ProductDialog;
import com.foodstore.models.Category;
import com.foodstore.models.Manufacturer;
import com.foodstore.models.Product;
import com.foodstore.models.Unit;
import com.foodstore.services.CategoryService;
import com.foodstore.services.ManufacturerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Контролер за страницата с категориите продукти

@Component
public class ProductController extends ModifiableTablePageController<Product> {
    // Колони на таблицата
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

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private CategoryService categoryService;

    @Override
    protected void setColumnProperties() {
        // Link-ване на колонките
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

    @Override
    protected Optional<Product> showSelectedRecord(Optional<Product> record) {
        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);

        ObservableList<Manufacturer> manufacturersList = FXCollections.observableArrayList();
        manufacturersList.addAll(manufacturerService.findAllRecords());

        ObservableList<Category> categoriesList = FXCollections.observableArrayList();
        categoriesList.addAll(categoryService.findAllRecords());

        ProductDialog dialog = new ProductDialog(owner, record);
        dialog.setManufacturers(manufacturersList);
        dialog.setCategories(categoriesList);
        return dialog.showAndWait();
    }

    @Override
    protected void initializeRowContextMenu(ContextMenu contextMenu, TableRow<Product> selectedRow) {
        final MenuItem loadMenu = new MenuItem("Зареди");
        loadMenu.setOnAction(event -> loadProduct(selectedRow));

        loadMenu.disableProperty().bind(selectedRow.emptyProperty());

        final MenuItem sellMenu = new MenuItem("Продай");
        sellMenu.setOnAction(event -> loadProduct(selectedRow));

        sellMenu.disableProperty().bind(selectedRow.emptyProperty());

        contextMenu.getItems().addAll(loadMenu, sellMenu);

        super.initializeRowContextMenu(contextMenu, selectedRow);
    }


    private void loadProduct(TableRow<Product> selectedRow){

    }

    private void sellProduct(TableRow<Product> selectedRow){
    }
}

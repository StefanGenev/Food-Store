package com.foodstore.controllers;

import com.foodstore.models.Product;
import com.foodstore.models.Sale;
import com.foodstore.services.SaleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

// Контролер за страница Продажби

@Component
public class SalesPageController implements Initializable {

    // Колони на таблицата
    @FXML
    private TableView<Sale> salesTable;

    @FXML
    private TableColumn<Sale, Long> colSaleId;

    @FXML
    private TableColumn<Sale, Product> colProductName;

    @FXML
    private TableColumn<Sale, Long> colQuantity;

    @FXML
    private TableColumn<Sale, LocalDateTime> colDateOfSale;

    @Autowired
    private SaleService saleService;

    // Списък на продажбите
    private ObservableList<Sale> salesList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Задаваме селекция на множество колони
        salesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Задаваме как да се пълнят редовете
        setColumnProperties();

        // Зареждаме продажбите в таблицата
        loadSalesDetails();
    }

    private void loadSalesDetails() {
        salesList.clear();
        salesList.addAll(saleService.findAllSales());
        salesTable.setItems(salesList);
    }

    private void setColumnProperties() {
        // Попълване на дата
        colDateOfSale.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDateTime>() {
            String pattern = "dd/MM/yyyy HH:MM:SS";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDateTime dateTime) {
                if (dateTime != null) {
                    return dateFormatter.format(dateTime);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDateTime fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDateTime.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        }));

        // Попълване на данни за продукт
        colProductName.setCellFactory(col -> new TableCell<Sale, Product>() {
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
        colSaleId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("product"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDateOfSale.setCellValueFactory(new PropertyValueFactory<>("dateOfSale"));
    }
}
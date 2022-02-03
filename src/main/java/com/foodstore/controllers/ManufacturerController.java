package com.foodstore.controllers;

import com.foodstore.models.Manufacturer;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManufacturerController extends BaseTablePageController<Manufacturer>{
    // Колони на таблицата
    @FXML
    private TableColumn<Manufacturer, Long> colManufacturerId;

    @FXML
    private TableColumn<Manufacturer, String> colManufacturerName;

    @Override
    protected void setColumnProperties() {
        // Link-ваме колонките
        colManufacturerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colManufacturerName.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));
    }
}

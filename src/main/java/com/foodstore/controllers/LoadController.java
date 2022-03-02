package com.foodstore.controllers;

import com.foodstore.models.Load;
import com.foodstore.models.Product;
import com.foodstore.utils.ProductTableCell;
import com.foodstore.utils.converters.StringDateConverter;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

// Контролер за страница със зареждания

@Component
public class LoadController extends ModifiableTablePageController<Load> {
    // Колони на таблицата

    @FXML
    private TableColumn<Load, Product> colProductName;

    @FXML
    private TableColumn<Load, Long> colQuantity;

    @FXML
    private TableColumn<Load, LocalDate> colDateOfLoading;

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

    @Override
    protected Optional<Load> showSelectedRecord(Optional<Load> recordOptional) {
        return Optional.empty();
    }

    @Override
    protected void initializeRowContextMenu(ContextMenu contextMenu, TableRow<Load> selectedRow) {
        this.initializeTableContextMenu(contextMenu);
    }

    @Override
    protected void initializeTableContextMenu(ContextMenu menu) {
        // меню
        final MenuItem refreshMenuItem = new MenuItem("Презареждане");

        // Действия при избор на менюто
        refreshMenuItem.setOnAction(event -> reloadTable());

        // Добавяме дефинираните опции в контекстното меню
        menu.getItems().addAll(refreshMenuItem);
    }
}

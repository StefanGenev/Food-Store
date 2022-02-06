package com.foodstore.controllers;

// Базов клас за контролер за страница с Таблица

import com.foodstore.services.ReadableRegister;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BaseTablePageController<T> implements Initializable {

    @FXML
    protected TableView<T> tableView; // Таблица

    @Autowired
    private ReadableRegister<T> recordService; // Клас за бизнес логика

    // Списък на записите
    protected ObservableList<T> recordsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Инициализация на контекстно меню на таблицата
        ContextMenu menu = new ContextMenu();
        initializeTableContextMenu(menu);
        tableView.setContextMenu(menu);

        // Инициализация на контекстно меню на ред
        tableView.setRowFactory(tableView -> {
            final TableRow<T> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();

            initializeRowContextMenu(contextMenu, row);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then(contextMenu)
                            .otherwise(contextMenu)
            );

            return row;
        });

        // Задаваме селекция на множество колони
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Задаваме как да се пълнят редовете
        setColumnProperties();

        // Зареждаме записите в таблицата
        reloadTable();
    }

    protected void initializeTableContextMenu(ContextMenu menu) {
        // Инициализация на контекстно меню на таблицата
        final MenuItem refreshMenuItem = new MenuItem("Презареждане");

        // Действия при избор на менюто
        refreshMenuItem.setOnAction(event -> reloadTable());

        menu.getItems().add(refreshMenuItem);
    }

    protected abstract void setColumnProperties();

    protected void loadRecordsFromDB() {
        recordsList.clear();
        recordsList.addAll(recordService.findAllRecords());
    }

    protected void fillTableView() {
        tableView.setItems(recordsList);
    }

    protected void reloadTable() {
        loadRecordsFromDB();
        fillTableView();
    }

    protected void initializeRowContextMenu(ContextMenu contextMenu, TableRow<T> selectedRow) {
        // Дефинираме опциите в контекстното меню
        final MenuItem refreshMenuItem = new MenuItem("Презареждане");

        // Действия при избор на менюто
        refreshMenuItem.setOnAction(event -> reloadTable());

        contextMenu.getItems().add(refreshMenuItem);
    }
}

package com.foodstore.controllers;

// Базов клас за контролер за страница с Таблица

import com.foodstore.services.BaseCRUDServiceInterface;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BaseTablePageController<T> implements Initializable {

    @FXML
    protected TableView<T> tableView; // Таблица

    @Autowired
    private BaseCRUDServiceInterface<T> recordService; // Клас за бизнес логика

    // Списък на записите
    protected ObservableList<T> recordsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Инициализация на контекстното меню
        tableView.setRowFactory(tableView -> {
            final TableRow<T> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();

            initializeContextMenu(contextMenu, row);

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
        loadRecordDetails();
    }

    protected abstract void setColumnProperties();

    protected void loadRecordDetails() {
        recordsList.clear();
        recordsList.addAll(recordService.findAllRecords());
        tableView.setItems(recordsList);
    }

    protected void initializeContextMenu(ContextMenu contextMenu, TableRow<T> selectedRow){
        // Дефинираме опциите в контекстното меню
        final MenuItem addMenuItem = new MenuItem("Add");

        // Действия при избор на менюто
        addMenuItem.setOnAction(event -> AddRecord());

        final MenuItem updateMenuItem = new MenuItem("Update");
        updateMenuItem.setOnAction(event -> UpdateRecord(selectedRow));

        // Ако няма селектиран запис не го използваме
        updateMenuItem.disableProperty().bind(selectedRow.emptyProperty());

        final MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> DeleteRecord(selectedRow));

        deleteMenuItem.disableProperty().bind(selectedRow.emptyProperty());

        // Добавяме дефинираните опции в контекстното меню
        contextMenu.getItems().addAll(addMenuItem, updateMenuItem, deleteMenuItem);
    }

    // Добавяне на запис
    protected void AddRecord(){
    }

    // Редакция на запис
    protected void UpdateRecord(TableRow<T> selectedRow){
    }

    // Изтриване на запис
    protected void DeleteRecord(TableRow<T> selectedRow){
    }
}

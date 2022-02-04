package com.foodstore.controllers;

import com.foodstore.services.ModifiableRegister;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class ModifiableTablePageController<T> extends BaseTablePageController<T>{

    // Клас за бизнес логика позволяващ модификации на данни от регистъра
    @Autowired
    private ModifiableRegister<T> modifiableRegisterService;

    @Override
    protected void initializeContextMenu(ContextMenu contextMenu, TableRow<T> selectedRow) {
        // Дефинираме опциите в контекстното меню
        final MenuItem addMenuItem = new MenuItem("Добавяне");

        // Действия при избор на менюто
        addMenuItem.setOnAction(event -> AddRecord());

        final MenuItem updateMenuItem = new MenuItem("Редакция");
        updateMenuItem.setOnAction(event -> UpdateRecord(selectedRow));

        // Ако няма селектиран запис не го използваме
        updateMenuItem.disableProperty().bind(selectedRow.emptyProperty());

        final MenuItem deleteMenuItem = new MenuItem("Изтриване");
        deleteMenuItem.setOnAction(event -> DeleteRecord(selectedRow));

        deleteMenuItem.disableProperty().bind(selectedRow.emptyProperty());

        // Добавяме дефинираните опции в контекстното меню
        contextMenu.getItems().addAll(addMenuItem, updateMenuItem, deleteMenuItem);

        super.initializeContextMenu(contextMenu, selectedRow);
    }

    // Добавяне на запис
    protected void AddRecord(){
        // Визуализация на запис
        Optional<T> record = showSelectedRecord(Optional.empty());
        if (record.isEmpty())
            return;

        // Добавяне в база данни
        modifiableRegisterService.addRecord(record.get());

        // Добавяне в таблица на екран
        recordsList.add(record.get());
        fillTableView();
    }

    // Редакция на запис
    protected void UpdateRecord(TableRow<T> selectedRow) {
        // Визуализация на запис
        Optional<T> record = showSelectedRecord(Optional.of(tableView.getSelectionModel().getSelectedItem()));
        if (record.isEmpty())
            return;

        // Редакция в база данни
        modifiableRegisterService.updateRecord(record.get());

        // Редакция в таблица на екран
        recordsList.set(selectedRow.getIndex(),record.get());
        fillTableView();
    }

    // Изтриване на запис
    protected void DeleteRecord(TableRow<T> selectedRow) {
    }

    protected abstract Optional<T> showSelectedRecord(Optional<T> recordOptional);
}
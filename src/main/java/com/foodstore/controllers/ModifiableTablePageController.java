package com.foodstore.controllers;

import com.foodstore.models.NameableEntity;
import com.foodstore.services.ModifiableRegister;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class ModifiableTablePageController<T extends NameableEntity> extends BaseTablePageController<T>{

    // Клас за бизнес логика позволяващ модификации на данни от регистъра
    @Autowired
    private ModifiableRegister<T> modifiableRegisterService;

    @Override
    protected void initializeRowContextMenu(ContextMenu contextMenu, TableRow<T> selectedRow) {
        // Дефинираме опциите в контекстно меню на ред от таблицата
        final MenuItem addMenuItem = new MenuItem("Добавяне");

        // Действия при избор на менюто
        addMenuItem.setOnAction(event -> AddRecord());

        // Действия при избор на менюто
        final MenuItem updateMenuItem = new MenuItem("Редакция");
        updateMenuItem.setOnAction(event -> UpdateRecord(selectedRow));

        // Ако няма селектиран запис не го използваме
        updateMenuItem.disableProperty().bind(selectedRow.emptyProperty());

        final MenuItem deleteMenuItem = new MenuItem("Изтриване");
        deleteMenuItem.setOnAction(event -> DeleteRecord(selectedRow));

        deleteMenuItem.disableProperty().bind(selectedRow.emptyProperty());

        // Добавяме дефинираните опции в контекстното меню
        contextMenu.getItems().addAll(addMenuItem, updateMenuItem, deleteMenuItem);

        super.initializeRowContextMenu(contextMenu, selectedRow);
    }

    @Override
    protected void initializeTableContextMenu(ContextMenu menu) {
        // Дефинираме опциите в контекстното меню на таблицата
        final MenuItem addMenuItem = new MenuItem("Добавяне");

        // Действия при избор на менюто
        addMenuItem.setOnAction(event -> AddRecord());

        menu.getItems().addAll(addMenuItem);

        super.initializeTableContextMenu(menu);
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
        Optional<T> record = showSelectedRecord(Optional.of(tableView.getItems().get(selectedRow.getIndex())));
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
        Optional<T> record = Optional.of(tableView.getItems().get(selectedRow.getIndex()));
        if (record.isEmpty())
            return;

        // Питаме дали потребителят е сигурен
        ButtonType buttonYes = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonNo = new ButtonType("Не", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.NONE,   getDeleteAlertMessage(record.get()), buttonYes, buttonNo);
        alert.setTitle("Изтриване на запис");
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();

        if (alert.getResult() != buttonYes)
            return;

        // Изтриване в база данни
        modifiableRegisterService.deleteRecord(record.get());

        // Изтриване в таблица на екран
        recordsList.remove(record.get());
        fillTableView();
    }

    protected abstract Optional<T> showSelectedRecord(Optional<T> recordOptional);

    protected String getDeleteAlertMessage(T record){
        return "Сигурни ли сте че искате да изтриете запис: " + record.getName() + " ?";
    }
}
package com.foodstore.controllers.dialogs;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

// Базов клас за диалози за изобразяване и редакция на запис от база данни, с бутон Запис

public abstract class BaseRecordDialog<T> extends Dialog<T> {

    @FXML
    private ButtonType saveButtonType;

    public BaseRecordDialog(Window owner, Optional<T> record) {
        try {
            initialize(owner);
            setDialogData(record);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void initialize(Window owner) throws IOException {
        // Зареждане на fxml и контролер на ресурса
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(this.getResourceAddress()));
        loader.setController(this);

        DialogPane dialogPane = loader.load();
        setDialogPane(dialogPane);

        // Инизиализация на притежател, заглавие и модалност на диалога
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(true);
        setTitle(this.getDialogTitle());

        // Действия при запис или отказ
        setResultConverter(buttonType -> {
            // Ако изберем отказ не правим нищо
            if (!Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData()))
                return null;

            return getDialogData();
        });

        // Допълнителни настройки от наследниците
        additionalDialogInitialization();
    }

    protected void additionalDialogInitialization() {
    }

    protected void setDialogData(Optional<T> record) {
    }

    public String getDialogTitle() { // всеки наследник на базовия диалог трябва да пренапише този метод
        return "";                   // името на диалога
    }

    public String getResourceAddress(){ // всеки наследник на базовия диалог трябва да пренапише този метод
        return "";                      // път към .fxml файлът, който отговаря на диалога
    }

    protected T getDialogData() {
        return null;
    }
}
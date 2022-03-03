package com.foodstore.controllers.dialogs;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

// Базов клас за диалози за изобразяване и редакция на запис от база данни, с бутон Запис

public abstract class BaseRecordDialog<T> extends Dialog<T> {
    private final String DIALOG_STYLES_ADDRESS = "/styles/dialogs.css";

    @FXML
    protected ButtonType okayButtonType;

    public BaseRecordDialog(Window owner, Optional<T> record) {
        try {
            initialize(owner);
            loadData();
            setDialogData(record);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void loadData() {
    }

    @FXML
    protected void initialize(Window owner) throws IOException {
        // Зареждане на fxml и контролер на ресурса
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(this.getResourceAddress()));
        loader.setController(this);

        DialogPane dialogPane = loader.load();
        setDialogPane(dialogPane);

        // Валидация на диалог
        final Button okButton = (Button) getDialogPane().lookupButton(okayButtonType);
        okButton.setDisable(false);

        // Стилове на диалог
        getDialogPane().getStylesheets().add(DIALOG_STYLES_ADDRESS);

        validateDialog(okButton);

        // Инизиализация на притежател, заглавие и модалност на диалога
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(true);
        setTitle(this.getDialogTitle());

        // Допълнителни настройки от наследниците
        additionalDialogInitialization();

        // Действия при запис или отказ
        setResultConverter(buttonType -> {
            // Ако изберем отказ не правим нищо
            if (!Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData()))
                return null;

            return getDialogData();
        });
    }

    protected void validateDialog(Button okButton) {
        okButton.setDisable(false);
    }

    protected void additionalDialogInitialization() {
    }

    protected void setDialogData(Optional<T> record) {
    }

    public String getDialogTitle() { // всеки наследник на базовия диалог трябва да пренапише този метод
        return "";                   // името на диалога
    }

    protected abstract String getResourceAddress(); // всеки наследник на базовия диалог трябва да пренапише този метод
    // път към .fxml файлът, който отговаря на диалога

    protected T getDialogData() {
        return null;
    }
}
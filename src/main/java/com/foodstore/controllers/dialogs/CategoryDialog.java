package com.foodstore.controllers.dialogs;

import com.foodstore.models.Category;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.util.Optional;

// Диалог за данни на категория

public class CategoryDialog extends BaseRecordDialog<Category> {
    private final String RESOURCE_ADDRESS = "/ui/categoryDialog.fxml";
    private final String DIALOG_TITLE = "Категория";
    private final int CATEGORY_MINIMUM_SYMBOLS = 3;

    // Полета на диалог
    @FXML
    private TextField categoryTextField;

    // Запис
    private Category category;

    public CategoryDialog(Window owner, Optional<Category> record) {
        super(owner, record);
    }

    @Override
    protected void additionalDialogInitialization() {
        setOnShowing(dialogEvent -> Platform.runLater(() -> categoryTextField.requestFocus()));
    }

    @Override
    protected void validateDialog(Button okButton) {
        categoryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue == null || newValue.trim().length() < CATEGORY_MINIMUM_SYMBOLS);
        });
    }

    @Override
    protected void setDialogData(Optional<Category> record){
        category = record.orElseGet(Category::new);

        categoryTextField.setText(category.getCategoryName());
    }

    @Override
    protected Category getDialogData(){
        this.category.setCategoryName(categoryTextField.getText());

        return this.category;
    }

    @Override
    public String getDialogTitle() {
        return DIALOG_TITLE;
    }

    @Override
    public String getResourceAddress(){
        return RESOURCE_ADDRESS;
    }
}
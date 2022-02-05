package com.foodstore.controllers.dialogs;

import com.foodstore.models.Category;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.util.Optional;

// Диалог за данни на категория

public class CategoryDialog extends BaseRecordDialog<Category> {

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
        return "Категория";
    }

    @Override
    public String getResourceAddress(){
        return "/ui/categoryDialog.fxml";
    }
}
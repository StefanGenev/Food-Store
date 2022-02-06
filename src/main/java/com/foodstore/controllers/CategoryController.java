package com.foodstore.controllers;

import com.foodstore.controllers.dialogs.CategoryDialog;
import com.foodstore.models.Category;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Контролер за страницата с категориите продукти

@Component
public class CategoryController extends ModifiableTablePageController<Category> {
    // Колони на таблицата
    @FXML
    private TableColumn<Category, String> colCategoryName;

    @Override
    protected void setColumnProperties() {
        // Link-ваме колонките
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
    }

    @Override
    protected Optional<Category> showSelectedRecord(Optional<Category> record) {
        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        CategoryDialog dialog = new CategoryDialog(owner, record);

        return dialog.showAndWait();
    }
}

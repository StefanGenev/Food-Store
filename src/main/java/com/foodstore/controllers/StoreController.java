package com.foodstore.controllers;

import com.foodstore.models.Product;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

// Контролер за страница Магазин

@Component
@FxmlView("/ui/store.fxml")
public class StoreController implements Initializable {

    @FXML
    private Button btnExit; // бутон Изход

    // Колони на таблица

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Long> colProductId;

    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage = new Stage();
    }
}
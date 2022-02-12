package com.foodstore.controllers;

import com.foodstore.models.StoreData;
import com.foodstore.services.StoreDataService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

// Контролер за страница Магазин

@Component
@FxmlView("/ui/store.fxml")
public class StoreController implements Initializable {

    // Контроли
    @FXML
    private Label storeName;
    @FXML
    private Label cashRegister;

    @FXML
    private Button btnExit; // бутон Изход

    // Страници
    @FXML
    private TabPane tabPane;

    @FXML
    private ProductController productController; // Страница за продукти

    // Клас за бизнес логика на параметри
    @Autowired
    private StoreDataService storeDataService;

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage = new Stage();

        productController.setParentController(this);

        storeName.setText(storeDataService.getParameterValue(StoreData.STORE_NAME_PARAMETER));
        cashRegister.setText(storeDataService.getParameterValue(StoreData.CASH_PARAMETER));
    }
}
package com.foodstore.controllers;

import com.foodstore.controllers.dialogs.ManufacturerDialog;
import com.foodstore.models.Manufacturer;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ManufacturerController extends ModifiableTablePageController<Manufacturer> {
    // Колони на таблицата
    @FXML
    private TableColumn<Manufacturer, String> colManufacturerName;

    @Override
    protected void setColumnProperties() {
        // Link-ваме колонките
        colManufacturerName.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));
    }

    @Override
    protected Optional<Manufacturer> showSelectedRecord(Optional<Manufacturer> recordOptional) {
        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        ManufacturerDialog dialog = new ManufacturerDialog(owner, recordOptional);

        return dialog.showAndWait();
    }

    @Override
    protected String getDeleteAlertMessage(Manufacturer record) {
        return "Сигурни ли сте че искате да изтриете производител " + record.getManufacturerName() + "?";
    }
}

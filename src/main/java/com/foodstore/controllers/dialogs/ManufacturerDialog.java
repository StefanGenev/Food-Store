package com.foodstore.controllers.dialogs;

import com.foodstore.models.Manufacturer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.util.Optional;

// Диалог за данни на производител

public class ManufacturerDialog extends BaseRecordDialog<Manufacturer> {

    // Полета на диалог
    @FXML
    private TextField manufacturerTextField;

    // Запис
    private Manufacturer manufacturer;

    public ManufacturerDialog(Window owner, Optional<Manufacturer> record) {
        super(owner, record);
    }

    @Override
    protected void additionalDialogInitialization() {
        setOnShowing(dialogEvent -> Platform.runLater(() -> manufacturerTextField.requestFocus()));
    }

    @Override
    protected void setDialogData(Optional<Manufacturer> record){
        manufacturer = record.orElseGet(Manufacturer::new);

        manufacturerTextField.setText(manufacturer.getManufacturerName());
    }

    @Override
    protected Manufacturer getDialogData(){
        this.manufacturer.setManufacturerName(manufacturerTextField.getText());

        return this.manufacturer;
    }

    @Override
    public String getDialogTitle() {
        return "Фирма/Производител";
    }

    @Override
    public String getResourceAddress(){
        return "/ui/manufacturerDialog.fxml";
    }
}
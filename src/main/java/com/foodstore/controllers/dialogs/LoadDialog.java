package com.foodstore.controllers.dialogs;

import com.foodstore.models.Product;
import com.foodstore.models.StoreStock;
import com.foodstore.utils.converters.StringIntegerConverter;
import javafx.scene.control.Button;
import javafx.stage.Window;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoadDialog extends BaseTradeDialog<StoreStock> {
    private final String RESOURCE_ADDRESS = "/ui/dialogs/loadDialog.fxml";
    private final String DIALOG_TITLE = "Зареждане на продукт";
    private Double currentCash = 0d; // текущ баланс в магазина

    public LoadDialog(Window owner, Optional<StoreStock> record, Double currentCash) {
        super(owner, record);
        this.currentCash = currentCash;
    }

    @Override
    protected String getResourceAddress() {
        return RESOURCE_ADDRESS;
    }

    @Override
    public String getDialogTitle() {
        return DIALOG_TITLE;
    }

    @Override
    protected StoreStock getDialogData() { // взима въведеното в диалога
        StoreStock storeStock = new StoreStock();
        storeStock.setQuantity(Double.parseDouble(this.quantityTextField.getText()));
        storeStock.setProduct(this.productComboBox.getValue());
        storeStock.setAvailabilityDate(LocalDate.now());

        return storeStock;
    }

    @Override
    protected void setDialogData(Optional<StoreStock> record) { // попълва comboBox-а
        record.ifPresent(storeStock -> this.productComboBox.setValue(storeStock.getProduct()));
    }

    @Override
    protected void validateDialog(Button okButton) { // валидираме полетата на диалога
        quantityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(!validateAvailableCash(new StringIntegerConverter().fromString(newValue)));
        });

        //super.validateDialog(okButton);
    }

    private boolean checkForInvalidFields() { // валидира полетата
        AtomicBoolean fieldsAreInvalid = new AtomicBoolean(false);

        if (this.quantityTextField.getText() == null || this.quantityTextField.getText().trim().isEmpty())
            fieldsAreInvalid.set(true);

        if (this.productComboBox.getValue() == null)
            fieldsAreInvalid.set(true);

        // TODOR известяване?
        return fieldsAreInvalid.get();
    }

    private boolean validateAvailableCash(int quantity) {
        if(this.currentCash == null)
            return false;
        // дали цената на зареждането е по-голяма от парите в магазина
        StringIntegerConverter stringIntegerConverter = new StringIntegerConverter();
        String quantityStr = this.quantityTextField.getText();
        double loadPrice = 0;
        if (this.productComboBox != null && this.productComboBox.getValue() != null) {
            loadPrice = this.productComboBox.getValue().getLoadPrice();
        }

        double cost = quantity * loadPrice;

        if (cost > this.currentCash || cost <= 0)
            return false;

        return true;
    }

}

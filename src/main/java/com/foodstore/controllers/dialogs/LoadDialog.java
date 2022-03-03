package com.foodstore.controllers.dialogs;

import com.foodstore.models.StoreStock;
import com.foodstore.utils.converters.StringDoubleConverter;
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

        final Button okButton = (Button) getDialogPane().lookupButton(okayButtonType);
        okButton.setDisable(false);
        this.validateDialog(okButton);
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

    protected void setTotalPriceField(Optional<StoreStock> opStoreStock) { // цена на зареждането = количество * цена 1 ед.
        StringIntegerConverter stringIntegerConverter = new StringIntegerConverter();
        int quantity = stringIntegerConverter.fromString(this.quantityTextField.getText());

        double totalPrice = quantity * opStoreStock.get().getProduct().getLoadPrice();

        StringDoubleConverter stringDoubleConverter = new StringDoubleConverter();
        this.totalPriceTextField.setText(stringDoubleConverter.toString(totalPrice));
    }

    // задава стойности на полетата в диалога след инициализацията му
    @Override
    protected void setDialogData(Optional<StoreStock> record) {
        // попълва comboBox-а
        if (record.isPresent()) {
            this.productComboBox.setValue(record.get().getProduct());
            this.setTotalPriceField(Optional.of(record.get()));
            this.totalPriceTextField.setText(new StringDoubleConverter().toString(1 * this.productComboBox.getValue().getLoadPrice()));
        }

        // попълва default-на стойност на количеството
        this.quantityTextField.setText("1");
    }

    @Override
    protected boolean validateQuantity(String newValue) {
        if (!super.validateQuantity(newValue))
            return false;

        if (this.currentCash == null)
            return false;

        double cost = new StringIntegerConverter().fromString(newValue) * this.productComboBox.getValue().getLoadPrice();
        this.totalPriceTextField.setText(new StringDoubleConverter().toString(cost));
        if (!validateAvailableCash(cost))
            return false;

        return true;
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

    private boolean validateAvailableCash(double cost) {
        // дали цената на зареждането е по-голяма от парите в магазина
        if (cost > this.currentCash || cost <= 0)
            return false;

        return true;
    }

}

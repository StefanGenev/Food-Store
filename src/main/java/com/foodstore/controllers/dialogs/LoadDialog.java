package com.foodstore.controllers.dialogs;

import com.foodstore.models.Product;
import com.foodstore.models.StoreStock;
import com.foodstore.utils.converters.StringDoubleConverter;
import javafx.scene.control.Button;
import javafx.stage.Window;

import java.time.LocalDate;
import java.util.Optional;

// Диалог за зареждания
public class LoadDialog extends BaseTradeDialog {
    private final String RESOURCE_ADDRESS = "/ui/dialogs/loadDialog.fxml";
    private final String DIALOG_TITLE = "Зареждане на продукт";
    private Double currentCash; // текущ баланс в магазина

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

    @Override
    protected boolean validateQuantity(String newValue) {
        if (!super.validateQuantity(newValue))
            return false;

        if (this.currentCash == null)
            return false;

        double cost = new StringDoubleConverter().fromString(newValue) * this.productComboBox.getValue().getLoadPrice();
        this.totalPriceTextField.setText(new StringDoubleConverter().toString(cost));
        if (!validateAvailableCash(cost))
            return false;

        return true;
    }

    private boolean validateAvailableCash(double cost) {
        // дали цената на зареждането е по-голяма от парите в магазина
        if (cost > this.currentCash || cost <= 0)
            return false;

        return true;
    }

    @Override
    protected void setTotalPriceField(Product product) { // цена на зареждането = количество * цена 1 ед.
        StringDoubleConverter stringDoubleConverter = new StringDoubleConverter();
        double quantity = stringDoubleConverter.fromString(this.quantityTextField.getText());

        double totalPrice = quantity * product.getLoadPrice();
        this.totalPriceTextField.setText(new StringDoubleConverter().toString(totalPrice));
    }
}

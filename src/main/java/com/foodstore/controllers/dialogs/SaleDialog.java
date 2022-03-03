package com.foodstore.controllers.dialogs;

import com.foodstore.models.StoreStock;
import javafx.stage.Window;

import java.time.LocalDate;
import java.util.Optional;

// Диалог за продажби
public class SaleDialog extends BaseTradeDialog{
    private final String RESOURCE_ADDRESS = "/ui/dialogs/saleDialog.fxml";
    private final String DIALOG_TITLE = "Продажба на продукт";

    public SaleDialog(Window owner, Optional<StoreStock> record) {
        super(owner, record);
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
}

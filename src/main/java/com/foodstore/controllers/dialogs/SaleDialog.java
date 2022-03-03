package com.foodstore.controllers.dialogs;

import com.foodstore.models.Product;
import com.foodstore.models.StoreStock;
import com.foodstore.utils.StoreStockStringConverter;
import com.foodstore.utils.converters.StringDoubleConverter;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.util.converter.DoubleStringConverter;

import java.util.Optional;

// Диалог за продажби
public class SaleDialog extends BaseTradeDialog {
    private final String RESOURCE_ADDRESS = "/ui/dialogs/saleDialog.fxml";
    private final String DIALOG_TITLE = "Продажба на продукт";
    private Double currentQuantity = 0d;

    // продукти към днешна дата за comboBox-а
    private ObservableList<StoreStock> storeStockList;

    @FXML
    protected TextField totalQuantityTextField;

    // list с опции за продажба на продукт
    @FXML
    protected ComboBox<StoreStock> storeStockComboBox;

    public SaleDialog(Window owner, Optional<StoreStock> record) {
        super(owner, record);
        this.currentQuantity = record.get().getQuantity();

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
        StoreStock storeStock = this.storeStockComboBox.getValue();
        storeStock.setQuantity(new DoubleStringConverter().fromString(quantityTextField.getText()));

        return storeStock;
    }

    @Override
    protected boolean validateQuantity(String newValue) {
        if (this.currentQuantity == null || this.totalQuantityTextField == null)
            return false;

        this.totalQuantityTextField.setText(new StringDoubleConverter().toString(this.currentQuantity));

        DoubleStringConverter doubleStringConverter = new DoubleStringConverter();
        Double quantity = doubleStringConverter.fromString(newValue);

        this.setTotalPriceField(this.storeStockComboBox.getValue().getProduct());

        if (quantity > this.currentQuantity || quantity <= 0)
            return false;

        return true;
    }

    @Override
    protected void validateDialog(Button okButton) { // валидираме полетата на диалога
        this.quantityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.validateQuantity(newValue);
            okButton.setDisable(!this.validateQuantity(newValue));
        });

        this.storeStockComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.currentQuantity = newValue.getQuantity();
            this.totalQuantityTextField.setText(newValue.getQuantity().toString());
            this.setTotalPriceField(newValue.getProduct());
        });
    }

    @Override
    protected void setDialogData(Optional<StoreStock> record) { // попълва comboBox-а
        this.record = record.orElseGet(StoreStock::new);

        if (record.isPresent()) {
            this.setTotalPriceField(record.get().getProduct());
            this.storeStockComboBox.setValue(record.get());
        }
    }

    public void setStoreStocks(ObservableList<StoreStock> storeStocks) {
        this.storeStockList = storeStocks;
        this.storeStockComboBox.setItems(this.storeStockList);
    }

    @Override
    protected void additionalDialogInitialization() {
        setOnShowing(dialogEvent -> Platform.runLater(() -> storeStockComboBox.requestFocus()));

        this.totalQuantityTextField.setDisable(true);
        this.totalPriceTextField.setDisable(true);

        initializeQuantityFields();
        initializePriceField();

        storeStockComboBox.setConverter(new StoreStockStringConverter());
    }

    @Override
    protected void setTotalPriceField(Product product) { // цена на зареждането = количество * цена 1 ед.
        StringDoubleConverter stringDoubleConverter = new StringDoubleConverter();
        double quantity = stringDoubleConverter.fromString(this.quantityTextField.getText());

        double totalPrice = quantity * product.getSellPrice();
        this.totalPriceTextField.setText(new StringDoubleConverter().toString(totalPrice));
    }

}

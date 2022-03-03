package com.foodstore.controllers.dialogs;

import com.foodstore.models.Product;
import com.foodstore.models.StoreStock;
import com.foodstore.utils.DoubleFormatFilter;
import com.foodstore.utils.EntityStringConverter;
import com.foodstore.utils.converters.StringDoubleConverter;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.UnaryOperator;

public abstract class BaseTradeDialog extends BaseRecordDialog<StoreStock> {
    // Полета на диалога
    @FXML
    protected TextField quantityTextField;

    @FXML
    protected ComboBox<Product> productComboBox;

    @FXML
    protected TextField totalPriceTextField;

    protected StoreStock record; // записа на диалога

    // Продукти
    protected ObservableList<Product> productsList;

    public BaseTradeDialog(Window owner, Optional<StoreStock> record) {
        super(owner, record);
    }

    @Override
    protected void setDialogData(Optional<StoreStock> record) {
        super.setDialogData(record);

        this.record = record.orElseGet(StoreStock::new);

        if (record.isPresent()) {
            this.productComboBox.setValue(record.get().getProduct());
            this.setTotalPriceField(record.get().getProduct());
        }
    }

    @Override
    protected void additionalDialogInitialization() {
        setOnShowing(dialogEvent -> Platform.runLater(() -> productComboBox.requestFocus()));

        this.totalPriceTextField.setDisable(true);
        productComboBox.setConverter(new EntityStringConverter<>());
        initializeQuantityFields();
        initializePriceField();

        // При промяна на селектиран продукт
        productComboBox.valueProperty().addListener((observable, oldValue, newValue) -> onChangeSelectedProduct(newValue));
    }

    @Override
    protected StoreStock getDialogData() { // взима въведеното в диалога
        return super.getDialogData();
    }

    public void setProducts(ObservableList<Product> productsList) {
        this.productsList = productsList;
        productComboBox.setItems(this.productsList);
    }

    protected void initializeQuantityFields() {
        UnaryOperator<TextFormatter.Change> filter = new DoubleFormatFilter();
        StringConverter<Double> stringDoubleConverter = new StringDoubleConverter();

        // Позволяваме писане само на целочислени стойности в полето за количество
        TextFormatter<Double> quantityFormatter = new TextFormatter<Double>(stringDoubleConverter, 0d, filter);
        this.quantityTextField.setTextFormatter(quantityFormatter);
    }

    @Override
    protected void validateDialog(Button okButton) { // валидираме полетата на диалога
        quantityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(!this.validateQuantity(newValue));
        });
    }

    protected boolean validateQuantity(String newValue) {
        if (this.productComboBox == null || this.productComboBox.getValue() == null)
            return false;

        return true;
    }

    private boolean checkForInvalidFields() {
        AtomicBoolean fieldsAreInvalid = new AtomicBoolean(false);

        if (productComboBox.getValue() == null)
            fieldsAreInvalid.set(true);

        if (quantityTextField.getText() == null || quantityTextField.getText().trim().isEmpty())
            fieldsAreInvalid.set(true);

        return fieldsAreInvalid.get();
    }

    protected void onChangeSelectedProduct(Product product) {
        // попълва default-на стойност на количеството
        this.quantityTextField.setText("1");
        setTotalPriceField(product);
    }

    protected void setTotalPriceField(Product product) { // цена на зареждането = количество * цена 1 ед.

    }

    protected void initializePriceField(){
        UnaryOperator<TextFormatter.Change> filter = new DoubleFormatFilter();
        StringConverter<Double> stringDoubleConverter = new StringDoubleConverter();

        // Позволяваме писане само на числени стойности в полета за цени
        TextFormatter<Double> loadPriceFormatter = new TextFormatter<>(stringDoubleConverter, 0.0, filter);
        this.totalPriceTextField.setTextFormatter(loadPriceFormatter);
    }
}

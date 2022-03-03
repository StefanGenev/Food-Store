package com.foodstore.controllers.dialogs;

import com.foodstore.models.Product;
import com.foodstore.utils.EntityStringConverter;
import com.foodstore.utils.IntegerFormatFilter;
import com.foodstore.utils.converters.StringIntegerConverter;
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

public abstract class BaseTradeDialog<T> extends BaseRecordDialog<T> {
    // Полета на диалога
    @FXML
    protected TextField quantityTextField;

    @FXML
    protected ComboBox<Product> productComboBox;

    @FXML
    protected TextField totalPriceTextField;

    protected Optional<T> record; // записа на диалога

    // Продукти
    protected ObservableList<Product> productsList;

    public BaseTradeDialog(Window owner, Optional<T> record) {
        super(owner, record);
    }

    @Override
    protected void additionalDialogInitialization() {
        setOnShowing(dialogEvent -> Platform.runLater(() -> productComboBox.requestFocus()));

        this.totalPriceTextField.setDisable(true);
        productComboBox.setConverter(new EntityStringConverter<>());
        initializeQuantityFields();
    }

    @Override
    protected void setDialogData(Optional<T> record) {
        super.setDialogData(record);
    }

    @Override
    protected T getDialogData() { // взима въведеното в диалога
        return super.getDialogData();
    }

    public void setProducts(ObservableList<Product> productsList) {
        this.productsList = productsList;
        productComboBox.setItems(this.productsList);
    }

    private void initializeQuantityFields() {
        UnaryOperator<TextFormatter.Change> filter = new IntegerFormatFilter();
        StringConverter<Integer> stringIntegerConverter = new StringIntegerConverter();

        // Позволяваме писане само на целочислени стойности в полето за количество
        TextFormatter<Integer> quantityFormatter = new TextFormatter<>(stringIntegerConverter, 0, filter);
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
}

package com.foodstore.controllers.dialogs;

import com.foodstore.models.Category;
import com.foodstore.models.Manufacturer;
import com.foodstore.models.Product;
import com.foodstore.models.Unit;
import com.foodstore.utils.DoubleFormatFilter;
import com.foodstore.utils.EntityStringConverter;
import com.foodstore.utils.converters.StringDoubleConverter;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.UnaryOperator;

// Диалог за продукти

public class ProductDialog extends BaseRecordDialog<Product> {
    private final String RESOURCE_ADDRESS = "/ui/productDialog.fxml";
    private final String DIALOG_TITLE = "Продукти";

    // Полета на диалог

    @FXML
    private TextField productNameTextField;

    @FXML
    private ComboBox<Manufacturer> manufacturerComboBox;

    @FXML
    private ComboBox<Unit> unitComboBox;

    @FXML
    private ComboBox<Category> categoryComboBox;

    @FXML
    private TextField loadPriceTextField;

    @FXML
    private TextField sellPriceTextField;

    // Запис
    private Product product;

    // Производители
    private ObservableList<Manufacturer> manufacturersList;

    // Категории
    private ObservableList<Category> categoriesList;

    public ProductDialog(Window owner, Optional<Product> record) {
        super(owner, record);
    }

    @Override
    public String getDialogTitle() {
        return DIALOG_TITLE;
    }

    @Override
    protected String getResourceAddress() {
        return RESOURCE_ADDRESS;
    }

    @Override
    protected void loadData() {
        unitComboBox.getItems().setAll(Unit.values());
    }


    @Override
    protected void validateDialog(Button okButton) {
        okButton.disableProperty()
                .bind(Bindings.createBooleanBinding(
                        this::checkForInvalidFields,
                        productNameTextField.textProperty(),
                        manufacturerComboBox.valueProperty(),
                        unitComboBox.valueProperty(),
                        categoryComboBox.valueProperty(),
                        loadPriceTextField.textProperty(),
                        sellPriceTextField.textProperty()
                ));
    }

    @Override
    protected void additionalDialogInitialization() {
        setOnShowing(dialogEvent -> Platform.runLater(() -> productNameTextField.requestFocus()));

        manufacturerComboBox.setConverter(new EntityStringConverter<>());
        categoryComboBox.setConverter(new EntityStringConverter<>());

        initializePriceFields();
    }

    @Override
    protected void setDialogData(Optional<Product> record) {
        product = record.orElseGet(Product::new);

        productNameTextField.setText(product.getProductName());
        manufacturerComboBox.setValue(product.getManufacturer());
        unitComboBox.setValue(product.getUnit());
        categoryComboBox.setValue(product.getCategory());

        if (product.getLoadPrice() > 0)
            loadPriceTextField.setText(Double.toString(product.getLoadPrice()));

        if (product.getSellPrice() > 0)
            sellPriceTextField.setText(Double.toString(product.getSellPrice()));
    }

    @Override
    protected Product getDialogData() {
        this.product.setProductName(productNameTextField.getText());
        this.product.setManufacturer(manufacturerComboBox.getValue());
        this.product.setUnit(unitComboBox.getValue());
        this.product.setCategory(categoryComboBox.getValue());
        this.product.setLoadPrice(Double.parseDouble(loadPriceTextField.getText()));
        this.product.setSellPrice(Double.parseDouble(sellPriceTextField.getText()));

        return this.product;
    }

    public void setManufacturers(ObservableList<Manufacturer> manufacturersList) {
        this.manufacturersList = manufacturersList;
        manufacturerComboBox.setItems(manufacturersList);
        manufacturerComboBox.setValue(product.getManufacturer());
    }

    public void setCategories(ObservableList<Category> categoriesList) {
        this.categoriesList = categoriesList;
        categoryComboBox.setItems(categoriesList);
        categoryComboBox.setValue(product.getCategory());
    }

    private boolean checkForInvalidFields() {
        AtomicBoolean fieldsAreInvalid = new AtomicBoolean(false);

        if (productNameTextField.getText() == null || productNameTextField.getText().trim().isEmpty())
            fieldsAreInvalid.set(true);

        if (manufacturerComboBox.getValue() == null)
            fieldsAreInvalid.set(true);

        if (unitComboBox.getValue() == null)
            fieldsAreInvalid.set(true);

        if (categoryComboBox.getValue() == null)
            fieldsAreInvalid.set(true);

        if (loadPriceTextField.getText() == null || loadPriceTextField.getText().trim().isEmpty())
            fieldsAreInvalid.set(true);

        if (sellPriceTextField.getText() == null || sellPriceTextField.getText().trim().isEmpty())
            fieldsAreInvalid.set(true);

        return fieldsAreInvalid.get();
    }

    private void initializePriceFields() {
        UnaryOperator<TextFormatter.Change> filter = new DoubleFormatFilter();
        StringConverter<Double> stringDoubleConverter = new StringDoubleConverter();

        // Позволяваме писане само на числени стойности в полета за цени
        TextFormatter<Double> loadPriceFormatter = new TextFormatter<>(stringDoubleConverter, 0.0, filter);
        loadPriceTextField.setTextFormatter(loadPriceFormatter);

        TextFormatter<Double> sellPriceFormatter = new TextFormatter<>(stringDoubleConverter, 0.0, filter);
        sellPriceTextField.setTextFormatter(sellPriceFormatter);
    }
}

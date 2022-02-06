package com.foodstore.controllers.dialogs;

import com.foodstore.models.Category;
import com.foodstore.models.Manufacturer;
import com.foodstore.models.Product;
import com.foodstore.models.Unit;
import com.foodstore.utils.EntityStringConverter;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.util.Optional;

// Диалог за продукти

public class ProductDialog extends BaseRecordDialog<Product> {

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
    protected String getResourceAddress() {
        return "/ui/productDialog.fxml";
    }

    @Override
    protected void loadData() {
        unitComboBox.getItems().setAll(Unit.values());
    }

    @Override
    protected void additionalDialogInitialization() {
        setOnShowing(dialogEvent -> Platform.runLater(() -> productNameTextField.requestFocus()));

        manufacturerComboBox.setConverter(new EntityStringConverter<>());
        categoryComboBox.setConverter(new EntityStringConverter<>());
    }

    @Override
    protected void setDialogData(Optional<Product> record) {
        product = record.orElseGet(Product::new);

        productNameTextField.setText(product.getProductName());
        manufacturerComboBox.setValue(product.getManufacturer());
        unitComboBox.setValue(product.getUnit());
        categoryComboBox.setValue(product.getCategory());
        loadPriceTextField.setText(Double.toString(product.getLoadPrice()));
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
}

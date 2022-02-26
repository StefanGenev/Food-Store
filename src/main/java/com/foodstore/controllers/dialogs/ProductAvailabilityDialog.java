package com.foodstore.controllers.dialogs;

import com.foodstore.models.Category;
import com.foodstore.models.Product;
import com.foodstore.models.StoreStock;
import com.foodstore.utils.EntityStringConverter;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Window;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


// Диалог за филтър на справката по категория и по дата на наличност
public class ProductAvailabilityDialog extends BaseRecordDialog<StoreStock> {
    private final String RESOURCE_ADDRESS = "/ui/dialogs/productAvailabilityDialog.fxml";
    private final String DIALOG_TITLE = "Филтър на справка по категории към дата";
    private final String DATE_TIME_FORMATTER = "dd/MM/yyyy";

    // Полета на диалога
    @FXML
    private ComboBox<Category> categoryComboBox;

    @FXML
    private DatePicker availabilityDateDatePicker;

    // Запис - филтър
    private StoreStock storeStock;

    // Категории
    private ObservableList<Category> categoriesList;

    public ProductAvailabilityDialog(Window owner) {
        super(owner, null);
    }

    @Override
    protected void additionalDialogInitialization() {
        setOnShowing(dialogEvent -> Platform.runLater(() -> categoryComboBox.requestFocus()));

        categoryComboBox.setConverter(new EntityStringConverter<>());

        availabilityDateDatePicker.getEditor().setDisable(true);
        availabilityDateDatePicker.getEditor().setOpacity(1);
    }

    @Override
    protected void setDialogData(Optional<StoreStock> record) {
        this.categoryComboBox.getSelectionModel().selectFirst();
        this.availabilityDateDatePicker.setValue(LocalDate.now());
    }

    @Override
    protected StoreStock getDialogData() {
        this.storeStock = new StoreStock();
        this.storeStock.setAvailabilityDate(availabilityDateDatePicker.getValue());
        Product product = new Product();
        product.setCategory(this.categoryComboBox.getValue());
        this.storeStock.setProduct(product);
        return this.storeStock;
    }

    public void setCategories(ObservableList<Category> categoriesList) {
        this.categoriesList = categoriesList;
        categoryComboBox.setItems(categoriesList);
    }

    @Override
    protected void validateDialog(Button okButton) { // валидираме полетата на диалога
        okButton.disableProperty()
                .bind(Bindings.createBooleanBinding(
                        this::checkForInvalidFields,
                        availabilityDateDatePicker.valueProperty(),
                        categoryComboBox.valueProperty()
                )); // ако има празно поле ОК е disable-нато
    }

    @Override
    public String getDialogTitle() {
        return DIALOG_TITLE;
    }

    @Override
    public String getResourceAddress() {
        return RESOURCE_ADDRESS;
    }


    private boolean checkForInvalidFields() {
        AtomicBoolean fieldsAreInvalid = new AtomicBoolean(false);

        if (availabilityDateDatePicker.getValue() == null || availabilityDateDatePicker.getValue().toString().isEmpty())
            fieldsAreInvalid.set(true);

        if (categoryComboBox.getValue() == null)
            fieldsAreInvalid.set(true);

        return fieldsAreInvalid.get();
    }
}


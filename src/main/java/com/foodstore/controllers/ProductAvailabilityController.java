package com.foodstore.controllers;

import com.foodstore.controllers.dialogs.ProductAvailabilityDialog;
import com.foodstore.models.Category;
import com.foodstore.models.Product;
import com.foodstore.models.StoreStock;
import com.foodstore.services.CategoryService;
import com.foodstore.services.StoreStockService;
import com.foodstore.utils.ProductTableCell;
import com.foodstore.utils.StringDateConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

// Контролер за страница с наличност на продукти

@Component
public class ProductAvailabilityController extends ModifiableTablePageController<StoreStock> {
    // Колони на таблицата
    @FXML
    private TableColumn<StoreStock, Product> colProductName;

    @FXML
    private TableColumn<StoreStock, String> colQuantity;

    @FXML
    private TableColumn<StoreStock, Product> colCategory;

    @FXML
    private TableColumn<StoreStock, LocalDate> colAvailabilityDate;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StoreStockService storeStockService;

    private boolean isFilterSpr = false; // дали при refresh да прави справката

    private StoreStock storeStock;

    @Override
    protected void setColumnProperties() {
        // Попълване на данни за продукт
        colProductName.setCellFactory(col -> new ProductTableCell<>());

        colCategory.setCellFactory(col -> new TableCell<StoreStock, Product>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null || product.getCategory() == null) {
                    setText(null);
                } else {
                    setText(product.getCategory().getCategoryName());
                }
            }
        });

        colAvailabilityDate.setCellFactory(TextFieldTableCell.forTableColumn(new StringDateConverter()));

        // Задаване от къде в табличния клас четем данните
        colProductName.setCellValueFactory(new PropertyValueFactory<>("product"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("product"));
        colAvailabilityDate.setCellValueFactory(new PropertyValueFactory<>("availabilityDate"));
    }

    @Override
    protected Optional<StoreStock> showSelectedRecord(Optional<StoreStock> recordOptional) {
        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);

        ObservableList<Category> categoriesList = FXCollections.observableArrayList();
        categoriesList.addAll(categoryService.findAllRecords());

        ProductAvailabilityDialog dialog = new ProductAvailabilityDialog(owner);
        dialog.setCategories(categoriesList);
        return dialog.showAndWait();
    }

    protected void doFilterSpr() {
        // Подготвяме полето за филтъра - this.storeStock
        this.storeStock = new StoreStock();
        Product product = new Product();
        Category category = new Category();
        product.setCategory(category);
        this.storeStock.setProduct(product);

        Optional<StoreStock> record = showSelectedRecord(Optional.of(this.storeStock));
        if(record.isEmpty())
            return;

        // вдигаме този флаг за да знае reloadTable(), че трябва да използва метода за филтър на справката
        this.isFilterSpr = true;
        this.storeStock = record.get();

        // изпълняваме условията на филтъра и обновяваме листа с резултатие на справката
        super.reloadTable();
        fillTableView();
    }

    @Override
    protected void loadRecordsFromDB() { // пренаписваме refresh-ването на листа, за да можем да избираме кога да викаме всички резултати и кога филтъра на справката
        recordsList.clear();
        if(this.isFilterSpr){
            recordsList.addAll(this.storeStockService.doFilterSpr(this.storeStock));
        }else{
            recordsList.addAll(this.storeStockService.findAllRecords());
        }
        this.isFilterSpr = false;
    }

    @Override
    protected void initializeRowContextMenu(ContextMenu contextMenu, TableRow<StoreStock> selectedRow) {
        this.initializeTableContextMenu(contextMenu);
    }

    @Override
    protected void initializeTableContextMenu(ContextMenu menu) {
        // меню
        final MenuItem doFilterSpr = new MenuItem("Филтър на справката");
        final MenuItem refreshMenuItem = new MenuItem("Обща наличност");

        // Действия при избор на менюто
        refreshMenuItem.setOnAction(event -> reloadTable());
        doFilterSpr.setOnAction(event -> doFilterSpr());

        // Добавяме дефинираните опции в контекстното меню
        menu.getItems().addAll(doFilterSpr, refreshMenuItem);
    }
}

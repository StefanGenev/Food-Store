package com.foodstore.controllers;

import com.foodstore.controllers.dialogs.LoadDialog;
import com.foodstore.controllers.dialogs.ProductDialog;
import com.foodstore.controllers.dialogs.SaleDialog;
import com.foodstore.models.*;
import com.foodstore.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

// Контролер за страницата с категориите продукти

@Component
public class ProductController extends ModifiableTablePageController<Product> {
    // Колони на таблицата
    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private TableColumn<Product, Manufacturer> colManufacturer;

    @FXML
    private TableColumn<Product, Unit> colUnit;

    @FXML
    private TableColumn<Product, Category> colCategory;

    @FXML
    private TableColumn<Product, Double> colLoadPrice;

    @FXML
    private TableColumn<Product, Double> colSellPrice;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private LoadService loadService;

    @Autowired
    private StoreStockService storeStockService;

    @Override
    protected void setColumnProperties() {
        // Link-ване на колонките
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colLoadPrice.setCellValueFactory(new PropertyValueFactory<>("loadPrice"));
        colSellPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));

        // Задаване от къде в табличния клас четем данните
        colManufacturer.setCellFactory(col -> new TableCell<Product, Manufacturer>() {
            @Override
            protected void updateItem(Manufacturer manufacturer, boolean empty) {
                super.updateItem(manufacturer, empty);
                if (empty || manufacturer == null) {
                    setText(null);
                } else {
                    setText(manufacturer.getManufacturerName());
                }
            }
        });

        colUnit.setCellFactory(col -> new TableCell<Product, Unit>() {
            @Override
            protected void updateItem(Unit unit, boolean empty) {
                super.updateItem(unit, empty);
                if (empty || unit == null) {
                    setText(null);
                } else {
                    setText(unit.toString());
                }
            }
        });

        colCategory.setCellFactory(col -> new TableCell<Product, Category>() {
            @Override
            protected void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                if (empty || category == null) {
                    setText(null);
                } else {
                    setText(category.getCategoryName());
                }
            }
        });
    }

    @Override
    protected Optional<Product> showSelectedRecord(Optional<Product> record) {
        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);

        ObservableList<Manufacturer> manufacturersList = FXCollections.observableArrayList();
        manufacturersList.addAll(manufacturerService.findAllRecords());

        ObservableList<Category> categoriesList = FXCollections.observableArrayList();
        categoriesList.addAll(categoryService.findAllRecords());

        ProductDialog dialog = new ProductDialog(owner, record);
        dialog.setManufacturers(manufacturersList);
        dialog.setCategories(categoriesList);
        return dialog.showAndWait();
    }

    @Override
    protected void initializeRowContextMenu(ContextMenu contextMenu, TableRow<Product> selectedRow) {
        final MenuItem loadMenu = new MenuItem("Зареди");
        loadMenu.setOnAction(event -> loadProduct(selectedRow));

        loadMenu.disableProperty().bind(selectedRow.emptyProperty());

        final MenuItem sellMenu = new MenuItem("Продай");
        sellMenu.setOnAction(event -> sellProduct(selectedRow));

        sellMenu.disableProperty().bind(selectedRow.emptyProperty());

        contextMenu.getItems().addAll(loadMenu, sellMenu);

        super.initializeRowContextMenu(contextMenu, selectedRow);
    }


    private static Optional<StoreStock> openDialogEntry(TableRow<Product> selectedRow) {
        // подготвяме полето за диалога
        StoreStock storeStock = new StoreStock();
        storeStock.setQuantity(0.0);
        if (selectedRow.isEmpty()) {
            Product product = new Product();
            Category category = new Category();
            product.setCategory(category);
            storeStock.setProduct(product);
        } else {
            storeStock.setProduct(selectedRow.getItem());
        }

        return Optional.of(storeStock);
    }

    private void loadProduct(TableRow<Product> selectedRow) {
        Optional<StoreStock> dialogParam = openDialogEntry(selectedRow);

        // отваряме диалога
        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        LoadDialog loadDialog = new LoadDialog(owner, dialogParam, this.storeStockService.getCash());

        // подаваме му листа с продуктите
        ObservableList<Product> productsList = FXCollections.observableArrayList();
        productsList.addAll(this.productService.findAllRecords());
        loadDialog.setProducts(productsList);

        // чакаме отговор от диалога
        dialogParam = loadDialog.showAndWait();

        // бизнес логика:
        if (dialogParam.isPresent()) {
            // добавяме в sales таблица
            Load load = new Load();
            load.setDateOfLoading(LocalDate.now());
            load.setProduct(dialogParam.get().getProduct());
            load.setQuantity(dialogParam.get().getQuantity());
            this.loadService.addLoad(load);

            // редактираме/добавяме в storestocks
            this.storeStockService.updateStoreStockByProductAndAvailabilityDate(dialogParam.get(), true);

            // каса
            if (!this.storeStockService.updateCashRegister(load)) {
                //TODOR: alert
            }
            getParentController().refreshCashRegister(); // обновяваме касата
        } else {
            //TODOR: alert
        }
    }

    private void sellProduct(TableRow<Product> selectedRow) {
        Optional<StoreStock> dialogParam = openDialogEntry(selectedRow);

        // взимаме Текуща наличност към днешна дата
        ObservableList<StoreStock> ssCurrentDate =  FXCollections.observableArrayList();
        ssCurrentDate.addAll(this.storeStockService.takeCurrentDateAvailability());

        // взимаме избрания продукт от листа
        for (int i = 0; i < ssCurrentDate.size(); i++) {
            if(ssCurrentDate.get(i).getProduct().getId() == selectedRow.getItem().getId()){
                dialogParam = Optional.of(ssCurrentDate.get(i));
                break;
            }
        }

        // отваряме диалога
        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        SaleDialog saleDialog = new SaleDialog(owner, dialogParam);

        // подаваме storeStocks на comboBox-а
        saleDialog.setStoreStocks(ssCurrentDate);

        // чакаме отговор от диалога
        dialogParam = saleDialog.showAndWait();

        // бизнес логика:
        if (dialogParam.isPresent()) {
            // sales таблица
            Sale sale = new Sale();
            sale.setDateOfSale(LocalDate.now());
            sale.setProduct(dialogParam.get().getProduct());
            sale.setQuantity(dialogParam.get().getQuantity());
            this.saleService.addSale(sale);

            // storestocks
            this.storeStockService.updateStoreStockByProductAndAvailabilityDate(dialogParam.get(), false);

            // каса
            if (!this.storeStockService.updateCashRegister(sale)) {
                //TODO:R alert
            }
            getParentController().refreshCashRegister(); // обновяваме касата
        } else {
            //TODO:R alert
        }
    }
}

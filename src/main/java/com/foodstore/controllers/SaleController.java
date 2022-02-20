package com.foodstore.controllers;

import com.foodstore.controllers.dialogs.PeriodFilterDialog;
import com.foodstore.models.PeriodFilter;
import com.foodstore.models.Product;
import com.foodstore.models.Sale;
import com.foodstore.services.SaleService;
import com.foodstore.utils.ProductTableCell;
import com.foodstore.utils.converters.StringDateConverter;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

// Контролер за страница Продажби

@Component
public class SaleController extends BaseTablePageController<Sale> {

    // Колони на таблицата
    @FXML
    private TableColumn<Sale, Product> colProductName;

    @FXML
    private TableColumn<Sale, Long> colQuantity;

    @FXML
    private TableColumn<Sale, LocalDate> colDateOfSale;

    // Клас за бизнес логика
    @Autowired
    private SaleService saleService;

    @Override
    protected void setColumnProperties() {
        // Попълване на дата
        colDateOfSale.setCellFactory(TextFieldTableCell.forTableColumn(new StringDateConverter()));

        // Попълване на данни за продукт
        colProductName.setCellFactory(col -> new ProductTableCell<>());

        // Задаване от къде в табличния клас четем данните
        colProductName.setCellValueFactory(new PropertyValueFactory<>("product"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDateOfSale.setCellValueFactory(new PropertyValueFactory<>("dateOfSale"));
    }

    @Override
    protected void initializeTableContextMenu(ContextMenu menu) {
        // Меню - филтър по период
        addPeriodFilterMenu(menu);

        super.initializeTableContextMenu(menu);
    }

    @Override
    protected void initializeRowContextMenu(ContextMenu contextMenu, TableRow<Sale> selectedRow) {
        // Меню - филтър по период
        addPeriodFilterMenu(contextMenu);

        super.initializeRowContextMenu(contextMenu, selectedRow);
    }

    private void addPeriodFilterMenu(ContextMenu menu) {
        // Меню
        final MenuItem periodFilterMenu = new MenuItem("Филтър по период");

        // Действия при избор на менюто
        periodFilterMenu.setOnAction(event -> filterByPeriod());

        // Добавяме дефинираните опции в контекстното меню
        menu.getItems().addAll(periodFilterMenu);
    }

    private void filterByPeriod() {
        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        PeriodFilterDialog dialog = new PeriodFilterDialog(owner, Optional.of(new PeriodFilter()));

        Optional<PeriodFilter> record = dialog.showAndWait();
        if (record.isEmpty())
            return;

        recordsList.clear();
        recordsList.addAll(saleService.findSalesForPeriod(record.get()));
    }
}
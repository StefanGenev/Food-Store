package com.foodstore.controllers.dialogs;


import com.foodstore.models.PeriodFilter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Window;

import java.time.LocalDate;
import java.util.Optional;

// Диалог за филтрация по период
public class PeriodFilterDialog extends BaseRecordDialog<PeriodFilter> {
    private final String RESOURCE_ADDRESS = "/ui/dialogs/periodFilterDialog.fxml";
    private final String DIALOG_TITLE = "Филтър по период";

    // Полета на диалог
    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    private PeriodFilter periodFilter;

    public PeriodFilterDialog(Window owner, Optional<PeriodFilter> record) {
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
    protected void additionalDialogInitialization() {
        setOnShowing(dialogEvent -> Platform.runLater(() -> dateFrom.requestFocus()));

        dateFrom.getEditor().setDisable(true);
        dateFrom.getEditor().setOpacity(1);

        dateTo.getEditor().setDisable(true);
        dateTo.getEditor().setOpacity(1);
    }

    @Override
    protected void setDialogData(Optional<PeriodFilter> record) {
        periodFilter = record.orElseGet(PeriodFilter::new);

        this.dateFrom.setValue(LocalDate.now());
        this.dateTo.setValue(LocalDate.now());
    }

    @Override
    protected PeriodFilter getDialogData() {
        this.periodFilter.setDateFrom(dateFrom.getValue());
        this.periodFilter.setDateTo(dateTo.getValue());

        return this.periodFilter;
    }
}

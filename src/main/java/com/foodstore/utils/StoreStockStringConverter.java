package com.foodstore.utils;

import javafx.util.StringConverter;
import com.foodstore.models.StoreStock;

public class StoreStockStringConverter extends StringConverter<StoreStock> {
    @Override
    public String toString(StoreStock object) {
        return object.getProduct().getName();
    }

    @Override
    public StoreStock fromString(String str) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

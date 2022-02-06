package com.foodstore.utils;

import com.foodstore.models.NameableEntity;
import javafx.util.StringConverter;

// Клас за четене на стринг от обект

public class EntityStringConverter<T extends NameableEntity> extends StringConverter<T> {
    @Override
    public String toString(T object) {
        return object.getName();
    }

    @Override
    public T fromString(String s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

package com.foodstore.utils.converters;

import javafx.util.StringConverter;

// Клас за трансформация от double към низ и обратно

public class StringDoubleConverter extends StringConverter<Double> {
    @Override
    public Double fromString(String s) {
        if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
            return 0.0;
        } else {
            return Double.valueOf(s);
        }
    }

    @Override
    public String toString(Double d) {
        return String.format("%.2f", d);
    }
}

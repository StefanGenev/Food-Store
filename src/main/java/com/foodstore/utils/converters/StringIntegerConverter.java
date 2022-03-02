package com.foodstore.utils.converters;

import javafx.util.StringConverter;

// Клас за трансформация от integer към низ и обратно

public class StringIntegerConverter extends StringConverter<Integer> {
    @Override
    public Integer fromString(String s) {
        if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
            return 0;
        } else {
            return Integer.valueOf(s);
        }
    }

    @Override
    public String toString(Integer n) {
        return n.toString();
    }
}

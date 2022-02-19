package com.foodstore.utils.converters;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Клас за трансформация от дата към низ и обратно

public class StringDateConverter extends StringConverter<LocalDate> {
    private final String pattern = "dd/MM/yyyy";
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

    @Override
    public String toString(LocalDate dateTime) {
        if (dateTime != null) {
            return dateFormatter.format(dateTime);
        } else {
            return "";
        }
    }

    @Override
    public LocalDate fromString(String string) {
        if (string != null && !string.isEmpty()) {
            return LocalDate.parse(string, dateFormatter);
        } else {
            return null;
        }
    }
}

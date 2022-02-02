package com.foodstore.utils;

import javafx.util.StringConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Клас за трансформация от дата към низ и обратно

public class StringDateConverter extends StringConverter<LocalDateTime> {
    private final String pattern = "dd/MM/yyyy HH:MM:SS";
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

    @Override
    public String toString(LocalDateTime dateTime) {
        if (dateTime != null) {
            return dateFormatter.format(dateTime);
        } else {
            return "";
        }
    }

    @Override
    public LocalDateTime fromString(String string) {
        if (string != null && !string.isEmpty()) {
            return LocalDateTime.parse(string, dateFormatter);
        } else {
            return null;
        }
    }
}

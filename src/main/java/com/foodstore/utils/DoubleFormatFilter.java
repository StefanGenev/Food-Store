package com.foodstore.utils;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

// Филтър за числа с плаваща запетая

public class DoubleFormatFilter implements UnaryOperator<TextFormatter.Change> {
    private final Pattern DOUBLE_PATTERN = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        String text = change.getControlNewText();
        if (DOUBLE_PATTERN.matcher(text).matches()) {
            return change;
        } else {
            return null ;
        }
    }
}

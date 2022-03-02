package com.foodstore.utils;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class IntegerFormatFilter implements UnaryOperator<TextFormatter.Change> {
    private final Pattern INTEGER_PATTERN = Pattern.compile("(([1-9][0-9]*)|0)?");

    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        String text = change.getControlNewText();
        if (INTEGER_PATTERN.matcher(text).matches()) {
            return change;
        } else {
            return null ;
        }
    }
}
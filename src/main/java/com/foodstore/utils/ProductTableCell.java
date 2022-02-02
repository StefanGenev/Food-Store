package com.foodstore.utils;

import com.foodstore.models.Product;
import javafx.scene.control.TableCell;

// Клас за клетка с данни за продукт

public class ProductTableCell<T> extends TableCell<T, Product> {
    @Override
    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);
        if (empty) {
            setText(null);
        } else {
            setText(product.getProductName());
        }
    }
}

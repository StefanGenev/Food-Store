package com.foodstore;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodStoreApplication {
    public static void main(String[] args) {
        Application.launch(FXApplication.class, args);
    }
}

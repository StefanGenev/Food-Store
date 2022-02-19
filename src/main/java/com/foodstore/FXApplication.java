package com.foodstore;

import com.foodstore.utils.events.StageReadyEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

// Главен клас за управление на user interface

public class FXApplication extends Application {

    private ConfigurableApplicationContext applicationContext; // Контекст на приложението

    @Override
    public void init() {
        // Инициализация на контекста
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder()
                .sources(FoodStoreApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) {
        // Стартиране на приложението

        // Създаваме евент, който ще извърши инициализация на прозореца на приложението
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        // Действия за затваряне на приложението
        applicationContext.close();
        Platform.exit();
    }
}
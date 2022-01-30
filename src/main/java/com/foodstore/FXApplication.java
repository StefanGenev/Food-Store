package com.foodstore;

import com.foodstore.controllers.StoreController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

// Главен клас за управление на user interface

public class FXApplication extends Application {
    @Value("${spring.application.ui.title}")
    private String applicationTitle;

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

        // Инициализация на клас за зареждане на ресурси fxml
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);

        // Зареждане на view
        Parent root = fxWeaver.loadView(StoreController.class);

        // Зареждане на сцена и заглавие на приложението
        Scene scene = new Scene(root);
        stage.setScene(scene);

        //TODO Add reading from parameter
        stage.setTitle("");

        stage.show();
    }

    @Override
    public void stop() {
        // Действия за затваряне на приложението
        applicationContext.close();
        Platform.exit();
    }
}
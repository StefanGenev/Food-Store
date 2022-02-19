package com.foodstore.utils;

import com.foodstore.controllers.StoreController;
import com.foodstore.utils.events.StageReadyEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

// Клас за иницализация на приложението

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final String applicationTitle; // заглавие на приложението
    private ApplicationContext applicationContext; // Контекст на приложението

    private final Image APPLICATION_ICON = new Image("/images/appicon.png"); // Икона на приложението

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle, ApplicationContext applicationContext){
        super();
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }


    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        final Stage stage = stageReadyEvent.getStage();

        // Инициализация на клас за зареждане на ресурси fxml
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);

        // Зареждане на view
        Parent root = fxWeaver.loadView(StoreController.class);

        // Зареждане на сцена и заглавие и икона на приложението
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(applicationTitle);
        stage.getIcons().add(APPLICATION_ICON);
        stage.show();
    }
}

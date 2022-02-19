package com.foodstore.utils.events;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

// Действия при стартиране на приложението

public class StageReadyEvent extends ApplicationEvent {

    public StageReadyEvent(Stage stage) {
        super(stage);
    }

    public Stage getStage() {
        return (Stage) this.getSource();
    }
}
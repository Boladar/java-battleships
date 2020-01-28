package sample;

import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class UpdateTimeLabel extends ScheduledService<Void> {

    private Label target;

    public UpdateTimeLabel(Label target) {
        this.target = target;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                Platform.runLater(() -> {
                    Integer value = Integer.parseInt(target.getText()) + 1;
                    target.setText(value.toString());
                });
                return null;
            }
        };
    }
}

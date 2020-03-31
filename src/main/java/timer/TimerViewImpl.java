package timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TimerViewImpl implements TimerView {

    private static final int UPDATE_RATE = 1_000;
    private final Timer timer;
    private final Label label;
    private final Timeline displayRefresher;

    protected TimerViewImpl(final Timer timer, final Label label, final Color numbersColor, final Color backgroundColor, final Font font) {
        this.timer = timer;
        this.label = label;
        this.label.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        this.label.setTextFill(numbersColor);
        this.label.setFont(font);
        this.displayRefresher = new Timeline(new KeyFrame(javafx.util.Duration.millis(UPDATE_RATE), updateView()));

    }

    @Override
    public final void startDisplaying() {
        this.displayRefresher.setCycleCount(Timeline.INDEFINITE);
        this.displayRefresher.play();
    }

    @Override
    public final void stopDisplaying() {
        this.displayRefresher.stop();
    }

    private EventHandler<ActionEvent> updateView() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                label.setText(String.valueOf(timer.getValue() / UPDATE_RATE));
            }
        };
    }

}

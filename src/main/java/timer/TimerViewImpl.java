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

/**
 * The implementation of {@link TimerView}.
 */
public class TimerViewImpl implements TimerView {

    /**
     * This is the refresh rate of the displayed value, expressed in
     * <i>milliseconds</i>.
     */
    private static final int UPDATE_RATE = 1_000;

    private final Timer timer;
    private final Label label;
    private final Timeline displayRefresher;

    /**
     * Sets up the {@link Label} as chosen.
     * 
     * @param timer
     *                            The {@link Timer} to display.
     * @param label
     *                            The {@link Label} that displays the Timer.
     * @param numbersColor
     *                            The color of the numbers displayed.
     * @param backgroundColor
     *                            The color of the Label.
     * @param font
     *                            The font of the numbers displayed.
     */
    protected TimerViewImpl(final Timer timer, final Label label, final Color numbersColor, final Color backgroundColor,
            final Font font) {
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

    /**
     * @return Returns a {@link EventHandler} with the instructions to refresh the
     *         numbers displayed.
     */
    private EventHandler<ActionEvent> updateView() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                label.setText(String.valueOf(timer.getValue() / UPDATE_RATE));
            }
        };
    }

}

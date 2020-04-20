package timer;

import graphicsutility.TimeEventsListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * The implementation of {@link TimerView}.
 */
public class TimerViewImpl implements TimerView {

    private static final int UPDATE_RATE = 1_000;

    private final Timer timer;
    private final Label label;
    private final Timeline displayRefresher;

    private TimeEventsListener listener;

    /**
     * Sets up the {@link Label} as chosen.
     * 
     * @param timer
     *                  The {@link Timer} to display.
     * @param label
     *                  The {@link Label} that displays the Timer.
     */
    public TimerViewImpl(final Timer timer, final Label label) {
        this.timer = timer;
        this.label = label;
        this.displayRefresher = new Timeline(new KeyFrame(Duration.millis(UPDATE_RATE / 2), updateView()));

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

    @Override
    public final void setTimeEventListener(final TimeEventsListener listener) {
        this.listener = listener;
    }

    /**
     * This method will update the label's text each second with the current timer's
     * value.<br>
     * The label will stop refreshing its value when it reaches the limit.
     * 
     * @return Returns a {@link EventHandler} with the instructions to refresh the
     *         numbers displayed.
     */
    private EventHandler<ActionEvent> updateView() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                final long currentTime = timer.getValue() / UPDATE_RATE;
                label.setText(String.valueOf(currentTime));
                if (currentTime == timer.getLimit()) {
                    stopDisplaying();
                    if (timer.getLimit() == Verse.DOWN.getLimit()) {
                        outOfTime();
                    }
                }
            }
        };
    }

    /**
     * Creates a {@link OutOfTimeEvent}.
     */
    private void outOfTime() {
        this.listener.singlePlayerTimeEvent(new OutOfTimeEvent(this));
    }
}

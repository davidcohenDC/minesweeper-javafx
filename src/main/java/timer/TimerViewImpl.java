package timer;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The {@link Label} implementing {@link TimerView}.
 */
public class TimerViewImpl extends Label implements TimerView {

    private final Timer timer;
    private boolean stopShowing;

    /**
     * Sets up the label.
     * @param timer
     * The Timer to display.
     * @param font
     * The Font of the displayed numbers.
     * @param color
     * The Color of the displayed numbers.
     */
    public TimerViewImpl(final Timer timer, final Font font, final Color color) {
        super();
        this.stopShowing = false;
        this.timer = timer;
        super.setFont(font);
        super.setTextFill(color);
    }

    @Override
    public final void showTimer() {
        while (!stopShowing) {
            super.setText(String.valueOf(this.timer.getValue()));
            super.setVisible(true);
        }
    }

    @Override
    public final void stopShowing() {
        this.stopShowing = true;
    }

}

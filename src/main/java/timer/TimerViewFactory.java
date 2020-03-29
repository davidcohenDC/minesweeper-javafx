package timer;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A Factory to modify a timer visual representation easily.
 */
public interface TimerViewFactory {

    /**
     * Creates a default configuration of a Timers GUI.
     * @param timer
     * The Timer to visualize.
     * @return
     * Returns a {@link TimerView} to add to the GUI.
     */
    TimerView defaultTimerDisplay(Timer timer);

    /**
     * Creates a default configuration of a Timers GUI.
     * @param timer
     * The Timer to visualize.
     * @param font 
     * The Font of the numbers displayed.
     * @param color
     * The Color of the number displayed.
     * @return
     * Returns a {@link TimerView} to add to the GUI.
     */
    TimerView personalizedTimerDisplay(Timer timer, Font font, Color color);
}

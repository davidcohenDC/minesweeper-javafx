package timer;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A Factory created to make customization easier for The Timers' GUI.
 */
public interface TimerViewFactory {

    /**
     * Creates the Timer's GUI with <strong>default</strong> settings.
     * 
     * @param timer
     *                  The {@link Timer} to display.
     * @param label
     *                  The {@link Label} to use.
     * @return Returns a {@link TimerView}.
     */
    TimerView createDefault(Timer timer, Label label);

    /**
     * Creates the Timer's GUI with <strong>default</strong> settings.
     * 
     * @param timer
     *                            The {@link Timer} to display.
     * @param label
     *                            The {@link Label} to use.
     * @param numbersColor
     *                            The desired color for the numbers displayed.
     * @param backgroundColor
     *                            The desired color for the display's background.
     * @param font
     *                            The desired font for the numbers displayed.
     * @return Returns a {@link TimerView}.
     */
    TimerView createPersonalized(Timer timer, Label label, Color numbersColor, Color backgroundColor, Font font);

}

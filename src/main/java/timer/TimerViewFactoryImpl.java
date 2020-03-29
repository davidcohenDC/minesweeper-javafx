package timer;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The implementation of {@link TimerViewFactory}.
 */
public class TimerViewFactoryImpl implements TimerViewFactory {

    @Override
    public final TimerView defaultTimerDisplay(final Timer timer) {
        return new TimerViewImpl(timer, Font.getDefault(), Color.BLACK);
    }

    @Override
    public final TimerView personalizedTimerDisplay(final Timer timer, final Font font, final Color color) {
       return new TimerViewImpl(timer, font, color);
    }
}

package timer;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The implementation of {@link TimerFactory}.
 */
public class TimerViewFactoryImpl implements TimerViewFactory {

    @Override
    public final TimerView createDefault(final Timer timer, final Label label) {
        return new TimerViewImpl(timer, label, Color.BLACK, Color.LIGHTGRAY, Font.getDefault());
    }

    @Override
    public final TimerView createPersonalized(final Timer timer, final Label label, final Color numbersColor,
            final Color backgroundColor, final Font font) {
        return new TimerViewImpl(timer, label, numbersColor, backgroundColor, font);
    }

}

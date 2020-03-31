package timer;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public interface TimerViewFactory {

    TimerView createDefault(Timer timer, Label label);

    TimerView createPersonalized(Timer timer, Label label, Color numbersColor, Color backgroundColor, Font font);

}

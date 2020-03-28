package timer;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class TimerViewImpl extends Label implements TimerView {

    private final Timer timer;

    public TimerViewImpl(final Timer timer, final Font font) {
        super();
        super.setFont(font);
        this.timer = timer;
    }

    @Override
    public final void showTimer() {
        while (!stopShowing()) {
            super.setText(String.valueOf(timer.getValue()));
        }
    }

    private boolean stopShowing() {
        // TODO Auto-generated method stub
        return false;
    }

}

package timer;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class TimerViewImpl extends Label implements TimerView {

    private final Timer timer;
    private boolean stopShowing;

    public TimerViewImpl(final Timer timer, final Font font) {
        super();
        this.stopShowing = false;
        this.timer = timer;
        super.setFont(font);
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

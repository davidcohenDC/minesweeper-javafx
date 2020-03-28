package timer;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class TimerViewImpl extends Label implements TimerView {

    private final Timer timer;
    private boolean stopTimer;

    public TimerViewImpl(final Timer timer) {
        super();
        this.stopTimer = false;
        this.timer = timer;
    }

    @Override
    public final void showTimer() {
        while (!stopTimer) {
            super.setText(String.valueOf(this.timer.getValue()));
            super.setVisible(true);
        }
    }

    @Override
    public final void stopShowing() {
        this.stopTimer = true;
    }

}

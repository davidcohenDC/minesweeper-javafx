package graphics;

import gamelogics.GameStatus;
import graphicsutility.AlertHandler;
import timer.OutOfTimeEvent;
/**
 * The implementation of {@link TimeEventsListener} to handle {@link OutOfTimeEvent}.
 */
public class TimeEventsListenerImpl implements TimeEventsListener {

    private SinglePlayerController controller;

    public TimeEventsListenerImpl(final SinglePlayerController controller) {
        this.controller = controller;
    }

    @Override
    public final void singlePlayerTimeEvent(final OutOfTimeEvent event) {
        this.controller.endTimer();
    }


}

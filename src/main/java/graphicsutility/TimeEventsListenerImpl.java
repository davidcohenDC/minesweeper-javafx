package graphicsutility;

import graphics.SinglePlayerController;
import timer.OutOfTimeEvent;

/**
 * The implementation of {@link TimeEventsListener} to handle
 * {@link OutOfTimeEvent}.
 */
public class TimeEventsListenerImpl implements TimeEventsListener {

    private final SinglePlayerController controller;

    /**
     * Creates a {@link TimeEventsListener}.
     * 
     * @param controller
     *                       The {@link SinglePlayerController} of the game to
     *                       notify.
     */
    public TimeEventsListenerImpl(final SinglePlayerController controller) {
        this.controller = controller;
    }

    @Override
    public final void singlePlayerTimeEvent(final OutOfTimeEvent event) {
        this.controller.endTimer();
    }

}

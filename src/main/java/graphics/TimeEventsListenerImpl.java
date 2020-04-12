package graphics;

import gamelogics.GameStatus;
import graphicsutility.AlertHandler;
import timer.OutOfTimeEvent;
/**
 * The implementation of {@link TimeEventsListener} to handle {@link OutOfTimeEvent}. 
 */
public class TimeEventsListenerImpl implements TimeEventsListener {

    private SinglePlayerController singlePlayerController;
    private MultiplayerController multiplayerController;

    public TimeEventsListenerImpl(final MultiplayerController controller) {
        this.multiplayerController = controller;
    }

    public TimeEventsListenerImpl(final SinglePlayerController controller) {
        this.singlePlayerController = controller;
    }

    @Override
    public final void singlePlayerTimeEvent(final OutOfTimeEvent event) {
        this.singlePlayerController.endTimer(GameStatus.LOST);
    }


}

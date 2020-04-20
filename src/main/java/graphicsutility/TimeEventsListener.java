package graphicsutility;

import java.util.EventListener;
import timer.OutOfTimeEvent;

/**
 * This interface models an {@link EventListener} to handle events linked to the
 * Timer.
 */
public interface TimeEventsListener extends EventListener {

    /**
     * Begins a procedure to change the games'course.
     * 
     * @param event
     *                  The event occurred.
     */
    void singlePlayerTimeEvent(OutOfTimeEvent event);

}

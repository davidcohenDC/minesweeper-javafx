package graphics;

import timer.OutOfTimeEvent;
/**
 * The implementation of {@link TimeEventsListener} to handle {@link OutOfTimeEvent}. 
 */
public class TimeEventsListenerImpl implements TimeEventsListener {

    @Override
    public final void timeEventOccured(final OutOfTimeEvent event) {
        //TODO Game ending procedure.
        System.out.println("EVENTO TIMER SCATENATO!!");
    }

}

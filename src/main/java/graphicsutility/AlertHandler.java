package graphicsutility;

import timer.OutOfTimeEvent;
import scoresystem.Player;
import java.util.Optional;

/**
 * Interface to handle and manage the Alerts.
 */
public interface AlertHandler {

    /**
     * The won alert for the player selected.
     *
     * @param player
     *                  the {@link Player} selected to perform the alert
     *
     */
    void won(final Optional<Player> player);

    /**
     * The lost alert for the player selected.
     *
     * @param player
     *                  the {@link Player} selected to perform the alert
     *
     */
    void lost(final Optional<Player> player);

    /**
     * The lost alert when event from {@link OutOfTimeEvent} occur.
     *
     */
    void lostWithTimer();

    /**
     * The confirm alert.
     *
     * @return the chosen of the player
     *
     */
    Boolean confirm();

    /**
     * The alert to handle the same name error of {@link Player}.
     *
     */
    void sameName();


}

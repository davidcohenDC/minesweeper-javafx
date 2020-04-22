package graphicsutility;

import javafx.scene.control.Label;
import java.util.HashMap;

/**
 * The PlayerSupervisor.
 * <p>
 * This interface manage the turn of each {@link scoresystem.Player} and save
 * them
 * </p>
 */
public interface PlayerSupervisor {

    /**
     * Set the {@link scoresystem.Player} name on the {@link Label}.
     *
     * @param label
     *                  The {@link Label} used to write the
     *                  {@link scoresystem.Player} name
     *
     */
    void view(final Label label);

    /**
     * Set the baton to this {@link PlayerSupervisor}.
     *
     */
    void setBaton();

    /**
     * Unset the baton to this {@link PlayerSupervisor}.
     *
     */
    void unsetBaton();

    /**
     * Check if the Baton is set up.
     *
     * @return the status according with the Baton
     *
     */
    Boolean isMaster();

    /**
     * Set the Baton true to the next {@link PlayerSupervisor}.
     *
     * @param playersMap
     *                       The map of all {@link PlayerSupervisor}
     */
    void giveMaster(final HashMap<PlayerSupervisor, Boolean> playersMap);

}

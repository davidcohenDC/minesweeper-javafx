package graphicsutility;

import java.util.Optional;

/**
 * Interface to set the AcquireDialog Style.
 */
public interface AcquireDialog {

    /**
     * The {@link AcquireDialog} for acquire the name used in the game.
     *
     * @return a Optional {@link String} used for set the {@link scoresystem.Player} name
     *
     */
    Optional<String> acquireFirst();

    /**
     * The {@link AcquireDialog} for acquire the second name used in the game.s
     *
     * @return a Optional {@link String} used for set the {@link scoresystem.Player} for second name
     *
     */
    Optional<String> acquireSecond();

}

package timer;

/**
 * A Factory to create a {@link Timer} for each game mode.
 */
public interface TimerFactory {

    /**
     * Creates a Timer for the "Standard" mode of the game.
     * 
     * @return Returns a {@link Timer} going from 0 up to 999.
     */
    Timer createTimerForStandardMode();

    /**
     * Creates a Timer for the "Beat the Timer" mode of the game.
     * 
     * @param amountOfTime
     *                         The amount of time the player has to finish the game
     *                         in seconds
     * @return Returns a {@link Timer} going from the value of amountOfTime down to
     *         0.
     */
    Timer createTimerForBeatTheTimerMode(int amountOfTime);

    /**
     * Creates two Timers for the "1 versus 1" mode of the game.
     * 
     * @return Returns a {@link DoubleTimer}, the class to manage the two Timers.
     */
    DoubleTimer createTimersFor1vs1Mode();
}

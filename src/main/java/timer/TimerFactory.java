package timer;

/**
 * Factory to create right the {@link Timer} for each {@link Modality}.
 */
public interface TimerFactory {

    /**
     * Creates a Timer for the "Standard" mode of the game.
     * 
     * @return Returns a Timer going from 0 up to 999.
     */
    Timer createTimerForStandardMode();

    /**
     * Creates a Timer for the "Beat the Timer" mode of the game.
     * 
     * @param amountOfTime
     *                         The amount of time the player has to finish the game
     *                         in <i>seconds</i>.
     * @return Returns a Timer going from the value of amountOfTime down to 0.
     */
    Timer createTimerForBeatTheTimerMode(int amountOfTime);

    /**
     * Creates two Timers for the "1 vs 1" mode of the game.
     * 
     * @return Returns a {@link DoubleTimer} to handle two Standard Timers.
     */
    DoubleTimer createTimersFor1vs1Mode();
}

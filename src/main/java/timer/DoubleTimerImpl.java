package timer;

/**
 * An implementation of {@link MultipleTimers} that handles two Standard Timers.
 */
public class DoubleTimerImpl implements MultipleTimers {

    private final Timer player1Timer;
    private final Timer player2Timer;

    /**
     * Sets up the Timers as Standard Timers.
     */
    protected DoubleTimerImpl() {
        this.player1Timer = new TimerImpl(0, Verse.UP);
        this.player2Timer = new TimerImpl(0, Verse.UP);
    }

    @Override
    public final long getValue() {
        return runningTimer().getValue();
    }

    @Override
    public final void start() {
        this.player1Timer.start();
    }

    @Override
    public final void stop() {
        this.player1Timer.stop();
        this.player2Timer.stop();
    }

    @Override
    public final boolean isRunning() {
        return this.player1Timer.isRunning() || this.player2Timer.isRunning();
    }

    @Override
    public final void switchTurn() {
        final Timer oldRunningTimer = runningTimer();
        runningTimer().stop();

        if (oldRunningTimer.equals(player1Timer)) {
            player1Timer.start();
        } else {
            player2Timer.start();
        }
    }

    /**
     * @return Returns the Timer running at the moment.
     */
    private Timer runningTimer() {
        if (this.player1Timer.isRunning()) {
            return this.player1Timer;
        } else {
            return this.player2Timer;
        }
    }
}

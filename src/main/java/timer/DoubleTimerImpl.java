package timer;

/**
 * An implementation of {@link DoubleTimer} that handles two Standard Timers.
 */
public class DoubleTimerImpl implements DoubleTimer {

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
            player2Timer.start();
        } else {
            player1Timer.start();
        }
    }

    @Override
    public final Timer getPlayer1Timer() {
        return this.player1Timer;
    }

    @Override
    public final Timer getPlayer2Timer() {
        return this.player2Timer;
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

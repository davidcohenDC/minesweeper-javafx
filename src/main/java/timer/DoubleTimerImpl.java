package timer;

/**
 * The implementation of {@link DoubleTimer}.
 * <p>
 * This class extends {@link Thread}.
 */
public class DoubleTimerImpl extends Thread implements DoubleTimer {

    private final Timer player1Timer;
    private final Timer player2Timer;

    /**
     * Creates the two standard Timers to handle.
     */
    public DoubleTimerImpl() {
        this.player1Timer = new TimerImpl(0, Verse.UP);
        this.player2Timer = new TimerImpl(0, Verse.UP);
    }

    /**
     * Sets up the DoubleTimer making the player one start first and putting player
     * 2 on hold.
     */
    @Override
    public final void run() {
        this.player1Timer.startTimer();
        this.player2Timer.startTimer();
        this.player2Timer.pause();
    }

    @Override
    public final void switchTurn() {
        final Timer temporaryTimerHolder = timerInAction();
        timerInAction().pause();

        if (temporaryTimerHolder.equals(this.player1Timer)) {
            this.player2Timer.play();
        } else if (temporaryTimerHolder.equals(this.player2Timer)) {
            this.player1Timer.play();
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

    @Override
    public final void startTimers() {
        start();
    }

    /**
     * @return Returns which timer is running.
     *         <p>
     *         if none of them are throws an <code>illegalStateException</code>.
     */
    private Timer timerInAction() {
        if (!this.player1Timer.isPaused()) {
            return player1Timer;
        } else if (!this.player2Timer.isPaused()) {
            return player2Timer;
        }
        throw new IllegalStateException("Both timers are not running");
    }
}

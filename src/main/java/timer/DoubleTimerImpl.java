package timer;

public class DoubleTimerImpl extends Thread implements DoubleTimer {

    private final TimerImpl player1Timer;
    private final TimerImpl player2Timer;

    public DoubleTimerImpl() {
        this.player1Timer = new TimerImpl(0, Verse.UP);
        this.player2Timer = new TimerImpl(0, Verse.UP);
    }

    /**
     * Sets up the DoubleTimer making the player one start first
     * and putting player 2 on hold.
     */
    @Override
    public final void run() {
        this.player1Timer.start();
        this.player2Timer.start();
        this.player2Timer.pause();
    }

    @Override
    public final void switchTurn() {
        Timer temporaryTimerHolder = timerInAction();
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

    /**
     * @return
     * Returns which timer is running.
     * 
     * if none of them are throws an illegalStateException
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

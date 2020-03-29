package timer;

/**
 * The implementation of {@link Timer}.
 */
public class TimerImpl extends Thread implements Timer {

    private static final int SLEEP_TIME = 1_000;

    private final Verse verse;

    private int value;
    private boolean paused;
    private boolean stop;

    protected TimerImpl(final int startingTime, final Verse verse) {
        this.verse = verse;
        this.value = startingTime;
        this.paused = true;
        this.stop = false;
    }

    /**
     * Increases the timer value in the chosen verse. 
     */
    @Override
    public final void run() {
        this.paused = false;

        while (!stop) {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!paused) {
                this.value = this.value + this.verse.getVerseIncrementValue();
            }
        }
    }

    @Override
    public final synchronized int getValue() {
        return this.value;
    }

    @Override
    public final synchronized void pause() {
        this.paused = true;
    }

    @Override
    public final synchronized void play() {
        this.paused = false;
    }

    @Override
    public final synchronized void stopTimer() {
        pause();
        this.stop = true;
    }

    @Override
    public final synchronized boolean isPaused() {
        return this.paused;
    }

    @Override
    public final synchronized void startTimer() {
        start();
    }
}

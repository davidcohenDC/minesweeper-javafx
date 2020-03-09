package timer;

public class TimerImpl extends Thread implements Timer {

    private static final int SLEEP_TIME = 1_000;

    private final Verse verse;
    private int increment;
    private int value;
    private boolean paused = false;

    protected TimerImpl(final int startingTime, final Verse verse) {
        this.verse = verse;
        this.value = startingTime;
    }
    /**
     * Increases the timer value by verse. 
     */
    @Override
    public final void run() {

        while (!paused) {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.value = this.value + verse.getVerseIncrementValue();
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
    public final synchronized boolean isPaused() {
        return paused;
    }

}

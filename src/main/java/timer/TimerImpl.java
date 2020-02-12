package timer;

public class TimerImpl implements Timer {

    private static final int SLEEP_TIME = 1_000;

    private final Verse verse;
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
    public final int getValue() {
        return this.value;
    }

    @Override
    public final void pause() {
        this.paused = true;
    }

    @Override
    public final void unPause() {
        this.paused = false;
    }

}

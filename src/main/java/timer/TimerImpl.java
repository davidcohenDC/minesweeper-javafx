package timer;

/**
 * The implementation of {@link Timer}.
 */
public class TimerImpl implements Timer {

    private final Verse verse;
    private final int limit;

    private long initialTime;
    private long startTime;
    private boolean stop;

    /**
     * Sets up the Timer.
     * <p>
     * This will also set the Timer's limit accordingly to its {@link Verse}.
     * 
     * @param initialTime
     *                        The amount of time from which the Timer will start.
     * @param verse
     *                        The {@link Verse} of the Timer.
     */
    protected TimerImpl(final long initialTime, final Verse verse) {
        this.initialTime = initialTime;
        this.verse = verse;
        this.limit = verse.getLimit() * 1_000;
        this.stop = true;
    }

    @Override
    public final long getValue() {

        if (isRunning()) {
            if (limitNotReached()) {
                this.initialTime = this.initialTime
                        + ((System.currentTimeMillis() - this.startTime) * this.verse.getVerseIncrementValue());
                this.startTime = System.currentTimeMillis();
            } else {
                stop();
                this.initialTime = this.limit;
            }
        }
        return this.initialTime;

    }

    @Override
    public final void start() {
        this.stop = false;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public final void stop() {
        this.stop = true;
    }

    @Override
    public final boolean isRunning() {
        return !this.stop;
    }

    @Override
    public final int getLimit() {
        return this.limit;
    }

    /**
     * @return Returns {@value True} if the Timer did not reach its limit.
     */
    private boolean limitNotReached() {
        return (this.initialTime - this.limit) * this.verse.getVerseIncrementValue() < 0;
    }
}

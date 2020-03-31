package timer;

public class TimerImpl implements Timer {

    private final Verse verse;
    private final int limit;

    private long initialTime;
    private long startTime;
    private boolean stop;

    protected TimerImpl(final long initialTime, final Verse verse) {
        this.initialTime = initialTime;
        this.verse = verse;
        this.limit = verse.getLimit();
        this.stop = true;
    }

    @Override
    public final long getValue() {
        if (!stop && this.initialTime != this.limit) {
            this.initialTime = this.initialTime + ((System.currentTimeMillis() - this.startTime) * this.verse.getVerseIncrementValue());
            this.startTime = System.currentTimeMillis();
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

}

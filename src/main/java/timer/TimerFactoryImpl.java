package timer;

/**
 * The implementation for {@link TimerFactory}.
 */
public class TimerFactoryImpl implements TimerFactory {

    @Override
    public final Timer createTimerForStandardMode() {
        return new TimerImpl(0, Verse.UP);
    }

    @Override
    public final Timer createTimerForBeatTheTimerMode(final int amountOfTime) {
        return new TimerImpl(amountOfTime * 1_000, Verse.DOWN);
    }

    @Override
    public final DoubleTimer createTimersFor1vs1Mode() {
        return new DoubleTimerImpl(createTimerForStandardMode(), createTimerForStandardMode());
    }

}

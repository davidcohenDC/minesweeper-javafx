package timer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * A Class to test {@link Timer} functionalities are working.
 */
class TestTimer {

    /**
     * The amount of milliseconds in a second.
     */
    private static final int TO_SECONDS = 1_000;

    private final TimerFactory f = new TimerFactoryImpl();

    @Test
    public void standardTimerTest() {

        final Timer t = f.createTimerForStandardMode();
        assertFalse(t.isRunning());
        assertEquals(0, t.getValue());
        assertEquals(Verse.UP.getLimit() * TO_SECONDS, t.getLimit());

        t.start();
        while (t.getValue() == 0) {
            assertTrue(t.isRunning());
        }
        t.stop();
        assertFalse(t.isRunning());
        assertEquals(1, t.getValue());
        assertTrue(t.getValue() > 0);
    }

    @Test
    public void beatTheTimerTest() {

        final int startingAmmount = 2;
        final Timer t = f.createTimerForBeatTheTimerMode(startingAmmount);
        assertFalse(t.isRunning());
        assertEquals(startingAmmount * TO_SECONDS, t.getValue());
        assertEquals(Verse.DOWN.getLimit(), t.getLimit());

        t.start();
        assertTrue(t.isRunning());

        t.stop();
        assertFalse(t.isRunning());

        t.start();
        assertTrue(t.isRunning());

        while (t.getValue() != 0) {
            assertTrue(t.getValue() <= startingAmmount * TO_SECONDS);
        }

        // timer should not go beyond its limit
        assertEquals(0, t.getValue());
        assertFalse(t.isRunning());
    }

    @Test
    public void doubleTimerTest() {
        final DoubleTimer dt = f.createTimersFor1vs1Mode();
        assertEquals(0, dt.getValue());
        assertFalse(dt.isRunning());

        dt.start();
        assertTrue(dt.isRunning());

        while (dt.getValue() < 10) {

            while (dt.getValue() == 0) {
                assertTrue(dt.isRunning());
            }
            assertTrue(dt.getPlayer1Timer().isRunning());
            assertFalse(dt.getPlayer2Timer().isRunning());
            assertTrue(dt.getValue() > 0);
        }

        assertTrue(0 < dt.getValue());

        dt.switchTurn();

        assertTrue(dt.getPlayer2Timer().isRunning());
        assertFalse(dt.getPlayer1Timer().isRunning());

        dt.stop();
        assertFalse(dt.getPlayer1Timer().isRunning());
        assertFalse(dt.getPlayer2Timer().isRunning());

        assertEquals(0, dt.getValue());
        assertFalse(dt.isRunning());
    }
}

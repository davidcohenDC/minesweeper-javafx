package timer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * A Class to test {@link Timer} functionalities are working.
 */
class TestTimer {

    /**
     * The maximum amount of seconds a Timer can run at a time.
     * <p>
     * <strong>BEWARE THIS TEST COULD TAKE UP TO<i><font color="red"> MAX_SLEEP_TIME
     * * 6 </font></i>SECONDS TO EXECUTE<br>
     * SO THE HIGHER THIS NUMBER GETS THE MORE TIME IT WILL TAKE TO FINISH THE
     * TEST.</strong>
     */
    private static final int MAX_SLEEP_TIME = 3;

    private final TimerFactory f = new TimerFactoryImpl();
    private final Random rnd = new Random();

    @Test
    public void standardTimerTest() {
        int goodtests = 0;
        int badtests = 0;
        for (int i = 0; i < 10; i++) {
        final Timer t = f.createTimerForStandardMode();
        final int time = 10;
        int c = 0;
//        System.out.println("time = " + time);

        // timer starts running

            // after a random time the timer gets paused
            t.startTimer();
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 11) {
                c++;
                try {
                    Thread.sleep(0, 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(c);
            }
            int tvalue = t.getValue();
////            System.out.println(t.getValue());
//            System.out.println("Test " + i + " actual " + time + " timer " + t.getValue() + " " + (time == t.getValue()));
            System.out.println(tvalue);
            if (c == tvalue) {
                goodtests++;
            } else {
                badtests++;
            }
//            assertEquals(time, t.getValue());

            t.stopTimer();



        }
        System.out.println();
        System.out.println();
        System.out.println("good " + goodtests + " bad " + badtests);
    }

//    assertTrue(t.isPaused());
//
//    // timer starts running
//    t.startTimer();
////    assertFalse(t.isPaused());
//
//    try {
//        // after a random time the timer gets paused
//        Thread.sleep(1 * time);
//        System.out.println("actual " + time + " timer = " + t.getValue() + (time == t.getValue()));
//
//        t.pause();
//        assertTrue(t.isPaused());
////        assertEquals(time, t.getValue());
//
//        // then it starts again
//        t.play();
//        assertFalse(t.isPaused());
//        Thread.sleep(1 * time);
//        // until it gets stopped
//        t.stopTimer();
//        assertTrue(t.isPaused());
//
//        // play should not reactivate the timer after it was stopped
//        t.play();
//        assertTrue(t.isPaused());
////        assertEquals(time * 2, t.getValue());
//
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//        fail();
//    }
//    }
//    @Test
//    public void beatTheTimerTest() {
//
//        final Timer t = f.createTimerForBeatTheTimerMode(MAX_SLEEP_TIME * 2);
//        final int time = rnd.nextInt(MAX_SLEEP_TIME);
//
//        // timer has not started so it should be on hold
//        assertTrue(t.isPaused());
//
//        // timer starts running
//        t.startTimer();
//        assertFalse(t.isPaused());
//
//        try {
//            // after a random time the timer gets paused
//            Thread.sleep(1_000 * time);
//            t.pause();
//            assertTrue(t.isPaused());
//            assertEquals(time, t.getValue());
//
//            // then it starts again
//            t.play();
//            assertFalse(t.isPaused());
//            Thread.sleep(1_000 * time);
//
//            // until it gets stopped
//            t.stopTimer();
//            assertTrue(t.isPaused());
//
//            // play should not reactivate the timer after it was stopped
//            t.play();
//            assertTrue(t.isPaused());
//            assertEquals(0, t.getValue());
//
//            // the timer's value should be decreased
//            if (time == 0) {
//                assertEquals(time, t.getValue());
//            } else {
//                assertTrue(t.getValue() < time);
//            }
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            fail();
//        }
//    }
//
//    @Test
//    public void doubleTimerTest() {
//        final DoubleTimer dt = f.createTimersFor1vs1Mode();
//        final int time = rnd.nextInt(MAX_SLEEP_TIME);
//
//        // timer has not started so it should be on hold
//        assertTrue(dt.getPlayer1Timer().isPaused());
//        assertTrue(dt.getPlayer2Timer().isPaused());
//
//        // player 1 should go first while player 2 is on hold
//        dt.startTimers();
//        assertFalse(dt.getPlayer1Timer().isPaused());
//        assertTrue(dt.getPlayer2Timer().isPaused());
//
//        try {
//            // after a random time the player switch turns
//            Thread.sleep(1_000 * time);
//            dt.switchTurn();
//            assertTrue(dt.getPlayer1Timer().isPaused());
//            assertFalse(dt.getPlayer2Timer().isPaused());
//            assertEquals(0, dt.getPlayer2Timer().getValue());
//            assertEquals(time, dt.getPlayer1Timer().getValue());
//
//            // then it starts again
//            Thread.sleep(1_000 * time);
//            dt.switchTurn();
//            assertTrue(dt.getPlayer2Timer().isPaused());
//            assertFalse(dt.getPlayer1Timer().isPaused());
//            assertEquals(time, dt.getPlayer2Timer().getValue());
//            assertEquals(time, dt.getPlayer1Timer().getValue());
//
//            // both timers get stopped
//            dt.stopTimers();
//            assertTrue(dt.getPlayer1Timer().isPaused());
//            assertTrue(dt.getPlayer2Timer().isPaused());
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            fail();
//        }
//
//    }
}

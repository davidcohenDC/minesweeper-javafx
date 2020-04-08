package scoresystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import controlutility.Difficulty;
import controlutility.Modality;
import gamelogics.GameStatus;

/**
 * A Test to see if {@link Player} functionalities are working.
 */
class TestPlayer {

    private final PlayerFactory f = new PlayerFactoryImpl();
    private final Player p = f.createPlayerForStandardMode("luigi", Difficulty.MEDIUM);

    @Test
    public void playerTest() {
        System.out.println("playerTest");

        assertEquals("luigi", p.getName());
        assertEquals(Difficulty.MEDIUM, p.getDifficuly());
        assertEquals(Modality.STANDARD, p.getModality());

        // player has not won or lost yet so the result must not be available
        try {
            assertEquals(Optional.empty(), Optional.of(p.getResult()));
            fail("TRYING TO ACCESS WHEN FORBIDDEN");
        } catch (IllegalStateException e) {
            System.out.println(e);
            assertEquals(IllegalStateException.class, e.getClass());
        }

        // player has not won or lost yet so the score is not been achived yet
        try {
            assertEquals(Optional.empty(), Optional.of(p.getScore()));
            fail("NO SCORE TO BE ACCESSED");
        } catch (IllegalStateException e) {
            System.out.println(e);
            assertEquals(IllegalStateException.class, e.getClass());
        }
        System.out.println();
    }

    @Test
    public void lostTest() {
        System.out.println("lostTest");

        // player loses
        p.lost();
        assertEquals(GameStatus.LOST, p.getResult());

        // player lost so no score needs to be accessed
        try {
            assertEquals(Optional.empty(), Optional.of(p.getScore()));
            fail("NO SCORE TO BE ACCESSED");
        } catch (IllegalStateException e) {
            System.out.println(e);
            assertEquals(IllegalStateException.class, e.getClass());
        }

        // player shouldn't be able to change its status after winning or losing
        try {
            p.won(3);
            fail("CANNOT WIN AFTER LOSING");
        } catch (IllegalStateException e) {
            System.out.println(e);
            assertNotEquals(GameStatus.WON, p.getResult());
            assertEquals(IllegalStateException.class, e.getClass());
        }

        try {
            assertEquals(Optional.empty(), Optional.of(p.getScore()));
            fail();
        } catch (IllegalStateException e) {
            System.out.println(e);
        }

        // Personalized difficulty players' scores should not be tracked
        final Player p2 = f.createPlayerForStandardMode("rossi", Difficulty.PERSONALIZED);
        p2.won(8);
        try {
            assertEquals(Optional.empty(), Optional.of(p.getScore()));
            fail();
        } catch (IllegalStateException e) {
            System.out.println(e);
        }
        System.out.println();
    }

    @Test
    public void wonTest() {
        System.out.println("wonTest");

        // player wins the game in 8 seconds
        p.won(8);
        assertEquals(GameStatus.WON, p.getResult());
        assertEquals(8, p.getScore());

        // player shouldn't be able to change its status after winning or losing
        try {
            p.lost();
            fail("CANNOT LOSE AFTER WINNING");
        } catch (IllegalStateException e) {
            System.out.println(e);
            assertEquals(8, p.getScore());
            assertEquals(IllegalStateException.class, e.getClass());
        }

        // player shouldn't be able to change its status after winning or losing
        try {
            p.won(3);
            fail("CANNOT WIN TWICE");
        } catch (IllegalStateException e) {
            System.out.println(e);
            assertNotEquals(3, p.getScore());
            assertEquals(IllegalStateException.class, e.getClass());
        }

        System.out.println();

    }

}

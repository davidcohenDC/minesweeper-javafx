package scoresystem;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import controlutility.Difficulty;
import controlutility.Modality;
import gameLogics.GameStatus;

class TestScoreSystem {

    private static final String FILE_EXTENCION = ".txt";
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String ROOT = System.getProperty("user.home") + FILE_SEPARATOR + ".minesweeper" + FILE_SEPARATOR + "score_files" + FILE_SEPARATOR;

    private ScoreWriter sw;
    private String filename;

//TEST for PLAYER FUNCTIONALITIES
    @Test
    void playerGettersTest() {
        Player p = new PlayerImpl("luigi", Modality.STANDARD, Difficulty.MEDIUM);

        assertEquals("luigi", p.getName());
        assertEquals(Difficulty.MEDIUM, p.getDifficuly()); 
        assertEquals(Modality.STANDARD, p.getModality());

        //player has not won or lost yet so the result must not be available
        try {
            assertEquals(Optional.empty(), Optional.of(p.getResult()));
            fail("TRYING TO ACCESS WHEN FORBIDDEN");
        } catch (IllegalStateException e) {
            System.out.println(e);
            assertEquals(IllegalStateException.class, e.getClass());
        }

        //player has not won or lost yet so the score is not been achived yet
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
    void lostTest() {
        Player p = new PlayerImpl("luigi", Modality.STANDARD, Difficulty.MEDIUM);

        //player loses
        p.lost();
        assertEquals(GameStatus.LOSE, p.getResult());

        //player lost so no score needs to be accessed
        try {
            assertEquals(Optional.empty(), Optional.of(p.getScore()));
            fail("NO SCORE TO BE ACCESSED");
        } catch (IllegalStateException e) {
            System.out.println(e);
            assertEquals(IllegalStateException.class, e.getClass());
        }

        //player shouldn't be able to change its status after winning or losing
        try {
            p.won(3);
            fail("CANNOT WIN AFTER LOSING");
        } catch (IllegalStateException e) {
            System.out.println(e);
            assertNotEquals(GameStatus.WIN, p.getResult());
            assertEquals(IllegalStateException.class, e.getClass());
        }

        try {
            assertEquals(Optional.empty(), Optional.of(p.getScore()));
            fail();
        } catch (IllegalStateException e) {
            System.out.println(e);
        }
        System.out.println();
    }

    @Test
    void wonTest() {
        Player p = new PlayerImpl("luigi", Modality.STANDARD, Difficulty.EASY);

        //player wins the game in 8 seconds
        p.won(8);
        assertEquals(GameStatus.WIN, p.getResult());
        assertEquals(8, p.getScore());

        //player shouldn't be able to change its status after winning or losing
        try {
            p.lost();
            fail("CANNOT LOSE AFTER WINNING");
        } catch (IllegalStateException e) {
            System.out.println(e);
            assertEquals(8, p.getScore());
            assertEquals(IllegalStateException.class, e.getClass());
        }

        //player shouldn't be able to change its status after winning or losing
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

//TEST for SCORE WRITING FUNCTIONALITIES
    @Test
    void scoreFileDoesNotExistTest() {
        filename = ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.EASY.getName() + FILE_EXTENCION; 
        Player p = new PlayerImpl("luigi", Modality.STANDARD, Difficulty.EASY);

        try {
            Files.deleteIfExists(Path.of(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //the file we are looking for does not exist
        assertTrue(Files.notExists(Path.of(filename)));

        //by initializing the score writer the file should be created if not existent
        sw = new ScoreWriterImpl(p);
        assertTrue(Files.exists(Path.of(filename)));

        //file should not be created if player is playing personalized modality
        Player personalized = new PlayerImpl("personalized", Modality.STANDARD, Difficulty.PERSONALIZED);
        filename = ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.PERSONALIZED.getName() + FILE_EXTENCION; 

        assertTrue(Files.notExists(Path.of(filename)));
        sw = new ScoreWriterImpl(personalized);
        assertFalse(Files.exists(Path.of(filename)));
    }
    

}

package scoresystem;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
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
    private File file;

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
            assertEquals(IllegalStateException.class, e.getClass());
        }

        try {
            assertEquals(Optional.empty(), Optional.of(p.getScore()));
            fail("NO SCORE TO BE ACCESSED");
        } catch (IllegalStateException e) {
            assertEquals(IllegalStateException.class, e.getClass());
        }

        p.lost();
        assertEquals(GameStatus.LOSE, p.getResult());

        //if player lost there is still no score to be taken care of
        try {
            assertEquals(Optional.empty(), Optional.of(p.getScore()));
            fail("NO SCORE TO BE ACCESSED");
        } catch (IllegalStateException e) {
            assertEquals(IllegalStateException.class, e.getClass());
        }
    }

    @Test
    void scoreFileDoesNotExistTest() {

         file = new File(ROOT + Modality.STANDARD + FILE_SEPARATOR + Difficulty.HARD + FILE_EXTENCION);

         if (file.exists()) {
            file.delete();
         }
         assertTrue(!file.exists());
    }

}

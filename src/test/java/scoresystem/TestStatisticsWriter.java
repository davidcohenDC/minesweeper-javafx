package scoresystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import controlutility.Difficulty;
import controlutility.Modality;

/**
 * Class to test {@link StatistcsWriter} functionalities.
 * <p>
 * <strong>BEWARE! RUNNING THIS CLASS WILL ALTER ACTUAL STATISTICS
 * FILES.</strong>
 */
class TestStatisticsWriter {
    private static final String FILE_EXTENCION = ".txt";
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String ROOT = System.getProperty("user.home") + FILE_SEPARATOR + ".minesweeper" + FILE_SEPARATOR
            + "score_files" + FILE_SEPARATOR;

    private static final String PLAYER_NAME = "loser";

    private final PlayerFactory f = new PlayerFactoryImpl();
    private final ScoreWriter sw = new ScoreWriterImpl();
    private final Path path = Path
            .of(ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + "Statistics" + FILE_EXTENCION);

    @Test
    public void singlePlayerWritingTest() {
        Player p;
        // if a player looses it should be put in the statistics file
        p = f.createPlayerForStandardMode(PLAYER_NAME, Difficulty.EASY);

        try {
            Files.deleteIfExists(path);
            Files.deleteIfExists(Path.of(
                    ROOT + p.getModality().getDirectoryName() + FILE_SEPARATOR + p.getDifficuly().getName() + FILE_EXTENCION));
        } catch (IOException e) {
            fail("File should have been deketed for next portion of the test");
        }

        p.lost();
        sw.write(p);

        // the score was not registered beacuse the player lost
        assertFalse(sw.getScoreBoard(p.getModality(), p.getDifficuly()).keySet().contains(p.getName()));
        // but the loss was registered
        assertTrue(Files.exists(path));

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            fail("File should have been deketed for next portion of the test");
        }

        // player gets created multiple times to simulate the same player playing
        // multiple times
        for (int i = 0; i < 2; i++) {
            p = f.createPlayerForStandardMode(PLAYER_NAME, Difficulty.EASY);
            p.lost(); // he loses 3 times
            sw.write(p);
        }
        for (int i = 0; i < 2; i++) {
            p = f.createPlayerForStandardMode(PLAYER_NAME, Difficulty.EASY);
            p.won(100); // wins 2 times
            sw.write(p);
        }
        p = f.createPlayerForStandardMode(PLAYER_NAME, Difficulty.EASY);
        p.lost(); // then loses again
        sw.write(p);

        assertEquals(4, new StatisticsWriterImpl().getLosses(PLAYER_NAME, p.getModality()));
        assertEquals(2, new StatisticsWriterImpl().getWins(PLAYER_NAME, p.getModality()));

    }
}

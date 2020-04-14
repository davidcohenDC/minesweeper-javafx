package scoresystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import controlutility.Difficulty;
import controlutility.Modality;

/**
 * Class to test {@link ScoreWriter} functionalities.
 * <p>
 * <strong>BEWARE! RUNNING THIS CLASS WILL ALTER ACTUAL SCORE FILES.</strong>
 */
class TestScoreWriting {

    private static final String FILE_EXTENCION = ".txt";
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String ROOT = System.getProperty("user.home") + FILE_SEPARATOR + ".minesweeper" + FILE_SEPARATOR
            + "score_files" + FILE_SEPARATOR;

    private final PlayerFactory f = new PlayerFactoryImpl();
    private final Random rnd = new Random();
    private ScoreWriter sw = new ScoreWriterImpl();
    private Path path;
    private Player p;

    @Test
    public void scoreFileDoesNotExistTest() {
        path = Path.of(ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.EASY.getName() + FILE_EXTENCION);
        p = f.createPlayerForStandardMode("luigi", Difficulty.EASY);

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            fail("File should have been deketed for next portion of the test");
        }

        // the file we are looking for does not exist
        assertTrue(Files.notExists(path));

        // by initializing the score writer the file should not be created if not
        // existent
        sw = new ScoreWriterImpl();
        assertTrue(Files.notExists(path));

        // file should not be created if player is playing personalized modality
        p = f.createPlayerForStandardMode("personalized", Difficulty.PERSONALIZED);
        path = Path.of(ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.PERSONALIZED.getName()
                + FILE_EXTENCION);

        assertTrue(Files.notExists(path));
        System.out.println();

    }

    @Test
    public void notWritableScoresTest() {
        // Personalized players' scores are not to be kept track of so nothing should
        // happen
        p = f.createPlayerForStandardMode("luigi", Difficulty.PERSONALIZED);
        p.won(8);
        path = Path.of(ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.PERSONALIZED.getName()
                + FILE_EXTENCION);
        assertTrue(Files.notExists(path));
        sw.write(p);
        assertTrue(Files.notExists(path));

        // if a Player lost, his score should not be written
        p = f.createPlayerForStandardMode("loser", Difficulty.EASY);
        path = Path.of(ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.EASY.getName() + FILE_EXTENCION);
        p.lost();

        // if file did not exist it should also not be created
        if (Files.exists(path)) {
            long oldSize;
            try {
                oldSize = Files.size(path);
                sw.write(p);
                assertTrue(Files.exists(path));

                // File's size should not have changed since nothing was written on it
                assertEquals(oldSize, Files.size(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void scoreFileIsInOrderTest() {
        path = Path
                .of(ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.MEDIUM.getName() + FILE_EXTENCION);

        // file MUST be empty in the begging of this test
        try {
            Files.deleteIfExists(path);
        } catch (IOException e1) {
            fail("FILE WAS NOT CANCELLED AT THE BEGGING OF THIS TEST SO IT MAKES THE REST USELESS");
        }

        final List<Integer> expectedScoreBoard = new ArrayList<>();
        int score;
        final int numberOfPlayers = 100;

        // generate numberOfPlayers players and gives them a random score
        for (int i = 0; i < numberOfPlayers; i++) {
            p = f.createPlayerForStandardMode("p" + i, Difficulty.MEDIUM);
            score = rnd.nextInt(1_000);
            expectedScoreBoard.add(score); // this keeps track of the scores being written
            p.won(score);
            sw.write(p);
        }

        // sorts the expected score board
        expectedScoreBoard.sort((score1, score2) -> score1 - score2);
        assertEquals(expectedScoreBoard, getLines(path));
    }

    @Test
    public void multiplayerScoreWritingTest() {
        path = Path
                .of(ROOT + Modality.ONE_VS_ONE.getDirectoryName() + FILE_SEPARATOR + Difficulty.HARD.getName() + FILE_EXTENCION);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            fail("File should have been deketed for next portion of the test");
        }

        // 2 players play against each other
        Player p1 = f.createPlayerFor1vs1Mode("winner", Difficulty.HARD, "loserAdversary");
        Player p2 = f.createPlayerFor1vs1Mode("loserAdversary", Difficulty.HARD, p1.getName());

        // game ends
        p1.won(100);
        p2.lost();

        // scores get written
        sw.write(p1);
        sw.write(p2);

        // file should have been written since p1 won
        assertTrue(Files.exists(path));

        final List<String> lines = new ArrayList<>();
        try {
            for (final Object line : Files.lines(path).toArray()) {
                lines.add(String.valueOf(line));
            }
        } catch (IOException e) {
            System.err.println("The lines from the file were not transfered correctly.");
            System.err.println(lines);
        }

        // p1 won so only one line should be present in the file
        assertEquals(1, lines.size());
        assertEquals(100, sw.getScoreBoard(p1.getModality(), p1.getDifficuly()).get(p1.getName()));

        // same 2 players play again
        p1 = f.createPlayerFor1vs1Mode("winner", Difficulty.HARD, "loser");
        p2 = f.createPlayerFor1vs1Mode("loserAdversary", Difficulty.HARD, p1.getName());

        // game ends better than last time
        p1.won(10);
        p2.lost();

        // scores get written
        sw.write(p1);
        sw.write(p2);

        lines.clear();
        try {
            for (final Object line : Files.lines(path).toArray()) {
                lines.add(String.valueOf(line));
            }
        } catch (IOException e) {
            System.err.println("The lines from the file were not transfered correctly.");
            System.err.println(lines);
        }

        // p1 won so only one line should be present in the file
        assertEquals(1, lines.size());
        assertEquals(10, sw.getScoreBoard(p1.getModality(), p1.getDifficuly()).get(p1.getName()));
    }

    /**
     * Gets the lines from the file and puts the scores without changing the order.
     * 
     * @param path
     *                 The Path of the file.
     * @return Return a List of scores.
     */
    private Collection<? extends Integer> getLines(final Path path) {
        final List<Integer> actualScoreBoard = new ArrayList<>();
        try {
            for (final Object line : Files.lines(path).toArray()) {
                final String string = String.valueOf(line);
                actualScoreBoard.add(Integer.valueOf(string.split("-")[1]));
            }
        } catch (IOException e) {
            System.err.println("The lines from the file were not transfered correctly.");
            System.err.println(actualScoreBoard);
        }
        return actualScoreBoard;
    }
}

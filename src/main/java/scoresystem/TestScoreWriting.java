package scoresystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import controlutility.Difficulty;
import controlutility.Modality;

/**
 * Class to test score writing functionalities. 
 *
 * BEWARE! RUNNING THIS CLASS WILL ALTER ACTUAL SCOREFILES
 */
class TestScoreWriting {

    private static final String FILE_EXTENCION = ".txt";
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String ROOT = System.getProperty("user.home") + FILE_SEPARATOR + ".minesweeper" + FILE_SEPARATOR + "score_files" + FILE_SEPARATOR;

    private ScoreWriter sw = new ScoreWriterImpl();
    private Path path;
    private Player p;
    private Random rnd = new Random();

    @Test
    void scoreFileDoesNotExistTest() {
        path =  Path.of(ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.EASY.getName() + FILE_EXTENCION); 
        p = new PlayerImpl("luigi", Modality.STANDARD, Difficulty.EASY);

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            fail("File should have been deketed for next portion of the test");
        }

        //the file we are looking for does not exist
        assertTrue(Files.notExists(path));

        //by initializing the score writer the file should not be created if not existent
        sw = new ScoreWriterImpl();
        assertTrue(Files.notExists(path));

        //file should not be created if player is playing personalized modality
        p = new PlayerImpl("personalized", Modality.STANDARD, Difficulty.PERSONALIZED);
        path =  Path.of(ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.PERSONALIZED.getName() + FILE_EXTENCION); 

        assertTrue(Files.notExists(path));
        System.out.println();

    }

    @Test
    void notWritableScoresTest() {
        System.out.println("notWritableScores");

        //Personalized players' scores are not to be kept track of so nothing should happen
        p = new PlayerImpl("luigi", Modality.STANDARD, Difficulty.PERSONALIZED);
        p.won(8);
        path = Path.of(ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.PERSONALIZED.getName() + FILE_EXTENCION);
        assertTrue(Files.notExists(path));
        sw.write(p);
        assertTrue(Files.notExists(path));

        //if a Player lost, his score should not be written
        p = new PlayerImpl("loser", Modality.STANDARD, Difficulty.EASY);
        path = Path.of(ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.EASY.getName() + FILE_EXTENCION);
        p.lost();

        //if file did not exist it should also not be created
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
        System.out.println("-------");
    }


    @Test
    void scoreFileIsInOrderTest() {
        path = Path.of(ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.MEDIUM.getName() + FILE_EXTENCION);

        //file MUST be empty in the begging of this test
        try {
            Files.deleteIfExists(path);
        } catch (IOException e1) {
            fail("FILE WAS NOT CANCELLED AT THE BEGGING OF THIS TEST SO IT MAKES THE REST USELESS");
        }

        List<Integer> expectedScoreBoard = new ArrayList<Integer>();
        int score;
        int numberOfPlayers = 100;

        //generate numberOfPlayers players and gives them a random score
        for (int i = 0; i < numberOfPlayers; i++) {
            p = new PlayerImpl("p" + i, Modality.STANDARD, Difficulty.MEDIUM);
            score = rnd.nextInt(1_000);
            expectedScoreBoard.add(score); //this keeps track of the scores being written
            p.won(score);
            sw.write(p);
        }

        //sorts the expected score board
        expectedScoreBoard.sort(new Comparator<Integer>() {
            @Override
            public int compare(final Integer score1, final Integer score2) {
                return score1 - score2;
            }
        });
        assertEquals(expectedScoreBoard, getLines(path));
    }

    //gets the lines from the file and puts the scores without changing the order in actualScoreBoard
    private Collection<? extends Integer> getLines(final Path path) {
        List<Integer> actualScoreBoard = new ArrayList<Integer>();
        try {
            for (Object line : Files.lines(path).toArray()) {
                String string = String.valueOf(line);
                actualScoreBoard.add(Integer.valueOf(string.split("-")[1]));
            }
        } catch (IOException e) {
                System.err.println("The lines from the file were not transfered correctly.");
                System.err.println(actualScoreBoard);
            }
        return actualScoreBoard;
    }
}

package scoresystem;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import controlutility.Difficulty;
import controlutility.Modality;

class TestScoreWriting {

    private static final String FILE_EXTENCION = ".txt";
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String ROOT = System.getProperty("user.home") + FILE_SEPARATOR + ".minesweeper" + FILE_SEPARATOR + "score_files" + FILE_SEPARATOR;

    private ScoreWriter sw;
    private String filename;
    private Player p;
    @Test
    void scoreFileDoesNotExistTest() {
        System.out.println("scoreFileDoesNotExistTest");

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
        System.out.println();

    }

    @Test
    void notWritableScores() {
        System.out.println("notWritableScores");

        //Personalized players' scores are not to be kept track of so nothing should happen
        Player p = new PlayerImpl("luigi", Modality.STANDARD, Difficulty.PERSONALIZED);
        p.won(8);
        sw = new ScoreWriterImpl(p);
        assertTrue(Files.notExists(Path.of(ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.PERSONALIZED.getName() + FILE_EXTENCION)));
        sw.writeScore();
        assertTrue(Files.notExists(Path.of(ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.PERSONALIZED.getName() + FILE_EXTENCION)));

        //if a Player lost, his score should not be written
        p = new PlayerImpl("loser", Modality.STANDARD, Difficulty.EASY);
        filename = ROOT + Modality.STANDARD.getDirectoryName() + FILE_SEPARATOR + Difficulty.EASY.getName() + FILE_EXTENCION;
        p.lost();
        sw = new ScoreWriterImpl(p);
        assertTrue(Files.exists(Path.of(filename)));

        long oldSize;
        try {
           oldSize = Files.size(Path.of(filename));
           sw.writeScore();
           // File's size should not have changed since nothing was written on it 
           assertEquals(oldSize, Files.size(Path.of(filename)));
        } catch (IOException e) {
                e.printStackTrace();
        }
        System.out.println();
    }

    @Test
    void writeScoreForSinglePlayerModality() {
        Player p = new PlayerImpl("luigi", Modality.BTT, Difficulty.MEDIUM);
        filename = ROOT + p.getModality().getDirectoryName() + FILE_SEPARATOR + p.getDifficuly().getName() + FILE_EXTENCION;
        //file must be empty in the begging
        try {
            Files.deleteIfExists(Path.of(filename));
        } catch (IOException e1) {
            fail("FILE WAS NOT CANCELLED AT THE BEGGING OF THIS TEST SO IT MAKES THE REST USELESS");
        }
        sw = new ScoreWriterImpl(p);
        p.won(23);
        sw.writeScore();

        assertTrue(Files.exists(Path.of(filename)));
        try {
            //if file's size is 0 it means nnothing was written
            assertFalse(Files.size(Path.of(filename)) == 0L);
        } catch (IOException e) {
            //it means the file was not written it the correct way
            fail();
        }
    }

}

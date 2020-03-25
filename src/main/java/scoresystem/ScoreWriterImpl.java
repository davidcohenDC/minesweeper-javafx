package scoresystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Streams;

import controlutility.Difficulty;


public class ScoreWriterImpl implements ScoreWriter {

    //separates the player id from his score
    private static final String PLAYER_SEPARATOR = ":";
    //separates each score form each other based on the difficulty 
    private static final String DIFFICULTY_SEPARATOR = "-"; 

    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String ROOT = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "score_files" + SEPARATOR;

    private final Player player;
    private final int previousHighScore;
    private final File scoreFile;
    private final Set<String> fileLines;

    /**
     * Sets up the score writing process.
     * @param player
     * The player to register the score
     */
    protected ScoreWriterImpl(final Player player) {
        this.player = player;
        this.scoreFile = new File(ROOT + player.getModality().getFileName());
        if (!scoreFile.exists()) {
            try {
                createNewScoreFile();
            } catch (IOException e) {
                System.err.println("Could not create new file");
            }
        }

        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream fis = loader.getResourceAsStream(this.player.getModality().getFileName());
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        this.fileLines = new HashSet<String>(br.lines().collect(Collectors.toList()));

        this.previousHighScore = 0;
    }

    private void createNewScoreFile() throws IOException {
        scoreFile.createNewFile();
    }

    @Override
    public final void writeScore() {
        if (scoreIsWritable()) {

        }
    }

    private boolean scoreIsWritable() {
        /*
         * This try/catch will check if anything that is not supposed to be written tries to be written 
         * or if the setup is not right
         */
        try {
//          check(this.player.getResult().equals(...));
            check(this.player.getDifficuly().equals(Difficulty.PERSONALIZED));
            check(this.previousHighScore < this.player.getScore());
        } catch (IllegalStateException e) {
            return false;
        }
        return true;
    }

    /**
     * The method checks if an expression is correct.
     * If the expression is true it will throw an IllegalStateExeption.
     * @param expression
     * The expression too check
     */
    private void check(final boolean expression) {
        if (expression) {
           throw new IllegalStateException(); 
        }
    }
}

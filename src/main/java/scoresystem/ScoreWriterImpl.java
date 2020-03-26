package scoresystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import controlutility.Difficulty;
import controlutility.Modality;
import gameLogics.GameStatus;

public class ScoreWriterImpl implements ScoreWriter {

    private static final String SCORE_SEPARATOR = "-";
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String FILE_EXTENCION = ".txt";
    private static final String ROOT = System.getProperty("user.home") + FILE_SEPARATOR + ".minesweeper" + FILE_SEPARATOR + "score_files" + FILE_SEPARATOR;

    private final Path path;
    private final List<String> lines = new ArrayList<String>();
    private final Map<String, Integer> scoreboard = new HashMap<String, Integer>();

    private final Player player;
    private Optional<Integer> previousHighScore = Optional.empty();

    /**
     * Sets up the score writing process.
     * @param player
     * The player to register the score
     */
    public ScoreWriterImpl(final Player player) {

        this.player = player;
        // "ROOT/MODE/Diff.txt"
        this.path = Path.of(ROOT + this.player.getModality().getDirectoryName() + FILE_SEPARATOR + this.player.getDifficuly().getName() + FILE_EXTENCION);

        if (!player.getDifficuly().equals(Difficulty.PERSONALIZED)) {

            if (Files.notExists(this.path)) {
                 try {
                     Files.createFile(this.path);
                 } catch (IOException e) {
                     System.err.println("Could not create new file.");
                 }
            }

            try {
                for (Object line : Files.lines(this.path).toArray()) {
                    this.lines.add(String.valueOf(line));
                }
            } catch (IOException e) {
                    System.err.println("The lines from the file were not transfered correctly.");
                    System.err.println(this.lines);
            }
        }
    }

    @Override
    public final void writeScore() {

        //mapping of the file lines
        for (String line: this.lines) {
            List<String> entry = List.of(line.split(SCORE_SEPARATOR));
            this.scoreboard.put(entry.get(0), Integer.valueOf(entry.get(entry.size() - 1)));
        }

        //writes a player lost and won games
        writePlayerStatistics(); 

        //if player already played with this settings this if fetches its old high score
        if (this.scoreboard.containsKey(this.player.getName())) {
            this.previousHighScore = Optional.of(this.scoreboard.get(player.getName()));
        }

        if (scoreIsWritable()) {
            if (this.player.getModality().equals(Modality.ONE_VS_ONE)) {
                writeScoreForMultiplayer();
            } else {
                writeScoreForSingleplayer();
            }
        }
    }

    private void writePlayerStatistics() {
        // TODO Auto-generated method stub
    }

    private void writeScoreForMultiplayer() {
        // TODO Auto-generated method stub
    }

    private void writeScoreForSingleplayer() {

        //if the player already played it replaces its previous score otherwise put a new entry in the score board map
        if (!this.scoreboard.containsKey(this.player.getName())) {
            this.scoreboard.put(this.player.getName(), this.player.getScore());
        } else {
            this.scoreboard.replace(this.player.getName(), this.player.getScore());
        }

        //writes to file after converting the score board entries to strings
        for (String playerName: this.scoreboard.keySet()) {
            this.lines.add(playerName + SCORE_SEPARATOR + this.scoreboard.get(playerName));
        }

        //sorts the map
        this.lines.sort(new Comparator<String>() {
            @Override
            public int compare(final String playerA, final String playerB) {
                return Integer.valueOf(playerA.split(SCORE_SEPARATOR)[1]) - Integer.valueOf(playerB.split(SCORE_SEPARATOR)[1]);
            }
        });

        //actually writes the file
        try {
            Files.write(this.path, this.lines);
        } catch (IOException e) {
            System.err.println("File writing was unsuccessful");
        }
    }

    private boolean scoreIsWritable() {
        try {
            check(Optional.of(this.player.getResult()).isEmpty());
            check(this.player.getResult().equals(GameStatus.LOSE));
            check(this.player.getDifficuly().equals(Difficulty.PERSONALIZED));
            check(this.previousHighScore.isPresent() && this.player.getScore() > this.previousHighScore.get());
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
           throw new IllegalStateException("File is NOT writable in this conditions"); 
        }
    }
}

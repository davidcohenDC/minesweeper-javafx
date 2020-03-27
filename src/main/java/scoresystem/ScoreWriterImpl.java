package scoresystem;

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

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String FILE_EXTENCION = ".txt";
    private static final String ROOT = System.getProperty("user.home") + FILE_SEPARATOR + ".minesweeper" + FILE_SEPARATOR + "score_files" + FILE_SEPARATOR;

    private static final String SCORE_SEPARATOR = "-";

    private final Writer statisticsWriter;

    private final List<String> lines;
    private final Map<String, Integer> scoreboard; 

    private Player player;
    private Path path;
    private Optional<Integer> previousHighScore = Optional.empty();

    /**
     * Sets up the list and maps.
     */
    public ScoreWriterImpl() {
        this.lines = new ArrayList<String>();
        this.scoreboard = new HashMap<String, Integer>();
        this.statisticsWriter = new StatisticsWriterImpl();
    }

    @Override
    public final void write(final Player player) {

        this.player = player;
        // "ROOT/MODE/Difficulty.txt"
        this.path = Path.of(ROOT + this.player.getModality().getDirectoryName() + FILE_SEPARATOR + this.player.getDifficuly().getName() + FILE_EXTENCION);

        if (!player.getDifficuly().equals(Difficulty.PERSONALIZED)) {

            if (Files.notExists(this.path)) {
                 try {
                     Files.createFile(this.path);
                 } catch (IOException e) {
                     System.err.println("Could not create new file.");
                 }
            }
            this.lines.addAll(convertFileToList(this.path));

            //updates a player statistics using a different writer
            this.statisticsWriter.write(this.player);
        }

        if (scoreIsWritable()) {

            //mapping of the file lines
            this.scoreboard.putAll(getScoreBoard(this.player.getModality(), this.player.getDifficuly()));

            //if player already played with this settings this if fetches its old high score
            if (this.scoreboard.containsKey(this.player.getName())) {
                this.previousHighScore = Optional.of(this.scoreboard.get(player.getName()));
            }

            if (this.player.getModality().equals(Modality.ONE_VS_ONE)) {
                writeScoreForMultiplayer();
            } else {
                writeScoreForSingleplayer();
            }
        }
    }



    @Override
    public final Map<String, Integer> getScoreBoard(final Modality gameMode, final Difficulty difficulty) {
        final Map<String, Integer> scoreboard = new HashMap<String, Integer>();
        for (String line: convertFileToList(Path.of(ROOT + gameMode.getDirectoryName() + FILE_SEPARATOR + difficulty.getName() + FILE_EXTENCION))) {
            List<String> entry = List.of(line.split(SCORE_SEPARATOR));
            scoreboard.put(entry.get(0), Integer.valueOf(entry.get(entry.size() - 1)));
        }
        return scoreboard;
    }

    /**
     * Converts a file in a list of its lines.
     * @param path
     * path of the file to convert
     * @return
     * return a List of strings
     */
    private List<String> convertFileToList(final Path path) {
        final List<String> lines = new ArrayList<String>();
        try {
            for (Object line : Files.lines(path).toArray()) {
                if (String.valueOf(line).contains(SCORE_SEPARATOR)) { //this control should keep wrong format of lines out
                   lines.add(String.valueOf(line));
                }
            }
        } catch (IOException e) {
                System.err.println("The lines from the file were not transfered correctly.");
                System.err.println(lines);
        }
        return lines;
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

        //converting the score board entries to strings
        this.lines.removeAll(this.lines);
        for (String playerName: this.scoreboard.keySet()) {
            this.lines.add(playerName + SCORE_SEPARATOR + this.scoreboard.get(playerName));
        }

        //sorts the list of lines
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
            check(Optional.of(this.player.getResult()).isEmpty(), "Result is empty");
            check(this.player.getResult().equals(GameStatus.LOSE), "Player has lost");
            check(this.player.getDifficuly().equals(Difficulty.PERSONALIZED), "Scores for personalized difficulty must not be written");
            check(this.previousHighScore.isPresent() && this.player.getScore() > this.previousHighScore.get(), "Previous score was better");
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
    private void check(final boolean expression, final String errorMessage) {
        if (expression) {
           throw new IllegalStateException(errorMessage); 
        }
    }
}

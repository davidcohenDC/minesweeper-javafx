package scoresystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import controlutility.Difficulty;
import controlutility.Modality;
import gamelogics.GameStatus;

/**
 * The implementation of {@link ScoreWriter}.
 */
public class ScoreWriterImpl implements ScoreWriter {

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String FILE_EXTENCION = ".txt";
    private static final String ROOT = System.getProperty("user.home") + FILE_SEPARATOR + ".minesweeper" + FILE_SEPARATOR
            + "score_files" + FILE_SEPARATOR;

    private static final String SCORE_SEPARATOR = "-";
    private static final int POINTS_COLUMN = 1;
    private static final int ADVERSARY_COLUMN = 2;

    private final StatistcsWriter statisticsWriter;

    private final List<String> lines;
    private final Map<String, Long> scoreboard;
    private final Map<String, String> adversaries;

    private Player player;
    private Path path;
    private Optional<Long> previousHighScore = Optional.empty();

    /**
     * Sets up the necessary tools to write scores.
     */
    public ScoreWriterImpl() {
        this.lines = new ArrayList<>();
        this.scoreboard = new HashMap<>();
        this.adversaries = new HashMap<>();
        this.statisticsWriter = new StatisticsWriterImpl();
    }

    @Override
    public final void write(final Player player) {

        this.player = player;
        // "ROOT/MODE/Difficulty.txt"
        this.path = Path.of(ROOT + this.player.getModality().getDirectoryName() + FILE_SEPARATOR
                + this.player.getDifficuly().getName() + FILE_EXTENCION);

        if (!player.getDifficuly().equals(Difficulty.PERSONALIZED)) {

            if (Files.notExists(this.path)) {
                try {
                    Files.createFile(this.path);
                } catch (IOException e) {
                    System.err.println("Could not create new file.");
                }
            }
            this.lines.addAll(Converter.fileToList(path, SCORE_SEPARATOR));

            // updates a player statistics using a different writer
            this.statisticsWriter.write(this.player);
        }

        if (scoreIsWritable()) {

            // mapping of the file lines
            this.scoreboard.putAll(getScoreBoard(this.player.getModality(), this.player.getDifficuly()));

            // if player already played with this settings this if fetches its old high
            // score
            if (this.scoreboard.containsKey(this.player.getName())) {
                this.previousHighScore = Optional.of(this.scoreboard.get(player.getName()));
                this.scoreboard.replace(this.player.getName(), this.player.getScore());
            } else {
                this.scoreboard.put(this.player.getName(), this.player.getScore());
            }

            this.lines.clear();

            // converting the scoreboard in a list of lines
            this.lines.addAll(this.scoreboard.keySet().stream()
                                                      .map(playerName -> format(playerName))
                                                      .collect(Collectors.toList()));

            // sorts the list of lines
            this.lines.sort((playerA, playerB) -> Integer.parseInt(playerA.split(SCORE_SEPARATOR)[POINTS_COLUMN])
                    - Integer.parseInt(playerB.split(SCORE_SEPARATOR)[POINTS_COLUMN]));

            // actually writes the file
            try {
                Files.write(this.path, this.lines);
            } catch (IOException e) {
                System.err.println("File writing was unsuccessful");
            }
        }
    }

    @Override
    public final Map<String, Long> getScoreBoard(final Modality gameMode, final Difficulty difficulty) {
        this.path = Path.of(ROOT + gameMode.getDirectoryName() + FILE_SEPARATOR + difficulty.getName() + FILE_EXTENCION);
        final Map<String, Long> scoreboard = new HashMap<>();
        for (final String line : Converter.fileToList(this.path, SCORE_SEPARATOR)) {
            final List<String> entry = List.of(line.split(SCORE_SEPARATOR));
            scoreboard.put(entry.get(0), Long.valueOf(entry.get(POINTS_COLUMN)));
            if (gameMode.equals(Modality.ONE_VS_ONE)) {
                this.adversaries.put(entry.get(0), entry.get(ADVERSARY_COLUMN));
            }
        }
        return scoreboard;
    }

    /**
     * Creates the lines to put in the score file in the right format.
     * <p>
     * Singleplayer format: <i>player</i> - <i>score</i><br>
     * Multiplayer format: <i>winner</i> - <i>point of the winner</i> - <i>loser</i>
     * 
     * @param playerName
     *                       The name of the player which score needs to be written.
     * @return Returns a string in the format according to the modality.
     */
    private String format(final String playerName) {
        final String formattedLine = playerName + SCORE_SEPARATOR + this.scoreboard.get(playerName);
        if (this.player.getModality().equals(Modality.ONE_VS_ONE)) {
            return formattedLine + SCORE_SEPARATOR + this.adversaries.get(playerName);
        }
        return formattedLine;
    }

    /**
     * Controls if a score is suitable for writing on file.
     *
     * @return Returns true if the score that is trying to be written should be
     *         written, returns false if it should be discarded.
     */
    private boolean scoreIsWritable() {
        try {
            check(Optional.of(this.player.getResult()).isEmpty(), "Result is empty");
            check(this.player.getResult().equals(GameStatus.LOST), "Player has lost");
            check(this.player.getDifficuly().equals(Difficulty.PERSONALIZED),
                    "Scores for personalized difficulty must not be written");
            if (this.player.getModality().equals(Modality.BTT)) {
                check(this.previousHighScore.isPresent() && this.player.getScore() < this.previousHighScore.get(),
                        "Previous score was better");
            } else {
                check(this.previousHighScore.isPresent() && this.player.getScore() > this.previousHighScore.get(),
                        "Previous score was better");
            }
        } catch (IllegalStateException e) {
            return false;
        }
        return true;
    }

    /**
     * The method checks if an expression is correct.<br>
     * If the expression is true it will throw an <code>IllegalStateExeption</code>.
     *
     * @param expression
     *                         The <code>boolean</code> expression to check.
     * @param errorMessage
     *                         The message to show if the exception gets thrown.
     */
    private void check(final boolean expression, final String errorMessage) {
        if (expression) {
            throw new IllegalStateException(errorMessage);
        }
    }
}

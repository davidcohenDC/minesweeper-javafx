package scoresystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import controlutility.Difficulty;
import controlutility.Modality;

/**
 * The implementation of {@link StatisticsWriter}.
 */
public class StatisticsWriterImpl implements StatistcsWriter {

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String FILE_NAME = "Statistics";
    private static final String FILE_EXTENCION = ".txt";
    private static final String ROOT = System.getProperty("user.home") + FILE_SEPARATOR + ".minesweeper" + FILE_SEPARATOR
            + "score_files" + FILE_SEPARATOR;

    private static final String DATA_SEPARATOR = ":";
    private static final int NUMBER_OF_FIELDS = 2;
    private static final int WINS_COLUMN = 0;
    private static final int LOSSES_COLUMN = 1;

    private final List<String> lines;
    private final Map<String, List<Integer>> statistics;

    private Path path;
    private Player player;

    /**
     * Sets up the necessary tools to write statistics.
     */
    public StatisticsWriterImpl() {
        this.statistics = new HashMap<>();
        this.lines = new ArrayList<>();
    }

    @Override
    public final void write(final Player player) {
        this.player = player;
        this.path = Path.of(ROOT + this.player.getModality().getDirectoryName() + FILE_SEPARATOR + FILE_NAME + FILE_EXTENCION);

        if (!player.getDifficuly().equals(Difficulty.PERSONALIZED)) {

            if (Files.notExists(this.path)) {
                try {
                    Files.createFile(this.path);
                } catch (IOException e) {
                    System.err.println("Could not create new file.");
                }
            }
            // represent the file in a list of lines
            this.lines.addAll(Converter.fileToList(path, DATA_SEPARATOR));

            // map the file
            this.statistics.putAll(mapFileLines(this.path));

            // if file does not contain the player it initializes the other field as 0
            if (!this.statistics.containsKey(this.player.getName())) {
                this.statistics.put(this.player.getName(), Collections.nCopies(NUMBER_OF_FIELDS, 0));
            }

            // control that player has finished the game
            if (Optional.of(player.getResult()).isPresent()) {
                // Depending on players result it increases the field accordingly
                switch (player.getResult()) {
                case WON:
                    updateField(WINS_COLUMN);
                    break;
                case LOST:
                    updateField(LOSSES_COLUMN);
                    break;
                default:// if the player has a result differing from the ones above it will throw
                        // exception
                    throw new IllegalStateException("This player has no data to update");
                }
            }

            // converts data map back to strings
            this.lines.clear();
            for (final String playerName : this.statistics.keySet()) {
                String values = playerName;
                for (final Integer value : this.statistics.get(playerName)) {
                    values = values.concat(DATA_SEPARATOR + value);
                }
                this.lines.add(values);
            }

            // actual file writing
            try {
                Files.write(this.path, this.lines);
            } catch (IOException e) {
                System.err.println("File writing was unsuccessful");
            }
        }
    }

    @Override
    public final int getWins(final String playerName, final Modality gameMode) {
        return getColumn(playerName, gameMode, WINS_COLUMN);
    }

    @Override
    public final int getLosses(final String playerName, final Modality gameMode) {
        return getColumn(playerName, gameMode, LOSSES_COLUMN);
    }

    @Override
    public final int getAllWins(final Modality gameMode) {
        return getTotalOfColumns(gameMode, WINS_COLUMN);
    }

    @Override
    public final int getAllLosses(final Modality gameMode) {
        return getTotalOfColumns(gameMode, LOSSES_COLUMN);
    }

    /**
     * Gets the value from a column of a statistics file.
     * 
     * @param playerName
     *                       The name of the Player from which to take the value.
     * @param gameMode
     *                       The {@link Modality} in which to get the statistics
     *                       file.
     * @param column
     *                       The column from which to get a value.
     * @return The value of the chosen column.
     */
    private int getColumn(final String playerName, final Modality gameMode, final int column) {
        this.path = Path.of(ROOT + gameMode.getDirectoryName() + FILE_SEPARATOR + FILE_NAME + FILE_EXTENCION);

        if (!mapFileLines(this.path).containsKey(playerName)) {
            return 0;
        }
        return mapFileLines(this.path).get(playerName).get(column);
    }

    /**
     * Gets a total of the values from a column of a statistics file.
     * 
     * @param gameMode
     *                     The {@link Modality} in which to get the statistics file.
     * @param column
     *                     The column from which to get a value.
     * @return The total of all the values of the chosen column.
     */
    private int getTotalOfColumns(final Modality gameMode, final int column) {
        int field = 0;
        this.path = Path.of(ROOT + gameMode.getDirectoryName() + FILE_SEPARATOR + FILE_NAME + FILE_EXTENCION);
        if (Files.exists(this.path)) {
            for (final String playerName : mapFileLines(this.path).keySet()) {
                field = field + getColumn(playerName, gameMode, column);
            }
        }
        return field;
    }

    /**
     * Increases the value of a column by 1.
     * 
     * @param column
     *                   The column holding the value to update.
     */
    private void updateField(final int column) {
        final List<Integer> updatedField = new ArrayList<>();
        updatedField.addAll(this.statistics.get(this.player.getName()));
        updatedField.set(column, updatedField.get(column) + 1);
        this.statistics.replace(this.player.getName(), updatedField);
    }

    /**
     * Maps a file from the wanted format. Format: <i>player</i> : <i>value</i> :
     * <i>value</i> : <i>...</i>
     * 
     * @param path
     *                 The path of the statistics file to map.
     * @return Returns a Map<String, List<Integer>> where the key is the Player's
     *         name and the value are list of his statistics.
     */
    private Map<String, List<Integer>> mapFileLines(final Path path) {
        final Map<String, List<Integer>> map = new HashMap<>();
        for (final String line : Converter.fileToList(path, DATA_SEPARATOR)) {
            final List<String> entry = List.of(line.split(DATA_SEPARATOR));
            final List<Integer> data = new ArrayList<>();
            for (final String value : entry.subList(1, entry.size())) {
                data.add(Integer.valueOf(value));
            }
            map.put(entry.get(0), data);
        }
        return map;
    }
}

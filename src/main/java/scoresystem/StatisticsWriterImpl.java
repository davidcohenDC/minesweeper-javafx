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

public class StatisticsWriterImpl implements Writer {

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String FILE_EXTENCION = ".txt";
    private static final String ROOT = System.getProperty("user.home") + FILE_SEPARATOR + ".minesweeper" + FILE_SEPARATOR + "score_files" + FILE_SEPARATOR;

    private static final String DATA_SEPARATOR = ":";
    private static final int NUMBER_OF_FIELDS = 2;

    private final List<String> lines;
    private final Map<String, List<Integer>> statistics;

    private Path path;
    private Player player;

    public StatisticsWriterImpl() {
        this.statistics = new HashMap<String, List<Integer>>();
        this.lines = new ArrayList<String>();
    }

    @Override
    public final void write(final Player player) {
        this.player = player;
        this.path = Path.of(ROOT + this.player.getModality().getDirectoryName() + FILE_SEPARATOR + "Statistics" + FILE_EXTENCION);

        if (!player.getDifficuly().equals(Difficulty.PERSONALIZED)) {

            if (Files.notExists(this.path)) {
                 try {
                     Files.createFile(this.path);
                 } catch (IOException e) {
                     System.err.println("Could not create new file.");
                 }
            }
            //represent the file in a list of lines
            this.lines.addAll(convertFileToList(this.path));

            //map the file 
            for (String line: convertFileToList(this.path)) {
                List<String> entry = List.of(line.split(DATA_SEPARATOR));
                List<Integer> data = new ArrayList<Integer>(); 
                for (String value: entry.subList(1, entry.size() - 1)) {
                    data.add(Integer.valueOf(value));
                }
                this.statistics.put(entry.get(0), data);
            }

            //if file does not contain the player it initializes the other field as 0
            if (!this.statistics.containsKey(this.player.getName())) {
                this.statistics.put(this.player.getName(), Collections.nCopies(NUMBER_OF_FIELDS, 0));
            }

            //control that player has finished the game
            if (Optional.of(player.getResult()).isPresent()) {
                //Depending on players result it increases the field accordingly
                switch (player.getResult()) {
                case WIN:
                    this.statistics.get(player.getName()).set(0, this.statistics.get(player.getName()).get(0) + 1);
                    break;
                case LOSE:
                    this.statistics.get(player.getName()).set(1, this.statistics.get(player.getName()).get(1) + 1);
                    break;
                default://if the player has a result differing from the ones above it will throw exception
                    throw new IllegalStateException("This player has no data to update");
                }
            }

            //converts data map back to strings
            this.lines.removeAll(this.lines);
            for (String playerName: this.statistics.keySet()) {
                String values = new String();
                for (Integer value: this.statistics.get(playerName)) {
                    values = values + DATA_SEPARATOR + value;
                }
                this.lines.add(playerName + DATA_SEPARATOR + this.statistics.get(playerName));
            }

            //actual file writing
            try {
                Files.write(this.path, this.lines);
            } catch (IOException e) {
                System.err.println("File writing was unsuccessful");
            }
        }
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
                if (String.valueOf(line).contains(DATA_SEPARATOR)) { //this control should keep wrong format of lines out
                   lines.add(String.valueOf(line));
                }
            }
        } catch (IOException e) {
                System.err.println("The lines from the file were not transfered correctly.");
                System.err.println(lines);
        }
        return lines;
    }

}

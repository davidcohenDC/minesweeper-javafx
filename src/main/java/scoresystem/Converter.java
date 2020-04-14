package scoresystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A converter to easily convert lists and files.
 */
public final class Converter {

    /**
     * Empty private constructor.
     */
    private Converter() {
        /* no-operation */
    }

    /**
     * Converts a File in a List of its Lines.
     * @param path
     * The Path of the file to convert.
     * @param separator
     * The separator to make sure no line with the wrong format is included.
     * @return Returns a {@link List} of strings containing the each line of the file.
     */
    public static List<String> fileToList(final Path path, final String separator) {
        final List<String> lines = new ArrayList<>();
        try {
            List.of(Files.lines(path).toArray()).stream()
                                                .filter(line -> String.valueOf(line).contains(separator))
                                                .forEach(line -> lines.add(String.valueOf(line)));
        } catch (IOException e) {
            if (Files.exists(path)) {
                System.err.println("The lines from the file were not transfered correctly.");
                System.err.println(lines);
            }
        }
        return lines;
    }

}

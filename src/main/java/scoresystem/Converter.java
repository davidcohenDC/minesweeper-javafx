package scoresystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

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
     * Converts a File in a List of strings containing its Lines.
     * 
     * @param path
     *                      The Path of the file to convert.
     * @param separator
     *                      The separator to make sure no line with the wrong format
     *                      is included.
     * @return Returns a {@link List} of strings containing the each line of the
     *         file.<br>
     *         If the conversion goes wrong it will return an empty
     *         {@link List}.
     */
    public static List<String> fileToList(final Path path, final String separator) {
        try {
            return List.of(Files.lines(path).toArray()).stream()
                                                       .map(String::valueOf)
                                                       .filter(line -> line.contains(separator))
                                                       .collect(Collectors.toList());
        } catch (IOException e) {
            if (Files.exists(path)) {
                System.err.println("The lines from the file were not transfered correctly.");
            }
        }
        return List.of();
    }

}

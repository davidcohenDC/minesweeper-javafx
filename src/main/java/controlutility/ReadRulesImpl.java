package controlutility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * */
public class ReadRulesImpl implements ReadRules {
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final List<String> lines;

    /**
     * @exception IOException
     *                            if an I/O error occurs.
     */
    public ReadRulesImpl() throws IOException {
        final String fileName = "src" + SEPARATOR + "main" + SEPARATOR
                + "resources" + SEPARATOR + "file" + SEPARATOR
                + "rules.txt";
        this.lines = new ArrayList<>(Files.lines(Paths.get(fileName)).collect(Collectors.toList()));
    }

    @Override
    public final List<String> getAllLines() {
        return Collections.unmodifiableList(lines);
    }

}

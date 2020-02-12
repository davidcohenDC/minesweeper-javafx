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

    private final List<String> lines;
    private final String separator = System.getProperty("file.separator");

    /**
     * @exception IOException
     *                            if an I/O error occurs.
     */
    public ReadRulesImpl() throws IOException {
        final String fileName = "src" + this.separator + "main" + this.separator
                + "resources" + this.separator + "file" + this.separator
                + "rules.txt";
        this.lines = new ArrayList<>(Files.lines(Paths.get(fileName)).collect(Collectors.toList()));
    }

    @Override
    public final List<String> getAllLines() {
        return Collections.unmodifiableList(lines);
    }

}

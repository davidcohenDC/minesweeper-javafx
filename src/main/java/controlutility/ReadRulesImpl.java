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

    private List<String> lines = new ArrayList<>();

    /**
     * @exception IOException
     *                            if an I/O error occurs.
     */
    public ReadRulesImpl() throws IOException {
        final String fileName = "src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator")
                + "resources" + System.getProperty("file.separator") + "file" + System.getProperty("file.separator")
                + "rules.txt";
        this.lines = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
    }

    @Override
    public final List<String> getAllLines() {
        //System.out.println(lines);
        return Collections.unmodifiableList(lines);
    }

}

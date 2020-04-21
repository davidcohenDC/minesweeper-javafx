package controlutility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** *The implementation of {@link ReadRules}. */
public class ReadRulesImpl implements ReadRules {
    private final List<String> lines;

    /**
     * @exception IOException
     *                            if an I/O error occurs.
     */
    public ReadRulesImpl() throws IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream fis = loader.getResourceAsStream("rule/rules.txt");
                InputStreamReader isr = new InputStreamReader(fis,
                        StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr)) {
            this.lines = new ArrayList<>(br.lines().collect(Collectors.toList()));
        }
    }

    @Override
    public final List<String> getAllLines() {
        return Collections.unmodifiableList(lines);
    }

}

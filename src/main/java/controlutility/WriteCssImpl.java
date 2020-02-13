package controlutility;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * */

public class WriteCssImpl implements WriteCss {
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final String fileName;
    private final List<String> lines;
    private String oldCol1;
    private String oldCol2;

    /**
     * @param old1 is the old first color
     * @param old2 is the old second color
     * @exception IOException
     *                            if an I/O error occurs.
     */
    public WriteCssImpl(final String old1, final String old2) throws IOException {
        this.fileName = "src" + SEPARATOR + "main" + SEPARATOR + "resources"
                + SEPARATOR + "layouts" + SEPARATOR + "form.css";

        this.lines = new ArrayList<>(Files.lines(Paths.get(fileName)).collect(Collectors.toList()));
        this.oldCol1 = old1;
        this.oldCol2 = old2;
    }

    @Override
    public final void update(final String first, final String second) {
        final List<String> wkl = new ArrayList<>(lines);
        // sostituisco i colori
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(this.oldCol1) && lines.get(i).contains(this.oldCol2)) {
                String s = lines.get(i).replace(this.oldCol1, first);
                s = s.replace(this.oldCol2, second);
                wkl.remove(i);
                wkl.add(i, s);
            }
        }
        // riscrivo su file
        try (PrintStream ps = new PrintStream(fileName)) {
            for (final String s : wkl) {
                ps.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.oldCol1 = first;
        this.oldCol2 = second;
        this.lines.clear();
        this.lines.addAll(Collections.unmodifiableList(wkl));
    }


}

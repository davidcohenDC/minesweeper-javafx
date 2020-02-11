package controlutility;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * */

public class WriteCssImpl implements WriteCss {
    private final String fileName;
    private List<String> lines = new ArrayList<>();
    private String oldCol1;
    private String oldCol2;

    /**
     * @param old1 is the old first color
     * @param old2 is the old second color
     * @exception IOException
     *                            if an I/O error occurs.
     */
    public WriteCssImpl(final String old1, final String old2) throws IOException {
        this.fileName = "src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "resources"
                + System.getProperty("file.separator") + "layouts" + System.getProperty("file.separator") + "form.css";

        this.lines = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        this.oldCol1 = old1;
        this.oldCol2 = old2;
    }

    @Override
    public final void update(final String first, final String second) {
        List<String> wkl = new ArrayList<>(lines);
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
            for (String s : wkl) {
                ps.println(s);
            }
        } catch (IOException e) {
            throw new IllegalStateException();
        }

        this.oldCol1 = first;
        this.oldCol2 = second;
        this.lines = wkl;
    }


}

package controlutility;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** *The implementation of {@link RWSettings}. */
public class RWSettingsImpl implements RWSettings {
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final String fileName;
    private final List<String> lines;

    /**
     * @exception IOException
     *                            if an I/O error occurs.
     */
    public RWSettingsImpl() throws IOException {
        this.fileName = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "settings" + SEPARATOR
                + "settings.txt";
        this.lines = new ArrayList<>(Files.lines(Paths.get(fileName)).collect(Collectors.toList()));
    }


    @Override
    public final void setMines(final String mine) {
        this.lines.set(0, mine);
        this.save();
    }

    @Override
    public final void setFlags(final String flag) {
        this.lines.set(1, flag);
        this.save();
    }


    @Override
    public final void setSong(final String song) {
        this.lines.set(2, song);
        this.save();
    }

    @Override
    public final void setCss(final String css) {
        this.lines.set(3, css);
        this.save();
    }

    @Override
    public final String getMines() {
        return this.lines.get(0);
    }

    @Override
    public final String getFlags() {
        return this.lines.get(1);
    }

    @Override
    public final String getSong() {
        return this.lines.get(2);
    }

    @Override
    public final String getCss() {
        return this.lines.get(3);
    }

    @Override
    public final String getClick() {
        return this.lines.get(4);
    }

    /**Save changes in settings.txt.*/
    private void save() {
        try (PrintStream ps = new PrintStream(fileName)) {
            for (final String s : lines) {
                ps.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

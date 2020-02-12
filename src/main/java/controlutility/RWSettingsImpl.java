package controlutility;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/***/
public class RWSettingsImpl implements RWSettings {
    private final String fileName;
    private final List<String> lines;
    private final String separator = System.getProperty("file.separator");

    /**
     * @exception IOException
     *                            if an I/O error occurs.
     */
    public RWSettingsImpl() throws IOException {
        this.fileName = "src" + this.separator + "main" + this.separator + "resources" + this.separator + "file" + this.separator
                + "settings.txt";
        this.lines = new ArrayList<>(Files.lines(Paths.get(fileName)).collect(Collectors.toList()));

    }

    @Override
    public final void setFirstColor(final String color) {
        this.lines.set(2, color);
        this.save();
    }

    @Override
    public final void setSecondColor(final String color) {
        this.lines.set(3, color);
        this.save();
    }

    @Override
    public final void setSong(final String song) {
        this.lines.set(4, song);
        this.save();
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
    public final String getFirstColor() {
        return this.lines.get(2);
    }

    @Override
    public final String getSecondColor() {
        return this.lines.get(3);
    }

    @Override
    public final String getSong() {
        return this.lines.get(4);
    }

    @Override
    public final String getMines() {
        return this.lines.get(0);
    }

    @Override
    public final String getFlags() {
        return this.lines.get(1);
    }

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

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
    private List<String> lines = new ArrayList<>();

    /**
     * @exception IOException
     *                            if an I/O error occurs.
     */
    public RWSettingsImpl() throws IOException {
        this.fileName = "src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "resources"
                + System.getProperty("file.separator") + "file" + System.getProperty("file.separator") + "settings.txt";
        this.lines = Files.lines(Paths.get(fileName)).collect(Collectors.toList());

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
        String out = this.lines.get(2);
        return out;
    }

    @Override
    public final String getSecondColor() {
        String out = this.lines.get(3);
        return out;
    }

    @Override
    public final String getSong() {
        String out = this.lines.get(4);
        return out;
    }

    @Override
    public final String getMines() {
        String out = this.lines.get(0);
        return out;
    }

    @Override
    public final String getFlags() {
        String out = this.lines.get(1);
        return out;
    }

    private void save() {
        try (PrintStream ps = new PrintStream(fileName)) {
            for (String s : lines) {
                ps.println(s);
            }
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

}

package scoresystem;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StatisticsWriterImpl implements Writer {

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String FILE_EXTENCION = ".txt";
    private static final String ROOT = System.getProperty("user.home") + FILE_SEPARATOR + ".minesweeper" + FILE_SEPARATOR + "score_files" + FILE_SEPARATOR;

    private Path path;
    private final List<String> lines = new ArrayList<String>();
    private Player player;

    public StatisticsWriterImpl() {
    }

    @Override
    public final void write(final Player player) {
        this.player = player;
        this.path = Path.of(ROOT + this.player.getModality().getDirectoryName() + FILE_SEPARATOR + "Statistics" + FILE_EXTENCION);
    }

}

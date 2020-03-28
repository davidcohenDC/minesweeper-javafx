package controlutility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***/
public class LoadDataImpl implements LoadData {
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final ClassLoader loader = Thread.currentThread().getContextClassLoader();
    private final String root = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR;
    private final List<String> imgMines = new ArrayList<>(
            Arrays.asList("panda.png", "bomba.png", "fiore.png", "panDiZenzero.png"));
    private final List<String> imgFlags = new ArrayList<>(
            Arrays.asList("bianca.png", "rossa.png", "scacchi.png", "puntina_verde.png", "tovagliolo.png"));
    private final List<String> sound = new ArrayList<>(
            Arrays.asList("prova1.wav", "prova2.wav"));

    /**
     * @throws IOException
     * @throws URISyntaxException
     **/
    @Override
    public void loadData() throws IOException {
        // se esiste .minesweeper/ in user.home non fa nulla, altrimenti la crea e copia
        // dentro i file contenuti in resources
        final File file = new File(this.root);
        if (!file.exists() && file.mkdir()) {
            loadSettings();
            loadImage();
            loadSound();
            loadScoreSystem();
        }
    }

    private void loadImage() throws IOException {
        final String strImg = this.root + "image" + SEPARATOR;
        final String strMine = strImg + "mines" + SEPARATOR;
        final String strFlag = strImg + "flags" + SEPARATOR;
        final File image = new File(strImg);
        final File mine = new File(strMine);
        final File flag = new File(strFlag);
        if (!image.exists() && image.mkdir() && mine.mkdir() && flag.mkdir()) {
            for (final String s : this.imgMines) {
                try (InputStream fis = loader.getResourceAsStream("image/mines/" + s)) {
                    try {
                        Files.copy(fis, Paths.get(strMine + s), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (final String s : this.imgFlags) {
                try (InputStream fis = loader.getResourceAsStream("image/flags/" + s)) {
                    try {
                        Files.copy(fis, Paths.get(strFlag + s), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private void loadSettings() throws IOException {
        final String strSett = this.root + "settings" + SEPARATOR;
        final File file = new File(strSett);
        if (!file.exists() && file.mkdir()) {
            try (InputStream fis = loader.getResourceAsStream("settings/settings.txt")) {
                try {
                    Files.copy(fis, Paths.get(strSett + "settings.txt"), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadSound() throws IOException {
        final String strSound = this.root + "sound" + SEPARATOR;
        final File file = new File(strSound);
        if (!file.exists() && file.mkdir()) {
            for (final String s : this.sound) {
                try (InputStream fis = loader.getResourceAsStream("sound/" + s)) {
                    try {
                        Files.copy(fis, Paths.get(strSound + s), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    private void loadScoreSystem() {
        final String strScores = this.root + "score_files" + SEPARATOR;
        final File file = new File(strScores);
        if (!file.exists() && file.mkdir()) {
            for (final Modality gameMode: Modality.values()) {
                final File directory = new File(strScores + gameMode.getDirectoryName() + SEPARATOR);
                if (!directory.exists()) {
                    try {
                        Files.createDirectory(directory.toPath());
                    } catch (IOException e) {
                        System.err.println("Could not properly set up scoresystem directories");
                    }
                } 
            }
        }

    }

}

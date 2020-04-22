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

/** *The implementation of {@link LoadData}. */
public class LoadDataImpl implements LoadData {
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final ClassLoader loader = Thread.currentThread().getContextClassLoader();
    private final String root = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR;
    private final List<String> imgMines = new ArrayList<>(
            Arrays.asList("panda.png", "bomba.png", "fiore.png", "panDiZenzero.png"));
    private final List<String> imgFlags = new ArrayList<>(
            Arrays.asList("bianca.png", "rossa.png", "scacchi.png", "puntina_verde.png", "tovagliolo.png"));
    private final List<String> sound = new ArrayList<>(
            Arrays.asList("song01.wav", "song02.wav", "song03.wav", "song04.wav", "song05.wav", 
                    "song06.wav", "song07.wav", "song08.wav", "song09.wav", "song10.wav"));
    private final List<String> audioeffect = new ArrayList<>(
            Arrays.asList("addflag.wav", "click.wav", "firstclick.wav", "removeflag.wav"));

    /**
     * @throws IOException
     * @throws URISyntaxException
     **/
    @Override
    public void loadData() throws IOException {
        final File file = new File(this.root);
        if (!file.exists() && file.mkdir()) {
            loadSettings();
            loadImage();
            loadSound();
            loadScoreSystem();
            loadAudioEffect();
        }
    }

    /**Load audio effect into audioeffect folder.*/
    private void loadAudioEffect() throws IOException {
        final String strAudioEffect = this.root + "audioeffect" + SEPARATOR;
        final File file = new File(strAudioEffect);
        if (!file.exists() && file.mkdir()) {
            for (final String s : this.audioeffect) {
                try (InputStream fis = loader.getResourceAsStream("audioeffect/" + s)) {
                    try {
                        Files.copy(fis, Paths.get(strAudioEffect + s), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    /**Load Images into mines and flags.*/
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

    /**Load the settings file into settings folder.*/
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

    /**Load soundtracks  into sound folder.*/
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

    /**Create scoreSystems folder width a subfolder for each modality.*/
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

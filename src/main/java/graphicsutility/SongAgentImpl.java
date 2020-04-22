package graphicsutility;

import controlutility.RWSettings;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * The implementation of {@link SongAgent}.
 */
public class SongAgentImpl implements SongAgent {
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final String urlSound = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "sound"
            + SEPARATOR;
    private Clip clip;
    private Boolean playing = false;
    private Boolean checkStart = false;
    final RWSettings rwSett;

    public SongAgentImpl(final RWSettings rwSett) {
        this.rwSett = rwSett;
    }

    @Override
    public final void close() {
        this.clip.stop();
        this.clip.close();
    }

    @Override
    public final Boolean isPlaying() {
        return this.playing;
    }

    @Override
    public final void play() {
        this.playing = true;

        if (this.checkStart) {
            this.clip.start();
        } else {
            this.start();
        }
    }

    @Override
    public final void pause() {
        this.playing = false;
        this.clip.stop();
    }

    /**
     * Start the {@link Clip}.
     */
    private void start() {
        this.checkStart = true;
        try {
            this.clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        final String path = this.urlSound + this.rwSett.getSong();
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile())) {
            this.clip.open(audioStream);
            this.clip.start();
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }

    }

}

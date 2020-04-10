package graphicsutility;

import controlutility.RWSettings;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import javafx.scene.control.Button;

public class SongAgentImpl implements SongAgent {
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final String urlSound = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "sound" + SEPARATOR;
    private Clip clip;
    private Boolean playing = false;
    private Boolean checkStart = false;
    final RWSettings rwSett;

    public SongAgentImpl(final RWSettings rwSett) {
        this.rwSett = rwSett;
    }

    @Override
    public void close() {
        clip.stop();
        clip.close();
    }




    @Override
    public Boolean isPlaying() {
        return this.playing;
    }

    @Override
    public Boolean shift() {
        this.playing = !playing;
        return playing;
    }

    @Override
    public void play() {
        this.playing = true;

        if(checkStart) {
            clip.start();
        } else {
            start();
        }
    }

    @Override
    public void stop() {
        this.playing = false;
        clip.stop();
    }

    @Override
    public void checkSong(final Button btnSong) {
        if(isPlaying()) {
            //btnSong.setText("MUTED");
            btnSong.setText("MUTED");
            stop();
        } else {
            //btnSong.setText("MUTE");
            btnSong.setText("MUTE");
            play();
        }
    }

    private void start() {
        this.checkStart = true;
        try {
            this.clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        final String path = urlSound + rwSett.getSong();
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile())) {
            clip.open(audioStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }

    }






}

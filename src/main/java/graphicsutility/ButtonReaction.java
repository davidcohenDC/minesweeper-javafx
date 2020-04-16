package graphicsutility;

import javafx.scene.control.Button;

import java.io.IOException;

public interface ButtonReaction {

    Boolean backHome() throws IOException;

    void checkMusic(final Button btnSong, final SongAgent music);

    void checkDualMusic(final Button btnSong,final Button btnSong2, final SongAgent music);
}

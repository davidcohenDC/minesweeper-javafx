package graphicsutility;

import javafx.scene.control.Button;

public interface SongAgent {


     void play();

     void stop();

     Boolean shift();

     Boolean isPlaying();

     void close();

     void checkSong(final Button btnSong);
}

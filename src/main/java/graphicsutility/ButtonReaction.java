package graphicsutility;

import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Interface to manage {@link Button} of the game.
 */
public interface ButtonReaction {

    /**
     * The function of back home {@link Button} return to the
     * {@link controllers.BackHomeController} scene.
     *
     * @return a boolean to manage back home confirm with {@link AlertHandler}
     */
    Boolean backHome() throws IOException;

    /**
     * The function of back home {@link Button} return to the
     * {@link controllers.BackHomeController} scene.
     *
     * @param btnSong
     *                    The {@link Button} for the Music
     * @param music
     *                    The {@link SongAgent} for manage the music
     */
    void checkMusic(final Button btnSong, final SongAgent music);

    /**
     * The function of back home {@link Button} return to the
     * {@link controllers.BackHomeController} scene.
     *
     * @param btnSong
     *                     The {@link Button} for handle the music on click
     *
     * @param btnSong2
     *                     The second {@link Button} for handle the music on click
     *
     * @param music
     *                     The {@link SongAgent} for manage the music
     */
    void checkDualMusic(final Button btnSong, final Button btnSong2, final SongAgent music);
}

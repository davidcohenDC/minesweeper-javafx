package graphicsutility;

import graphics.GameController;

import java.io.IOException;

public interface ButtonReaction {

    void backHome() throws IOException;

    void restartGame(final GameController controller) throws IOException;
}

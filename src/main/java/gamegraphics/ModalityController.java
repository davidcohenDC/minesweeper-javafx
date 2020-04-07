package gamegraphics;

import java.io.IOException;

public interface ModalityController {

    void startGame();

    /** initialize fields.
     * @throws IOException */
    void initialize() throws IOException;
}

package GameGraphics;

import java.io.IOException;

/**interface for GraphicsController.*/

public interface GraphicsControllerImpl {
    /** initialize graphics fields.
     * @throws IOException */
    void initialize() throws IOException;
    /**
     * The supervisor for PlayGame button.
     * @exception IOException
     *                            if an I/O error occurs.
     */
    void btPlayGame() throws IOException;
    /**
     * The supervisor for PlayGame button.
     * @exception IOException
     *                            if an I/O error occurs.
     */
    void btRestart() throws IOException;
    /**
     * The supervisor for Restart button.
     * @exception IOException
     *                            if an I/O error occurs.
     */
    void exit() throws IOException;
}

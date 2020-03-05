package controllers;

import java.io.IOException;

/**interface for HomeController.*/
public interface HomeInterface {
    /** initialize fields.
     * @throws IOException */
    void initialize() throws IOException;
    /**
     * The handler for 'PLAY GAME' button.
     * @exception IOException
     *                            if an I/O error occurs.
     */
    void btPlayGame() throws IOException;
    /**
     * The handler for  'HOW TO PLAY' button.
     * @exception IOException
     *                            if an I/O error occurs.
     */
    void btHowToPlay() throws IOException;
    /**
     * The handler for 'SETTINGS' button.
     * @exception IOException
     *                            if an I/O error occurs.
     */
    void btSettings() throws IOException;
    /**
     * The handler for 'STATISTICS' button.
     * @exception IOException
     *                            if an I/O error occurs.
     */
    void btStatistics() throws IOException;
    /**
     * The handler for 'exit' button.
     * @throws IOException 
     */
    void exit() throws IOException;

}

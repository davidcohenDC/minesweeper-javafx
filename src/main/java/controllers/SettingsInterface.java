package controllers;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;


/**interface for settingsController.*/
public interface SettingsInterface {

    /**
     * initialize fields.
     * 
     * @exception IOException
     *                            if an I/O error occurs.
     * @throws LineUnavailableException 
     */
    void initialize() throws IOException, LineUnavailableException;
    /**
     * The handler for  'ADD (MINES)' button.
     * @throws IOException 
     */
    void btAddImgMines() throws IOException;
    /**
     * The handler for  'ADD (FLAG)' button.
     * @throws IOException 
     */
    void btAddImgFlags() throws IOException;

    /**
     * The handler for '■' (stop) button.
     */
    void btStop();
}

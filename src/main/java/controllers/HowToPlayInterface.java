package controllers;

import java.io.IOException;

/** interface for HowToPlayController. */
public interface HowToPlayInterface {
    /**
     * initialize the text area with the game rule.
     * @throws IOException 
     */
    void initialize() throws IOException;
}

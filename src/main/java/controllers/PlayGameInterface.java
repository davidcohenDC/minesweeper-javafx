package controllers;

import java.io.IOException;

/**interface for playGameController.*/
public interface PlayGameInterface {

    /**
     * Initialize fields to start.
     * @throws IOException 
     */
    void initialize() throws IOException;
    /**
     * The handler for 'STANDARD' radio button.
     */
    void rbtStandard();
    /**
     * The handler for 'ONE VS ONE' radio button.
     */
    void rbtOnevsOne();
    /**
     * The handler for 'BEAT THE TIMER' radio button.
     */
    void rbtBtt();
    /**
     * The handler for 'EASY' radio button.
     */
    void rbtEasy();
    /**
     * The handler for 'MEDIUM' radio button.
     */
    void rbtMedium();
    /**
     * The handler for 'HARD' radio button.
     */
    void rbtHard();
    /**
     * The handler for 'PERSONALIZED' radio button.
     */
    void rbtPersonalized();
    /**
     * The handler for 'MINES' text field.
     * Check if the input number is correct.
     */
    void tfCheckMines();
    /**
     * The handler for 'HEIGHT' text field.
     * Check if the input number is correct.
     */
    void tfCheckHeight();
    /**
     * The handler for 'WIDTH' text field.
     * Check if the input number is correct.
     */
    void tfCheckWidth();
    /**
     * The handler for 'PLAY' button.
     */
    void btPlay();
}

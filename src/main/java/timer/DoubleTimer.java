package timer;

public interface DoubleTimer extends Runnable {

    /**
     * Switches which timer is running and which timer is on hold.
     */
    void switchTurn();

    /**
     * @return
     * Returns the timer assigned to player 1.
     */
    Timer getPlayer1Timer();

    /**
     * @return
     * Returns the timer assigned to player 2.
     */
    Timer getPlayer2Timer();

    /**
     * Starts the timers.
     * 
     * player1 will begin
     * 
     * player2 will be on hold waiting for his turn
     */
    void startTimers();
}

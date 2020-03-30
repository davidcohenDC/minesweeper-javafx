package timer;

public interface MultipleTimers extends Timer {

    /**
     * Switches which Timer is running.
     */
    void switchTurn();
}

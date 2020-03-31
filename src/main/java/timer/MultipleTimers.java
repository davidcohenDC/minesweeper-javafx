package timer;

/**
 * A Class designed to run multiple Timers with an order.
 */
public interface MultipleTimers extends Timer {

    /**
     * Switches which Timer is running.
     */
    void switchTurn();
}

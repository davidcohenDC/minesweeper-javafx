package timer;

public interface Timer extends Runnable {

    int getValue();

    void pause();

    void unPause();

    boolean isPaused();
}

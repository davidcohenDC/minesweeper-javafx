package graphicsutility;

import scoresystem.Player;

import java.util.Optional;

public interface AlertHandler {

    void won(final int score);

    void lost();

    void lostWithTimer();


}

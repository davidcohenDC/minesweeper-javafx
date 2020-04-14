package graphicsutility;

import scoresystem.Player;

import java.util.Optional;

public interface AlertHandler {


    void won(final Optional<Player> player);

    void lost(final Optional<Player> player);

    void lostWithTimer();

    void confirm();

    void sameName();


}

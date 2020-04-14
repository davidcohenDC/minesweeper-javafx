package graphicsutility;

import scoresystem.Player;

import java.util.Optional;

public interface AlertHandler {

    void wonWithoutPlayer();

    void wonWithPlayer(final Optional<Player> player);

    void lost();

    void lostWithTimer();

    public void confirm();


}

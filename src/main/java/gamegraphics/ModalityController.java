package gamegraphics;

import scoresystem.Player;

import java.io.IOException;
import java.util.Optional;

public interface ModalityController{

    void initialize() throws IOException;

    void setPlayer(Optional<Player>player);

}

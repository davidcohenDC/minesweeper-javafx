package graphics;

import gamelogics.GameStatus;
import javafx.scene.control.Label;

import java.util.Map;

public interface PlayerSupervisor {

    void view(final Label label);

    void setBaton();

    void unsetBaton();

    Boolean isMaster();

    void giveMaster();

    void setMaster(final Map<PlayerSupervisor,Boolean> playermap);

    void writePlayer(GameStatus status);


}

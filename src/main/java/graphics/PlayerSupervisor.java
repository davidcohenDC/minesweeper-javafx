package graphics;

import javafx.scene.control.Label;

public interface PlayerSupervisor {

    void view(final Label label);

    void setBaton();

    void unsetBaton();

    Boolean isMaster();

    void giveMaster();



}

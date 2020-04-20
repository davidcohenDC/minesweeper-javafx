package graphicsutility;

import javafx.scene.control.Label;
import scoresystem.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * The implementation of {@link PlayerSupervisor}.
 */
public class PlayerSupervisorImpl implements PlayerSupervisor {
    private final Optional<Player> player;
    private Boolean baton;
    private HashMap<PlayerSupervisor,Boolean> playersMap;

    public PlayerSupervisorImpl(final Optional<Player> player, final Boolean baton, final HashMap<PlayerSupervisor,Boolean> playersMap) {
        this.player = player;
        this.playersMap = playersMap;
        this.baton = baton;
        playersMap.put(this,baton);
    }

    @Override
    public void giveMaster(final HashMap<PlayerSupervisor,Boolean> playersMap) {
        Iterator<Map.Entry<PlayerSupervisor, Boolean>> entries = playersMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<PlayerSupervisor, Boolean> entry = entries.next();
            if(entry.getValue()) {
                entry.setValue(false);
                entry.getKey().unsetBaton();
                if(entries.hasNext()) {
                    entry = entries.next();
                    entry.setValue(true);
                    entry.getKey().setBaton();
                    break;
                } else {
                    for (Map.Entry<PlayerSupervisor, Boolean> first : playersMap.entrySet()) {
                        first.setValue(true);
                        first.getKey().setBaton();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void view(final Label label) {
        label.setText(player.isPresent() ? player.get().getName() : "NONE");
    }

    @Override
    public void setBaton() {
        this.baton = true;
    }

    @Override
    public void unsetBaton() {
        this.baton = false;
    }

    @Override
    public Boolean isMaster() {
        return this.baton;
    }




}

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

    public PlayerSupervisorImpl(final Optional<Player> player, final Boolean baton,
            final HashMap<PlayerSupervisor, Boolean> playersMap) {
        this.player = player;
        this.baton = baton;
        playersMap.put(this, baton);
    }

    @Override
    public final void giveMaster(final HashMap<PlayerSupervisor, Boolean> playersMap) {
        Iterator<Map.Entry<PlayerSupervisor, Boolean>> entries = playersMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<PlayerSupervisor, Boolean> entry = entries.next();
            if (entry.getValue()) {
                entry.setValue(false);
                entry.getKey().unsetBaton();
                if (entries.hasNext()) {
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
    public final void view(final Label label) {
        label.setText(player.isPresent() ? player.get().getName() : "NONE");
    }

    @Override
    public final void setBaton() {
        this.baton = true;
    }

    @Override
    public final void unsetBaton() {
        this.baton = false;
    }

    @Override
    public final Boolean isMaster() {
        return this.baton;
    }

}

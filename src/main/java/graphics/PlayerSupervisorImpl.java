package graphics;

import gamelogics.GameStatus;
import graphics.PlayerSupervisor;
import graphicsutility.AlertHandler;
import graphicsutility.AlertHandlerImpl;
import javafx.scene.control.Label;
import scoresystem.Player;
import scoresystem.ScoreWriter;
import scoresystem.ScoreWriterImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class PlayerSupervisorImpl implements PlayerSupervisor {
    private Optional<Player> player;
    private Boolean baton;
    private AlertHandler alert;
    private ScoreWriter scoreWriter;
    final HashMap<PlayerSupervisor,Boolean> playermap;



    public PlayerSupervisorImpl(final Optional<Player> player, final Boolean baton, final HashMap<PlayerSupervisor,Boolean>  playermap) {
        this.player = player;
        this.alert = new AlertHandlerImpl();
        this.playermap = playermap;
        this.baton = baton;
        this.scoreWriter = new ScoreWriterImpl();
        playermap.put(this,baton);
    }

    @Override
    public void view(final Label label) {
        if(player.isPresent()) {
            label.setText(player.get().getName());
        } else {
            label.setText("NONE");
        }
    }

    private Boolean exist() {
        return this.player.isPresent();
    }

    private Boolean empty() {
        if(player.get().getName() == "") {
            return true;
        } else {
            return false;
        }
    }

    public void setBaton() {
        this.baton = true;
    }

    public void unsetBaton() {
        this.baton = false;
    }

    public Boolean isMaster() {
        return this.baton;
    }


    public void giveMaster() {
        Iterator<Map.Entry<PlayerSupervisor, Boolean>> entries = playermap.entrySet().iterator();
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
                    for (Map.Entry<PlayerSupervisor, Boolean> first : playermap.entrySet()) {
                        first.setValue(true);
                        first.getKey().setBaton();
                        break;
                        }
                    }
                }
            }
    }

    public void setMaster(final Map<PlayerSupervisor,Boolean> playermap) {

        Iterator<Map.Entry<PlayerSupervisor, Boolean>> entries = playermap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<PlayerSupervisor, Boolean> entry = entries.next();
            if (entry.getKey() == this) {
                if (this.baton) {
                    System.out.println("you are master");
                  }
            if (entry.getValue()) {
                if (entry.getKey() != this) {
                    entry.setValue(false);
                    entry.getKey().unsetBaton();
                } else {
                    entry.setValue(true);
                    entry.getKey().setBaton();
                }
            }
            }
        }
    }

    public void writePlayer(GameStatus status) {
        if (this.player.isPresent()) {
            if (status.equals(GameStatus.LOST)) {
                    this.player.get().lost();
            } else if(status.equals(GameStatus.WON)) {
                this.scoreWriter.write(this.player.get());
            }
        }
    }
}

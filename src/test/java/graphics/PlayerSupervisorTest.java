package graphics;

import graphicsutility.PlayerSupervisor;
import graphicsutility.PlayerSupervisorImpl;
import org.junit.jupiter.api.Test;
import scoresystem.Player;
import java.util.HashMap;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class PlayerSupervisorTest {
    Optional<Player> player;
    Optional<Player> player2;
    private HashMap<PlayerSupervisor, Boolean> playersMap = new HashMap<>();
    private PlayerSupervisor playerP1;
    private PlayerSupervisor playerP2;

    @Test
    void testPlayerSupervisor() {
        this.playerP1 = new PlayerSupervisorImpl(player, false, playersMap);
        this.playerP2 = new PlayerSupervisorImpl(player2, false, playersMap);
        playerP1.unsetBaton();
        assertEquals(false, playerP1.isMaster());
        playerP2.setBaton();
        assertEquals(true, playerP2.isMaster());
        assertEquals(true, playerP2.isMaster());
        playerP1.giveMaster(this.playersMap);
        assertEquals(false, playerP1.isMaster());
        assertEquals(true, playerP2.isMaster());
    }

}
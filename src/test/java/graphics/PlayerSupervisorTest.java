package graphics;

import graphicsutility.PlayerSupervisor;
import graphicsutility.PlayerSupervisorImpl;
import org.junit.jupiter.api.Test;
import scoresystem.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test PlayerSupervisor.*/
public class PlayerSupervisorTest {
    private final Optional<Player> player = Optional.empty();
    private final Optional<Player> player2 = Optional.empty();

    /**test the correct baton between players.*/
    @Test
    public void testPlayerSupervisor() {
        final Map<PlayerSupervisor, Boolean> playersMap = new HashMap<>();
        final PlayerSupervisor playerP1;
        final PlayerSupervisor playerP2;
        playerP1 = new PlayerSupervisorImpl(player, false, playersMap);
        playerP2 = new PlayerSupervisorImpl(player2, false, playersMap);
        playerP1.unsetBaton();
        assertFalse(playerP1.isMaster());
        playerP2.setBaton();
        assertTrue(playerP2.isMaster());
        assertTrue(playerP2.isMaster());
        playerP1.giveMaster(playersMap);
        assertFalse(playerP1.isMaster());
        assertTrue(playerP2.isMaster());
    }

}

package gameLogics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameEngineImplTest {

    @org.junit.jupiter.api.Test
    void getGameStatus() {
        //test without bombs for checking the win
        final GameEngine withOutBomb = new GameEngineImpl(4, 4, 0);
        assertEquals(withOutBomb.getGameStatus(), GameStatus.NORMAL);
        //test win
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                withOutBomb.hit(new Pair<>(i,j));
            }
        }
        //after all box are clicked I win
        assertEquals(withOutBomb.getGameStatus(), GameStatus.WIN);

        //test without bombs and with flags
        final GameEngine withFlag = new GameEngineImpl(4, 4, 0);
        assertEquals(withFlag.getGameStatus(), GameStatus.NORMAL);
        //test win
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                withFlag.setFlag(new Pair<>(i,j));
            }
        }
        //after all box are flagged I win
        assertEquals(withFlag.getGameStatus(), GameStatus.WIN);

        //test loss
        final GameEngine withBomb = new GameEngineImpl(4, 4, 1);
        assertEquals(withBomb.getGameStatus(), GameStatus.NORMAL);
        //test win
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                withBomb.hit(new Pair<>(i,j));
            }
        }
        //after all box are clicked I had clicked also the bomb...so I lost
        assertEquals(withBomb.getGameStatus(), GameStatus.LOSE);
    }

    @Test
    void getBoard() {
    }
}
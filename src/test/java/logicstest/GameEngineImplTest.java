package logicstest;

import gamelogics.GameEngine;
import gamelogics.GameEngineImpl;
import gamelogics.GameStatus;
import gamelogics.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameEngineImplTest {

    @org.junit.jupiter.api.Test
    public void testGetGameStatus() {
        final int width = 4;
        final int height = 4;

        //test without bombs for checking the win
        final GameEngine withOutBomb = new GameEngineImpl(width, height, 0);
        assertEquals(withOutBomb.getGameStatus(), GameStatus.PLAYING);
        //test win
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                withOutBomb.hit(new Pair<>(i, j));
            }
        }
        //after all box are clicked I win
        assertEquals(withOutBomb.getGameStatus(), GameStatus.WON);

        //test without bombs and with flags
        final GameEngine withFlag = new GameEngineImpl(width, height, 0);
        assertEquals(withFlag.getGameStatus(), GameStatus.PLAYING);
        //test win
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                withFlag.setFlag(new Pair<>(i, j));
            }
        }
        //after all box are flagged I win
        assertEquals(withFlag.getGameStatus(), GameStatus.WON);

        //test loss
        final GameEngine withBomb = new GameEngineImpl(width, height, 1);
        assertEquals(withBomb.getGameStatus(), GameStatus.PLAYING);
        //test win
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                withBomb.hit(new Pair<>(i, j));
                //Uncomment to show how change board when hit
                /* System.out.println(withBomb.getBoard());
                 * System.out.println("----------"); */
            }
        }
        //after all box are clicked I had clicked also a bomb...so I lost
        assertEquals(withBomb.getGameStatus(), GameStatus.LOST);
    }
}

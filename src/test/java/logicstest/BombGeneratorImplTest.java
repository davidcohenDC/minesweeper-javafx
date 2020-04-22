package logicstest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gamelogics.BombGenerator;
import gamelogics.BombGeneratorImpl;

class BombGeneratorImplTest {

    @org.junit.jupiter.api.Test
    public void nextTest() {
        final int width = 5;
        final int height = 4;

        //setup without bombs
        final BombGenerator withoutBomb = new BombGeneratorImpl(width, height, 0);
        for (int i = 0; i < width * height; i++) {
            // 0 boolean false found
            assertFalse(withoutBomb.next());
        }

        //setup all box with a bomb
        final BombGenerator fullBomb = new BombGeneratorImpl(width, height, width * height);
        for (int i = 0; i < width * height; i++) {
            // 0 boolean false found
            assertTrue(fullBomb.next());
        }
    }

    @org.junit.jupiter.api.Test
    public void nextTestExeption() {
        final int width = 5;
        final int height = 4;

        //setup with bomb
        final BombGenerator withBomb = new BombGeneratorImpl(width, height, 3);
        for (int i = 0; i < width * height; i++) {
            withBomb.next();
            //Uncomment down and comment up to test how boolean are distributed
            //final boolean bomb = withBomb.next();
            //System.out.println(bomb);
        }
        //Test throws exception if trying to get boolean that is out of range of the board
        assertThrows(IndexOutOfBoundsException.class, () -> withBomb.next());
    }
}

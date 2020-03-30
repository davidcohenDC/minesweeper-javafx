package logicstest;

import gamelogics.BombGenerator;
import gamelogics.BombGeneratorImpl;

class BombGeneratorImplTest {

    @org.junit.jupiter.api.Test
    public void next() {
        final int width = 5;
        final int height = 4;
        final BombGenerator bg = new BombGeneratorImpl(width, height, 5);
        for (int i = 0; i < width * height; i++) {
            System.out.println(bg.next());
        }
    }
}

package gameLogics;

import org.junit.jupiter.api.Test;

import gamelogics.BombGenerator;
import gamelogics.BombGeneratorImpl;

import static org.junit.jupiter.api.Assertions.*;

class BombGeneratorImplTest {

    @Test
    void next() {
        final BombGenerator bg = new BombGeneratorImpl(20, 5);
        for(int i=0; i<20; i++) {
            System.out.println(bg.next());
        }
    }
}
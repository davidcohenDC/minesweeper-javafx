package gameLogics;

import java.util.*;

public class BombGeneratorImpl implements BombGenerator {

    private int size;
    private int head = -1;
    private final List<Boolean> bombList = new LinkedList<>();

    public BombGeneratorImpl(int size, int bombs) {
        this.size = 0;
        for(int i=0; i<bombs; i++) {
            bombList.add(true);
        }
        for(int j=0; j<size-bombs; j++) {
            bombList.add(false);
        }
        Collections.shuffle(bombList);
    }

    public boolean next() {
        this.head++;
        return bombList.get(head);
    }
}

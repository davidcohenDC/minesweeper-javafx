package gamelogics;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The implementation of {@link BombGenerator}.
 */
public class BombGeneratorImpl implements BombGenerator {

    private int head = -1;
    private final List<Boolean> bombList = new LinkedList<>();

    /**
     * Setup the bombs generator.
     * 
     * @param width
     *                   The width of the board
     * @param height
     *                   The height of the board
     * @param bombs
     *                   The number of bombs in the board
     */
    public BombGeneratorImpl(final int width, final int height, final int bombs) {
        final int size = width * height;
        for (int i = 0; i < bombs; i++) {
            bombList.add(true);
        }
        for (int j = 0; j < size - bombs; j++) {
            bombList.add(false);
        }
        Collections.shuffle(bombList);
    }

    @Override
    public final boolean next() {
        this.head++;
        return bombList.get(head);
    }
}

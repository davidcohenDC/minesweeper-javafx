package logicstest;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import gamelogics.Board;
import gamelogics.BoardBuilder;
import gamelogics.BoardBuilderImpl;
import gamelogics.Box;
import gamelogics.BoxImpl;
import gamelogics.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {

    private static Board board;

    @org.junit.jupiter.api.BeforeAll
    public static void initialize() {
        //Setup a board
        final int width = 4;
        final int height = 4;
        final BoardBuilder bb = new BoardBuilderImpl()
            .withWidth(4)
            .withHeight(4);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                final Box box = new BoxImpl(new Pair<>(i, j));
                bb.addBox(box);
            }
        }
        board = bb.build();
    }

    @org.junit.jupiter.api.Test
    public void testGetBox() {
        //Tests of getBox
        assertEquals(board.getBox(new Pair<>(0, 0)).getPosition(), new Pair<>(0, 0));
        assertEquals(board.getBox(new Pair<>(1, 1)).getPosition(), new Pair<>(1, 1));
        assertEquals(board.getBox(new Pair<>(2, 2)).getPosition(), new Pair<>(2, 2));
        assertEquals(board.getBox(new Pair<>(3, 3)).getPosition(), new Pair<>(3, 3));
        //Test throws exception if trying to get a box that doesn't exist
        assertThrows(NoSuchElementException.class, () -> board.getBox(new Pair<>(4, 4)));
    }

    @org.junit.jupiter.api.Test
    public void testGetNearBox() {
        //Test of get the box near the box in position 0,0
        final Set<Box> correctSet = new HashSet<>();
        correctSet.add(new BoxImpl(new Pair<>(1, 0)));
        correctSet.add(new BoxImpl(new Pair<>(0, 1)));
        correctSet.add(new BoxImpl(new Pair<>(1, 1)));
        assertEquals(board.getNearBox(new BoxImpl(new Pair<>(0, 0))), correctSet);
    }
}

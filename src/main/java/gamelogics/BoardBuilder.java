package gamelogics;

import java.util.Set;

/**
 * Interface for building Board.
 * */
public interface BoardBuilder {

    /**
     * set the board width.
     * @param w
     * width of the board
     * @return
     * BoardBuilder
     */
    BoardBuilder withWidth(int w);

    /**
     * set the board height.
     * @param h 
     * height of the board
     * @return 
     * BoardBuilder
     */
    BoardBuilder withHeight(int h);

    /**
     * add a box to board.
     * @param box
     * box to add
     * @return 
     * BoardBuilder
     */
    BoardBuilder addBox(Box box);

    /**
     * add a box of list to board.
     * @param boxSet
     * Set of box
     * @return
     * BoardBuilder
     */
    BoardBuilder addBoxSet(Set<Box> boxSet);

    /**
     * build the board.
     * @return 
     * Board
     */
    Board build();
}

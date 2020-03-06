package gameLogics;

import java.util.Set;

/**
 * Interface for build Board
 * */
public interface BoardBuilder {

    /**
     * set the board width
     * @param w is weigth of the board
     * @return the BoardBuilder
     */
    BoardBuilder setWidth(int w);

    /**
     * set the board height
     * @param h is height of the board
     * @return the BoardBuilder
     */
    BoardBuilder setHeight(int h);

    /**
     * add a box to board
     * @param box to add
     * @return the BoardBuilder
     */
    BoardBuilder addBox(Box box);

    /**
     * add a box of list to board
     * @param boxSet is a set of box
     * @return the BoardBuilder
     */
    BoardBuilder addBoxSet(Set<Box> boxSet);

    /**
     * build the board
     * @return the built board
     */
    Board build();
}

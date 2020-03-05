package gameLogics;

import java.util.List;

/**
 * Interface for build Board
 * */
public interface BoardBuilder {

    /**
     * set the board width
     * @param w
     * @return
     */
    BoardBuilder setWidth(int w);

    /**
     * set the board height
     * @param h
     * @return
     */
    BoardBuilder setHeight(int h);

    /**
     * add a box to board
     * @param box
     * @return
     */
    BoardBuilder addBox(Box box);

    /**
     * add a box of list to board
     * @param boxList
     * @return
     */
    BoardBuilder addBoxList(List<Box> boxList);

    /**
     * build the board
     * @return
     */
    Board build();
}

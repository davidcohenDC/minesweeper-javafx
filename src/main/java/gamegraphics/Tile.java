package gamegraphics;


import java.io.FileInputStream;
import java.io.IOException;

import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**is a button of the board.*/
public class Tile extends Button {
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final String urlImgMine = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "image" + SEPARATOR
            + "mines" + SEPARATOR;
    private final String urlImgFlag = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "image" + SEPARATOR
            + "flags" + SEPARATOR;
    private static final int BUTTON_SIZE = 35;
    private static final int IMAGE_SIZE = 26;
    private static final int MINE_VALUE = 9;
    private final ImageView imgFlag;
    private final ImageView imgMine;
    private boolean flagged;
    private boolean mine;
    private final int x; //row
    private final int y; //col
    private int value;


    /**
     * @param x of tile
     * @param y of tile
     * @throws IOException */
    public Tile(final int x, final int y) throws IOException {
        final RWSettings rwSett = new RWSettingsImpl();
        this.x = x;
        this.y = y;
        this.setText(" ");
        this.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
        final Image flag = new Image(new FileInputStream(this.urlImgFlag + rwSett.getFlags()), IMAGE_SIZE, IMAGE_SIZE, true, true);
        this.imgFlag = new ImageView(flag);
        final Image mine = new Image(new FileInputStream(this.urlImgMine + rwSett.getMines()), IMAGE_SIZE, IMAGE_SIZE, true, true);
        this.imgMine = new ImageView(mine);
        this.setStyle("-fx-padding:0");
    }
    /**
     * @return x of tile*/
    public int getX() {
        return x;
    }
    /**
     * @return y of tile*/
    public int getY() {
        return y;
    }
    /**
     * @return the value of tile*/
    public int getValue() {
        return value;
    }
    /**
     * @param n value to set*/
    public final void setValue(final int n) {
        value = n;
    }
    /**
     * remove / set the flag.*/
    public final void flag() {
        flagged = !flagged;
        if (flagged) {
            this.setGraphic(this.imgFlag);
        } else {
            this.setGraphic(null);
        }
    }
    /**
     * @return if is flagged*/
    public boolean isFlagged() {
        return flagged;
    }

    /**set mine.*/
    public final void setMine() {
        mine = true;
        value = MINE_VALUE;
    }
    /**
     * verify if is a mine.
     * @return mine*/
    public final boolean isMine() {
        return mine;
    }
    /**
     * disable the tile.*/
    public final void disable() {
        this.setDisable(true);
        if (mine) {
            this.setGraphic(this.imgMine);
        } else if (value > 0) {
            this.setText(String.valueOf(value));
        }
    }

}


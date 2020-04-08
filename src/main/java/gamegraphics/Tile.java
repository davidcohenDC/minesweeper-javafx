package gamegraphics;


import java.io.FileInputStream;
import java.io.IOException;

import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

//Tile of the GriPane
public class Tile extends Button {
    //Upload the seletected image of Mines and Flags in the image folder
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final String urlImgMine = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "image" + SEPARATOR
            + "mines" + SEPARATOR;
    private final String urlImgFlag = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "image" + SEPARATOR
            + "flags" + SEPARATOR;
    private static final int BUTTON_SIZE = 40;
    private static final int IMAGE_SIZE = 26;
    private static final int MINE_VALUE = 9;
    private final ImageView imgFlag;
    private final ImageView imgMine;
    private boolean flagged;
    private boolean mine;
    private final int x; //row
    private final int y; //col
    private int value;



    public Tile(final int x, final int y) throws IOException {
        final RWSettings rwSett = new RWSettingsImpl();
        this.x = x;
        this.y = y;
        this.setText("");
        this.setId("tile");
        this.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
        final Image flag = new Image(new FileInputStream(this.urlImgFlag + rwSett.getFlags()), IMAGE_SIZE, IMAGE_SIZE, true, true);
        this.imgFlag = new ImageView(flag);
        final Image mine = new Image(new FileInputStream(this.urlImgMine + rwSett.getMines()), IMAGE_SIZE, IMAGE_SIZE, true, true);
        this.imgMine = new ImageView(mine);
        this.setStyle("-fx-padding:0");
        //setStyle("-fx-border: 1px solid; -fx-border-color: black;");

    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public final void setValue(final int n) {
        value = n;
    }



    public final void flag() {
        flagged = !flagged;
        if (flagged) {
            this.setGraphic(this.imgFlag);
        } else {
            this.setGraphic(null);
        }

    }

    public boolean isFlagged() {
        return flagged;
    }

    public final void setMine() {
        mine = true;
        value = MINE_VALUE;
    }

    public final boolean isMine() {
        return mine;
    }

    public final void disable() {
        this.setDisable(true);
        if (mine) {
            this.setGraphic(this.imgMine);
        } else if (value > 0) {
            this.setText(String.valueOf(value));
        }
    }

}


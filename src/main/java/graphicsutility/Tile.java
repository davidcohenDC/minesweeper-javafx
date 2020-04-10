package graphicsutility;


import java.io.FileInputStream;
import java.io.IOException;

import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    private boolean flagged;
    private boolean mine;
    private final int x; //row
    private final int y; //col
    private int value;
    private ImageView imgFlag;
    private ImageView imgMine;
    private final RWSettings rwSett;



    public Tile(final int x, final int y) throws IOException {
        this.rwSett = new RWSettingsImpl();
        this.x = x;
        this.y = y;
        this.setText("");
        this.setId("tile");
        this.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
        this.setStyle("-fx-padding:0");
    }

    public void openStreamFlag() throws IOException{
        final Image flag = new Image(new FileInputStream(this.urlImgFlag + rwSett.getFlags()), IMAGE_SIZE, IMAGE_SIZE, true, true);
        imgFlag = new ImageView(flag);

    }
    public void openStreamBomb() throws IOException{
        final Image mine = new Image(new FileInputStream(this.urlImgMine + rwSett.getMines()), IMAGE_SIZE, IMAGE_SIZE, true, true);
        imgMine = new ImageView(mine);
    }

    public final void setflag() {
        flagged = !flagged;
        if (flagged) {
            try {
                openStreamFlag();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setGraphic(this.imgFlag);
        } else {
            this.setGraphic(null);
        }

    }

    public final void setMine() {

        try {
            openStreamBomb();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setGraphic(this.imgMine);
    }

    public final void disable() {
        this.setDisable(true);
        if (mine) {
            try {
                openStreamBomb();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setGraphic(this.imgMine);
        } else if (value > 0) {
            this.setText(String.valueOf(value));
        }
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

    public Boolean isFlagged() {
        return flagged;
    }

    public final void setValue(final int n) {
        value = n;
    }

}


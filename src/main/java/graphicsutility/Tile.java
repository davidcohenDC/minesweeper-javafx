package graphicsutility;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javax.sound.sampled.*;

//Tile of the GriPane
public class Tile extends Button {
    //Upload the seletected image of Mines and Flags in the image folder
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final String urlImgMine = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "image" + SEPARATOR
            + "mines" + SEPARATOR;
    private final String urlImgFlag = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "image" + SEPARATOR
            + "flags" + SEPARATOR;
    private final String urlAudioEffects = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "audioeffect" + SEPARATOR;
    final String srcAddFlag = this.urlAudioEffects + "addflag.wav";
    final String srcRemoveFlag = this.urlAudioEffects + "removeflag.wav";
    final String srcOpenTile = this.urlAudioEffects + "click.wav";
    final String srcOpenBigTile = this.urlAudioEffects + "firstclick.wav";
    private static final int BUTTON_SIZE = 35;
    private static final int IMAGE_SIZE = 26;
    private Clip clip;
    private Clip clip2;
    private boolean flagged;
    private boolean mine;
    private final int x; //row
    private final int y; //col
    private int value;
    private ImageView imgFlag;
    private ImageView imgMine;
    private final RWSettings rwSett;
    private NodeEffect effect;



    public Tile(final int x, final int y) throws IOException, LineUnavailableException {
        this.rwSett = new RWSettingsImpl();
        this.x = x;
        this.y = y;
        this.setText("");
        this.setId("tile");
        this.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
        this.setStyle("-fx-padding:0");
        this.clip = AudioSystem.getClip();
        this.clip2 = AudioSystem.getClip();
        this.effect = new NodeEffectImpl();
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
                clipAddFlag();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setGraphic(this.imgFlag);
        } else {
            clipRemoveFlag();
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
        return this.value;
    }

    public Boolean isFlagged() {
        return flagged;
    }

    public final void setValue(final int n) {
        value = n;
    }

    public void clipAudioClick() {
        if(clip.isOpen()) {
            clip.close();
            clip.flush();
        }
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(srcOpenTile).getAbsoluteFile())) {
            clip.open(audioStream);
            clip.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
    }

    private void clipAddFlag() {
        if(clip.isOpen()) {
            clip.close();
            clip.flush();
        }
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(srcAddFlag).getAbsoluteFile())) {
            clip.open(audioStream);
            clip.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
    }

    private void clipRemoveFlag() {
        if(clip2.isOpen()) {
            clip2.close();
            clip2.flush();
        }
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(srcRemoveFlag).getAbsoluteFile())) {
            clip2.open(audioStream);
            clip2.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
    }

    public void clipBigClick() {
        if(clip.isOpen()) {
            clip.close();
            clip.flush();
        }
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(srcOpenBigTile).getAbsoluteFile())) {
            clip.open(audioStream);
            clip.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
    }

    public final void style(final int value) {
        switch (value) {
            case 0:
                this.setStyle("-fx-background-color:grey; -fx-padding:0; -fx-font-weight: bold;");
                break;
            case 1:
                this.setStyle("-fx-background-color:grey; -fx-padding:0; -fx-font-size: 20px; -fx-text-fill: blue; -fx-font-weight: bold;");
                break;
            case 2:
                this.setStyle("-fx-background-color:grey; -fx-padding:0; -fx-font-size: 20px; -fx-text-fill: green; -fx-font-weight: bold;");
                break;
            case 3:
                this.setStyle("-fx-background-color:grey; -fx-padding:0; -fx-font-size: 20px; -fx-text-fill: darkred; -fx-font-weight: bold;");
                break;
            case 4:
                this.setStyle("-fx-background-color:grey; -fx-padding:0; -fx-font-size: 20px; -fx-text-fill: purple; -fx-font-weight: bold;");
                break;
        }


    }

    public void fallingEffect() {
        effect.fallingTiles(this);
    }

}


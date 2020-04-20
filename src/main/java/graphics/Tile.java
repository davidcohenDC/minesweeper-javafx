package graphics;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import graphicsutility.NodeEffect;
import graphicsutility.NodeEffectImpl;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.sound.sampled.*;

public class Tile extends Button {
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final String urlImgMine = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "image" + SEPARATOR
            + "mines" + SEPARATOR;
    private final String urlImgFlag = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "image" + SEPARATOR
            + "flags" + SEPARATOR;
    private final String urlAudioEffects = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "audioeffect" + SEPARATOR;
    private static final int BUTTON_SIZE = 35;
    private static final int IMAGE_SIZE = 26;
    private final Clip clip;
    private final Clip clip2;
    private final int x; //row
    private final int y; //col
    private final RWSettings rwSett;
    private final NodeEffect effect;
    private final String srcAddFlag = this.urlAudioEffects + "addflag.wav";
    private final String srcRemoveFlag = this.urlAudioEffects + "removeflag.wav";
    private final String srcOpenTile = this.urlAudioEffects + "click.wav";
    private final String srcOpenBigTile = this.urlAudioEffects + "firstclick.wav";
    private boolean flagged;
    private boolean mine;
    private int value;
    private ImageView imgFlag;
    private ImageView imgMine;

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
        final Image flag = new Image(new FileInputStream(this.urlImgFlag + this.rwSett.getFlags()), IMAGE_SIZE, IMAGE_SIZE, true, true);
        this.imgFlag = new ImageView(flag);

    }

    public void openStreamBomb() throws IOException{
        final Image mine = new Image(new FileInputStream(this.urlImgMine + this.rwSett.getMines()), IMAGE_SIZE, IMAGE_SIZE, true, true);
        this.imgMine = new ImageView(mine);
    }

    public final void setflag() {
        this.flagged = !this.flagged;
        if (this.flagged) {
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
        if (this.mine) {
            try {
                openStreamBomb();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setGraphic(this.imgMine);
        } else if (this.value > 0) {
            this.setText(String.valueOf(this.value));
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getValue() {
        return this.value;
    }

    public Boolean isFlagged() {
        return this.flagged;
    }

    public final void setValue(final int n) {
        this.value = n;
}

    public void clipAudioClick() {
        if(this.clip.isOpen()) {
            this.clip.close();
            this.clip.flush();
        }
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(this.srcOpenTile).getAbsoluteFile())) {
            this.clip.open(audioStream);
            this.clip.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
    }

    private void clipAddFlag() {
        if(this.clip.isOpen()) {
            this.clip.close();
            this.clip.flush();
        }
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(this.srcAddFlag).getAbsoluteFile())) {
            this.clip.open(audioStream);
            this.clip.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
    }

    private void clipRemoveFlag() {
        if(this.clip2.isOpen()) {
            this.clip2.close();
            this.clip2.flush();
        }
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(this.srcRemoveFlag).getAbsoluteFile())) {
            this.clip2.open(audioStream);
            this.clip2.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
    }

    public void clipBigClick() {
        if(this.clip.isOpen()) {
            this.clip.close();
            this.clip.flush();
        }
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(this.srcOpenBigTile).getAbsoluteFile())) {
            this.clip.open(audioStream);
            this.clip.start();

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
        this.effect.fallingTiles(this);
    }

}


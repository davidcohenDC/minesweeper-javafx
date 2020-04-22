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

/**
 * The implementation of {@link Tile}.
 */
public class TileImpl extends Button implements Tile {
    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String urlImgMine = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "image"
            + SEPARATOR + "mines" + SEPARATOR;
    private static final String urlImgFlag = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "image"
            + SEPARATOR + "flags" + SEPARATOR;
    private static final String urlAudioEffects = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR
            + "audioeffect" + SEPARATOR;
    private final String srcAddFlag = urlAudioEffects + "addflag.wav";
    private final String srcRemoveFlag = urlAudioEffects + "removeflag.wav";
    private final String srcOpenTile = urlAudioEffects + "click.wav";
    private final String srcOpenBigTile = urlAudioEffects + "firstclick.wav";
    private final double size;
    private static final int IMAGE_SIZE = 26;
    private final Clip clip;
    private final Clip clip2;
    private final int x;
    private final int y;
    private final RWSettings rwSett;
    private final NodeEffect effect;
    private boolean flagged;
    private boolean mine;
    private int value;
    private ImageView imgFlag;
    private ImageView imgMine;

    public TileImpl(final int x, final int y, final double size) throws IOException, LineUnavailableException {
        this.rwSett = new RWSettingsImpl();
        this.x = x;
        this.y = y;
        this.size = size;
        this.setText("");
        this.setId("tile");
        this.setPrefSize(this.size, this.size);
        this.setStyle("-fx-padding:0");
        this.clip = AudioSystem.getClip();
        this.clip2 = AudioSystem.getClip();
        this.effect = new NodeEffectImpl();
    }

    @Override
    public final void setFlag() {
        this.flagged = !this.flagged;
        if (this.flagged) {
            try {
                openStreamFlag();
                audioAddFlag();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setGraphic(this.imgFlag);
        } else {
            audioRemoveFlag();
            this.setGraphic(null);
        }
    }

    @Override
    public final void setMine() {
        try {
            openStreamBomb();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setGraphic(this.imgMine);
    }

    @Override
    public final void setDisable() {
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

    @Override
    public final void setStyle(final int value) {
        switch (value) {
        case 0:
            this.setStyle("-fx-background-color:grey; -fx-padding:0; -fx-font-weight: bold;");
            break;
        case 1:
            this.setStyle("-fx-background-color:grey; -fx-padding:0; -fx-text-fill: blue; -fx-font-weight: bold;");
            break;
        case 2:
            this.setStyle("-fx-background-color:grey; -fx-padding:0; -fx-text-fill: green; -fx-font-weight: bold;");
            break;
        case 3:
            this.setStyle("-fx-background-color:grey; -fx-padding:0; -fx-text-fill: darkred; -fx-font-weight: bold;");
            break;
        case 4:
            this.setStyle("-fx-background-color:grey; -fx-padding:0; -fx-text-fill: purple; -fx-font-weight: bold;");
            break;
        default:
            break;
        }

    }

    @Override
    public final void setValue(final int n) {
        this.value = n;
    }

    @Override
    public final int getX() {
        return this.x;
    }

    @Override
    public final int getY() {
        return this.y;
    }

    @Override
    public final int getValue() {
        return this.value;
    }

    @Override
    public final Boolean isFlagged() {
        return this.flagged;
    }

    @Override
    public final void setEffect() {
        this.effect.fallingTiles(this);
    }

    @Override
    public final void audioClick() {
        if (this.clip.isOpen()) {
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

    @Override
    public final void audioBigClick() {
        if (this.clip.isOpen()) {
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

    @Override
    public final void audioAddFlag() {
        if (this.clip.isOpen()) {
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

    @Override
    public final void audioRemoveFlag() {
        if (this.clip2.isOpen()) {
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

    /**
     * The handler for open flag image stream of {@link Tile}.
     * 
     * @exception IOException
     *                            if an I/O error occurs.
     */
    private void openStreamFlag() throws IOException {
        final Image flag = new Image(new FileInputStream(urlImgFlag + this.rwSett.getFlags()), IMAGE_SIZE, IMAGE_SIZE, true,
                true);
        this.imgFlag = new ImageView(flag);

    }

    /**
     * The handler for open bomb image stream of {@link Tile}.
     * 
     * @exception IOException
     *                            if an I/O error occurs.
     */
    private void openStreamBomb() throws IOException {
        final Image mine = new Image(new FileInputStream(urlImgMine + this.rwSett.getMines()), IMAGE_SIZE, IMAGE_SIZE, true,
                true);
        this.imgMine = new ImageView(mine);
    }

}

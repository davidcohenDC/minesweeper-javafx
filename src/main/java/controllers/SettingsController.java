package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import controlutility.WriteCss;
import controlutility.WriteCssImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * */

public final class SettingsController extends BackHomeController {
    private Color firstColor;
    private String urlImgMine;
    private String urlImgFlag;
    private Color secondColor;
    private WriteCss writeCss;
    private RWSettings rwSett;
    private final String separator = System.getProperty("file.separator");

    @FXML
    private final Button btBackHome = new Button();

    @FXML
    private final ColorPicker colorPicker1 = new ColorPicker();

    @FXML
    private final ColorPicker colorPicker2 = new ColorPicker();

    @FXML
    private final Button btPreview = new Button();

    @FXML
    private final MenuButton mbtMines = new MenuButton();

    @FXML
    private final MenuButton mbtFlags = new MenuButton();

    @FXML
    private final ImageView ivMines = new ImageView();

    @FXML
    private final ImageView ivFlags = new ImageView();

    /**
     * initialize fields.
     * 
     * @exception IOException
     *                            if an I/O error occurs.
     */
    public void initialize() throws IOException {
        this.urlImgMine = "src" + this.separator + "main" + this.separator + "resources"
                + this.separator + "image" + this.separator
                + "mines" + this.separator;
        this.urlImgFlag = "src" + this.separator + "main" + this.separator + "resources"
                + this.separator + "image" + this.separator
                + "flags" + this.separator;

        this.rwSett = new RWSettingsImpl();
        this.writeCss = new WriteCssImpl(this.rwSett.getFirstColor(), this.rwSett.getSecondColor());
        this.firstColor = Color.web(this.rwSett.getFirstColor());
        this.secondColor = Color.web(this.rwSett.getSecondColor());
        this.createMenuButtonM();
        this.createMenuButtonF();
        this.updateImgMines();
        this.updateImgFlag();
        this.updateBtPreview();
    }

    private final EventHandler<ActionEvent> selectMine = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent e) {
            rwSett.setMines(((MenuItem) e.getSource()).getText());
            try {
                updateImgMines();
                } catch (FileNotFoundException e1) {
               e1.printStackTrace();
            }
        }
    };


    private final EventHandler<ActionEvent> selectFlag = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent e) {
            rwSett.setFlags(((MenuItem) e.getSource()).getText());
            try {
                updateImgFlag();
                } catch (FileNotFoundException e1) {
               e1.printStackTrace();
            }
        }
    };

    private void createMenuButtonM() {
        try (Stream<Path> walk = Files.walk(Paths.get(urlImgMine))) {

            final List<String> result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());

            for (final String s : result) {
                final String name = s.replace(urlImgMine, "");
                final MenuItem item = new MenuItem(name);
                this.mbtMines.getItems().add(item);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mbtMines.getItems().forEach(e -> e.setOnAction(selectMine));
    }

    private void updateImgMines() throws FileNotFoundException {
        this.ivMines.setImage(new Image(new FileInputStream(this.urlImgMine + this.rwSett.getMines())));
        this.mbtMines.setText(this.rwSett.getMines());
    }

    private void createMenuButtonF() {
        try (Stream<Path> walk = Files.walk(Paths.get(urlImgFlag))) {

            final List<String> result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());

            for (final String s : result) {
                final String name = s.replace(urlImgFlag, "");
                final MenuItem item = new MenuItem(name);
                this.mbtFlags.getItems().add(item);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mbtFlags.getItems().forEach(e -> e.setOnAction(selectFlag));
    }

    private void updateImgFlag() throws FileNotFoundException {
        this.ivFlags.setImage(new Image(new FileInputStream(this.urlImgFlag + this.rwSett.getFlags())));
        this.mbtFlags.setText(this.rwSett.getFlags());
    }

    private void updateBtPreview() {
        final String fc = this.convertColor(this.firstColor);
        final String sc = this.convertColor(this.secondColor);
        final String btStyle = "-fx-background-color: linear-gradient(#" + fc + ", #" + sc
                + "); -fx-background-radius: 30, 30, 29, 28;-fx-padding: 3px 10px 3px 10px;";
        this.btPreview.setStyle(btStyle);
    }

    @FXML
    private void colorPicker1(final ActionEvent event) {
        event.consume();
        this.firstColor = this.colorPicker1.getValue();
        this.updateBtPreview();
        this.writeCss.update(this.convertColor(this.firstColor), this.convertColor(this.secondColor));
        this.rwSett.setFirstColor(this.convertColor(this.firstColor));
    }

    @FXML
    private void colorPicker2(final ActionEvent event) {
        event.consume();
        this.secondColor = this.colorPicker2.getValue();
        this.updateBtPreview();
        this.writeCss.update(this.convertColor(this.firstColor), this.convertColor(this.secondColor));
        this.rwSett.setSecondColor(this.convertColor(this.secondColor));
    }

    private String convertColor(final Color col) {
        return col.toString().substring(2, 8);
    }
}

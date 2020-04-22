package controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javafx.stage.FileChooser.ExtensionFilter;
/**
 * The Controller related to the settings.fxml GUI.
 * The implementation of {@link SettingsInterface }.
 */

public final class SettingsController extends BackHomeController implements SettingsInterface {
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final String urlImgMine = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "image" + SEPARATOR
            + "mines" + SEPARATOR;
    private final String urlImgFlag = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "image" + SEPARATOR
            + "flags" + SEPARATOR;
    private final String urlSound = System.getProperty("user.home") + SEPARATOR + ".minesweeper" + SEPARATOR + "sound" + SEPARATOR;
    private final List<String> css = new ArrayList<>(Arrays.asList("orange.css", "blue.css", "green.css", "pink.css", "white.css", 
            "yellow.css", "red.css", "black.css"));
    private RWSettings rwSett;
    private Clip clip;

    @FXML
    private  Button btBackHome;

    @FXML
    private  MenuButton mbtMines;

    @FXML
    private  MenuButton mbtFlags;

    @FXML
    private  MenuButton mbtCss;

    @FXML
    private  MenuButton mbtSound;

    @FXML
    private  ImageView ivMines;

    @FXML
    private  ImageView ivFlags;

    @Override
    public void initialize() throws IOException, LineUnavailableException {
        this.clip = AudioSystem.getClip(); 
        this.rwSett = new RWSettingsImpl();
        this.createMenuButtonM();
        this.createMenuButtonF();
        this.createMenuButtonC();
        this.createMenuButtonS();
        this.updateImgMines();
        this.updateImgFlag();
    }


    /**Event associate to mines' menu button.*/
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

    /**Event associate to flags' menu button.*/
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

    /**Event associate to style' menu button.*/
    private final EventHandler<ActionEvent> selectCss = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent e) {
            rwSett.setCss(((MenuItem) e.getSource()).getText());
            mbtCss.setText(rwSett.getCss());
            mbtCss.getScene().getStylesheets().clear();
            mbtCss.getScene().getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        }
    };

    /**Populate Mines menu button with the name of mines' images.*/
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

    /**Set the select name of mine image in the mines menu button.*/
    private void updateImgMines() throws FileNotFoundException {
        this.ivMines.setImage(new Image(new FileInputStream(this.urlImgMine + this.rwSett.getMines())));
        this.mbtMines.setText(this.rwSett.getMines());
    }

    @FXML
    @Override
    public void btAddImgMines() throws IOException {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select new mines image");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        final File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            final Path destination = Paths.get(this.urlImgMine + SEPARATOR + selectedFile.getName());
            Files.copy(selectedFile.toPath(), destination);
            this.rwSett.setMines(selectedFile.getName());
            this.mbtMines.getItems().clear(); 
            this.createMenuButtonM();
            this.updateImgMines();
        }
    }

    /**Populate Flags menu button with the name of flags' images.*/
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

    /**Set the select name of flag image in the image menu button.*/
    private void updateImgFlag() throws FileNotFoundException {
        this.ivFlags.setImage(new Image(new FileInputStream(this.urlImgFlag + this.rwSett.getFlags())));
        this.mbtFlags.setText(this.rwSett.getFlags());
    }

    @FXML
    @Override
    public void btAddImgFlags() throws IOException {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select new flag image");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
        final File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            final Path destination = Paths.get(this.urlImgFlag + SEPARATOR + selectedFile.getName());
            Files.copy(selectedFile.toPath(), destination);
            this.rwSett.setFlags(selectedFile.getName());
            this.mbtFlags.getItems().clear(); 
            this.createMenuButtonF();
            this.updateImgFlag();
        }
    }

    /**Populate Style menu button with the name of css.*/
    private void createMenuButtonC() throws IOException {
        for (final String l : this.css) {
            final MenuItem item = new MenuItem(l);
            item.setOnAction(selectCss);
            this.mbtCss.getItems().add(item);
            this.mbtCss.setText(this.rwSett.getCss());
        }
    }

    /**Event associate to Song' menu button.*/
    private final EventHandler<ActionEvent> selectSound = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent e) {
            btStop();
            rwSett.setSong(((MenuItem) e.getSource()).getText());
            mbtSound.setText(rwSett.getSong());
            final String path = urlSound + rwSett.getSong();
            try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile())) {
                btStop();
                clip.open(audioStream);
                clip.start();
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            }
        }
    };

    /**Populate Song menu button with the name of soundtracks.*/
    private void createMenuButtonS() {
        try (Stream<Path> walk = Files.walk(Paths.get(urlSound))) {
            final List<String> result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
            for (final String s : result) {
                final String name = s.replace(urlSound, "");
                final MenuItem item = new MenuItem(name);
                this.mbtSound.getItems().add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mbtSound.getItems().forEach(e -> e.setOnAction(selectSound));
        this.mbtSound.setText(rwSett.getSong());
    }

    @FXML
    @Override
    public void btStop() {
        if (clip.isOpen()) {
            clip.stop();
            clip.close();
        }
    }

    @FXML
    @Override
    public void btBackHome() {
        this.btStop();
        try {
            super.btBackHome();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

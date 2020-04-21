package controllers;

import java.io.IOException;

import controlutility.ReadRules;
import controlutility.ReadRulesImpl;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * The Controller related to the howToPlay.fxml GUI.
 * The implementation of {@link HowToPlayInterface }.
 */
public final class HowToPlayController extends BackHomeController implements HowToPlayInterface {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextArea txtArea;

    @Override
    public void initialize() throws IOException {
        final ReadRules reader = new ReadRulesImpl();
        this.txtArea.clear();
        reader.getAllLines().forEach(l -> txtArea.appendText(l + System.getProperty("line.separator")));
        this.txtArea.setScrollTop(Double.MAX_VALUE);

    }

}

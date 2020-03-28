package controllers;

import java.io.IOException;

import controlutility.Modality;
import controlutility.RWSettings;
import controlutility.RWSettingsImpl;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Controller related to the statistics.fxml GUI.
 */
public class StatisticsController implements StatisticsControllerInterface {
    private final Modality modality;
    private final String buttonText;
    @FXML
    private Text title;
    @FXML 
    private AnchorPane rootPane;
    @FXML
    private VBox vBox;
    @FXML
    private ScrollPane scrollPane;

    /**constructor that initialize field.
     * @param modality modality chosen.
     * @param buttonText set title in order the modality chosen.
    */
    public StatisticsController(final Modality modality, final String buttonText) {
        this.modality = modality;
        this.buttonText = buttonText;
    }

    /****/
    public void initialize() {
        this.title.setText("STATISTICS - " + this.buttonText);
        this.vBox.setAlignment(Pos.CENTER);
        this.addGeneralChart();
        this.addEasyChart();
        this.addMediumChart();
        this.addHardChart();
    }

    private void addHardChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> hardChart = new BarChart<>(xAxis, yAxis);
        hardChart.setTitle("Classification-Hard");
        xAxis.setLabel("PLAYERS");
        yAxis.setLabel("POINTS");
        //for players (messi in ordine decr.)
        /*
         * final XYChart.Series<String, Number> p1 = new XYChart.Series<>();
        p1.setName("Palyer1"); -> player name
        p1.getData().add(new XYChart.Data<>("player1", //PLAYER POINT));
        easyChart.getData().add(p1);
        */

        final XYChart.Series<String, Number> p1 = new XYChart.Series<>();
        p1.setName("Palyer1");
        p1.getData().add(new XYChart.Data<>("player1",100));
        final XYChart.Series<String, Number> p2 = new XYChart.Series<>();
        p2.setName("Palyer2");
        p2.getData().add(new XYChart.Data<>("player2",20));
        hardChart.getData().add(p1);
        hardChart.getData().add(p2);
        hardChart.setLegendVisible(false);
        this.vBox.getChildren().add(hardChart);
    }

    private void addMediumChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> mediumChart = new BarChart<>(xAxis, yAxis);
        mediumChart.setTitle("Classification-Medium");
        xAxis.setLabel("PLAYERS");
        yAxis.setLabel("POINTS");
        //for players (messi in ordine decr.)
        /*
         * final XYChart.Series<String, Number> p1 = new XYChart.Series<>();
        p1.setName("Palyer1"); -> player name
        p1.getData().add(new XYChart.Data<>("player1", //PLAYER POINT));
        easyChart.getData().add(p1);
        */

        final XYChart.Series<String, Number> p1 = new XYChart.Series<>();
        p1.setName("Palyer1");
        p1.getData().add(new XYChart.Data<>("player1",300));
        final XYChart.Series<String, Number> p2 = new XYChart.Series<>();
        p2.setName("Palyer2");
        p2.getData().add(new XYChart.Data<>("player2",100));
        mediumChart.getData().add(p1);
        mediumChart.getData().add(p2);
        mediumChart.setLegendVisible(false);
        this.vBox.getChildren().add(mediumChart);
    }

    private void addEasyChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> easyChart = new BarChart<>(xAxis, yAxis);
        easyChart.setTitle("Classification-Easy");
        xAxis.setLabel("PLAYERS");
        yAxis.setLabel("POINTS");
        //for players (messi in ordine decr.)
        /*
         * final XYChart.Series<String, Number> p1 = new XYChart.Series<>();
        p1.setName("Palyer1"); -> player name
        p1.getData().add(new XYChart.Data<>("player1", //PLAYER POINT));
        easyChart.getData().add(p1);
        */

        final XYChart.Series<String, Number> p1 = new XYChart.Series<>();
        p1.setName("Palyer1");
        p1.getData().add(new XYChart.Data<>("player1",1200));
        final XYChart.Series<String, Number> p2 = new XYChart.Series<>();
        p2.setName("Palyer2");
        p2.getData().add(new XYChart.Data<>("player2",1000));
        easyChart.getData().add(p1);
        easyChart.getData().add(p2);
        easyChart.setLegendVisible(false);
        this.vBox.getChildren().add(easyChart);
    }

    private void addGeneralChart() {
        final ObservableList<PieChart.Data> generalPieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Win", 120), //...TOTAL WIN
                new PieChart.Data("Defeat", 100)); //..TOTAL DEFEAT
        final PieChart generalChart = new PieChart(generalPieChartData);
        generalChart.setData(generalPieChartData);
        generalChart.setTitle("General");
        generalChart.setLegendSide(Side.LEFT);
        this.vBox.getChildren().add(generalChart);
    }

    @FXML
    @Override
    public  final void btBackStat() throws IOException {
        final RWSettings rwSett = new RWSettingsImpl();
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/mainStatistics.fxml"));
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(scene);
    }
}

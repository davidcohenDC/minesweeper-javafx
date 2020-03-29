package controllers;

import java.io.IOException;

import controlutility.Difficulty;
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
    private final double oldHeight;
    private final double oldWidth;
    @FXML
    private Text title;
    @FXML 
    private AnchorPane rootPane;
    @FXML
    private VBox vBox;


    /**constructor that initialize field.
     * @param modality modality chosen.
     * @param buttonText set title in order the modality chosen.
     * @param oldW weight of mainStatistic.
     * @param oldH height of mainStatistic.
    */
    public StatisticsController(final Modality modality, final String buttonText, final double oldW, final double oldH) {
        this.modality = modality;
        this.buttonText = buttonText;
        this.oldWidth = oldW;
        this.oldHeight = oldH;
    }

    /****/
    public void initialize() {
        this.title.setText("STATISTICS - " + this.buttonText);
        this.vBox.setAlignment(Pos.CENTER);
        this.addGeneralChart();
        this.addClassify();
    }

    private void addClassify() {
        for (final Difficulty difficulty : Difficulty.values()) {
            if (difficulty != Difficulty.PERSONALIZED) {
                final CategoryAxis yAxis = new CategoryAxis();
                final NumberAxis xAxis = new NumberAxis();
                final BarChart<Number, String> chart = new BarChart<>(xAxis, yAxis);
                chart.setTitle("Top 10-" + difficulty);
                xAxis.setLabel("POINTS");
                yAxis.setLabel("PLAYERS");
                //for players (messi in ordine decr.)
                /*
                 * final XYChart.Series<String, Number> p1 = new XYChart.Series<>();
                p1.setName("Palyer1"); -> player name
                p1.getData().add(new XYChart.Data<>("player1", //PLAYER POINT));
                easyChart.getData().add(p1);
                */
                for (int i = 0;i<10; i++) {
                    final XYChart.Series<Number,String> p = new XYChart.Series<>();
                    p.setName("Palyerrr"+ String.valueOf(i));
                    p.getData().add(new XYChart.Data<>(i*100,p.getName()));
                    chart.getData().add(p);
                }
                chart.setLegendVisible(false);
                chart.setStyle("-fx-font-size: 15");
                this.vBox.getChildren().add(chart);
            }
        }
    }

    private void addGeneralChart() {
        final ObservableList<PieChart.Data> generalPieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Wins", 120), //...TOTAL WIN
                new PieChart.Data("Losses", 100)); //..TOTAL DEFEAT
        final PieChart generalChart = new PieChart(generalPieChartData);
        //generalChart.setData(generalPieChartData);
        generalChart.setTitle("General");
        generalChart.setStyle("-fx-font-size: 15");
        generalChart.setLegendSide(Side.LEFT);
        this.vBox.getChildren().add(generalChart);
    }

    @FXML
    @Override
    public  final void btBackStat() throws IOException {
        final RWSettings rwSett = new RWSettingsImpl();
        final Parent pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/mainStatistics.fxml"));
        final Stage stage = (Stage) this.rootPane.getScene().getWindow();
        final Scene scene = new Scene(pane, this.oldWidth, this.oldHeight);
        scene.getStylesheets().add(ClassLoader.getSystemResource("css/" + rwSett.getCss()).toExternalForm());
        stage.setScene(scene);
    }
}

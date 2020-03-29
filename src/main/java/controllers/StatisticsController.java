package controllers;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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
import scoresystem.ScoreWriter;
import scoresystem.ScoreWriterImpl;
import scoresystem.StatistcsWriter;
import scoresystem.StatisticsWriterImpl;

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
        final ScoreWriter scoreWriter = new ScoreWriterImpl();
        for (final Difficulty difficulty : Difficulty.values()) {
            if (difficulty != Difficulty.PERSONALIZED) {
                final Map<String, Integer> top10 = scoreWriter.getScoreBoard(modality, difficulty)
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                        .limit(10)
                        .sorted(Map.Entry.<String, Integer>comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                System.out.println(top10);
                final CategoryAxis yAxis = new CategoryAxis();
                final NumberAxis xAxis = new NumberAxis();
                final BarChart<Number, String> chart = new BarChart<>(xAxis, yAxis);
                chart.setTitle("Top 10-" + difficulty);
                xAxis.setLabel("POINTS");
                yAxis.setLabel("PLAYERS");
                for (final Entry<String, Integer> entry : top10.entrySet()) {
                    final XYChart.Series<Number, String> p = new XYChart.Series<>();
                    p.setName(entry.getKey());
                    p.getData().add(new XYChart.Data<>(entry.getValue(), p.getName()));
                    chart.getData().add(p);
                }
                chart.setLegendVisible(false);
                chart.setStyle("-fx-font-size: 15");
                this.vBox.getChildren().add(chart);
            }
        }
    }

    private void addGeneralChart() {
        final StatistcsWriter statisticWriter = new StatisticsWriterImpl();
        final ObservableList<PieChart.Data> generalPieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Wins", statisticWriter.getAllWins(modality)),
                new PieChart.Data("Losses", statisticWriter.getAllLosses(modality)));
        final PieChart generalChart = new PieChart(generalPieChartData);
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

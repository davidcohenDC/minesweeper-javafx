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
 * The implementation of {@link StatisticsControllerInterface }.
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


    /**constructor that initialize field.
     * @param modality modality chosen.
     * @param buttonText set title in order the modality chosen.
    */
    public StatisticsController(final Modality modality, final String buttonText) {
        this.modality = modality;
        this.buttonText = buttonText;
    }

    @Override
    public final void initialize() {
        this.title.setText("STATISTICS - " + this.buttonText);
        this.vBox.setAlignment(Pos.CENTER);
        this.addGeneralChart();
        this.addClassify();
    }

    /**Create and add classify charters, one for each difficulty. */
    private void addClassify() {
        final ScoreWriter scoreWriter = new ScoreWriterImpl();
        for (final Difficulty difficulty : Difficulty.values()) {
            if (difficulty != Difficulty.PERSONALIZED) {
                final Map<String, Long> top10 = scoreWriter.getScoreBoard(modality, difficulty)
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.<String, Long>comparingByValue())
                        .limit(10)
                        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                final CategoryAxis yAxis = new CategoryAxis();
                final NumberAxis xAxis = new NumberAxis();
                final BarChart<Number, String> chart = new BarChart<>(xAxis, yAxis);
                chart.setTitle("Top 10-" + difficulty);
                xAxis.setLabel("POINTS");
                yAxis.setLabel("PLAYERS");
                for (final Entry<String, Long> entry : top10.entrySet()) {
                    final XYChart.Series<Number, String> p = new XYChart.Series<>();
                    p.setName(entry.getKey());
                    p.getData().add(new XYChart.Data<>(entry.getValue(), p.getName() + " (" + entry.getValue() + ")"));
                    chart.getData().add(p);
                }
                chart.setLegendVisible(false);
                chart.setStyle("-fx-font-size: 18");
                this.vBox.getChildren().add(chart);
            }
        }
    }
    /**Create and add grneral charter, that represent global wins and losses of a specific modality. */
    private void addGeneralChart() {
        final StatistcsWriter statisticWriter = new StatisticsWriterImpl();
        final ObservableList<PieChart.Data> generalPieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Wins", statisticWriter.getAllWins(modality)),
                new PieChart.Data("Losses", statisticWriter.getAllLosses(modality)));
        final PieChart generalChart = new PieChart(generalPieChartData);
        generalChart.setTitle("General");
        generalChart.setStyle("-fx-font-size: 18");
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

import com.sun.org.apache.xpath.internal.operations.Number;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    League nba = new League("local");

    @FXML
    private CategoryAxis categoryAxis;

    private XYChart.Series series1;

    @FXML
    private ComboBox<Player> player1ComboBox;

    @FXML
    private Tab tab1;

    @FXML
    private Label label;

    @FXML
    private Tab tab2;

    @FXML
    private Label labelComparePlayers;

    @FXML
    private Button button;

    @FXML
    private Button clearButton;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private VBox tabVBox;

    @FXML
    private ComboBox<Player> player3ComboBox;

    @FXML
    private VBox teamsVBox;

    @FXML
    private ComboBox<String> compareStatBox;

    @FXML
    private ComboBox<Team> comboBox;

    @FXML
    private ComboBox<Player> player2ComboBox;

    @FXML
    private NumberAxis numberAxis;

    @FXML
    private TabPane tabPane;

    @FXML
    private TableView<Player> table;

    @FXML
    private AnchorPane compareAnchor;

    private ComboBox[] comboBoxes;

    @FXML
    void clicked(ActionEvent event) {
        buildTable(getPlayers());
    }

    @FXML
    void updateAllStats(ActionEvent event) {
        nba = new League();
    }

    @FXML
    void selectPlayer(ActionEvent event) {
        barChart.getData().clear();
        series1 = new XYChart.Series();
        for (int i = 0; i < 3; i++) {
            if (comboBoxes[i] != null) {
                Player selected = getAllPlayers().get(comboBoxes[i].getSelectionModel().getSelectedIndex());
                XYChart.Data data = new XYChart.Data(selected.getName(), Double.parseDouble(selected.getAllStats()[compareStatBox.getSelectionModel().getSelectedIndex() + 6]));
                series1.getData().add(data);
                series1.setName(selected.getName());
                barChart.getData().add(series1);
                barChart.setLegendVisible(false);
            }
        }
    }

    @FXML
    void selectStat(ActionEvent event) {
        numberAxis.setLabel(compareStatBox.getSelectionModel().getSelectedItem());
        selectPlayer(event);
    }

    @FXML
    void clearChart() {
        barChart.getData().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Tab for Team Table
        buildComboBox();
        buildTable(getPlayers());

        //Tab for Player Compare
        buildComboBoxPlayers();
        buildComboBoxStats();
        buildBarChart();

        comboBoxes = new ComboBox[3];
        comboBoxes[0] = player1ComboBox;
        comboBoxes[1] = player2ComboBox;
        comboBoxes[2] = player3ComboBox;
    }

    //BEGIN METHODS FOR Tab for Team Table

    public void buildComboBox() {
        comboBox.setItems(getTeams());
        comboBox.setValue(nba.getTeams()[0]);
    }

    public ObservableList<Team> getTeams() {
        ObservableList<Team> data = FXCollections.observableArrayList();
        data.addAll(nba.getTeams());

        return data;
    }

    public void buildTable(ObservableList<Player> players) {
        table.getColumns().clear();
        table.getColumns().addAll(getColumn(table));
        table.setItems(players);
    }

    public static ArrayList<TableColumn<Player, String>> getColumn(TableView table) {
        ArrayList<TableColumn<Player, String>> columns = new ArrayList<TableColumn<Player, String>>();

        String[] columnNames = CSV.getHeader().split(",");
        String[] variableNames = CSV.getHeaderNoSpaces().split(",");
        //System.out.println(Arrays.toString(variableNames));

        for (int i = 0; i < columnNames.length; i++) {
            TableColumn<Player, String> col = new TableColumn<>(columnNames[i]);
            col.setCellValueFactory(new PropertyValueFactory<Player, String>(variableNames[i]));
            columns.add(col);
        }

        return columns;
    }

    public ObservableList<Player> getPlayers() {
        ObservableList<Player> data = FXCollections.observableArrayList();
        data.addAll(comboBox.getSelectionModel().getSelectedItem().getPlayers());
        Collections.sort(data);
        return data;
    }

    //END METHODS FOR Tab for Team Table

    //BEGIN METHODS FOR Tab for Player Compare

    public ObservableList<Player> getAllPlayers() {
        ObservableList<Player> data = FXCollections.observableArrayList();
        for (Team t : nba.getTeams()) {
            data.addAll(t.getPlayers());
        }
        Collections.sort(data);
        return data;
    }

    public void buildComboBoxPlayers() {
        player1ComboBox.setItems(getAllPlayers());
        player2ComboBox.setItems(getAllPlayers());
        player3ComboBox.setItems(getAllPlayers());
    }

    public ObservableList<String> getStats() {
        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll(CSV.getHeader().split(","));

        for (int i = 0; i < 5; i++)
            data.remove(0);

        return data;
    }

    public void buildComboBoxStats() {
        compareStatBox.setItems(getStats());
        compareStatBox.setValue(getStats().get(0));
    }

    public void buildBarChart() {
        categoryAxis.setLabel("Player");
        barChart.setBarGap(0);
    }

    //END METHODS FOR Tab for Player Compare


}

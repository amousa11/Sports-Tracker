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
import java.util.ResourceBundle;

public class Controller implements Initializable {
    League nba = new League("local");

    //FXML loaded from the XML file styles and guides layout of javafx gui components
    //Used scene builder to model the desired gui components
    @FXML
    private CategoryAxis categoryAxis;

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
    private BarChart<String, Double> barChart;

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


    //ActionEvent event is initialized based on what the user clicks
    //actions and method calls depend on what the user clicks
    //each new list clears the existing list and loads the new player data into table
    @FXML
    void clicked(ActionEvent event) {
        buildTable(getPlayers());
    }

    @FXML
    void updateAllStats(ActionEvent event) {
        nba = new League();
    }

    @FXML
    void selectPlayer1(ActionEvent event) {
        XYChart.Series series1 = new XYChart.Series();
        Player selected = getAllPlayers().get(player1ComboBox.getSelectionModel().getSelectedIndex());
        if (selected != null) {
            series1.getData().add(new XYChart.Data(selected.getName(), selected.getAllStats()[compareStatBox.getSelectionModel().getSelectedIndex()]));
            barChart.getData().add(series1);
        }
    }

    @FXML
    void selectPlayer2(ActionEvent event) {
        XYChart.Series series1 = new XYChart.Series();
        Player selected = getAllPlayers().get(player2ComboBox.getSelectionModel().getSelectedIndex());
        if (selected != null) {
            series1.getData().add(new XYChart.Data(selected.getName(), selected.getAllStats()[compareStatBox.getSelectionModel().getSelectedIndex()]));
            barChart.getData().add(series1);
        }
    }

    @FXML
    void selectPlayer3(ActionEvent event) {
        XYChart.Series series1 = new XYChart.Series();
        Player selected = getAllPlayers().get(player3ComboBox.getSelectionModel().getSelectedIndex());
        if (selected != null) {
            series1.getData().add(new XYChart.Data(selected.getName(), selected.getAllStats()[compareStatBox.getSelectionModel().getSelectedIndex()]));
            barChart.getData().add(series1);
        }
    }

    @FXML
    void selectStat(ActionEvent event) {
        selectPlayer1(event);
        selectPlayer2(event);
        selectPlayer3(event);
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

        return data;
    }

    //END METHODS FOR Tab for Team Table

    //BEGIN METHODS FOR Tab for Player Compare

    public ObservableList<Player> getAllPlayers() {
        ObservableList<Player> data = FXCollections.observableArrayList();
        for (Team t : nba.getTeams()) {
            data.addAll(t.getPlayers());
        }

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

        return data;
    }

    public void buildComboBoxStats() {
        compareStatBox.setItems(getStats());
        compareStatBox.setValue(getStats().get(5));
    }

    public void buildBarChart() {
        categoryAxis.setLabel("Player");
    }

    //END METHODS FOR Tab for Player Compare
}

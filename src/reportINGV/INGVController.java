package reportINGV;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class INGVController implements Initializable {

    @FXML
    private TableView<INGEvent> tableView;

    @FXML
    private TableColumn<INGEvent, LocalDateTime> dateColumn;

    @FXML
    private TableColumn<INGEvent, Float> magnitudeColumn;

    @FXML
    private TableColumn<INGEvent, String> locationColumn;

    @FXML
    private DatePicker dpStart;

    @FXML
    private DatePicker dpEnd;

    @FXML
    private TextField tfResults;

    @FXML
    private Button loadButton;

    @FXML
    private MenuItem saveItem;

    @FXML
    private ProgressIndicator progInd;

    @FXML
    private Label status;

    private ObservableList<INGEvent> list;

    private URL url;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList();

        tableView.setItems(list);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        magnitudeColumn.setCellValueFactory(new PropertyValueFactory<>("magnitude"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocationName"));

        dpStart.setValue(LocalDate.now());
        dpEnd.setValue(LocalDate.now());

        saveItem.disableProperty().bind(Bindings.isNull(tableView.getSelectionModel().selectedItemProperty()));
    }

    @FXML
    void loadFile(ActionEvent event) {
        final String startTime = dpStart.getValue().toString();
        final String endTime = dpEnd.getValue().toString();
        final String limit = (tfResults.getText().equals("")) ? "10" : tfResults.getText();
        try {
            url = new URL(
                    "https://webservices.ingv.it/fdsnws/event/1/query?starttime=" + startTime +
                            "T00%3A00%3A00&endtime="
                            + endTime
                            +
                            "T23%3A59%3A59&minmag=2&maxmag=10&mindepth=-10&maxdepth=1000&minlat=-90&maxlat=90&minlon=-180&maxlon=180&minversion=100&orderby=time-asc&format=text&limit="
                            + limit);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        CaricaReportService carica = new CaricaReportService(url, limit);

        progInd.visibleProperty().bind(carica.runningProperty());
        progInd.progressProperty().bind(carica.progressProperty());
        status.textProperty().bind(carica.messageProperty());
        tableView.itemsProperty().bind(carica.valueProperty());

        carica.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                list.clear();
                list.addAll(carica.getValue());
            }
        });
        carica.start();
    }

    @FXML
    void saveFile(ActionEvent event) {
        List<INGEvent> saveList = tableView.getSelectionModel().getSelectedItems();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
        final File file = fc.showOpenDialog(tfResults.getScene().getWindow());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.append("Date|Magnitude|Location\n");
            for (INGEvent ev : saveList) {
                bw.append(ev.getTime().toString() + "|");
                bw.append(ev.getMagnitude().toString() + "|");
                bw.append(ev.getEventLocationName() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

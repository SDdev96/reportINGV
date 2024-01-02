package reportINGV;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class INGVMainClass extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("INGVEvent.fxml"));

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setTitle("INGV Report eventi Sismici");
        primaryStage.show();
    }

    public static void main(String... args) {
        launch(args);
    }
}

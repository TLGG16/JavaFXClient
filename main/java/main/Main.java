package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Utility.ClientCon;
import main.Utility.Connect;
import main.Utility.SessionData;
import main.Utility.SessionDataHolder;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SessionData sessionData = new SessionData();
        SessionDataHolder holder = SessionDataHolder.getInstance();
        holder.setSessionData(sessionData);

        Connect.clientCon = new ClientCon("127.0.0.1", "5555");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/index.fxml")));
        primaryStage.setTitle("Condorcet");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


     public static void main(String[] args) {
        launch(args);
    }
}

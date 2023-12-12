package main.Controllers.Admin.Reports;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Enums.RequestCRUD;
import main.Models.Entities.Product;
import main.Models.Entities.Supplier;
import main.Models.TCP.Request;
import main.Utility.Connect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import static main.Enums.RequestType.*;

public class Menu {
    public Button CreateReportbtn;

    public void CreateReport_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminCreateReport.fxml")));
            Scene scene = new Scene(blah, 800 , 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void ShowReports_Pressed(ActionEvent actionEvent) {
        Request requestModel = new Request();
        requestModel.setRequestType(ADMINMENUREPORTS);
        requestModel.setRequestCRUD(RequestCRUD.SHOWALL);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<Product>>(){}.getType();
        ListView<Product> listView = new ListView<>();
        List<Product> suppliers = gson.fromJson(message, type);
        for (int i =0; i<suppliers.size(); i++){
            listView.getItems().add(i, suppliers.get(i));

        }
        VBox vBox = new VBox(listView);
        Scene scene = new Scene(vBox, 800,600);
        Stage stage = new Stage();
        stage.setTitle("Все отчеты");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void DeleteReport_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminDeleteReport.fxml")));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void UpdateReport_Pressed(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Не готов");
        alert.setContentText("Данный раздел еще не готов");
        alert.showAndWait();
    }

    public void Back_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminMenu.fxml")));
            Scene scene = new Scene(blah);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}

package main.Controllers.Accountant;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Enums.RequestCRUD;
import main.Models.Entities.Report;
import main.Models.TCP.Request;
import main.Models.TCP.Response;
import main.Utility.Connect;
import main.Utility.SessionDataHolder;

import java.io.IOException;
import java.util.Objects;

import static main.Enums.RequestType.ADMINMENUREPORTS;
import static main.Enums.ResponseStatus.ERROR;
import static main.Enums.ResponseStatus.OK;

public class CreateReport {

    public TextField TypeField;
    public TextArea DescriptionArea;

    public void Create_Pressed(ActionEvent actionEvent) {
        Report report = new Report();

        report.setDescription(DescriptionArea.getText());
        report.setType(TypeField.getText());

        SessionDataHolder holder = SessionDataHolder.getInstance();
        report.setUser(holder.getSessionData().getUser());


        //Отправка данных
        Request requestModel = new Request();
        requestModel.setRequestMessage(new Gson().toJson(report));
        requestModel.setRequestType(ADMINMENUREPORTS);
        requestModel.setRequestCRUD(RequestCRUD.ADD);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Response response;
        Gson gson= new Gson();
        response = gson.fromJson(message, Response.class);
        if(response.qetResponseStatus() == OK){
            System.out.println("ОК");

            try {
                Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AccountantMenu.fxml")));
                Scene scene = new Scene(blah, 800 , 600);
                Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                appStage.setScene(scene);
                appStage.show();
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
        else if(response.qetResponseStatus() == ERROR) {
            System.out.println("Ошибка");
        }
        else {
            System.out.println("Чтото непредвиденное");
        }
    }

    public void Back_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AccountantMenu.fxml")));
            Scene scene = new Scene(blah, 800 , 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}

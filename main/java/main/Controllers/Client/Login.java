package main.Controllers.Client;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Enums.RequestType;
import main.Models.Entities.Client;
import main.Models.Entities.User;
import main.Models.TCP.Request;
import main.Models.TCP.Response;
import main.Utility.Connect;
import main.Utility.SessionData;
import main.Utility.SessionDataHolder;

import java.io.IOException;
import java.util.Objects;

import static main.Enums.ResponseStatus.ERROR;
import static main.Enums.ResponseStatus.OK;

public class Login {
    public PasswordField passwordField;
    public TextField loginField;

    public void Enterbtn_Pressed(ActionEvent actionEvent) {

        Client client = new Client();

        client.setLogin(loginField.getText());
        client.setPassword(passwordField.getText());


        Request requestModel = new Request();
        requestModel.setRequestMessage(new Gson().toJson(client));
        requestModel.setRequestType(RequestType.CLIENTLOGIN);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        String message = Connect.clientCon.readObject().toString();
        Response response;
        Gson gson= new Gson();
        response = gson.fromJson(message, Response.class);
        if(response.qetResponseStatus() == OK){
            System.out.println("ОК");
            try {

                Client responseclient = new Client();
                responseclient = gson.fromJson(response.getResponseMessage(), Client.class);
                SessionData sessionData = new SessionData();
                sessionData.setClient(responseclient);

                SessionDataHolder holder = SessionDataHolder.getInstance();
                holder.setSessionData(sessionData);

                Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ClientMenu.fxml")));
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
            System.out.println("Проблема");
        }
        else {
            System.out.println("Чтото непредвиденное");
        }
    }

    public void Back_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("index.fxml")));
            Scene scene = new Scene(blah, 800 , 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void ToReg_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ClientRegistration.fxml")));
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

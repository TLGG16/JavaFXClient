package main.Controllers.Accountant;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Enums.RequestType;
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
        User user = new User();

        if (loginField.getText().isEmpty() || passwordField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Вы не ввели логин или пароль");
            alert.setContentText("");
            alert.showAndWait();
            System.out.println("Проблема");
            return;
        }

        user.setLogin(loginField.getText());
        user.setPassword(passwordField.getText());
        user.setRole("accountant");
        user.setPerson(null);

        Request requestModel = new Request();
        requestModel.setRequestMessage(new Gson().toJson(user));
        requestModel.setRequestType(RequestType.ACCOUNTANTLOGIN);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        String message = Connect.clientCon.readObject().toString();
        Response response;
        Gson gson= new Gson();
        response = gson.fromJson(message, Response.class);
        if(response.qetResponseStatus() == OK){
            System.out.println("ОК");
            try {
                User responseuser = new User();
                responseuser = gson.fromJson(response.getResponseMessage(), User.class);
                SessionData sessionData = new SessionData(responseuser);

                SessionDataHolder holder = SessionDataHolder.getInstance();

                holder.setSessionData(sessionData);

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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Неправильный логин или пароль");
            alert.setContentText("");
            alert.showAndWait();
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
}

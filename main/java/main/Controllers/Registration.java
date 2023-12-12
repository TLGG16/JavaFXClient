package main.Controllers;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Models.Entities.User;
import main.Models.TCP.Request;
import main.Models.TCP.Response;
import main.Utility.Connect;

import static main.Enums.RequestType.REGISTER;
import static main.Enums.ResponseStatus.OK;
import static main.Enums.ResponseStatus.ERROR;

import java.io.IOException;
import java.util.Objects;

public class Registration {
    public PasswordField passwordFieldPassword;
    public TextField textFieldLogin;
    public Button buttonRegister;
    public Button buttonGotoLogin;

    public void Register_Pressed(ActionEvent actionEvent) {
        User user = new User();

        user.setLogin(textFieldLogin.getText());
        user.setPassword(passwordFieldPassword.getText());

        //Отправка данных
        Request requestModel = new Request();
        requestModel.setRequestMessage(new Gson().toJson(user));
        requestModel.setRequestType(REGISTER);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Response response;
        Gson gson= new Gson();
        response = gson.fromJson(message, Response.class);
        if(response.qetResponseStatus() == OK){
            System.out.println("ОК");

            try {
                Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Login.fxml")));
                Scene scene = new Scene(blah, 400 , 600);
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

    public void GotoLogin_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Login.fxml")));
            Scene scene = new Scene(blah, 400, 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

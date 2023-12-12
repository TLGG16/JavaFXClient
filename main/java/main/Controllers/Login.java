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

import static main.Enums.RequestType.LOGIN;
import static main.Enums.ResponseStatus.OK;
import static main.Enums.ResponseStatus.ERROR;

import java.io.IOException;
import java.util.Objects;


public class Login {
    public PasswordField passwordFieldPassword;
    public TextField textFieldLogin;
    public Button buttonRegister;
    public Button buttonLogin;
    public Button buttonGotoReg;


    public void Login_Pressed(ActionEvent actionEvent) {
        User user = new User();

        if (textFieldLogin.getText().isEmpty() || passwordFieldPassword.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Вы не ввели логин или пароль");
            alert.setContentText("");
            alert.showAndWait();
            System.out.println("Проблема");
            return;
        }

        user.setLogin(textFieldLogin.getText());
        user.setPassword(passwordFieldPassword.getText());

        Request requestModel = new Request();
        requestModel.setRequestMessage(new Gson().toJson(user));
        requestModel.setRequestType(LOGIN);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        String message = Connect.clientCon.readObject().toString();
        Response response;
        Gson gson= new Gson();
        response = gson.fromJson(message, Response.class);
        if(response.qetResponseStatus() == OK){
            System.out.println("ОК");

//            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
//            primaryStage.setTitle("Condorcet");
//            primaryStage.setScene(new Scene(root, 300, 275));
//            primaryStage.show();
            try {


                Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("PlaceHolder.fxml")));
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

    public void GotoReg_Pressed(ActionEvent actionEvent) {
        try {
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Register.fxml")));
            Scene scene = new Scene(blah, 400, 600);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

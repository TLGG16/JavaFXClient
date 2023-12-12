package main.Controllers.Admin.Accounts;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Enums.RequestCRUD;
import main.Models.Entities.Client;
import main.Models.Entities.Person;
import main.Models.TCP.Request;
import main.Models.TCP.Response;
import main.Utility.Connect;

import java.io.IOException;
import java.util.Objects;

import static main.Enums.RequestType.ADMINMENUACCOUNTSCLIENTS;
import static main.Enums.ResponseStatus.ERROR;
import static main.Enums.ResponseStatus.OK;

public class CreateClient {
    public TextField EmailField;
    public TextField PhoneField;
    public TextField LoginField;
    public TextField PasswordField;
    public Button Createbtn;
    public TextField AddressField;
    public TextField NameField;
    public TextField SurnameField;

    public void Create_Pressed(ActionEvent actionEvent) {
        Client client = new Client();
        Person person = new Person();

        person.setName(NameField.getText());
        person.setSurname(SurnameField.getText());
        client.setPerson(person);
        client.setLogin(LoginField.getText());
        client.setPassword(PasswordField.getText());
        client.setAddress(AddressField.getText());
        client.setEmail(EmailField.getText());
        client.setPhonenumber(PhoneField.getText());


        //Отправка данных
        Request requestModel = new Request();
        requestModel.setRequestMessage(new Gson().toJson(client));
        requestModel.setRequestType(ADMINMENUACCOUNTSCLIENTS);
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
                Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminMenu.fxml")));
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
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminMenuAccounts.fxml")));
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

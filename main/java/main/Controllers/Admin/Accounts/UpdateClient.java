package main.Controllers.Admin.Accounts;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Enums.RequestCRUD;
import main.Models.Entities.Client;
import main.Models.Entities.Person;
import main.Models.Entities.User;
import main.Models.TCP.Request;
import main.Models.TCP.Response;
import main.Utility.Connect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import static main.Enums.RequestType.ADMINMENUACCOUNTSADMINS;
import static main.Enums.RequestType.ADMINMENUACCOUNTSCLIENTS;
import static main.Enums.ResponseStatus.ERROR;
import static main.Enums.ResponseStatus.OK;

public class UpdateClient {
    public TextField EmailField;
    public TextField PhoneField;
    public TextField LoginField;
    public TextField PasswordField;
    public Button Updatebtn;
    public TextField AddressField;
    public TextField NameField;
    public TextField SurnameField;
    public TextField IDField;
    public TableView<Client> infTable;
    public TableColumn<Client, Integer> IDCollumn;
    public TableColumn<Client, String> NameCollumn;
    public TableColumn<Client, String> SurnameCollumn;
    public TableColumn<Client, String> EmailCollumn;
    public TableColumn<Client, String> PhoneCollumn;
    public TableColumn<Client, String> AddressCollumn;

    @FXML
    void initialize(){
        IDCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getId()));
        PhoneCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPhonenumber()));
        NameCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPerson().getName()));
        SurnameCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPerson().getSurname()));
        EmailCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getEmail()));
        AddressCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getAddress()));

        Request requestModel = new Request();
        requestModel.setRequestType(ADMINMENUACCOUNTSCLIENTS);
        requestModel.setRequestCRUD(RequestCRUD.SHOWALL);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<Client>>(){}.getType();
        List<Client> clients = gson.fromJson(message, type);
        ObservableList<Client> ClientList = FXCollections.observableArrayList();
        if (ClientList!=null){
            ClientList.addAll(clients);
            infTable.setItems(ClientList);
            infTable.refresh();
        }
        else {
            System.out.println("ClientList is null");
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

    public void Update_Pressed(ActionEvent actionEvent) {
        Client client = new Client();
        Person person = new Person();

        person.setName(NameField.getText());
        person.setSurname(SurnameField.getText());

        client.setPerson(person);
        client.setId(Integer.parseInt(IDField.getText()));
        client.setLogin(LoginField.getText());
        client.setPassword(PasswordField.getText());
        client.setPhonenumber(PhoneField.getText());
        client.setEmail(EmailField.getText());
        client.setAddress(AddressField.getText());


        //Отправка данных
        Request requestModel = new Request();
        requestModel.setRequestMessage(new Gson().toJson(client));
        requestModel.setRequestType(ADMINMENUACCOUNTSCLIENTS);
        requestModel.setRequestCRUD(RequestCRUD.UPDATE);
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
}

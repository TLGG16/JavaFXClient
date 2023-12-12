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
import main.Models.Entities.Person;
import main.Models.Entities.User;
import main.Models.TCP.Request;
import main.Models.TCP.Response;
import main.Utility.Connect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static main.Enums.RequestType.ADMINMENUACCOUNTSACCOUNTANTS;
import static main.Enums.RequestType.ADMINMENUACCOUNTSADMINS;
import static main.Enums.ResponseStatus.ERROR;
import static main.Enums.ResponseStatus.OK;

public class UpdateAdmin {
    public TextField NameField;
    public TextField SurnameField;
    public TextField LoginField;
    public TextField PasswordField;
    public Button Updatebtn;
    public Button Backbtn;
    public TextField IDField;
    public TableView<User> infTable;
    public TableColumn<User,Integer> IDCollumn;
    public TableColumn<User, String> RoleCollumn;
    public TableColumn<User, String> NameCollumn;
    public TableColumn<User, String> SurnameCollumn;

    @FXML
    void initialize(){
        IDCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getId()));
        RoleCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getRole()));
        NameCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPerson().getName()));
        SurnameCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPerson().getSurname()));

        Request requestModel = new Request();
        requestModel.setRequestType(ADMINMENUACCOUNTSADMINS);
        requestModel.setRequestCRUD(RequestCRUD.SHOWALL);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<User>>(){}.getType();
        ArrayList<User> list = new ArrayList<>();
        List<User> users = gson.fromJson(message, type);
        int j =0;
        for (int i =0; i<users.size(); i++){
            if (users.get(i).getRole().toLowerCase().equals("admin")) {
                list.add(j, users.get(i));
                j++;
            }
        }
        ObservableList<User> UserList = FXCollections.observableArrayList();
        if (UserList!=null){
            UserList.addAll(list);
            infTable.setItems(UserList);
            infTable.refresh();
        }
        else {
            System.out.println("UserList is null");
        }


    }

    public void Update_Pressed(ActionEvent actionEvent) {
        User user = new User();
        Person person = new Person();

        person.setName(NameField.getText());
        person.setSurname(SurnameField.getText());

        user.setPerson(person);
        user.setId(Integer.parseInt(IDField.getText()));
        user.setLogin(LoginField.getText());
        user.setPassword(PasswordField.getText());
        user.setRole("admin");


        //Отправка данных
        Request requestModel = new Request();
        requestModel.setRequestMessage(new Gson().toJson(user));
        requestModel.setRequestType(ADMINMENUACCOUNTSADMINS);
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

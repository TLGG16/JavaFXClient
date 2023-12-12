package main.Controllers.Admin.Suppliers;

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
import main.Models.Entities.Supplier;
import main.Models.TCP.Request;
import main.Models.TCP.Response;
import main.Utility.Connect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import static main.Enums.RequestType.ADMINMENUACCOUNTSCLIENTS;
import static main.Enums.RequestType.ADMINMENUSUPPLIERS;
import static main.Enums.ResponseStatus.ERROR;
import static main.Enums.ResponseStatus.OK;

public class Delete {


    public TextField IDField;
    public Button Deletebtn;
    public TableView<Supplier> infTable;
    public TableColumn<Supplier, Integer> IDCollumn;
    public TableColumn<Supplier, String> NameCollumn;
    public TableColumn<Supplier, String> PhoneCollumn;
    public TableColumn<Supplier, String> AddressCollumn;

    @FXML
    void initialize(){
        IDCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getId()));
        PhoneCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPhone()));
        NameCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getName()));
        AddressCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getAddress()));

        Request requestModel = new Request();
        requestModel.setRequestType(ADMINMENUSUPPLIERS);
        requestModel.setRequestCRUD(RequestCRUD.SHOWALL);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<Supplier>>(){}.getType();
        List<Supplier> suppliers = gson.fromJson(message, type);
        ObservableList<Supplier> suplierlist = FXCollections.observableArrayList();
        if (suplierlist!=null){
            suplierlist.addAll(suppliers);
            infTable.setItems(suplierlist);
            infTable.refresh();
        }
        else {
            System.out.println("supplierlist is null");
        }


    }


    public void Delete_Pressed(ActionEvent actionEvent) {
        Request requestModel = new Request();
        requestModel.setRequestMessage(IDField.getText());
        requestModel.setRequestType(ADMINMENUSUPPLIERS);
        requestModel.setRequestCRUD(RequestCRUD.DELETE);
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
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminMenuSuppliers.fxml")));
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

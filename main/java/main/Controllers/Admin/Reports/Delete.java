package main.Controllers.Admin.Reports;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Enums.RequestCRUD;
import main.Models.Entities.Report;
import main.Models.Entities.Supplier;
import main.Models.TCP.Request;
import main.Models.TCP.Response;
import main.Utility.Connect;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import static main.Enums.RequestType.ADMINMENUREPORTS;
import static main.Enums.RequestType.ADMINMENUSUPPLIERS;
import static main.Enums.ResponseStatus.ERROR;
import static main.Enums.ResponseStatus.OK;

public class Delete {
    public TextField IDField;
    public TableView<Report> infTable;
    public TableColumn<Report , Integer> IDCollumn;
    public TableColumn<Report, String> TypeCollumn;
    public TableColumn<Report, String> DescriptionCollumn;
    public TableColumn<Report, String> UserNameCollumn;
    public TableColumn<Report, String> RoleCollumn;

    @FXML
    void initialize(){
        IDCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getReport_id()));
        TypeCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getType()));
        DescriptionCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getDescription()));
        UserNameCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getUser().getLogin()));
        RoleCollumn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getUser().getRole()));


        Request requestModel = new Request();
        requestModel.setRequestType(ADMINMENUREPORTS);
        requestModel.setRequestCRUD(RequestCRUD.SHOWALL);
        Connect.clientCon.sendMessage(new Gson().toJson(requestModel));

        // Чтение данных
        String message = Connect.clientCon.readObject().toString();
        Gson gson= new Gson();
        Type type = new TypeToken<List<Report>>(){}.getType();
        List<Report> reports = gson.fromJson(message, type);
        ObservableList<Report> reportlist = FXCollections.observableArrayList();
        if (reportlist!=null){
            reportlist.addAll(reports);
            infTable.setItems(reportlist);
            infTable.refresh();
        }
        else {
            System.out.println("reportlist is null");
        }


    }

    public void Delete_Pressed(ActionEvent actionEvent) {
        Request requestModel = new Request();
        requestModel.setRequestMessage(IDField.getText());
        requestModel.setRequestType(ADMINMENUREPORTS);
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
            Parent blah = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AdminMenuReports.fxml")));
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

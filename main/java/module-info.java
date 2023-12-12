module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;



    opens main.Models.Entities to com.google.gson;
    opens main.Models.TCP to com.google.gson;
    opens main.Enums to com.google.gson;
    opens main.Utility to com.google.gson;

    opens main to javafx.fxml;
    opens main.Controllers to javafx.fxml;
    opens main.Controllers.Admin to javafx.fxml;
    opens main.Controllers.Admin.Products to javafx.fxml;
    opens main.Controllers.Admin.Suppliers to javafx.fxml;
    opens main.Controllers.Admin.Accounts to javafx.fxml;
    opens main.Controllers.Admin.Orders to javafx.fxml;
    opens main.Controllers.Admin.Reports to javafx.fxml;
    opens main.Controllers.Admin.Reviews to javafx.fxml;
    opens main.Controllers.Client to javafx.fxml;
    opens main.Controllers.Client.Review to javafx.fxml;
    opens main.Controllers.Client.Order to javafx.fxml;
    opens main.Controllers.Accountant to javafx.fxml;



    exports main;
    exports main.Controllers;
    exports main.Controllers.Admin;
    exports main.Controllers.Admin.Accounts;
    exports main.Controllers.Admin.Suppliers;
    exports main.Controllers.Admin.Products;
    exports main.Controllers.Admin.Orders;
    exports main.Controllers.Admin.Reports;
    exports main.Controllers.Admin.Reviews;
    exports main.Controllers.Client;
    exports main.Controllers.Client.Review;
    exports main.Controllers.Client.Order;
    exports main.Utility;




}
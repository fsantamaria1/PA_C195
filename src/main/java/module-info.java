module santamaria.pa_c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens santamaria.pa_c195 to javafx.fxml;
    exports santamaria.pa_c195;
    opens helper to javafx.fxml;
    exports helper;
    exports controllers;
    opens controllers to javafx.fxml;
    exports DbAcessObjects;
    opens DbAcessObjects to javafx.fxml;
    exports models;
    opens models to javafx.fxml;
}
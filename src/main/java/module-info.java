module santamaria.pa_c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
//    requires mysql.connector.java;


    opens santamaria.pa_c195 to javafx.fxml;
    exports santamaria.pa_c195;
    opens helper to javafx.fxml;
    exports helper;
}
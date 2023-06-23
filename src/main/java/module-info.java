module platebasedisplayer {
    requires javafx.controls;
    requires javafx.fxml;

    exports hu.mvmxpert.david.giczi.pcc.displayers.platebasedisplayer;
    opens hu.mvmxpert.david.giczi.pcc.displayers.platebasedisplayer to javafx.fxml;
}
module weightbasedisplayer {
    requires javafx.controls;
    requires javafx.fxml;

    exports hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer;
    opens hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer to javafx.fxml;
}
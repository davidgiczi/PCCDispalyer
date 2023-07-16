package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InputPillarDataWindow {

    private final Color textColor = Color.rgb(112,128,144);

    public InputPillarDataWindow(){
        Stage stage = new Stage();
        AnchorPane pane = new AnchorPane();
        pane.setStyle("-fx-background-color: white");
        Scene scene = new Scene(pane);
        stage.setWidth(400);
        stage.setHeight(500);
        stage.setTitle(FileProcess.PROJECT_FILE_NAME);
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

}

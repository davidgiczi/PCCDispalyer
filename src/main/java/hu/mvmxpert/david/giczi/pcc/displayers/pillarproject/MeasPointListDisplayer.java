package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import hu.mvmxpert.david.giczi.pcc.displayers.model.MeasPoint;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

public class MeasPointListDisplayer {


    public MeasPointListDisplayer(List<MeasPoint> measPointList){
        Stage stage = new Stage();
        AnchorPane pane = new AnchorPane();
        pane.setStyle("-fx-background-color: white");
        Scene scene = new Scene(pane);
        stage.setTitle(FileProcess.PROJECT_FILE_NAME + " projekt");
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setWidth(400);
        stage.setHeight(100);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private HBox getMeasData(MeasPoint measPointData){
        HBox hbox = new HBox();

        return hbox;
    }

}

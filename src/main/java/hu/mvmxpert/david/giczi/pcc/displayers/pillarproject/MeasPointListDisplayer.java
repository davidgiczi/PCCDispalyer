package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import hu.mvmxpert.david.giczi.pcc.displayers.model.MeasPoint;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class MeasPointListDisplayer {

    private final AnchorPane pane;
    private  VBox vBox;
    private final int[] clickValue;

    public MeasPointListDisplayer(List<MeasPoint> measPointList){
        clickValue = new int[measPointList.size()];
        vBox = new VBox();
        Stage stage = new Stage();
        stage.setWidth(430);
        stage.setMaxHeight(600);
        pane = new AnchorPane();
        pane.setStyle("-fx-background-color: white");
        addMeasData(measPointList);
        addButton();
        pane.getChildren().add(vBox);
        ScrollPane scrollPane = getScrollPane(pane);
        Scene scene = new Scene(scrollPane);
        stage.setTitle(FileProcess.PROJECT_FILE_NAME + " projekt");
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void addMeasData(List<MeasPoint> measPointList){
        for (MeasPoint measPont: measPointList) {
            HBox hbox = new HBox(20);
            hbox.setPadding(new Insets(10, 10, 10, 10));
            hbox.setCursor(Cursor.HAND);
            hbox.setStyle("-fx-border-color: lightgray");
            hbox.setOnMouseClicked(mouseEvent -> {
                int rowIndex = vBox.getChildren().indexOf((Node) mouseEvent.getSource());
               if(clickValue[rowIndex] == 5){
                   clickValue[rowIndex] = 1;
               }
               else{
                   clickValue[rowIndex]++;
               }
                hbox.setBackground(new Background(
                        new BackgroundFill(getColorValue(rowIndex), null, null)));
            });
            Text measID = new Text(measPont.getPointID());
            Text x = new Text(String.format("%.3f", measPont.getX_coord()).replace(",", "."));
            Text y = new Text(String.format("%.3f", measPont.getY_coord()).replace(",", "."));
            Text z = new Text(String.format("%.3f", measPont.getZ_coord()).replace(",", "."));
            Text type = new Text(measPont.getPointType().name());
            CheckBox check = new CheckBox("Használ");
            check.setSelected(true);
            hbox.getChildren().addAll(measID, x, y, z, type, check);
            vBox.getChildren().add(hbox);
        }
    }

    private void addButton(){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        Button calcBtn = new Button("Számol");
        calcBtn.setCursor(Cursor.HAND);
        hBox.getChildren().add(calcBtn);
        vBox.getChildren().add(hBox);
    }

    private Color getColorValue(int rowIndex) {

        switch (clickValue[rowIndex]) {
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.PINK;
            case 3:
                return Color.CORNFLOWERBLUE;
            case 4 :
                return  Color.TOMATO;
        }
        return Color.TRANSPARENT;
    }
    private ScrollPane getScrollPane(AnchorPane content){
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scroller;
    }
}

package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import hu.mvmxpert.david.giczi.pcc.displayers.model.MeasPoint;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;


public class MeasPointListDisplayer {

    public Stage stage;
    private final AnchorPane pane;
    private final  VBox vBoxForMeasuredData;
    private final int[] clickValue;
    private final MeasuredPillarDataController measuredPillarDataController;
    private final Font font = Font.font("Arial", FontWeight.BOLD, 13);

    public MeasPointListDisplayer(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        clickValue = new int[measuredPillarDataController
                .measuredPillarData.getMeasPillarPoints().size()];
        vBoxForMeasuredData = new VBox();
        stage = new Stage();
        pane = new AnchorPane();
        pane.setStyle("-fx-background-color: white");
        addInstructionButtons();
        addMeasData(measuredPillarDataController.measuredPillarData.getMeasPillarPoints());
        pane.getChildren().add(vBoxForMeasuredData);
        Scene scene = new Scene(getScrollPane());
        stage.setOnCloseRequest(windowEvent -> measuredPillarDataController.init());
        stage.setWidth(510);
        stage.setHeight(600);
        stage.setTitle(FileProcess.MEAS_FILE_NAME);
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void parseDisplayerData(){
            for (int i = 3; i < vBoxForMeasuredData.getChildren().size(); i++ ) {
          measuredPillarDataController.measuredPillarData.getMeasPillarPoints().get(i - 3).setGroupID(clickValue[i - 3]);
                HBox hbox = (HBox) vBoxForMeasuredData.getChildren().get(i);
                for( int j = 0; j < hbox.getChildren().size(); j++) {
                    if ( hbox.getChildren().get(j) instanceof CheckBox ) {
                     measuredPillarDataController.measuredPillarData.getMeasPillarPoints()
                                .get(i - 3).setUsed(((CheckBox) hbox.getChildren().get(j)).isSelected());
                    }
                }
            }
        }
        private void addInstructionButtons(){
        Button createProjectButton = new Button("Új projekt létrehozása");
        createProjectButton.setFont(font);
        createProjectButton.setCursor(Cursor.HAND);
        createProjectButton.setPrefWidth(480);
        createProjectButton.setPrefHeight(30);
        createProjectButton.setOnMouseClicked(e -> {
            parseDisplayerData();
            measuredPillarDataController.createNewProject();
              });
        Button addMoreMeasuredDataButton = new Button("További mérési adatok hozzáadása");
        addMoreMeasuredDataButton.setFont(font);
        addMoreMeasuredDataButton.setCursor(Cursor.HAND);
        addMoreMeasuredDataButton.setPrefWidth(480);
        addMoreMeasuredDataButton.setPrefHeight(30);
        addMoreMeasuredDataButton.setOnMouseClicked(e -> {
            measuredPillarDataController.addMoreMeasuredPillarData();
        });
        Button addNewMeasuredDataButton = new Button("Új mérési adatok hozzáadása");
        addNewMeasuredDataButton.setFont(font);
        addNewMeasuredDataButton.setCursor(Cursor.HAND);
        addNewMeasuredDataButton.setPrefWidth(480);
        addNewMeasuredDataButton.setPrefHeight(30);
        addNewMeasuredDataButton.setOnMouseClicked(e -> {
        measuredPillarDataController.init();
        measuredPillarDataController.openNewMeasuredPillarData();
        });
        vBoxForMeasuredData.getChildren().addAll(createProjectButton,
                addMoreMeasuredDataButton, addNewMeasuredDataButton);

         }
    private void addMeasData(List<MeasPoint> measPointList){
        for (MeasPoint measPont: measPointList) {
            HBox hbox = new HBox(20);
            hbox.setPadding(new Insets(10, 10, 10, 10));
            hbox.setCursor(Cursor.HAND);
            hbox.setStyle("-fx-border-color: lightgray");
            hbox.setOnMouseClicked(mouseEvent -> {
                int rowIndex = vBoxForMeasuredData.getChildren().indexOf((Node) mouseEvent.getSource()) - 3;
                clickValue[rowIndex]++;
                hbox.setBackground(new Background(
                        new BackgroundFill(getColorValue(rowIndex), null, null)));
                if( clickValue[rowIndex] == 5 ){
                    clickValue[rowIndex] = 0;
                }
            });
            Text measID = new Text(measPont.getPointID());
            measID.setFont(font);
            Text x = new Text(String.format("%.3f", measPont.getX_coord()).replace(",", "."));
            x.setFont(font);
            Text y = new Text(String.format("%.3f", measPont.getY_coord()).replace(",", "."));
            y.setFont(font);
            Text z = new Text(String.format("%.3f", measPont.getZ_coord()).replace(",", "."));
            z.setFont(font);
            Text type = new Text(measPont.getPointType().name());
            type.setFont(font);
            CheckBox check = new CheckBox("Használ");
            check.setFont(font);
            check.setSelected(true);
            hbox.getChildren().addAll(measID, x, y, z, type, check);
            vBoxForMeasuredData.getChildren().add(hbox);
        }
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

    private ScrollPane getScrollPane(){
        ScrollPane scroller = new ScrollPane(pane);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        return scroller;
    }
}

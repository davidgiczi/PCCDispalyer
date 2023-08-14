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


public class MeasPointListDisplayer {

    public Stage stage;
    private final GridPane root;
    private VBox measDataVBox;
    private ScrollPane scrollPane;
    private int[] clickValues;
    private final MeasuredPillarDataController measuredPillarDataController;
    private final Font font = Font.font("Arial", FontWeight.BOLD, 13);

    public MeasPointListDisplayer(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        root = new GridPane();
        addInstructionButtons();
        addMeasData();
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.initOwner(measuredPillarDataController.fxHomeWindow.homeStage);
        stage.setOnCloseRequest(windowEvent -> measuredPillarDataController.init());
        stage.setWidth(510);
        stage.setHeight(600);
        stage.setTitle(FileProcess.MEAS_FILE_NAME);
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void parseDisplayerData(){
            for (int i = 0; i < measuredPillarDataController.measuredPillarData.getMeasPillarPoints().size(); i++ ) {
                    measuredPillarDataController.measuredPillarData
                  .getMeasPillarPoints().get(i).setGroupID(clickValues[i]);
                HBox hbox = (HBox) measDataVBox.getChildren().get(i);
                for( int j = 0; j < hbox.getChildren().size(); j++) {
                    if ( hbox.getChildren().get(j) instanceof CheckBox ) {
                     measuredPillarDataController.measuredPillarData.getMeasPillarPoints()
                                .get(i).setUsed(((CheckBox) hbox.getChildren().get(j)).isSelected());
                    }
                }
            }
        }
        private void addInstructionButtons(){
        Button createProjectButton = new Button("Új projekt létrehozása");
        createProjectButton.setFont(font);
        createProjectButton.setCursor(Cursor.HAND);
        createProjectButton.setPrefWidth(495);
        createProjectButton.setPadding(new Insets(10, 10, 10, 10));
        createProjectButton.setOnMouseClicked(e -> {
            measuredPillarDataController.createNewProject();
              });
        Button addMoreMeasuredDataButton = new Button("További mérési adatok hozzáadása");
        addMoreMeasuredDataButton.setFont(font);
        addMoreMeasuredDataButton.setCursor(Cursor.HAND);
        addMoreMeasuredDataButton.setPrefWidth(495);
        addMoreMeasuredDataButton.setPadding(new Insets(10, 10, 10, 10));
        addMoreMeasuredDataButton.setOnMouseClicked(e -> {
            measuredPillarDataController.addMoreMeasuredPillarData();
        });
        Button addNewMeasuredDataButton = new Button("Új mérési adatok lista létrehozása");
        addNewMeasuredDataButton.setFont(font);
        addNewMeasuredDataButton.setCursor(Cursor.HAND);
        addNewMeasuredDataButton.setPrefWidth(495);
        addNewMeasuredDataButton.setPadding(new Insets(10, 10, 10, 10));
        addNewMeasuredDataButton.setOnMouseClicked(e -> {
        measuredPillarDataController.init();
        measuredPillarDataController.openNewMeasuredPillarData();
        });
        root.add(createProjectButton, 0, 0);
        root.add(addMoreMeasuredDataButton, 0, 1);
        root.add(addNewMeasuredDataButton, 0, 2);
    }


    public void addMeasData() {

        clickValues = new int[measuredPillarDataController.measuredPillarData.getMeasPillarPoints().size()];
        measDataVBox = new VBox();
        measDataVBox.setStyle("-fx-background-color: white");
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        for (int i = 0; i < measuredPillarDataController
                .measuredPillarData.getMeasPillarPoints().size(); i++) {
            MeasPoint measPoint = measuredPillarDataController
                    .measuredPillarData.getMeasPillarPoints().get(i);
            clickValues[i] = measPoint.getGroupID();
            HBox hbox = new HBox(20);
            hbox.setPadding(new Insets(10, 10, 10, 10));
            hbox.setCursor(Cursor.HAND);
            hbox.setStyle("-fx-border-color: lightgray");
            if( measPoint.getGroupID() == 0 && i % 2 != 0 ){
                hbox.setBackground( new Background(
                        new BackgroundFill(Color.rgb(211, 211, 211),
                                null, null)));
            }
            else if( measPoint.getGroupID() != 0 ){
                hbox.setBackground(
                        new Background(
                                new BackgroundFill(getColorValue(i),
                                        null, null)));
            }
            CheckBox check = new CheckBox("Használ");
            hbox.setOnMouseClicked(mouseEvent -> {
                if( !check.isSelected() ){
                    return;
                }
                int rowIndex = measDataVBox.getChildren().indexOf((Node) mouseEvent.getSource());
                clickValues[rowIndex]++;
                hbox.setBackground(new Background(
                        new BackgroundFill(getColorValue(rowIndex), null, null)));
                if ( clickValues[rowIndex] == 5 ) {
                    clickValues[rowIndex] = 0;
                }
            });
            Text measID = new Text(measPoint.getPointID());
            measID.setFont(font);
            Text x = new Text(String.format("%.3f", measPoint.getX_coord()).replace(",", "."));
            x.setFont(font);
            Text y = new Text(String.format("%.3f", measPoint.getY_coord()).replace(",", "."));
            y.setFont(font);
            Text z = new Text(String.format("%.3f", measPoint.getZ_coord()).replace(",", "."));
            z.setFont(font);
            Text type = new Text(measPoint.getPointType().name());
            type.setFont(font);
            if( measPoint.isUsed() ){
                check.setSelected(true);
            }
            check.setFont(font);
            check.setOnMouseClicked(mouseEvent -> {
                if ( !check.isSelected() ) {
                    int rowIndex = measDataVBox.getChildren().indexOf((Node) hbox);
                    clickValues[rowIndex] = 0;
                    if( rowIndex % 2 == 0) {
                        hbox.setBackground(
                                new Background(
                                        new BackgroundFill(Color.TRANSPARENT,
                                                null, null)));
                    } else {
                        hbox.setBackground(
                                new Background(
                                        new BackgroundFill(Color.rgb(211, 211, 211),
                                                null, null)));
                    }
                }
            });
            hbox.getChildren().addAll(measID, x, y, z, type, check);
            measDataVBox.getChildren().add(hbox);
        }
        scrollPane.setContent(measDataVBox);
       if( root.getChildren().size() == 4 ){
            root.getChildren().remove(3);
        }
        root.add(scrollPane, 0, 4);
    }

    private Color getColorValue(int rowIndex) {

        switch (clickValues[rowIndex]) {
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.PINK;
            case 3:
                return Color.CORNFLOWERBLUE;
            case 4 :
                return  Color.TOMATO;
        }
        return rowIndex % 2 == 0 ? Color.TRANSPARENT : Color.rgb(211, 211, 211);
    }

}

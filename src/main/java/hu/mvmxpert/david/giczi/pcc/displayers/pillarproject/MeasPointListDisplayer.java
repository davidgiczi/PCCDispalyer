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

import java.util.ArrayList;
import java.util.List;


public class MeasPointListDisplayer {

    public Stage stage;
    private final GridPane root;
    private final VBox measDataVBox;
    private final ScrollPane scrollPane;
    private final List<Integer> clickValues;
    private final MeasuredPillarDataController measuredPillarDataController;
    private final Font font = Font.font("Arial", FontWeight.BOLD, 13);

    public MeasPointListDisplayer(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;

        clickValues = new ArrayList<>();
        initClickValues();
        measDataVBox = new VBox();
        root = new GridPane();
        addInstructionButtons();
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        addMeasData();
        scrollPane.setContent(measDataVBox);
        root.add(scrollPane, 0, 4);
        measDataVBox.setStyle("-fx-background-color: white");
        Scene scene = new Scene(root);

        stage = new Stage();
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
            for (int i = 0; i < measDataVBox.getChildren().size(); i++ ) {
          measuredPillarDataController.measuredPillarData
                  .getMeasPillarPoints().get(i).setGroupID(clickValues.get(i));
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
            parseDisplayerData();
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

    public void initClickValues() {
        for (int i = measuredPillarDataController.measuredPillarData.getMeasPillarPoints().size();
             i < measuredPillarDataController.measuredPillarData.getMeasPillarPoints().size() +
                     measuredPillarDataController.fileProcess.getMeasData().size(); i++) {
            clickValues.add(0);
        }
    }

    public void addMeasData() {
        for (int i = 0; i < measuredPillarDataController
                .measuredPillarData.getMeasPillarPoints().size(); i++) {
            MeasPoint measPoint = measuredPillarDataController
                    .measuredPillarData.getMeasPillarPoints().get(i);
            HBox hbox = new HBox(20);
            hbox.setPadding(new Insets(10, 10, 10, 10));
            hbox.setCursor(Cursor.HAND);
            hbox.setStyle("-fx-border-color: lightgray");
            if( i % 2 != 0){
                hbox.setBackground( new Background(
                        new BackgroundFill(Color.rgb(211, 211, 211),
                                null, null)));
            }
            else {
                hbox.setBackground(
                        new Background(
                                new BackgroundFill(getColorValue(measPoint.getGroupID()),
                                        null, null)));
            }
            hbox.setOnMouseClicked(mouseEvent -> {
                int rowIndex = measDataVBox.getChildren().indexOf((Node) mouseEvent.getSource());
                clickValues.set(rowIndex, clickValues.get(rowIndex) + 1);
                hbox.setBackground(new Background(
                        new BackgroundFill(getColorValue(rowIndex), null, null)));
                if ( clickValues.get(rowIndex) == 5 ) {
                    clickValues.set(rowIndex, 0);
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
            CheckBox check = new CheckBox("Használ");
            if( measPoint.isUsed() ){
                check.setSelected(true);
            }
            check.setFont(font);
            check.setOnMouseClicked(mouseEvent -> {
                if ( !check.isSelected() ) {
                    int rowIndex = measDataVBox.getChildren().indexOf((Node) hbox);
                    clickValues.set(rowIndex, 0);
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
    }

    private Color getColorValue(int rowIndex) {

        switch (clickValues.get(rowIndex)) {
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

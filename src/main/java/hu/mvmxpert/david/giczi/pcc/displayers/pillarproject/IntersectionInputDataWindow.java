package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IntersectionInputDataWindow {


    public Stage stage;
    private final MeasuredPillarDataController measuredPillarDataController;
    private final AnchorPane pane;
    private final VBox vBox;
    private final Color color = Color.rgb(112,128,144);
    private final Font normalFont = Font.font("Arial", FontWeight.NORMAL, 13);
    private final Font boldFont = Font.font("Arial", FontWeight.BOLD, 13);
    public TextField startPointIdField;
    public TextField endPointIdField;
    public TextField startField_X;
    public TextField startField_Y;
    public TextField endField_X;
    public TextField endField_Y;
    public TextField newPointIdField;
    public TextField standingAIdField;
    public TextField standingAPointField_X;
    public TextField standingAPointField_Y;
    public TextField standingAPointField_Z;
    public TextField standingAPointAzimuthAngleField;
    public TextField standingAPointAzimuthMinField;
    public TextField standingAPointAzimuthSecField;
    public TextField standingAPointElevationAngleField;
    public TextField standingAPointElevationMinField;
    public TextField standingAPointElevationSecField;
    public TextField standingBIdField;
    public TextField standingBPointField_X;
    public TextField standingBPointField_Y;
    public TextField standingBPointField_Z;
    public TextField standingBPointAzimuthAngleField;
    public TextField standingBPointAzimuthMinField;
    public TextField standingBPointAzimuthSecField;
    public TextField standingBPointElevationAngleField;
    public TextField standingBPointElevationMinField;
    public TextField standingBPointElevationSecField;

    public IntersectionInputDataWindow(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        stage = new Stage();
        stage.setOnCloseRequest(windowEvent ->
                measuredPillarDataController.fxHomeWindow.homeStage.show());
        pane = new AnchorPane();
        vBox = new VBox();
        pane.setStyle("-fx-background-color: white");
        addWireDataFields();
        addNewPointDataFields();
        addStandingPointADataFields();
        addStandingPointBDataFields();
        addCalcButton();
        addTestData();
        pane.getChildren().add(vBox);
        Scene scene = new Scene(pane);
        stage.setWidth(400);
        stage.setHeight(840);
        stage.setTitle("Előmetszés számítása");
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void addWireDataFields(){
        Line leftTopLine = new Line();
        leftTopLine.setStroke(color);
        leftTopLine.setStartX(5);
        leftTopLine.setStartY(10);
        leftTopLine.setEndX(80);
        leftTopLine.setEndY(10);
        Line rightTopLine = new Line();
        rightTopLine.setStroke(color);
        rightTopLine.setStartX(290);
        rightTopLine.setStartY(10);
        rightTopLine.setEndX(380);
        rightTopLine.setEndY(10);
        Line leftLine = new Line();
        leftLine.setStroke(color);
        leftLine.setStartX(5);
        leftLine.setStartY(10);
        leftLine.setEndX(5);
        leftLine.setEndY(220);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(10);
        rightLine.setEndX(380);
        rightLine.setEndY(220);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(220);
        bottomLine.setEndX(380);
        bottomLine.setEndY(220);
        pane.getChildren().addAll(leftTopLine, rightTopLine, leftLine, rightLine, bottomLine);
        Text wireDataText = new Text("Sodrony adatok megadása");
        wireDataText.setFont(normalFont);
        wireDataText.setFill(color);
        HBox wireDataTextHbox = new HBox();
        wireDataTextHbox.setAlignment(Pos.CENTER);
        wireDataTextHbox.getChildren().add(wireDataText);

        startPointIdField = new TextField();
        startPointIdField.setCursor(Cursor.HAND);
        startPointIdField.setFont(normalFont);
        startPointIdField.setStyle("-fx-text-inner-color: #708090; -fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        startPointIdField.setPrefColumnCount(10);
        Text startPointIdText = new Text("A kezdőpont azonosítója:");
        startPointIdText.setFont(boldFont);
        HBox startPointIdHbox = new HBox();
        startPointIdHbox.setAlignment(Pos.BASELINE_LEFT);
        startPointIdHbox.setPadding(new Insets(5,5,5,20));
        startPointIdHbox.setSpacing(50);
        startPointIdHbox.getChildren().addAll(startPointIdText, startPointIdField);

        HBox startXHbox = new HBox();
        startXHbox.setPadding(new Insets(5,5,5,20));
        startXHbox.setSpacing(40);
        startXHbox.setAlignment(Pos.CENTER);
        Text startXText = new Text("Y koordináta [m]:");
        startXText.setFont(boldFont);
        startField_X = new TextField();
        startField_X.setCursor(Cursor.HAND);
        startField_X.setFont(normalFont);
        startField_X.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        startField_X.setPrefColumnCount(15);
        startField_X.setFont(normalFont);
        startField_X.textProperty().addListener((observable, oldValue, newValue) -> {

            if( oldValue.length() < 3 ){
                endField_X.setText(newValue);
                standingAPointField_X.setText(newValue);
                standingBPointField_X.setText(newValue);
            }
        });
        startXHbox.getChildren().addAll(startXText, startField_X);

        HBox startYHbox = new HBox();
        startYHbox.setPadding(new Insets(5,5,5,20));
        startYHbox.setSpacing(40);
        startYHbox.setAlignment(Pos.CENTER);
        Text startYText = new Text("X koordináta [m]:");
        startYText.setFont(boldFont);
        startField_Y = new TextField();
        startField_Y.setCursor(Cursor.HAND);
        startField_Y.setFont(normalFont);
        startField_Y.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        startField_Y.setPrefColumnCount(15);
        startField_Y.setFont(normalFont);
        startField_Y.textProperty().addListener((observable, oldValue, newValue) -> {

            if( oldValue.length() < 3 ){
                endField_Y.setText(newValue);
                standingAPointField_Y.setText(newValue);
                standingBPointField_Y.setText(newValue);
            }
        });
        startYHbox.getChildren().addAll(startYText, startField_Y);

        endPointIdField = new TextField();
        endPointIdField.setCursor(Cursor.HAND);
        endPointIdField.setFont(normalFont);
        endPointIdField.setStyle("-fx-text-inner-color: #708090; -fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        endPointIdField.setPrefColumnCount(10);
        Text endPointIdText = new Text("A végpont azonosítója:");
        endPointIdText.setFont(boldFont);
        HBox endPointIdHbox = new HBox();
        endPointIdHbox.setAlignment(Pos.BASELINE_LEFT);
        endPointIdHbox.setPadding(new Insets(5,5,5,20));
        endPointIdHbox.setSpacing(65);
        endPointIdHbox.getChildren().addAll(endPointIdText, endPointIdField);

        HBox endXHbox = new HBox();
        endXHbox.setPadding(new Insets(5,5,5,20));
        endXHbox.setSpacing(40);
        endXHbox.setAlignment(Pos.CENTER);
        Text endXText = new Text("Y koordináta [m]:");
        endXText.setFont(boldFont);
        endField_X = new TextField();
        endField_X.setCursor(Cursor.HAND);
        endField_X.setFont(normalFont);
        endField_X.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        endField_X.setPrefColumnCount(15);
        endField_X.setFont(normalFont);
        endXHbox.getChildren().addAll(endXText, endField_X);

        HBox endYHbox = new HBox();
        endYHbox.setPadding(new Insets(5,5,5,20));
        endYHbox.setSpacing(40);
        endYHbox.setAlignment(Pos.CENTER);
        Text endYText = new Text("X koordináta [m]:");
        endYText.setFont(boldFont);
        endField_Y = new TextField();
        endField_Y.setCursor(Cursor.HAND);
        endField_Y.setFont(normalFont);
        endField_Y.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        endField_Y.setPrefColumnCount(15);
        endField_Y.setFont(normalFont);
        endYHbox.getChildren().addAll(endYText, endField_Y);

        vBox.getChildren().addAll(wireDataTextHbox,
                startPointIdHbox, startXHbox, startYHbox,
                endPointIdHbox, endXHbox, endYHbox);
    }

   private void addNewPointDataFields(){
        Line leftTopLine = new Line();
        leftTopLine.setStroke(color);
        leftTopLine.setStartX(5);
        leftTopLine.setStartY(230);
        leftTopLine.setEndX(80);
        leftTopLine.setEndY(230);
        Line rightTopLine = new Line();
        rightTopLine.setStroke(color);
        rightTopLine.setStartX(290);
        rightTopLine.setStartY(230);
        rightTopLine.setEndX(380);
        rightTopLine.setEndY(230);
        Line leftLine = new Line();
        leftLine.setStroke(color);
        leftLine.setStartX(5);
        leftLine.setStartY(230);
        leftLine.setEndX(5);
        leftLine.setEndY(280);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(230);
        rightLine.setEndX(380);
        rightLine.setEndY(280);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(280);
        bottomLine.setEndX(380);
        bottomLine.setEndY(280);
        pane.getChildren().addAll(leftTopLine, rightTopLine, leftLine, rightLine, bottomLine);

        Text measuredDataText = new Text("Új pont megadása");
        measuredDataText.setFont(normalFont);
        measuredDataText.setFill(color);
        HBox measuredDataTextHbox = new HBox();
        measuredDataTextHbox.setAlignment(Pos.CENTER);
        measuredDataTextHbox.setPadding(new Insets(7,5,5,5));
        measuredDataTextHbox.getChildren().add(measuredDataText);
       vBox.getChildren().add(measuredDataTextHbox);

        HBox newPointIdTextHbox = new HBox();
        newPointIdTextHbox.setAlignment(Pos.CENTER);
        newPointIdTextHbox.setSpacing(35);
        Text newPointIdText = new Text("Az új pont azonosítója:");
        newPointIdText.setFont(boldFont);
        newPointIdField = new TextField();
        newPointIdField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        newPointIdField.setPrefColumnCount(12);
        newPointIdField.setFont(normalFont);
        newPointIdField.setCursor(Cursor.HAND);
        newPointIdField.setText("METSZÉSPONT");
        newPointIdTextHbox.getChildren().addAll(newPointIdText, newPointIdField );
        vBox.getChildren().add(newPointIdTextHbox);
    }

    private void addStandingPointADataFields(){
        Line leftTopLine = new Line();
        leftTopLine.setStroke(color);
        leftTopLine.setStartX(5);
        leftTopLine.setStartY(290);
        leftTopLine.setEndX(40);
        leftTopLine.setEndY(290);
        Line rightTopLine = new Line();
        rightTopLine.setStroke(color);
        rightTopLine.setStartX(340);
        rightTopLine.setStartY(290);
        rightTopLine.setEndX(380);
        rightTopLine.setEndY(290);
        Line leftLine = new Line();
        leftLine.setStroke(color);
        leftLine.setStartX(5);
        leftLine.setStartY(290);
        leftLine.setEndX(5);
        leftLine.setEndY(525);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(290);
        rightLine.setEndX(380);
        rightLine.setEndY(525);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(525);
        bottomLine.setEndX(380);
        bottomLine.setEndY(525);
        pane.getChildren().addAll(leftTopLine, rightTopLine,
                leftLine, rightLine, bottomLine);
        Text standingAPointDataText = new Text("1. álláspontra vonatkozó adatok megadása");
        standingAPointDataText.setFont(normalFont);
        standingAPointDataText.setFill(color);
        HBox standingAPointTextHbox = new HBox();
        standingAPointTextHbox.setPadding(new Insets(15,5,5,5));
        standingAPointTextHbox.setAlignment(Pos.CENTER);
        standingAPointTextHbox.getChildren().add(standingAPointDataText);
        vBox.getChildren().add(standingAPointTextHbox);

        HBox standingAPointIdTextHbox = new HBox();
        standingAPointIdTextHbox.setPadding(new Insets(5,5,5,5));
        standingAPointIdTextHbox.setAlignment(Pos.CENTER);
        standingAPointIdTextHbox.setSpacing(40);
        Text standingAPointIdText = new Text("Az 1. pont azonosítója:");
        standingAPointIdText.setFont(boldFont);
        standingAIdField = new TextField();
        standingAIdField.setText("1_PONT");
        standingAIdField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingAIdField.setPrefColumnCount(12);
        standingAIdField.setFont(normalFont);
        standingAIdField.setCursor(Cursor.HAND);
        standingAPointIdTextHbox.getChildren().addAll(standingAPointIdText, standingAIdField);
        vBox.getChildren().add(standingAPointIdTextHbox);

        HBox standingPointAXHbox = new HBox();
        standingPointAXHbox.setPadding(new Insets(5,5,5,5));
        standingPointAXHbox.setSpacing(40);
        standingPointAXHbox.setAlignment(Pos.CENTER);
        Text standingAPointXText = new Text("Y koordináta [m]:");
        standingAPointXText.setFont(boldFont);
        standingAPointField_X = new TextField();
        standingAPointField_X.setPrefColumnCount(15);
        standingAPointField_X.setFont(normalFont);
        standingAPointField_X.setCursor(Cursor.HAND);
        standingAPointField_X.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingPointAXHbox.getChildren().addAll(standingAPointXText, standingAPointField_X);
        standingAPointField_X.textProperty().addListener((observable, oldValue, newValue) -> {

            if( oldValue.length() < 3 ){
                standingBPointField_X.setText(newValue);
            }
        });
        vBox.getChildren().add(standingPointAXHbox);

        HBox standingPointAYHbox = new HBox();
        standingPointAYHbox.setPadding(new Insets(5,5,5,5));
        standingPointAYHbox.setSpacing(40);
        standingPointAYHbox.setAlignment(Pos.CENTER);
        Text standingPointAYText = new Text("X koordináta [m]:");
        standingPointAYText.setFont(boldFont);
        standingAPointField_Y = new TextField();
        standingAPointField_Y .setCursor(Cursor.HAND);
        standingAPointField_Y .setFont(normalFont);
        standingAPointField_Y .setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingAPointField_Y .setPrefColumnCount(15);
        standingAPointField_Y.textProperty().addListener((observable, oldValue, newValue) -> {

            if( oldValue.length() < 3 ){
                standingBPointField_Y.setText(newValue);
            }
        });
        standingPointAYHbox.getChildren().addAll(standingPointAYText, standingAPointField_Y);
        vBox.getChildren().add(standingPointAYHbox);

        HBox standingPointAZHbox = new HBox();
        standingPointAZHbox.setPadding(new Insets(5,5,5,5));
        standingPointAZHbox.setSpacing(40);
        standingPointAZHbox.setAlignment(Pos.CENTER);
        Text standingPointAZText = new Text("h magasság [m]:");
        standingPointAZText.setFont(boldFont);
        standingAPointField_Z = new TextField();
        standingAPointField_Z .setCursor(Cursor.HAND);
        standingAPointField_Z .setFont(normalFont);
        standingAPointField_Z .setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingAPointField_Z .setPrefColumnCount(15);
        standingPointAZHbox.getChildren().addAll(standingPointAZText, standingAPointField_Z);
        vBox.getChildren().add(standingPointAZHbox);

        Text horizontalText = new Text("\tHz:         ");
        horizontalText.setFont(boldFont);
        Text angleHzText = new Text("fok");
        angleHzText.setFont(boldFont);
        Text minHzText = new Text("perc");
        minHzText.setFont(boldFont);
        Text secHzText = new Text("mperc");
        secHzText.setFont(boldFont);
        standingAPointAzimuthAngleField = new TextField();
        standingAPointAzimuthAngleField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingAPointAzimuthAngleField.setFont(normalFont);
        standingAPointAzimuthAngleField.setCursor(Cursor.HAND);
        standingAPointAzimuthAngleField.setPrefColumnCount(3);
        standingAPointAzimuthMinField = new TextField();
        standingAPointAzimuthMinField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingAPointAzimuthMinField.setFont(normalFont);
        standingAPointAzimuthMinField.setCursor(Cursor.HAND);
        standingAPointAzimuthMinField.setPrefColumnCount(3);
        standingAPointAzimuthSecField = new TextField();
        standingAPointAzimuthSecField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingAPointAzimuthSecField.setFont(normalFont);
        standingAPointAzimuthSecField.setCursor(Cursor.HAND);
        standingAPointAzimuthSecField.setPrefColumnCount(3);
        HBox staningPointAAzimuthHbox = new HBox();
        staningPointAAzimuthHbox.setAlignment(Pos.CENTER);
        staningPointAAzimuthHbox.setSpacing(5);
        staningPointAAzimuthHbox.setPadding(new Insets(10,10,10,10));
        staningPointAAzimuthHbox.getChildren().addAll(horizontalText, standingAPointAzimuthAngleField,
                angleHzText, standingAPointAzimuthMinField, minHzText, standingAPointAzimuthSecField, secHzText);
        vBox.getChildren().addAll(staningPointAAzimuthHbox);

        Text verticalText = new Text("\tVz:         ");
        verticalText.setFont(boldFont);
        Text angleVzText = new Text("fok");
        angleVzText.setFont(boldFont);
        Text minVzText = new Text("perc");
        minVzText.setFont(boldFont);
        Text secVzText = new Text("mperc");
        secVzText.setFont(boldFont);
        standingAPointElevationAngleField = new TextField();
        standingAPointElevationAngleField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingAPointElevationAngleField.setFont(normalFont);
        standingAPointElevationAngleField.setCursor(Cursor.HAND);
        standingAPointElevationAngleField.setPrefColumnCount(3);
        standingAPointElevationMinField = new TextField();
        standingAPointElevationMinField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingAPointElevationMinField.setFont(normalFont);
        standingAPointElevationMinField.setCursor(Cursor.HAND);
        standingAPointElevationMinField.setPrefColumnCount(3);
        standingAPointElevationSecField = new TextField();
        standingAPointElevationSecField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingAPointElevationSecField.setFont(normalFont);
        standingAPointElevationSecField.setCursor(Cursor.HAND);
        standingAPointElevationSecField.setPrefColumnCount(3);
        HBox standingPointAElevationHbox = new HBox();
        standingPointAElevationHbox.setAlignment(Pos.CENTER);
        standingPointAElevationHbox.setSpacing(5);
        standingPointAElevationHbox.setPadding(new Insets(10,10,10,10));
        standingPointAElevationHbox.getChildren().addAll(verticalText, standingAPointElevationAngleField,
                angleVzText, standingAPointElevationMinField, minVzText,
                standingAPointElevationSecField, secVzText);
        vBox.getChildren().addAll(standingPointAElevationHbox);

    }

    private void addStandingPointBDataFields(){
        Line leftTopLine = new Line();
        leftTopLine.setStroke(color);
        leftTopLine.setStartX(5);
        leftTopLine.setStartY(535);
        leftTopLine.setEndX(40);
        leftTopLine.setEndY(535);
        Line rightTopLine = new Line();
        rightTopLine.setStroke(color);
        rightTopLine.setStartX(340);
        rightTopLine.setStartY(535);
        rightTopLine.setEndX(380);
        rightTopLine.setEndY(535);
        Line leftLine = new Line();
        leftLine.setStroke(color);
        leftLine.setStartX(5);
        leftLine.setStartY(535);
        leftLine.setEndX(5);
        leftLine.setEndY(770);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(535);
        rightLine.setEndX(380);
        rightLine.setEndY(770);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(770);
        bottomLine.setEndX(380);
        bottomLine.setEndY(770);
        pane.getChildren().addAll(leftTopLine, rightTopLine,
                leftLine, rightLine, bottomLine);
        Text standingBPointDataText = new Text("2. álláspontra vonatkozó adatok megadása");
        standingBPointDataText.setFont(normalFont);
        standingBPointDataText.setFill(color);
        HBox standingBPointTextHbox = new HBox();
        standingBPointTextHbox.setAlignment(Pos.CENTER);
        standingBPointTextHbox.getChildren().add(standingBPointDataText);
        vBox.getChildren().add(standingBPointTextHbox);

        HBox standingBPointIdTextHbox = new HBox();
        standingBPointIdTextHbox.setPadding(new Insets(5,5,5,5));
        standingBPointIdTextHbox.setAlignment(Pos.CENTER);
        standingBPointIdTextHbox.setSpacing(45);
        Text standingBPointIdText = new Text("A 2. pont azonosítója:");
        standingBPointIdText.setFont(boldFont);
        standingBIdField = new TextField();
        standingBIdField.setText("2_PONT");
        standingBIdField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingBIdField.setPrefColumnCount(12);
        standingBIdField.setFont(normalFont);
        standingBIdField.setCursor(Cursor.HAND);
        standingBPointIdTextHbox.getChildren().addAll(standingBPointIdText, standingBIdField);
        vBox.getChildren().add(standingBPointIdTextHbox);

        HBox standingPointBXHbox = new HBox();
        standingPointBXHbox.setPadding(new Insets(5,5,5,5));
        standingPointBXHbox.setSpacing(40);
        standingPointBXHbox.setAlignment(Pos.CENTER);
        Text standingBPointXText = new Text("Y koordináta [m]:");
        standingBPointXText.setFont(boldFont);
        standingBPointField_X = new TextField();
        standingBPointField_X.setPrefColumnCount(15);
        standingBPointField_X.setFont(normalFont);
        standingBPointField_X.setCursor(Cursor.HAND);
        standingBPointField_X.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingPointBXHbox.getChildren().addAll(standingBPointXText, standingBPointField_X);
        vBox.getChildren().add(standingPointBXHbox);

        HBox standingPointBYHbox = new HBox();
        standingPointBYHbox.setPadding(new Insets(5,5,5,5));
        standingPointBYHbox.setSpacing(40);
        standingPointBYHbox.setAlignment(Pos.CENTER);
        Text standingPointBYText = new Text("X koordináta [m]:");
        standingPointBYText.setFont(boldFont);
        standingBPointField_Y = new TextField();
        standingBPointField_Y .setCursor(Cursor.HAND);
        standingBPointField_Y .setFont(normalFont);
        standingBPointField_Y .setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingBPointField_Y .setPrefColumnCount(15);
        standingPointBYHbox.getChildren().addAll(standingPointBYText, standingBPointField_Y);
        vBox.getChildren().add(standingPointBYHbox);

        HBox standingPointBZHbox = new HBox();
        standingPointBZHbox.setPadding(new Insets(5,5,5,5));
        standingPointBZHbox.setSpacing(40);
        standingPointBZHbox.setAlignment(Pos.CENTER);
        Text standingPointBZYText = new Text("h magasság [m]:");
        standingPointBZYText.setFont(boldFont);
        standingBPointField_Z = new TextField();
        standingBPointField_Z .setCursor(Cursor.HAND);
        standingBPointField_Z .setFont(normalFont);
        standingBPointField_Z .setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingBPointField_Z .setPrefColumnCount(15);
        standingPointBZHbox.getChildren().addAll(standingPointBZYText, standingBPointField_Z);
        vBox.getChildren().add(standingPointBZHbox);

        Text horizontalText = new Text("\tHz:         ");
        horizontalText.setFont(boldFont);
        Text angleHzText = new Text("fok");
        angleHzText.setFont(boldFont);
        Text minHzText = new Text("perc");
        minHzText.setFont(boldFont);
        Text secHzText = new Text("mperc");
        secHzText.setFont(boldFont);
        standingBPointAzimuthAngleField = new TextField();
        standingBPointAzimuthAngleField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingBPointAzimuthAngleField.setFont(normalFont);
        standingBPointAzimuthAngleField.setCursor(Cursor.HAND);
        standingBPointAzimuthAngleField.setPrefColumnCount(3);
        standingBPointAzimuthMinField = new TextField();
        standingBPointAzimuthMinField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingBPointAzimuthMinField.setFont(normalFont);
        standingBPointAzimuthMinField.setCursor(Cursor.HAND);
        standingBPointAzimuthMinField.setPrefColumnCount(3);
        standingBPointAzimuthSecField = new TextField();
        standingBPointAzimuthSecField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingBPointAzimuthSecField.setFont(normalFont);
        standingBPointAzimuthSecField.setCursor(Cursor.HAND);
        standingBPointAzimuthSecField.setPrefColumnCount(3);
        HBox standingPointBAzimuthHbox = new HBox();
        standingPointBAzimuthHbox.setAlignment(Pos.CENTER);
        standingPointBAzimuthHbox.setSpacing(5);
        standingPointBAzimuthHbox.setPadding(new Insets(10,10,10,10));
        standingPointBAzimuthHbox.getChildren().addAll(horizontalText, standingBPointAzimuthAngleField,
                angleHzText, standingBPointAzimuthMinField, minHzText, standingBPointAzimuthSecField, secHzText);
        vBox.getChildren().addAll(standingPointBAzimuthHbox);

        Text verticalText = new Text("\tVz:         ");
        verticalText.setFont(boldFont);
        Text angleVzText = new Text("fok");
        angleVzText.setFont(boldFont);
        Text minVzText = new Text("perc");
        minVzText.setFont(boldFont);
        Text secVzText = new Text("mperc");
        secVzText.setFont(boldFont);
        standingBPointElevationAngleField = new TextField();
        standingBPointElevationAngleField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingBPointElevationAngleField.setFont(normalFont);
        standingBPointElevationAngleField.setCursor(Cursor.HAND);
        standingBPointElevationAngleField.setPrefColumnCount(3);
        standingBPointElevationMinField = new TextField();
        standingBPointElevationMinField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingBPointElevationMinField.setFont(normalFont);
        standingBPointElevationMinField.setCursor(Cursor.HAND);
        standingBPointElevationMinField.setPrefColumnCount(3);
        standingBPointElevationSecField = new TextField();
        standingBPointElevationSecField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingBPointElevationSecField.setFont(normalFont);
        standingBPointElevationSecField.setCursor(Cursor.HAND);
        standingBPointElevationSecField.setPrefColumnCount(3);
        HBox standingPointBElevationHbox = new HBox();
        standingPointBElevationHbox.setAlignment(Pos.CENTER);
        standingPointBElevationHbox.setSpacing(5);
        standingPointBElevationHbox.setPadding(new Insets(10,10,10,10));
        standingPointBElevationHbox.getChildren().addAll(verticalText, standingBPointElevationAngleField,
                angleVzText, standingBPointElevationMinField, minVzText, standingBPointElevationSecField, secVzText);
        vBox.getChildren().addAll(standingPointBElevationHbox);

    }
    private void addCalcButton(){
        Button calcButton = new Button("Számol");
        calcButton.setOnMouseClicked(e -> measuredPillarDataController.onClickCountButtonForIntersectionProcess());
        calcButton.setCursor(Cursor.HAND);
        calcButton.setFont(boldFont);
        HBox calcButtonHbox = new HBox();
        calcButtonHbox.setPadding(new Insets(7,5,5,5));
        calcButtonHbox.setAlignment(Pos.CENTER);
        calcButtonHbox.getChildren().add(calcButton);
        vBox.getChildren().add(calcButtonHbox);
    }

    private void addTestData(){

        startPointIdField.setText("113");
        startField_X.setText("670865.848");
        startField_Y.setText("243668.540");
        endPointIdField.setText("114");
        endField_X.setText("670622.992");
        endField_Y.setText("244081.461");
      double azimuthA =  8.25144120;
      double elevationA = 88.29508200;
      double azimuthB = 142.27146520;
      double elevationB = 87.47520440;
      int azimuthAMin = (int) ((azimuthA - (int) azimuthA) * 60);
      int azimuthBMin = (int) ((azimuthB - (int) azimuthB) * 60);
      int elevationAMin = (int) ((elevationA - (int) elevationA) * 60);
      int elevationBMin = (int) ((elevationB - (int) elevationB) * 60);
        standingAPointField_X.setText("670697.831");
        standingAPointField_Y.setText("243560.267");
        standingAPointField_Z.setText("264.758");
        standingAPointAzimuthAngleField.setText(String.valueOf((int) (azimuthA)));
        standingAPointAzimuthMinField.setText(String.valueOf((int) ((azimuthA - (int) azimuthA) * 60)));
        standingAPointAzimuthSecField
                .setText(String.valueOf((int) (azimuthA * 3600 - (int) azimuthA * 3600 - azimuthAMin * 60)));
        standingAPointElevationAngleField.setText(String.valueOf((int) (elevationA)));
        standingAPointElevationMinField
                .setText(String.valueOf((int) ((elevationA - (int) elevationA) * 60)));
        standingAPointElevationSecField
                .setText(String.valueOf((int) (elevationA * 3600 -
                        (int) elevationA * 3600 - elevationAMin * 60)));

        standingBPointField_X.setText("670209.354");
        standingBPointField_Y.setText("244571.161");
        standingBPointField_Z.setText("238.545");
        standingBPointAzimuthAngleField.setText(String.valueOf((int) (azimuthB)));
        standingBPointAzimuthMinField.setText(String.valueOf((int) ((azimuthB - (int) azimuthB) * 60)));
        standingBPointAzimuthSecField
                .setText(String.valueOf((int) (azimuthB * 3600 - (int) azimuthB * 3600 - azimuthBMin * 60)));
        standingBPointElevationAngleField.setText(String.valueOf((int) (elevationB)));
        standingBPointElevationMinField
                .setText(String.valueOf((int) ((elevationB - (int) elevationB) * 60)));
        standingBPointElevationSecField.setText(String.valueOf((int) (elevationB * 3600 -
                (int) elevationB * 3600 - elevationBMin * 60)));
    }


}

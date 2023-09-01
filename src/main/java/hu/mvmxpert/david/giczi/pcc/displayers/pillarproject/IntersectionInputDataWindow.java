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
    private final Font normalFont = Font.font("Arial", FontWeight.NORMAL, 14);
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
    public TextField standingAPointAzimuthMinField;;
    public TextField standingAPointAzimuthSecField;
    public TextField standingAPointElevationAngleField;
    public TextField standingAPointElevationMinField;;
    public TextField standingAPointElevationSecField;
    public TextField standingBIdField;
    public TextField standingBPointField_X;
    public TextField standingBPointField_Y;
    public TextField standingBPointField_Z;
    public TextField standingBPointAzimuthAngleField;
    public TextField standingBPointAzimuthMinField;;
    public TextField standingBPointAzimuthSecField;
    public TextField standingBPointElevationAngleField;
    public TextField standingBPointElevationMinField;;
    public TextField standingBPointElevationSecField;

    public IntersectionInputDataWindow(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        stage = new Stage();
        stage.setOnCloseRequest(windowEvent ->
        {measuredPillarDataController.init();
            measuredPillarDataController.fxHomeWindow.homeStage.show();
            measuredPillarDataController.setCreatedInputPillarDataWindow(true);});
        pane = new AnchorPane();
        vBox = new VBox();
        pane.setStyle("-fx-background-color: white");
        addWireDataFields();
        addNewPointDataFields();
        addStandingPointADataFields();
        //addStandingPointBDataFields();
        addCalcButton();
        pane.getChildren().add(vBox);
        Scene scene = new Scene(pane);
        stage.setWidth(400);
        stage.setHeight(800);
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
        leftLine.setEndY(260);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(10);
        rightLine.setEndX(380);
        rightLine.setEndY(260);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(260);
        bottomLine.setEndX(380);
        bottomLine.setEndY(260);
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
        startPointIdHbox.setPadding(new Insets(10,10,10,10));
        startPointIdHbox.setSpacing(50);
        startPointIdHbox.getChildren().addAll(startPointIdText, startPointIdField);

        HBox startXHbox = new HBox();
        startXHbox.setPadding(new Insets(5,5,5,5));
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
        startXHbox.getChildren().addAll(startXText, startField_X);

        HBox startYHbox = new HBox();
        startYHbox.setPadding(new Insets(5,5,5,5));
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
        endPointIdHbox.setPadding(new Insets(10,10,10,10));
        endPointIdHbox.setSpacing(65);
        endPointIdHbox.getChildren().addAll(endPointIdText, endPointIdField);

        HBox endXHbox = new HBox();
        endXHbox.setPadding(new Insets(5,5,5,5));
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
        endYHbox.setPadding(new Insets(5,5,5,5));
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
        leftTopLine.setStartY(270);
        leftTopLine.setEndX(80);
        leftTopLine.setEndY(270);
        Line rightTopLine = new Line();
        rightTopLine.setStroke(color);
        rightTopLine.setStartX(290);
        rightTopLine.setStartY(270);
        rightTopLine.setEndX(380);
        rightTopLine.setEndY(270);
        Line leftLine = new Line();
        leftLine.setStroke(color);
        leftLine.setStartX(5);
        leftLine.setStartY(270);
        leftLine.setEndX(5);
        leftLine.setEndY(335);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(270);
        rightLine.setEndX(380);
        rightLine.setEndY(335);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(335);
        bottomLine.setEndX(380);
        bottomLine.setEndY(335);
        pane.getChildren().addAll(leftTopLine, rightTopLine, leftLine, rightLine, bottomLine);

        Text measuredDataText = new Text("Új pont megadása");
        measuredDataText.setFont(normalFont);
        measuredDataText.setFill(color);
        HBox measuredDataTextHbox = new HBox();
        measuredDataTextHbox.setAlignment(Pos.CENTER);
        measuredDataTextHbox.setPadding(new Insets(15,15,15,15));
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
        newPointIdField.setText("METSZESPONT");
        newPointIdTextHbox.getChildren().addAll(newPointIdText, newPointIdField );
        vBox.getChildren().add(newPointIdTextHbox);
    }

    private void addStandingPointADataFields(){
        Line leftTopLine = new Line();
        leftTopLine.setStroke(color);
        leftTopLine.setStartX(5);
        leftTopLine.setStartY(355);
        leftTopLine.setEndX(40);
        leftTopLine.setEndY(355);
        Line rightTopLine = new Line();
        rightTopLine.setStroke(color);
        rightTopLine.setStartX(340);
        rightTopLine.setStartY(355);
        rightTopLine.setEndX(380);
        rightTopLine.setEndY(355);
        Line leftLine = new Line();
        leftLine.setStroke(color);
        leftLine.setStartX(5);
        leftLine.setStartY(355);
        leftLine.setEndX(5);
        leftLine.setEndY(560);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(355);
        rightLine.setEndX(380);
        rightLine.setEndY(560);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(560);
        bottomLine.setEndX(380);
        bottomLine.setEndY(560);
        pane.getChildren().addAll(leftTopLine, rightTopLine,
                leftLine, rightLine, bottomLine);
        Text standingAPointDataText = new Text("1. álláspontra vonatkozó adatok megadása");
        standingAPointDataText.setFont(normalFont);
        standingAPointDataText.setFill(color);
        HBox standingAPointTextHbox = new HBox();
        standingAPointTextHbox.setPadding(new Insets(23,23,5,23));
        standingAPointTextHbox.setAlignment(Pos.CENTER);
        standingAPointTextHbox.getChildren().add(standingAPointDataText);
        vBox.getChildren().add(standingAPointTextHbox);

        HBox standingAPointIdTextHbox = new HBox();
        standingAPointIdTextHbox.setPadding(new Insets(5,5,5,5));
        standingAPointIdTextHbox.setAlignment(Pos.CENTER);
        standingAPointIdTextHbox.setSpacing(45);
        Text standingAPointIdText = new Text("Az 1. pont azonosítója:");
        standingAPointIdText.setFont(boldFont);
        standingAIdField = new TextField();
        standingAIdField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingAIdField.setPrefColumnCount(12);
        standingAIdField.setFont(normalFont);
        standingAIdField.setCursor(Cursor.HAND);
        standingAPointIdTextHbox.getChildren().addAll(standingAPointIdText, standingAIdField);
        vBox.getChildren().add(standingAPointIdTextHbox);

        HBox standingAPointXHbox = new HBox();
        standingAPointXHbox.setPadding(new Insets(5,5,5,5));
        standingAPointXHbox.setSpacing(40);
        standingAPointXHbox.setAlignment(Pos.CENTER);
        Text standingAPointXText = new Text("Y koordináta [m]:");
        standingAPointXText.setFont(boldFont);
        standingAPointField_X = new TextField();
        standingAPointField_X.setPrefColumnCount(15);
        standingAPointField_X.setFont(normalFont);
        standingAPointField_X  = new TextField();
        standingAPointField_X.setCursor(Cursor.HAND);
        standingAPointField_X.setFont(normalFont);
        standingAPointField_X.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingAPointXHbox.getChildren().addAll(standingAPointXText, standingAPointField_X);
        vBox.getChildren().add(standingAPointXHbox);

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
        standingAPointField_Y.setFont(normalFont);
        standingPointAYHbox.getChildren().addAll(standingPointAYText, standingAPointField_Y);
        vBox.getChildren().add(standingPointAYHbox);

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
        HBox staningPointAElevationHbox = new HBox();
        staningPointAElevationHbox.setAlignment(Pos.CENTER);
        staningPointAElevationHbox.setSpacing(5);
        staningPointAElevationHbox.setPadding(new Insets(10,10,10,10));
        staningPointAElevationHbox.getChildren().addAll(verticalText, standingAPointElevationAngleField,
                angleVzText, standingAPointElevationMinField, minVzText,
                standingAPointElevationSecField, secVzText);
        vBox.getChildren().addAll(staningPointAElevationHbox);

    }

    private void addStandingPointBDataFields(){
        Line leftTopLine = new Line();
        leftTopLine.setStroke(color);
        leftTopLine.setStartX(5);
        leftTopLine.setStartY(570);
        leftTopLine.setEndX(40);
        leftTopLine.setEndY(570);
        Line rightTopLine = new Line();
        rightTopLine.setStroke(color);
        rightTopLine.setStartX(340);
        rightTopLine.setStartY(570);
        rightTopLine.setEndX(380);
        rightTopLine.setEndY(570);
        Line leftLine = new Line();
        leftLine.setStroke(color);
        leftLine.setStartX(5);
        leftLine.setStartY(570);
        leftLine.setEndX(5);
        leftLine.setEndY(665);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(570);
        rightLine.setEndX(380);
        rightLine.setEndY(665);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(665);
        bottomLine.setEndX(380);
        bottomLine.setEndY(665);
        pane.getChildren().addAll(leftTopLine, rightTopLine,
                leftLine, rightLine, bottomLine);
        Text standingBPointDataText = new Text("2. álláspontra vonatkozó adatok megadása");
        standingBPointDataText.setFont(normalFont);
        standingBPointDataText.setFill(color);
        HBox standingBPointTextHbox = new HBox();
        standingBPointTextHbox.setPadding(new Insets(10,10,10,10));
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
        standingBIdField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingBIdField.setPrefColumnCount(12);
        standingBIdField.setFont(normalFont);
        standingBIdField.setCursor(Cursor.HAND);
        standingBPointIdTextHbox.getChildren().addAll(standingBPointIdText, standingBIdField);
        vBox.getChildren().add(standingBPointIdTextHbox);

        HBox standingBPointXHbox = new HBox();
        standingBPointXHbox.setPadding(new Insets(5,5,5,5));
        standingBPointXHbox.setSpacing(40);
        standingBPointXHbox.setAlignment(Pos.CENTER);
        Text standingBPointXText = new Text("Y koordináta [m]:");
        standingBPointXText.setFont(boldFont);
        standingBPointField_X = new TextField();
        standingBPointField_X.setPrefColumnCount(15);
        standingBPointField_X.setFont(normalFont);
        standingBPointField_X  = new TextField();
        standingBPointField_X.setCursor(Cursor.HAND);
        standingBPointField_X.setFont(normalFont);
        standingBPointField_X.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingBPointXHbox.getChildren().addAll(standingBPointXText, standingBPointField_X);

        HBox standingPointBYHbox = new HBox();
        standingPointBYHbox.setPadding(new Insets(5,5,5,5));
        standingPointBYHbox.setSpacing(40);
        standingPointBYHbox.setAlignment(Pos.CENTER);
        Text standingPointAXText = new Text("X koordináta [m]:");
        standingPointAXText.setFont(boldFont);
        standingBPointField_Y = new TextField();
        standingBPointField_Y.setCursor(Cursor.HAND);
        standingBPointField_Y.setFont(normalFont);
        standingBPointField_Y.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        standingBPointField_Y.setPrefColumnCount(15);
        standingBPointField_Y.setFont(normalFont);
        standingPointBYHbox.getChildren().addAll(standingPointAXText, standingAPointField_X);
        vBox.getChildren().add(standingPointBYHbox);

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
        HBox staningPointBAzimuthHbox = new HBox();
        staningPointBAzimuthHbox.setAlignment(Pos.CENTER);
        staningPointBAzimuthHbox.setSpacing(5);
        staningPointBAzimuthHbox.setPadding(new Insets(10,10,10,10));
        staningPointBAzimuthHbox.getChildren().addAll(horizontalText, standingBPointAzimuthAngleField,
                angleHzText, standingBPointAzimuthMinField, minHzText, standingBPointAzimuthSecField, secHzText);
        vBox.getChildren().addAll(staningPointBAzimuthHbox);

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
        HBox staningPointBElevationHbox = new HBox();
        staningPointBElevationHbox.setAlignment(Pos.CENTER);
        staningPointBElevationHbox.setSpacing(5);
        staningPointBElevationHbox.setPadding(new Insets(10,10,10,10));
        staningPointBElevationHbox.getChildren().addAll(verticalText, standingBPointElevationAngleField,
                angleVzText, standingBPointElevationMinField, minVzText,
                standingBPointElevationSecField, secVzText);
        vBox.getChildren().addAll(staningPointBElevationHbox);

    }
    private void addCalcButton(){
        Button calcButton = new Button("Számol");
        calcButton.setOnMouseClicked(e -> measuredPillarDataController.onClickCountButtonForIntersectionProcess());
        calcButton.setCursor(Cursor.HAND);
        calcButton.setFont(boldFont);
        HBox calcButtonHbox = new HBox();
        calcButtonHbox.setPadding(new Insets(20,20,20,20));
        calcButtonHbox.setAlignment(Pos.CENTER);
        calcButtonHbox.getChildren().add(calcButton);
        vBox.getChildren().add(calcButtonHbox);
    }

}

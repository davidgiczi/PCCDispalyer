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
        //addCalcButton();
        pane.getChildren().add(vBox);
        Scene scene = new Scene(pane);
        stage.setWidth(400);
        stage.setHeight(680);
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
        leftLine.setEndY(600);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(355);
        rightLine.setEndX(380);
        rightLine.setEndY(600);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(600);
        bottomLine.setEndX(380);
        bottomLine.setEndY(600);
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
        standingAPointIdTextHbox.setSpacing(35);
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

        Text standingAPointY = new Text("X koordináta [m]:");
        standingAPointY.setFont(boldFont);

        /*HBox xCoordHbox = new HBox();
        xCoordHbox.setPadding(new Insets(5,5,5,5));
        xCoordHbox.setSpacing(40);
        xCoordHbox.setAlignment(Pos.CENTER);
        xCoordHbox.getChildren().addAll(xCoordText, directionPillarField_X);
        vBox.getChildren().addAll(standingAPointIdTextHbox,
                xCoordHbox, standingAPointXHbox);*/
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

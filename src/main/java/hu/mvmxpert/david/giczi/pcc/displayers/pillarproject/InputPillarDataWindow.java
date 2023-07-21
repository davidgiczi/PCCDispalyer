package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import hu.mvmxpert.david.giczi.pcc.displayers.model.MeasPoint;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

import java.util.List;
import java.util.Optional;

public class InputPillarDataWindow {

    private final AnchorPane pane;
    private final VBox vBox;
    private final Color color = Color.rgb(112,128,144);
    private final Font normalFont = Font.font("Arial", FontWeight.NORMAL, 14);
    private final Font boldFont = Font.font("Arial", FontWeight.BOLD, 13);
    private TextField projectNameField;
    private TextField projectPathField;
    private TextField centerPillarIDField;
    private TextField centerPillarField_X;
    private TextField centerPillarField_Y;
    private TextField rotationAngleField;
    private TextField rotationMinField;;
    private TextField rotationSecField;
    private TextField directionPillarIDField;
    private TextField directionPillarField_X;
    private TextField directionPillarField_Y;


    public InputPillarDataWindow(){
        Stage stage = new Stage();
        pane = new AnchorPane();
        vBox = new VBox();
        pane.setStyle("-fx-background-color: white");
        addProjectDataFields();
        addCenterPillarDataFields();
        addDirectionPillarDataFields();
        addCalcButton();
        pane.getChildren().add(vBox);
        Scene scene = new Scene(pane);
        stage.setWidth(400);
        stage.setHeight(580);
        stage.setTitle(FileProcess.MEAS_FILE_NAME);
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void addProjectDataFields(){
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
        leftLine.setEndY(170);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(10);
        rightLine.setEndX(380);
        rightLine.setEndY(170);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(170);
        bottomLine.setEndX(380);
        bottomLine.setEndY(170);
        pane.getChildren().addAll(leftTopLine, rightTopLine, leftLine, rightLine, bottomLine);
        Text projectDataText = new Text("Projekt adatainak megadása");
        projectDataText.setFont(normalFont);
        projectDataText.setFill(color);
        HBox projectDataTextHbox = new HBox();
        projectDataTextHbox.setAlignment(Pos.CENTER);
        projectDataTextHbox.getChildren().add(projectDataText);
        projectNameField = new TextField();
        projectNameField.setCursor(Cursor.HAND);
        projectNameField.setFont(normalFont);
        projectNameField.setStyle("-fx-text-inner-color: #708090; -fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        projectNameField.setPrefColumnCount(15);
        Text projectNameText = new Text("A projekt neve:");
        projectNameText.setFont(boldFont);
        HBox projectNameHbox = new HBox();
        projectNameHbox.setAlignment(Pos.BASELINE_LEFT);
        projectNameHbox.setPadding(new Insets(10,10,10,10));
        projectNameHbox.setSpacing(50);
        projectNameHbox.getChildren().addAll(projectNameText, projectNameField);
        HBox filePathTextHbox = new HBox();
        filePathTextHbox.setPadding(new Insets(10,10,10,10));
        filePathTextHbox.setAlignment(Pos.CENTER);
        Text filePathText = new Text("Mentési mappa választása");
        filePathText.setFont(boldFont);
        filePathTextHbox.getChildren().add(filePathText);
        projectPathField = new TextField();
        projectPathField.setCursor(Cursor.HAND);
        projectPathField.setPrefColumnCount(26);
        projectPathField.setStyle("-fx-text-inner-color: #708090; -fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        projectPathField.setFont(normalFont);
        HBox projectPathHbox = new HBox();
        projectPathHbox.setPadding(new Insets(5,5,5,5));
        projectPathHbox.setAlignment(Pos.CENTER);
        projectPathHbox.getChildren().add(projectPathField);
        Button browseButton = new Button("Tallóz");
        browseButton.setCursor(Cursor.HAND);
        browseButton.setFont(boldFont);
        HBox browseButtonHbox = new HBox();
        browseButtonHbox.setAlignment(Pos.CENTER);
        browseButtonHbox.getChildren().add(browseButton);
        vBox.getChildren().addAll(projectDataTextHbox, projectNameHbox,
                filePathTextHbox, projectPathHbox, browseButtonHbox);
    }

    private void addCenterPillarDataFields(){
        Line leftTopLine = new Line();
        leftTopLine.setStroke(color);
        leftTopLine.setStartX(5);
        leftTopLine.setStartY(180);
        leftTopLine.setEndX(80);
        leftTopLine.setEndY(180);
        Line rightTopLine = new Line();
        rightTopLine.setStroke(color);
        rightTopLine.setStartX(290);
        rightTopLine.setStartY(180);
        rightTopLine.setEndX(380);
        rightTopLine.setEndY(180);
        Line leftLine = new Line();
        leftLine.setStroke(color);
        leftLine.setStartX(5);
        leftLine.setStartY(180);
        leftLine.setEndX(5);
        leftLine.setEndY(370);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(180);
        rightLine.setEndX(380);
        rightLine.setEndY(370);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(370);
        bottomLine.setEndX(380);
        bottomLine.setEndY(370);
        Text projectDataText = new Text("Oszlop adatainak megadása");
        projectDataText.setFont(normalFont);
        projectDataText.setFill(color);
        HBox centerPillarTextHbox = new HBox();
        centerPillarTextHbox.setAlignment(Pos.CENTER);
        centerPillarTextHbox.setPadding(new Insets(15,15,15,15));
        centerPillarTextHbox.getChildren().add(projectDataText);
        vBox.getChildren().add(centerPillarTextHbox);
        pane.getChildren().addAll(leftTopLine, rightTopLine, leftLine, rightLine, bottomLine);

        HBox pillarIDTextHbox = new HBox();
        pillarIDTextHbox.setPadding(new Insets(5,5,5,5));
        pillarIDTextHbox.setAlignment(Pos.CENTER);
        pillarIDTextHbox.setSpacing(35);
        Text pillarIDText = new Text("Az oszlop száma:");
        pillarIDText.setFont(boldFont);
        centerPillarIDField = new TextField();
        centerPillarIDField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        centerPillarIDField.setPrefColumnCount(15);
        centerPillarIDField.setFont(normalFont);
        centerPillarIDField.setCursor(Cursor.HAND);
        pillarIDTextHbox.getChildren().addAll(pillarIDText, centerPillarIDField );
        HBox yCoordHbox = new HBox();
        yCoordHbox.setPadding(new Insets(5,5,5,5));
        yCoordHbox.setSpacing(40);
        yCoordHbox.setAlignment(Pos.CENTER);
        Text yCoordText = new Text("Y koordináta [m]:");
        yCoordText.setFont(boldFont);
        centerPillarField_Y = new TextField();
        centerPillarField_Y.setCursor(Cursor.HAND);
        centerPillarField_Y.setFont(normalFont);
        centerPillarField_Y.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        centerPillarField_Y.setPrefColumnCount(15);
        centerPillarField_Y.setFont(normalFont);
        yCoordHbox.getChildren().addAll(yCoordText, centerPillarField_Y);
        HBox xCoordHbox = new HBox();
        xCoordHbox.setPadding(new Insets(5,5,5,5));
        xCoordHbox.setSpacing(40);
        xCoordHbox.setAlignment(Pos.CENTER);
        Text xCoordText = new Text("X koordináta [m]:");
        xCoordText.setFont(boldFont);
        centerPillarField_X = new TextField();
        centerPillarField_X.setCursor(Cursor.HAND);
        centerPillarField_X.setFont(normalFont);
        centerPillarField_X.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        centerPillarField_X.setPrefColumnCount(15);
        centerPillarField_X.setFont(normalFont);
        xCoordHbox.getChildren().addAll(xCoordText, centerPillarField_X);
        Text rotationText = new Text("A nyomvonal által bezárt jobb oldali szög");
        rotationText.setFont(boldFont);
        HBox rotationTextHbox = new HBox();
        rotationTextHbox.setAlignment(Pos.CENTER);
        rotationTextHbox.getChildren().add(rotationText);
        Text angleText = new Text("fok");
        angleText.setFont(boldFont);
        Text minText = new Text("perc");
        minText.setFont(boldFont);
        Text secText = new Text("mperc");
        secText.setFont(boldFont);
        rotationAngleField = new TextField();
        rotationAngleField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        rotationAngleField.setFont(normalFont);
        rotationAngleField.setCursor(Cursor.HAND);
        rotationAngleField.setPrefColumnCount(3);
        rotationMinField = new TextField();
        rotationMinField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        rotationMinField.setFont(normalFont);
        rotationMinField.setCursor(Cursor.HAND);
        rotationMinField.setPrefColumnCount(3);
        rotationSecField = new TextField();
        rotationSecField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        rotationSecField.setFont(normalFont);
        rotationSecField.setCursor(Cursor.HAND);
        rotationSecField.setPrefColumnCount(3);
        HBox rotationHbox = new HBox();
        rotationHbox.setAlignment(Pos.CENTER);
        rotationHbox.setSpacing(5);
        rotationHbox.setPadding(new Insets(10,10,10,10));
        rotationHbox.getChildren().addAll(rotationAngleField, angleText,
       rotationMinField, minText, rotationSecField, secText);

        vBox.getChildren().addAll(pillarIDTextHbox, xCoordHbox,
                yCoordHbox, rotationTextHbox, rotationHbox);
    }

    private void addDirectionPillarDataFields(){
        Line leftTopLine = new Line();
        leftTopLine.setStroke(color);
        leftTopLine.setStartX(5);
        leftTopLine.setStartY(380);
        leftTopLine.setEndX(40);
        leftTopLine.setEndY(380);
        Line rightTopLine = new Line();
        rightTopLine.setStroke(color);
        rightTopLine.setStartX(340);
        rightTopLine.setStartY(380);
        rightTopLine.setEndX(380);
        rightTopLine.setEndY(380);
        Line leftLine = new Line();
        leftLine.setStroke(color);
        leftLine.setStartX(5);
        leftLine.setStartY(380);
        leftLine.setEndX(5);
        leftLine.setEndY(500);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(380);
        rightLine.setEndX(380);
        rightLine.setEndY(500);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(500);
        bottomLine.setEndX(380);
        bottomLine.setEndY(500);
        Text projectDataText = new Text("A következő/előző oszlop adatainak megadása");
        projectDataText.setFont(normalFont);
        projectDataText.setFill(color);
        HBox directionPillarTextHbox = new HBox();
        directionPillarTextHbox.setAlignment(Pos.CENTER);
        directionPillarTextHbox.getChildren().add(projectDataText);
        vBox.getChildren().add(directionPillarTextHbox);
        pane.getChildren().addAll(leftTopLine, rightTopLine,
                 leftLine, rightLine, bottomLine);
        HBox pillarIDTextHbox = new HBox();
        pillarIDTextHbox.setPadding(new Insets(5,5,5,5));
        pillarIDTextHbox.setAlignment(Pos.CENTER);
        pillarIDTextHbox.setSpacing(35);
        Text pillarIDText = new Text("Az oszlop száma:");
        pillarIDText.setFont(boldFont);
        directionPillarIDField = new TextField();
        directionPillarIDField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        directionPillarIDField.setPrefColumnCount(15);
        directionPillarIDField.setFont(normalFont);
        directionPillarIDField.setCursor(Cursor.HAND);
        pillarIDTextHbox.getChildren().addAll(pillarIDText, directionPillarIDField);
        HBox yCoordHbox = new HBox();
        yCoordHbox.setPadding(new Insets(5,5,5,5));
        yCoordHbox.setSpacing(40);
        yCoordHbox.setAlignment(Pos.CENTER);
        Text yCoordText = new Text("Y koordináta [m]:");
        yCoordText.setFont(boldFont);
        directionPillarField_Y = new TextField();
        directionPillarField_Y.setCursor(Cursor.HAND);
        directionPillarField_Y.setFont(normalFont);
        directionPillarField_Y.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        directionPillarField_Y.setPrefColumnCount(15);
        directionPillarField_Y.setFont(normalFont);
        yCoordHbox.getChildren().addAll(yCoordText, directionPillarField_Y);
        HBox xCoordHbox = new HBox();
        xCoordHbox.setPadding(new Insets(5,5,5,5));
        xCoordHbox.setSpacing(40);
        xCoordHbox.setAlignment(Pos.CENTER);
        Text xCoordText = new Text("X koordináta [m]:");
        xCoordText.setFont(boldFont);
        directionPillarField_X = new TextField();
        directionPillarField_X.setCursor(Cursor.HAND);
        directionPillarField_X.setFont(normalFont);
        directionPillarField_X.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        directionPillarField_X.setPrefColumnCount(15);
        directionPillarField_X.setFont(normalFont);
        xCoordHbox.getChildren().addAll(xCoordText, directionPillarField_X);
        vBox.getChildren().addAll(pillarIDTextHbox,
                xCoordHbox, yCoordHbox);
    }

    private void addCalcButton(){
    Button calcButton = new Button("Számol");
    calcButton.setCursor(Cursor.HAND);
    calcButton.setFont(boldFont);
    HBox calcButtonHbox = new HBox();
    calcButtonHbox.setPadding(new Insets(20,20,20,20));
    calcButtonHbox.setAlignment(Pos.CENTER);
    calcButtonHbox.getChildren().add(calcButton);
    vBox.getChildren().add(calcButtonHbox);
    }

}

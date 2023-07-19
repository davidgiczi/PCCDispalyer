package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

public class InputPillarDataWindow {

    private final AnchorPane pane;
    private final Color color = Color.rgb(112,128,144);
    private final Font normalFont = Font.font("Arial", FontWeight.NORMAL, 14);
    private final Font boldFont = Font.font("Arial", FontWeight.BOLD, 13);
    private TextField projectNameField;
    private TextField centerPillar_X;
    private TextField centerPillar_Y;
    private TextField directionPillarID;
    private TextField directionPillar_X;
    private TextField directionPillar_Y;
    private TextField rotationAngle;
    private TextField rotationMin;;
    private TextField rotationSec;


    public InputPillarDataWindow(){
        Stage stage = new Stage();
        pane = new AnchorPane();
        pane.setStyle("-fx-background-color: white");
        addProjectDataFields();
        Scene scene = new Scene(pane);
        stage.setWidth(400);
        stage.setHeight(500);
        stage.setTitle(FileProcess.PROJECT_FILE_NAME);
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
        leftLine.setEndY(200);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(10);
        rightLine.setEndX(380);
        rightLine.setEndY(200);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(200);
        bottomLine.setEndX(380);
        bottomLine.setEndY(200);

        pane.getChildren().addAll(leftTopLine, rightTopLine, leftLine, rightLine, bottomLine);
        VBox vBox = new VBox();
        Text projectDataText = new Text("Projekt adatainak megadása");
        projectDataText.setFont(normalFont);
        projectDataText.setFill(color);
        HBox centerPillarTextHbox = new HBox();
        centerPillarTextHbox.setAlignment(Pos.CENTER);
        centerPillarTextHbox.getChildren().add(projectDataText);
        projectNameField = new TextField();
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
        vBox.getChildren().addAll(centerPillarTextHbox, projectNameHbox);
        pane.getChildren().add(vBox);
    }
}

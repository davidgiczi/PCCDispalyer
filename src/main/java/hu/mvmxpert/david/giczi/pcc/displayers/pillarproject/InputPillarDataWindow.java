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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class InputPillarDataWindow {

    private final AnchorPane pane;
    private final Color textColor = Color.rgb(112,128,144);
    private final Font font = Font.font("Arial", FontWeight.BOLD, 14);
    private TextField centerPillarIDField;
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
        addInputFields();
        Scene scene = new Scene(pane);
        stage.setWidth(400);
        stage.setHeight(500);
        stage.setTitle(FileProcess.PROJECT_FILE_NAME);
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void addInputFields(){
        VBox vBox = new VBox();
        Text centerPillarText = new Text("Az oszlop középpontjának megadása");
        centerPillarText.setFont(font);
        HBox centerPillarTextHbox = new HBox();
        centerPillarTextHbox.setAlignment(Pos.CENTER);
        centerPillarTextHbox.getChildren().add(centerPillarText);
        centerPillarIDField = new TextField();
        centerPillarIDField.setFont(font);
        centerPillarIDField.setStyle("-fx-text-inner-color: #708090;");
        Text pillarIDText = new Text("Az oszlop száma:");
        pillarIDText.setFont(font);
        HBox centerPillarHbox = new HBox();
        centerPillarHbox.setAlignment(Pos.BASELINE_LEFT);
        centerPillarHbox.setPadding(new Insets(10,10,10,10));
        centerPillarHbox.setSpacing(70);
        centerPillarHbox.getChildren().addAll(pillarIDText, centerPillarIDField);
        vBox.getChildren().addAll(centerPillarTextHbox, centerPillarHbox);
        pane.getChildren().add(vBox);
    }
}

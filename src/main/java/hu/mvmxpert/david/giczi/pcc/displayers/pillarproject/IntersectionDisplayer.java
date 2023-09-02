package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import hu.mvmxpert.david.giczi.pcc.displayers.model.MeasPoint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class IntersectionDisplayer {


    private final AnchorPane pane = new AnchorPane();
    public MeasuredPillarDataController measuredPillarDataController;
    private List<MeasPoint> transformedPillarBasePoints;
    private ComboBox<String> scaleComboBox;
    private static final double MILLIMETER = 1000.0 / 225.0;
    private static double SCALE;
    private final Font normalFont = Font.font("Arial", FontWeight.NORMAL, 14);
    private final Font boldFont = Font.font("Arial", FontWeight.BOLD, 16);

    public IntersectionDisplayer(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        SCALE = 1000;
        Stage stage = new Stage();
        stage.initOwner(measuredPillarDataController.fxHomeWindow.homeStage);
        stage.setOnCloseRequest(windowEvent -> {
            measuredPillarDataController.fxHomeWindow.homeStage.show();
        });
        pane.setStyle("-fx-background-color: white");
        addContent();
        ScrollPane scrollPane = getScrollPane(pane);
        Scene scene = new Scene(scrollPane);
        stage.setTitle("Előmetszés");
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setMinWidth(1050);
        stage.setMinHeight(750);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    private ScrollPane getScrollPane(AnchorPane content){
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scroller;
    }

    private void addContent(){
        addNorthSign();
        addComboBoxForScaleValue();
        addIntersectionPointData();
    }

    private void addNorthSign(){
        ImageView northSign = new ImageView(new Image("file:images/north.jpg"));
        northSign.setFitWidth(40 * MILLIMETER);
        northSign.setFitHeight(40 * MILLIMETER);
        northSign.xProperty().bind(pane.widthProperty().divide(18));
        northSign.setY(5 * MILLIMETER);
        pane.getChildren().add(northSign);
    }

    private void copyText(String text){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    private void addComboBoxForScaleValue(){
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "500",
                        "1000",
                        "2000",
                        "4000",
                        "5000"
                );
        HBox hbox = new HBox();
        scaleComboBox = new ComboBox<>(options);
        scaleComboBox.setValue("1000");
        scaleComboBox
                .setStyle("-fx-background-color: white;-fx-font: 16px \"Book-Antique\";-fx-font-weight: bold;");
        scaleComboBox.setCursor(Cursor.HAND);
        scaleComboBox.setOnAction(e -> setOnActionEvent());
        Label textM = new Label("M= 1: ");
        textM.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 16));
        textM.setPadding(new Insets(5.5, 0 , 0 , 0));
        hbox.getChildren().addAll(textM, scaleComboBox);
        hbox.setLayoutX(5 * MILLIMETER);
        hbox.setLayoutY(2 * MILLIMETER);
        pane.getChildren().add(hbox);
    }
    private void setOnActionEvent(){
        String selectedScale = scaleComboBox.getSelectionModel().getSelectedItem();
        SCALE = Integer.parseInt(selectedScale);
        pane.getChildren().clear();
        addContent();
        scaleComboBox.setValue(selectedScale);
    }

    private void addIntersectionPointData(){
        Text idText = new Text(measuredPillarDataController
                .intersection.getIntersectionPoint().getPointID());
        idText.xProperty().bind(pane.widthProperty().divide(22).multiply(4));
        idText.setY(10 * MILLIMETER);
        idText.setFont(boldFont);
        idText.setFill(Color.MAGENTA);
        Text newPointXText = new Text("Y (mért)");
        newPointXText.setFont(boldFont);
        newPointXText.xProperty().bind(pane.widthProperty().divide(21).multiply(6));
        newPointXText.setY(5 * MILLIMETER);
        Text newPointX = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPoint().getX_coord()).replace(",", "."));
        newPointX.xProperty().bind(pane.widthProperty().divide(22).multiply(6));
        newPointX.setY(10 * MILLIMETER);
        newPointX.setFont(normalFont);
        Text newPointYText = new Text("X (mért)");
        newPointYText.setFont(boldFont);
        newPointYText.xProperty().bind(pane.widthProperty().divide(21).multiply(8));
        newPointYText.setY(5 * MILLIMETER);
        Text newPointY = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPoint().getY_coord()).replace(",", "."));
        newPointY.setFont(normalFont);
        newPointY.xProperty().bind(pane.widthProperty().divide(22).multiply(8));
        newPointY.setY(10 * MILLIMETER);
        Text newPointZText = new Text("h (mért)");
        newPointZText.setFont(boldFont);
        newPointZText.xProperty().bind(pane.widthProperty().divide(21).multiply(10));
        newPointZText.setY(5 * MILLIMETER);
        Text newPointZ = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPoint().getZ_coord()).replace(",", "."));
        newPointZ.setFont(normalFont);
        newPointZ.xProperty().bind(pane.widthProperty().divide(22).multiply(10));
        newPointZ.setY(10 * MILLIMETER);
        Text deltaXText = new Text("ΔY [cm]");
        deltaXText.setFont(boldFont);
        deltaXText.xProperty().bind(pane.widthProperty().divide(21).multiply(12));
        deltaXText.setY(5 * MILLIMETER);
        Text deltaX = new Text(String.format("%20.1f", 100 * (measuredPillarDataController
                .intersection.getHalfLinePointData().getX_coord() -
                measuredPillarDataController
                .intersection.getIntersectionPoint().getX_coord()
                )).replace(",", "."));
        deltaX.xProperty().bind(pane.widthProperty().divide(22).multiply(12));
        deltaX.setY(10 * MILLIMETER);
        deltaX.setFont(normalFont);
        Text deltaYText = new Text("ΔX [cm]");
        deltaYText.setFont(boldFont);
        deltaYText.xProperty().bind(pane.widthProperty().divide(21).multiply(14));
        deltaYText.setY(5 * MILLIMETER);
        Text deltaY = new Text(String.format("%20.1f", 100 * (measuredPillarDataController
                .intersection.getHalfLinePointData().getY_coord() -
                measuredPillarDataController
                        .intersection.getIntersectionPoint().getY_coord()
        )).replace(",", "."));
        deltaY.xProperty().bind(pane.widthProperty().divide(22).multiply(14));
        deltaY.setY(10 * MILLIMETER);
        deltaY.setFont(normalFont);
        Text deltaZText = new Text("Δh [cm]");
        deltaZText.setFont(boldFont);
        deltaZText.xProperty().bind(pane.widthProperty().divide(21).multiply(16));
        deltaZText.setY(5 * MILLIMETER);
        Text deltaZ = new Text(String.format("%+20.1f", 100 * (measuredPillarDataController
                .intersection.getIntersectionPointFromA().getZ_coord()
                - measuredPillarDataController.intersection.getIntersectionPointFromB().getZ_coord())
        ).replace(",", "."));
        deltaZ.xProperty().bind(pane.widthProperty().divide(22).multiply(16));
        deltaZ.setY(10 * MILLIMETER);
        deltaZ.setFont(normalFont);
        pane.getChildren().addAll(idText, newPointXText, newPointX,
                newPointYText, newPointY, newPointZText, newPointZ,
                deltaXText, deltaX, deltaYText, deltaY, deltaZText, deltaZ);
        setDataToClipboard();
    }

    private void setDataToClipboard(){

    }
}

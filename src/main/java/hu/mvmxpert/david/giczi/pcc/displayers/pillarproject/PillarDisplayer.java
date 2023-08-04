package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import hu.mvmxpert.david.giczi.pcc.displayers.model.MeasPoint;
import hu.mvmxpert.david.giczi.pcc.displayers.model.Point;
import hu.mvmxpert.david.giczi.pcc.displayers.service.AzimuthAndDistance;
import hu.mvmxpert.david.giczi.pcc.displayers.service.PolarPoint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PillarDisplayer {
    private final AnchorPane pane = new AnchorPane();
    public MeasuredPillarDataController measuredPillarDataController;
    private List<MeasPoint> transformedPillarBasePoints;
    private ComboBox<String> scaleComboBox;
    private static final double MILLIMETER = 1000.0 / 225.0;
    private static double SCALE;
    private final Font normalFont = Font.font("Arial", FontWeight.NORMAL, 14);
    private final Font boldFont = Font.font("Arial", FontWeight.BOLD, 16);


    public PillarDisplayer(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        SCALE = 100;
        Stage stage = new Stage();
        stage.setOnCloseRequest(windowEvent -> {
            measuredPillarDataController.fxHomeWindow.homeStage.show();
            measuredPillarDataController.init();
        });
        pane.setStyle("-fx-background-color: white");
        pane.setOnMouseClicked(mouseEvent -> {
            if( mouseEvent.getButton() == MouseButton.SECONDARY ){
           if( measuredPillarDataController.getConfirmationAlert(
                   "Az oszlop magassági adatainak láthatósága",
                   "Láthatók legyenek az oszlop magasságára, dőlésére vonatkozó adatok?") ){

           }
            }
        });
        addContent();
        ScrollPane scrollPane = getScrollPane(pane);
        Scene scene = new Scene(scrollPane);
        stage.setTitle(FileProcess.FOLDER_PATH + "\\" + FileProcess.PROJECT_FILE_NAME + ".plr");
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
        addCenterPillarData();
        getTransformedPillarBaseCoordsForDisplayer();
        addBase();
        addCircleForBasePoints();
        addPreviousAndNextPillarDirections();
        addComboBoxForScaleValue();
    }

    private void addNorthSign(){
        ImageView northSign = new ImageView(new Image("file:images/north.jpg"));
        northSign.setFitWidth(40 * MILLIMETER);
        northSign.setFitHeight(40 * MILLIMETER);
        northSign.xProperty().bind(pane.widthProperty().divide(20));
        northSign.setY(5 * MILLIMETER);
        pane.getChildren().add(northSign);
    }

    private void addCenterPillarData(){
      Text idText = new Text(measuredPillarDataController
                .measuredPillarData
                .getPillarCenterPoint()
                .getPointID());
        idText.xProperty().bind(pane.widthProperty().divide(22).multiply(5));
        idText.setY(10 * MILLIMETER);
        idText.setFont(boldFont);
        idText.setFill(Color.MAGENTA);
        Text designedXText = new Text("EOV Y (tervezett)");
        designedXText.setFont(boldFont);
        designedXText.xProperty().bind(pane.widthProperty().divide(21).multiply(6));
        designedXText.setY(5 * MILLIMETER);
        Text designedX = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarCenterPoint().getX_coord()).replace(",", "."));
        designedX.xProperty().bind(pane.widthProperty().divide(22).multiply(6));
        designedX.setY(10 * MILLIMETER);
        designedX.setFont(normalFont);
        Text designedYText = new Text("EOV X (tervezett)");
        designedYText.setFont(boldFont);
        designedYText.xProperty().bind(pane.widthProperty().divide(21).multiply(8));
        designedYText.setY(5 * MILLIMETER);
        Text designedY = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarCenterPoint().getY_coord()).replace(",", "."));
        designedY.setFont(normalFont);
        designedY.xProperty().bind(pane.widthProperty().divide(22).multiply(8));
        designedY.setY(10 * MILLIMETER);
        Text measXText = new Text("EOV X (mért)");
        measXText.setFont(boldFont);
        measXText.xProperty().bind(pane.widthProperty().divide(21).multiply(10));
        measXText.setY(5 * MILLIMETER);
        Text measX = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarBaseCenterPoint().getX_coord()).replace(",", "."));
       measX.xProperty().bind(pane.widthProperty().divide(22).multiply(10));
       measX.setY(10 * MILLIMETER);
       measX.setFont(normalFont);
        Text measYText = new Text("EOV Y (mért)");
        measYText.setFont(boldFont);
        measYText.xProperty().bind(pane.widthProperty().divide(21).multiply(12));
        measYText.setY(5 * MILLIMETER);
       Text measY = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarBaseCenterPoint().getY_coord()).replace(",", "."));
        measY.xProperty().bind(pane.widthProperty().divide(22).multiply(12));
        measY.setY(10 * MILLIMETER);
        measY.setFont(normalFont);
        Text deltaXText = new Text("ΔY [cm]");
        deltaXText.setFont(boldFont);
        deltaXText.xProperty().bind(pane.widthProperty().divide(21).multiply(14));
        deltaXText.setY(5 * MILLIMETER);
        Text deltaX = new Text(String.format("%+20.1f", 100 * (measuredPillarDataController
                .measuredPillarData.getPillarCenterPoint().getX_coord()
                - measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord())
        ).replace(",", "."));
        deltaX.xProperty().bind(pane.widthProperty().divide(22).multiply(14));
        deltaX.setY(10 * MILLIMETER);
        deltaX.setFont(normalFont);
        Text deltaYText = new Text("ΔX [cm]");
        deltaYText.setFont(boldFont);
        deltaYText.xProperty().bind(pane.widthProperty().divide(21).multiply(16));
        deltaYText.setY(5 * MILLIMETER);
        Text deltaY = new Text(String.format("%+20.1f", 100 * (measuredPillarDataController
                .measuredPillarData.getPillarCenterPoint().getY_coord()
                - measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord())
        ).replace(",", "."));
        deltaY.xProperty().bind(pane.widthProperty().divide(22).multiply(16));
        deltaY.setY(10 * MILLIMETER);
        deltaY.setFont(normalFont);
        pane.getChildren().addAll(idText, designedXText, designedX,
                designedYText, designedY, measXText, measX, measYText, measY,
                deltaXText, deltaX, deltaYText, deltaY);
        copyText( idText.getText() + "\t" +
                String.format("%10.3f", measuredPillarDataController
                        .measuredPillarData.getPillarCenterPoint()
                        .getX_coord()).replace(",", ".") + "\t" +
                String.format("%10.3f", measuredPillarDataController
                        .measuredPillarData.getPillarCenterPoint()
                        .getY_coord()).replace(",", ".") + "\t" +
                String.format("%10.3f", measuredPillarDataController
                        .measuredPillarData.getPillarBaseCenterPoint()
                        .getX_coord()).replace(",", ".") + "\t" +
                String.format("%10.3f", measuredPillarDataController
                        .measuredPillarData.getPillarBaseCenterPoint()
                        .getY_coord()).replace(",", ".") + "\t" +
                String.format("%+3.1f", 100 * (measuredPillarDataController
                        .measuredPillarData.getPillarCenterPoint().getX_coord()
                        - measuredPillarDataController.measuredPillarData
                        .getPillarBaseCenterPoint().getX_coord())).replace(",", ".") + "\t" +
                String.format("%+3.1f", 100 * (measuredPillarDataController
                        .measuredPillarData.getPillarCenterPoint().getY_coord()
                        - measuredPillarDataController.measuredPillarData
                        .getPillarBaseCenterPoint().getY_coord())).replace(",", "."));
    }

    private void copyText(String text){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    private void getTransformedPillarBaseCoordsForDisplayer() {
        transformedPillarBasePoints = new ArrayList<>();
        double X = measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord();
        double Y = measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord();
        for (MeasPoint pillarBasePoint : measuredPillarDataController.measuredPillarData.getPillarBasePoints()) {
            MeasPoint point = new MeasPoint(pillarBasePoint.getPointID(),
                    Math.round((pillarBasePoint.getX_coord() - X) * 1000.0) * MILLIMETER / SCALE,
                    Math.round((pillarBasePoint.getY_coord() - Y) * 1000.0) * MILLIMETER / SCALE,
                    pillarBasePoint.getZ_coord(), PointType.ALAP);
        transformedPillarBasePoints.add(point);
        }
    }

    private void addBase(){
        if( transformedPillarBasePoints.size() != 4 ){
            measuredPillarDataController.getInfoAlert("Nem megfelelő bemeneti adatok",
                    "Az oszlop alapja nem ábrázolható.");
            return;
        }
        Line line1 = new Line();
        line1.setStroke(Color.BLUE);
        line1.setStrokeWidth(2);
        line1.startXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(transformedPillarBasePoints.get(0).getX_coord()));
        line1.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(0).getY_coord()));
        line1.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(transformedPillarBasePoints.get(1).getX_coord()));
        line1.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(1).getY_coord()));
        Line line2 = new Line();
        line2.setStroke(Color.BLUE);
        line2.setStrokeWidth(2);
        line2.startXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(transformedPillarBasePoints.get(1).getX_coord()));
        line2.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(1).getY_coord()));
        line2.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(transformedPillarBasePoints.get(2).getX_coord()));
        line2.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(2).getY_coord()));
        Line line3 = new Line();
        line3.setStroke(Color.BLUE);
        line3.setStrokeWidth(2);
        line3.startXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(transformedPillarBasePoints.get(2).getX_coord()));
        line3.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(2).getY_coord()));
        line3.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(transformedPillarBasePoints.get(3).getX_coord()));
        line3.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(3).getY_coord()));
        Line line4 = new Line();
        line4.setStroke(Color.BLUE);
        line4.setStrokeWidth(2);
        line4.startXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(transformedPillarBasePoints.get(3).getX_coord()));
        line4.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(3).getY_coord()));
        line4.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(transformedPillarBasePoints.get(0).getX_coord()));
        line4.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(0).getY_coord()));
        pane.getChildren().addAll(line1, line2, line3, line4);
    }
    private void addComboBoxForScaleValue(){
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "50",
                        "100",
                        "200",
                        "400",
                        "500"
                );
        HBox hbox = new HBox();
        scaleComboBox = new ComboBox<>(options);
        scaleComboBox.setValue("100");
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

    private void addCircleForBasePoints(){
        for (MeasPoint transformedPillarBasePoint : transformedPillarBasePoints) {
            Circle circle = new Circle();
            circle.setRadius(5);
            circle.setStroke(Color.MAGENTA);
            circle.setStrokeWidth(2);
            circle.setFill(Color.TRANSPARENT);
            circle.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                    .add(transformedPillarBasePoint.getX_coord()));
            circle.centerYProperty().bind(pane.heightProperty().divide(2)
                    .subtract(transformedPillarBasePoint.getY_coord()));
            Tooltip tooltip = new Tooltip(transformedPillarBasePoint.getPointID());
            Tooltip.install(circle, tooltip);
            circle.setCursor(Cursor.HAND);
            pane.getChildren().add(circle);
        }
    }
    private void addPreviousAndNextPillarDirections(){
        Point transformedPillarCenterPoint =  new Point("pillarCenterPoint", 0.0, 0.0);
        Point transformedDirectionPillarPoint = new Point("transformedDirectionPoint",
                Math.round((measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getX_coord() -
                        measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getX_coord()) * 1000.0)
                        * MILLIMETER / SCALE,
                Math.round((measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getY_coord() -
                        measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getY_coord()) * 1000.0)
                        * MILLIMETER / SCALE
                );
            AzimuthAndDistance mainLineDirection =
                    new AzimuthAndDistance(transformedPillarCenterPoint, transformedDirectionPillarPoint);
            PolarPoint slavePoint = new PolarPoint(transformedPillarCenterPoint,
                100 * MILLIMETER, mainLineDirection.calcAzimuth(),
                "forwardDirection");

            Line forwardDirection = new Line();
            forwardDirection.setStrokeWidth(2);
            forwardDirection.setStroke(Color.MAGENTA);
            forwardDirection.startXProperty()
                    .bind(pane.widthProperty()
                            .divide(10)
                            .multiply(5));
            forwardDirection.startYProperty()
                    .bind(pane.heightProperty()
                            .divide(2));
            forwardDirection.endXProperty()
                    .bind(pane.widthProperty()
                            .divide(10)
                            .multiply(5).add(slavePoint.calcPolarPoint().getX_coord()));
            forwardDirection.endYProperty()
                    .bind(pane.heightProperty()
                            .divide(2)
                            .subtract(slavePoint.calcPolarPoint().getY_coord()));

            addArrow(transformedPillarCenterPoint, transformedDirectionPillarPoint);
            pane.getChildren().add(forwardDirection);


    }

    private void addArrow(Point startPoint, Point endPoint){
        AzimuthAndDistance baseLineData = new AzimuthAndDistance(startPoint, endPoint);
        PolarPoint slavePoint1 = new PolarPoint(startPoint, 5 * MILLIMETER,
                baseLineData.calcAzimuth() + Math.PI / 6, "arrow1");
        PolarPoint slavePoint2 = new PolarPoint(startPoint, 5 * MILLIMETER,
                baseLineData.calcAzimuth() - Math.PI / 6, "arrow2");
        Line arrow1 = new Line();
        arrow1.setStroke(Color.MAGENTA);
        arrow1.setStrokeWidth(2);
        arrow1.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(startPoint.getX_coord()));
        arrow1.startYProperty().bind(pane.heightProperty().divide(2)
                .subtract(startPoint.getY_coord()));
        arrow1.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(slavePoint1.calcPolarPoint().getX_coord()));
        arrow1.endYProperty().bind(pane.heightProperty().divide(2)
                .subtract(slavePoint1.calcPolarPoint().getY_coord()));
        Line arrow2 = new Line();
        arrow2.setStroke(Color.MAGENTA);
        arrow2.setStrokeWidth(2);
        arrow2.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(startPoint.getX_coord()));
        arrow2.startYProperty().bind(pane.heightProperty().divide(2)
                .subtract(startPoint.getY_coord()));
        arrow2.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(slavePoint2.calcPolarPoint().getX_coord()));
        arrow2.endYProperty().bind(pane.heightProperty().divide(2)
                .subtract(slavePoint2.calcPolarPoint().getY_coord()));
        pane.getChildren().addAll(arrow1, arrow2);
    }
}

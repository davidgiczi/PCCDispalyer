package hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer;

import hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer.model.Point;
import hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer.service.AzimuthAndDistance;
import hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer.service.PolarPoint;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class WeightBaseDisplayer extends Application {

    private static String TITLE;

    private static List<Point> PILLAR_BASE_POINTS;
    private static Point DIRECTION_POINT;
    private static final double MILLIMETER = 1000.0 / 225.0; // 1mm = 1000/225 JavaUnit
    private static double SCALE = 200;
    private final AnchorPane pane = new AnchorPane();
    private List<Point> transformedPillarBasePoints;
    private int circeID;
    private  ComboBox<String> scaleComboBox;
    private final DecimalFormat df = new DecimalFormat();
    private List<Point> distancePointList;
    private double nextRowValue;

    public static void setTitle(String TITLE) {
        WeightBaseDisplayer.TITLE = TITLE;
    }

    public static void setPillarBasePoints(List<Point> pillarBasePoints) {
        PILLAR_BASE_POINTS = pillarBasePoints;
    }

    public static void setDirectionPoint(Point directionPoint) {
        DIRECTION_POINT = directionPoint;
    }

    @Override
    public void start(Stage stage)  {
        pane.setStyle("-fx-background-color: white");
        getContent();
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if( mouseEvent.getButton() == MouseButton.SECONDARY ){
                   distancePointList.clear();
                   nextRowValue += 5 * MILLIMETER;
                }
            }
        });
        ScrollPane scrollPane = getScrollPane(pane);
        Scene scene = new Scene(scrollPane);
        stage.setTitle(TITLE);
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setMinWidth(1050);
        stage.setMinHeight(750);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    private void getContent(){
        distancePointList = new ArrayList<>();
        nextRowValue  = 5 * MILLIMETER;
        addNorthSign();
        addPointCoordsData();
        addPillarMainAxes();
        addHoleA();
        addHoleB();
        addHoleC();
        addHoleD();
        addNameTextsForHoles();
        addTextsForBase();
        addInformation();
        addCirleForPoint();
        addPreviousAndNextPillarDirections();
        addComboBoxForScaleValue();
    }

    private void addNorthSign(){
        ImageView northSign = new ImageView(new Image("file:images/north.jpg"));
        northSign.setFitWidth(40 * MILLIMETER);
        northSign.setFitHeight(40 * MILLIMETER);
        northSign.xProperty().bind(pane.widthProperty().divide(10).add(50 * MILLIMETER));
        northSign.setY(10 * MILLIMETER);
        pane.getChildren().add(northSign);
    }

    private void addPointCoordsData(){
        double row = 15 * MILLIMETER;
        for (Point point : PILLAR_BASE_POINTS){
            String[] pointIdValues = point.getPointID().split("_");
            Text pointID = new Text(point.getPointID());
            if( pointIdValues.length == 1)
                pointID.setFill(Color.MAGENTA);
            pointID.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 16));
            pointID.setCursor(Cursor.HAND);
            pointID.xProperty().bind(pane.widthProperty().divide(10).subtract(30 * MILLIMETER));
            pointID.setY(row);
            pointID.setId(point.getPointID());
            if( pointIdValues.length ==  2 ) {
                pointID.setOnMouseEntered(e -> onMouseEnteredEvent(pointID));
                pointID.setOnMouseExited(e -> onMouseExitedEvent(pointID));
            }
            Text coords = new Text(point.toString());
            coords.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 16));
            coords.setFill(Color.RED);
            coords.setCursor(Cursor.HAND);
            coords.xProperty().bind(pane.widthProperty().divide(10).subtract(10 * MILLIMETER));
            coords.setY(row);
            coords.setId(point.getPointID());
            if( pointIdValues.length ==  2 ) {
                coords.setOnMouseEntered(e -> onMouseEnteredEvent(coords));
                coords.setOnMouseExited(e -> onMouseExitedEvent(coords));
            }
            row += 6 * MILLIMETER;
            pane.getChildren().addAll(pointID, coords);
        }
        addDirectionPointCoords(row);
    }

    private void onMouseEnteredEvent(Text text){
        text.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 17));
        String[] textIDValues = text.getId().split("_");
        if( textIDValues.length == 1 )
            return;
        Circle circle = (Circle) pane.lookup("#" + textIDValues[1]);
        circle.setStroke(null);
        circle.setRadius(10);
        circle.setFill(Color.RED);
    }
    private void onMouseExitedEvent(Text text){
        text.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 16));
            for(int id = 1; id < circeID; id++) {
                Circle circle = (Circle) pane.lookup("#" + id);
                circle.setStroke(Color.FIREBRICK);
                circle.setStrokeWidth(2);
                circle.setFill(Color.TRANSPARENT);
                circle.setRadius(5);
            }
    }

    private void addDirectionPointCoords(double row){
        Text pointID = new Text(DIRECTION_POINT.getPointID());
        pointID.setFill(Color.MAGENTA);
        pointID.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 16));
        pointID.xProperty().bind(pane.widthProperty().divide(10).subtract(30 * MILLIMETER));
        pointID.setY(row);
        Text coords = new Text(DIRECTION_POINT.toString());
        coords.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 16));
        coords.setFill(Color.RED);
        coords.xProperty().bind(pane.widthProperty().divide(10).subtract(10 * MILLIMETER));
        coords.setY(row);
        pane.getChildren().addAll(pointID, coords);
    }
    private void addPillarMainAxes(){
        getTransformPillarCoordsForDisplayer();
        Line mainAxis = new Line();
        mainAxis.setStroke(Color.RED);
        mainAxis.setStrokeWidth(2);
        mainAxis.getStrokeDashArray().addAll(10d);
        mainAxis.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(1).getX_coord()));
        mainAxis.startYProperty().bind(pane.heightProperty().divide(2)
                .subtract(transformedPillarBasePoints.get(1).getY_coord()));
        mainAxis.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(3).getX_coord()));
        mainAxis.endYProperty().bind(pane.heightProperty().divide(2)
                .subtract(transformedPillarBasePoints.get(3).getY_coord()));
        Line perpendicularAxis = new Line();
        perpendicularAxis.setStroke(Color.RED);
        perpendicularAxis.setStrokeWidth(2);
        perpendicularAxis.getStrokeDashArray().addAll(10d);
        perpendicularAxis.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(2).getX_coord()));
        perpendicularAxis.startYProperty().bind(pane.heightProperty().divide(2)
                .subtract(transformedPillarBasePoints.get(2).getY_coord()));
        perpendicularAxis.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(4).getX_coord()));
        perpendicularAxis.endYProperty().bind(pane.heightProperty().divide(2)
                .subtract(transformedPillarBasePoints.get(4).getY_coord()));
        pane.getChildren().addAll(mainAxis, perpendicularAxis);
    }

    private void addTextsForBase(){
        setText(PILLAR_BASE_POINTS.get(0).getPointID(),
                transformedPillarBasePoints.get(0), Color.MAGENTA, 16 );
        if(PILLAR_BASE_POINTS.size() == 25){
            AzimuthAndDistance mainLineData = new AzimuthAndDistance(transformedPillarBasePoints.get(0),
                    transformedPillarBasePoints.get(1));
            PolarPoint slavePoint = new PolarPoint(transformedPillarBasePoints.get(1),
                    30 * MILLIMETER, mainLineData.calcAzimuth(),
                    "forwardDirection");
            setText(DIRECTION_POINT.getPointID(), slavePoint.calcPolarPoint(), Color.MAGENTA, 16);
        }
        else {
            setText(DIRECTION_POINT.getPointID(),
                    transformedPillarBasePoints.get(25), Color.MAGENTA, 16);
            int mainPillarID;
            int directionPillarID;
            try {
                mainPillarID = Integer.parseInt(PILLAR_BASE_POINTS.get(0).getPointID());
                directionPillarID = Integer.parseInt(DIRECTION_POINT.getPointID());
            } catch (NumberFormatException n) {
                mainPillarID = 0;
                directionPillarID = 1;
            }
            if (directionPillarID > mainPillarID) {
                setText(String.valueOf(mainPillarID - 1),
                        transformedPillarBasePoints.get(26), Color.MAGENTA, 16);
            } else if (directionPillarID < mainPillarID) {
                setText(String.valueOf(mainPillarID + 1),
                        transformedPillarBasePoints.get(26), Color.MAGENTA, 16);
            } else {
                setText(DIRECTION_POINT.getPointID(),
                        transformedPillarBasePoints.get(26), Color.MAGENTA, 16);
            }
        }
    }

    private void addNameTextsForHoles(){
        int mainPillarID;
        int directionPillarID;
        try {
            mainPillarID = Integer.parseInt(PILLAR_BASE_POINTS.get(0).getPointID());
            directionPillarID = Integer.parseInt(DIRECTION_POINT.getPointID());
            if( mainPillarID == directionPillarID )
                return;
        } catch (NumberFormatException n) {
            return;
        }

        int size = (int) (3000 * MILLIMETER / SCALE);
        if( mainPillarID < directionPillarID ){

            AzimuthAndDistance dataA =
                    new AzimuthAndDistance(transformedPillarBasePoints.get(9), transformedPillarBasePoints.get(11));
            PolarPoint posA = new PolarPoint(transformedPillarBasePoints.get(9),
                    1500 * MILLIMETER / SCALE, dataA.calcAzimuth(), "A");
            setText("A",
                    posA.calcPolarPoint(), Color.RED, size);
            AzimuthAndDistance dataB =
                    new AzimuthAndDistance(transformedPillarBasePoints.get(23), transformedPillarBasePoints.get(21));
            PolarPoint posB = new PolarPoint(transformedPillarBasePoints.get(23),
                    1500 * MILLIMETER / SCALE, dataB.calcAzimuth(), "B");
            setText("B",
                    posB.calcPolarPoint(), Color.RED, size);
            AzimuthAndDistance dataC =
                    new AzimuthAndDistance(transformedPillarBasePoints.get(20), transformedPillarBasePoints.get(18));
            PolarPoint posC = new PolarPoint(transformedPillarBasePoints.get(20),
                    1500 * MILLIMETER / SCALE, dataC.calcAzimuth(), "C");
            setText("C",
                    posC.calcPolarPoint(), Color.RED, size);
            AzimuthAndDistance dataD =
                    new AzimuthAndDistance(transformedPillarBasePoints.get(13), transformedPillarBasePoints.get(15));
            PolarPoint posD = new PolarPoint(transformedPillarBasePoints.get(13),
                    1500 * MILLIMETER / SCALE, dataD.calcAzimuth(), "D");
            setText("D",
                    posD.calcPolarPoint(), Color.RED, size);
        }
        else {

            AzimuthAndDistance dataA =
                    new AzimuthAndDistance(transformedPillarBasePoints.get(20), transformedPillarBasePoints.get(18));
            PolarPoint posA = new PolarPoint(transformedPillarBasePoints.get(20),
                    1500 * MILLIMETER / SCALE, dataA.calcAzimuth(), "A");
            setText("A",
                    posA.calcPolarPoint(), Color.RED, size);
            AzimuthAndDistance dataB =
                    new AzimuthAndDistance(transformedPillarBasePoints.get(13), transformedPillarBasePoints.get(15));
            PolarPoint posB = new PolarPoint(transformedPillarBasePoints.get(13),
                    1500 * MILLIMETER / SCALE, dataB.calcAzimuth(), "B");
            setText("B",
                    posB.calcPolarPoint(), Color.RED, size);
            AzimuthAndDistance dataC =
                    new AzimuthAndDistance(transformedPillarBasePoints.get(9), transformedPillarBasePoints.get(11));
            PolarPoint posC = new PolarPoint(transformedPillarBasePoints.get(9),
                    1500 * MILLIMETER / SCALE, dataC.calcAzimuth(), "C");
            setText("C",
                    posC.calcPolarPoint(), Color.RED, size);
            AzimuthAndDistance dataD =
                    new AzimuthAndDistance(transformedPillarBasePoints.get(23), transformedPillarBasePoints.get(21));
            PolarPoint posD = new PolarPoint(transformedPillarBasePoints.get(23),
                    1500 * MILLIMETER / SCALE, dataD.calcAzimuth(), "D");
            setText("D",
                    posD.calcPolarPoint(), Color.RED, size);
        }
    }

    private void addHoleA(){
        Line line1 = new Line();
        line1.setStroke(Color.BLUE);
        line1.setStrokeWidth(2);
        line1.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(9).getX_coord()));
        line1.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(9).getY_coord()));
        line1.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(10).getX_coord()));
        line1.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(10).getY_coord()));
        Line line2 = new Line();
        line2.setStroke(Color.BLUE);
        line2.setStrokeWidth(2);
        line2.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(10).getX_coord()));
        line2.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(10).getY_coord()));
        line2.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(11).getX_coord()));
        line2.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(11).getY_coord()));
        Line line3 = new Line();
        line3.setStroke(Color.BLUE);
        line3.setStrokeWidth(2);
        line3.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(11).getX_coord()));
        line3.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(11).getY_coord()));
        line3.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(12).getX_coord()));
        line3.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(12).getY_coord()));
        Line line4 = new Line();
        line4.setStroke(Color.BLUE);
        line4.setStrokeWidth(2);
        line4.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(12).getX_coord()));
        line4.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(12).getY_coord()));
        line4.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(9).getX_coord()));
        line4.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(9).getY_coord()));
        pane.getChildren().addAll(line1, line2, line3, line4);
    }

    private void addHoleB(){
        Line line1 = new Line();
        line1.setStroke(Color.BLUE);
        line1.setStrokeWidth(2);
        line1.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(21).getX_coord()));
        line1.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(21).getY_coord()));
        line1.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(22).getX_coord()));
        line1.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(22).getY_coord()));
        Line line2 = new Line();
        line2.setStroke(Color.BLUE);
        line2.setStrokeWidth(2);
        line2.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(22).getX_coord()));
        line2.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(22).getY_coord()));
        line2.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(23).getX_coord()));
        line2.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(23).getY_coord()));
        Line line3 = new Line();
        line3.setStroke(Color.BLUE);
        line3.setStrokeWidth(2);
        line3.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(23).getX_coord()));
        line3.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(23).getY_coord()));
        line3.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(24).getX_coord()));
        line3.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(24).getY_coord()));
        Line line4 = new Line();
        line4.setStroke(Color.BLUE);
        line4.setStrokeWidth(2);
        line4.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(24).getX_coord()));
        line4.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(24).getY_coord()));
        line4.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(21).getX_coord()));
        line4.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(21).getY_coord()));
        pane.getChildren().addAll(line1, line2, line3, line4);
    }

    private void addHoleC(){
        Line line1 = new Line();
        line1.setStroke(Color.BLUE);
        line1.setStrokeWidth(2);
        line1.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(17).getX_coord()));
        line1.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(17).getY_coord()));
        line1.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(18).getX_coord()));
        line1.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(18).getY_coord()));
        Line line2 = new Line();
        line2.setStroke(Color.BLUE);
        line2.setStrokeWidth(2);
        line2.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(18).getX_coord()));
        line2.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(18).getY_coord()));
        line2.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(19).getX_coord()));
        line2.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(19).getY_coord()));
        Line line3 = new Line();
        line3.setStroke(Color.BLUE);
        line3.setStrokeWidth(2);
        line3.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(19).getX_coord()));
        line3.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(19).getY_coord()));
        line3.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(20).getX_coord()));
        line3.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(20).getY_coord()));
        Line line4 = new Line();
        line4.setStroke(Color.BLUE);
        line4.setStrokeWidth(2);
        line4.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(20).getX_coord()));
        line4.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(20).getY_coord()));
        line4.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(17).getX_coord()));
        line4.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(17).getY_coord()));
        pane.getChildren().addAll(line1, line2, line3, line4);
    }

    private void addHoleD(){
        Line line1 = new Line();
        line1.setStroke(Color.BLUE);
        line1.setStrokeWidth(2);
        line1.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(13).getX_coord()));
        line1.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(13).getY_coord()));
        line1.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(14).getX_coord()));
        line1.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(14).getY_coord()));
        Line line2 = new Line();
        line2.setStroke(Color.BLUE);
        line2.setStrokeWidth(2);
        line2.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(14).getX_coord()));
        line2.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(14).getY_coord()));
        line2.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(15).getX_coord()));
        line2.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(15).getY_coord()));
        Line line3 = new Line();
        line3.setStroke(Color.BLUE);
        line3.setStrokeWidth(2);
        line3.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(15).getX_coord()));
        line3.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(15).getY_coord()));
        line3.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(16).getX_coord()));
        line3.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(16).getY_coord()));
        Line line4 = new Line();
        line4.setStroke(Color.BLUE);
        line4.setStrokeWidth(2);
        line4.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(16).getX_coord()));
        line4.startYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(16).getY_coord()));
        line4.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(13).getX_coord()));
        line4.endYProperty().bind(pane.heightProperty().divide(2).subtract(transformedPillarBasePoints.get(13).getY_coord()));
        pane.getChildren().addAll(line1, line2, line3, line4);
    }

    private void addCirleForPoint(){
        circeID = 0;
        for (Point point: transformedPillarBasePoints) {
            Circle circle = new Circle();
            circle.setRadius(5);
            circle.centerXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                    .add(point.getX_coord()));
            circle.centerYProperty().bind(pane.heightProperty().divide(2)
                    .subtract(point.getY_coord()));
            circle.setStroke(Color.FIREBRICK);
            circle.setStrokeWidth(2);
            circle.setFill(Color.TRANSPARENT);
            circle.setCursor(Cursor.HAND);
            circle.setId(String.valueOf(circeID++));
            circle.setOnMouseClicked(e -> setOnMouseClickEvent(circle));
            Tooltip tooltip = new Tooltip(point.getPointID());
            Tooltip.install(circle, tooltip);
            if( point.getPointID().split("_").length == 1){
                circle.setStroke(null);
                circle.setRadius(10);
                circle.setFill(Color.MAGENTA);
            }
            pane.getChildren().add(circle);
        }

    }

    private void setOnMouseClickEvent(Circle circle){
        Point transformedPoint = transformedPillarBasePoints.get(Integer.parseInt(circle.getId()));
        circle.setStroke(null);
        circle.setRadius(10);
        circle.setFill(Color.RED);
        if( transformedPoint.getPointID().split("_").length == 2) {
            setText(transformedPoint.getPointID(), transformedPoint, Color.BLACK, 16);
        }
        Point pillarBasePoint = PILLAR_BASE_POINTS.get(Integer.parseInt(circle.getId()));
        distancePointList.add(pillarBasePoint);
        addDistanceInformation();
    }

    private void addDistanceInformation(){
        if( distancePointList.size() == 1 ){
            return;
        }
        df.applyPattern("0.00");
        double distance =
                new AzimuthAndDistance(distancePointList.get(distancePointList.size() - 2),
                        distancePointList.get(distancePointList.size() - 1)).calcDistance();
        Text title = new Text("Pontok távolsága:");
        title.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 14));
        Text distanceInfo =
                new Text(distancePointList.get(distancePointList.size() - 2).getPointID()
                        + " → " + distancePointList.get(distancePointList.size() - 1).getPointID() + ":\t"
                        + df.format(distance).replace(",", ".") + "m");
        distanceInfo.setFont(Font.font("Book-Antique", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 14));
       if( distancePointList.size() == 2 ){
           title.xProperty().bind(pane.widthProperty().divide(11).multiply(9));
           title.yProperty().bind((pane.heightProperty().divide(10).multiply(2)));
           distanceInfo.xProperty().bind(pane.widthProperty().divide(11).multiply(9));
           distanceInfo.yProperty().bind((pane.heightProperty().divide(10).multiply(2)).add(nextRowValue));
           pane.getChildren().addAll(title, distanceInfo);
        }
       else {
           nextRowValue += 5 * MILLIMETER;
           distanceInfo.xProperty().bind(pane.widthProperty().divide(11).multiply(9));
           distanceInfo.yProperty().bind((pane.heightProperty().divide(10).multiply(2)).add(nextRowValue));
           double summaDistance = 0;
           for(int i = 0; i < distancePointList.size() - 1; i++) {
               summaDistance +=
                       new AzimuthAndDistance(distancePointList.get(i),
                               distancePointList.get(i + 1) ).calcDistance();
           }
           nextRowValue += 5 * MILLIMETER;
           Text sumDistance =
                   new Text("Összesen távolság:\t"
                           + df.format(summaDistance).replace(",",".") + "m");
           sumDistance.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 14));
           sumDistance.xProperty().bind(pane.widthProperty().divide(11).multiply(9));
           sumDistance.yProperty().bind((pane.heightProperty().divide(10).multiply(2)).add(nextRowValue));
           pane.getChildren().addAll(distanceInfo, sumDistance);
       }
    }


    private void addPreviousAndNextPillarDirections(){
        if( PILLAR_BASE_POINTS.size() == 25 ){
            AzimuthAndDistance mainLineData = new AzimuthAndDistance(transformedPillarBasePoints.get(0),
                    transformedPillarBasePoints.get(1));
            PolarPoint slavePoint = new PolarPoint(transformedPillarBasePoints.get(1),
                            30 * MILLIMETER, mainLineData.calcAzimuth(),
                            "forwardDirection");
            Line forwardDirection = new Line();
            forwardDirection.setStrokeWidth(2);
            forwardDirection.setStroke(Color.MAGENTA);
            forwardDirection.startXProperty()
                    .bind(pane.widthProperty()
                            .divide(10)
                            .multiply(6)
                            .add(transformedPillarBasePoints.get(1).getX_coord()));
            forwardDirection.startYProperty()
                    .bind(pane.heightProperty()
                            .divide(2)
                            .subtract(transformedPillarBasePoints.get(1).getY_coord()));
            forwardDirection.endXProperty()
                    .bind(pane.widthProperty()
                            .divide(10)
                            .multiply(6)
                            .add(slavePoint.calcPolarPoint().getX_coord()));
            forwardDirection.endYProperty()
                    .bind(pane.heightProperty()
                            .divide(2)
                            .subtract(slavePoint.calcPolarPoint().getY_coord()));
                    addArrow(slavePoint.calcPolarPoint(), transformedPillarBasePoints.get(0));
            pane.getChildren().add(forwardDirection);
            return;
        }
        Line previousPillarDirection = new Line();
        previousPillarDirection.setStroke(Color.MAGENTA);
        previousPillarDirection.setStrokeWidth(2);
        previousPillarDirection.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(0).getX_coord()));
        previousPillarDirection.startYProperty().bind(pane.heightProperty().divide(2)
                .subtract(transformedPillarBasePoints.get(0).getY_coord()));
        previousPillarDirection.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(26).getX_coord()));
        previousPillarDirection.endYProperty().bind(pane.heightProperty().divide(2)
                .subtract(transformedPillarBasePoints.get(26).getY_coord()));
        addArrow(transformedPillarBasePoints.get(26), transformedPillarBasePoints.get(0));
        Line nextPillarDirection = new Line();
        nextPillarDirection.setStroke(Color.MAGENTA);
        nextPillarDirection.setStrokeWidth(2);
        nextPillarDirection.startXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(0).getX_coord()));
        nextPillarDirection.startYProperty().bind(pane.heightProperty().divide(2)
                .subtract(transformedPillarBasePoints.get(0).getY_coord()));
        nextPillarDirection.endXProperty().bind(pane.widthProperty().divide(10).multiply(6)
                .add(transformedPillarBasePoints.get(25).getX_coord()));
        nextPillarDirection.endYProperty().bind(pane.heightProperty().divide(2)
                .subtract(transformedPillarBasePoints.get(25).getY_coord()));
        addArrow(transformedPillarBasePoints.get(25), transformedPillarBasePoints.get(0));
        pane.getChildren().addAll(previousPillarDirection, nextPillarDirection);
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

    private void addComboBoxForScaleValue(){
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "50",
                        "100",
                        "150",
                        "200",
                        "250",
                        "400",
                        "500",
                        "750",
                        "1000"
                );
        HBox hbox = new HBox();
        scaleComboBox = new ComboBox<>(options);
        scaleComboBox.setValue("200");
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

    private void addInformation(){
        AzimuthAndDistance baseLineData =
                new AzimuthAndDistance(PILLAR_BASE_POINTS.get(0), DIRECTION_POINT);
        df.applyPattern("0.000");
        Text distanceInfo =
                new Text(PILLAR_BASE_POINTS.get(0).getPointID() + ". és "
                        + DIRECTION_POINT.getPointID() + ". oszlopok távolsága: " +
                        df.format(baseLineData.calcDistance()).replace(",", ".") + "m");
        distanceInfo.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 16));
        distanceInfo.xProperty().bind(pane.widthProperty().divide(10).multiply(3));
        distanceInfo.yProperty().bind(pane.heightProperty().divide(10).multiply(9));
        Text unit = new Text("1m");
        unit.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 16));
        unit.xProperty().bind(pane.widthProperty().divide(10).multiply(3).subtract(100 * MILLIMETER / SCALE));
        unit.yProperty().bind(pane.heightProperty()
                .divide(10).multiply(9).subtract(10 * MILLIMETER ));
        Line distanceUnit = new Line();
        distanceUnit.setStrokeWidth(2);
        distanceUnit
                .startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(3));
        distanceUnit
                .startYProperty()
                .bind(pane.heightProperty()
                .divide(10)
                        .multiply(9)
                        .subtract(8 * MILLIMETER ));
        distanceUnit
                .endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(3)
                        .add(1000 * MILLIMETER / SCALE));
        distanceUnit
                .endYProperty()
                .bind(pane.heightProperty()
                .divide(10)
                        .multiply(9)
                        .subtract( 8 * MILLIMETER ));
        Line leftEndLine = new Line();
        leftEndLine.setStrokeWidth(2);
        leftEndLine
                .startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(3));
        leftEndLine
                .startYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract(8 * MILLIMETER ).add(0.5 * MILLIMETER));
        leftEndLine
                .endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(3));
        leftEndLine
                .endYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract( 8 * MILLIMETER ).subtract(0.5 * MILLIMETER));
        Line rightEndLine = new Line();
        rightEndLine.setStrokeWidth(2);
        rightEndLine
                .startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(3).add(1000 * MILLIMETER / SCALE));
        rightEndLine
                .startYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract(8 * MILLIMETER ).add(0.5 * MILLIMETER));
        rightEndLine
                .endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(3).add(1000 * MILLIMETER / SCALE));
        rightEndLine
                .endYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract( 8 * MILLIMETER ).subtract(0.5 * MILLIMETER));
        pane.getChildren().addAll(distanceInfo, unit, distanceUnit, leftEndLine, rightEndLine);
    }

    private void setText(String textData, Point startPoint, Color color, int size){
        Text text = new Text(textData);
        text.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, size));
        text.setFill(color);
        text.xProperty()
                .bind(pane.widthProperty()
                        .divide(10).multiply(6)
                        .add(startPoint.getX_coord())
                        .add(2 * MILLIMETER));
        text.yProperty()
                .bind(pane.heightProperty()
                        .divide(2)
                        .subtract(startPoint.getY_coord())
                        .subtract(2 * MILLIMETER));
        pane.getChildren().add(text);
    }

    private void setOnActionEvent(){
        String selectedScale = scaleComboBox.getSelectionModel().getSelectedItem();
        SCALE = Integer.parseInt(selectedScale);
        pane.getChildren().clear();
        getContent();
        scaleComboBox.setValue(selectedScale);
    }
    private ScrollPane getScrollPane(AnchorPane content){
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scroller;
    }

    private void getTransformPillarCoordsForDisplayer() {
        transformedPillarBasePoints = new ArrayList<>();
        double X = PILLAR_BASE_POINTS.get(0).getX_coord();
        double Y = PILLAR_BASE_POINTS.get(0).getY_coord();
        for (Point pillarBasePoint : PILLAR_BASE_POINTS) {
            Point point = new Point(pillarBasePoint.getPointID(),
                    Math.round((pillarBasePoint.getX_coord() - X) * 1000.0) * MILLIMETER / SCALE,
                    Math.round((pillarBasePoint.getY_coord() - Y) * 1000.0) * MILLIMETER / SCALE);
            transformedPillarBasePoints.add(point);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

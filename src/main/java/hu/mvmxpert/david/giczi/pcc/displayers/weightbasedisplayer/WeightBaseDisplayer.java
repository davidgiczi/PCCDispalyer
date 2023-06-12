package hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer;

import hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer.model.Point;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class WeightBaseDisplayer extends Application {

    private static String TITLE;

    private static List<Point> PILLAR_BASE_POINTS;
    private static Point DIRECTION_POINT;
    private static final double SCALE = 1 * 22.5;
    public static final double MILLIMETER = 1000 / 224.0;
    private AnchorPane pane;
    private double displayerCenterX;
    private double displayerCenterY;
    private List<Point> transformedPillarBasePoints;


    public static void setTitle(String TITLE) {
        WeightBaseDisplayer.TITLE = TITLE;
    }

    public static void setPillarBasePoints(List<Point> pillarBasePoints) {
        PILLAR_BASE_POINTS = pillarBasePoints;
    }

    public static void setDirectionPoint(Point directionPoint) {
        DIRECTION_POINT = directionPoint;
    }

    private void getContent(){
        pane = new AnchorPane();
        pane.setStyle("-fx-background-color: white");
        addNorthSign();
        addPointCoordsData();
        addPillarMainAxes();
        addHoleA();
        addHoleB();
        addHoleC();
        addHoleD();
        addCirleForPoint();
    }

    private void addNorthSign(){
        ImageView northSign = new ImageView(new Image("file:images/north.jpg"));
        northSign.setFitWidth(40 * MILLIMETER);
        northSign.setFitHeight(40 * MILLIMETER);
        northSign.setX(100 * MILLIMETER);
        northSign.setY(10 * MILLIMETER);
        pane.getChildren().add(northSign);
    }

    private void addPointCoordsData(){
        double row = 10 * MILLIMETER;
        for (Point point : PILLAR_BASE_POINTS){
            Text pointID = new Text(point.getPointID());
            if( point.getPointID().split("_").length == 1)
                pointID.setFill(Color.MAGENTA);
            pointID.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 16));
            pointID.setCursor(Cursor.HAND);
            pointID.setX(10 * MILLIMETER);
            pointID.setY(row);
            Text coords = new Text(point.toString());
            coords.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 16));
            coords.setFill(Color.RED);
            coords.setCursor(Cursor.HAND);
            coords.setX(30 * MILLIMETER);
            coords.setY(row);
            row += 6 * MILLIMETER;
            pane.getChildren().addAll(pointID, coords);
        }
        addDirectionPointCoords(row);
    }
    private void addDirectionPointCoords(double row){
        Text pointID = new Text(DIRECTION_POINT.getPointID());
        pointID.setFill(Color.MAGENTA);
        pointID.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 16));
        pointID.setX(10 * MILLIMETER);
        pointID.setY(row);
        Text coords = new Text(DIRECTION_POINT.toString());
        coords.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 16));
        coords.setFill(Color.RED);
        coords.setX(30 * MILLIMETER);
        coords.setY(row);
        pane.getChildren().addAll(pointID, coords);
    }
    private void addPillarMainAxes(){
        getDisplayerCenterCoords();
        getTransformPillarCoordsForDisplayer();
        Line mainAxis = new Line();
        mainAxis.setStroke(Color.RED);
        mainAxis.setStrokeWidth(2);
        mainAxis.getStrokeDashArray().addAll(10d);
        mainAxis.setStartX(transformedPillarBasePoints.get(2).getX_coord());
        mainAxis.setStartY(transformedPillarBasePoints.get(2).getY_coord());
        mainAxis.setEndX(transformedPillarBasePoints.get(4).getX_coord());
        mainAxis.setEndY(transformedPillarBasePoints.get(4).getY_coord());
        Line perpendicularAxis = new Line();
        perpendicularAxis.setStroke(Color.RED);
        perpendicularAxis.setStrokeWidth(2);
        perpendicularAxis.getStrokeDashArray().addAll(10d);
        perpendicularAxis.setStartX(transformedPillarBasePoints.get(1).getX_coord());
        perpendicularAxis.setStartY(transformedPillarBasePoints.get(1).getY_coord());
        perpendicularAxis.setEndX(transformedPillarBasePoints.get(3).getX_coord());
        perpendicularAxis.setEndY(transformedPillarBasePoints.get(3).getY_coord());
        pane.getChildren().addAll(mainAxis, perpendicularAxis);
    }

    private void addHoleA(){
        Line line1 = new Line();
        line1.setStroke(Color.BLUE);
        line1.setStrokeWidth(2);
        line1.setStartX(transformedPillarBasePoints.get(9).getX_coord());
        line1.setStartY(transformedPillarBasePoints.get(9).getY_coord());
        line1.setEndX(transformedPillarBasePoints.get(10).getX_coord());
        line1.setEndY(transformedPillarBasePoints.get(10).getY_coord());
        Line line2 = new Line();
        line2.setStroke(Color.BLUE);
        line2.setStrokeWidth(2);
        line2.setStartX(transformedPillarBasePoints.get(10).getX_coord());
        line2.setStartY(transformedPillarBasePoints.get(10).getY_coord());
        line2.setEndX(transformedPillarBasePoints.get(11).getX_coord());
        line2.setEndY(transformedPillarBasePoints.get(11).getY_coord());
        Line line3 = new Line();
        line3.setStroke(Color.BLUE);
        line3.setStrokeWidth(2);
        line3.setStartX(transformedPillarBasePoints.get(11).getX_coord());
        line3.setStartY(transformedPillarBasePoints.get(11).getY_coord());
        line3.setEndX(transformedPillarBasePoints.get(12).getX_coord());
        line3.setEndY(transformedPillarBasePoints.get(12).getY_coord());
        Line line4 = new Line();
        line4.setStroke(Color.BLUE);
        line4.setStrokeWidth(2);
        line4.setStartX(transformedPillarBasePoints.get(12).getX_coord());
        line4.setStartY(transformedPillarBasePoints.get(12).getY_coord());
        line4.setEndX(transformedPillarBasePoints.get(9).getX_coord());
        line4.setEndY(transformedPillarBasePoints.get(9).getY_coord());
        pane.getChildren().addAll(line1, line2, line3, line4);
    }

    private void addHoleB(){
        Line line1 = new Line();
        line1.setStroke(Color.BLUE);
        line1.setStrokeWidth(2);
        line1.setStartX(transformedPillarBasePoints.get(21).getX_coord());
        line1.setStartY(transformedPillarBasePoints.get(21).getY_coord());
        line1.setEndX(transformedPillarBasePoints.get(22).getX_coord());
        line1.setEndY(transformedPillarBasePoints.get(22).getY_coord());
        Line line2 = new Line();
        line2.setStroke(Color.BLUE);
        line2.setStrokeWidth(2);
        line2.setStartX(transformedPillarBasePoints.get(22).getX_coord());
        line2.setStartY(transformedPillarBasePoints.get(22).getY_coord());
        line2.setEndX(transformedPillarBasePoints.get(23).getX_coord());
        line2.setEndY(transformedPillarBasePoints.get(23).getY_coord());
        Line line3 = new Line();
        line3.setStroke(Color.BLUE);
        line3.setStrokeWidth(2);
        line3.setStartX(transformedPillarBasePoints.get(23).getX_coord());
        line3.setStartY(transformedPillarBasePoints.get(23).getY_coord());
        line3.setEndX(transformedPillarBasePoints.get(24).getX_coord());
        line3.setEndY(transformedPillarBasePoints.get(24).getY_coord());
        Line line4 = new Line();
        line4.setStroke(Color.BLUE);
        line4.setStrokeWidth(2);
        line4.setStartX(transformedPillarBasePoints.get(24).getX_coord());
        line4.setStartY(transformedPillarBasePoints.get(24).getY_coord());
        line4.setEndX(transformedPillarBasePoints.get(21).getX_coord());
        line4.setEndY(transformedPillarBasePoints.get(21).getY_coord());
        pane.getChildren().addAll(line1, line2, line3, line4);
    }

    private void addHoleC(){
        Line line1 = new Line();
        line1.setStroke(Color.BLUE);
        line1.setStrokeWidth(2);
        line1.setStartX(transformedPillarBasePoints.get(17).getX_coord());
        line1.setStartY(transformedPillarBasePoints.get(17).getY_coord());
        line1.setEndX(transformedPillarBasePoints.get(18).getX_coord());
        line1.setEndY(transformedPillarBasePoints.get(18).getY_coord());
        Line line2 = new Line();
        line2.setStroke(Color.BLUE);
        line2.setStrokeWidth(2);
        line2.setStartX(transformedPillarBasePoints.get(18).getX_coord());
        line2.setStartY(transformedPillarBasePoints.get(18).getY_coord());
        line2.setEndX(transformedPillarBasePoints.get(19).getX_coord());
        line2.setEndY(transformedPillarBasePoints.get(19).getY_coord());
        Line line3 = new Line();
        line3.setStroke(Color.BLUE);
        line3.setStrokeWidth(2);
        line3.setStartX(transformedPillarBasePoints.get(19).getX_coord());
        line3.setStartY(transformedPillarBasePoints.get(19).getY_coord());
        line3.setEndX(transformedPillarBasePoints.get(20).getX_coord());
        line3.setEndY(transformedPillarBasePoints.get(20).getY_coord());
        Line line4 = new Line();
        line4.setStroke(Color.BLUE);
        line4.setStrokeWidth(2);
        line4.setStartX(transformedPillarBasePoints.get(20).getX_coord());
        line4.setStartY(transformedPillarBasePoints.get(20).getY_coord());
        line4.setEndX(transformedPillarBasePoints.get(17).getX_coord());
        line4.setEndY(transformedPillarBasePoints.get(17).getY_coord());
        pane.getChildren().addAll(line1, line2, line3, line4);
    }

    private void addHoleD(){
        Line line1 = new Line();
        line1.setStroke(Color.BLUE);
        line1.setStrokeWidth(2);
        line1.setStartX(transformedPillarBasePoints.get(13).getX_coord());
        line1.setStartY(transformedPillarBasePoints.get(13).getY_coord());
        line1.setEndX(transformedPillarBasePoints.get(14).getX_coord());
        line1.setEndY(transformedPillarBasePoints.get(14).getY_coord());
        Line line2 = new Line();
        line2.setStroke(Color.BLUE);
        line2.setStrokeWidth(2);
        line2.setStartX(transformedPillarBasePoints.get(14).getX_coord());
        line2.setStartY(transformedPillarBasePoints.get(14).getY_coord());
        line2.setEndX(transformedPillarBasePoints.get(15).getX_coord());
        line2.setEndY(transformedPillarBasePoints.get(15).getY_coord());
        Line line3 = new Line();
        line3.setStroke(Color.BLUE);
        line3.setStrokeWidth(2);
        line3.setStartX(transformedPillarBasePoints.get(15).getX_coord());
        line3.setStartY(transformedPillarBasePoints.get(15).getY_coord());
        line3.setEndX(transformedPillarBasePoints.get(16).getX_coord());
        line3.setEndY(transformedPillarBasePoints.get(16).getY_coord());
        Line line4 = new Line();
        line4.setStroke(Color.BLUE);
        line4.setStrokeWidth(2);
        line4.setStartX(transformedPillarBasePoints.get(16).getX_coord());
        line4.setStartY(transformedPillarBasePoints.get(16).getY_coord());
        line4.setEndX(transformedPillarBasePoints.get(13).getX_coord());
        line4.setEndY(transformedPillarBasePoints.get(13).getY_coord());
        pane.getChildren().addAll(line1, line2, line3, line4);
    }

    private void addCirleForPoint(){

        for (Point point: transformedPillarBasePoints) {
            Circle circle = new Circle();
            circle.setRadius(5);
            circle.setCenterX(point.getX_coord());
            circle.setCenterY(point.getY_coord());
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);
            circle.setFill(Color.TRANSPARENT);
            circle.setCursor(Cursor.HAND);
            Tooltip tooltip = new Tooltip(point.getPointID());
            Tooltip.install(circle, tooltip);
            pane.getChildren().add(circle);
        }

    }

    private ScrollPane getScrollPane(AnchorPane content){
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scroller;
    }

    private void getDisplayerCenterCoords() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getBounds();
        displayerCenterX = 3 * bounds.getWidth() / 4;
        displayerCenterY = 3 * bounds.getHeight() / 5;
    }

    private void getTransformPillarCoordsForDisplayer() {
        transformedPillarBasePoints = new ArrayList<>();
        double X = PILLAR_BASE_POINTS.get(0).getX_coord();
        double Y = PILLAR_BASE_POINTS.get(0).getY_coord();
        for (Point pillarBasePoint : PILLAR_BASE_POINTS) {
            Point point = new Point(pillarBasePoint.getPointID(),
                    displayerCenterX + Math.round((pillarBasePoint.getX_coord() - X) * 1000.0) / SCALE,
                    displayerCenterY - Math.round((pillarBasePoint.getY_coord() - Y) * 1000.0) / SCALE);
            transformedPillarBasePoints.add(point);
        }
    }

    @Override
    public void start(Stage stage)  {
        getContent();
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

    public static void main(String[] args) {
        launch(args);
    }
}

package hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer;

import hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer.model.Point;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.List;

public class WeightBaseDisplayer extends Application {

    private static String TITLE;

    private static List<Point> PILLAR_POINTS;
    private static final double SCALE = 2 * 22.5;
    public static final double MILLIMETER = 1000 / 224.0;
    private AnchorPane pane;
    private static double displayerCenterX;
    private static double displayerCenterY;

    public static void setTITLE(String TITLE) {
        WeightBaseDisplayer.TITLE = TITLE;
    }

    public static void setPillarPoints(List<Point> pillarPoints) {
        PILLAR_POINTS = pillarPoints;
    }

    private void getContent(){
        pane = new AnchorPane();
        pane.setStyle("-fx-background-color: white");
        addPointCoordsData();
    }
    private void addPointCoordsData(){
        double row = 10 * MILLIMETER;
        for (Point point : PILLAR_POINTS){
            Text pointID = new Text(point.getPointID());
            pointID.setFont(Font.font("Book-Antiqua", FontWeight.BOLD, FontPosture.REGULAR, 16));
            pointID.setX(10 * MILLIMETER);
            pointID.setY(row);
            Text coords = new Text(point.toString());
            coords.setFont(Font.font("Book-Antiqua", FontWeight.BOLD, FontPosture.REGULAR, 16));
            coords.setFill(Color.RED);
            coords.setX(30 * MILLIMETER);
            coords.setY(row);
            row += 6 * MILLIMETER;
            pane.getChildren().addAll(pointID, coords);
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
        displayerCenterX = bounds.getWidth() / 2;
        displayerCenterY = bounds.getHeight() / 2;
    }

    @Override
    public void start(Stage stage)  {
        getContent();
        ScrollPane scrollPane = getScrollPane(pane);
        Scene scene = new Scene(scrollPane);
        stage.setTitle(TITLE);
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

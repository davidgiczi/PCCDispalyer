package hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer;

import hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer.model.Point;
import hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer.service.PillarCoordsForWeightBase;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
   private final PillarCoordsForWeightBase weightBaseCalculator;
    public Main() {
        Point center = new Point("7", 639453.94, 218115.40);
        Point direction = new Point("8", 639369.24,217820.07);
        weightBaseCalculator =
                new PillarCoordsForWeightBase(center, direction);
    }

    @Override
    public void start(Stage stage) throws Exception {
        WeightBaseFXDisplayer.setTitle(System.getProperty("user.dir"));
        WeightBaseFXDisplayer.setPillarBasePoints(getData());
        WeightBaseFXDisplayer.setDirectionPoint(weightBaseCalculator.getAxisDirectionPoint());
        new WeightBaseFXDisplayer();
    }

    public List<Point> getData(){
        weightBaseCalculator.setDistanceOnTheAxis(10);
        weightBaseCalculator.setHorizontalDistanceBetweenPillarLegs(5.7);
        weightBaseCalculator.setVerticalDistanceBetweenPillarLegs(5.7);
        weightBaseCalculator.setHorizontalSizeOfHoleOfPillarLeg(3.7);
        weightBaseCalculator.setVerticalSizeOfHoleOfPillarLeg(3.7);
        weightBaseCalculator.setAngleValueBetweenMainPath(180);
        /*weightBaseCalculator.setAngleValueBetweenMainPath(110);
        weightBaseCalculator.setAngularMinuteValueBetweenMainPath(19);
        weightBaseCalculator.setAngularSecondValueBetweenMainPath(0);*/
        weightBaseCalculator.calculatePillarPoints();
        return  weightBaseCalculator.getPillarPoints();
    }

    public static void main(String[] args) {
        launch();
    }
}

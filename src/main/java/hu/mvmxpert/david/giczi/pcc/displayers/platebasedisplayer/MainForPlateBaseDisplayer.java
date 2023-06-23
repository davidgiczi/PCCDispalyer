package hu.mvmxpert.david.giczi.pcc.displayers.platebasedisplayer;

import hu.mvmxpert.david.giczi.pcc.displayers.model.Point;
import hu.mvmxpert.david.giczi.pcc.displayers.service.PillarCoordsForPlateBase;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class MainForPlateBaseDisplayer extends Application {
   private final PillarCoordsForPlateBase plateBaseCalculator;
    public MainForPlateBaseDisplayer() {
        Point center = new Point("208", 661408.3, 245741.57);
        Point direction = new Point("209", 660997.32,245494.16);
        plateBaseCalculator =
                new PillarCoordsForPlateBase(center, direction);
    }

    @Override
    public void start(Stage stage) throws Exception {
        PlateBaseFXDisplayer.setTitle(System.getProperty("user.dir"));
        PlateBaseFXDisplayer.setPillarBasePoints(getData());
        PlateBaseFXDisplayer.setDirectionPoint(plateBaseCalculator.getAxisDirectionPoint());
        new PlateBaseFXDisplayer();
    }

    public List<Point> getData(){
        plateBaseCalculator.setHorizontalSizeOfHole(11.4);
        plateBaseCalculator.setVerticalSizeOfHole(12.9);
        plateBaseCalculator.setHorizontalDistanceFromTheSideOfHole(7);
        plateBaseCalculator.setVerticalDistanceFromTheSideOfHole(7);
        plateBaseCalculator.setAngleValueBetweenMainPath(180);
        plateBaseCalculator.setAngularMinuteValueBetweenMainPath(0);
        plateBaseCalculator.setAngularSecondValueBetweenMainPath(0);
        plateBaseCalculator.calculatePillarPoints();
        return  plateBaseCalculator.getPillarPoints();
    }

    public static void main(String[] args) {
        launch();
    }
}

package hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer;

import hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer.model.Point;
import hu.mvmxpert.david.giczi.pcc.displayers.weightbasedisplayer.service.PillarCoordsForWeightBase;

import java.util.List;

public class Main {
   public PillarCoordsForWeightBase weightBaseCalculator;
    public Main() {
        Point center = new Point("174", 670688.81, 243584.65);
        Point direction = new Point("173", 670496.91,243693.95);
        weightBaseCalculator =
                new PillarCoordsForWeightBase(center, direction);
    }

    public List<Point> getData(){
        weightBaseCalculator.setDistanceOnTheAxis(10);
        weightBaseCalculator.setHorizontalDistanceBetweenPillarLegs(8.1);
        weightBaseCalculator.setVerticalDistanceBetweenPillarLegs(8.1);
        weightBaseCalculator.setHorizontalSizeOfHoleOfPillarLeg(4.8);
        weightBaseCalculator.setVerticalSizeOfHoleOfPillarLeg(4.8);
        //weightBaseCalculator.setAngleValueBetweenMainPath(180);
        weightBaseCalculator.setAngleValueBetweenMainPath(110);
        weightBaseCalculator.setAngularMinuteValueBetweenMainPath(19);
        weightBaseCalculator.setAngularSecondValueBetweenMainPath(0);
        weightBaseCalculator.calculatePillarPoints();
        return  weightBaseCalculator.getPillarPoints();
    }

    public static void main(String[] args) {
        Main main = new Main();
        WeightBaseDisplayer.setTitle(System.getProperty("user.dir"));
        WeightBaseDisplayer.setPillarBasePoints(main.getData());
        WeightBaseDisplayer.setDirectionPoint(main.weightBaseCalculator.getAxisDirectionPoint());
        WeightBaseDisplayer.main(args);
    }
}

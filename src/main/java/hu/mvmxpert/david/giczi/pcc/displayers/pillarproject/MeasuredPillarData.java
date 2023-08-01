package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import hu.mvmxpert.david.giczi.pcc.displayers.model.MeasPoint;
import hu.mvmxpert.david.giczi.pcc.displayers.model.Point;
import hu.mvmxpert.david.giczi.pcc.displayers.service.AzimuthAndDistance;
import java.util.*;

public class MeasuredPillarData {

    public MeasuredPillarDataController measuredPillarDataController;
    private final List<MeasPoint> measPillarPoints;
    private List<MeasPoint> pillarBasePoints;
    private List<MeasPoint> pillarTopPoints;
    private MeasPoint pillarCenterPoint;
    private MeasPoint baseLineDirectionPoint;
    private double rotation;

    public MeasuredPillarData(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        measPillarPoints = new ArrayList<>();
    }

    public List<MeasPoint> getMeasPillarPoints() {
        return measPillarPoints;
    }

    public MeasPoint getPillarCenterPoint() {
        return pillarCenterPoint;
    }
    public void setPillarCenterPoint(MeasPoint pillarCenterPoint) {
        this.pillarCenterPoint = pillarCenterPoint;
    }

    public void setBaseLineDirectionPoint(MeasPoint baseLineDirectionPoint) {
        this.baseLineDirectionPoint = baseLineDirectionPoint;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public List<MeasPoint> getPillarBasePoints() {
        return pillarBasePoints;
    }

    public List<MeasPoint> getPillarTopPoints() {
        return pillarTopPoints;
    }

    public void calcPillarLegsPoint(){
        pillarBasePoints = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            List<MeasPoint> pillarLegPoints = new ArrayList<>();
            for (MeasPoint measPillarPoint : measPillarPoints) {
                if( measPillarPoint.isUsed() &&
                        PointType.ALAP == measPillarPoint.getPointType() &&
                        i == measPillarPoint.getGroupID() ){
                    pillarLegPoints.add(measPillarPoint);
                }
            }
        if( pillarLegPoints.isEmpty() ){
            continue;
        }
            double pcs = 0;
            double x = 0.0;
            for (MeasPoint pillarLegPoint : pillarLegPoints) {
                x += pillarLegPoint.getX_coord();
                pcs++;
            }
            x = x / pcs;
            pcs = 0;
            double y = 0.0;
            for (MeasPoint pillarLegPoint : pillarLegPoints) {
                y += pillarLegPoint.getY_coord();
                pcs++;
            }
            y = y / pcs;
            pcs = 0;
            double z = 0.0;
            for (MeasPoint pillarLegPoint : pillarLegPoints) {
                z += pillarLegPoint.getZ_coord();
                pcs++;
            }
            z = z / pcs;
            MeasPoint pillarLegPoint = new MeasPoint(null, x, y, z, PointType.ALAP);
            pillarLegPoint.setUsed(true);
            pillarLegPoint.setGroupID(i);
            pillarBasePoints.add(pillarLegPoint);
        }

        addIDsForPillarLegs();
    }

    private void addIDsForPillarLegs(){
       if( pillarBasePoints.size() != 4 ){
           for( int i = 65; i < 65 + pillarBasePoints.size(); i++ ){
               pillarBasePoints.get(i - 65).setPointID(String.valueOf((char) i));
           }
           return;
       }
        Point center = new Point("center", pillarCenterPoint.getX_coord(),
                pillarCenterPoint.getY_coord());
        Point direction = new Point("direction", baseLineDirectionPoint.getX_coord(),
                baseLineDirectionPoint.getY_coord());
        AzimuthAndDistance baseLineDirection = new AzimuthAndDistance(center, direction);
        for (MeasPoint pillarBasePoint : pillarBasePoints) {
            pillarBasePoint.setAzimuth(center);
        }

        if( rotation == Math.PI ){

              for(int i = 0; i < pillarBasePoints.size(); i++){
                  for(int j = i + 1; j < pillarBasePoints.size(); j++) {
                      if (pillarBasePoints.get(i).getAzimuth() > pillarBasePoints.get(j).getAzimuth()) {
                          Collections.swap(pillarBasePoints, i, j);
                      }
                  }
            }

            if( pillarBasePoints.get(0).getAzimuth() < baseLineDirection.calcAzimuth() &&
                    baseLineDirection.calcAzimuth() < pillarBasePoints.get(1).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("A");
                pillarBasePoints.get(1).setPointID("B");
                pillarBasePoints.get(2).setPointID("C");
                pillarBasePoints.get(3).setPointID("D");
            }
            else if( pillarBasePoints.get(1).getAzimuth() < baseLineDirection.calcAzimuth() &&
                    baseLineDirection.calcAzimuth() < pillarBasePoints.get(2).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("D");
                pillarBasePoints.get(1).setPointID("A");
                pillarBasePoints.get(2).setPointID("B");
                pillarBasePoints.get(3).setPointID("C");
            }
            else if( pillarBasePoints.get(2).getAzimuth() < baseLineDirection.calcAzimuth() &&
                    baseLineDirection.calcAzimuth() < pillarBasePoints.get(3).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("C");
                pillarBasePoints.get(1).setPointID("D");
                pillarBasePoints.get(2).setPointID("A");
                pillarBasePoints.get(3).setPointID("B");
            }
            else if( pillarBasePoints.get(3).getAzimuth() < baseLineDirection.calcAzimuth() &&
                    baseLineDirection.calcAzimuth() < pillarBasePoints.get(0).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("B");
                pillarBasePoints.get(1).setPointID("C");
                pillarBasePoints.get(2).setPointID("D");
                pillarBasePoints.get(3).setPointID("A");
            }
            return;
        }
        int centerID = Integer.parseInt(pillarCenterPoint.getPointID());
        int directionID = Integer.parseInt(baseLineDirectionPoint.getPointID());

        if( centerID == directionID ){
            return;
        }

        if( centerID < directionID ){
            rotation += (Math.PI - rotation) / 2;
            if( pillarBasePoints.get(0).getAzimuth() < baseLineDirection.calcAzimuth() + rotation &&
                    baseLineDirection.calcAzimuth() + rotation < pillarBasePoints.get(1).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("A");
                pillarBasePoints.get(1).setPointID("B");
                pillarBasePoints.get(2).setPointID("C");
                pillarBasePoints.get(3).setPointID("D");
            }
            else if( pillarBasePoints.get(1).getAzimuth() < baseLineDirection.calcAzimuth() + rotation &&
                    baseLineDirection.calcAzimuth() + rotation < pillarBasePoints.get(2).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("D");
                pillarBasePoints.get(1).setPointID("A");
                pillarBasePoints.get(2).setPointID("B");
                pillarBasePoints.get(3).setPointID("C");
            }
            else if( pillarBasePoints.get(2).getAzimuth() < baseLineDirection.calcAzimuth() + rotation &&
                    baseLineDirection.calcAzimuth() + rotation < pillarBasePoints.get(3).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("C");
                pillarBasePoints.get(1).setPointID("D");
                pillarBasePoints.get(2).setPointID("A");
                pillarBasePoints.get(3).setPointID("B");
            }
            else if( pillarBasePoints.get(3).getAzimuth() < baseLineDirection.calcAzimuth() + rotation &&
                    baseLineDirection.calcAzimuth() + rotation < pillarBasePoints.get(0).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("B");
                pillarBasePoints.get(1).setPointID("C");
                pillarBasePoints.get(2).setPointID("D");
                pillarBasePoints.get(3).setPointID("A");
            }
        }
        else {

            rotation += (Math.PI + rotation) / 2;
            if( pillarBasePoints.get(0).getAzimuth() < baseLineDirection.calcAzimuth() + rotation &&
                    baseLineDirection.calcAzimuth() + rotation < pillarBasePoints.get(1).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("A");
                pillarBasePoints.get(1).setPointID("B");
                pillarBasePoints.get(2).setPointID("C");
                pillarBasePoints.get(3).setPointID("D");
            }
            else if( pillarBasePoints.get(1).getAzimuth() < baseLineDirection.calcAzimuth() + rotation &&
                    baseLineDirection.calcAzimuth() + rotation < pillarBasePoints.get(2).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("D");
                pillarBasePoints.get(1).setPointID("A");
                pillarBasePoints.get(2).setPointID("B");
                pillarBasePoints.get(3).setPointID("C");
            }
            else if( pillarBasePoints.get(2).getAzimuth() < baseLineDirection.calcAzimuth() + rotation &&
                    baseLineDirection.calcAzimuth() + rotation < pillarBasePoints.get(3).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("C");
                pillarBasePoints.get(1).setPointID("D");
                pillarBasePoints.get(2).setPointID("A");
                pillarBasePoints.get(3).setPointID("B");
            }
            else if( pillarBasePoints.get(3).getAzimuth() < baseLineDirection.calcAzimuth() + rotation &&
                    baseLineDirection.calcAzimuth() + rotation < pillarBasePoints.get(0).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("B");
                pillarBasePoints.get(1).setPointID("C");
                pillarBasePoints.get(2).setPointID("D");
                pillarBasePoints.get(3).setPointID("A");
            }
        }
    }

    public MeasPoint getPillarBaseCenterPoint(){
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        for (MeasPoint basePoint : pillarBasePoints) {
            x += basePoint.getX_coord();
            y += basePoint.getY_coord();
            z += basePoint.getZ_coord();
        }
        x = x / pillarBasePoints.size();
        y = y / pillarBasePoints.size();
        z = z / pillarBasePoints.size();
        return new MeasPoint("baseCenter", x , y, z, PointType.ALAP);
    }
    public void calcPillarTopPoints(){
        pillarTopPoints = new ArrayList<>();
        for (MeasPoint topPoint: measPillarPoints) {
            if( topPoint.isUsed() && topPoint.getPointType() == PointType.CSUCS ){
                pillarTopPoints.add(topPoint);
            }
        }
    }
    public MeasPoint getPillarTopCenterPoint(){
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        for (MeasPoint topPoint : pillarTopPoints) {
            x += topPoint.getX_coord();
            y += topPoint.getY_coord();
            z += topPoint.getZ_coord();
        }
        x = x / pillarTopPoints.size();
        y = y / pillarTopPoints.size();
        z = z / pillarTopPoints.size();
        return new MeasPoint("topCenter", x , y, z, PointType.CSUCS);
    }

    public void convertMeasuredDataToMeasPoints(List<String> measData){
        measData.forEach(data -> {
            String[] baseData = data.split(",");
            String[] topData = data.split(";");
            if( baseData.length == 1 && topData.length == 1){
              measuredPillarDataController. getInfoAlert("Nem megfelelő mérési fájl formátum",
                        "Az elválasztó karakter a mérési fájlban \",\" vagy \";\" lehet.");
                return;
            }
            if( baseData.length > 1) {
                MeasPoint basePoint = new MeasPoint(baseData[0],
                        Double.parseDouble(baseData[1]),
                        Double.parseDouble(baseData[2]),
                        Double.parseDouble(baseData[3]),
                        PointType.ALAP);
                if ( !measPillarPoints.contains(basePoint) ) {
                        measPillarPoints.add(basePoint);
                }
            }
            if( topData.length > 1 ) {
                MeasPoint topPoint = new MeasPoint(topData[0],
                        Double.parseDouble(topData[1]),
                        Double.parseDouble(topData[2]),
                        Double.parseDouble(topData[3]),
                        PointType.CSUCS);
                if ( !measPillarPoints.contains(topPoint) ) {
                    measPillarPoints.add(topPoint);
                }
            }
        });
    }

}

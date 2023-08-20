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
    public double radRotation;
    private int angleRotation;
    private int minRotation;
    private int secRotation;

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

    public MeasPoint getBaseLineDirectionPoint() {
        return baseLineDirectionPoint;
    }

    public void setBaseLineDirectionPoint(MeasPoint baseLineDirectionPoint) {
        this.baseLineDirectionPoint = baseLineDirectionPoint;
    }

    public List<MeasPoint> getPillarBasePoints() {
        return pillarBasePoints;
    }

    public List<MeasPoint> getPillarTopPoints() {
        return pillarTopPoints;
    }

    public int getAngleRotation() {
        return angleRotation;
    }

    public void setAngleRotation(int angleRotation) {
        this.angleRotation = angleRotation;
    }
    public int getMinRotation() {
        return minRotation;
    }

    public void setMinRotation(int minRotation) {
        this.minRotation = minRotation;
    }

    public int getSecRotation() {
        return secRotation;
    }

    public void setSecRotation(int secRotation) {
        this.secRotation = secRotation;
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
    }

    public void addIDsForPillarLegs() {
        radRotation = Math.toRadians(angleRotation + minRotation / 60.0 + secRotation / 3600.0);
        if (pillarBasePoints.size() != 4) {
            for (int i = 65; i < 65 + pillarBasePoints.size(); i++) {
                pillarBasePoints.get(i - 65).setPointID(String.valueOf((char) i));
            }
            return;
        }
        Point center = new Point("center", getPillarBaseCenterPoint().getX_coord(),
                getPillarBaseCenterPoint().getY_coord());
        Point direction = new Point("direction", baseLineDirectionPoint.getX_coord(),
                baseLineDirectionPoint.getY_coord());
        AzimuthAndDistance baseLineDirection = new AzimuthAndDistance(center, direction);
        for (MeasPoint pillarBasePoint : pillarBasePoints) {
            pillarBasePoint.setAzimuth(center);
        }

        for (int i = 0; i < pillarBasePoints.size(); i++) {
            for (int j = i + 1; j < pillarBasePoints.size(); j++) {
                if (pillarBasePoints.get(i).getAzimuth() > pillarBasePoints.get(j).getAzimuth()) {
                    Collections.swap(pillarBasePoints, i, j);
                }
            }
        }

        int centerID = Integer.parseInt(pillarCenterPoint.getPointID());
        int directionID = Integer.parseInt(baseLineDirectionPoint.getPointID());

        if (centerID < directionID) {

            if (radRotation == Math.PI) {

                if (0 < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(0).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("B");
                    pillarBasePoints.get(1).setPointID("C");
                    pillarBasePoints.get(2).setPointID("D");
                    pillarBasePoints.get(3).setPointID("A");
                } else if (pillarBasePoints.get(0).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(1).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("A");
                    pillarBasePoints.get(1).setPointID("B");
                    pillarBasePoints.get(2).setPointID("C");
                    pillarBasePoints.get(3).setPointID("D");
                } else if (pillarBasePoints.get(1).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(2).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("D");
                    pillarBasePoints.get(1).setPointID("A");
                    pillarBasePoints.get(2).setPointID("B");
                    pillarBasePoints.get(3).setPointID("C");
                } else if (pillarBasePoints.get(2).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(3).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("C");
                    pillarBasePoints.get(1).setPointID("D");
                    pillarBasePoints.get(2).setPointID("A");
                    pillarBasePoints.get(3).setPointID("B");
                } else if (pillarBasePoints.get(3).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= 2 * Math.PI) {
                    pillarBasePoints.get(0).setPointID("B");
                    pillarBasePoints.get(1).setPointID("C");
                    pillarBasePoints.get(2).setPointID("D");
                    pillarBasePoints.get(3).setPointID("A");
                }
                return;
            }
        }

        if (centerID > directionID) {

            if (radRotation == Math.PI) {

                if (0 < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(0).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("D");
                    pillarBasePoints.get(1).setPointID("A");
                    pillarBasePoints.get(2).setPointID("B");
                    pillarBasePoints.get(3).setPointID("C");
                } else if (pillarBasePoints.get(0).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(1).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("C");
                    pillarBasePoints.get(1).setPointID("D");
                    pillarBasePoints.get(2).setPointID("A");
                    pillarBasePoints.get(3).setPointID("B");
                } else if (pillarBasePoints.get(1).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(2).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("B");
                    pillarBasePoints.get(1).setPointID("C");
                    pillarBasePoints.get(2).setPointID("D");
                    pillarBasePoints.get(3).setPointID("A");
                } else if (pillarBasePoints.get(2).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(3).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("A");
                    pillarBasePoints.get(1).setPointID("B");
                    pillarBasePoints.get(2).setPointID("C");
                    pillarBasePoints.get(3).setPointID("D");
                } else if (pillarBasePoints.get(3).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= 2 * Math.PI) {
                    pillarBasePoints.get(0).setPointID("D");
                    pillarBasePoints.get(1).setPointID("A");
                    pillarBasePoints.get(2).setPointID("B");
                    pillarBasePoints.get(3).setPointID("C");
                }
                return;
            }
        }

        if( centerID < directionID ){

            double rotation = (Math.PI - radRotation) / 2;

          if( 0 < (baseLineDirection.calcAzimuth() - rotation) &&
                  (baseLineDirection.calcAzimuth() - rotation) <= pillarBasePoints.get(0).getAzimuth()){
              pillarBasePoints.get(0).setPointID("B");
              pillarBasePoints.get(1).setPointID("C");
              pillarBasePoints.get(2).setPointID("D");
              pillarBasePoints.get(3).setPointID("A");
          }
          else if( pillarBasePoints.get(0).getAzimuth() < (baseLineDirection.calcAzimuth() - rotation) &&
                    (baseLineDirection.calcAzimuth() - rotation) <= pillarBasePoints.get(1).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("A");
                pillarBasePoints.get(1).setPointID("B");
                pillarBasePoints.get(2).setPointID("C");
                pillarBasePoints.get(3).setPointID("D");
            }
            else if( pillarBasePoints.get(1).getAzimuth() < (baseLineDirection.calcAzimuth() - rotation) &&
                    (baseLineDirection.calcAzimuth() - rotation) <= pillarBasePoints.get(2).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("D");
                pillarBasePoints.get(1).setPointID("A");
                pillarBasePoints.get(2).setPointID("B");
                pillarBasePoints.get(3).setPointID("C");
            }
            else if( pillarBasePoints.get(2).getAzimuth() < (baseLineDirection.calcAzimuth() - rotation) &&
                    (baseLineDirection.calcAzimuth() - rotation) <= pillarBasePoints.get(3).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("C");
                pillarBasePoints.get(1).setPointID("D");
                pillarBasePoints.get(2).setPointID("A");
                pillarBasePoints.get(3).setPointID("B");
            }
            else if( pillarBasePoints.get(3).getAzimuth() < (baseLineDirection.calcAzimuth() - rotation) &&
                    (baseLineDirection.calcAzimuth() - rotation) <= 2 * Math.PI ){
                pillarBasePoints.get(0).setPointID("B");
                pillarBasePoints.get(1).setPointID("C");
                pillarBasePoints.get(2).setPointID("D");
                pillarBasePoints.get(3).setPointID("A");
            }
            return;
        }

        if( centerID > directionID)   {

            double rotation = (Math.PI + radRotation) / 2;

            if( 0 < (baseLineDirection.calcAzimuth() + rotation) &&
                    (baseLineDirection.calcAzimuth() + rotation) <= pillarBasePoints.get(0).getAzimuth()){
                pillarBasePoints.get(0).setPointID("B");
                pillarBasePoints.get(1).setPointID("C");
                pillarBasePoints.get(2).setPointID("D");
                pillarBasePoints.get(3).setPointID("A");
            }
           else if( pillarBasePoints.get(0).getAzimuth() < (baseLineDirection.calcAzimuth() + rotation) &&
                    (baseLineDirection.calcAzimuth() + rotation) <= pillarBasePoints.get(1).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("A");
                pillarBasePoints.get(1).setPointID("B");
                pillarBasePoints.get(2).setPointID("C");
                pillarBasePoints.get(3).setPointID("D");
            }
            else if( pillarBasePoints.get(1).getAzimuth() < (baseLineDirection.calcAzimuth() + rotation) &&
                    (baseLineDirection.calcAzimuth() + rotation) <= pillarBasePoints.get(2).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("D");
                pillarBasePoints.get(1).setPointID("A");
                pillarBasePoints.get(2).setPointID("B");
                pillarBasePoints.get(3).setPointID("C");
            }
            else if( pillarBasePoints.get(2).getAzimuth() < (baseLineDirection.calcAzimuth() + rotation) &&
                    (baseLineDirection.calcAzimuth() + rotation) <= pillarBasePoints.get(3).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("C");
                pillarBasePoints.get(1).setPointID("D");
                pillarBasePoints.get(2).setPointID("A");
                pillarBasePoints.get(3).setPointID("B");
            }
            else if( pillarBasePoints.get(3).getAzimuth() < (baseLineDirection.calcAzimuth() + rotation) &&
                    (baseLineDirection.calcAzimuth() + rotation) <= 2 * Math.PI ){
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
        return new MeasPoint(pillarCenterPoint.getPointID(), x , y, z, PointType.ALAP);
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
        return new MeasPoint("TopCenter", x , y, z, PointType.CSUCS);
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

    public double getXDifferenceOnMainLine(){
        AzimuthAndDistance mainLineData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("direction",
                                baseLineDirectionPoint.getX_coord(), baseLineDirectionPoint.getY_coord()));
        AzimuthAndDistance differenceData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("topCenter",
                                getPillarTopCenterPoint().getX_coord(), getPillarTopCenterPoint().getY_coord()));

        return differenceData.calcDistance()
                * Math.cos(mainLineData.calcAzimuth() - differenceData.calcAzimuth());
    }

    public double getYDifferenceOnMainLine(){
        AzimuthAndDistance mainLineData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("direction",
                                baseLineDirectionPoint.getX_coord(), baseLineDirectionPoint.getY_coord()));
        AzimuthAndDistance differenceData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("topCenter",
                                getPillarTopCenterPoint().getX_coord(), getPillarTopCenterPoint().getY_coord()));
        return differenceData.calcDistance()
                * Math.sin(mainLineData.calcAzimuth() - differenceData.calcAzimuth());
    }

    public double getXDifferenceOnBackwardLine(){
        AzimuthAndDistance mainLineData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("direction",
                                baseLineDirectionPoint.getX_coord(), baseLineDirectionPoint.getY_coord()));
        AzimuthAndDistance differenceData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("topCenter",
                                getPillarTopCenterPoint().getX_coord(), getPillarTopCenterPoint().getY_coord()));
        return differenceData.calcDistance()
                * Math.cos(mainLineData.calcAzimuth() + radRotation - differenceData.calcAzimuth());
    }

    public double getYDifferenceOnBackwardLine(){
        AzimuthAndDistance mainLineData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("direction",
                                baseLineDirectionPoint.getX_coord(), baseLineDirectionPoint.getY_coord()));
        AzimuthAndDistance differenceData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("topCenter",
                                getPillarTopCenterPoint().getX_coord(), getPillarTopCenterPoint().getY_coord()));
        return differenceData.calcDistance()
                * Math.sin(mainLineData.calcAzimuth() + radRotation - differenceData.calcAzimuth());
    }

}

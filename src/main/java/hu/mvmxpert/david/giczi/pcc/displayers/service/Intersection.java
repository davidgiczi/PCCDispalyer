package hu.mvmxpert.david.giczi.pcc.displayers.service;

import hu.mvmxpert.david.giczi.pcc.displayers.model.MeasPoint;
import hu.mvmxpert.david.giczi.pcc.displayers.model.Point;
import hu.mvmxpert.david.giczi.pcc.displayers.pillarproject.PointType;


public class Intersection {

    private MeasPoint intersectionPoint;

    private MeasPoint intersectionPointFromA;
    private MeasPoint intersectionPointFromB;

    private MeasPoint standingPointA;
    private MeasPoint standingPointB;
    private Point lineStartPoint;
    private Point lineEndPoint;
    private double alfa;
    private double beta;
    private int azimuthAngleA;
    private int azimuthMinuteA;
    private int azimuthSecA;

    private int elevationAngleA;
    private int elevationMinuteA;
    private int elevationSecA;

    private int azimuthAngleB;
    private int azimuthMinuteB;
    private int azimuthSecB;
    private int elevationAngleB;
    private int elevationMinuteB;
    private int elevationSecB;

    public void calcIntersectionPoint(){

        double azimuthA = Math.toRadians(azimuthAngleA + azimuthMinuteA / 60.0 + azimuthSecA / 3600.0);
        double azimuthB = Math.toRadians(azimuthAngleB + azimuthMinuteB / 60.0 + azimuthSecB / 3600.0);
        double elevationA = Math.toRadians(elevationAngleA + elevationMinuteA / 60.0 +
                elevationSecA / 3600.0);
        double elevationB = Math.toRadians(elevationAngleB + elevationMinuteB / 60.0 +
                elevationSecB / 3600.0);
        AzimuthAndDistance azimuthAB =
                new AzimuthAndDistance(
                        new Point("A",
                                standingPointA.getX_coord(),
                                standingPointA.getY_coord()),
                        new Point("B",
                                standingPointB.getX_coord(),
                                standingPointB.getY_coord()));
        AzimuthAndDistance azimuthBA =
                new AzimuthAndDistance(
                        new Point("B",
                                standingPointB.getX_coord(),
                                standingPointB.getY_coord()),
                        new Point("A",
                                standingPointA.getX_coord(),
                                standingPointA.getY_coord()));
    alfa = Math.abs(azimuthA - azimuthAB.calcAzimuth());
    beta = Math.abs(azimuthB - azimuthBA.calcAzimuth());

    double distanceA = azimuthAB.calcDistance() * Math.sin( beta ) /  Math.sin( alfa + beta );
    double distanceB = azimuthAB.calcDistance() * Math.sin( alfa ) /  Math.sin( alfa + beta );

    PolarPoint polarPointA =
            new PolarPoint(
                    new Point("A", standingPointA.getX_coord(),
                            standingPointA.getY_coord()),
                    distanceA, azimuthA, "IntersectionA");
        PolarPoint polarPointB =
                new PolarPoint(
                        new Point("B", standingPointB.getX_coord(),
                                standingPointB.getY_coord()),

                        distanceB, azimuthB, "IntersectionB");

    double intersectionElevationA =
            standingPointA.getZ_coord() + distanceA * Math.tan(elevationA);
    double intersectionElevationB =
                standingPointB.getZ_coord() + distanceB * Math.tan(elevationB);

        intersectionPointFromA = new MeasPoint("IntersectionA",
                polarPointA.calcPolarPoint().getX_coord(),
                polarPointA.calcPolarPoint().getY_coord(),
                intersectionElevationA,
                PointType.INTERSECTION);

        intersectionPointFromB = new MeasPoint("IntersectionB",
                polarPointB.calcPolarPoint().getX_coord(),
                polarPointB.calcPolarPoint().getY_coord(),
                intersectionElevationB,
                PointType.INTERSECTION);

        intersectionPoint = new MeasPoint("Intersection",
                ( polarPointA.calcPolarPoint().getX_coord() +
                        polarPointB.calcPolarPoint(). getX_coord() ) / 2.0,
                ( polarPointA.calcPolarPoint().getY_coord() +
                        polarPointB.calcPolarPoint(). getY_coord() ) / 2.0,
                (intersectionElevationA + intersectionElevationB) / 2.0,
                PointType.INTERSECTION);
    }

    public String getIntersectionAngleValueAtNewPoint(){

      double gamma = 180 - Math.toDegrees(alfa) - Math.toDegrees(beta);
      int gammaAngleValue = (int) gamma;
      int gammaMinValue = (int) ((gamma - (int) gamma) * 60);
      int gammaSecValue = (int) (gamma * 3600 - gammaAngleValue * 3600 - gammaMinValue * 60);

      return gammaAngleValue  + "°" + gammaMinValue + "'" +
             gammaSecValue + "\"";
    }

    public Point getHalfLinePointData(){
        AzimuthAndDistance lineData =
                new AzimuthAndDistance(lineStartPoint, lineEndPoint);
        PolarPoint halfLinePoint = new PolarPoint(lineStartPoint,
                lineData.calcDistance() / 2.0,
                lineData.calcAzimuth(), "halfLinePoint");
        return halfLinePoint.calcPolarPoint();
    }

    public MeasPoint getIntersectionPoint() {
        return intersectionPoint;
    }

    public void setIntersectionPoint(MeasPoint intersectionPoint) {
        this.intersectionPoint = intersectionPoint;
    }

    public MeasPoint getIntersectionPointFromA() {
        return intersectionPointFromA;
    }

    public void setIntersectionPointFromA(MeasPoint intersectionPointFromA) {
        this.intersectionPointFromA = intersectionPointFromA;
    }

    public MeasPoint getIntersectionPointFromB() {
        return intersectionPointFromB;
    }

    public void setIntersectionPointFromB(MeasPoint intersectionPointFromB) {
        this.intersectionPointFromB = intersectionPointFromB;
    }

    public Point getLineStartPoint() {
        return lineStartPoint;
    }

    public void setLineStartPoint(Point lineStartPoint) {
        this.lineStartPoint = lineStartPoint;
    }

    public Point getLineEndPoint() {
        return lineEndPoint;
    }

    public void setLineEndPoint(Point lineEndPoint) {
        this.lineEndPoint = lineEndPoint;
    }

    public MeasPoint getStandingPointA() {
        return standingPointA;
    }

    public void setStandingPointA(MeasPoint standingPointA) {
        this.standingPointA = standingPointA;
    }

    public MeasPoint getStandingPointB() {
        return standingPointB;
    }

    public void setStandingPointB(MeasPoint standingPointB) {
        this.standingPointB = standingPointB;
    }

    public int getAzimuthAngleA() {
        return azimuthAngleA;
    }

    public void setAzimuthAngleA(int azimuthAngleA) {
        this.azimuthAngleA = azimuthAngleA;
    }

    public int getAzimuthMinuteA() {
        return azimuthMinuteA;
    }

    public void setAzimuthMinuteA(int azimuthMinuteA) {
        this.azimuthMinuteA = azimuthMinuteA;
    }

    public int getAzimuthSecA() {
        return azimuthSecA;
    }

    public void setAzimuthSecA(int azimuthSecA) {
        this.azimuthSecA = azimuthSecA;
    }

    public int getAzimuthAngleB() {
        return azimuthAngleB;
    }

    public void setAzimuthAngleB(int azimuthAngleB) {
        this.azimuthAngleB = azimuthAngleB;
    }

    public int getAzimuthMinuteB() {
        return azimuthMinuteB;
    }

    public void setAzimuthMinuteB(int azimuthMinuteB) {
        this.azimuthMinuteB = azimuthMinuteB;
    }

    public int getAzimuthSecB() {
        return azimuthSecB;
    }

    public void setAzimuthSecB(int azimuthSecB) {
        this.azimuthSecB = azimuthSecB;
    }

    public int getElevationAngleA() {
        return elevationAngleA;
    }

    public void setElevationAngleA(int elevationAngleA) {
        this.elevationAngleA = elevationAngleA;
    }

    public int getElevationMinuteA() {
        return elevationMinuteA;
    }

    public void setElevationMinuteA(int elevationMinuteA) {
        this.elevationMinuteA = elevationMinuteA;
    }

    public int getElevationSecA() {
        return elevationSecA;
    }

    public void setElevationSecA(int elevationSecA) {
        this.elevationSecA = elevationSecA;
    }

    public int getElevationAngleB() {
        return elevationAngleB;
    }

    public void setElevationAngleB(int elevationAngleB) {
        this.elevationAngleB = elevationAngleB;
    }

    public int getElevationMinuteB() {
        return elevationMinuteB;
    }

    public void setElevationMinuteB(int elevationMinuteB) {
        this.elevationMinuteB = elevationMinuteB;
    }

    public int getElevationSecB() {
        return elevationSecB;
    }

    public void setElevationSecB(int elevationSecB) {
        this.elevationSecB = elevationSecB;
    }


}

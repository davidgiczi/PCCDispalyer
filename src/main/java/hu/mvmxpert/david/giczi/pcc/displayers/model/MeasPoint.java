package hu.mvmxpert.david.giczi.pcc.displayers.model;

import hu.mvmxpert.david.giczi.pcc.displayers.service.AzimuthAndDistance;

import java.text.DecimalFormat;
import java.util.Objects;


public class MeasPoint  {

    private String pointID;
    private final double x_coord;
    private final double y_coord;
    private final double z_coord;
    private double azimuth;
    private int groupID;
    private final Enum pointType;
    private boolean isUsed;
    private final DecimalFormat df = new DecimalFormat("0.000");

    public MeasPoint(String pointID, double x_coord, double y_coord, double z_coord, Enum pointType) {

        this.pointID = pointID;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.z_coord = z_coord;
        this.pointType = pointType;
    }

    public String getPointID() {
        return pointID;
    }

    public void setPointID(String pointID) {
        this.pointID = pointID;
    }

    public double getX_coord() {
        return x_coord;
    }

    public double getY_coord() {
        return y_coord;
    }

    public double getZ_coord() {
        return z_coord;
    }

    public double getAzimuth() {
        return azimuth;
    }
    public void setAzimuth(Point azimuthFrom) {
        this.azimuth =
                new AzimuthAndDistance(azimuthFrom, new Point(null, x_coord, y_coord)).calcAzimuth();
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public Enum getPointType() {
        return pointType;
    }

    public boolean isUsed() {
        return isUsed;
    }
    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasPoint measPoint = (MeasPoint) o;
        return Double.compare(measPoint.x_coord, x_coord) == 0 &&
                Double.compare(measPoint.y_coord, y_coord) == 0 &&
                Double.compare(measPoint.z_coord, z_coord) == 0 &&
                Objects.equals(pointID, measPoint.pointID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointID, x_coord, y_coord, z_coord);
    }


    @Override
    public String toString() {
        return pointID + "#" +  x_coord + "#" + y_coord + "#" +
                z_coord +  "#" + pointType;
    }

}

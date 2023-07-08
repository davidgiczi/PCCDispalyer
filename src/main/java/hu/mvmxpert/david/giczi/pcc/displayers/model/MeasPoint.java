package hu.mvmxpert.david.giczi.pcc.displayers.model;

import javafx.scene.paint.Color;

import java.text.DecimalFormat;
import java.util.Objects;


public class MeasPoint {

    private final String pointID;
    private final double x_coord;
    private final double y_coord;
    private final double z_coord;
    private Color color;
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


    public double getX_coord() {
        return x_coord;
    }


    public double getY_coord() {
        return y_coord;
    }

    public double getZ_coord() {
        return z_coord;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
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
        return  pointID + ", "
                + df.format(x_coord).replace(",", ".") + ","
                + df.format(y_coord).replace(",", ".") + ", "
                + df.format(z_coord).replace(",", ".") + ", "
                + pointType;
    }

}

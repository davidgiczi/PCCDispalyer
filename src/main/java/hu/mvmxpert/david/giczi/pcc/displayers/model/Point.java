package hu.mvmxpert.david.giczi.pcc.displayers.model;

import java.text.DecimalFormat;
import java.util.Objects;

public class Point {

	private final String pointID;
	private double x_coord;
	private double y_coord;
	private DecimalFormat df = new DecimalFormat("0.000");
	
	public Point(String pointID, double x_coord, double y_coord) {

		this.pointID = pointID;
		this.x_coord = x_coord;
		this.y_coord = y_coord;
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
	
	public void setX_coord(double x_coord) {
		this.x_coord = x_coord;
	}


	public void setY_coord(double y_coord) {
		this.y_coord = y_coord;
	}



	@Override
	public int hashCode() {
		return Objects.hash(pointID, x_coord, y_coord);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		return Objects.equals(pointID, other.pointID)
				&& Double.doubleToLongBits(x_coord) == Double.doubleToLongBits(other.x_coord)
				&& Double.doubleToLongBits(y_coord) == Double.doubleToLongBits(other.y_coord);
	}

	@Override
	public String toString() {
		return  df.format(x_coord).replace(",", ".") + "\t"
				+ df.format(y_coord).replace(",", ".");
	}
}

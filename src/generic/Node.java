package generic;

import java.awt.geom.Point2D;

public class Node {

	private Point2D.Double position;
	private Building building;

	public Node(double x, double y) {
		this.position = new Point2D.Double(x, y);
	}

	public Node(double x, double y, Building building) {
		this.position = new Point2D.Double(x, y);
		this.building = building;
	}
}
package generic;

import generic.objects.Building;
import googlemaps.LatLng;

public class Node {

	transient private boolean startNode = false;
	transient private boolean endNode = false;
	private LatLng position;
	private Building building;

	public LatLng getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return "Node [startNode=" + startNode + ", endNode=" + endNode + ", position=" + position + ", building="
				+ building + "]";
	}

	public boolean isStart() {
		return startNode;
	}

	public boolean isEnd() {
		return endNode;
	}

	public void setStart(boolean s) {
		this.startNode = s;
	}

	public void setEnd(boolean e) {
		this.endNode = e;
	}

	public void setPosition(LatLng position) {
		this.position = position;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public Node(LatLng position) {
		this.position = position;
	}

	public Node(LatLng position, Building building) {
		this.position = position;
		this.building = building;
	}

	public Node(double latitude, double longitude, Building building, boolean isStartNode, boolean isEndNode) {
		this.position = new LatLng(latitude, longitude);
		this.building = building;
		this.startNode = isStartNode;
		this.endNode = isEndNode;
	}

	public Node() {
	}
}

package generic.objects;

import server.JSONTools;

public class Marker extends WalkerObject {
	private double latitude;
	private double longitude;
	private String title;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Marker(double latitude, double longitude, String title, String id) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.title = title;
		this.id = id;
	}

	@Override
	public String toString() {
		return "Marker [latitude=" + latitude + ", longitude=" + longitude + ", title=" + title + ", id=" + id + "]";
	}

	public Marker(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Marker() {

	}

	public String toJson() {
		String jsonString = JSONTools.g.toJson(this);
		return jsonString;
	}
}
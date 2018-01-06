package data;

import java.util.ArrayList;

/**
 * The class City contains high level data related to a city.
 * @author asthasharma017
 *
 */
public class City {
	private String name;
	private int latitude;
	private int longitude;
	//connectingCities is the list containing all the cities which are neighbors to the current city.
	private ArrayList<String> connectingCities;

	public City(String name, int latitude, int longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public City(String name) {
		this.name = name;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public ArrayList<String> getConnections() {
		return connectingCities;
	}

	public void setConnections(ArrayList<String> connectingCities) {
		this.connectingCities = connectingCities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "{" + name + "}";
	}

}

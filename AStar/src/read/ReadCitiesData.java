package read;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import data.CityHeuristic;
import gui.GUI;

/**
 * The class ReadCitiesData has methods to read and extract data from
 * locations.txt and connections.txt files.
 * 
 * @author asthasharma017
 *
 */
public class ReadCitiesData {

	public static final int STRAIGHT_LINE_DISTANCE_HEURISTIC = 1;
	public static final int MINIMUM_HOP_COUNT = 2;
	public static final String STR_STRAIGHT_LINE_DISTANCE_HEURISTIC = "Straight Line distance";
	public static final String STR_MINIMUM_HOP_COUNT = "Fewest Link";
	
	// cityMap stores all the data related to a city in key-value pair so that it's
	// easy to retrieve.
	public static HashMap<String, CityHeuristic> cityMap = new HashMap<String, CityHeuristic>();

	public static void main(String[] args) {
		System.out.println(
				"This program is an implementation of A* algorithm for finding shortest path between two cities. You can select one of the two heuristics, “Straight Line Distance” and the “Fewest Links” to get the shortest path");
		System.out.println("Developed by - Astha Sharma, Mounica Reddy");
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				JFrame ex = new GUI();
				ex.setVisible(true);
			}
		});
	}

	/**
	 * This method check is the city which user gave as input is present in our set
	 * of cities.
	 * 
	 * @param cityName
	 *            : name of the city to check
	 * @return boolean : false if city is not one of the cities from our data.
	 */
	public static boolean checkIfCityNameIsCorrent(String cityName) {
		return cityMap.containsKey(cityName.toUpperCase()) ? true : false;
	}

	/**
	 * readLocations method read locations.txt file using FileReader and loads it's
	 * contents into cityMap
	 * 
	 * @return true if location.txt is read successfully
	 */
	public static boolean readLocations(String locationsPath) {
		try {
			FileReader locationsFileReader = new FileReader(locationsPath);
			BufferedReader locationsBufferedReader = new BufferedReader(locationsFileReader);

			String line = "";
			while (!(line = locationsBufferedReader.readLine()).equals("END")) {
				String[] cityLocationData = line.split(" ");
				cityMap.put(cityLocationData[0].toUpperCase(), new CityHeuristic(cityLocationData[0].toUpperCase(),
						Integer.parseInt(cityLocationData[1]), Integer.parseInt(cityLocationData[2])));
			}
			locationsBufferedReader.close();
		} catch (FileNotFoundException exception) {
			System.out.println("Unable to open file '" + "'");
			return false;
		} catch (IOException exception) {
			System.out.println("Error reading file '" + "'");
			return false;
		} catch (Exception exception) {
			System.out.println("Error reading file '" + "'");
			return false;
		}
		return true;
	}

	/**
	 * readConnections method read connections.txt file using FileReader and loads
	 * it's contents into cityMap
	 * 
	 * @param connectionsPath
	 * @return true if location.txt is read successfully
	 */
	public static boolean readConnections(String connectionsPath) {
		try {
			FileReader connectionsFileReader = new FileReader(connectionsPath);
			BufferedReader connectionsBufferedReader = new BufferedReader(connectionsFileReader);

			String line = "";
			while (!(line = connectionsBufferedReader.readLine()).equals("END")) {
				String[] cityConnectionData = line.split(" ");
				int connectingCityCount;
				try {
					connectingCityCount = Integer.parseInt(cityConnectionData[1]);
				} catch (NumberFormatException exception) {
					System.out.print("City count is not valid!!");
					connectingCityCount = 0;
				}
				int i = connectingCityCount;
				ArrayList<String> connectingCities = new ArrayList<String>();
				while (i > 0) {
					connectingCities.add(cityConnectionData[i + 1].toUpperCase());
					i--;
				}
				(cityMap.get(cityConnectionData[0].toUpperCase())).setConnections(connectingCities);

			}
			connectionsBufferedReader.close();
		} catch (FileNotFoundException exception) {
			System.out.println("Unable to open file '" + "'");
			return false;
		} catch (IOException exception) {
			System.out.println("Error reading file '" + "'");
			return false;
		} catch (Exception exception) {
			System.out.println("Error reading file '" + "'");
			return false;
		}
		return true;
	}
}

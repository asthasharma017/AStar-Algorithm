package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Class AStarData contains data which is needed to keep track of the Algorithm.
 * @author asthasharma017
 *
 */
public class AStarData {
	//traversedCities is the lsit of closed nodes.
	public static ArrayList<CityHeuristic> traversedCities = new ArrayList<CityHeuristic>();
	//openNodes is the list of open nodes.
	public static PriorityQueue<CityHeuristic> openNodes = new PriorityQueue<CityHeuristic>();
	//currentCity is the current node for which calculation is being done.
	public static CityHeuristic currentCity;
	//cityCalculated is the map which contains all the cities whether open or close for which distance calculation has been done. This map is required for easy retrieval of city related data.
	public static HashMap<String, CityHeuristic> cityCalculated = new HashMap<String, CityHeuristic>();
	//These strings stores cities in string form
	public static StringBuilder traversedPath;
	public static StringBuilder finalPath; 
}

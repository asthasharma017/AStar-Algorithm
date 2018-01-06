package algo;

import java.util.Stack;

import data.AStarData;
import data.City;
import data.CityHeuristic;
import read.ReadCitiesData;

/**
 * Class AStarProcedure contains procedure to calculate the shortest path.
 * 
 * @author asthasharma017
 *
 */
public class AStarProcedure {

	/**
	 * Method searchShortestPath calls procedure to calculate shortest path and
	 * displays the shortest path.
	 * 
	 * @param startCity
	 *            : start city
	 * @param end
	 *            : destination city
	 * @param intHeuristic
	 *            : Heuristic type (straight line distance or minimum hop count)
	 * @param i
	 */
	public void searchShortestPath(CityHeuristic startCity, City endCity, int intHeuristic) {
		displayUserInput(intHeuristic, startCity, endCity);
		AStarData.currentCity = startCity;
		startCity.setActualCost(0);
		startCity.setPreviousCityName(null);
		startCity.setHeuristicEstimate(calculateDistance(startCity, endCity));
		displayCurrentPath(startCity, false);
		// This loop runs till the procedure reaches to destination city. At that point,
		// currentCity would be equal to endCity.
		while (null != AStarData.currentCity && !AStarData.currentCity.equals(endCity)) {
			traverseCity(AStarData.currentCity, endCity, intHeuristic);
		}

	}

	/**
	 * Method displayUserInput displays the formated user input.
	 * 
	 * @param intHeuristic
	 * @param startCity
	 * @param endCity
	 */
	private void displayUserInput(int intHeuristic, CityHeuristic startCity, City endCity) {
		System.out.println("Heuristic: " + (intHeuristic == ReadCitiesData.STRAIGHT_LINE_DISTANCE_HEURISTIC
				? ReadCitiesData.STR_STRAIGHT_LINE_DISTANCE_HEURISTIC
				: ReadCitiesData.STR_MINIMUM_HOP_COUNT) + ":");
		System.out.println("Starting city: " + startCity.getName());
		System.out.println("Target city: " + endCity.getName() + "\n");
	}

	/**
	 * The method traverseCity traverses all the cities, calculates actual and
	 * heuristic distances, adds traversed cities into open and closed nodes lists
	 * and updates current cities.
	 * 
	 * @param currentCity
	 *            : The city for which calculation is being done currently.
	 * @param endCity
	 * @param intHeuristic
	 *            : Heuristic type (straight line distance or minimum hop count)
	 */
	private void traverseCity(CityHeuristic currentCity, City endCity, int intHeuristic) {
		if (intHeuristic == ReadCitiesData.STRAIGHT_LINE_DISTANCE_HEURISTIC) {
			for (String connectingCityName : currentCity.getConnections()) {
				CityHeuristic connectingCity = ReadCitiesData.cityMap.get(connectingCityName);
				// connectingCity == null means user has selected this city not to be included
				// in the optimal path. In this case we skip to the next iteration of the loop.
				if (connectingCity == null) {
					continue;
				}
				// If the connectingCity is in the list of closed nodes, we do not need to
				// calculate the data again and can skip to the next iteration of the loop.
				if (!AStarData.traversedCities.contains(connectingCity)) {
					double currentCost = currentCity.getActualCost() + calculateDistance(connectingCity, currentCity);
					// This condition checks if calculation is already done for the connectingCity.
					if (AStarData.cityCalculated.containsKey(connectingCity.getName())) {
						CityHeuristic previousCityHuristic = AStarData.cityCalculated.get(connectingCity.getName());
						// If calculation is already done for the connectingCity, we check if an update
						// is needed. If the actual distance calculated from current path is greater
						// than or equal to the actual distance calculated previous path, we don't need
						// an update for this city and current iteration of the loop is skipped.
						if (previousCityHuristic.getActualCost() <= currentCost) {
							continue;
						}
						// Actual distance and parent node are updated only if the actual distance
						// calculated from current path is lesser that the actual distance calculated
						// previously.
						else {
							connectingCity.setPreviousCityName(currentCity.getName());
							connectingCity.setActualCost(currentCost);
							continue;
						}
					}
					// If the connectingCity is not previously calculated, we set all the city
					// related data.
					connectingCity.setPreviousCityName(currentCity.getName());
					connectingCity.setActualCost(currentCost);
					connectingCity.setHeuristicEstimate(calculateDistance(connectingCity, endCity));

					AStarData.openNodes.add(connectingCity);
					AStarData.cityCalculated.put(connectingCity.getName(), connectingCity);
					if (connectingCity.equals(endCity)) {
						AStarData.currentCity = connectingCity;
						AStarData.traversedCities.add(AStarData.currentCity);
						displayCurrentPath(connectingCity, true);
						return;
					} else {
						displayCurrentPath(connectingCity, false);
					}
				}
			}
		} else if (intHeuristic == ReadCitiesData.MINIMUM_HOP_COUNT) {
			for (String connectingCityName : currentCity.getConnections()) {
				CityHeuristic connectingCity = ReadCitiesData.cityMap.get(connectingCityName);
				// connectingCity == null means user has selected this city not to be included
				// in optimal path
				if (connectingCity == null) {
					break;
				}
				// If the connectingCity is in the list of closed nodes or calculation is
				// already done for connectingCity, we don't need to do it again.
				if (!AStarData.traversedCities.contains(connectingCity)
						&& !AStarData.cityCalculated.containsKey(connectingCity.getName())) {
					connectingCity.setPreviousCityName(currentCity.getName());
					connectingCity.setActualCost(currentCity.getActualCost() + 1);
					connectingCity.setHeuristicEstimate(1);

					AStarData.openNodes.add(connectingCity);
					AStarData.cityCalculated.put(connectingCity.getName(), connectingCity);
					if (connectingCity.equals(endCity)) {
						AStarData.currentCity = connectingCity;
						AStarData.traversedCities.add(AStarData.currentCity);
						displayCurrentPath(connectingCity, true);
						return;
					} else {
						displayCurrentPath(connectingCity, false);
					}
				}
			}
		}
		// Once all the connecting cities to the current city have been traversed, we
		// add the current city to the traversedCities list.
		AStarData.traversedCities.add(AStarData.currentCity);
		// The method poll() removes and returns city with the lowest value of actual
		// cost + estimated heuristic cost.
		AStarData.currentCity = AStarData.openNodes.poll();
	}

	/**
	 * Method displayCurrentPath displays current path on every hop.
	 * 
	 * @param city
	 * @param isFinalPath
	 */
	private void displayCurrentPath(CityHeuristic city, boolean isFinalPath) {
		System.out.println((isFinalPath ? "Final " : "Current Optimal ") + "Path:");
		// Stack cityStackTemp is a temporary stack to store path from current node to
		// the first node in order to display it in correct manner.
		Stack<CityHeuristic> cityStackTemp = new Stack<CityHeuristic>();
		cityStackTemp.push(city);
		double currentCost = city.getActualCost();
		// This loop pushes the whole path in the stack.
		while (null != city.getPreviousCityName()) {
			city = ReadCitiesData.cityMap.get(city.getPreviousCityName());
			cityStackTemp.push(city);
		}
		// This loop pops and displays whole path from the stack.
		while (!cityStackTemp.isEmpty()) {
			if (isFinalPath) {
				AStarData.finalPath.append(cityStackTemp.peek().getName());
				AStarData.traversedPath.append(cityStackTemp.peek().getName());
			} else {
				AStarData.traversedPath.append(cityStackTemp.peek().getName());
			}
			System.out.print(cityStackTemp.pop().getName());
			if (!cityStackTemp.isEmpty()) {
				if (isFinalPath) {
					AStarData.finalPath.append("->");
					AStarData.traversedPath.append("->");
				} else {
					AStarData.traversedPath.append("->");
				}
				System.out.print("->");
			}
		}
		if(!isFinalPath) {
			AStarData.traversedPath.append("\n");
		}
		System.out.print("\n");
		System.out.println("Distance Traveled: " + currentCost);
	}

	/**
	 * This method calculates and returns the distance between two cities calculated
	 * using distance formula.
	 * 
	 * @param start
	 * @param end
	 * @return distance
	 */
	private double calculateDistance(CityHeuristic start, City end) {
		return Math.sqrt(Math.pow(start.getLatitude() - end.getLatitude(), 2)
				+ Math.pow(start.getLongitude() - end.getLongitude(), 2));
	}

}

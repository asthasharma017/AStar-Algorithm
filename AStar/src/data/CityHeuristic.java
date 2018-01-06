package data;

/**
 * Class CityHuristic is the child class of City since it contains some low level data related to a city, calculated during the search procedure. 
 * It needs to be added into PriorityQueue, so it implements Comparable.
 * @author asthasharma017
 *
 */
public class CityHeuristic extends City implements Comparable<CityHeuristic> {

	//actualCost at current node is the total cost of the path till current node.
	private double actualCost;
	//heuristicEstimate at current node is the underestimated cost from current node to the destination.
	private double heuristicEstimate;
	//previousCityName is the parent of current node.
	private String previousCityName;

	public CityHeuristic(String name, int latitude, int longitude) {
		super(name, latitude, longitude);
	}

	public String getPreviousCityName() {
		return previousCityName;
	}

	public void setPreviousCityName(String currentCityName) {
		this.previousCityName = currentCityName;
	}

	public double getHeuristicEstimate() {
		return heuristicEstimate;
	}

	public void setHeuristicEstimate(double heuristicEstimate) {
		this.heuristicEstimate = heuristicEstimate;
	}

	public double getActualCost() {
		return actualCost;
	}

	public void setActualCost(double actualCost) {
		this.actualCost = actualCost;
	}

	/**
	 * compareTo gets called every time when an element is removed from PriorityQueue in order to get the element with least cost.
	 */
	@Override
	public int compareTo (CityHeuristic o) {
		double cost1 = getActualCost() + getHeuristicEstimate(); 
		double cost2 = o.getActualCost() + o.getHeuristicEstimate(); 
		return cost1 <= cost2 ? -1 : 1 ;
	}

}

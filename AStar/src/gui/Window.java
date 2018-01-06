package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import algo.AStarProcedure;
import data.AStarData;
import data.CityHeuristic;
import read.ReadCitiesData;

public class Window extends JPanel implements ActionListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	public static int frames;

	private ArrayList<String> traversedCities = new ArrayList<String>();
	private ArrayList<String> tempCitiesList = new ArrayList<String>();

	public JButton[] buttons;

	public JLabel[] lables;

	public JTextField[] textFields;

	JPanel panel;

	Graphics g;

	private String strStartCity;

	private String strEndCity;

	private int intHeuristicType;

	public Window(JPanel panel) {
		this.panel = panel;
		setLayout(null);
		setDoubleBuffered(true);
		setupLeftPanel(panel);

	}

	/**
	 * loadTraveredsCitiesList method loads traversedPath StringBuffer to
	 * traversedCities list and tempCitiesList. These two lists are being used in
	 * logic to display the path.
	 */
	private void loadTraveredsCitiesList() {
		String[] citiesArray = AStarData.traversedPath.toString().split("\n");
		for (int i = 0; i < citiesArray.length; i++) {
			String[] cities = citiesArray[i].split("->");
			for (int i1 = 0; i1 < cities.length; i1++) {
				traversedCities.add(cities[i1]);
			}
			traversedCities.add("-");
		}
		tempCitiesList.addAll(traversedCities);
	}

	/**
	 * displayCityMap method displays cities as a map.
	 * @param panel: panel to add components.
	 */
	private void displayCityMap(final JPanel panel) {
		Iterator<Entry<String, CityHeuristic>> iterator = ReadCitiesData.cityMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, CityHeuristic> cityEntry = iterator.next();
			CityHeuristic city = cityEntry.getValue();
			final Label label = new Label(city.getName());
			label.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					CityHeuristic cityHeuristic = ReadCitiesData.cityMap.get(label.getText());
					int newX = cityHeuristic.getLatitude() + e.getX();
					int newY = cityHeuristic.getLongitude() + e.getY();
					cityHeuristic.setLatitude(newX);
					cityHeuristic.setLongitude(newY);
					label.setBounds(newX + Constants.margin_left, newY, label.getPreferredSize().width,
							label.getPreferredSize().height);
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					String s = label.getText();
					s.toCharArray();
				}
			});
			panel.add(label);
			label.setBounds(city.getLatitude() + Constants.margin_left, city.getLongitude(),
					label.getPreferredSize().width, label.getPreferredSize().height);
		}
	}

	/**
	 * setupLeftPanel method adds input components to the left of the panel. 
	 * @param panel: panel to add components.
	 */
	public void setupLeftPanel(JPanel panel) {
		Insets insets = panel.getInsets();

		this.lables = new JLabel[7];

		this.textFields = new JTextField[6];

		this.lables[0] = new JLabel(Constants.STR_ENTER_LOCATIONS_PATH);
		this.lables[0].setBounds(insets.left, insets.top, lables[0].getPreferredSize().width,
				lables[0].getPreferredSize().height);
		panel.add(lables[0]);

		this.lables[1] = new JLabel(Constants.STR_ENTER_CONNECTIONS_PATH);
		this.lables[1].setBounds(insets.left, insets.top + 40, lables[1].getPreferredSize().width,
				lables[1].getPreferredSize().height);
		panel.add(lables[1]);

		this.lables[2] = new JLabel(Constants.STR_ENTER_START_CITY_NAME);
		this.lables[2].setBounds(insets.left, insets.top + 80, lables[2].getPreferredSize().width,
				lables[2].getPreferredSize().height);
		panel.add(lables[2]);

		this.lables[3] = new JLabel(Constants.STR_ENTER_END_CITY_NAME);
		this.lables[3].setBounds(insets.left, insets.top + 120, lables[3].getPreferredSize().width,
				lables[3].getPreferredSize().height);
		panel.add(lables[3]);

		this.lables[4] = new JLabel(Constants.STR_ENTER_CITIES_TO_EXCLUDE);
		this.lables[4].setBounds(insets.left, insets.top + 160, lables[4].getPreferredSize().width,
				lables[4].getPreferredSize().height);
		panel.add(lables[4]);

		this.lables[5] = new JLabel(Constants.STR_ENTER_HEURISTIC_TYPE);
		this.lables[5].setBounds(insets.left, insets.top + 200, lables[5].getPreferredSize().width,
				lables[5].getPreferredSize().height);
		panel.add(lables[5]);

		this.lables[6] = new JLabel(Constants.ERROR);
		this.lables[6].setBounds(insets.left, insets.top + 240, lables[6].getPreferredSize().width,
				lables[6].getPreferredSize().height);
		panel.add(lables[6]);
		this.lables[6].setVisible(false);

		this.textFields[0] = new JTextField();
		this.textFields[0].setBounds(insets.left, insets.top + 20, Constants.tfWidth,
				textFields[0].getPreferredSize().height);
		panel.add(textFields[0]);

		this.textFields[1] = new JTextField();
		this.textFields[1].setBounds(insets.left, insets.top + 60, Constants.tfWidth,
				textFields[1].getPreferredSize().height);
		panel.add(textFields[1]);

		this.textFields[2] = new JTextField();
		this.textFields[2].setBounds(insets.left, insets.top + 100, Constants.tfWidth,
				textFields[2].getPreferredSize().height);
		panel.add(textFields[2]);

		this.textFields[3] = new JTextField();
		this.textFields[3].setBounds(insets.left, insets.top + 140, Constants.tfWidth,
				textFields[3].getPreferredSize().height);
		panel.add(textFields[3]);

		this.textFields[4] = new JTextField();
		this.textFields[4].setBounds(insets.left, insets.top + 180, Constants.tfWidth,
				textFields[4].getPreferredSize().height);
		panel.add(textFields[4]);

		this.textFields[5] = new JTextField();
		this.textFields[5].setBounds(insets.left, insets.top + 220, Constants.tfWidth,
				textFields[5].getPreferredSize().height);
		panel.add(textFields[5]);

		this.buttons = new JButton[5];

		this.buttons[0] = new JButton(Constants.STR_NEXT_STEP);
		this.buttons[0].setActionCommand(Constants.ACTION_NEXT_STEP);
		this.buttons[0].addActionListener(this);
		Dimension size = this.buttons[0].getPreferredSize();
		this.buttons[0].setBounds(insets.left, insets.top + 300, size.width, size.height);
		this.buttons[0].setVisible(false);
		panel.add(this.buttons[0]);

		this.buttons[1] = new JButton(Constants.STR_FINAL_PATH);
		this.buttons[1].setActionCommand(Constants.ACTION_FINAL_PATH);
		this.buttons[1].addActionListener(this);
		this.buttons[1].setBounds(insets.left, insets.top + 340, size.width, size.height);
		this.buttons[1].setVisible(false);
		panel.add(this.buttons[1]);

		this.buttons[2] = new JButton(Constants.STR_GO);
		this.buttons[2].setActionCommand(Constants.ACTION_GO);
		this.buttons[2].addActionListener(this);
		this.buttons[2].setBounds(insets.left, insets.top + 260, size.width, size.height);
		panel.add(this.buttons[2]);

		this.buttons[3] = new JButton(Constants.STR_RESET);
		this.buttons[3].setActionCommand(Constants.ACTION_RESET);
		this.buttons[3].addActionListener(this);
		this.buttons[3].setBounds(insets.left, insets.top + 380, size.width, size.height);
		this.buttons[3].setVisible(false);
		panel.add(this.buttons[3]);

		this.buttons[4] = new JButton(Constants.STR_CALCULATE_DISTANCE);
		this.buttons[4].setActionCommand(Constants.ACTION_CALCULATE_DISTANCE);
		this.buttons[4].addActionListener(this);
		this.buttons[4].setBounds(insets.left, insets.top + 260, size.width, size.height);
		this.buttons[4].setVisible(false);
		panel.add(this.buttons[4]);

	}

	public void actionPerformed(ActionEvent e) {
		String a = e.getActionCommand();

		switch (a) {
		case Constants.ACTION_NEXT_STEP:
			showNextStep();
			break;
		case Constants.ACTION_FINAL_PATH:
			showFinalPath();
			break;
		case Constants.ACTION_GO:
			go();
			break;
		case Constants.ACTION_RESET:
			resetDisplay();
			break;
		case Constants.ACTION_CALCULATE_DISTANCE:
			calculateDistance();
		default:
			break;
		}
	}

	/**
	 * calculateDistance method internally gives a call to the method to find the shortest path.
	 */
	private void calculateDistance() {
		resetPreviousCalculations();
		new AStarProcedure().searchShortestPath(ReadCitiesData.cityMap.get(strStartCity.toUpperCase()),
				ReadCitiesData.cityMap.get(strEndCity.toUpperCase()), intHeuristicType);
		this.buttons[0].setVisible(true);
		this.buttons[1].setVisible(true);
		this.buttons[3].setVisible(true);
		loadTraveredsCitiesList();
	}

	/**
	 * resetPreviousCalculations makes the code ready to calculate the distance again.
	 */
	private void resetPreviousCalculations() {
		AStarData.traversedPath = new StringBuilder();
		AStarData.finalPath = new StringBuilder();
		AStarData.traversedCities.clear();
		AStarData.openNodes.clear();
		AStarData.cityCalculated.clear();
		tempCitiesList.clear();
		traversedCities.clear();
	}

	/**
	 * resetDisplay resets all the input components to the initial stage.
	 */
	private void resetDisplay() {
		textFields[0].setText("");
		textFields[1].setText("");
		textFields[2].setText("");
		textFields[3].setText("");
		textFields[4].setText("");
		textFields[5].setText("");

		buttons[0].setVisible(false);
		buttons[1].setVisible(false);
		buttons[3].setVisible(false);
		buttons[4].setVisible(false);
		buttons[2].setVisible(true);

		panel.revalidate();
		panel.repaint();
		Component[] c = panel.getComponents();
		for (Component component : c) {
			if (component instanceof Label) {
				panel.remove(component);
			}
		}
		ReadCitiesData.cityMap.clear();
		resetPreviousCalculations();
	}

	/**
	 * go method reads and validates all the input data.
	 */
	private void go() {
		String strLocationsPath = this.textFields[0].getText();
		String strConnectionsPath = this.textFields[1].getText();
		strStartCity = this.textFields[2].getText();
		strEndCity = this.textFields[3].getText();
		String strCitiesToExclude = this.textFields[4].getText();
		try {
			intHeuristicType = Integer.parseInt(this.textFields[5].getText());
		} catch (Exception e) {
			lables[6].setVisible(true);
			return;
		}
		if (!ReadCitiesData.readLocations(strLocationsPath) || !ReadCitiesData.readConnections(strConnectionsPath)) {

			lables[6].setVisible(true);
			return;
		} else if (strLocationsPath.trim().length() == 0 || strConnectionsPath.trim().length() == 0
				|| strStartCity.trim().length() == 0 | strEndCity.trim().length() == 0
				|| !(intHeuristicType == 1 || intHeuristicType == 2)
				|| !ReadCitiesData.checkIfCityNameIsCorrent(strStartCity)
				|| !ReadCitiesData.checkIfCityNameIsCorrent(strEndCity)
				|| !excludeCities(strCitiesToExclude, strStartCity, strEndCity)) {
			lables[6].setVisible(true);
			return;
		}
		lables[6].setVisible(false);

		displayCityMap(panel);

		this.buttons[2].setVisible(false);
		this.buttons[4].setVisible(true);

	}

	/**
	 * excludeCities gets comma separated city names as parameter and removes from
	 * cityMap.
	 * 
	 * @param strCitiesToExclude
	 * @param destination
	 * @param start
	 */
	private static boolean excludeCities(String strCitiesToExclude, String start, String destination) {
		if (strCitiesToExclude.toUpperCase().contains(start.toUpperCase())
				|| strCitiesToExclude.toUpperCase().contains(destination.toUpperCase())) {
			return false;
		}
		String[] arrCityNamesToExclude = strCitiesToExclude.split(",");
		for (String string : arrCityNamesToExclude) {
			ReadCitiesData.cityMap.remove(string.trim().toUpperCase());
		}
		return true;
	}

	/**
	 * showFinalPath method displays the final path.
	 */
	private void showFinalPath() {
		String[] cities = AStarData.finalPath.toString().split("->");
		g = panel.getGraphics();
		for (int i = 0; i < cities.length - 1; i++) {
			CityHeuristic city1 = ReadCitiesData.cityMap.get(cities[i]);
			CityHeuristic city2 = ReadCitiesData.cityMap.get(cities[i + 1]);
			g.drawLine(city1.getLatitude() + Constants.margin_left, city1.getLongitude(),
					city2.getLatitude() + Constants.margin_left, city2.getLongitude());
		}
	}

	/**
	 * showNextStep method displays the path to the next city traversed.
	 */
	private void showNextStep() {
		g = panel.getGraphics();
		if (traversedCities.size() > 0) {
			CityHeuristic city1 = ReadCitiesData.cityMap.get(traversedCities.get(0));
			CityHeuristic city2 = ReadCitiesData.cityMap.get(traversedCities.get(1));
			if (null != city1 && null != city2) {
				g.drawLine(city1.getLatitude() + Constants.margin_left, city1.getLongitude(),
						city2.getLatitude() + Constants.margin_left, city2.getLongitude());
			} else {
				panel.repaint();
				traversedCities.remove(1);
			}
			traversedCities.remove(0);
		} else {
			traversedCities.addAll(tempCitiesList);
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

}

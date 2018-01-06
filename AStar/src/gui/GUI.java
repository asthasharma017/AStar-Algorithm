package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame {

	/**
	 * serialVersionUID needs to be generated for every class that creates a GUI
	 * component to keep track if the GUI component is called by and returned to the
	 * same class.
	 */
	private static final long serialVersionUID = 2941318999657277463L;

	public GUI() {

		Window.frames = 0;

		setDefaultCloseOperation(3);
		setSize(2000, 2000);
		setLocationRelativeTo(null);
		setTitle("A Star");

		setBackground(Color.BLACK);

		JPanel panel = (JPanel) getContentPane();
		panel.setLayout(null);

		panel.add(new Window(panel));

		setResizable(false);

	}

}

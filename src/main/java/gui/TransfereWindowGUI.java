package gui;

//Java program to create a blank text 
//field of definite number of columns. 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TransfereWindowGUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// JTextField
	static JTextField t;

	// JFrame
	static JFrame f;

	// JButton
	static JButton b;

	// JLabel
	static JLabel l;

	// Value
	public static String value;

	// Monitor
	public static Object modalMonitor = new Object();

	public static String handleWindow() {

		value = null;

		createWindow();
		showWindow();
		synchronized (TransfereWindowGUI.modalMonitor) {
			try {
				TransfereWindowGUI.modalMonitor.wait(50000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		TransfereWindowGUI.hideWindow();
		return value;
	}

	public static void createWindow() {

		// create a new frame to store text field and button
		f = new JFrame("STEP 3: Transfering Troops\n");

		// create a new button
		b = new JButton("ATTACK");

		// create a new label
		l = new JLabel("Enter Transfere Count\n");

		// create a object of the text class
		TransfereWindowGUI inputGUI = new TransfereWindowGUI();

		// addActionListener to button
		b.addActionListener(inputGUI);

		// create a object of JTextField with 16 columns
		t = new JTextField(16);

		// create a panel to add buttons and text field
		JPanel p = new JPanel();

		// add buttons and text field to panel
		p.add(l);
		p.add(t);
		p.add(b);

		// add panel to frame
		f.add(p);

		// set the size of frame
		f.setSize(300, 300);

	}

	public static void showWindow() {
		f.setVisible(true);
	}

	public static void hideWindow() {
		f.dispose();
	}

	// if the button is pressed
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s.equals("ATTACK")) {

			value = t.getText();

			synchronized (modalMonitor) {
				modalMonitor.notify();
			}

		}
	}

}
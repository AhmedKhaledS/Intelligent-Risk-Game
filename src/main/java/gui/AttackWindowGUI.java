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

public class AttackWindowGUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// JTextField
	static JTextField t1;
	static JTextField t2;

	// JFrame
	static JFrame f;

	// JButton
	static JButton b;

	// JLabel
	static JLabel l1;
	static JLabel l2;

	// Value
	public static String value1;
	public static String value2;

	// Monitor
	public static Object modalMonitor = new Object();

	public static String[] handleWindow() {

		value1 = null;
		value2 = null;

		createWindow();
		showWindow();
		synchronized (AttackWindowGUI.modalMonitor) {
			try {
				AttackWindowGUI.modalMonitor.wait(50000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		AttackWindowGUI.hideWindow();
		return new String[] { value1, value2 };
	}

	public static void createWindow() {

		// create a new frame to store text field and button
		f = new JFrame("STEP 2: Perfomring the attack");

		// create a new button
		b = new JButton("CONFIRM");

		// create a new label
		l1 = new JLabel("Enter Attacking Country ID (-1 for skip)\n");
		l2 = new JLabel("Enter Attacked Country ID  (-1 for skip)\n");

		// create a object of the text class
		AttackWindowGUI inputGUI = new AttackWindowGUI();

		// addActionListener to button
		b.addActionListener(inputGUI);

		// create a object of JTextField with 16 columns
		t1 = new JTextField(16);
		t2 = new JTextField(16);

		// create a panel to add buttons and text field
		JPanel p = new JPanel();

		// add buttons and text field to panel
		p.add(l1);
		p.add(t1);
		p.add(l2);
		p.add(t2);
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
		if (s.equals("CONFIRM")) {

			value1 = t1.getText();
			value2 = t2.getText();

			synchronized (modalMonitor) {
				modalMonitor.notify();
			}

		}
	}

}
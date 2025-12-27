package JEST.graphicalInterface;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class GraphicalInterface {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicalInterface window = new GraphicalInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GraphicalInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("JEST");
		frame.setBounds(100, 100, 792, 593);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton referenceCardOriginal = new JButton("");
		referenceCardOriginal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		referenceCardOriginal.setIcon(new ImageIcon(GraphicalInterface.class.getResource("/pictures/reference_card.png")));
		referenceCardOriginal.setBounds(25, 32, 127, 181);
		frame.getContentPane().add(referenceCardOriginal);
		
		
		
		
	}

}

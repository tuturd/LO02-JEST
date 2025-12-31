package JEST.view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MakeOffer {

	private JButton referenceCardOriginal;
	private JFrame frame;
	
	/*referenceCardOriginal = new JButton("");
	referenceCardOriginal.setIcon(
			new ImageIcon(Objects.requireNonNull(getClass().getResource("/JEST/static/reference_card.png"))));
	referenceCardOriginal.setBounds(25, 32, 127, 181);
	referenceCardOriginal.setVisible(false);
	add(this.referenceCardOriginal);
	
		public void addReferenceCardListener(ActionListener listener) {
		referenceCardOriginal.addActionListener(listener);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MakeOffer window = new MakeOffer();
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
	public MakeOffer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

package JEST.view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainWindow {

	private JFrame frame;
	private JPanel container;
	private CardLayout layout;

	private static final String[] STEPS = {
			"START",
			"CONFIG_NEW",
			"LOAD",
			"CHOOSE_OFFER",
			"MAKE_OFFER",
			"RESULT"
	};

	public MainWindow() {
		this.frame = new JFrame("JEST");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(800, 600);

		this.layout = new CardLayout();
		this.container = new JPanel(layout);

		this.container.add(new PartyStartInterface(this), STEPS[0]);
		this.container.add(new NewGameCreation(this), STEPS[1]);

		this.frame.setContentPane(container);
		this.frame.setVisible(true);
	}

	public void show(int step) {
		layout.show(container, STEPS[step]);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainWindow::new);
	}
}
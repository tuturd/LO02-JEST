package JEST.view;

import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ChooseAnOffer extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private MainWindow main;
	private JButton referenceCardOriginal;
	
	public ChooseAnOffer(MainWindow main) {
		this.main = main;
		
		setLayout(null);
		
		referenceCardOriginal = new JButton("");
		referenceCardOriginal.setIcon(
				new ImageIcon(Objects.requireNonNull(getClass().getResource("/JEST/static/reference_card.png"))));
		referenceCardOriginal.setBounds(25, 32, 127, 181);
		add(this.referenceCardOriginal);
	}
	
	
	public void addReferenceCardListener(ActionListener listener) {
		referenceCardOriginal.addActionListener(listener);
	}
}

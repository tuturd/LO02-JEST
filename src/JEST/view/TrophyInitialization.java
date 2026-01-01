package JEST.view;

import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import JEST.model.Game;
import JEST.model.cards.Card;
import JEST.model.cards.SuitCard;

public class TrophyInitialization extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private MainWindow main;
	private JButton referenceCardOriginal, trophy1, trophy2;
	private final Game game = Game.getInstance();
	
	public TrophyInitialization(MainWindow main) {
		this.main = main;
		
		setLayout(null);
		
		referenceCardOriginal = new JButton("");
		referenceCardOriginal.setIcon(
				new ImageIcon(Objects.requireNonNull(getClass().getResource("/JEST/static/reference_card.png"))));
		referenceCardOriginal.setBounds(10, 10, 127, 181);
		add(this.referenceCardOriginal);
		
		/*trophy1 = new JButton("");
		trophy1.setIcon(
				new ImageIcon(Objects.requireNonNull(getClass().getResource(game.getTrophyCards().get(0).getPicturePath()))));
		trophy1.setBounds(147, 10, 127, 181);
		add(trophy1);
		
		if (game.getPlayers().size() == 3) {
			trophy2 = new JButton("");
			trophy2.setIcon(
					new ImageIcon(Objects.requireNonNull(getClass().getResource(game.getTrophyCards().get(1).getPicturePath()))));
			trophy2.setBounds(284, 10, 127, 181);
			add(trophy2);
		}*/
	}
	
	
	public void addReferenceCardListener(ActionListener listener) {
		referenceCardOriginal.addActionListener(listener);
	}
}

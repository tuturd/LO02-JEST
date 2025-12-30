package JEST.view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GraphicalInterface extends JFrame {

    private JButton referenceCardOriginal;

    public GraphicalInterface() {
        super("JEST");
        initialize();
    }

    private void initialize() {
        setBounds(100, 100, 792, 593);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        referenceCardOriginal = new JButton("");
        referenceCardOriginal.setIcon(
                new ImageIcon(
                        Objects.requireNonNull(
                        		getClass().getResource("/JEST/static/reference_card.png")
                        )
                )
        );
        referenceCardOriginal.setBounds(25, 32, 127, 181);
        getContentPane().add(referenceCardOriginal);
    }

    public void addReferenceCardListener(ActionListener listener) {
        referenceCardOriginal.addActionListener(listener);
    }
}

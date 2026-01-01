package JEST.view;

import javax.swing.*;

import JEST.model.Game;

import java.awt.Font;

public class NewGameCreation extends JPanel {

    private static final long serialVersionUID = 1L;

    // ===== UI =====
    private JLabel lblNombreDeJoueurs;
    private JLabel nomJoueur;

    private JButton btn3Players;
    private JButton btn4Players;

    private JButton btnOui;
    private JButton btnNon;

    private JButton btnSuivant;

    private JButton btnRandom;
    private JButton btnDefensive;
    private JButton btnAggressive;

    private JTextField txtPrenomNom;

    // ===== Game =====
    private final Game game = Game.getInstance();

    // ===== Gestion joueurs =====
    private int totalPlayers;
    private int currentPlayer = 1;
    private boolean isVirtualPlayer;

    // ===== Etats =====
    private static final int STEP_FIRSTNAME = 0;
    private static final int STEP_LASTNAME  = 1;
    private static final int STEP_STRATEGY  = 2;

    private int step;
    private String firstName;
    private String lastName;
    private MainWindow main;

    public NewGameCreation(MainWindow main) {

    	this.main = main;
    	
        setLayout(null);

        // ===== Choix nombre joueurs =====
        lblNombreDeJoueurs = new JLabel("Nombre de joueurs ?");
        lblNombreDeJoueurs.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombreDeJoueurs.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        lblNombreDeJoueurs.setBounds(167, 30, 322, 50);
        add(lblNombreDeJoueurs);

        btn3Players = new JButton("3");
        btn3Players.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btn3Players.setBounds(140, 116, 128, 31);
        btn3Players.addActionListener(e -> setPlayers(3));
        add(btn3Players);

        btn4Players = new JButton("4");
        btn4Players.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btn4Players.setBounds(413, 116, 128, 31);
        btn4Players.addActionListener(e -> setPlayers(4));
        add(btn4Players);

        // ===== Label dynamique =====
        nomJoueur = new JLabel();
        nomJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        nomJoueur.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        nomJoueur.setBounds(167, 30, 322, 50);
        nomJoueur.setVisible(false);
        add(nomJoueur);

        // ===== Virtuel ? =====
        btnOui = new JButton("OUI");
        btnOui.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btnOui.setBounds(140, 116, 128, 31);
        btnOui.setVisible(false);
        btnOui.addActionListener(e -> {
            isVirtualPlayer = true;
            startPlayerCreation();
        });
        add(btnOui);

        btnNon = new JButton("NON");
        btnNon.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btnNon.setBounds(413, 116, 128, 31);
        btnNon.setVisible(false);
        btnNon.addActionListener(e -> {
            isVirtualPlayer = false;
            startPlayerCreation();
        });
        add(btnNon);

        // ===== Champ texte =====
        txtPrenomNom = new JTextField();
        txtPrenomNom.setBounds(195, 90, 265, 31);
        txtPrenomNom.setVisible(false);
        add(txtPrenomNom);

        // ===== Bouton suivant =====
        btnSuivant = new JButton("Suivant");
        btnSuivant.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
        btnSuivant.setBounds(413, 157, 128, 31);
        btnSuivant.setVisible(false);
        btnSuivant.addActionListener(e -> handleNext());
        add(btnSuivant);

        // ===== Boutons stratégies =====
        btnRandom = new JButton("Aléatoire");
        btnRandom.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
        btnRandom.setBounds(89, 117, 128, 31);
        btnRandom.setVisible(false);
        btnRandom.addActionListener(e -> selectStrategy("1"));
        add(btnRandom);

        btnDefensive = new JButton("Défensive");
        btnDefensive.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
        btnDefensive.setBounds(260, 117, 128, 31);
        btnDefensive.setVisible(false);
        btnDefensive.addActionListener(e -> selectStrategy("2"));
        add(btnDefensive);

        btnAggressive = new JButton("Agressive");
        btnAggressive.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
        btnAggressive.setBounds(441, 117, 128, 31);
        btnAggressive.setVisible(false);
        btnAggressive.addActionListener(e -> selectStrategy("3"));
        add(btnAggressive);
    }

    // =====================================================
    // =================== LOGIQUE =========================
    // =====================================================

    private void setPlayers(int number) {
        totalPlayers = number;
        currentPlayer = 1;

        lblNombreDeJoueurs.setVisible(false);
        btn3Players.setVisible(false);
        btn4Players.setVisible(false);

        nomJoueur.setVisible(true);
        btnOui.setVisible(true);
        btnNon.setVisible(true);

        nomJoueur.setText("Le joueur " + currentPlayer + " est-il virtuel ?");
    }

    private void startPlayerCreation() {

        btnOui.setVisible(false);
        btnNon.setVisible(false);

        txtPrenomNom.setText("");
        txtPrenomNom.setVisible(true);
        btnSuivant.setVisible(true);

        step = STEP_FIRSTNAME;
        nomJoueur.setText("Prénom du joueur " + currentPlayer + " :");
    }

    private void handleNext() {

        if (step == STEP_FIRSTNAME) {

            firstName = txtPrenomNom.getText().trim();
            if (firstName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un prénom");
                return;
            }

            txtPrenomNom.setText("");
            nomJoueur.setText("Nom du joueur " + currentPlayer + " :");
            step = STEP_LASTNAME;
        }
        else if (step == STEP_LASTNAME) {

            lastName = txtPrenomNom.getText().trim();
            if (lastName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un nom");
                return;
            }

            txtPrenomNom.setVisible(false);
            btnSuivant.setVisible(false);

            if (isVirtualPlayer) {
                showStrategyButtons();
                step = STEP_STRATEGY;
            } else {
                game.addHumanPlayer(firstName, lastName);
                showPlayerCreatedMessage();
                delayBeforeNextPlayer();
            }
        }
    }

    private void showStrategyButtons() {
        nomJoueur.setText("Choisissez la stratégie du joueur " + currentPlayer + " :");

        btnRandom.setVisible(true);
        btnDefensive.setVisible(true);
        btnAggressive.setVisible(true);
    }

    private void hideStrategyButtons() {
        btnRandom.setVisible(false);
        btnDefensive.setVisible(false);
        btnAggressive.setVisible(false);
    }

    private void selectStrategy(String strategy) {

        hideStrategyButtons();

        game.addVirtualPlayer(firstName, lastName, strategy);

        showPlayerCreatedMessage();
        delayBeforeNextPlayer();
    }

    private void nextPlayer() {

        currentPlayer++;

        if (currentPlayer > totalPlayers) {
            finishPlayersCreation();
            return;
        }

        btnOui.setVisible(true);
        btnNon.setVisible(true);

        nomJoueur.setText("Le joueur " + currentPlayer + " est-il virtuel ?");
    }

    private void finishPlayersCreation() {
        nomJoueur.setText("Tous les joueurs sont créés !");
        txtPrenomNom.setVisible(false);
        btnSuivant.setVisible(false);
        btnOui.setVisible(false);
        btnNon.setVisible(false);
        
        Timer timer = new Timer(1500, e -> {
            ((Timer) e.getSource()).stop();
            this.main.show(3);
        });
        timer.setRepeats(false); // ne pas répéter
        timer.start();
    }

    private void showPlayerCreatedMessage() {
        nomJoueur.setText(
            "Joueur " + currentPlayer + " créé : " + firstName + " " + lastName
        );
    }

    private void delayBeforeNextPlayer() {
        Timer timer = new Timer(1200, e -> {
            ((Timer) e.getSource()).stop();
            nextPlayer();
        });
        timer.setRepeats(false);
        timer.start();
    }
}

package JEST.controller;

import JEST.model.Game;
import JEST.view.MainWindow;
import JEST.view.PartyStartInterface;

public class GameController {

    private final Game model;
    private final MainWindow main;

    public GameController(Game model, MainWindow main) {
        this.model = model;
        this.main = main;

        //main.addReferenceCardListener(e -> onReferenceCardClicked());
    }

    private void onReferenceCardClicked() {
        System.out.println("Carte de référence cliquée !");

    }
}

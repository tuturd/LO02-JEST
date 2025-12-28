package JEST.controller;

import JEST.model.Game;
import JEST.view.GraphicalInterface;

public class GameController {

    private final Game model;
    private final GraphicalInterface view;

    public GameController(Game model, GraphicalInterface view) {
        this.model = model;
        this.view = view;

        view.addReferenceCardListener(e -> onReferenceCardClicked());
    }

    private void onReferenceCardClicked() {
        System.out.println("Carte de référence cliquée !");

    }
}

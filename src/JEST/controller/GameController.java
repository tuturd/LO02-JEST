package JEST.controller;

import JEST.model.Game;
import JEST.view.PartyStartInterface;

public class GameController {

    private final Game model;
    private final PartyStartInterface view;

    public GameController(Game model, PartyStartInterface view) {
        this.model = model;
        this.view = view;

        view.addReferenceCardListener(e -> onReferenceCardClicked());
    }

    private void onReferenceCardClicked() {
        System.out.println("Carte de référence cliquée !");

    }
}

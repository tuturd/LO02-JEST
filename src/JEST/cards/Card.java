package JEST.cards;
import JEST.cards.trophy.Trophy;

import java.io.Serializable;

public abstract class Card implements Serializable {

    private final Trophy trophy;

    protected Card(Trophy trophy) {
        this.trophy = trophy;
    }

    public abstract int getFaceValue();
    public abstract Suit getSuit();

    public abstract void accept(CardVisitor visitor);

    public Trophy getTrophy() {
        return trophy;
    }

    @Override
    public String toString() {
        return getSuit() + " " + getFaceValue();
    }
}

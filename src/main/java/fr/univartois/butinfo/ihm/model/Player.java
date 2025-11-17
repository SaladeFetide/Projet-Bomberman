package fr.univartois.butinfo.ihm.model;


import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Player extends AbstractCharacter {

    private final ObservableList<AbstractBomb> bombs = FXCollections.observableArrayList();

    public Player( BombermanGameFacade facade) {
        super(3, facade);
        // Remplissage de la liste avec 20 bombes de différents types
        for (int i = 0; i < 10; i++) {
            bombs.add(new Bomb(facade));
        }
        for (int i = 0; i < 5; i++) {
            bombs.add(new LargeBomb(facade));
        }
        for (int i = 0; i < 3; i++) {
            bombs.add(new RowBomb(facade));
        }
        for (int i = 0; i < 2; i++) {
            bombs.add(new ColumnBomb(facade));
        }
    }

    @Override
    public String getName() {
        return "guy";
    }

    public ObservableList<AbstractBomb> getBombs() {
        return bombs;
    }

    public void removeBombAt(int index) {
        if (index >= 0 && index < bombs.size()) {
            bombs.remove(index);
        }
    }

    public IntegerBinding bombCountProperty() {
        return Bindings.size(bombs);
    }
}
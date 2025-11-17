package fr.univartois.butinfo.ihm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class AbstractCharacter {

    private final IntegerProperty row = new SimpleIntegerProperty();
    private final IntegerProperty column = new SimpleIntegerProperty();
    private int health;
    protected BombermanGameFacade facade;
    private final IntegerProperty healthProperty = new SimpleIntegerProperty();

    protected AbstractCharacter(int initialHealth, BombermanGameFacade facade) {
        this.health = initialHealth;
        this.facade = facade;
        this.healthProperty.set(initialHealth);
    }

    public abstract String getName();

    public int getRow() {
        return row.get();
    }

    public void setRow(int row) {
        this.row.set(row);
    }

    public IntegerProperty rowProperty() {
        return row;
    }

    public int getColumn() {
        return column.get();
    }

    public void setColumn(int column) {
        this.column.set(column);
    }

    public IntegerProperty columnProperty() {
        return column;
    }

    public void setPosition(int row, int column) {
        setRow(row);
        setColumn(column);
    }

    public IntegerProperty healthProperty() {
        return healthProperty;
    }


    public void decHealth() {
        health--;
        healthProperty.set(health);
    }

    public void incHealth() {
        health++;
        healthProperty.set(health);
    }

    public int getHealth() {
        return health;
    }

}
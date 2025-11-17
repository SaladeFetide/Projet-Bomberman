package fr.univartois.butinfo.ihm.model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Random;

public class Enemy extends AbstractCharacter {
    private Timeline timeline;
    private final String name;
    private static final Random RANDOM = new Random();

    public Enemy(String name, BombermanGameFacade facade) {
        super(1, facade);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void moveRandomly() {
        int direction = RANDOM.nextInt(4);
        switch (direction) {
            case 0 -> facade.moveUp(this);
            case 1 -> facade.moveDown(this);
            case 2 -> facade.moveLeft(this);
            case 3 -> facade.moveRight(this);
        }
    }

    public void animate() {
        this.timeline = new Timeline(
                new KeyFrame ( Duration.seconds(1), e -> moveRandomly()));
        this.timeline.setCycleCount( Animation.INDEFINITE);
        this.timeline.play();
    }

    @Override
    public void decHealth() {
        super.decHealth();
        if (getHealth() <= 0 && timeline != null) {
            stop();
        }
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }


}
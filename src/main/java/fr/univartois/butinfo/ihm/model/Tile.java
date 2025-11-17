package fr.univartois.butinfo.ihm.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

/**
 * La classe Tile représente une tuile composant la carte du jeu du Bomberman.
 * Une fois créée, une telle tuile devient fixe sur la carte : c'est son
 * contenu qui change au cours du jeu.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class Tile {

    /**
     * La ligne où cette tuile est positionnée sur la carte.
     */
    private final int row;

    /**
     * La colonne où cette tuile est positionnée sur la carte.
     */
    private final int column;

    /**
     * Le contenu de cette tuile, transformé en propriété observable.
     */
    private final ObjectProperty<TileContent> content = new SimpleObjectProperty<>();

    /**
     * Construit une nouvelle instance de Tile.
     *
     * @param row La ligne où la tuile est positionnée sur la map.
     * @param column La colonne où la tuile est positionnée sur la map.
     */
    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Donne la ligne où cette tuile est positionnée sur la map.
     *
     * @return La ligne où cette tuile est positionnée sur la map.
     */
    public int getRow() {
        return row;
    }

    /**
     * Donne la colonne où cette tuile est positionnée sur la map.
     *
     * @return La colonne où cette tuile est positionnée sur la map.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Donne le contenu de cette tuile.
     *
     * @return Le contenu de cette tuile.
     */
    public TileContent getContent() {
        return content.get();
    }

    /**
     * Change le contenu de cette tuile.
     *
     * @param content Le nouveau contenu de cette tuile.
     */
    public void setContent(TileContent content) {
        this.content.set(content);
    }

    /**
     * Donne la propriété du contenu de cette tuile.
     *
     * @return La propriété du contenu de cette tuile.
     */
    public ObjectProperty<TileContent> contentProperty() {
        return content;
    }

    /**
     * Vérifie si cette tuile est vide, c'est-à-dire que son contenu est vide.
     *
     * @return Si cette tuile est vide.
     *
     * @see TileContent#isEmpty()
     */
    public boolean isEmpty() {
        return content.get().isEmpty();
    }

    private final BooleanProperty exploding = new SimpleBooleanProperty (false);

    public BooleanProperty explodingProperty() {
        return exploding;
    }

    public void explode() {
        if (content.get().isDestroyableByExplosion()) {
            exploding.set(true);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(3), e -> {
                        exploding.set(false);
                        setContent(TileContent.LAWN);
                    })
            );
            timeline.setCycleCount(1);
            timeline.play();
        }
    }

}
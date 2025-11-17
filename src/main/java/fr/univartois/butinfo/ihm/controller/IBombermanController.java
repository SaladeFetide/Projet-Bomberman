package fr.univartois.butinfo.ihm.controller;

import fr.univartois.butinfo.ihm.model.GameMap;
import fr.univartois.butinfo.ihm.model.BombermanGameFacade;
import fr.univartois.butinfo.ihm.model.AbstractCharacter;
import javafx.scene.Scene;

public interface IBombermanController {

    void initialize(GameMap map);
    void setFacade(BombermanGameFacade facade);
    void setScene( Scene scene );
    void bindPlayerBombCount( fr.univartois.butinfo.ihm.model.Player player);
    void bindCharacter(AbstractCharacter character);
    void showBomb(fr.univartois.butinfo.ihm.model.AbstractBomb bomb);
    void bindPlayerHealth(fr.univartois.butinfo.ihm.model.Player player);
    void setPlayerBombs(javafx.collections.ObservableList<fr.univartois.butinfo.ihm.model.AbstractBomb> bombs);
}
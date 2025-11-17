/**
 * Ce logiciel est distribué à des fins éducatives.
 *
 * Il est fourni "tel quel", sans garantie d'aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d'adéquation
 * à un usage particulier et d'absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d'auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d'un contrat, d'un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d'autres éléments du logiciel.
 *
 * (c) 2022-2025 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

package fr.univartois.butinfo.ihm;

import fr.univartois.butinfo.ihm.controller.BombermanController;
import fr.univartois.butinfo.ihm.model.BombermanGameFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * La classe HelloApplication illustre le fonctionnement d'une {@link Application} JavaFX.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class BombermanApplication extends Application {

    /**
     * Cette méthode permet d'initialiser l'affichage de la fenêtre de l'application.
     *
     * @param primaryStage La fenêtre (initialement vide) de l'application.
     */

    // Fichier : src/main/java/fr/univartois/butinfo/ihm/BombermanApplication.java
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Bomberman.fxml"));
        Parent root = loader.load();

        BombermanController controller = loader.getController();
        BombermanGameFacade facade = new BombermanGameFacade();

        controller.setFacade(facade);
        facade.setController(controller);

        Scene scene = new Scene(root);

        controller.setScene(scene);
        controller.setStage(primaryStage); // <-- Ajout ici

        facade.initializeGame(11, 13, 30);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Cette méthode exécute l'application JavaFX.
     * Pour le cours d'IHM, la méthode {@code main} d'une application JavaFX sera
     * toujours la même : un simple appel à la méthode {@link #launch(String...)}
     * définie dans la classe {@link Application}.
     *
     * @param args Les arguments de la ligne de commande (dont on ne tient pas compte).
     *
     * @see #launch(String...)
     */
    public static void main(String[] args) {
        launch();
    }

}

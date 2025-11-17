// Fichier : src/main/java/fr/univartois/butinfo/ihm/controller/BombermanController.java
package fr.univartois.butinfo.ihm.controller;

import fr.univartois.butinfo.ihm.model.AbstractCharacter;
import fr.univartois.butinfo.ihm.model.BombermanGameFacade;
import fr.univartois.butinfo.ihm.model.GameMap;
import fr.univartois.butinfo.ihm.model.Tile;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;


public class BombermanController implements IBombermanController {

    @FXML
    private Button restartButton;
    private final Map<String, Image> imageCache = new HashMap<> ();
    @FXML
    private Button inventoryButton;
    @FXML
    private GridPane gameGrid;
    @FXML
    private GridPane inventoryGrid;
    @FXML
    private StackPane rootPane;
    private boolean inventoryVisible = false;
    private GameMap gameMap;
    private BombermanGameFacade facade;
    private Scene scene;
    @FXML
    private Label bombsLeft;
    @FXML
    private Label playerLives;
    private javafx.collections.ObservableList<fr.univartois.butinfo.ihm.model.AbstractBomb> playerBombs;

    @Override
    public void initialize(GameMap map) {
        gameMap = map;
        gameGrid.getChildren().clear();
        int rows = gameMap.getHeight();
        int cols = gameMap.getWidth();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                final int finalRow = row;
                final int finalCol = col;
                Tile tile = gameMap.get(finalRow, finalCol);
                ImageView imageView = new ImageView();
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);

                imageView.imageProperty().bind(
                        Bindings.createObjectBinding(
                                () -> loadImage(tile.getContent().getName()),
                                tile.contentProperty()
                        )
                );

                gameGrid.add(imageView, finalCol, finalRow);


                tile.explodingProperty().addListener((obs, wasExploding, isExploding) -> {
                    if (isExploding) {
                        ImageView explosionView = new ImageView(loadImage("explosion"));
                        explosionView.setFitWidth(50);
                        explosionView.setFitHeight(50);
                        GridPane.setColumnIndex(explosionView, finalCol);
                        GridPane.setRowIndex(explosionView, finalRow);
                        gameGrid.getChildren().add(explosionView);
                        tile.explodingProperty().addListener((o, oldVal, newVal) -> {
                            if (!newVal) {
                                gameGrid.getChildren().remove(explosionView);
                            }
                        });
                    }
                });
            }
        }
        restartButton.setOnAction(e -> handleRestart());
        inventoryButton.setOnAction(e -> handleInventory());
    }

    @Override
    public void setFacade(BombermanGameFacade facade) {
        this.facade = facade;
    }

    private Image loadImage(String name) {
        return imageCache.computeIfAbsent(name, n ->
                new Image(getClass().getResourceAsStream("/images/" + n + ".png"))
        );
    }


    @Override
    public void bindCharacter(AbstractCharacter character) {
        ImageView imageView = new ImageView(loadImage(character.getName()));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        GridPane.setConstraints(imageView, character.getColumn(), character.getRow());
        gameGrid.getChildren().add(imageView);
        character.rowProperty().addListener((obs, oldVal, newVal) ->
                GridPane.setRowIndex(imageView, newVal.intValue())
        );
        character.columnProperty().addListener((obs, oldVal, newVal) ->
                GridPane.setColumnIndex(imageView, newVal.intValue())
        );

        character.healthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() <= 0) {
                gameGrid.getChildren().remove(imageView);
            }
        });
    }

    @Override
    public void setScene ( Scene scene ) {
        this.scene = scene;
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> facade.movePlayerUp();
                case DOWN -> facade.movePlayerDown();
                case LEFT -> facade.movePlayerLeft();
                case RIGHT -> facade.movePlayerRight();
                case SPACE -> facade.dropBombAtPlayerPosition();
                default -> { /* rien */ }
            }
        });
    }

    private void handleRestart() {
        if (facade != null) {
            facade.initializeGame(11, 13, 30);
        }
    }

    private void handleInventory() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/fr/univartois/butinfo/ihm/view/BombInventory.fxml"));
            javafx.scene.Parent inventoryRoot = loader.load();

            BombInventoryController inventoryController = loader.getController();
            inventoryController.setScene(this.scene);
            inventoryController.bindBombs(playerBombs);
            inventoryController.setFacade(facade);
            javafx.scene.Scene inventoryScene = new javafx.scene.Scene(inventoryRoot);
            javafx.stage.Stage stage = (javafx.stage.Stage) scene.getWindow();
            stage.setScene(inventoryScene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setBackgroundImage() {
        rootPane.setStyle("-fx-background-image: url('/images/castle.png'); -fx-background-size: cover;");
    }

    @Override
    public void bindPlayerBombCount(fr.univartois.butinfo.ihm.model.Player player) {
        bombsLeft.textProperty().bind(player.bombCountProperty().asString());
    }



    @Override
    public void showBomb(fr.univartois.butinfo.ihm.model.AbstractBomb bomb) {
        ImageView imageView = new ImageView(loadImage(bomb.getName()));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        GridPane.setColumnIndex(imageView, bomb.column);
        GridPane.setRowIndex(imageView, bomb.row);
        gameGrid.getChildren().add(imageView);
        bomb.explodedProperty().addListener((obs, wasExploded, isExploded) -> {
            if (isExploded) {
                gameGrid.getChildren().remove(imageView);
            }
        });
    }

    @Override
    public void bindPlayerHealth(fr.univartois.butinfo.ihm.model.Player player) {
        playerLives.textProperty().bind(player.healthProperty().asString());
    }

    public void setPlayerBombs(javafx.collections.ObservableList<fr.univartois.butinfo.ihm.model.AbstractBomb> bombs) {
        this.playerBombs = bombs;
    }

    public void setStage( Stage stage) {
    }


}
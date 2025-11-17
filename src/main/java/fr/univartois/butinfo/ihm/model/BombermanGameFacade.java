package fr.univartois.butinfo.ihm.model;

import fr.univartois.butinfo.ihm.controller.IBombermanController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class BombermanGameFacade {

    private static final int ENEMY_COUNT = 2;
    private static final String[] ENEMY_NAMES = { "agent", "agent", "agent", "agent" };

    private GameMap gameMap;
    private IBombermanController controller;
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();

    public void setController(IBombermanController controller) {
        this.controller = controller;
    }

    public void initializeGame(int rows, int cols, int brickCount) {
        this.gameMap = GameMapFactory.createMapWithRandomBrickWalls(rows, cols, brickCount);
        this.player = new Player(this);

        enemies.clear();

        for (int i = 0; i < ENEMY_COUNT; i++) {
            Enemy enemy = new Enemy(ENEMY_NAMES[i], this);
            placeCharacterRandomly(enemy);
            enemies.add(enemy);
            enemy.animate();
        }

        placeCharacterRandomly(player);


        if (controller != null) {
            controller.initialize(gameMap);
            controller.bindCharacter(player);
            for (Enemy enemy : enemies) {
                controller.bindCharacter(enemy);
            }
            controller.bindPlayerBombCount(player);
            controller.bindPlayerHealth(player);
            controller.setPlayerBombs(player.getBombs()); // <-- Ajoutez cet appel ici
        }
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void placeCharacterRandomly(AbstractCharacter character) {
        List<Tile> emptyTiles = gameMap.getEmptyTiles();
        if (emptyTiles.isEmpty()) {
            throw new IllegalStateException("Aucune tuile vide disponible pour placer le personnage.");
        }
        Tile chosenTile = emptyTiles.get(new Random().nextInt(emptyTiles.size()));
        character.setPosition(chosenTile.getRow(), chosenTile.getColumn());
    }

    public void moveUp(AbstractCharacter character) {
        int newRow = character.getRow() - 1;
        int col = character.getColumn();
        if (canMoveTo(newRow, col)) {
                character.setPosition(newRow, col);
        }
    }

    public void moveDown(AbstractCharacter character) {
        int newRow = character.getRow() + 1;
        int col = character.getColumn();
        if (canMoveTo(newRow, col)) {
            character.setPosition(newRow, col);
        }
    }

    public void moveLeft(AbstractCharacter character) {
        int row = character.getRow();
        int newCol = character.getColumn() - 1;
        if (canMoveTo(row, newCol)) {
            character.setPosition(row, newCol);
        }
    }

    public void moveRight(AbstractCharacter character) {
        int row = character.getRow();
        int newCol = character.getColumn() + 1;
        if (canMoveTo(row, newCol)) {
            character.setPosition(row, newCol);
        }
    }

    private boolean canMoveTo(int row, int col) {
        if (!gameMap.isOnMap(row, col)) {
            return false;
        }
        Tile tile = gameMap.get(row, col);
        return tile.getContent() == TileContent.LAWN;
    }

    public void dropBombAtPlayerPosition(int index) {
        if (!player.getBombs().isEmpty() && index >= 0 && index < player.getBombs().size()) {
            AbstractBomb bomb = player.getBombs().get(index);
            bomb.setPosition(player.getRow(), player.getColumn());
            player.removeBombAt(index);
            if (controller != null) {
                controller.showBomb(bomb);
            }
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(bomb.getDelay()), e -> bomb.explode())
            );
            timeline.setCycleCount(1);
            timeline.play();
        }
    }

    public void dropBombAtPlayerPosition() {
        dropBombAtPlayerPosition(0);
    }


    public void explode(int row, int column) {
        if (gameMap != null && gameMap.isOnMap(row, column)) {
            Tile tile = gameMap.get(row, column);
            tile.explode();
            if (player.getRow() == row && player.getColumn() == column) {
                player.decHealth();
                if (player.getHealth() <= 0) {
                    // Interrompre la partie (ex : désactiver les contrôles)
                    if (controller != null) {
                        // À adapter selon votre logique d'arrêt de partie
                        // Par exemple : controller.showGameOver();
                    }
                }
            }
            for (Enemy enemy : enemies) {
                if (enemy.getRow() == row && enemy.getColumn() == column) {
                    enemy.decHealth();
                }
            }
        }
    }

    public void movePlayerUp() {
        moveUp(player);
    }
    public void movePlayerDown() {
        moveDown(player);
    }
    public void movePlayerLeft() {
        moveLeft(player);
    }
    public void movePlayerRight() {
        moveRight(player);
    }

}
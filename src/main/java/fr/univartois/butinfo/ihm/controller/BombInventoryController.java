package fr.univartois.butinfo.ihm.controller;

import fr.univartois.butinfo.ihm.model.AbstractBomb;
import fr.univartois.butinfo.ihm.model.BombermanGameFacade;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class BombInventoryController {

    @FXML
    private ListView<AbstractBomb> bombListView;
    @FXML
    private Label bombName;
    @FXML
    private Label bombDescription;
    @FXML
    private ImageView bombImage;
    @FXML
    private Button validateButton;
    @FXML
    private Button cancelButton;
    private AbstractBomb selectedBomb;
    private Scene mainScene;


    private BombermanGameFacade facade;

    public void setBombs(ObservableList<AbstractBomb> bombs) {
        bombListView.setItems(bombs);
    }

    public void setFacade(BombermanGameFacade facade) {
        this.facade = facade;
    }

    @FXML
    public void initialize() {
        bombListView.getSelectionModel().selectedItemProperty().addListener((obs, oldBomb, newBomb) -> {
            selectedBomb = newBomb;
            if (newBomb != null) {
                bombName.setText(newBomb.getName());
                bombDescription.setText(newBomb.getDescription());
                bombImage.setImage(new Image(getClass().getResourceAsStream("/images/" + newBomb.getName() + ".png")));
            } else {
                bombName.setText("");
                bombDescription.setText("");
                bombImage.setImage(null);
            }
        });

        validateButton.setOnAction(e -> handleValidate());
        cancelButton.setOnAction(e -> handleCancel());
    }

    private void handleValidate() {
        int index = bombListView.getSelectionModel().getSelectedIndex();
        if (index >= 0 && facade != null) {
            facade.dropBombAtPlayerPosition(index);
            Stage stage = (Stage) bombListView.getScene().getWindow();
            if (mainScene != null) {
                stage.setScene(mainScene);
            }
        }
    }


    private void handleCancel() {
        Stage stage = (Stage) bombListView.getScene().getWindow();
        if (mainScene != null) {
            stage.setScene(mainScene);
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) bombListView.getScene().getWindow();
        stage.close();
    }

    public void setStage(Stage stage) {
    }

    public void setScene(Scene scene) {
        this.mainScene = scene;
    }

    public void bindBombs(ObservableList<AbstractBomb> bombs) {
        bombListView.setItems(bombs);
    }
}
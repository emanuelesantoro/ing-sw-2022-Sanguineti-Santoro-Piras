package it.polimi.ingsw.Client.View.Gui.SceneController;

import it.polimi.ingsw.Client.PhaseAndComand.Commands.GameCommand;
import it.polimi.ingsw.Client.View.Gui.ViewGUI;
import it.polimi.ingsw.Client.model.CharacterCardClient;
import it.polimi.ingsw.Client.model.GameComponentClient;
import it.polimi.ingsw.Client.model.IslandClient;
import it.polimi.ingsw.Util.Color;
import it.polimi.ingsw.Util.HouseColor;
import it.polimi.ingsw.Util.Wizard;
import it.polimi.ingsw.Util.AssistantCard;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class MenuController implements SceneController {
    public Button createButton;
    public Button joinIdButton;
    public Button joinMatchTypeButton;
    public ToggleGroup player;
    public ToggleGroup gameType;
    @FXML
    AnchorPane root;
    ViewGUI viewGUI;
    @FXML
    Group mainGroup;

    @FXML
    Button connectButton;
    @FXML
    Button chatButton;
    @FXML
    Button nickNameButton;


    private ListView<String> listView;
    ObservableList<String> chat;

    private void initialize() {
        connectButton.setOnAction(GameCommand.CONNECT_SERVER.getGUIHandler(viewGUI));
        hideEverything();
        chatButton.setOnAction(actionEvent -> switchChat());
        chat = FXCollections.observableArrayList();
        //create the box for chat
        listView = new ListView<>(chat);
        listView.setVisible(false);
        root.getChildren().add(listView);
        nickNameButton.setOnAction(GameCommand.SET_NICKNAME.getGUIHandler(viewGUI));
        createButton.setOnAction(GameCommand.CREATE_MATCH.getGUIHandler(viewGUI));
        joinIdButton.setOnAction(GameCommand.JOIN_MATCH_BY_ID.getGUIHandler(viewGUI));
        joinMatchTypeButton.setOnAction(GameCommand.JOIN_MATCH_BY_TYPE.getGUIHandler(viewGUI));
    }

    @Override
    public void hideEverything() {
        for (Node child : mainGroup.getChildren()) {
            child.setVisible(false);
        }
    }

    @Override
    public Node getElementById(String id) {

        //toggle group are not a node but i'll make them return the selected radio button
        if (id.equals("#gameType")) {
            return (RadioButton) gameType.getSelectedToggle();
        }
        if (id.equals("#player")) {
            return (RadioButton) player.getSelectedToggle();
        }
        //return the chat
        if (id.equals("#chat")) {
            return listView;
        }
        return mainGroup.lookup(id);
    }

    public void switchChat() {
        listView.setVisible(!listView.isVisible());
    }

    @Override
    public void updateMessage(String message) {
        Platform.runLater(() -> chat.add(message));
    }

    @Override
    public void setViewGUI(ViewGUI viewGUI) {
        this.viewGUI = viewGUI;
        this.initialize();
    }

    @Override
    public void updateMotherNature(Byte motherNaturePosition) {
    }

    @Override
    public void update(GameComponentClient gameComponent) {

    }

    @Override
    public void update(IslandClient island) {

    }

    @Override
    public void update(ArrayList<IslandClient> islands) {

    }

    @Override
    public void update(HouseColor houseColor, Byte towerLefts) {

    }

    @Override
    public void update(Color color, Wizard wizard) {

    }

    @Override
    public void update(String currentPlayer, boolean isMyTurn) {

    }

    @Override
    public void updateMembers(int membersLeftToStart, String nickPlayerJoined) {

    }

    @Override
    public void updateCardPlayed(AssistantCard playedCard) {

    }

    @Override
    public void updateIgnoredColor(Color color) {
    }

    @Override
    public void updateExtraSteps(boolean extraSteps) {
    }

    @Override
    public void updateCharacter(List<CharacterCardClient> characters) {

    }

    @Override
    public void updateCoins(Byte coins) {

    }

    @Override
    public void setWinners(List<HouseColor> winners) {

    }
}

package it.polimi.ingsw.client.phaseAndComand.Phases;

import it.polimi.ingsw.client.model.CharacterCardClient;
import it.polimi.ingsw.client.phaseAndComand.Commands.GameCommand;
import it.polimi.ingsw.client.view.gui.GuiFX;
import it.polimi.ingsw.client.view.gui.SceneController.SceneController;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * PlayCharacterCardPhase class represents the game phase in which the client can play a character card and add inputs to it.
 */
public class PlayCharacterCardPhase extends ClientPhase {

    /**
     * Constructor PlayCharacterCardPhase creates a new instance of PlayCharacterCardPhase.
     */
    public PlayCharacterCardPhase() {
        super();
    }

    /**
     * Method playPhase disables everything except the card buttons and the GUI nodes necessary for the card effect, adding the respective command on them.
     *
     * @param viewGUI of type {@link ViewGUI} - instance of the client's view (GUI).
     */
    @Override
    public void playPhase(ViewGUI viewGUI) {
        SceneController sceneController = GuiFX.getActiveSceneController();
        sceneController.disableEverything();
        CharacterCardClient current = viewGUI.getCurrentCharacterCard();

        current.setHandler(viewGUI);

        int indexOfCurrent = viewGUI.getModel().getCharacters().indexOf(current);
        //children 2 is the button to choose, 3 to undo, 4 to play
        AnchorPane singleChar = (AnchorPane) ((HBox) sceneController.getElementById("#characters")).getChildren().get(indexOfCurrent);
        sceneController.selectNode(singleChar);
        Button play = (Button) singleChar.getChildren().get(4);
        if (current.canPlay()) {
            sceneController.enableNode(play,true);
            play.setOnMouseClicked(GameCommand.PLAY_CHARACTER.getGUIHandler(viewGUI));
        }

        Node undo = singleChar.getChildren().get(3);
        sceneController.enableNode(undo, true);
        undo.setOnMouseClicked(GameCommand.DESELECT_CHARACTER.getGUIHandler(viewGUI));

    }

    /**
     * Method toString returns the name of the phase.
     *
     * @return {@code String} - "Play Character Card Phase".
     */
    @Override
    public String toString() {
        return "Play Character Card Phase";
    }
}

package it.polimi.ingsw.client.phaseAndComand.Phases;

import it.polimi.ingsw.client.model.GameClientView;
import it.polimi.ingsw.client.model.IslandClient;
import it.polimi.ingsw.client.phaseAndComand.Commands.GameCommand;
import it.polimi.ingsw.client.view.gui.GuiFX;
import it.polimi.ingsw.client.view.gui.SceneController.SceneController;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.scene.Node;

import java.util.List;
import java.util.Set;

/**
 * MoveMotherNaturePhase class represents the game phase in which the client can move mother nature.
 */
public class MoveMotherNaturePhase extends ClientPhase {

    /**
     * Constructor MoveMotherNaturePhase creates a new instance of MoveMotherNaturePhase.
     */
    public MoveMotherNaturePhase() {
        super();
    }

    /**
     * Method playPhase disables everything except the islands where mother nature can be placed, according
     * to the allowed number of moves, and adds the respective game command on the mouse clicked event.
     *
     * @param viewGUI of type {@link ViewGUI} - instance of the client's view (GUI).
     */
    @SuppressWarnings("unchecked")
    public void playPhase(ViewGUI viewGUI) {
        SceneController sceneController = GuiFX.getActiveSceneController();
        sceneController.disableEverything();
        GameClientView model = viewGUI.getModel();
        List<IslandClient> islands = model.getIslands();

        int max_moves = model.getCurrentPlayer().getPlayedCard().moves();
        if (model.isExtraSteps()) {
            max_moves += model.getMatchConstants().extraStep();
        }
        if (max_moves > islands.size()) max_moves = islands.size();
        byte motherNaturePosition = model.getMotherNaturePosition();
        int islandId;
        Node island;
        Set<Integer> containedIslands;
        // the island distance in steps from the relative one where mother nature is positioned
        for (int moves = 1; moves <= max_moves; moves++) {
            islandId = islands.get((motherNaturePosition + moves) % islands.size()).getId();
            island = sceneController.getElementById("#" + islandId);
            containedIslands = (Set<Integer>) island.getProperties().get("containedIslands");
            for (Integer i : containedIslands) {
                island = sceneController.getElementById("#" + i);
                island.getProperties().put("moves", moves);
                // make clickable
                sceneController.enableNode(island);
                island.setOnMouseClicked(GameCommand.MOVE_MOTHER_NATURE.getGUIHandler(viewGUI));
            }
        }
        if (getGameCommands().contains(GameCommand.CHOOSE_CHARACTER)) {
            viewGUI.enableChooseCharacter(GameCommand.CHOOSE_CHARACTER.getGUIHandler(viewGUI));
        }
    }

    /**
     * Method toString returns the name of the phase.
     *
     * @return {@code String} - "Move Mother Nature phase".
     */
    @Override
    public String toString() {
        return "Move Mother Nature phase";
    }
}
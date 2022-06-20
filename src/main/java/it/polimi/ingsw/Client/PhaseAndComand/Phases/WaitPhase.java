package it.polimi.ingsw.Client.PhaseAndComand.Phases;

import it.polimi.ingsw.Client.View.Gui.GuiFX;
import it.polimi.ingsw.Client.View.Gui.SceneController.SceneController;
import it.polimi.ingsw.Client.View.Gui.ViewGUI;

/**
 * WaitPhase class represents the game phase in which the client is when waiting for other players to join the
 * match or play their turn.
 */
public class WaitPhase extends ClientPhase {

    /**
     * Constructor WaitPhase creates a new instance of WaitPhase.
     */
    public WaitPhase() {
        super();
    }

    /**
     * Method playPhase disables everything except the chat.
     *
     * @param viewGUI of type {@link ViewGUI} - instance of the client's view (GUI).
     */
    @Override
    public void playPhase(ViewGUI viewGUI) {
        SceneController sceneController = GuiFX.getActiveSceneController();
        sceneController.disableEverything();
        sceneController.getElementById("#chat").setVisible(true);
    }

    /**
     * Method toString returns the name of the phase.
     *
     * @return {@code String} - "Wait Phase".
     */
    @Override
    public String toString() {
        return "Wait Phase";
    }
}

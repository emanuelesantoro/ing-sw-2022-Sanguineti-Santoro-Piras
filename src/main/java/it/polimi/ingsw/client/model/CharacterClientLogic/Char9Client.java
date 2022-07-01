package it.polimi.ingsw.client.model.CharacterClientLogic;

import it.polimi.ingsw.client.view.cli.ViewForCharacterCli;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import it.polimi.ingsw.exceptions.clientExceptions.SkipCommandException;
import it.polimi.ingsw.server.model.characterServerLogic.Char9;
import it.polimi.ingsw.utils.Color;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Char9Client class represents the character card on the client side and corresponds to the server class {@link Char9}.
 */
public class Char9Client implements CharacterClientLogicInterface {
    private final List<Integer> inputs;

    /**
     * Constructor Char9Client creates a new instance of Char9Client.
     */
    public Char9Client() {

        inputs = new ArrayList<>();
    }

    @Override
    public String getDescription() {
        return "You may exchange up to 2 Students between your Dining and your Entrance Room.";
    }

    @Override
    public void setNextInput(ViewForCharacterCli view) throws SkipCommandException {
        System.out.println("Select the color of the student from Lunch Hall");
        inputs.add(view.getColorInput(false).ordinal());
        System.out.println("Select the color of the student from your Entrance Hall");
        inputs.add(view.getColorInput(false).ordinal());
    }

    @Override
    public void setHandler(ViewGUI viewGUI) {
        if (inputs.size() % 2 == 0 && !isFull()) {
            viewGUI.enableStudentsLunchHall(setInput(viewGUI));
        } else if (inputs.size() % 2 == 1 && !isFull()) {
            viewGUI.enableEntrance(setInput(viewGUI));
        }
    }

    /**
     * Method setInputs returns the event handler for a mouse event to add to a specific node of the GUI in order to obtain the card required inputs.
     *
     * @param viewGUI of type {@link ViewGUI} - client's GUI view from which the inputs are obtained.
     * @return {@code EventHandler}<{{@code MouseEvent}> - function that will be executed when the node that adds the
     * event handler is clicked.
     */
    private EventHandler<MouseEvent> setInput(ViewGUI viewGUI) {
        return mouseEvent -> {
            Node clicked = (Node) mouseEvent.getSource();
            inputs.add(((Color) clicked.getProperties().get("color")).ordinal());
            System.out.println(inputs);
            viewGUI.repeatPhase();
        };
    }

    @Override
    public boolean canPlay() {
        return (inputs.size() == 2 || inputs.size() == 4);
    }

    @Override
    public boolean isFull() {
        return inputs.size() == 4;
    }

    @Override
    public void resetInput() {
        inputs.clear();
    }

    @Override
    public List<Integer> getInputs() {
        return inputs;
    }

    @Override
    public String toString() {
        return "Minstrel";
    }

}


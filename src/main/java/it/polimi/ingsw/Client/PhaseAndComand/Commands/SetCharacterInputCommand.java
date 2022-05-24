package it.polimi.ingsw.Client.PhaseAndComand.Commands;

import it.polimi.ingsw.Client.View.AbstractView;
import it.polimi.ingsw.Client.View.Cli.ViewCli;
import it.polimi.ingsw.Client.model.CharacterCardClient;
import it.polimi.ingsw.Enum.GamePhase;
import it.polimi.ingsw.exceptions.PhaseChangedException;

import java.awt.event.ActionEvent;

public class SetCharacterInputCommand extends GameCommand {
    public SetCharacterInputCommand(AbstractView view) {
        super(view);
    }

    @Override
    public void playCLICommand() {
        ViewCli viewCli = (ViewCli) getView();
        CharacterCardClient current = viewCli.getCurrentCharacterCard();
        if (current.canPlay())
            System.out.println("Card can already be played");
        if (current.isFull())
            viewCli.addMessage("Can't add more inputs to this character");
        else {
            boolean phaseChanged;
            do {
                phaseChanged = false;
                try {
                    current.setNextInput(viewCli);
                } catch (PhaseChangedException e) {
                    phaseChanged = true;
                }
            } while (phaseChanged);
        }
        viewCli.repeatPhase(false);
    }

    @Override
    public String toString() {
        return "Set the input for the character";
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

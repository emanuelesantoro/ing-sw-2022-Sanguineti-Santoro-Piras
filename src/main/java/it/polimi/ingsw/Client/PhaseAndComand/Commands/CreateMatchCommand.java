package it.polimi.ingsw.Client.PhaseAndComand.Commands;

import it.polimi.ingsw.Client.View.AbstractView;
import it.polimi.ingsw.Client.View.Cli.ViewCli;
import it.polimi.ingsw.exceptions.PhaseChangedException;
import it.polimi.ingsw.network.toServerMessage.CreateMatch;

import java.awt.event.ActionEvent;

public class CreateMatchCommand extends GameCommand {
    public CreateMatchCommand(AbstractView view) {
        super(view);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void playCLICommand() {
        ViewCli viewCli = (ViewCli) getView();
        boolean phaseChanged = false;
        do {
            try {
                viewCli.sendToServer(new CreateMatch(viewCli.getMatchTypeInput(false)));
            } catch (PhaseChangedException e) {
                phaseChanged = true;
            }
        } while (phaseChanged);

    }

    @Override
    public String toString() {
        return "Create match";
    }

}

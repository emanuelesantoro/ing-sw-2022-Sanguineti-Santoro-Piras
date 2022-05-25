package it.polimi.ingsw.network.toServerMessage;

import it.polimi.ingsw.Enum.Color;
import it.polimi.ingsw.Server.controller.ClientHandler;
import it.polimi.ingsw.Server.controller.Controller;
import it.polimi.ingsw.exceptions.serverExceptions.GameException;
import it.polimi.ingsw.exceptions.serverExceptions.NotAllowedException;

public class MoveStudent implements ToServerMessage {
    Color color;
    int idGameComponent;

    public MoveStudent(Color color, int idGameComponent) {
        this.color = color;
        this.idGameComponent = idGameComponent;
    }

    @Override
    public void execute(ClientHandler clientHandler) throws GameException {
        Controller c = clientHandler.getController();
        if(c.isGameFinished()){
            throw new NotAllowedException("Game is already finished");
        }
        if (c.isMyTurn(clientHandler))
            c.move(color, idGameComponent);
        else throw new NotAllowedException("It's not your turn");
    }
}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.exceptions.NotAllowedException;
import it.polimi.ingsw.model.*;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;

public class Controller {
    private final MatchType matchType;
    private final ArrayList<Player> playersList;
    private final ArrayList<PlayerHandler> playerHandlers;
    private final ArrayList<Team> teams;
    // This array is also used to represent the order of round
    private final ArrayList<Byte> playerOrder;
    private Game game;
    // current phase is true in the planification phase, false during the action phase
    private boolean isPlanificationPhase;
    /* it's a number that goes from 1 to 3, it represents the subsection of the action phase
    1-move 3-4 students; 2-move mother nature(calculate influence and merge); 3-drawStudent from cloud*/
    private byte actionPhase;
    //it's the index of playerOrder: it goes from 0 to players.size() and when it's 3 it changes phase
    private byte roundIndex, currentPlayerIndex;
    private boolean lastRound;

    public Controller(MatchType matchType) {
        this.matchType = matchType;
        this.playersList = new ArrayList<>(matchType.nPlayers());
        this.playerHandlers = new ArrayList<>(matchType.nPlayers());
        this.playerOrder = new ArrayList<>(matchType.nPlayers());
        for (byte i = 0; i < matchType.nPlayers(); i++)
            playerOrder.add(i);
        byte nTeams = (byte) ((matchType.nPlayers() % 2) + 2); // size is 2 or 3
        byte maxTowers = (byte) (12 - nTeams * 2); // 2 teams -> 8 towers, 3 teams -> 6 towers
        this.teams = new ArrayList<>(nTeams);
        for (byte i = 0; i < nTeams; i++) {
            teams.add(new Team(HouseColor.values()[i], (byte) (matchType.nPlayers() / nTeams), maxTowers));
        }
        this.isPlanificationPhase = true;
        this.actionPhase = 0;
        this.roundIndex = 0;
        this.currentPlayerIndex = 0;
        this.lastRound = false;
    }

    public synchronized void move(String colorString, String idGameComponentString) throws GameException {
        Color color = Color.valueOf(colorString);
        int idGameComponent = Integer.parseInt(idGameComponentString);
        if (isPlanificationPhase) {
            throw new NotAllowedException("Not in action phase");
        }

        if (actionPhase == 1) {
            if (idGameComponent <= 0) throw new NotAllowedException("Can't move to the selected GameComponent");
            game.move(color, 0, idGameComponent);
        } else if (actionPhase == 3) { // move students from cloud, destination is player entrance hall
            game.moveFromCloud(idGameComponent);
        } else {
            throw new NotAllowedException("Wrong Phase");
        }

        nextActionPhase();
    }

    //the value here need to go from 1 to 10
    public synchronized void playCard(String valueString) throws GameException {
        byte value = Byte.parseByte(valueString);
        if (!isPlanificationPhase) {
            throw new NotAllowedException("Not in planification phase");

        }
        ArrayList<Byte> playedCards = new ArrayList<>();
        //loop where I put in playedCard the previous card played by other Player.If it's current Player
        //it breaks the loop 'cause there aren't other previous player
        for (int i = 0; i < roundIndex; i++) {
            playedCards.add(playersList.get((playerOrder.get(0) + i) % playersList.size()).getPlayedCard());
        }

        if (playersList.get(currentPlayerIndex).canPlayCard(playedCards, value)) {
            try {
                game.playCard(value);
            } catch (EndGameException e) {
                handleError(e);
            }
            nextPlayer();
        } else {
            throw new NotAllowedException("Cannot play this card");
        }
    }

    public synchronized void moveMotherNature(String moves) throws GameException {
        int i = Integer.parseInt(moves);
        if (isPlanificationPhase || actionPhase != 2) {
            throw new NotAllowedException("Not allowed in this phase");
        }
        try {
            game.moveMotherNature(i);
        } catch (EndGameException e) {
            handleError(e);
        }
        nextActionPhase();
    }

    public synchronized boolean addPlayer(PlayerHandler handler) throws GameException {
        if (playersList.size() == matchType.nPlayers()) {
            throw new NotAllowedException("Match is full");

        }
        int teamIndex = playersList.size() % teams.size(); // circular team selection
        int entranceHallSize = (teams.size() % 2 == 0) ? 7 : 9;
        Player newPlayer;
        newPlayer = new Player(handler.getNickName(), teams.get(teamIndex), Wizard.values()[playersList.size()], entranceHallSize);

        playersList.add(newPlayer);
        playerHandlers.add(handler);
        if (playersList.size() == matchType.nPlayers()) {
            startGame();
            return true;
        }
        return false;
    }

    public void leaveLobby() {

    }

    public void sendMessage(String me, String message) {
        for (PlayerHandler h : playerHandlers)
            if (!h.getNickName().equals(me)) h.sendString("message/" + me + ": " + message);
    }

    public synchronized void setCharacterInput(String inputString) throws GameException {
        int input = Integer.parseInt(inputString);
        if (isPlanificationPhase) {
            throw new NotAllowedException("Not in action phase");
        }
        game.setCharacterInput(input);
    }

    public synchronized void chooseCharacter(String characterString) throws GameException {
        byte character = Byte.parseByte(characterString);
        if (isPlanificationPhase) {
            throw new NotAllowedException("Not in action phase");
        }
        game.chooseCharacter(character);

    }

    public synchronized void playCharacter() throws GameException {
        if (isPlanificationPhase) {
            throw new NotAllowedException("not in planification phase");
        }
        try {
            game.playCharacter();
        } catch (EndGameException e) {
            handleError(e);
        }
    }

    private void sendGameState() {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(game);
            so.flush();
            notifyClients(bo.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notifyClients(String message) {
        for (PlayerHandler h : playerHandlers) {
            h.sendString(message);
        }
    }

    private void startGame() {
        if (matchType.isExpert())
            game = new ExpertGame(matchType.nPlayers(), teams);
        else
            game = new NormalGame(matchType.nPlayers(), teams);
        game.setCurrentPlayer(currentPlayerIndex);
        sendGameState();
    }

    private void nextPlayer() {
        roundIndex++;
        if (roundIndex >= playersList.size()) nextPhase();
        if (!isPlanificationPhase)
            actionPhase = 1;
        currentPlayerIndex = getCurrentPlayerIndex();
        game.setCurrentPlayer(currentPlayerIndex);
    }

    private byte getCurrentPlayerIndex() {
        if (isPlanificationPhase) {
            // is planification phase, clockwise order
            return (byte) ((playerOrder.get(0) + roundIndex) % playersList.size());
        } else {
            // action phase, follow the playerOrder
            return playerOrder.get(roundIndex);
        }
    }

    private void nextPhase() {
        this.isPlanificationPhase = !isPlanificationPhase;
        //sort the array if the nextPhase is the action phase
        if (!isPlanificationPhase) {
            playerOrder.sort((b1, b2) -> {
                int ret = playersList.get(b1).getPlayedCard() - playersList.get(b2).getPlayedCard();
                // players have used the same card, compare in function of who played first
                // [3,1,2,4] : player 3 is the first playing in planification phase then clockwise order (3,4,1,2), doing bx -= 3 (mod 4) obtains -> [0,2,3,1] and that's the planification phase order from 0 to 3
                if (ret == 0)
                    ret = Math.floorMod(b1 - playerOrder.get(0), matchType.nPlayers()) - Math.floorMod(b2 - playerOrder.get(0), matchType.nPlayers());
                return ret;
            });
            playerOrder.sort(Comparator.comparingInt((Byte b) -> playersList.get(b).getPlayedCard()));
        } else {
            if (lastRound) {
                // should go in new preparation phase but is last round
                endGame();
            } else { // new planification phase
                try {
                    game.refillClouds();
                } catch (EndGameException e) {
                    handleError(e);
                }
            }
        }
        roundIndex = 0;
    }

    private void nextActionPhase() {
        // pass to new player
        if (actionPhase >= 3) nextPlayer();
        else actionPhase++;
    }

    private void endGame() {
        ArrayList<Team> winners = game.calculateWinner();
        StringBuilder message = new StringBuilder("end");
        for (Team t : winners)
            message.append("/").append(t.toString());
        notifyClients(String.valueOf(message));
        if (winners.size() == 3) System.out.println("Paolino tvb <3");
    }


    private void handleError(EndGameException e) {
        if (e.isEndInstantly()) endGame();
        else lastRound = true;
    }
}

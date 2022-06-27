package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.View.GameClientListener;
import it.polimi.ingsw.Client.model.GameComponentClient;
import it.polimi.ingsw.Client.model.IslandClient;
import it.polimi.ingsw.Util.*;

import java.util.ArrayList;
import java.util.List;

public abstract class GameClientListened {
    private List<GameClientListener> listeners = new ArrayList<>();

    public void notifyMotherNature(Byte motherNaturePosition) {
        for (GameClientListener listener : listeners) {
            listener.updateMotherNature(motherNaturePosition);
        }
    }

    public void notifyGameComponent(GameComponentClient gameComponent) {
        for (GameClientListener listener : listeners) {
            listener.updateGameComponent(gameComponent);
        }
    }

    public void notifyGameComponent(IslandClient island) {
        for (GameClientListener listener : listeners) {
            listener.updateGameComponent(island);
        }
    }

    public void notifyDeletedIsland(IslandClient deletedIsland, IslandClient islandWInner) {
        for (GameClientListener listener : listeners) {
            listener.updateDeletedIsland(deletedIsland, islandWInner);
        }
    }

    public void notifyTowerLeft(HouseColor houseColor, Byte towerLefts) {
        for (GameClientListener listener : listeners) {
            listener.updateTowerLeft(houseColor, towerLefts);
        }
    }

    public void notifyProfessor(Color color, Wizard wizard) {
        for (GameClientListener listener : listeners) {
            listener.updateProfessor(color, wizard);
        }
    }

    public void notifyCardPlayed(AssistantCard playedCard) {
        for (GameClientListener listener : listeners) {
            listener.updateCardPlayed(playedCard);
        }
    }

    public void notifyIgnoredColor(Color color) {
        for (GameClientListener listener : listeners) {
            listener.updateIgnoredColor(color);
        }
    }

    public void notifyCharacter(int charId) {
        for (GameClientListener listener : listeners) {
            listener.updateCharacter(charId);
        }
    }

    public void notifyMembers(int membersLeftToStart, String nickPlayerJoined) {
        for (GameClientListener listener : listeners) {
            listener.updateMembers(membersLeftToStart, nickPlayerJoined);
        }
    }

    public void notifyWinners(List<HouseColor> winners) {
        for (GameClientListener listener : listeners) {
            listener.setWinners(winners);
        }
    }

    public void addListener(GameClientListener listener) {
        if (listeners == null) listeners = new ArrayList<>();
        listeners.add(listener);
    }

    public void notifyMessage(String message) {
        for (GameClientListener listener : listeners) {
            listener.updateMessage(message);
        }
    }

    public void removeListeners() {
        listeners = new ArrayList<>();
    }

    public void removeListener(GameClientListener listener) {
        listeners.remove(listener);
    }

    public void notifyCoins(Byte coins) {
        for (GameClientListener listener : listeners) {
            listener.updateCoins(coins);
        }
    }

    public void notifyCoins(Wizard wizard, Byte coins) {
        for (GameClientListener listener : listeners) {
            listener.updateCoins(wizard, coins);
        }
    }

    public void notifyProhibitions(Byte prohibitions) {
        for (GameClientListener listener : listeners) {
            listener.updateProhibitions(prohibitions);
        }
    }

    public void notifyError(String error) {
        for (GameClientListener listener : listeners) {
            listener.updateError(error);
        }
    }
}

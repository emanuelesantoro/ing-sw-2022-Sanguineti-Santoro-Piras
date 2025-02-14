package it.polimi.ingsw.server.model;

import it.polimi.ingsw.exceptions.serverExceptions.NotEnoughCoinsException;

/**
 * CoinListener interface is used to update the coin logic whenever a player receives or spends coins during the game.
 * It is implemented by ExpertGame.
 */
public interface CoinListener {
    /**
     * Method notifyCoins is used to add coins to the current player, removing or adding them from the game.
     *
     * @param coins of type {@code int} - number of coins to add to the current player.
     * @throws NotEnoughCoinsException if there are no more coins left in the game.
     */
    void notifyCoins(int coins) throws NotEnoughCoinsException;
}

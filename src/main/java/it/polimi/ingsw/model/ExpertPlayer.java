package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NotEnoughCoinsException;

public class ExpertPlayer extends Player {
    private byte coins = 0;

    public ExpertPlayer(Wizard wizard, byte tower,String nickName){
        super(wizard, tower, nickName);
        this.coins=20;
    }

    public void addCoins(byte coins) {
        this.coins += coins;
    }

    public void removeCoins(byte coins) throws NotEnoughCoinsException {
        if (coins > this.coins) throw new NotEnoughCoinsException();
        this.coins -= coins;
    }

    public byte getCoins() {
        return coins;
    }
}

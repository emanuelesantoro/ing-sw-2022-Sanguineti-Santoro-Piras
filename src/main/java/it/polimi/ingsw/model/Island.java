package it.polimi.ingsw.model;

public class Island extends GameComponent {
    private Team team;
    //prohibition is the representation of the NO Entry Tiles which  avoids the calcutaion of the influence on a island
    private boolean prohibition;
    //it's the number of the island merged in this island
    private byte number;

    public Island() {
        super();
        team = null;
        prohibition = false;
        number = 1;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    //merge function is used to send all the students from another island to this: the try catch block is due to the fact that an island knows
    //exactly how many students are present, and therefore it will never throw the exception

    public void merge(Island island) {
        island.moveAll(this);
        this.number += island.getNumber();

    }

    public byte getNumber() {
        return number;
    }

    public boolean getProhibition() {
        return prohibition;
    }

    public void setProhibition(boolean value) {
        this.prohibition = value;
    }
}

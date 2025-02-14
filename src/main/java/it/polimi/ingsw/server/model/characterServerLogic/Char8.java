package it.polimi.ingsw.server.model.characterServerLogic;

import it.polimi.ingsw.exceptions.serverExceptions.NotAllowedException;
import it.polimi.ingsw.server.model.GameInterfaceForCharacter;
import it.polimi.ingsw.utils.Color;

/**
 * Char8 class represents the <b>"Mushroom man"</b> character card. <br>
 * <b>Effect</b>: Choose a color of Student: during the influence
 * calculation this turn, that color adds no influence. <br>
 * <b>Inputs required</b>: Student color.
 */
public class Char8 implements CharacterServerLogicInterface {

    /**
     * Method play sets the selected color as ignoredColor in the game.
     *
     * @param game of type {@link GameInterfaceForCharacter} - the game instance that the card modifies with its effect.
     * @throws NotAllowedException if the color is not valid.
     */
    @Override
    public void play(GameInterfaceForCharacter game) throws NotAllowedException {
        int color = game.getCharacterInputs().get(0);
        if (color < 0 || color >= Color.values().length)
            throw new NotAllowedException("Set wrong input for color");
        game.setIgnoredColorInfluence(Color.values()[color]);
    }

    @Override
    public boolean canPlay(int nInput) {
        return nInput == 1;
    }

}

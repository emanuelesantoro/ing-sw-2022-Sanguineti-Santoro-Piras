package it.polimi.ingsw.Client.PhaseAndComand.Phases;

import it.polimi.ingsw.Client.PhaseAndComand.Commands.GameCommand;
import it.polimi.ingsw.Client.View.Gui.GuiFX;
import it.polimi.ingsw.Client.View.Gui.SceneController.SceneController;
import it.polimi.ingsw.Client.View.Gui.ViewGUI;
import it.polimi.ingsw.Server.model.Game;
import it.polimi.ingsw.Util.AssistantCard;
import it.polimi.ingsw.Util.Wizard;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.security.auth.login.AccountNotFoundException;


public class PlanificationPhase extends ClientPhase {
    public PlanificationPhase() {
        super();
    }

    @Override
    public void playPhase(ViewGUI viewGUI) {
        SceneController sceneController = GuiFX.getActiveSceneController();
        sceneController.disableEverything();

        //add to  my image of wizard the handler that show all the playCharacter
        Pane assistantCardBox = (Pane) sceneController.getElementById("#assistantCardBox");
        AnchorPane assistantCardPane = (AnchorPane) sceneController.getElementById("#assistantCard" + viewGUI.getModel().getMyWizard());
        AnchorPane back = (AnchorPane) assistantCardPane.getChildren().get(0);
        sceneController.enableNode(back);
        back.setOnMouseClicked(mouseEvent -> assistantCardBox.setVisible(!assistantCardBox.isVisible()));

        HBox box = (HBox) assistantCardBox.getChildren().get(0);
        for (AssistantCard card : viewGUI.getModel().getCurrentPlayer().getAssistantCards()) {
            ImageView cardImage = new ImageView();
            cardImage.setImage(new Image("Graphical_Assets/AssistantCard/" + card.value() + ".png"));
            cardImage.setFitWidth(80);
            cardImage.setFitHeight(130);
            cardImage.getProperties().put("cardValue", card);
            HBox.setMargin(cardImage, new Insets(10, 10, 10, 10));
            box.getChildren().add(cardImage);
            sceneController.enableNode(cardImage);
            cardImage.addEventHandler(MouseEvent.MOUSE_CLICKED, GameCommand.PLAY_CARD.getGUIHandler(viewGUI));
        }
        AnchorPane deck = (AnchorPane) assistantCardPane.getChildren().get(0);
        sceneController.enableNode(deck);
        deck.setStyle("-fx-border-color: #f5f50a");
    }

    @Override
    public String toString() {
        return "Planification Phase";
    }
}

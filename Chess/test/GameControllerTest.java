import static org.junit.jupiter.api.Assertions.*;

import io.grpc.Server;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GameControllerTest {
    @Test
    public void testGameController() {
        GameController gameController = new GameController();
        assertNotNull(gameController);
    }


    @Test
    public void testSetup() throws IOException {
        ServerManager serverManager= new ServerManager();
        serverManager.ResetData();
        GameController gameController = new GameController();
        gameController.setUp();
        assertNotNull(gameController);
    }

    @Test
    public void testStartView() {
        ServerManager serverManager = new ServerManager();
        serverManager.ResetData();
        GameController gameController = new GameController();
        gameController.start();
        JFrame testStart = gameController.getStartView();
        Component[] components = testStart.getContentPane().getComponents();
        for (Component component : components){
            if (component instanceof JButton){
                ((JButton) component).doClick();
            }
        }
        assertNotNull(gameController);
    }

    @Test
    public void testConnectView() throws IOException {
        GameController gameController = new GameController();
        gameController.start();
        gameController.initializeConnectView();
        JFrame testConnect = gameController.getConnectView();
        assertNotNull(testConnect);
    }

    @Test
    public void testGameView() throws IOException {
        ServerManager serverManager = new ServerManager();
        serverManager.ResetData();
        GameController gameController1 = new GameController();
        gameController1.start();
        gameController1.setPlayerNumber(2);
        gameController1.initializeConnectView();
        gameController1.setUp();
        gameController1.initializeGameView();
        gameController1.setPlayerNumber(1);
        gameController1.initializeGameView();

        JFrame testGame = gameController1.getGameView();
        serverManager.setPlayersConnected(true);
        assertNotNull(testGame);

        gameController1.setPlayerColor(PieceColor.Black);
        serverManager = gameController1.getServerManager();
        serverManager.setPlayerTurn(1);
        FirebaseData firebaseData = new FirebaseData(true, new MovementMade((new PieceLocation(0,0)), new PieceLocation(0,0)), true, true, "");
        serverManager.setLastSavedData(firebaseData);
        Component[] components = testGame.getContentPane().getComponents();
        for (Component component : components){
            if (component instanceof JPanel){
                Component[] board = ((JPanel) component).getComponents();
                for (Component button : board){
                    if (button.getName() != null) {
                        if (button.getName().compareTo("61") == 0) {
                            ((JButton) button).doClick();
                        } else if (button.getName().compareTo("51") == 0) {
                            ((JButton) button).doClick();
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testCheckmateView() throws IOException {
        GameController gameController1 = new GameController();
        gameController1.start();
        gameController1.initializeConnectView();
        gameController1.setPlayerNumber(1);
        gameController1.initializeGameView();
        gameController1.initializeCheckmateView(1);
        JFrame testCheckMate = gameController1.getCheckmateView();
        assertNotNull(testCheckMate);
        gameController1.initializeGameView();
        gameController1.initializeCheckmateView(2);
        testCheckMate = gameController1.getCheckmateView();
        assertNotNull(testCheckMate);
        Component[] components = testCheckMate.getContentPane().getComponents();
        for (Component component : components){
            if (component instanceof JButton){
                ((JButton) component).doClick();
            }
        }
    }

    @Test
    public void testButtonClicked() throws IOException {
        ServerManager serverManager = new ServerManager();
        serverManager.ResetData();


        GameController gameController1 = new GameController();
        gameController1.start();
        gameController1.setPlayerNumber(1);
        gameController1.initializeConnectView();
        gameController1.setUp();
        gameController1.initializeGameView();

        JFrame testGame = gameController1.getGameView();
        //serverManager.setPlayersConnected(true);
        assertNotNull(testGame);

        gameController1.setPlayerColor(PieceColor.Black);
        serverManager = gameController1.getServerManager();
        serverManager.setPlayerTurn(1);

        GameController gameController2 = new GameController();
        gameController2.start();
        gameController2.setPlayerNumber(2);
        gameController2.initializeConnectView();
        gameController2.initializeGameView();

        JFrame testGame2 = gameController2.getGameView();
        serverManager.setPlayersConnected(true);
        assertNotNull(testGame2);


        gameController2.setPlayerColor(PieceColor.White);
        serverManager = gameController2.getServerManager();
        FirebaseData firebaseData = new FirebaseData(false, new MovementMade((new PieceLocation(0,0)), new PieceLocation(0,0)), true, true, "");
        serverManager.setLastSavedData(firebaseData);
        Component[] components = testGame.getContentPane().getComponents();
        for (Component component : components){
            if (component instanceof JPanel){
                Component[] board = ((JPanel) component).getComponents();
                for (Component button : board){
                    if (button.getName() != null) {
                        if (button.getName().compareTo("11") == 0) {
                            ((JButton) button).doClick();
                        } else if (button.getName().compareTo("31") == 0) {
                            ((JButton) button).doClick();
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testPlayerInCheck() throws IOException {
        ServerManager serverManager = new ServerManager();

        GameController gameController1 = new GameController();
        gameController1.start();
        gameController1.setPlayerNumber(2);
        gameController1.initializeConnectView();
        gameController1.setUp();
        gameController1.initializeGameView();

        JFrame testGame = gameController1.getGameView();
        assertNotNull(testGame);


        gameController1.setPlayerColor(PieceColor.White);
        serverManager = gameController1.getServerManager();
        serverManager.setPlayersConnected(true);
        serverManager.setPlayerTurn(1);
        FirebaseData firebaseData = new FirebaseData(false, new MovementMade((new PieceLocation(0,0)), new PieceLocation(0,0)), true, true, "");
        serverManager.setLastSavedData(firebaseData);
        Component[] components = testGame.getContentPane().getComponents();
        for (Component component : components){
            if (component instanceof JPanel){
                Component[] board = ((JPanel) component).getComponents();
                for (Component button : board){
                    if (button.getName() != null) {
                        if (button.getName().compareTo("06") == 0) {
                            ((JButton) button).doClick();
                        } else if (button.getName().compareTo("27") == 0) {
                            ((JButton) button).doClick();
                            firebaseData = new FirebaseData(false, new MovementMade((new PieceLocation(0,6)), new PieceLocation(2,7)), true, true, "");
                            serverManager.setLastSavedData(firebaseData);
                            ((JButton) button).doClick();
                        } else if (button.getName().compareTo("46") == 0) {
                            ((JButton) button).doClick();
                            firebaseData = new FirebaseData(false, new MovementMade((new PieceLocation(2,7)), new PieceLocation(4,6)), true, true, "");
                            serverManager.setLastSavedData(firebaseData);
                            ((JButton) button).doClick();
                        } else if (button.getName().compareTo("54") == 0) {
                            ((JButton) button).doClick();
                            firebaseData = new FirebaseData(true, new MovementMade((new PieceLocation(4,6)), new PieceLocation(5,4)), true, true, "");
                            serverManager.setLastSavedData(firebaseData);
                        }
                    }
                }
            }
        }
        gameController1.playerInCheck(PieceColor.Black);
        assertNotNull(testGame);

    }

    @Test
    public void testPromotion() throws IOException {
        ServerManager serverManager = new ServerManager();
        serverManager.ResetData();
        GameController gameController1 = new GameController();
        gameController1.start();
        gameController1.setPlayerNumber(2);
        gameController1.initializeConnectView();
        gameController1.setUp();
        gameController1.initializeGameView();
        gameController1.setPlayerNumber(1);
        gameController1.initializeGameView();

        JFrame testGame = gameController1.getGameView();
        serverManager.setPlayersConnected(true);
        assertNotNull(testGame);

        gameController1.setPlayerColor(PieceColor.Black);
        serverManager = gameController1.getServerManager();
        serverManager.setPlayerTurn(1);
        FirebaseData firebaseData = new FirebaseData(true, new MovementMade((new PieceLocation(0,0)), new PieceLocation(0,0)), true, true, "");
        serverManager.setLastSavedData(firebaseData);
        Component[] components = testGame.getContentPane().getComponents();
        for (Component component : components){
            if (component instanceof JPanel){
                Component[] board = ((JPanel) component).getComponents();
                for (Component button : board){
                    if (button.getName() != null) {
                        if (button.getName().compareTo("61") == 0) {
                            ((JButton) button).doClick();
                        } else if (button.getName().compareTo("51") == 0) {
                            ((JButton) button).doClick();
                        }
                    }
                }
            }
        }
        firebaseData = new FirebaseData(false, new MovementMade((new PieceLocation(0,0)), new PieceLocation(0,0)), true, true, "");
        serverManager.setLastSavedData(firebaseData);
        gameController1.generatePromotionButtons(new Pawn(new PieceLocation(1,1), PieceColor.White, PieceMovementDirection.UpColumn));
        Component component = testGame.getGlassPane().getComponentAt(400,500);
        ((JButton)component).doClick();

        firebaseData = new FirebaseData(false, new MovementMade((new PieceLocation(0,0)), new PieceLocation(0,0)), true, true, "");
        serverManager.setLastSavedData(firebaseData);
        gameController1.generatePromotionButtons(new Pawn(new PieceLocation(1,1), PieceColor.White, PieceMovementDirection.UpColumn));
        component = testGame.getGlassPane().getComponentAt(100,500);
        ((JButton)(component.getComponentAt(600,500))).doClick();

    }

}
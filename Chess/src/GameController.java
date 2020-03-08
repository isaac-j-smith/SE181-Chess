import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;

public class GameController implements Observer {

    private JFrame view;
    private HashMap<String, JButton> map;
    private ArrayList<ArrayList<ChessPiece>> boardValues;
    private Chessboard board;
    private ArrayList<PieceLocation> availableMoves;
    private ChessPiece selectedPiece;
    private ServerManager serverManager;
    private int playerNumber = 1;

    public GameController() throws IOException {
        this.map = new HashMap<>();
        this.selectedPiece = null;
        serverManager =  new ServerManager();
        serverManager.addObserver(this);
        serverManager.Firebase();
        serverManager.ListenData();
        serverManager.GetLastSavedData();
    }

    /**
     * Starts the game
     */
    public void start(){
        initializeView();

    }

    /**
     * Initializes the board with new values. Done at the start
     */
    private void initializeView() {

        this.board = new Chessboard();

        this.view = new GameView("Chess Game");
        this.view.setLayout(new GridLayout(8, 8, 2, 2));

        if (playerNumber == 1) {
            for (int i = 7; i >= 0; i--) {
                for (int j = 7; j >= 0; j--) {
                    JButton b = new JButton("");
                    if (board.getPiece(i, j) != null) {
                        try {
                            String location = "images/"
                                    + board.getPiece(i, j).color.toString()
                                    + board.getPiece(i, j).getClass().getSimpleName()
                                    + ".png";
                            b.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(location))));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if ((i % 2 != 0 && j % 2 == 0)
                            || i % 2 == 0 && j % 2 != 0) {
                        b.setBackground(Color.white);
                    } else {
                        b.setBackground(new Color(75, 65, 50));
                    }
                    b.setName(i + "" + j);
                    b.addActionListener(e -> buttonClicked(b.getName()));
                    map.put(b.getName(), b);
                    this.view.add(b);
                }
            }
        }
        else if (playerNumber == 2){
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    JButton b = new JButton("");
                    if (board.getPiece(i, j) != null) {
                        try {
                            String location = "images/"
                                    + board.getPiece(i, j).color.toString()
                                    + board.getPiece(i, j).getClass().getSimpleName()
                                    + ".png";
                            b.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(location))));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if ((i % 2 != 0 && j % 2 == 0)
                            || i % 2 == 0 && j % 2 != 0) {
                        b.setBackground(Color.white);
                    } else {
                        b.setBackground(new Color(75, 65, 50));
                    }
                    b.setName(i + "" + j);
                    b.addActionListener(e -> buttonClicked(b.getName()));
                    map.put(b.getName(), b);
                    this.view.add(b);
                }
            }
        }
        this.boardValues = board.getBoard();
        display();
    }

    /**
     * Draws all the pieces on the board. Used after moving pieces
     */
    private void drawPieces(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton b = this.getButton(i+""+j);
                if (board.getPiece(i, j) != null) {
                    try {
                        String location = "images/"
                                + board.getPiece(i, j).color.toString()
                                + board.getPiece(i, j).getClass().getSimpleName()
                                + ".png";
                        assert b != null;
                        b.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(location))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    assert b != null;
                    b.setIcon(null);

                }
            }
        }
    }

    /**
     * Action for clicking buttons
     *
     * contains logic making moves and handling the clicks
     *
     * @param name
     */
    private void buttonClicked(String name){

        int row = Integer.parseInt(String.valueOf(name.charAt(0)));
        int col = Integer.parseInt(String.valueOf(name.charAt(1)));
        ChessPiece piece = this.boardValues.get(row).get(col);
        if(piece != null && selectedPiece == null){
            selectedPiece = piece;

            this.availableMoves = board.getValidMoves(piece);

            for (PieceLocation move : this.availableMoves){
                String temp = move.row+ "" + move.column;
                getButton(temp).setBackground(Color.green);
            }
        }else{
            for(PieceLocation move : this.availableMoves){
                if(row == move.row && col == move.column){
                    this.board.movePiece(selectedPiece, move);
                    this.availableMoves = new ArrayList<>();
                    this.boardValues = this.board.getBoard();

                    drawPieces();
                }

                if ((move.row % 2 != 0 && move.column % 2 == 0)
                        || move.row % 2 == 0 && move.column % 2 != 0) {
                    Objects.requireNonNull(getButton(move.row + "" + move.column)).setBackground(Color.white);
                } else {
                    Objects.requireNonNull(getButton(move.row + "" + move.column)).setBackground(new Color(75, 65, 50));
                }
            }
            selectedPiece = null;
        }

        display();
    }

    /**
     * Gets the button at the location described in a string
     * @param name
     * @return
     */
    private JButton getButton(String name) {
        if (this.map.containsKey(name)) {
            return this.map.get(name);
        }
        return null;
    }

    private void display() {
        this.view.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (playerNumber == 0) {
            playerNumber = serverManager.getPlayerNumber();
            System.out.println("player number is: " + playerNumber);
            initializeView();
        }

        if (serverManager.getPlayerTurn() == playerNumber) {
            ChessPiece piece = board.getPiece(serverManager.getRecentMove().from.row, serverManager.getRecentMove().from.column);
            PieceLocation destination = new PieceLocation(serverManager.getRecentMove().destination.row, serverManager.getRecentMove().destination.column);
            this.board.movePiece(piece, destination);
        }
    }
}

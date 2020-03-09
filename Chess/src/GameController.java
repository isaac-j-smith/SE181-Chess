import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.*;

public class GameController implements Observer {

    private JFrame startView;
    private JFrame connectView;
    private JFrame gameView;
    private JFrame checkmateView;
    private HashMap<String, JButton> map;
    private ArrayList<ArrayList<ChessPiece>> boardValues;
    private Chessboard board;
    private ArrayList<PieceLocation> availableMoves;
    private ChessPiece selectedPiece;
    private ServerManager serverManager;
    private int playerNumber;
    private PieceColor playerColor;

    public GameController() {
        this.map = new HashMap<>();
        this.selectedPiece = null;
    }

    /**
     * Sends player to start screen
     */
    public void start() {
        initializeStartView();
    }

    /**
     * Initializes serverManager and attaches this as observer
     */
    public void setUp() throws IOException {
        playerNumber = 0;
        serverManager = new ServerManager();
        serverManager.addObserver(this);
        serverManager.Firebase();
        serverManager.ListenData();
    }

    /**
     * Initializes JFrame for start screen.
     */
    public void initializeStartView(){
        startView = new GameView("Start Screen");
        JButton b=new JButton("Ready");

        b.setBounds(300,500,300, 100);
        b.setFont(new Font("Arial", Font.PLAIN, 40));

        JLabel label = new JLabel("Chess");
        label.setText("Chess");
        label.setFont(new Font("Arial", Font.PLAIN, 200));
        label.setForeground(Color.white);
        label.setBounds(160,100,700, 300);

        JLabel label1 = new JLabel("Release");
        label1.setText("Release Version 1.0.6");
        label1.setFont(new Font("Arial", Font.PLAIN, 30));
        label1.setForeground(Color.white);
        label1.setBounds(570,780,500, 100);

        //add to frame
        startView.add(b);
        startView.add(label);
        startView.add(label1);
        startView.setLayout(null);

        //action listener
        b.addActionListener(e -> {
            try {
                playerNumber = 0;
                initializeConnectView();
                setUp();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        display(startView);
    }

    /**
     * Initializes JFrame for connection screen.
     */
    public void initializeConnectView() throws IOException {
        connectView = new GameView("Connect Screen");
        JLabel label = new JLabel("Connection");
        label.setText("Waiting for another player to connect...");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setForeground(Color.white);
        label.setBounds(160,300,900, 300);

        connectView.add(label);
        connectView.setLayout(null);

        hide(startView);
        display(connectView);
    }

    /**
     * Initializes the board with new values. Done at the start
     */
    public void initializeGameView() {
        this.board = new Chessboard();

        this.gameView = new GameView("Chess Game");
        JPanel boardLayer = new JPanel();
        boardLayer.setLayout(new GridLayout(10, 10, 2, 2));

        if (playerNumber == 1) {
            for (int i = 9; i >= 0; i--) {
                for (int j = 9; j >= 0; j--) {
                    if (i == 9 || j == 0) {
                        JLabel label = new JLabel();
                        boardLayer.add(label);
                    } else if (i == 0) {
                        if (j == 9) {
                            JLabel label = new JLabel();
                            label.setHorizontalAlignment(JLabel.CENTER);
                            label.setVerticalAlignment(JLabel.TOP);
                            boardLayer.add(label);
                        }
                        if (j < 9) {
                            char c = (char) (73 - j);
                            JLabel label = new JLabel();
                            label.setText(Character.toString(c));
                            label.setHorizontalAlignment(JLabel.CENTER);
                            label.setVerticalAlignment(JLabel.TOP);
                            boardLayer.add(label);
                        }
                    } else if (j == 9) {
                        JLabel label = new JLabel();
                        label.setText(Integer.toString(i));
                        label.setHorizontalAlignment(JLabel.RIGHT);
                        boardLayer.add(label);
                    } else {
                        JButton b = new JButton("");
                        if (board.getPiece(i - 1, j - 1) != null) {
                            try {
                                String location = "images/"
                                        + board.getPiece(i - 1, j - 1).color.toString()
                                        + board.getPiece(i - 1, j - 1).getClass().getSimpleName()
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
                        b.setName(i - 1 + "" + (j - 1));
                        b.addActionListener(e -> buttonClicked(b.getName()));
                        map.put(b.getName(), b);
                        boardLayer.add(b);
                    }
                }
            }
        } else if (playerNumber == 2) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (i == 0 || j == 9) {
                        JLabel label = new JLabel();
                        boardLayer.add(label);
                    } else if (i == 9) {
                        if (j == 0) {
                            JLabel label = new JLabel();
                            label.setHorizontalAlignment(JLabel.CENTER);
                            label.setVerticalAlignment(JLabel.TOP);
                            boardLayer.add(label);
                        }
                        if (j > 0) {
                            char c = (char) (j + 64);
                            JLabel label = new JLabel();
                            label.setText(Character.toString(c));
                            label.setHorizontalAlignment(JLabel.CENTER);
                            label.setVerticalAlignment(JLabel.TOP);
                            boardLayer.add(label);
                        }
                    } else if (j == 0) {
                        JLabel label = new JLabel();
                        label.setText(Integer.toString(9 - i));
                        label.setHorizontalAlignment(JLabel.RIGHT);
                        boardLayer.add(label);
                    } else {
                        JButton b = new JButton("");
                        if (board.getPiece(i - 1, j - 1) != null) {
                            try {
                                String location = "images/"
                                        + board.getPiece(i - 1, j - 1).color.toString()
                                        + board.getPiece(i - 1, j - 1).getClass().getSimpleName()
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
                        b.setName((i - 1) + "" + (j - 1));
                        b.addActionListener(e -> buttonClicked(b.getName()));
                        map.put(b.getName(), b);
                        boardLayer.add(b);
                    }
                }
            }
        }
        gameView.getContentPane().add(boardLayer);
        this.boardValues = board.getBoard();
        hide(connectView);
        display(gameView);
        resetOnExit(gameView);

    }

    public void initializeCheckmateView(int winningPlayer) throws IOException {
        checkmateView = new GameView("Checkmate Screen");

        JLabel label = new JLabel("Checkmate");
        if (playerNumber == winningPlayer){
            label.setText("Checkmate! You win!");
        }else{
            label.setText("Checkmate! You lose!");
        }

        label.setFont(new Font("Arial", Font.PLAIN, 50));
        label.setForeground(Color.white);
        label.setBounds(200,100,700, 300);

        JButton b=new JButton("Return to start screen");
        b.setBounds(100,500,700, 100);
        b.setFont(new Font("Arial", Font.PLAIN, 40));

        //add to frame
        checkmateView.add(b);
        checkmateView.add(label);
        checkmateView.setLayout(null);

        //action listener
        b.addActionListener(e -> {
                initializeStartView();
                hide(checkmateView);
        });
        hide(gameView);
        gameView = null;
        display(checkmateView);
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

        if (piece != null &&  selectedPiece == null) {
            if (serverManager.getPlayerTurn() == playerNumber && piece.color.equals(playerColor)){
                selectedPiece = piece;

                this.availableMoves = board.getValidMoves(piece);

                for (PieceLocation move : this.availableMoves) {
                    String temp = move.row + "" + move.column;
                    getButton(temp).setBackground(Color.green);
                }
            }
        } else {
            for (PieceLocation move : this.availableMoves) {
                if (row == move.row && col == move.column) {
                    SaveMove(selectedPiece, move, "");
                    this.board.movePiece(selectedPiece, move);
                    this.availableMoves = new ArrayList<>();
                    this.boardValues = this.board.getBoard();

                    drawPieces();

                    if (playerNumber == 1){
                        if (board.isInCheckmate(PieceColor.Black)){
                            serverManager.ResetData();
                            try {
                                initializeCheckmateView(playerNumber);
                                gameView = null;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (board.isInCheck(PieceColor.Black)){
                            playerInCheck(PieceColor.Black);
                        }
                    }else{
                        if (board.isInCheckmate(PieceColor.White)){
                            serverManager.ResetData();
                            try {
                                initializeCheckmateView(playerNumber);
                                gameView = null;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (board.isInCheck(PieceColor.White)){
                            playerInCheck(PieceColor.White);
                        }
                    }
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

        if (gameView != null) {
            display(gameView);
        }
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

    private void display(JFrame view) {
        view.setVisible(true);
    }

    private void hide(JFrame view) {
        view.setVisible(false);
    }

    private void resetOnExit(JFrame view){
        gameView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                serverManager.ResetData();
            }
        });
    }

    public void playerInCheck(PieceColor color){
        int delay = 2000;

        JPanel checkLayer = (JPanel) gameView.getGlassPane();
        checkLayer.setVisible(true);
        checkLayer.setLayout(new GridBagLayout());

        JLabel label = new JLabel("Check");
        if (color.equals(PieceColor.Black)){
            label.setText("Black is in Check");
        }
        else {
            label.setText("White is in Check");
        }
        label.setFont(new Font("Arial", Font.PLAIN, 100));
        label.setForeground(Color.red);
        label.setBounds(100,500,700, 300);
        checkLayer.add(label);

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                label.setVisible(false);
                checkLayer.remove(label);
            }
        };
        new javax.swing.Timer(delay, taskPerformer).start();
    }

    public void generatePromotionButtons(Pawn pawn){
        JPanel promotionLayer = (JPanel) gameView.getGlassPane();
        promotionLayer.setVisible(true);
        promotionLayer.setLayout(new GridBagLayout());

        JButton b1=new JButton("Bishop");
        JButton b2=new JButton("Knight");
        JButton b3=new JButton("Rook");
        JButton b4=new JButton("Queen");

        b1.setBounds(300,500,300, 100);
        b1.setFont(new Font("Arial", Font.PLAIN, 40));
        b1.addActionListener(e -> {
            board.promotePawn(pawn, "Bishop");
            SaveMove(board.getPiece(pawn.location), pawn.location, "Bishop");
            b1.setVisible(false);
            b2.setVisible(false);
            b3.setVisible(false);
            b4.setVisible(false);
            promotionLayer.remove(b1);
            promotionLayer.remove(b2);
            promotionLayer.remove(b3);
            promotionLayer.remove(b4);
            drawPieces();
            display(gameView);
        });

        b2.setBounds(300,500,300, 100);
        b2.setFont(new Font("Arial", Font.PLAIN, 40));
        b2.addActionListener(e -> {
            board.promotePawn(pawn, "Knight");
            SaveMove(board.getPiece(pawn.location), pawn.location, "Knight");
            b1.setVisible(false);
            b2.setVisible(false);
            b3.setVisible(false);
            b4.setVisible(false);
            promotionLayer.remove(b1);
            promotionLayer.remove(b2);
            promotionLayer.remove(b3);
            promotionLayer.remove(b4);
            drawPieces();
            display(gameView);
        });

        b3.setBounds(300,500,300, 100);
        b3.setFont(new Font("Arial", Font.PLAIN, 40));
        b3.addActionListener(e -> {
            board.promotePawn(pawn, "Rook");
            SaveMove(board.getPiece(pawn.location), pawn.location, "Rook");
            b1.setVisible(false);
            b2.setVisible(false);
            b3.setVisible(false);
            b4.setVisible(false);
            promotionLayer.remove(b1);
            promotionLayer.remove(b2);
            promotionLayer.remove(b3);
            promotionLayer.remove(b4);
            drawPieces();
            display(gameView);
         });


        b4.setBounds(300,500,300, 100);
        b4.setFont(new Font("Arial", Font.PLAIN, 40));
        b4.addActionListener(e -> {
            board.promotePawn(pawn, "Queen");
            SaveMove(board.getPiece(pawn.location), pawn.location, "Queen");
            b1.setVisible(false);
            b2.setVisible(false);
            b3.setVisible(false);
            b4.setVisible(false);
            promotionLayer.remove(b1);
            promotionLayer.remove(b2);
            promotionLayer.remove(b3);
            promotionLayer.remove(b4);
            drawPieces();
            display(gameView);
        });

        promotionLayer.add(b1);
        promotionLayer.add(b2);
        promotionLayer.add(b3);
        promotionLayer.add(b4);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (playerNumber == 0) {
            playerNumber = serverManager.getPlayerNumber();
            serverManager.connectPlayer(playerNumber);
            if (playerNumber == 1){
                playerColor = PieceColor.White;
            }
            else if (playerNumber == 2){
                playerColor = PieceColor.Black;
            }
            else{
                hide(connectView);
                playerNumber = 3;
                serverManager.DenyAccess();
                initializeStartView();
            }
        }

        if (serverManager.getPlayersConnected() && gameView == null){
            initializeGameView();
        }

        if (serverManager.getPlayerTurn() == playerNumber) {
            if (serverManager.getPawnPromotion().isEmpty()) {
                ChessPiece piece = board.getPiece(serverManager.getRecentMove().from.row, serverManager.getRecentMove().from.column);
                PieceLocation destination = new PieceLocation(serverManager.getRecentMove().destination.row, serverManager.getRecentMove().destination.column);
                this.board.movePiece(piece, destination);
            }
            else {
                //ChessPiece piece = board.getPiece(serverManager.getRecentMove().from.row, serverManager.getRecentMove().from.column);
                if (serverManager.getRecentMove().from.row == 7){
                    ArrayList<ChessPiece> chessPieces = board.getPieces(PieceColor.White);
                    for (ChessPiece piece : chessPieces){
                        for (PieceLocation destination : board.getValidMoves(piece)){
                            if (destination.row == serverManager.getRecentMove().from.row && destination.column == getServerManager().getRecentMove().from.column){
                                this.board.movePiece(piece, destination);
                                if (serverManager.getPawnPromotion().compareTo("Bishop") == 0) {
                                    board.promotePawn((Pawn) piece, "Bishop");
                                } else if (serverManager.getPawnPromotion().compareTo("Knight") == 0) {
                                    board.promotePawn((Pawn) piece, "Knight");
                                } else if (serverManager.getPawnPromotion().compareTo("Rook") == 0) {
                                    board.promotePawn((Pawn) piece, "Rook");
                                } else if (serverManager.getPawnPromotion().compareTo("Queen") == 0) {
                                    board.promotePawn((Pawn) piece, "Queen");
                                }
                            }
                        }
                    }

                }
                else if (serverManager.getRecentMove().from.row == 0){
                    ArrayList<ChessPiece> chessPieces = board.getPieces(PieceColor.Black);
                    for (ChessPiece piece : chessPieces){
                        for (PieceLocation destination : board.getValidMoves(piece)){
                            if (destination.row == serverManager.getRecentMove().from.row && destination.column == getServerManager().getRecentMove().from.column){
                                this.board.movePiece(piece, destination);
                                if (serverManager.getPawnPromotion().compareTo("Bishop") == 0) {
                                    board.promotePawn((Pawn) piece, "Bishop");
                                } else if (serverManager.getPawnPromotion().compareTo("Knight") == 0) {
                                    board.promotePawn((Pawn) piece, "Knight");
                                } else if (serverManager.getPawnPromotion().compareTo("Rook") == 0) {
                                    board.promotePawn((Pawn) piece, "Rook");
                                } else if (serverManager.getPawnPromotion().compareTo("Queen") == 0) {
                                    board.promotePawn((Pawn) piece, "Queen");
                                }
                            }
                        }
                    }
                }
            }
            drawPieces();

            if(board.isInCheckmate(playerColor)){
                serverManager.ResetData();
                try {
                    if (playerNumber == 1){
                        initializeCheckmateView(2);
                    }
                    else{
                        initializeCheckmateView(1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(board.isInCheck(playerColor)){
                playerInCheck(playerColor);
                display(gameView);
            }
        }
    }

    /**
     * Saves the move to firebase with players turn, from move and destination moves
     * @param ChessPiece - the piece to move
     * @param PieceLocation - the location to move the piece
     */
    public void SaveMove(ChessPiece piece, PieceLocation destination, String promotion){
        FirebaseData lastSaved = serverManager.GetLastSavedData();
        Boolean sameMove =  CompareLocation(lastSaved, piece.location, destination); //

        if (sameMove)
            return;

        Boolean isWhiteTurn = false;
        if (playerNumber == 1){
            isWhiteTurn = false;
        }
        else if (playerNumber == 2){
            isWhiteTurn = true;
        }
        PieceLocation from = piece.location;

        if (piece instanceof Pawn && (destination.row == 0 || destination.row == 7)){
            generatePromotionButtons((Pawn) piece);
        }
        else {
            serverManager.SaveData(isWhiteTurn, from, destination, promotion);
        }
    }

    public Boolean CompareLocation(FirebaseData lastSavedMove, PieceLocation from, PieceLocation destination){
        int board_fromColumn = from.column;
        int board_fromRow = from.row;
        int board_destinationColumn = destination.column;
        int board_destinationRow = destination.row;

        int fireBase_fromColumn = lastSavedMove.movementMade.from.column;
        int fireBase_fromRow = lastSavedMove.movementMade.from.row;
        int fireBase_destinationColumn = lastSavedMove.movementMade.destination.column;
        int fireBase_destinationRow = lastSavedMove.movementMade.destination.row;

        //location did not changed. no need to save
        //location change need to save
        return board_fromColumn == fireBase_fromColumn
                && board_fromRow == fireBase_fromRow
                && board_destinationColumn == fireBase_destinationColumn
                && board_destinationRow == fireBase_destinationRow;
    }

    public JFrame getStartView(){
        return startView;
    }

    public JFrame getConnectView(){
        return connectView;
    }

    public JFrame getGameView(){
        return gameView;
    }

    public JFrame getCheckmateView() {
        return checkmateView;
    }

    public int getPlayerNumber(){
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber){
        this.playerNumber = playerNumber;
    }

    public ServerManager getServerManager(){
        return serverManager;
    }

    public PieceColor getPlayerColor(){
        return playerColor;
    }

    public void setPlayerColor(PieceColor color){
        playerColor = color;
    }

}

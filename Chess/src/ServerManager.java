import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.Period;
import java.util.ArrayList;

import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class ServerManager extends Observable {
    public static FirebaseData lastSavedData;
    private MovementMade recentMove;
    private int playerTurn;
    private int playerNumber;
    private boolean playersConnected;
    private String pawnPromotion;


    public void Firebase() throws IOException {

        FileInputStream refreshToken = new FileInputStream("./Chess/src/service-account.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .setDatabaseUrl("https://chess-7c351.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

    }

    public void SaveData(Boolean isWhiteTurn, PieceLocation from, PieceLocation destination, String promotion){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference usersRef = ref.child("chess");

        Map<String, FirebaseData> users = new HashMap<>();

        FirebaseData firebaseData = new FirebaseData(isWhiteTurn, new MovementMade(from,destination), true, true, promotion);

        if (isWhiteTurn){
            playerTurn = 1;
        }
        else{
            playerTurn = 2;
        }

        users.put("firebaseData",firebaseData);

        usersRef.setValueAsync(users);
    }

    public static void connectPlayer(int playerNumber){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference usersRef = ref.child("chess");

        Map<String, FirebaseData> users = new HashMap<>();


        FirebaseData firebaseData = new FirebaseData(true, new MovementMade(new PieceLocation(0, 0), new PieceLocation(0, 0)), false, false, "");

        if (playerNumber == 1) {
            firebaseData = new FirebaseData(true, new MovementMade(new PieceLocation(0, 0), new PieceLocation(0, 0)), true, false, "");
        }
        else {
            firebaseData = new FirebaseData(true, new MovementMade(new PieceLocation(0,0),new PieceLocation(0,0)), true, true, "");
        }

        users.put("firebaseData",firebaseData);

        usersRef.setValueAsync(users);
    }

    public void ResetData(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference usersRef = ref.child("chess");

        Map<String, FirebaseData> users = new HashMap<>();

        FirebaseData firebaseData = new FirebaseData(true, new MovementMade(new PieceLocation(0, 0), new PieceLocation(0, 0)), false, false, "");

        users.put("firebaseData",firebaseData);

        usersRef.setValueAsync(users);
        FirebaseApp.getInstance().delete();
        deleteObservers();
    }

    public static FirebaseData GetLastSavedData(){
        return lastSavedData;
    }

    public int getPlayerNumber(){
        return playerNumber;
    }

    public MovementMade getRecentMove(){
        return recentMove;
    }

    public int getPlayerTurn(){
        return playerTurn;
    }

    public boolean getPlayersConnected(){
        return playersConnected;
    }

    public String getPawnPromotion(){
        return pawnPromotion;
    }

    public void ListenData(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("chess");

        // Attach a listener to read the data at our posts reference
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                lastSavedData = dataSnapshot.getValue(FirebaseData.class);

                if (!(lastSavedData.player1Connected)){
                    playerNumber = 1;
                }
                else if (!(lastSavedData.player2Connected)){
                    playerNumber = 2;
                }
                else{
                    playerNumber = 0;

                }
                setChanged();
                notifyObservers();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                FirebaseData retrievedData = dataSnapshot.getValue(FirebaseData.class);

                if (retrievedData.isWhiteTurn){
                    playerTurn = 1;
                }
                else{
                    playerTurn = 2;
                }

                if (retrievedData.player1Connected && retrievedData.player2Connected){
                    playersConnected = true;
                }
                else{
                    playersConnected = false;
                }

                pawnPromotion = retrievedData.promotionChoice;
                recentMove = retrievedData.movementMade;
                setChanged();
                notifyObservers();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }



}

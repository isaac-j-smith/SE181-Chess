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


    public void Firebase() throws IOException {

        FileInputStream refreshToken = new FileInputStream("./Chess/src/service-account.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .setDatabaseUrl("https://chess-7c351.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

    }

    public static void SaveData(Boolean isWhiteTurn, PieceLocation from, PieceLocation destination){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference usersRef = ref.child("chess");

        Map<String, FirebaseData> users = new HashMap<>();

        FirebaseData firebaseData = new FirebaseData(isWhiteTurn, new MovementMade(from,destination));

        users.put("firebaseData",firebaseData);

        usersRef.setValueAsync(users);

        System.out.println("data has changed");
    }

    public static FirebaseData GetLastSavedData(){
        System.out.println("data read");
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

    public void ListenData(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("chess");

        // Attach a listener to read the data at our posts reference
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                lastSavedData = dataSnapshot.getValue(FirebaseData.class);

                System.out.println(lastSavedData.player1Connected);
                if (!(lastSavedData.player1Connected)){
                    playerNumber = 1;
                }
                else if (!(lastSavedData.player2Connected)){
                    playerNumber = 2;
                }
                else{
                    playerNumber = 0;
                }
                this.notifyAll();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                System.out.println("reading changed");
                FirebaseData retrievedData = dataSnapshot.getValue(FirebaseData.class);

                if (retrievedData.isWhiteTurn){
                    playerTurn = 1;
                }
                else{
                    playerTurn = 2;
                }
                recentMove = retrievedData.movementMade;

                System.out.println(retrievedData.isWhiteTurn + " " + retrievedData.movementMade.from + " to " + retrievedData.movementMade.destination);

                System.out.println("from row:" + retrievedData.movementMade.from.row);
                System.out.println("from col:" + retrievedData.movementMade.from.column);
                System.out.println("to row:" + retrievedData.movementMade.destination.row);
                System.out.println("to col:" + retrievedData.movementMade.destination.column);
                this.notifyAll();

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

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

public class ServerManager {
    public void Firebase() throws IOException {

        FileInputStream refreshToken = new FileInputStream("./src/service-account.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .setDatabaseUrl("https://chess-7c351.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);


        /*
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("restricted_access/secret_document");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object document = dataSnapshot.getValue();
                System.out.println(document);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        */

    }

    public void SaveData(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference usersRef = ref.child("chess");

        Map<String, FirebaseData> users = new HashMap<>();

        PieceLocation from = new PieceLocation(2,2);
        PieceLocation destination = new PieceLocation(3,2);


        FirebaseData firebaseData = new FirebaseData(true, new MovementMade(from,destination));


        users.put("firebaseData",firebaseData);

        usersRef.setValueAsync(users);

        System.out.println("data has changed");
    }


    public void ReadData(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("chess");

        System.out.println("before reading");
        // Attach a listener to read the data at our posts reference
        ref.addChildEventListener(new ChildEventListener() {
/*
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("during reading222");
                var retrievedData = dataSnapshot.getValue(FirebaseData.class);
                System.out.println(retrievedData);
            }
*/
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("during reading222");
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    var retrievedData = dataSnapshot.getValue(FirebaseData.class);
                    System.out.println(retrievedData);

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

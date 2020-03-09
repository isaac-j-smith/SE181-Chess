import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class FirebaseDataTest {
    @Test
    public void testData() {
        FirebaseData firebaseData = new FirebaseData();
        assertNotNull(firebaseData);
        MovementMade testMovementMade = new MovementMade(new PieceLocation(0,0), new PieceLocation(0,0));
        firebaseData = new FirebaseData(true, testMovementMade, false, false, "");
        assertNotNull(firebaseData);
        assertEquals(true, firebaseData.isWhiteTurn);
        assertEquals(testMovementMade, firebaseData.movementMade);
        assertEquals(false, firebaseData.player1Connected);
        assertEquals(false, firebaseData.player2Connected);
        assertEquals("", firebaseData.promotionChoice);
    }
}
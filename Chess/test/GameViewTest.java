import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.awt.*;

public class GameViewTest {
    @Test
    public void testGameView() {
        GameView gameView = new GameView("Test View");
        assertEquals("Test View", gameView.getName());
        assertEquals(new Dimension(900, 900), gameView.getSize());
        assertEquals("Test View", gameView.getTitle());
        assertEquals(new Color(100, 100, 100), gameView.getContentPane().getBackground());
    }
}
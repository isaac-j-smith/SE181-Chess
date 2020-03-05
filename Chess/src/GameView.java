import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {

    public GameView(String name) {
        this.setName(name);
        this.setSize(900, 900);
        this.setTitle(name);
        this.getContentPane().setBackground(new Color(100, 100, 100));
        this.setLocationRelativeTo(null);
    }
}

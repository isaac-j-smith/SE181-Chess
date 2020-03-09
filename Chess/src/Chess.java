import java.io.IOException;


public class Chess<options> {

    public Chess() {
    }

    public static void main(String[] args) throws IOException {
        GameController controller = new GameController();
        controller.start();
    }

}
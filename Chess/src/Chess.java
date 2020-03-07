import java.io.IOException;


public class Chess<options> {

    public Chess() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        GameController controller = new GameController();
        controller.start();

        ServerManager server = new ServerManager();
        server.Firebase();
        server.ReadData();
        server.SaveData();
    }




}
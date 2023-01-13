import student.adventure.UserInterfaceEngine;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        UserInterfaceEngine game = new UserInterfaceEngine(args[0]);
        game.start();
    }
}

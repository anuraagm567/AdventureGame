import student.adventure.UserInterfaceEngine;
import student.adventure.TimeTangledIslandGameEngine;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        UserInterfaceEngine game = new UserInterfaceEngine("src/main/resources/time_tangled_island_full_game.json");
        game.start();
    }
}

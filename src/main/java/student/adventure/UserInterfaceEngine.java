package student.adventure;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Class that processes the direct input from user and sends information to the core game engine to respond to commands
 */
public class UserInterfaceEngine {
    private TimeTangledIslandGameEngine gameEngine;

    public UserInterfaceEngine(String filePathForGame) throws FileNotFoundException, JsonParseException {
        try {
            Gson gson = new Gson();
            FileReader jsonFileReader = new FileReader(filePathForGame);

            gameEngine = new TimeTangledIslandGameEngine(gson.fromJson(jsonFileReader, GamePropertyCollection.class));
        } catch (FileNotFoundException invalidFileException) {
            throw new FileNotFoundException("File does not exist or could not be read.");
        } catch (JsonParseException parsingException) {
            throw new JsonParseException("JSON file does not fit required schema.");
        }
    }

    /**
     * Starts user interface engine to begin taking input from user and manages the printing of response messages
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        List<String> setupMessage = gameEngine.executeCommand(new ArrayList<>(Arrays.asList("examine")));

        // Exception will be thrown if the starting room is null
        if (gameEngine.isGameFinished()) {
            throw new IllegalArgumentException(setupMessage.get(0));
        }

        // Print initial room state
        System.out.println(String.join("\n", setupMessage));

        while (!gameEngine.isGameFinished()) {
            System.out.print("> ");

            String userCommand = scanner.nextLine();

            List<String> gameResponseMessages = handleGameResponseForUserCommand(userCommand);

            if (gameResponseMessages.size() > 0) {
                System.out.println(String.join("\n", gameResponseMessages));
            }
        }
    }

    private List<String> handleGameResponseForUserCommand(String userCommand) {
        return gameEngine.executeCommand(organizeUserCommand(userCommand));
    }

    static List<String> organizeUserCommand(String userCommand) {
        String trimmedLowerCaseCommand = userCommand.toLowerCase().trim();

        if (trimmedLowerCaseCommand.isEmpty()) {
            return new ArrayList<>();
        }

        if (trimmedLowerCaseCommand.indexOf(" ") == -1) {
            return new ArrayList<>(Arrays.asList(trimmedLowerCaseCommand));
        }

        String firstComponent = trimmedLowerCaseCommand.substring(0, trimmedLowerCaseCommand.indexOf(" "));
        String secondComponent = trimmedLowerCaseCommand.substring(trimmedLowerCaseCommand.indexOf(" ") + 1);

        while(secondComponent.startsWith(" ")) {
            secondComponent = secondComponent.substring(1);
        }

        return new ArrayList<>(Arrays.asList(firstComponent, secondComponent));
    }

    public TimeTangledIslandGameEngine getGameEngine() {
        return gameEngine;
    }
}

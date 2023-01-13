package student.adventure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Game engine to handle interaction between user input and stored game data from JSON file.
 */
public class TimeTangledIslandGameEngine {
    private GamePropertyCollection gameProperties;
    private Room currentRoom;
    private List<Item> playerInventory;
    private boolean gameFinished;

    public TimeTangledIslandGameEngine(GamePropertyCollection setGameProperties) {
        gameProperties = setGameProperties;
        currentRoom = gameProperties.retrieveRoom(gameProperties.getStartingRoomName());

        playerInventory = new ArrayList<>();
    }

    /**
     * Accepts list of commands and returns corresponding list of response messages based on the provided commands
     * @param commandComponents Parts of command message sent in by user input
     * @return List of Strings corresponding to response messsages which will be printed by user interface
     */
    public List<String> executeCommand(List<String> commandComponents) {
        if (commandComponents.isEmpty()) {
            return new ArrayList<>();
        }

        if (commandComponents.size() == 1) {
            return executeOneComponentCommand(commandComponents.get(0));
        }

        return executeTwoComponentCommand(commandComponents);
    }

    private List<String> executeOneComponentCommand(String command) {
        switch (command) {
            case "examine":
                return handleExamineCommand();
            case "exit":
            case "quit":
                gameFinished = true;

                return new ArrayList<>(Arrays.asList("The game will now terminate."));
            case "inventory":
                return new ArrayList<>(Arrays.asList("items: " +
                                                     String.join(", ",
                                                                 playerInventory.stream()
                                                                                .map(Item::getItemName)
                                                                                .collect(Collectors.toList()))));
            case "trade":
                if (currentRoom.getNonPlayerCharacter() == null) {
                    return new ArrayList<>(Arrays.asList("There must be a Non-Player Character in the room " +
                                                         "for this command to be valid."));
                }

                return currentRoom.getNonPlayerCharacter().getItemRequestDialogue();
            default:
                return new ArrayList<>(Arrays.asList("The entered command is not supported."));
        }
    }

    private List<String> executeTwoComponentCommand(List<String> commandComponents) {
        switch (commandComponents.get(0)) {
            case "take":
                return handleTakeItemCommand(commandComponents.get(1));
            case "drop":
                return handleDropItemCommand(commandComponents.get(1));
            case "inspect":
                return handleInspectItemCommand(commandComponents.get(1));
            case "trade":
                return handleTradeItemCommand(commandComponents.get(1));
            case "go":
                return handleGoToRoomCommand(commandComponents.get(1));
            default:
                return new ArrayList<>(Arrays.asList("The entered command is not supported."));
        }
    }

    private List<String> handleExamineCommand() {
        if (currentRoom == null) {
            gameFinished = true;
            return new ArrayList<>(Arrays.asList("Unable to load starting room."));
        }

        if (currentRoom.toString() == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(Arrays.asList(currentRoom.toString()));
    }

    private List<String> handleTakeItemCommand(String itemName) {
        Item matchingItem = currentRoom.takeItem(itemName);

        if (matchingItem == null) {
            return new ArrayList<>(Arrays.asList("There is no " + itemName + " in the room"));
        }

        playerInventory.add(matchingItem);

        return new ArrayList<>(Arrays.asList("* " + itemName + " has been added to inventory *"));
    }

    private List<String> handleDropItemCommand(String itemName) {
        List<Item> matchingItems = findMatchingItemsInInventory(itemName);

        if (matchingItems.isEmpty()) {
            return new ArrayList<>(Arrays.asList("You don't have \"" + itemName + "\"!"));
        }

        Item matchingItem = matchingItems.get(0);
        playerInventory.remove(matchingItem);

        currentRoom.addItem(matchingItem);

        return new ArrayList<>(Arrays.asList("* " + itemName + " has been dropped from inventory *"));
    }

    private List<String> handleInspectItemCommand(String itemName) {
        List<Item> matchingItems = findMatchingItemsInInventory(itemName);

        if (matchingItems.isEmpty()) {
            return new ArrayList<>(Arrays.asList("You don't have \"" + itemName + "\"!"));
        }

        Item matchingItem = matchingItems.get(0);

        if (matchingItem.getItemDescription() == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(Arrays.asList(matchingItem.getItemDescription()));
    }

    private List<String> handleTradeItemCommand(String itemName) {
        List<Item> matchingItems = findMatchingItemsInInventory(itemName);

        if (matchingItems.isEmpty()) {
            return new ArrayList<>(Arrays.asList("You don't have \"" + itemName + "\"!"));
        }

        Item matchingItem = matchingItems.get(0);

        return tradeItem(matchingItem);
    }

    private List<String> tradeItem(Item itemToTrade) {
        Item rewardItem = currentRoom.getNonPlayerCharacter().getRewardItem();

        if (itemToTrade.equals(currentRoom.getNonPlayerCharacter().getMissingItem())) {
            List<String> responseMessage = currentRoom.getNonPlayerCharacter().getRequestCompletedDialogue();

            playerInventory.remove(itemToTrade);
            playerInventory.add(rewardItem);

            responseMessage.add("* " + rewardItem.getItemName() + " has been added to inventory *");

            return responseMessage;
        }

        return new ArrayList<>(Arrays.asList("Sorry, that is not the item I was looking for."));
    }

    private List<Item> findMatchingItemsInInventory(String itemName) {
        return playerInventory.stream()
                              .filter(item -> item.getItemName().equals(itemName))
                              .collect(Collectors.toList());
    }

    private List<String> handleGoToRoomCommand(String directionName) {
        List<Direction> matchingDirections = currentRoom.getDirections()
                                                        .stream()
                                                        .filter(direction -> direction.getDirectionName()
                                                                                      .equalsIgnoreCase(directionName))
                                                        .collect(Collectors.toList());

        if (matchingDirections.isEmpty()) {
            return new ArrayList<>(Arrays.asList("I can't go \"" +
                                                 directionName.substring(0, 1).toUpperCase() +
                                                 directionName.substring(1) +
                                                 "ward\"!"));
        }

        String roomName = matchingDirections.get(0).getRoom();

        if (roomName == null) {
            return new ArrayList<>(Arrays.asList("Can not enter room with null name."));
        }

        return updateCurrentRoom(gameProperties.retrieveRoom(roomName), matchingDirections.get(0));
    }

    private List<String> updateCurrentRoom(Room nextRoom, Direction direction) {
        if (nextRoom == null) {
            return new ArrayList<>(Arrays.asList("Game file does not contain the required " +
                    "data for the associated room name."));
        }

        if (!playerInventory.containsAll(direction.getItemsNeededToProgress())) {
            return new ArrayList<>(Arrays.asList("You do not have the needed item(s) to proceed in this direction."));
        }

        // Update current room and return new room description to be printed, and checks if ending room is reached
        if (gameProperties.getEndingRoomName() != null
            && gameProperties.getEndingRoomName().equals(nextRoom.getName())) {
            gameFinished = true;
        }

        currentRoom = nextRoom;

        return new ArrayList<>(Arrays.asList(currentRoom.toString()));
    }

    public GamePropertyCollection getGameProperties() {
        return gameProperties;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public List<Item> getPlayerInventory() {
        return new ArrayList<>(playerInventory);
    }

    public boolean isGameFinished() {
        return gameFinished;
    }
}

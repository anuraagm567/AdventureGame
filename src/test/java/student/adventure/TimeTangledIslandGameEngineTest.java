package student.adventure;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Testing file for Time Tangled Island Game Engine
 * Deals with core logic of program and checking whether user commands have properly updated the game state
 */
public class TimeTangledIslandGameEngineTest {
    private UserInterfaceEngine game;

    @Before
    public void setUp() throws FileNotFoundException {
        game = new UserInterfaceEngine("src/main/resources/time_tangled_island_sample.json");
    }

    //Helper methods for tests
    private boolean confirmUnchangedInventoryAfterCommand(List<String> command) {
        // Getter returns a copy rather than a reference, allowing the use of this value for comparison
        List<Item> playerInventoryBeforeCommand = game.getGameEngine().getPlayerInventory();

        game.getGameEngine().executeCommand(command);

        return playerInventoryBeforeCommand.equals(game.getGameEngine().getPlayerInventory());
    }

    private boolean confirmUnchangedRoomStateAfterCommand(List<String> command) {
        Room roomBeforeCommand = new Room(game.getGameEngine().getCurrentRoom());

        game.getGameEngine().executeCommand(command);

        return roomBeforeCommand.equals(game.getGameEngine().getCurrentRoom());
    }

    // This function makes the tests for room travelling more readable and concise
    private void movePlayerThroughTimeMachine() {
        // Completes trade for icard (reward item of trade) that is required to go through time machine
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade", "battery")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "east")));
    }

    // Tests for Empty List of Commands
    @Test
    public void testExecuteCommandEmptyListForEmptyResponseMessage() {
        assert(game.getGameEngine().executeCommand(new ArrayList<>()).isEmpty());
    }

    @Test
    public void testExecuteCommandEmptyListForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>()));
    }

    @Test
    public void testExecuteCommandEmptyListForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>()));
    }

    // Tests for "examine" command
    @Test
    public void testExecuteCommandExamineWithExtraInputForResponseMessage() {
        assertEquals("The entered command is not supported.",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("examine", "room"))).get(0));
    }

    @Test
    public void testExecuteCommandExamineWithExtraInputForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("examine", "room"))));
    }

    @Test
    public void testExecuteCommandExamineWithExtraInputForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("examine", "room"))));
    }

    @Test
    public void testExecuteCommandExamineForResponseMessage() {
        assertEquals(game.getGameEngine().getCurrentRoom().toString(),
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("examine"))).get(0));
    }

    @Test
    public void testExecuteCommandExamineForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("examine"))));
    }

    @Test
    public void testExecuteCommandExamineForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("examine"))));
    }

    // Tests for "exit" command
    @Test
    public void testExecuteCommandExitWithExtraInputForResponseMessage() {
        assertEquals("The entered command is not supported.",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("exit", "room"))).get(0));
    }

    @Test
    public void testExecuteCommandExitWithExtraInputForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("exit", "room"))));
    }

    @Test
    public void testExecuteCommandExitWithExtraInputForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("exit", "room"))));
    }

    @Test
    public void testExecuteCommandExitForResponseMessage() {
        assertEquals("The game will now terminate.",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("exit"))).get(0));
    }

    @Test
    public void testExecuteCommandExitForUpdatingGameEngine() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("exit")));

        assert(game.getGameEngine().isGameFinished());
    }

    // Tests for "quit" command
    @Test
    public void testExecuteCommandQuitWithExtraInputForResponseMessage() {
        assertEquals("The entered command is not supported.",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("quit", "game"))).get(0));
    }

    @Test
    public void testExecuteCommandQuitWithExtraInputForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("quit", "game"))));
    }

    @Test
    public void testExecuteCommandQuitWithExtraInputForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("quit", "game"))));
    }

    @Test
    public void testExecuteCommandQuitForResponseMessage() {
        assertEquals("The game will now terminate.",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("quit"))).get(0));
    }

    @Test
    public void testExecuteCommandQuitForUpdatingGameEngine() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("quit")));

        assert(game.getGameEngine().isGameFinished());
    }

    // Tests for "take [insert item name]" command
    @Test
    public void testExecuteCommandTakeItemNoSecondInputForResponseMessage() {
        assertEquals("The entered command is not supported.",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take"))).get(0));
    }

    @Test
    public void testExecuteCommandTakeItemNoSecondInputForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("take"))));
    }

    @Test
    public void testExecuteCommandTakeItemNoSecondInputForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("take"))));
    }

    @Test
    public void testExecuteCommandTakeItemNotInRoomForResponseMessage() {
        assertEquals("There is no nonsense in the room",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "nonsense"))).get(0));
    }

    @Test
    public void testExecuteCommandTakeItemNotInRoomForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("take", "nonsense"))));
    }

    @Test
    public void testExecuteCommandTakeItemNotInRoomForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("take", "nonsense"))));
    }

    @Test
    public void testExecuteCommandTakeItemFromRoomWithNoItemsLeftForResponseMessage() {
        // Take all items in first room
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        assertEquals("There is no battery in the room",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery"))).get(0));
    }

    @Test
    public void testExecuteCommandTakeItemFromRoomWithNoItemsLeftForUnchangedInventory() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("take", "battery"))));
    }

    @Test
    public void testExecuteCommandTakeItemFromRoomWithNoItemsLeftForUnchangedRoomState() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("take", "battery"))));
    }

    @Test
    public void testExecuteCommandTakeItemInRoomForResponseMessage() {
        assertEquals("* battery has been added to inventory *",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery"))).get(0));
    }

    @Test
    public void testExecuteCommandTakeItemInRoomForUpdatedInventory() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        assert(game.getGameEngine().getPlayerInventory().get(0).getItemName().equals("battery"));
    }

    @Test
    public void testExecuteCommandTakeItemInRoomForUpdatedRoomState() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        // There should be one more battery item and one piece of gum item left in the room based on source file
        assert(game.getGameEngine().getCurrentRoom().getVisibleItems().size() == 2);
    }

    @Test
    public void testExecuteCommandTakeDuplicateItemInRoomForResponseMessage() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        assertEquals("* battery has been added to inventory *",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery"))).get(0));
    }

    @Test
    public void testExecuteCommandTakeDuplicateItemInRoomForUpdatedInventory() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        assert(game.getGameEngine().getPlayerInventory().get(0).getItemName().equals("battery") &&
               game.getGameEngine().getPlayerInventory().get(1).getItemName().equals("battery"));
    }

    @Test
    public void testExecuteCommandTakeDuplicateItemInRoomForUpdatedRoomState() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        // There should be one piece of gum item left in the room based on source file
        assert(game.getGameEngine().getCurrentRoom().getVisibleItems().size() == 1);
    }

    // Tests for "drop [insert item name]" command
    @Test
    public void testExecuteCommandDropItemNoSecondInputForResponseMessage() {
        assertEquals("The entered command is not supported.",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("drop"))).get(0));
    }

    @Test
    public void testExecuteCommandDropItemNoSecondInputForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("drop"))));
    }

    @Test
    public void testExecuteCommandDropItemNoSecondInputForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("drop"))));
    }

    @Test
    public void testExecuteCommandDropItemNotInPlayerInventoryForResponseMessage() {
        assertEquals("You don't have \"nonsense\"!",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("drop", "nonsense"))).get(0));
    }

    @Test
    public void testExecuteCommandDropItemNotInPlayerInventoryForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("drop", "nonsense"))));
    }

    @Test
    public void testExecuteCommandDropItemNotInPlayerInventoryForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("drop", "nonsense"))));
    }

    @Test
    public void testExecuteCommandDropItemInPlayerInventoryForResponseMessage() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        assertEquals("* chewing gum has been dropped from inventory *",
                     game.getGameEngine()
                         .executeCommand(new ArrayList<>(Arrays.asList("drop", "chewing gum")))
                         .get(0));
    }

    @Test
    public void testExecuteCommandDropItemInPlayerInventoryForUpdatedInventory() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("drop", "chewing gum")));

        // Taking and dropping the same item should leave the player inventory empty
        assert(game.getGameEngine().getPlayerInventory().isEmpty());
    }

    @Test
    public void testExecuteCommandDropItemInPlayerInventoryForUpdatedRoomState() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("drop", "chewing gum")));

        // Dropping the chewing gum taking it should result in the room having 3 items as it did at the start
        assert(game.getGameEngine().getCurrentRoom().getVisibleItems().size() == 3);
    }

    @Test
    public void testExecuteCommandDropItemInPlayerInventoryWithMultipleItemsForResponseMessage() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        assertEquals("* chewing gum has been dropped from inventory *",
                     game.getGameEngine()
                         .executeCommand(new ArrayList<>(Arrays.asList("drop", "chewing gum")))
                         .get(0));
    }

    @Test
    public void testExecuteCommandDropItemInPlayerInventoryWithMultipleItemsForUpdatedInventory() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("drop", "chewing gum")));

        // Taking and dropping the same item should leave the player inventory empty
        assert(game.getGameEngine().getPlayerInventory().size() == 1);
    }

    @Test
    public void testExecuteCommandDropItemInPlayerInventoryWithMultipleItemsForUpdatedRoomState() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("drop", "chewing gum")));

        // Dropping the chewing gum taking it should result in the room having 3 items as it did at the start
        assert(game.getGameEngine().getCurrentRoom().getVisibleItems().size() == 2);
    }

    // Dropping a duplicate item denotes dropping an item in a room that already has an item with the same name
    @Test
    public void testExecuteCommandDropDuplicateItemInRoomForResponseMessage() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        assertEquals("* battery has been dropped from inventory *",
                     game.getGameEngine()
                         .executeCommand(new ArrayList<>(Arrays.asList("drop", "battery")))
                         .get(0));
    }

    @Test
    public void testExecuteCommandDropDuplicateItemInRoomForUpdatedInventory() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("drop", "battery")));

        assert(game.getGameEngine().getPlayerInventory().isEmpty());
    }

    @Test
    public void testExecuteCommandDropDuplicateItemInRoomForUpdatedRoomState() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("drop", "battery")));

        assert(game.getGameEngine().getCurrentRoom().getVisibleItems().get(0).getItemName().equals("battery") &&
               game.getGameEngine().getCurrentRoom().getVisibleItems().get(2).getItemName().equals("battery"));
    }

    // Tests for "inventory" command, which returns the items a player currently has in their inventory
    @Test
    public void testExecuteCommandInventoryWithNoItemsForResponseMessage() {
        assertEquals(new ArrayList<>(Arrays.asList("items: ")),
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("inventory"))));
    }

    @Test
    public void testExecuteCommandInventoryWithNoItemsForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("inventory"))));
    }

    @Test
    public void testExecuteCommandInventoryWithNoItemsForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("inventory"))));
    }

    public void testExecuteCommandInventoryWithOneItemForResponseMessage() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        assertEquals(new ArrayList<>(Arrays.asList("items: chewing gum")),
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("inventory"))));
    }

    @Test
    public void testExecuteCommandInventoryWithOneItemForUnchangedInventory() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("inventory"))));
    }

    @Test
    public void testExecuteCommandInventoryWithOneItemForUnchangedRoomState() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("inventory"))));
    }

    public void testExecuteCommandInventoryWithMultipleItemsForResponseMessage() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        assertEquals(new ArrayList<>(Arrays.asList("items: chewing gum, battery")),
                game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("inventory"))));
    }

    @Test
    public void testExecuteCommandInventoryWithMultipleItemsForUnchangedInventory() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("inventory"))));
    }

    @Test
    public void testExecuteCommandInventoryWithMultipleItemsForUnchangedRoomState() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("inventory"))));
    }

    // Tests for "inspect [insert item name]" command
    @Test
    public void testExecuteCommandInspectItemNoSecondInputForResponseMessage() {
        assertEquals("The entered command is not supported.",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("inspect"))).get(0));
    }

    @Test
    public void testExecuteCommandInspectItemNoSecondInputForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("inspect"))));
    }

    @Test
    public void testExecuteCommandInspectItemNoSecondInputForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("inspect"))));
    }

    @Test
    public void testExecuteCommandInspectItemNotInPlayerInventoryForResponseMessage() {
        assertEquals("You don't have \"nonsense\"!",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("inspect", "nonsense"))).get(0));
    }

    @Test
    public void testExecuteCommandInspectItemNotInPlayerInventoryForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("inspect", "nonsense"))));
    }

    @Test
    public void testExecuteCommandInspectItemNotInPlayerInventoryForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("inspect", "nonsense"))));
    }

    @Test
    public void testExecuteCommandInspectItemInPlayerInventoryForResponseMessage() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        assertEquals("This seems to be a used piece of gum. Why did I pick this up again?",
                     game.getGameEngine()
                         .executeCommand(new ArrayList<>(Arrays.asList("inspect", "chewing gum")))
                         .get(0));
    }

    @Test
    public void testExecuteCommandInspectItemInPlayerInventoryForUnchangedInventory() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("inspect", "chewing gum"))));
    }

    @Test
    public void testExecuteCommandInspectItemInPlayerInventoryForUnchangedRoomState() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("inspect", "chewing gum"))));
    }

    @Test
    public void testExecuteCommandInspectItemInPlayerInventoryWithMultipleItemsForResponseMessage() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        assertEquals("This seems to be a used piece of gum. Why did I pick this up again?",
                     game.getGameEngine()
                         .executeCommand(new ArrayList<>(Arrays.asList("inspect", "chewing gum")))
                         .get(0));
    }

    @Test
    public void testExecuteCommandInspectItemInPlayerInventoryWithMultipleItemsForUnchangedInventory() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("inspect", "chewing gum"))));
    }

    @Test
    public void testExecuteCommandInspectItemInPlayerInventoryWithMultipleItemsForUnchangedRoomState() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("inspect", "chewing gum"))));
    }

    // Tests for "trade" command, which returns the given characters item request dialogue
    @Test
    public void testExecuteCommandTradeForResponseMessage() {
        assertEquals(game.getGameEngine().getCurrentRoom().getNonPlayerCharacter().getItemRequestDialogue(),
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade"))));
    }

    @Test
    public void testExecuteCommandTradeForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("trade"))));
    }

    @Test
    public void testExecuteCommandTradeForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("trade"))));
    }

    // Tests for "trade [insert item name]" command
    @Test
    public void testExecuteCommandTradeItemNotInPlayerInventoryForResponseMessage() {
        assertEquals("You don't have \"nonsense\"!",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade", "nonsense"))).get(0));
    }

    @Test
    public void testExecuteCommandTradeItemNotInPlayerInventoryForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("trade", "nonsense"))));
    }

    @Test
    public void testExecuteCommandTradeItemNotInPlayerInventoryForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("trade", "nonsense"))));
    }

    @Test
    public void testExecuteCommandTradeWrongItemInPlayerInventoryForResponseMessage() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));
        assertEquals("Sorry, that is not the item I was looking for.",
                     game.getGameEngine()
                         .executeCommand(new ArrayList<>(Arrays.asList("trade", "chewing gum")))
                         .get(0));
    }

    @Test
    public void testExecuteCommandTradeWrongItemInPlayerInventoryForUnchangedInventory() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("trade", "chewing gum"))));
    }

    @Test
    public void testExecuteCommandTradeWrongItemInPlayerInventoryForUnchangedRoomState() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "chewing gum")));

        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("trade", "chewing gum"))));
    }

    @Test
    public void testExecuteCommandTradeCorrectItemInPlayerInventoryForResponseMessage() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        List<String> expectedResponseMessage = game.getGameEngine()
                                                   .getCurrentRoom()
                                                   .getNonPlayerCharacter()
                                                   .getRequestCompletedDialogue();

        expectedResponseMessage.add("* icard has been added to inventory *");

        assertEquals(expectedResponseMessage,
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade", "battery"))));
    }

    @Test
    public void testExecuteCommandTradeCorrectItemInPlayerInventoryForUpdatedInventory() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade", "battery")));

        assert(game.getGameEngine().getPlayerInventory().get(0).getItemName().equals("icard") &&
               game.getGameEngine().getPlayerInventory().size() == 1);
    }

    @Test
    public void testExecuteCommandTradeCorrectItemInPlayerInventoryForUnchangedRoomState() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("trade", "battery"))));
    }

    @Test
    public void testExecuteCommandTradeMultipleCorrectItemsInPlayerInventoryForResponseMessage() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade", "battery")));

        List<String> expectedResponseMessage = game.getGameEngine()
                                                   .getCurrentRoom()
                                                   .getNonPlayerCharacter()
                                                   .getRequestCompletedDialogue();

        expectedResponseMessage.add("* icard has been added to inventory *");

        assertEquals(expectedResponseMessage,
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade", "battery"))));
    }

    @Test
    public void testExecuteCommandTradeMultipleCorrectItemsInPlayerInventoryForUpdatedInventory() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade", "battery")));

        assert(game.getGameEngine().getPlayerInventory().get(0).getItemName().equals("icard") &&
               game.getGameEngine().getPlayerInventory().get(1).getItemName().equals("icard") &&
               game.getGameEngine().getPlayerInventory().size() == 2);
    }

    @Test
    public void testExecuteCommandTradeMultipleCorrectItemsInPlayerInventoryForUnchangedRoomState() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade", "battery")));

        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("trade", "battery"))));
    }

    // Tests for "go [insert room name]" command
    @Test
    public void testExecuteCommandGoToRoomInvalidDirectionForResponseMessage() {
        assertEquals("I can't go \"Oward\"!",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "o"))).get(0));
    }

    @Test
    public void testExecuteCommandGoToRoomInvalidDirectionForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("go", "o"))));
    }

    @Test
    public void testExecuteCommandGoToRoomInvalidDirectionForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("go", "o"))));
    }

    // Tests for valid direction with a room name that has no associated data in the game JSON file
    @Test
    public void testExecuteCommandGoToRoomNotInGameFileForResponseMessage() {
        assertEquals("Game file does not contain the required data for the associated room name.",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "south"))).get(0));
    }

    @Test
    public void testExecuteCommandGoToRoomNotInGameFileForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("go", "south"))));
    }

    @Test
    public void testExecuteCommandGoToRoomNotInGameFileForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("go", "south"))));
    }

    @Test
    public void testExecuteCommandGoToRoomWithNullNameForResponseMessage() {
        assertEquals("Can not enter room with null name.",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "west"))).get(0));
    }

    @Test
    public void testExecuteCommandGoToRoomWithNullNameForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("go", "west"))));
    }

    @Test
    public void testExecuteCommandGoToRoomWithNullNameForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("go", "west"))));
    }

    @Test
    public void testExecuteCommandGoToRoomWithoutRequiredItemInInventoryForResponseMessage() {
        assertEquals("You do not have the needed item(s) to proceed in this direction.",
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "east"))).get(0));
    }

    @Test
    public void testExecuteCommandGoToRoomWithoutRequiredItemInInventoryForUnchangedInventory() {
        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("go", "east"))));
    }

    @Test
    public void testExecuteCommandGoToRoomWithoutRequiredItemInInventoryForUnchangedRoomState() {
        assert(confirmUnchangedRoomStateAfterCommand(new ArrayList<>(Arrays.asList("go", "east"))));
    }

    @Test
    public void testExecuteCommandGoToRoomWithRequiredItemInInventoryForResponseMessage() {
        // Trade must occur to obtain icard to move eastward
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade", "battery")));

        assertEquals(game.getGameEngine().getGameProperties().retrieveRoom("DestroyedFuture").toString(),
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "east"))).get(0));
    }

    @Test
    public void testExecuteCommandGoToRoomWithRequiredItemInInventoryForUnchangedInventory() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade", "battery")));

        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("go", "east"))));
    }

    @Test
    public void testExecuteCommandGoToRoomWithRequiredItemInInventoryForUpdatedRoom() {
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "battery")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade", "battery")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "east")));

        assertEquals("DestroyedFuture", game.getGameEngine().getCurrentRoom().getName());
    }

    // Tests for going in a direction that has an null/empty list of required items to progress
    @Test
    public void testExecuteCommandGoToRoomWithNullListOfRequiredItemsForResponseMessage() {
        movePlayerThroughTimeMachine();

        assertEquals(game.getGameEngine().getGameProperties().retrieveRoom("VikingEra").toString(),
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "east"))).get(0));
    }

    @Test
    public void testExecuteCommandGoToRoomWithNullListOfRequiredItemsForUnchangedInventory() {
        movePlayerThroughTimeMachine();

        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("go", "east"))));
    }

    @Test
    public void testExecuteCommandGoToRoomWithNullListOfRequiredItemsForUpdatedRoom() {
        movePlayerThroughTimeMachine();

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "east")));

        assertEquals("VikingEra", game.getGameEngine().getCurrentRoom().getName());
    }

    @Test
    public void testExecuteCommandGoToRoomWithEmptyListOfRequiredItemsForResponseMessage() {
        movePlayerThroughTimeMachine();

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "east")));

        assertEquals(game.getGameEngine().getGameProperties().retrieveRoom("DestroyedFuture").toString(),
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "west"))).get(0));
    }

    @Test
    public void testExecuteCommandGoToRoomWithEmptyListOfRequiredItemsForUnchangedInventory() {
        movePlayerThroughTimeMachine();

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "east")));

        assert(confirmUnchangedInventoryAfterCommand(new ArrayList<>(Arrays.asList("go", "west"))));
    }

    @Test
    public void testExecuteCommandGoToRoomWithEmptyListOfRequiredItemsForUpdatedRoom() {
        movePlayerThroughTimeMachine();

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "east")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "west")));

        assertEquals("DestroyedFuture", game.getGameEngine().getCurrentRoom().getName());
    }

    @Test
    public void testExecuteCommandGoToRoomWhenReturningToPreviousRoomForUnchangedRoomState() {
        movePlayerThroughTimeMachine();

        // Drop and take items to test whether room will revert to its default state when player returns
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "glasses")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("drop", "icard")));

        Room roomBeforeReturning = new Room(game.getGameEngine().getCurrentRoom());

        // Player leaves room and then returns
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "east")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "west")));

        assert(roomBeforeReturning.equals(game.getGameEngine().getCurrentRoom()));
    }

    // Tests for reaching ending room to ensure that the game is completed and the proper response message is generated
    @Test
    public void testExecuteCommandGoToEndingRoomForResponseMessage() {
        movePlayerThroughTimeMachine();

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "protective amulet")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "east")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade", "protective amulet")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "west")));

        assertEquals(game.getGameEngine().getGameProperties().retrieveRoom("RestoredFuture").toString(),
                     game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "north"))).get(0));
    }

    @Test
    public void testExecuteCommandGoToEndingRoomForUpdatingGameFinished() {
        movePlayerThroughTimeMachine();

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("take", "protective amulet")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "east")));
        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("trade", "protective amulet")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "west")));

        game.getGameEngine().executeCommand(new ArrayList<>(Arrays.asList("go", "north")));

        assert(game.getGameEngine().isGameFinished());
    }
}

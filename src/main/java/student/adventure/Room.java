package student.adventure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class denoting a location in the game, which will contain items and players to interact with.
 */
public class Room {
    private String name;
    private List<String> description;
    private List<Item> visibleItems;
    private NonPlayerCharacter nonPlayerCharacter;
    private List<Direction> directions;

    public Room() { }

    public Room(Room roomToCopy) {
        name = roomToCopy.getName();
        description = roomToCopy.getDescription();
        visibleItems = roomToCopy.getVisibleItems();
        nonPlayerCharacter = roomToCopy.getNonPlayerCharacter();
        directions = roomToCopy.getDirections();
    }

    /**
     * Returns Item object matching the provided item name and removes corresponding item from Room's visible items
     * @param itemName Name of item to be taken out of room's visible items
     * @return The item matching the given itemName or null if there are no items with a matching name
     */
    public Item takeItem(String itemName) {
        List<Item> matchingItems = getVisibleItems().stream()
                                                    .filter(item -> item.getItemName().equals(itemName))
                                                    .collect(Collectors.toList());

        if (matchingItems.isEmpty()) {
            return null;
        }

        Item matchingItem = matchingItems.get(0);
        visibleItems.remove(matchingItem);

        return matchingItem;
    }

    /**
     * Adds given Item object to the room's visible items, used for "drop" command
     * @param itemToAdd
     */
    public void addItem(Item itemToAdd) {
        visibleItems.add(itemToAdd);
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        if (description == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(description);
    }

    public List<Item> getVisibleItems() {
        if (visibleItems == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(visibleItems);
    }

    public NonPlayerCharacter getNonPlayerCharacter() {
        return nonPlayerCharacter;
    }

    public List<Direction> getDirections() {
        if (directions == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(directions);
    }

    @Override
    public String toString() {
        if (name == null && description == null) {
            return null;
        }

        NonPlayerCharacter roomCharacter = nonPlayerCharacter;
        if (roomCharacter == null) {
            roomCharacter = new NonPlayerCharacter();
        }


        String extendedRoomDescription = String.join("\n", getDescription().stream()
                                                                                   .filter(line -> line != null &&
                                                                                                   !line.equals(""))
                                                                                   .collect(Collectors.toList()));

        List<String> directionNames = getDirections().stream()
                                                     .filter(direction -> direction != null &&
                                                                          !direction.getDirectionName().equals(""))
                                                     .map(Direction::getDirectionName)
                                                     .collect(Collectors.toList());

        List<String> itemNames = getVisibleItems().stream()
                                                  .filter(item -> item != null &&
                                                          !item.getItemName().equals(""))
                                                  .map(Item::getItemName)
                                                  .collect(Collectors.toList());

        extendedRoomDescription += "\nNon-Player Character: " + roomCharacter.getCharacterName() +
                                   "\nFrom here, you can go: " + String.join(", ", directionNames) +
                                   "\nItems visible: " + String.join(", ", itemNames);

        return extendedRoomDescription;
    }

    // Method used to check if the state of one room is the same as another
    // This also includes Objects contained in the room like items, available directions, and characters
    // This will primarily be used in testing to make sure that a room's state has not been changed
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        Room otherRoom;

        try {
            otherRoom = (Room) other;
        } catch(Exception e) {
            return false;
        }

        return toString().equals(otherRoom.toString());
    }
}

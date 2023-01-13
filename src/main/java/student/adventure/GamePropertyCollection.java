package student.adventure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class containing the properties of the given game, including the game rooms and the start/end rooms.
 */
public class GamePropertyCollection {
    private String startingRoomName;
    private String endingRoomName;
    private List<Room> rooms;

    public String getStartingRoomName() {
        return startingRoomName;
    }

    public String getEndingRoomName() {
        return endingRoomName;
    }

    public List<Room> getRooms() {
        if (rooms == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(rooms);
    }

    /**
     * Retrieves a game room corresponding to the provided room name, returns null if no room was found
     * @param roomName Name corresponding to room that needs to be accessed
     * @return Room object corresponding to the provided name, returns null if no object with matching name was found
     */
    public Room retrieveRoom(String roomName) {
        if (roomName == null || rooms == null) {
            return null;
        }

        List<Room> matchingRooms = rooms.stream()
                                        .filter(room -> room.getName().equals(roomName))
                                        .collect(Collectors.toList());

        if (matchingRooms.isEmpty()) {
            return null;
        }

        return matchingRooms.get(0);
    }
}

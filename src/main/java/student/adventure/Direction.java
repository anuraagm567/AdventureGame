package student.adventure;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a direction that can be travelled in game.
 * Includes information about the subsequent room and items needed to travel.
 */
public class Direction {
    private String directionName;
    private String room;
    private List<Item> itemsNeededToProgress;

    public String getDirectionName() {
        if (directionName == null) {
            return "";
        }

        return directionName;
    }

    public String getRoom() {
        return room;
    }

    public List<Item> getItemsNeededToProgress() {
        if (itemsNeededToProgress == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(itemsNeededToProgress);
    }
}

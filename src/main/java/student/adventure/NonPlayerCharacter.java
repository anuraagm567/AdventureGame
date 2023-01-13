package student.adventure;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that denotes a character that the player can interact and trade with.
 */
public class NonPlayerCharacter {
    private String characterName;
    private List<String> itemRequestDialogue;
    private List<String> requestCompletedDialogue;
    private Item missingItem;
    private Item rewardItem;

    public String getCharacterName() {
        if (characterName == null) {
            return "";
        }

        return characterName;
    }

    public List<String> getItemRequestDialogue() {
        if (itemRequestDialogue == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(itemRequestDialogue);
    }

    public List<String> getRequestCompletedDialogue() {
        if (requestCompletedDialogue == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(requestCompletedDialogue);
    }

    public Item getMissingItem() {
        return missingItem;
    }

    public Item getRewardItem() {
        return rewardItem;
    }
}

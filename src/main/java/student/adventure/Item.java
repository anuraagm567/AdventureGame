package student.adventure;

/**
 * Class denoting an game item, which can be used to travel through rooms and trade with other characters.
 */
public class Item {
    private String itemName;
    private String itemDescription;

    public String getItemName() {
        if (itemName == null) {
            return "";
        }

        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        Item otherItem;

        try {
            otherItem = (Item) other;
        } catch(Exception e) {
            return false;
        }

        return getItemName().equals(otherItem.getItemName()) &&
               getItemDescription().equals(otherItem.getItemDescription());
    }
}

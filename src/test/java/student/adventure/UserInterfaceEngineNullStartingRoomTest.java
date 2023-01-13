package student.adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import java.io.FileNotFoundException;

/**
 * Testing class for user interface engine's handling of null starting Room
 */
public class UserInterfaceEngineNullStartingRoomTest {
    @Test (expected = IllegalArgumentException.class)
    public void testResponseMessageForNullStartingRoom() throws FileNotFoundException {
        try {
            String filePath = "src/main/resources/time_tangled_island_null_starting_room.json";
            UserInterfaceEngine interfaceEngine = new UserInterfaceEngine(filePath);

            interfaceEngine.start();

            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException argumentException){
            assertEquals("Unable to load starting room.", argumentException.getMessage());

            throw argumentException;
        }
    }
}

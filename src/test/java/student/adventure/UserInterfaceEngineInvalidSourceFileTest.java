package student.adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.google.gson.JsonParseException;
import org.junit.Test;
import java.io.FileNotFoundException;

/**
 * Testing file for User Interface Engine when initialized using nonexistent/incompatible source files
 */
public class UserInterfaceEngineInvalidSourceFileTest {
    @Test (expected = FileNotFoundException.class)
    public void testUserInterfaceEngineConstructorNonexistentFile() throws FileNotFoundException {
        try {
            new UserInterfaceEngine("src/main/resources/.json");

            fail("FileNotFoundException not thrown");
        } catch (FileNotFoundException invalidFileException) {
            assertEquals("File does not exist or could not be read.", invalidFileException.getMessage());

            throw invalidFileException;
        }
    }

    @Test (expected = JsonParseException.class)
    public void testUserInterfaceEngineConstructorIncompatibleJsonSchema() throws FileNotFoundException {
        try {
            new UserInterfaceEngine("src/main/resources/invalid.json");

            fail("JsonParseException not thrown");
        } catch (JsonParseException parsingException) {
            assertEquals("JSON file does not fit required schema.", parsingException.getMessage());

            throw parsingException;
        }
    }
}
package student.adventure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Testing file for User Interface Engine, specifically to check String processing of user commands
 */
public class UserInterfaceEngineTest {
    @Test
    public void testOrganizeUserCommandEmptyInput() {
        assert(UserInterfaceEngine.organizeUserCommand("").isEmpty());
    }

    // Tests accounting for spaces
    @Test
    public void testOrganizeUserCommandSingleSpace() {
        assert(UserInterfaceEngine.organizeUserCommand(" ").isEmpty());
    }

    @Test
    public void testOrganizeUserCommandOnlySpaces() {
        assert(UserInterfaceEngine.organizeUserCommand("      ").isEmpty());
    }

    @Test
    public void testOrganizeUserCommandOneLetterFollowedBySpaces() {
        assertEquals(new ArrayList<>(Arrays.asList("a")),
                     UserInterfaceEngine.organizeUserCommand("a       "));
    }

    @Test
    public void testOrganizeUserCommandMultipleLettersFollowedBySpaces() {
        assertEquals(new ArrayList<>(Arrays.asList("code")),
                UserInterfaceEngine.organizeUserCommand("code         "));
    }

    @Test
    public void testOrganizeUserCommandWhiteSpace() {
        assertEquals(new ArrayList<>(Arrays.asList("code", "review")),
                     UserInterfaceEngine.organizeUserCommand("  code     review   "));
    }

    @Test
    public void testOrganizeUserCommandSpaceInSecondInput() {
        assertEquals(new ArrayList<>(Arrays.asList("code", "review   day")),
                     UserInterfaceEngine.organizeUserCommand("  code     review   day"));
    }

    // Tests for capitalization
    @Test
    public void testOrganizeUserCommandVariedCapitalization() {
        assertEquals(new ArrayList<>(Arrays.asList("fresh", "strawberry")),
                     UserInterfaceEngine.organizeUserCommand("fReSH sTrawBERry"));
    }

    @Test
    public void testOrganizeUserCommandWhiteSpaceAndVariedCapitalization() {
        assertEquals(new ArrayList<>(Arrays.asList("purple", "potatoes")),
                     UserInterfaceEngine.organizeUserCommand("  PurPLe     potaTOeS "));
    }

    // Tests for one letter commands
    @Test
    public void testOrganizeUserCommandOneLetterStrings() {
        assertEquals(new ArrayList<>(Arrays.asList(".", ".")),
                     UserInterfaceEngine.organizeUserCommand(". ."));
    }

    @Test
    public void testOrganizeUserCommandOneLetterStringsWhiteSpace() {
        assertEquals(new ArrayList<>(Arrays.asList("$", "t")),
                     UserInterfaceEngine.organizeUserCommand("  $     t     "));
    }

    @Test
    public void testOrganizeUserCommandOneLetterStringsWhiteSpaceAndVariedCapitalization() {
        assertEquals(new ArrayList<>(Arrays.asList("h", "p")),
                     UserInterfaceEngine.organizeUserCommand("       H    p "));
    }
}

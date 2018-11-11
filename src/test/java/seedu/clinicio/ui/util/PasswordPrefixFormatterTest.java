package seedu.clinicio.ui.util;

import static org.junit.Assert.assertEquals;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PASSWORD_ADAM;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;

import org.junit.Before;
import org.junit.Test;

import seedu.clinicio.logic.commands.LoginCommand;

public class PasswordPrefixFormatterTest {

    private static final String WHITESPACE = " ";

    private PasswordPrefixFormatter formatter;

    @Before
    public void setUp() {
        formatter = new PasswordPrefixFormatter();
    }

    @Test
    public void maskPassword() {
        String text = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + VALID_PASSWORD_ADAM;
        String expectedText = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + "-------2";

        // IsHistory -> false, IsBackspace -> false
        assertEquals(expectedText, formatter.maskPassword(text, false, false) + "2");

        // IsHistory -> false, IsBackspace -> true
        text = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + VALID_PASSWORD_ADAM;
        expectedText = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + "------1";

        assertEquals(expectedText, formatter.maskPassword(text, false, true));

        // IsHistory -> true, IsBackspace -> false
        assertEquals(expectedText, formatter.maskPassword(text, true, false));
    }

    @Test
    public void unmaskPassword() {
        // Password prefix is on the last one
        String text = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + "doctor";
        String expectedText = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + VALID_PASSWORD_ADAM;
        String formattedText = formatter.maskPassword(text, false, false) + "1";

        assertEquals(expectedText, formatter.unmaskPassword(formattedText));

        // Password prefix is before role and after name.
        text = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + "doctor1"
                + WHITESPACE + PREFIX_ROLE + "doctor";
        expectedText = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + VALID_PASSWORD_ADAM
                + WHITESPACE + PREFIX_ROLE + "doctor";
        formattedText = formatter.maskPassword(text, false, false);

        assertEquals(expectedText, formatter.unmaskPassword(formattedText));

        // Password prefix is the first one
        text = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_PASSWORD + "doctor1"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_ROLE + "doctor";
        expectedText = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_PASSWORD + VALID_PASSWORD_ADAM
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_ROLE + "doctor";
        formattedText = formatter.maskPassword(text, false, false);

        assertEquals(expectedText, formatter.unmaskPassword(formattedText));
    }
}

package seedu.clinicio.logic.parser;

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PASSWORD_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PASSWORD_ALAN;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ALAN;

import org.junit.Test;

import seedu.clinicio.logic.commands.LoginCommand;

import seedu.clinicio.model.staff.Staff;

import seedu.clinicio.testutil.StaffBuilder;

//@@author jjlee050
public class LoginCommandParserTest {

    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsLoginCommand() {
        // For doctor
        // no leading and trailing whitespaces
        Staff expectedStaff = new StaffBuilder(ADAM).withPassword(VALID_PASSWORD_ADAM, false).build();
        LoginCommand expectedLoginCommand = new LoginCommand(expectedStaff);
        assertParseSuccess(parser, " r/doctor n/" + ADAM.getName()
                + " pass/" + VALID_PASSWORD_ADAM, expectedLoginCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n r/doctor \n \t n/" + ADAM.getName() + " \t pass/"
                + VALID_PASSWORD_ADAM, expectedLoginCommand);

        // For receptionist
        // no leading and trailing whitespaces
        expectedStaff = new StaffBuilder(ALAN).withPassword(VALID_PASSWORD_ALAN, false).build();
        expectedLoginCommand = new LoginCommand(expectedStaff);
        assertParseSuccess(parser, " r/receptionist n/" + ALAN.getName()
                + " pass/" + VALID_PASSWORD_ALAN, expectedLoginCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n r/receptionist \n \t n/" + ALAN.getName() + " \t pass/"
                + VALID_PASSWORD_ALAN, expectedLoginCommand);
    }
}

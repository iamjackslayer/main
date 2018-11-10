package seedu.clinicio.logic.parser;

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinicio.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.clinicio.logic.commands.CancelApptCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CancelApptCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the CancelApptCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class CancelApptCommandParserTest {
    private CancelApptCommandParser parser = new CancelApptCommandParser();

    @Test
    public void parse_validArgs_returnsCancelApptCommand() {
        assertParseSuccess(parser, "1", new CancelApptCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelApptCommand.MESSAGE_USAGE));
    }
}

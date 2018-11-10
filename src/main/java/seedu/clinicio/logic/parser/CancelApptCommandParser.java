package seedu.clinicio.logic.parser;

//@@gingivitiss

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.logic.commands.CancelApptCommand;
import seedu.clinicio.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CancelApptCommand object.
 */
public class CancelApptCommandParser implements Parser<CancelApptCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CancelApptCommand
     * and returns an CancelApptCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CancelApptCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CancelApptCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelApptCommand.MESSAGE_USAGE), pe);
        }
    }
}

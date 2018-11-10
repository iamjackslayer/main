package seedu.clinicio.logic.parser;

//@@author gingivitiss

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.clinicio.logic.commands.ListApptCommand;
import seedu.clinicio.logic.parser.exceptions.ParseException;
import seedu.clinicio.model.appointment.AppointmentContainsDatePredicate;

/**
 * Parses inputs and creates a new ListApptCommand object.
 */
public class ListApptCommandParser implements Parser<ListApptCommand> {

    /**
     * Parses the given {@code String} in the context of ListApptCommand and returns
     * a ListApptCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ListApptCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListApptCommand.MESSAGE_USAGE));
        }

        String[] date = trimmedArgs.split("\\s");

        if (date.length < 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListApptCommand.MESSAGE_USAGE));
        }

        return new ListApptCommand(new AppointmentContainsDatePredicate(date));
    }
}

package seedu.clinicio.logic.parser;

//@@author aaronseahyh

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.logic.commands.SelectMedicineCommand;
import seedu.clinicio.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectMedicineCommand object
 */
public class SelectMedicineCommandParser implements Parser<SelectMedicineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectMedicineCommand
     * and returns an SelectMedicineCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectMedicineCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectMedicineCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectMedicineCommand.MESSAGE_USAGE), pe);
        }
    }
}

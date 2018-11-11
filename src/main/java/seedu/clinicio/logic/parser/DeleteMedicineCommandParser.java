package seedu.clinicio.logic.parser;

//@@author aaronseahyh

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.logic.commands.DeleteMedicineCommand;
import seedu.clinicio.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMedicineCommand object
 */
public class DeleteMedicineCommandParser implements Parser<DeleteMedicineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMedicineCommand
     * and returns a DeleteMedicineCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMedicineCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteMedicineCommand(index);
        } catch (ParseException parseException) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMedicineCommand.MESSAGE_USAGE), parseException);
        }
    }

}

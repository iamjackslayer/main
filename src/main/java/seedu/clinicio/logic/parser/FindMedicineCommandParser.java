package seedu.clinicio.logic.parser;

//@@author aaronseahyh

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.clinicio.logic.commands.FindMedicineCommand;
import seedu.clinicio.logic.parser.exceptions.ParseException;
import seedu.clinicio.model.medicine.MedicineNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindMedicineCommand object
 */
public class FindMedicineCommandParser implements Parser<FindMedicineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindMedicineCommand
     * and returns a FindMedicineCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindMedicineCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMedicineCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindMedicineCommand(new MedicineNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}

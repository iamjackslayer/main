package seedu.clinicio.logic.parser;

//@@author aaronseahyh

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_EFFECTIVE_DOSAGE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_LETHAL_DOSAGE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_PRICE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_QUANTITY;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_TYPE;

import java.util.stream.Stream;

import seedu.clinicio.logic.commands.AddMedicineCommand;
import seedu.clinicio.logic.parser.exceptions.ParseException;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.medicine.MedicineDosage;
import seedu.clinicio.model.medicine.MedicineName;
import seedu.clinicio.model.medicine.MedicinePrice;
import seedu.clinicio.model.medicine.MedicineQuantity;
import seedu.clinicio.model.medicine.MedicineType;

/**
 * Parses input arguments and creates a new AddMedicineCommand object
 */
public class AddMedicineCommandParser implements Parser<AddMedicineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMedicineCommand
     * and returns an AddMedicineCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMedicineCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEDICINE_NAME, PREFIX_MEDICINE_TYPE,
                        PREFIX_MEDICINE_EFFECTIVE_DOSAGE, PREFIX_MEDICINE_LETHAL_DOSAGE,
                        PREFIX_MEDICINE_PRICE, PREFIX_MEDICINE_QUANTITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEDICINE_NAME, PREFIX_MEDICINE_TYPE,
                PREFIX_MEDICINE_EFFECTIVE_DOSAGE, PREFIX_MEDICINE_LETHAL_DOSAGE,
                PREFIX_MEDICINE_PRICE, PREFIX_MEDICINE_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
        }

        MedicineName name = ParserUtil.parseMedicineName(argMultimap.getValue(PREFIX_MEDICINE_NAME).get());
        MedicineType type = ParserUtil.parseMedicineType(argMultimap.getValue(PREFIX_MEDICINE_TYPE).get());
        MedicineDosage effectiveDosage =
            ParserUtil.parseDosage(argMultimap.getValue(PREFIX_MEDICINE_EFFECTIVE_DOSAGE).get());
        MedicineDosage lethalDosage = ParserUtil.parseDosage(argMultimap.getValue(PREFIX_MEDICINE_LETHAL_DOSAGE).get());
        MedicinePrice price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_MEDICINE_PRICE).get());
        MedicineQuantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_MEDICINE_QUANTITY).get());

        Medicine medicine = new Medicine(name, type, effectiveDosage, lethalDosage, price, quantity);

        return new AddMedicineCommand(medicine);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

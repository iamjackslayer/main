package seedu.clinicio.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_TIME = new Prefix("tm/");
    public static final Prefix PREFIX_IC = new Prefix("ic/");
    public static final Prefix PREFIX_TYPE = new Prefix("tp/");

    /* Patient Prefix definitions */
    public static final Prefix PREFIX_MEDICAL_PROBLEM = new Prefix("medProb/");
    public static final Prefix PREFIX_MEDICATION = new Prefix("medList/");
    public static final Prefix PREFIX_ALLERGY = new Prefix("allergies/");
    public static final Prefix PREFIX_PREFERRED_DOCTOR = new Prefix("preferredDoc/");

    /* Login Prefix definitions */
    public static final Prefix PREFIX_ROLE = new Prefix("r/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("pass/");

    //@@author aaronseahyh
    public static final Prefix PREFIX_MEDICINE_NAME = new Prefix("mn/");
    public static final Prefix PREFIX_MEDICINE_TYPE = new Prefix("mt/");
    public static final Prefix PREFIX_MEDICINE_EFFECTIVE_DOSAGE = new Prefix("ed/");
    public static final Prefix PREFIX_MEDICINE_LETHAL_DOSAGE = new Prefix("ld/");
    public static final Prefix PREFIX_MEDICINE_PRICE = new Prefix("mp/");
    public static final Prefix PREFIX_MEDICINE_QUANTITY = new Prefix ("mq/");

}

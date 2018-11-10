package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import java.util.List;
import java.util.function.Predicate;

import seedu.clinicio.commons.util.StringUtil;

/**
 * Tests that a {@code Medicine}'s {@code MedicineName} matches any of the keywords given.
 */
public class MedicineNameContainsKeywordsPredicate implements Predicate<Medicine> {
    private final List<String> keywords;

    public MedicineNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Medicine medicine) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil
                        .containsWordIgnoreCase(medicine.getMedicineName().medicineName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicineNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((MedicineNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}

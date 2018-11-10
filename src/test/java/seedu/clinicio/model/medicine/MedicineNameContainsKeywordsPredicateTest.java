package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.clinicio.testutil.MedicineBuilder;

public class MedicineNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MedicineNameContainsKeywordsPredicate firstPredicate =
                new MedicineNameContainsKeywordsPredicate(firstPredicateKeywordList);
        MedicineNameContainsKeywordsPredicate secondPredicate =
                new MedicineNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MedicineNameContainsKeywordsPredicate firstPredicateCopy =
                new MedicineNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different medicine -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_medicineNameContainsKeywords_returnsTrue() {
        // One keyword
        MedicineNameContainsKeywordsPredicate predicate =
                new MedicineNameContainsKeywordsPredicate(Collections.singletonList("Paracetamol"));
        assertTrue(predicate.test(new MedicineBuilder().withMedicineName("Paracetamol One").build()));

        // Only one matching keyword
        predicate = new MedicineNameContainsKeywordsPredicate(Arrays.asList("Paracetamol", "Oracort"));
        assertTrue(predicate.test(new MedicineBuilder().withMedicineName("Paracetamol Two").build()));

        // Mixed-case keywords
        predicate = new MedicineNameContainsKeywordsPredicate(Arrays.asList("paraceTaMOl", "tHree"));
        assertTrue(predicate.test(new MedicineBuilder().withMedicineName("Paracetamol Three").build()));
    }

    @Test
    public void test_medicineNameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MedicineNameContainsKeywordsPredicate predicate =
                new MedicineNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new MedicineBuilder().withMedicineName("Paracetamol").build()));

        // Non-matching keyword
        predicate = new MedicineNameContainsKeywordsPredicate(Arrays.asList("Cheese"));
        assertFalse(predicate.test(new MedicineBuilder().withMedicineName("Paracetamol").build()));

        // Keywords match type, ed and ld, but does not match medicine name
        predicate =
                new MedicineNameContainsKeywordsPredicate(Arrays.asList("Tablet", "2", "8"));
        assertFalse(predicate.test(new MedicineBuilder().withMedicineName("Paracetamol").withMedicineType("Tablet")
                .withEffectiveDosage("2").withLethalDosage("8").build()));
    }

}

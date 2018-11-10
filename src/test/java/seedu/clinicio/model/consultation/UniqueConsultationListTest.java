package seedu.clinicio.model.consultation;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UniqueConsultationListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueConsultationList uniqueConsultationList = new UniqueConsultationList();

    @Test
    public void contains_nullConsultation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueConsultationList.contains(null);
    }

    @Test
    public void add_nullConsultation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueConsultationList.add(null);
    }

    @Test
    public void remove_nullConsultation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueConsultationList.remove(null);
    }

    @Test
    public void setConsultations_nullUniqueConsultationList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueConsultationList.setConsultations((UniqueConsultationList) null);
    }

    @Test
    public void setConsultations_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueConsultationList.setConsultations((List<Consultation>) null);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueConsultationList.asUnmodifiableObservableList().remove(0);
    }
}

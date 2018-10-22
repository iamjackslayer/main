package seedu.address.model.doctor;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

//@@author jjlee050
/**
 * Represents a Doctor in the ClinicIO.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Doctor extends Person {

    // Identity fields
    private final Password password;

    //Data fields
    //private final Shift shift;

    /**
     * Every field must be present and not null.
     */
    public Doctor(Name name, Password password) {
        super(name, new Phone("999"), new Email("alice@live.com"), new Address("1"), new HashSet<>());
        requireAllNonNull(name, password);
        this.password = password;
        //this.shift = shift;
    }
    
    public Password getPassword() {
        return password;
    }

    /*public Shift getShift() {
        return shift;
    }*/

    /**
     * Returns true if both doctors of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two doctor.
     */
    public boolean isSameDoctor(Doctor otherDoctor) {
        if (otherDoctor == this) {
            return true;
        }

        return otherDoctor != null
                && otherDoctor.getName().equals(getName())
                && otherDoctor.getPassword().equals(getPassword());
    }

    /**
     * Returns true if both doctors have the same identity and data fields.
     * This defines a stronger notion of equality between two doctors.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Doctor)) {
            return false;
        }

        Doctor otherDoctor = (Doctor) other;
        return otherDoctor.getName().equals(getName())
                && otherDoctor.getPassword().equals(getPassword());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(password);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Doctor-Name: ")
                .append(getName())
                .append(" Password: ")
                .append(getPassword());
        return builder.toString();
    }

}

package seedu.clinicio.model.staff;

import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;

import seedu.clinicio.model.person.Address;
import seedu.clinicio.model.person.Email;
import seedu.clinicio.model.person.Name;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.person.Phone;

//@@author jjlee050
/**
 * Represents a Staff in the ClinicIO.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Staff extends Person {

    // Identity fields
    private final Role role;

    // Data fields
    private Password password;

    /**
     * Every field must be present and not null.
     */
    public Staff(Role role, Name name) {
        super(name, new Phone("999"), new Email("alice@live.com"), new Address("1"), new HashSet<>());
        requireAllNonNull(role, name);
        this.role = role;
        this.password = new Password("123456", false);
    }

    /**
     * Every field must be present and not null.
     */
    public Staff(Role role, Name name, Password password) {
        super(name, new Phone("999"), new Email("alice@live.com"), new Address("1"), new HashSet<>());
        requireAllNonNull(role, name, password);
        this.role = role;
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public Password getPassword() {
        return password;
    }

    /**
     * Returns true if both staffs of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two staffs.
     */
    public boolean isSameStaff(Staff otherStaff) {
        if (otherStaff == this) {
            return true;
        }

        return otherStaff != null
                && otherStaff.getName().equals(getName())
                && (otherStaff.getRole().equals(getRole())
                || otherStaff.getPassword().equals(getPassword()));
    }

    /**
     * Returns true if both staffs have the same identity and data fields.
     * This defines a stronger notion of equality between two staffs.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Staff)) {
            return false;
        }

        Staff otherStaff = (Staff) other;
        return otherStaff.getName().equals(getName())
                && otherStaff.role.equals(getRole())
                && otherStaff.getPassword().equals(getPassword());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(role, password);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(role.toString())
                .append(" Name: ")
                .append(getName())
                .append(" Password: ")
                .append(getPassword());
        return builder.toString();
    }

}

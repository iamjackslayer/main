package seedu.clinicio.storage;

//@@gingivitiss

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.clinicio.commons.exceptions.IllegalValueException;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.appointment.Time;
import seedu.clinicio.model.patient.Patient;

/**
 * JAXB-friendly version of the Appointment.
 */
public class XmlAdaptedAppointment {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String time;
    @XmlElement(required = true)
    private int status;
    @XmlElement(required = true)
    private XmlAdaptedPatient patient;

    @XmlElement
    private int type;
    @XmlElement
    private XmlAdaptedStaff staff;

    /**
     * Constructs an XmlAdaptedAppointment. This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {
    }

    /**
     * Constructs an {@code XmlAdaptedAppointment} with the given appointment details.
     */
    public XmlAdaptedAppointment(String date, String time, XmlAdaptedPatient patient,
                                 int status, int type, XmlAdaptedStaff staff) {
        this.date = date;
        this.time = time;
        this.status = status;
        this.patient = patient;
        this.type = type;
        if (staff != null) {
            this.staff = staff;
        }
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAppointment
     */
    public XmlAdaptedAppointment(Appointment source) {
        int day = source.getAppointmentDate().getDay();
        int month = source.getAppointmentDate().getMonth();
        int year = source.getAppointmentDate().getYear();

        date = String.valueOf(day) + " " + String.valueOf(month) + " " + String.valueOf(year);

        int hour = source.getAppointmentTime().getHour();
        int min = source.getAppointmentTime().getMinute();

        time = String.valueOf(hour) + " " + String.valueOf(min);

        patient = new XmlAdaptedPatient(source.getPatient());

        status = source.getAppointmentStatus();
        type = source.getAppointmentType();

        if (source.getAssignedStaff().isPresent()) {
            staff = new XmlAdaptedStaff(source.getAssignedStaff().get());
        }
    }

    /**
     * Converts {@code Patient} string into it's object parameters.
     */
    public void toSimpleString(String patient) {

    }

    /**
     * Converts this jaxb-friendly adapted appointment object into the model's Appointment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment
     */
    public Appointment toModelType() throws IllegalValueException {

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(String.format(Date.MESSAGE_DATE_CONSTRAINTS));
        }

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }

        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }

        Patient modelPatient = patient.toModelType();

        return new Appointment(Date.newDate(date), Time.newTime(time), modelPatient, type);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAppointment)) {
            return false;
        }

        XmlAdaptedAppointment otherAppt = (XmlAdaptedAppointment) other;
        return Objects.equals(date, otherAppt.date)
                && Objects.equals(time, otherAppt.time)
                && Objects.equals(status, otherAppt.status)
                && Objects.equals(patient, otherAppt.patient)
                && Objects.equals(type, otherAppt.type)
                && Objects.equals(staff, otherAppt.staff);
    }
}

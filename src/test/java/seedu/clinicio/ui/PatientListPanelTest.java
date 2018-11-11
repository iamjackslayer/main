package seedu.clinicio.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import static seedu.clinicio.testutil.EventsUtil.postNow;
import static seedu.clinicio.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalPatients;

import static seedu.clinicio.ui.testutil.GuiTestAssert.assertCardDisplaysPatient;
import static seedu.clinicio.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.PatientCardHandle;
import guitests.guihandles.PatientListPanelHandle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.clinicio.commons.events.ui.JumpToListRequestEvent;
import seedu.clinicio.commons.util.FileUtil;
import seedu.clinicio.commons.util.XmlUtil;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.storage.XmlSerializableClinicIo;

public class PatientListPanelTest extends GuiUnitTest {
    private static final ObservableList<Patient> TYPICAL_PATIENTS =
            FXCollections.observableList(getTypicalPatients());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(
            INDEX_SECOND_PATIENT);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private PatientListPanelHandle patientListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_PATIENTS);

        for (int i = 0; i < TYPICAL_PATIENTS.size(); i++) {
            patientListPanelHandle.navigateToCard(TYPICAL_PATIENTS.get(i));
            Patient expectedPatient = TYPICAL_PATIENTS.get(i);
            PatientCardHandle actualCard = patientListPanelHandle.getPatientCardHandle(i);

            assertCardDisplaysPatient(expectedPatient, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_PATIENTS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        PatientCardHandle expectedPatient = patientListPanelHandle
                .getPatientCardHandle(INDEX_SECOND_PATIENT.getZeroBased());
        PatientCardHandle selectedPatient = patientListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPatient, selectedPatient);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code PatientListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Patient> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of patient cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code patientCount} persons that is used to populate the
     * {@code PatientListPanel}.
     */
    private ObservableList<Patient> createBackingList(int patientCount) throws Exception {
        Path xmlFile = createXmlFileWithPatients(patientCount);
        XmlSerializableClinicIo xmlClinicIo =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableClinicIo.class);
        return FXCollections.observableArrayList(xmlClinicIo.toModelType().getPatientList());
    }

    /**
     * Returns a .xml file containing {@code patientCount} persons. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithPatients(int patientCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<clinicio>\n");
        for (int i = 0; i < patientCount; i++) {
            builder.append("<patients>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<nric>S1234567B</nric>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<address>a</address>\n");
            builder.append("</patients>\n");
        }
        builder.append("</clinicio>\n");

        Path manyPatientsFile = TEST_DATA_FOLDER.resolve("manyPersons.xml");
        FileUtil.createFile(manyPatientsFile);
        FileUtil.writeToFile(manyPatientsFile, builder.toString());
        manyPatientsFile.toFile().deleteOnExit();
        return manyPatientsFile;
    }

    /**
     * Initializes {@code patientListPanelHandle} with a {@code PatientListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PatientListPanel}.
     */
    private void initUi(ObservableList<Patient> backingList) {
        PatientListPanel patientListPanel = new PatientListPanel(backingList);
        uiPartRule.setUiPart(patientListPanel);

        patientListPanelHandle = new PatientListPanelHandle(getChildNode(patientListPanel.getRoot(),
                PatientListPanelHandle.PATIENT_LIST_VIEW_ID));
    }
}

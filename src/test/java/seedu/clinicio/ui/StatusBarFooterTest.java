package seedu.clinicio.ui;

import static org.junit.Assert.assertEquals;
import static seedu.clinicio.testutil.EventsUtil.postNow;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.clinicio.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.clinicio.ui.StatusBarFooter.USER_SESSION_STATUS_INITIAL;
import static seedu.clinicio.ui.StatusBarFooter.USER_SESSION_STATUS_UPDATED;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import guitests.guihandles.StatusBarFooterHandle;
import seedu.clinicio.commons.events.model.ClinicIoChangedEvent;
import seedu.clinicio.commons.events.ui.LoginSuccessEvent;
import seedu.clinicio.commons.events.ui.LogoutClinicIoEvent;
import seedu.clinicio.model.ClinicIo;

public class StatusBarFooterTest extends GuiUnitTest {

    private static final Path STUB_SAVE_LOCATION = Paths.get("Stub");
    private static final Path RELATIVE_PATH = Paths.get(".");

    private static final ClinicIoChangedEvent EVENT_STUB = new ClinicIoChangedEvent(new ClinicIo());
    private static final LoginSuccessEvent LOGIN_SUCCESS_EVENT = new LoginSuccessEvent(ADAM);
    private static final LogoutClinicIoEvent LOGOUT_CLINIC_IO_EVENT = new LogoutClinicIoEvent();

    private static final Clock originalClock = StatusBarFooter.getClock();
    private static final Clock injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    private StatusBarFooterHandle statusBarFooterHandle;

    @BeforeClass
    public static void setUpBeforeClass() {
        // inject fixed clock
        StatusBarFooter.setClock(injectedClock);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        // restore original clock
        StatusBarFooter.setClock(originalClock);
    }

    @Before
    public void setUp() {
        StatusBarFooter statusBarFooter = new StatusBarFooter();
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(USER_SESSION_STATUS_INITIAL, SYNC_STATUS_INITIAL);

        // after ClinicIO is updated
        postNow(EVENT_STUB);
        assertStatusBarContent(USER_SESSION_STATUS_INITIAL,
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()));

        // after login successful
        postNow(LOGIN_SUCCESS_EVENT);
        assertStatusBarContent(String.format(USER_SESSION_STATUS_UPDATED, ADAM.getName().fullName, ADAM.getRole()),
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()));

        // after logout
        postNow(LOGOUT_CLINIC_IO_EVENT);
        assertStatusBarContent(USER_SESSION_STATUS_INITIAL,
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()));
    }

    /**
     * Asserts that the user session matches that of {@code expectedUserSession}, and the
     * sync status matches that of {@code expectedSyncStatus}.
     */
    private void assertStatusBarContent(String expectedUserSession, String expectedSyncStatus) {
        assertEquals(expectedUserSession, statusBarFooterHandle.getUserSession());
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        guiRobot.pauseForHuman();
    }

}

package seedu.clinicio.ui;

import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.core.UserSession;
import seedu.clinicio.commons.events.model.ClinicIoChangedEvent;
import seedu.clinicio.commons.events.ui.LoginSuccessEvent;
import seedu.clinicio.commons.events.ui.LogoutClinicIoEvent;
import seedu.clinicio.model.staff.Staff;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";

    public static final String USER_SESSION_STATUS_INITIAL = "You are not logged in";
    public static final String USER_SESSION_STATUS_UPDATED = "Logged in: %s (Role: %s)";

    /**
     * Used to generate time stamps.
     *
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */
    private static Clock clock = Clock.systemDefaultZone();

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar userSessionStatus;


    public StatusBarFooter() {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setUserSessionStatus(USER_SESSION_STATUS_INITIAL);
        registerAsAnEventHandler(this);
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        StatusBarFooter.clock = clock;
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    private void setUserSessionStatus(String userSession) {
        Platform.runLater(() -> userSessionStatus.setText(userSession));
    }

    private void setSyncStatus(String status) {
        Platform.runLater(() -> syncStatus.setText(status));
    }

    @Subscribe
    public void handleClinicIoChangedEvent(ClinicIoChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
    }

    @Subscribe
    public void handleLoginSuccessEvent(LoginSuccessEvent loginSuccessEvent) {
        Staff currentUser = loginSuccessEvent.getCurrentUser();
        logger.info(LogsCenter.getEventHandlingLogMessage(loginSuccessEvent,
                "You are now logged in as " + currentUser));
        setUserSessionStatus(String.format(USER_SESSION_STATUS_UPDATED,
                currentUser.getName(), currentUser.getRole()));
    }

    @Subscribe
    public void handleLogoutClinicIoEvent(LogoutClinicIoEvent logoutClinicIoEvent) {
        UserSession.destroy();
        logger.info(LogsCenter.getEventHandlingLogMessage(logoutClinicIoEvent,
                "You have logged out successfully"));
        setUserSessionStatus(USER_SESSION_STATUS_INITIAL);
    }
}

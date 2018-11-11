package seedu.clinicio;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.clinicio.commons.core.Config;
import seedu.clinicio.commons.core.EventsCenter;
import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.core.UserSession;
import seedu.clinicio.commons.core.Version;
import seedu.clinicio.commons.events.ui.ExitAppRequestEvent;
import seedu.clinicio.commons.exceptions.DataConversionException;
import seedu.clinicio.commons.util.ConfigUtil;
import seedu.clinicio.commons.util.StringUtil;
import seedu.clinicio.logic.Logic;
import seedu.clinicio.logic.LogicManager;
import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.ReadOnlyClinicIo;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.util.SampleDataUtil;
import seedu.clinicio.storage.ClinicIoStorage;
import seedu.clinicio.storage.JsonUserPrefsStorage;
import seedu.clinicio.storage.Storage;
import seedu.clinicio.storage.StorageManager;
import seedu.clinicio.storage.UserPrefsStorage;
import seedu.clinicio.storage.XmlClinicIoStorage;
import seedu.clinicio.ui.Ui;
import seedu.clinicio.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, false);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing ClinicIO ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        ClinicIoStorage clinicIoStorage = new XmlClinicIoStorage(userPrefs.getClinicIoFilePath());
        storage = new StorageManager(clinicIoStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        model.addUi(ui);

        initEventsCenter();
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s ClinicIO and {@code userPrefs}. <br>
     * The data from the sample ClinicIO will be used instead if {@code storage}'s ClinicIO is not found,
     * or an empty ClinicIO will be used instead if errors occur when reading {@code storage}'s ClinicIO.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyClinicIo> clinicIoOptional;
        ReadOnlyClinicIo initialData;
        try {
            clinicIoOptional = storage.readClinicIo();
            if (!clinicIoOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ClinicIO");
            }
            initialData = clinicIoOptional.orElseGet(SampleDataUtil::getSampleClinicIo);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ClinicIO");
            initialData = new ClinicIo();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ClinicIO");
            initialData = new ClinicIo();
        }
        //@@author iamjackslayer
        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ClinicIO");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting " + config.getAppTitle() + " " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping ClinicIO ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        UserSession.destroy();
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

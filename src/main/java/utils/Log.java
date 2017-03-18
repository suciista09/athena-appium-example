package utils;

import org.apache.log4j.*;
import ru.yandex.qatools.allure.annotations.Step;


/**
 * Created by buddyarifin on 8/15/16.
 */
public class Log {

    private static final Logger LOGGER = Logger.getLogger("APPIUM");
    private static PatternLayout layout = new PatternLayout("%d{dd MMM yyyy HH:mm:ss} %5p %c{1} - %m%n");
    private static ConsoleAppender consoleAppender;
    private static StepsScreenshotsRule screenshotsRule;
    private static ANSIConsoleAppender ansiConsoleAppender;

    static {
        screenshotsRule = new StepsScreenshotsRule(layout, "System.out");
        ansiConsoleAppender = new ANSIConsoleAppender(layout, "System.out");
    }

    /**
     * Method to display errors in log.
     *
     * @param message message to be displayed
     */
    public static void error(String message) {
        ansiConsoleAppender.setName("Console");
        LOGGER.addAppender(ansiConsoleAppender);
        LOGGER.setLevel(Level.ERROR);
        LOGGER.error(message);
    }

    /**
     * Method to display information in logs
     *
     * @param message message to be displayed
     */

    @Step("{0}")
    public static void info(String message) {
        ansiConsoleAppender.setName("Console");
        LOGGER.addAppender(ansiConsoleAppender);
        LOGGER.setLevel(Level.INFO);
        LOGGER.info(message);
    }

    public static void debug(String message) {
        ansiConsoleAppender.setName("Console");
        LOGGER.addAppender(ansiConsoleAppender);
        LOGGER.setLevel(Level.DEBUG);
        LOGGER.debug(message);
    }

    public static void warn(String message) {
        ansiConsoleAppender.setName("Console");
        LOGGER.addAppender(ansiConsoleAppender);
        LOGGER.setLevel(Level.WARN);
        LOGGER.warn(message);
    }
}

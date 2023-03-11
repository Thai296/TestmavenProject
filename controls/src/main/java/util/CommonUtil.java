package util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import trace.ExecutionTracer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommonUtil {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CommonUtil.class);
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private static final String SCREENSHOTS_ARCHIVE_ROOT = "./target/screenshots/";

    public static String logPrefix() {
        return Thread.currentThread().getId() + " - " + df.format(new Date()) + " - ";
    }

    public static void log(String message) {
        LOGGER.info(logPrefix() + message);
    }

    public static String screenshot(WebDriver webDriver) {
        byte[] bytes = ExecutionTracer.takeScreenshot(webDriver);
        String screenshotFileName = "screenshot" + System.currentTimeMillis() + ".png";
        copyScreenshot(screenshotFileName, bytes);
        return screenshotFileName;
    }

    public static void printConsoleLog(WebDriver webDriver) {
        LogEntries logEntries = webDriver.manage().logs().get(LogType.BROWSER);
        if (logEntries == null || logEntries.getAll() == null) {
            return;
        }

        List<LogEntry> logs = logEntries.getAll();

        LOGGER.info(logPrefix() + "Console log start.");
        for (LogEntry log : logs) {
            LOGGER.info(log.toString());
        }
        LOGGER.info(logPrefix() + "Console log end.");
    }

    public static void copyScreenshot(String screenshotFileName, byte[] bytes) {
        String fullpath = FilenameUtils.concat(SCREENSHOTS_ARCHIVE_ROOT, screenshotFileName);
        AsyncFileSaver saver = new AsyncFileSaver(fullpath, bytes);
        saver.save();
    }

    public static void copyScreenshot(File screenshot) {
        try {
            LOGGER.info(CommonUtil.logPrefix() + "Copying screenshot from " + screenshot.getAbsolutePath());
            FileUtils.copyFileToDirectory(screenshot, new File(SCREENSHOTS_ARCHIVE_ROOT));
            LOGGER.info(CommonUtil.logPrefix() + "Copied screenshot from " + screenshot.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to take screenshot of screen and store in framework directory "./target/images"
     *
     * @param webDriver instance of WebDriver
     * @return Name of screenshot or empty string
     */
    public static String takeScreenshot(WebDriver webDriver) {
        printConsoleLog(webDriver);
        if (webDriver instanceof TakesScreenshot) {
            try {
                File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
                new File("./target/images").mkdirs();
                CommonUtil.copyScreenshot(screenshot);
                FileUtils.copyFileToDirectory(screenshot, new File("./target/images/"));
                return screenshot.getName();
            } catch (IOException exd) {
                exd.printStackTrace();
            } catch (WebDriverException exd) {
                LOGGER.info(CommonUtil.logPrefix() + "Regular screenshot couldn't be taken because of " + exd.getMessage()
                        + ". Trying with robot");
                exd.printStackTrace();
                return takeScreenshotAsRobot();
            }
        }
        return "";
    }

    /**
     * Method to Take Screenshot using Robot Class and store in framework directory "./target/images"
     *
     * @return Name of screenshot or empty string
     */
    private static String takeScreenshotAsRobot() {
        try {
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            new File("./target/images").mkdirs();
            File screenshot = new File("./target/images/screenshot" + System.currentTimeMillis() + ".png");
            ImageIO.write(image, "png", screenshot);
            return screenshot.getName();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return "";
    }
}

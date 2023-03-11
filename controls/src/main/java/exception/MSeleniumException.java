package exception;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import util.CommonUtil;

/**
 * This class contains methods to get exception message and take screenshot when an exception occur
 *
 * Created by NiTesH bharDWAJ
 */

public class MSeleniumException extends WebDriverException
{
    private static final long serialVersionUID = 1L;

    private final WebDriver webDriver;
    private final String simpleMessage;
    private final String screenshotFileName;

    // ==============
    // Constructors	=
    // ==============

    /**
     * Initializes a new default instance of the MSeleniumException class.
     *
     * @param message Message to print
     * @param webDriver Webdriver class object
     */
    public MSeleniumException(String message, WebDriver webDriver) {
        super(message);
        this.webDriver = webDriver;
        simpleMessage = CommonUtil.logPrefix() + message;
        screenshotFileName = CommonUtil.takeScreenshot(webDriver);
    }

    /**
     * Initializes a new default instance of the MSeleniumException class.
     *
     * @param message Message to print
     * @param cause Cause of failure
     * @param webDriver Webdriver class object
     */
    public MSeleniumException(String message, Exception cause, WebDriver webDriver) {
        super(message, cause);
        this.webDriver = webDriver;
        simpleMessage = CommonUtil.logPrefix() + message + " - " + cause.getMessage();
        screenshotFileName = CommonUtil.takeScreenshot(webDriver);
    }

    /**
     * Initializes a new default instance of the MSeleniumException class.
     *
     * @param cause Cause of failure
     * @param additionalMessage Additional message to print
     */
    public MSeleniumException(MSeleniumException cause, String additionalMessage) {
        super(cause.getMessage(), cause);
        this.webDriver = null;
        simpleMessage = CommonUtil.logPrefix() + additionalMessage + " - " + cause.getSimpleMessage();
        screenshotFileName = cause.getScreenshotFileName();
    }

    // ==========
    // Commands	=
    // ==========
    /**
     * Overridden method to get exception description with screenshot file name
     *
     * @return message
     */
    @Override
    public String getMessage() {
        //return "[" + testClassName + "] " + simpleMessage + " screenshotFileName[" + screenshotFileName + "]";
        return simpleMessage + " screenshotFileName[" + screenshotFileName + "]";
    }

    /**
     * Method to get description of exception
     *
     * @return Simple Message
     */
    public String getSimpleMessage() {
        return simpleMessage;
    }

    /**
     * Method to get screenshot file name
     *
     * @return name of the screenshot file
     */
    public String getScreenshotFileName() {
        return screenshotFileName;
    }


}


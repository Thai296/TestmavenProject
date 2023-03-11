package controls;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import exception.MSeleniumException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import trace.ExecutionTracer;
import util.CommonUtil;
import util.CustomWebDriverWait;

/**
 * This class contains various methods that are common for all Controlled objects
 *
 * Created by NiTesH bharDWAJ
 */

public class ControlledObject<T extends Object>
{
    public static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ControlledObject.class);

    public static int TIME_OUT_IN_SECONDS;

    protected final WebDriver webDriver;
    protected final ExpectedCondition<T> retrievalCondition;
    protected T controlledObject;

    public final By by;
    public final String friendlyName;

    // ==============
    // Constructors	=
    // ==============

    /**
     * Initializes a new default instance of the ControlledObject class having expected condition
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param retrievalCondition Retrieval Condition
     */
    public ControlledObject(WebDriver webDriver, By by, ExpectedCondition<T> retrievalCondition) {
        this.webDriver = webDriver;
        this.by = by;
        this.retrievalCondition = retrievalCondition;
        friendlyName = null;
    }

    /**
     * Initializes a new default instance of the ControlledObject class having expected condition and friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param retrievalCondition Retrieval Condition
     * @param friendlyName Friendly name for the ControlledObject
     */
    public ControlledObject(WebDriver webDriver, By by, ExpectedCondition<T> retrievalCondition, String friendlyName) {
        this.webDriver = webDriver;
        this.by = by;
        this.retrievalCondition = retrievalCondition;
        this.friendlyName = friendlyName;
    }

    // ==========
    // Commands	=
    // ==========

    /**
     * Check that controlled object is defined on the page or not
     */
    public void shouldBeDefined() {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
            }
        });
    }

    /**
     * Method to check controlled object is present on current page or not
     *
     * @return if the controlled object is found on the current state of the page
     */
    public boolean isFound() {
        long findStart = System.currentTimeMillis();
        try {
            webDriver.findElement(by);
            long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
            LOGGER.info(by.toString() + " was found in " + timeToRetrieveElement + "ms");
            return true;
        } catch (WebDriverException exc) {
            long timeToLookForElement = (System.currentTimeMillis() - findStart);
            LOGGER.info("Element " + by + " was not found in " + timeToLookForElement + "ms");
            return false;
        }
    }

    public void waitForDisplayed() {
        try {
            LOGGER.info("wait for displayed controlled object " + by);
            WebDriverWait wait = new WebDriverWait(webDriver, TIME_OUT_IN_SECONDS);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }

    public void waitForElementExists(String xpath) {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, TIME_OUT_IN_SECONDS);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        } catch (Exception e) {
            throw e;
        }
    }

    public void waitForDisappear() {
        try {
            LOGGER.info("wait for undisplayed controlled object " + by);
            WebDriverWait wait = new WebDriverWait(webDriver, TIME_OUT_IN_SECONDS);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }

    public void waitForAttribute(String attribute, String value) {
        try {
            LOGGER.info("wait for attribute in controlled object " + by);
            WebDriverWait wait = new WebDriverWait(webDriver, TIME_OUT_IN_SECONDS);
            wait.until(ExpectedConditions.attributeContains(by,attribute, value));
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }

    /**
     * Method to check controlled object is not present on current page otherwise throws Exception
     */
    public void shouldNotBeDisplayed() {
        if (isFound()) {
            throw new MSeleniumException("Element " + by + " was found on the page", webDriver);
        }
    }

    // ==============
    // Accessors	=
    // ==============

    /**
     * Method to get current controlled object
     *
     * @return T: Type of object
     */
    public T getControlledObject() {
        return controlledObject;
    }

    // ==================
    // Internal state	=
    // ==================

    /**
     * Method to retrieve an controlled object by waiting for that controlled object
     */
    protected void retrieveControlledObject() {
        retrieveControlledObject(retrievalCondition);
    }

    /**
     * Method used to clear controlled object state.
     */
    protected void clearState() {
        controlledObject = null;
    }

    /**
     * Method to retrieve controlled object based on expected condition and updates retrieval time in tracer
     *
     * @param condition condition to retrieve controlled object
     */
    private void retrieveControlledObject(ExpectedCondition<T> condition) {
        LOGGER.info("finding controlled object " + by);
        long findStart = System.currentTimeMillis();
        controlledObject = new CustomWebDriverWait(webDriver, TIME_OUT_IN_SECONDS).until(condition);

        long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        LOGGER.info(by.toString() + " was found in " + timeToRetrieveElement + "ms");
        ExecutionTracer.updateMaxRetrievalTime(timeToRetrieveElement);

        if (controlledObject == null) {
            throw new MSeleniumException("Controlled object " + by + " is not available", webDriver);
        }
    }

    // ==================
    // Utility method	=
    // ==================

    /**
     * Method to print the message on console
     *
     * @param message: message to print on console
     */
    protected void println(String message) {
        LOGGER.info(CommonUtil.logPrefix() + message);
    }

    // ======================
    // Action management	=
    // ======================

    /**
     * This class is used by method and override the execute() method refer: shouldBeDefined(..)
     */
    public abstract class ManagedAction
    {
        public abstract void execute();

        public void reset() {

        }
    }

    /**
     * This class is used by method and override the execute() method refer: DropDown::getOptions(..)
     *
     * @param <A>: Return the current controlled object for future use
     */
    public abstract class ManagedActionWithReturn<A>
    {
        public abstract A execute();

        public void reset() {

        }
    }

    /**
     * Method to perform action by retry action
     *
     * @param action action to be performed and retried
     */
    protected void performAction(ManagedAction action) {
        long actionStart = System.currentTimeMillis();
        try {
            action.execute();
        } catch (Exception exc) {
            if (exc instanceof UnhandledAlertException) {
                LOGGER.info("Alert text is: " + ((UnhandledAlertException) exc).getAlertText());
                throw new MSeleniumException("Unable to perform action on " + by, exc, webDriver);
            } else {
                LOGGER.info("WARN: " + exc.getMessage());
                LOGGER.info("WARN: action on " + by + " failed, retrying...");
                clearState();
                try {
                    action.reset();
                    action.execute();
                    LOGGER.info("action retried SUCCESSFULLY !!!");
                    ExecutionTracer.incrementNumberOfRetriedClicks();
                    ExecutionTracer.addRetriedLocator(by);
                } catch (TimeoutException exd) {

                    LOGGER.info("WARN: " + exc.getMessage());
                    LOGGER.info("WARN: action on " + by + " failed, trying to find element in content...");
                    if (!findElementInContent()) {
                        long timeEllapsed = (System.currentTimeMillis() - actionStart);
                        LOGGER.info("ERROR - Action on " + by + " couldn't be performed in " + timeEllapsed + "ms (time out is "
                                + TIME_OUT_IN_SECONDS + " * 2 secs");
                        throw new MSeleniumException("Action on " + by + " couldn't be performed in " + timeEllapsed + "ms (time out is "
                                + TIME_OUT_IN_SECONDS + "secs", exd, webDriver);
                    } else {
                        LOGGER.info("ELEMENT FOUND IN CONTENT !!!");
                        try {
                            action.execute();
                        } catch (TimeoutException exe) {
                            long timeEllapsed = (System.currentTimeMillis() - actionStart);
                            LOGGER.info("ERROR - Action on " + by + " couldn't be performed in " + timeEllapsed + "ms (time out is "
                                    + TIME_OUT_IN_SECONDS + " * 3 secs");
                            throw new MSeleniumException("Action on " + by + " couldn't be performed in " + timeEllapsed
                                    + "ms (time out is " + TIME_OUT_IN_SECONDS + "secs", exe, webDriver);
                        }
                    }
                } catch (WebDriverException exd) {
                    if (exc instanceof UnhandledAlertException) {
                        throw new MSeleniumException("Unable to perform action on " + by, exc, webDriver);
                    } else {
                        if (!findElementInContent()) {
                            throw new MSeleniumException("Action on " + by + " cannot be performed", exd, webDriver);
                        } else {
                            LOGGER.info("ELEMENT FOUND IN CONTENT !!!");
                            action.execute();
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to perform action by retry action
     *
     * @param action: action to be performed and retried
     * @param <A>: Controlled type
     * @return return the current controlled object for use
     */
    protected <A> A performAction(ManagedActionWithReturn<A> action) {
        long actionStart = System.currentTimeMillis();
        try {
            A result = action.execute();
            return result;
        } catch (Exception exc) {
            if (exc instanceof UnhandledAlertException) {
                throw new MSeleniumException("Unable to perform action on " + by, exc, webDriver);
            } else {
                LOGGER.info("WARN: " + exc.getMessage());
                LOGGER.info("WARN: action on " + by + " failed, retrying...");
                clearState();
                try {
                    A result = action.execute();
                    LOGGER.info("action retried SUCCESSFULLY !!!");
                    ExecutionTracer.incrementNumberOfRetriedClicks();
                    ExecutionTracer.addRetriedLocator(by);
                    return result;
                } catch (TimeoutException exd) {
                    LOGGER.info("WARN: " + exc.getMessage());
                    LOGGER.info("WARN: action on " + by + " failed, trying to find element in content...");
                    if (!findElementInContent()) {
                        long timeEllapsed = (System.currentTimeMillis() - actionStart);
                        LOGGER.info("ERROR - Action on " + by + " couldn't be performed in " + timeEllapsed + "ms (time out is "
                                + TIME_OUT_IN_SECONDS + " * 2 secs");
                        throw new MSeleniumException("Action on " + by + " couldn't be performed in " + timeEllapsed + "ms (time out is "
                                + TIME_OUT_IN_SECONDS + "secs", exd, webDriver);
                    } else {
                        LOGGER.info("ELEMENT FOUND IN CONTENT !!!");
                        try {
                            return action.execute();
                        } catch (TimeoutException exe) {
                            long timeEllapsed = (System.currentTimeMillis() - actionStart);
                            LOGGER.info("ERROR - Action on " + by + " couldn't be performed in " + timeEllapsed + "ms (time out is "
                                    + TIME_OUT_IN_SECONDS + " * 3 secs");
                            throw new MSeleniumException("Action on " + by + " couldn't be performed in " + timeEllapsed
                                    + "ms (time out is " + TIME_OUT_IN_SECONDS + "secs", exe, webDriver);
                        }
                    }
                } catch (WebDriverException exd) {
                    throw new MSeleniumException("Action on " + by + " cannot be performed", exd, webDriver);
                }
            }
        }
    }

    /**
     * Method to find element in opened window and if not found then switch to frame and find the element
     * Implementation of method is provided in child class
     *
     * @return false
     */
    protected boolean findElementInContent() {
        return false;
    }

    // ==========
    // Wait	=
    // ==========

    /**
     * Method to wait for the provided time in second.
     *
     * @param reason for waiting
     * @param numberOfSeconds: Time in Seconds IMPORTANT: THIS SHOULD ONLY BE USED WHERE THERE IS NO SELENIUM
     *            IMPLICIT WAIT POSSIBLE
     */
    public void wait(String reason, int numberOfSeconds) {
        try {
            LOGGER.info(CommonUtil.logPrefix() + "WARN: necessary waiting for " + numberOfSeconds + " seconds " + reason);
            Thread.sleep(numberOfSeconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

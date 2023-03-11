package controls;

import exception.MSeleniumException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import trace.ExecutionTracer;
import trace.TraceReporter;
import util.CommonUtil;
import util.CustomWebDriverWait;

/**
 * This class contains customized methods of selenium webdriver for Alert.
 */

public class Alert
{
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Alert.class);
    protected final WebDriver webDriver;
    protected final ExpectedCondition<org.openqa.selenium.Alert> retrievalCondition;
    protected org.openqa.selenium.Alert controlledObject;

    // ==============
    // Constructors	=
    // ==============

    /**
     * Initializes a new default instance of the Alert class.
     *
     * @param webDriver Webdriver class object
     */
    public Alert(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.retrievalCondition = ExpectedConditions.alertIsPresent();
    }

    // ==========
    // Commands	=
    // ==========

    /**
     * Method to check that element is defined and visible on page
     */
    public void shouldBeDisplayed() {
        retrieveControlledObject();
    }

    /**
     * Method to get text of the element
     *
     * @return text of alert
     */
    public String getText() {
        retrieveControlledObject();
        return controlledObject.getText();
    }

    // ==================
    // Internal state	=
    // ==================

    /**
     * Method to retrieve controlled object
     */
    private void retrieveControlledObject() {
        println("finding alert");
        long findStart = System.currentTimeMillis();
        controlledObject = new CustomWebDriverWait(webDriver, ControlledObject.TIME_OUT_IN_SECONDS).until(retrievalCondition);

        long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        println("alert was found in " + timeToRetrieveElement + "ms");
        ExecutionTracer.updateMaxRetrievalTime(timeToRetrieveElement);

        if (controlledObject == null) {
            throw new MSeleniumException("alert is not available", webDriver);
        }
    }

    // ==================
    // Utility method	=
    // ==================

    /**
     * Method to print message on console
     *
     * @param message : Message to print
     */
    protected void println(String message) {
        LOGGER.info(CommonUtil.logPrefix() + message);
    }
}

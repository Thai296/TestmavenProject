
package trace;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;

import controls.ControlledElement;
import controls.ControlledList;
import controls.DropDown;
import controls.TextField;
import exception.MSeleniumException;
import util.CommonUtil;

/**
 * This class contains methods to generate tracer of test script as per action performed
 *
 * Created by NiTesH bharDWAJ
 */
public class ExecutionTracer
{
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ExecutionTracer.class);

    public static int traceSize = 10;
    public static List<TraceActionType> takeScreenshotsFor = Arrays.asList(
            new TraceActionType[] { TraceActionType.CLICK, TraceActionType.TYPE });
    public static ThreadLocal<String> className = new ThreadLocal<String>();

    private static ExecutionTracer executionTracer = new ExecutionTracer();

    private final HashMap<String, TestTracer> testTracers = new HashMap<String, TestTracer>();

    private int numberOfClickActions = 0;
    private int numberOfTypingActions = 0;
    private int numberOfListSelections = 0;

    private long maxRetrievalTime = 0;

    private int numberOfRetriedActions = 0;
    private Map<String, Integer> retriedLocators = new HashMap<String, Integer>();

    // ==========
    // Commands	=
    // ==========

    /**
     * Synchronized method to get test tracer
     *
     * @return test tracer
     */
    public static synchronized TestTracer getTestTracer() {
        TestTracer testTracer = executionTracer.testTracers.get(className.get());
        if (testTracer == null) {
            testTracer = new TestTracer();
            executionTracer.testTracers.put(className.get(), testTracer);
        }
        return testTracer;
    }

    /**
     * Method to trace of 'Click' Action
     *
     * @param browser instance of WebDriver
     * @param element controlled object
     * @return traced action of Click action type
     */
    public static TracedAction traceClickAction(WebDriver browser, ControlledElement element) {
        return executionTracer.traceAction(browser, getTestTracer(), TraceActionType.CLICK,
                TraceReporter.generateClickElementHtmlDescription(className.get(), element));
    }

    /**
     * Method to trace of 'Click All' Action
     *
     * @param browser instance of WebDriver
     * @param element controlled object
     * @return traced action of 'Click All' action type
     */
    public static TracedAction traceClickAllAction(WebDriver browser, ControlledList element) {
        return executionTracer.traceAction(browser, getTestTracer(), TraceActionType.CLICKALL,
                TraceReporter.generateClickAllElementHtmlDescription(className.get(), element));
    }

    /**
     * Method to trace of 'Check' Action
     *
     * @param browser instance of WebDriver
     * @param element controlled object
     * @return traced action of Check action type
     */
    public static TracedAction traceCheckAction(WebDriver browser, ControlledElement element) {
        return executionTracer.traceAction(browser, getTestTracer(), TraceActionType.CHECK,
                TraceReporter.generateCheckElementHtmlDescription(className.get(), element));
    }

    /**
     * Method to trace of 'Select' Action
     *
     * @param browser instance of WebDriver
     * @param element controlled object
     * @param value dropdown value
     * @return traced action of 'Select' action type
     */
    public static TracedAction traceSelectAction(WebDriver browser, DropDown element, String value) {
        return executionTracer.traceAction(browser, getTestTracer(), TraceActionType.SELECT,
                TraceReporter.generateSelectElementHtmlDescription(className.get(), element, value));
    }

    /**
     * Method to trace of 'Type' Action
     *
     * @param browser instance of WebDriver
     * @param element controlled object
     * @param value text field value
     * @return traced action of 'Type' action type
     */
    public static TracedAction traceTypeAction(WebDriver browser, TextField element, String value) {
        return executionTracer.traceAction(browser, getTestTracer(), TraceActionType.TYPE,
                TraceReporter.generateTypeElementHtmlDescription(className.get(), element, value));
    }

    /**
     * Method to add the click, type and list selection action in TestTracer
     *
     * @param action: contains all action related to test case
     */
    public static void add(TracedAction action) {
        TestTracer testTracer = getTestTracer();
        if (!testTracer.isFailed() && testTracer.isRecordingActions()) {
            testTracer.add(action);
            long actionDuration = System.currentTimeMillis() - action.startTime();
            if (action.actionType() == TraceActionType.CLICK || action.actionType() == TraceActionType.CHECK) {
                incrementNumberOfClickAction();
                if (actionDuration > 2 * TraceReporter.CLICK_ACTION_HUMAN) {
                    LOGGER.info(CommonUtil.logPrefix() + "WARN: action took " + actionDuration + " ms");
                    Thread.dumpStack();
                }
            } else if (action.actionType() == TraceActionType.TYPE) {
                incrementNumberOfTypingAction();
                if (actionDuration > 2 * TraceReporter.TYPE_ACTION_HUMAN) {
                    LOGGER.info(CommonUtil.logPrefix() + "WARN: action took " + actionDuration + " ms");
                    Thread.dumpStack();
                }
            } else if (action.actionType() == TraceActionType.SELECT) {
                incrementNumberOfListSelection();
                if (actionDuration > 2 * TraceReporter.LIST_SELECTION_HUMAN) {
                    LOGGER.info(CommonUtil.logPrefix() + "WARN: action took " + actionDuration + " ms");
                    Thread.dumpStack();
                }
            }
        }
    }

    /**
     * Method to add time taken by testcase for execution in tracer
     *
     * @param idleTime: Time taken to execute
     */
    public static void addIdleTime(long idleTime) {
        TestTracer testTracer = getTestTracer();
        testTracer.addToIdleTime(idleTime);
    }

    /**
     * Method to set the status of Failed testcase
     */
    public static void fail() {
        TestTracer testTracer = getTestTracer();
        testTracer.fail();
    }

    /**
     * Method to print exception and set the status of Failed testcase
     *
     * @param exc exception
     */
    public static void fail(Throwable exc) {
        LOGGER.info(CommonUtil.logPrefix() + className.get() + " has failed");
        TestTracer testTracer = getTestTracer();
        testTracer.fail(exc);
    }

    /**
     * Method to get collection of all test tracers
     *
     * @return an unmodifiable view of test tracers
     */
    public static Map<String, TestTracer> getTestTracers() {
        return Collections.unmodifiableMap(executionTracer.testTracers);
    }

    // ======================
    // Statistics method	=
    // ======================

    /**
     * Method to update max retrieval time taken by an Controlled element
     *
     * @param retrievalTime duration of last action
     */
    public static synchronized void updateMaxRetrievalTime(long retrievalTime) {
        if (executionTracer.maxRetrievalTime < retrievalTime) {
            executionTracer.maxRetrievalTime = retrievalTime;
        }
    }

    /**
     * Method to add number of retried operation performed for a locator into the Map
     *
     * @param locator locator of an controlled object
     */
    public static synchronized void addRetriedLocator(By locator) {
        Integer numberOfRetried = executionTracer.retriedLocators.get(locator.toString());
        if (numberOfRetried == null) {
            numberOfRetried = 0;
        }
        executionTracer.retriedLocators.put(locator.toString(), numberOfRetried + 1);
    }

    /**
     * Method to increment number of 'Click' action performed while execution
     */
    public static void incrementNumberOfClickAction() {
        executionTracer.numberOfClickActions++;
        LOGGER.info(CommonUtil.logPrefix() + "number of click actions = " + executionTracer.numberOfClickActions);
        getTestTracer().incrementNumberOfClickAction();
    }

    /**
     * Method to increment number of 'Typing' action performed while execution
     */
    public static void incrementNumberOfTypingAction() {
        executionTracer.numberOfTypingActions++;
        LOGGER.info(CommonUtil.logPrefix() + "number of typing actions = " + executionTracer.numberOfTypingActions);
        getTestTracer().incrementNumberOfTypingAction();
    }

    /**
     * Method to increment number of 'List Selections' performed while execution
     */
    public static void incrementNumberOfListSelection() {
        executionTracer.numberOfListSelections++;
        LOGGER.info(CommonUtil.logPrefix() + "number of list selections = " + executionTracer.numberOfListSelections);
        getTestTracer().incrementNumberOfListSelection();
    }

    /**
     * Method to increment number of 'Retried' action performed while execution
     */
    public static void incrementNumberOfRetriedClicks() {
        executionTracer.numberOfRetriedActions++;
    }

    /**
     * Method to get the max retrieval time for an Controlled element
     *
     * @return long value of max retrieval time
     */
    public static long maxRetrievalTime() {
        return executionTracer.maxRetrievalTime;
    }

    /**
     * Method to get the total number of actions performed while execution
     *
     * @return sum of click actions, typing actions and list selections
     */
    protected static int numberOfActions() {
        return executionTracer.numberOfClickActions + executionTracer.numberOfTypingActions + executionTracer.numberOfListSelections;
    }

    /**
     * Method to get number of 'Click' action performed while execution
     *
     * @return number of click Actions
     */
    protected static int numberOfClickActions() {
        return executionTracer.numberOfClickActions;
    }

    /**
     * Method to get number of 'Typing' action performed while execution
     *
     * @return number of typing Actions
     */
    protected static int numberOfTypingActions() {
        return executionTracer.numberOfTypingActions;
    }

    /**
     * Method to get number of 'List Selections' performed while execution
     *
     * @return number of list selections
     */
    protected static int numberOfListSelections() {
        return executionTracer.numberOfListSelections;
    }

    /**
     * Method to get number of 'Retried' action performed while execution
     *
     * @return number of retried Actions
     */
    public static int numberOfRetriedActions() {
        return executionTracer.numberOfRetriedActions;
    }

    /**
     * Method to get collection of retried locators
     *
     * @return unmodifiable view of retried locators
     */
    public static Map<String, Integer> retriedLocators() {
        return Collections.unmodifiableMap(executionTracer.retriedLocators);
    }

    // ==================
    // Private Methods	=
    // ==================
    /**
     * Method to take screenshot for action type and add into traced action
     *
     * @param browser instance of WebDriver
     * @param testTracer test tracer
     * @param actionType type of action like 'CLICK', 'TYPE', 'CHECK', 'CLICKALL' or 'SELECT'.
     * @param description html description of controlled element
     * @return performed action
     */
    private TracedAction traceAction(WebDriver browser, TestTracer testTracer, TraceActionType actionType, String description) {
        TracedAction performedAction = null;
        if (takeScreenshotsFor.contains(actionType)) {
            long prepareStart = System.currentTimeMillis();
            try {
                performedAction = new TracedAction(testTracer.getActionIndex(), actionType, description, takeScreenshot(browser));
                LOGGER.info(CommonUtil.logPrefix() + "trace with screenshot prepared in "
                        + (System.currentTimeMillis() - prepareStart) + "ms");
            } catch (UnhandledAlertException exc) {
                throw new MSeleniumException("Unable to take screenshot because of unexcepted alert ", exc, browser);
            }
        } else {
            LOGGER.info(CommonUtil.logPrefix() + "trace prepared without screenshot");
            performedAction = new TracedAction(testTracer.getActionIndex(), actionType, description);
        }
        return performedAction;
    }

    /**
     * Method to take screenshot of trace action
     *
     * @param webDriver instance of WebDriver
     * @return screenshot file as bytes array
     */
    public static byte[] takeScreenshot(WebDriver webDriver) {
        CommonUtil.printConsoleLog(webDriver);
        if (webDriver instanceof TakesScreenshot) {
            return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
        }
        return null;
    }

}



package trace;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import exception.MSeleniumException;

/**
 * This class stores the test related trace, number of actions and type of actions for reporting
 *
 * Created by NiTesH bharDWAJ
 */

public class TestTracer
{
    private final long startTime = System.currentTimeMillis();

    private long endTime;
    private long idleTime = 0;

    private int numberOfClickActions = 0;
    private int numberOfTypingActions = 0;
    private int numberOfListSelections = 0;
    private int actionIndex = 0;

    private boolean isFailed = false;
    private boolean isRecordingActions = true;

    private Throwable exc;
    private final LinkedList<TracedAction> actions = new LinkedList<TracedAction>();

    // ==========
    // Commands	=
    // ==========

    /**
     * Method to add the action into actions list
     *
     * @param action tracedAction
     */
    public void add(TracedAction action) {
        actionIndex = actionIndex + 1;
        actions.add(action);
        if (actions.size() > ExecutionTracer.traceSize) {
            actions.removeFirst();
        }
    }

    /**
     * Method to set 'isFailed' flag as 'true'
     */
    public void fail() {
        isFailed = true;
    }

    /**
     * Method to stop recording action
     */
    public void stopRecordingActions() {
        isRecordingActions = false;
    }

    /**
     * Method to set 'isFailed' flag as 'true' and set exception
     *
     * @param exc exception
     */
    public void fail(Throwable exc) {
        isFailed = true;
        this.exc = exc;
    }

    /**
     * Method to get recorded actions
     *
     * @return list of recorded actions
     */
    public List<TracedAction> getRecordedActions() {
        return Collections.unmodifiableList(actions);
    }

    /**
     * Method to Clear List of actions
     */
    public void clearActions() {
        actions.clear();
    }

    /**
     * Method to get the value of 'isFailed' flag
     *
     * @return true or false value of 'isFailed'
     */
    public boolean isFailed() {
        return isFailed;
    }

    /**
     * Method to get Recording actions status
     *
     * @return true if recording otherwise false
     */
    public boolean isRecordingActions() {
        return isRecordingActions;
    }

    /**
     * Method to get the Action index value
     *
     * @return integer value of action index
     */
    public int getActionIndex() {
        return actionIndex;
    }

    /**
     * Method to get simple error message
     *
     * @return "Error" or message if 'exc' is not null
     */
    public String getErrorMessage() {
        if (exc != null) {
            if (exc instanceof MSeleniumException) {
                return ((MSeleniumException) exc).getSimpleMessage();
            } else {
                return exc.getMessage();
            }
        } else {
            return "Error";
        }
    }

    /**
     * Method to set Recording Action Status
     *
     * @param status true or false
     */
    public void setRecordingActionsStatus(boolean status) {
        isRecordingActions = status;
    }

    /**
     * Method to Get Exception
     *
     * @return throwable exception
     */
    public Throwable getException() {
        return exc;
    }

    /**
     * Method to Set End time
     *
     * @param endTime long value of endTime value
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * Method to set idle time
     *
     * @param idleTime long value of idleTime value
     */
    public void addToIdleTime(long idleTime) {
        this.idleTime = this.idleTime + idleTime;
    }

    /**
     * Method to get Idle Time
     *
     * @return long value of idle Time
     */
    public long idleTime() {
        return idleTime;
    }

    /**
     * Method to get total duration between start time and end time
     *
     * @return subtraction of end time and start time
     */
    public long duration() {
        return endTime - startTime;
    }

    /**
     * Method to increment number of 'Click Action'.
     */
    public void incrementNumberOfClickAction() {
        numberOfClickActions++;
    }

    /**
     * Method to increment number of 'Typing Action'.
     */
    public void incrementNumberOfTypingAction() {
        numberOfTypingActions++;
    }

    /**
     * Method to increment number of 'List Selection'.
     */
    public void incrementNumberOfListSelection() {
        numberOfListSelections++;
    }

    /**
     * Method to Get Number of 'Click' Actions
     *
     * @return number of Click actions
     */
    protected int numberOfClickActions() {
        return numberOfClickActions;
    }

    /**
     * Method to get Number of 'Typing' actions
     *
     * @return number of typing actions
     */
    protected int numberOfTypingActions() {
        return numberOfTypingActions;
    }

    /**
     * Method to get Number of 'List' Selections
     *
     * @return number of list selections
     */
    protected int numberOfListSelections() {
        return numberOfListSelections;
    }
}


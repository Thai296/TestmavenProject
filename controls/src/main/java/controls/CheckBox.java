package controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import trace.ExecutionTracer;
import trace.TracedAction;

/**
 * This class contains customized methods of selenium webdriver for Checkbox
 *
 * Created by NiTesH bharDWAJ
 */

public class CheckBox extends ControlledElement
{

    /**
     * Initializes a new default instance of the CheckBox class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public CheckBox(WebDriver webDriver, By by) {
        super(webDriver, by);
    }

    /**
     * Initializes a new default instance of the CheckBox class with friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param friendlyName Friendly name for the CheckBox
     */
    public CheckBox(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, friendlyName);
    }

    /**
     * Method to return status of checkbox that it is checked or not
     *
     * @return if check box is checked
     */
    public boolean isChecked() {
        return performAction(new ManagedActionWithReturn<Boolean>() {
            @Override
            public Boolean execute() {
                retrieveControlledObject();
                return controlledObject.isSelected();
            }
        });
    }

    /**
     * Method to click the element and then check for the next element
     *
     * @param element Element to verify
     */
    public void checkAndVerify(final ControlledElement element) {
        TracedAction action = ExecutionTracer.traceCheckAction(webDriver, this);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.click();
                element.checkDisplay();
            }
        });
        ExecutionTracer.add(action);
    }
}
package controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import trace.ExecutionTracer;
import trace.TracedAction;

/**
 * RadioButton class contains Constructor for Super class ControlledElement and contains the custom methods
 * for different verifications and operations on Radio button
 *
 * Created by NiTesH bharDWAJ
 */

public class RadioButton extends ControlledElement
{
    /**
     * Initializes a new default instance of the RadioButton class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public RadioButton(WebDriver webDriver, By by) {
        super(webDriver, by);
    }

    /**
     * Initializes a new default instance of the RadioButton class with friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param friendlyName Friendly name for the RadioButton
     */
    public RadioButton(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, friendlyName);
    }

    /**
     * Method to click on a Radio button and check display of given element
     *
     * @param element Element to check presence
     * @param otherOption other radio button option used for retrying action
     */
    public void clickAndCheck(final ControlledElement element, final RadioButton otherOption) {
        TracedAction action = ExecutionTracer.traceClickAction(webDriver, this);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.click();
                element.checkDisplay();
            }
            @Override
            public void reset() {
                otherOption.click();
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Method to get boolean value if a RadioButton is selected or not
     *
     * @return if the radio button is selected or not
     */
    public boolean isSelected() {
        return performAction(new ManagedActionWithReturn<Boolean>() {
            @Override
            public Boolean execute() {
                retrieveControlledObject();
                return controlledObject.isSelected();
            }
        });
    }
}


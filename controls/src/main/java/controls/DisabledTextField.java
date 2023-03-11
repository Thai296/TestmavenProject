package controls;

import org.openqa.selenium.*;

/**
 * This class contains customized methods of selenium webdriver for disabled elements and using the parent
 * class methods
 *
 * Created by NiTesH bharDWAJ
 */

public class DisabledTextField extends DisabledElement
{

    // ==============
    // Constructors	=
    // ==============

    /**
     * Initializes a new default instance of the DisabledTextField class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public DisabledTextField(WebDriver webDriver, By by) {
        super(webDriver, by);
    }

    /**
     * Initializes a new default instance of the DisabledTextField class with friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param friendlyName Friendly name for the DisabledTextField
     */
    public DisabledTextField(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, friendlyName);
    }

    // ==========
    // Commands	=
    // ==========

    /**
     * Method to get value of the controlled element
     *
     * @return value of this controlled element
     */
    public String getValue() {
        return performAction(new ManagedActionWithReturn<String>() {
            @Override
            public String execute() {
                retrieveControlledObject();
                return controlledObject.getAttribute("value");
            }
        });
    }
}

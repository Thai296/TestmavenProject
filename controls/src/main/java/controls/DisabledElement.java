package controls;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * This class contains customized methods of selenium webdriver for disabled elements and using the parent
 * class ControlledObject methods
 *
 * Created by NiTesH bharDWAJ
 */

public class DisabledElement extends ControlledObject<WebElement>
{

    // ==============
    // Constructors	=
    // ==============

    /**
     * Initializes a new default instance of the DisabledElement class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public DisabledElement(WebDriver webDriver, By by) {
        super(webDriver, by, ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * Initializes a new default instance of the DisabledElement class with friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param friendlyName Friendly name for the DisabledElement
     */
    public DisabledElement(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, ExpectedConditions.presenceOfElementLocated(by), friendlyName);
    }

    // ==========
    // Commands	=
    // ==========

    /**
     * Checks that element is defined and visible on page
     */
    public void shouldBeDisplayed() {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
            }
        });
    }
}
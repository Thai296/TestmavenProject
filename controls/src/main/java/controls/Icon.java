

package controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Icon class contains Constructor for Super class ControlledElement and this is used to interact with icons
 * visible on the page
 *
 * Created by NiTesH bharDWAJ
 */

public class Icon extends ControlledElement
{

    /**
     * Initializes a new default instance of the Icon class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public Icon(WebDriver webDriver, By by) {
        super(webDriver, by);
    }

    /**
     * Initializes a new default instance of the Icon class with friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param friendlyName Friendly name for the Icon
     */
    public Icon(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, friendlyName);
    }
}
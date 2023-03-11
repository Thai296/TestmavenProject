

package controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * This class extending the ControlledElement class which is having customized methods of selenium webdriver.
 *
 * Created by NiTesH bharDWAJ
 */

public class Button extends ControlledElement
{
    /**
     * Initializes a new default instance of the Button class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public Button(WebDriver webDriver, By by) {
        super(webDriver, by);
    }

    /**
     * Initializes a new default instance of the Button class with friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param friendlyName Friendly name for the button
     */
    public Button(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, friendlyName);
    }
}


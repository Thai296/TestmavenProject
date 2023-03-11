

package controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * HiddenElement class contains Constructor for Super class ControlledObject and this is used to validate
 * elements those are not visible on the Page
 *
 * Created by NiTesH bharDWAJ
 */
public class HiddenElement extends ControlledObject<Boolean>
{
    /**
     * Initializes a new default instance of the HiddenElement class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public HiddenElement(WebDriver webDriver, By by) {
        super(webDriver, by, ExpectedConditions.invisibilityOfElementLocated(by));
    }

    /**
     * Initializes a new default instance of the HiddenElement class with friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param friendlyName Friendly name for the HiddenElement
     */
    public HiddenElement(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, ExpectedConditions.invisibilityOfElementLocated(by), friendlyName);
    }

}

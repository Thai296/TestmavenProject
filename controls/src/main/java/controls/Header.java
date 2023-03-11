package controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Header class contains Constructor for Super class ControlledElement
 *
 * Created by NiTesH bharDWAJ
 */

public class Header extends ControlledElement
{

    /**
     * Initializes a new default instance of the Header class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public Header(WebDriver webDriver, By by) {
        super(webDriver, by);
    }

    /**
     * Initializes a new default instance of the Header class with friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param friendlyName Friendly name for the Header
     */
    public Header(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, friendlyName);
    }

}

package controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Link class contains Constructor for Super class ControlledElement and this is used to interact with links
 * visible on the page
 *
 * Created by NiTesH bharDWAJ
 */
public class Link extends ControlledElement
{
    /**
     * Initializes a new default instance of the Link class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public Link(WebDriver webDriver, By by) {
        super(webDriver, by);
    }

    /**
     * Initializes a new default instance of the Link class with friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param friendlyName Friendly name for the Link
     */
    public Link(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, friendlyName);
    }
}
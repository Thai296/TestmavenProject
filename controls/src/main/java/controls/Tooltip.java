package controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * This class contains customized methods of selenium webdriver for Tooltip.
 *
 * Created by NiTesH bharDWAJ
 */

public class Tooltip extends ControlledElement
{

    /**
     * Initializes a new default instance of the Tooltip class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public Tooltip(WebDriver webDriver, By by) {
        super(webDriver, by);
    }

    /**
     * Initializes a new default instance of the Tooltip class with friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param friendlyName Friendly name for the Tooltip
     */
    public Tooltip(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, friendlyName);
    }

    /**
     * Method to get title of Tooltip
     *
     * @return title of the tooltip
     */
    public String getTitle() {
        return performAction(new ManagedActionWithReturn<String>() {
            @Override
            public String execute() {
                retrieveControlledObject();
                return controlledObject.getAttribute("title");
            }
        });
    }

}















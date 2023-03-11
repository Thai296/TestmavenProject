package controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * This class extending ControlledObject class and its used to verify any text present in located element
 *
 * Created by NiTesH bharDWAJ
 */
public class TextInElement extends ControlledObject<Boolean>
{
    /**
     * Initializes a new default instance of the TextInElement class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param text Text in the element
     */
    public TextInElement(WebDriver webDriver, By by, String text) {
        super(webDriver, by, ExpectedConditions.textToBePresentInElementLocated(by, text));
    }

}

package controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import trace.ExecutionTracer;
import trace.TracedAction;

/**
 * This class contains methods to perform various operations on list of elements found by the "by" parameter
 *
 * Created by NiTesH bharDWAJ
 */
public class ElementList extends ControlledList
{
    /**
     * Initializes a new default instance of the ElementList class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public ElementList(WebDriver webDriver, By by) {
        super(webDriver, by, ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    /**
     * Method to click all options in element list and add tracer for the events
     */
    public void clickAll() {
        TracedAction action = ExecutionTracer.traceClickAllAction(webDriver, this);
        retrieveControlledObject();
        for (WebElement element : controlledObject) {
            if (!element.isSelected()) {
                element.click();
                ExecutionTracer.incrementNumberOfClickAction();
            }
        }
        ExecutionTracer.add(action);
    }

}
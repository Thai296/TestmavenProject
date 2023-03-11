

package controls;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import exception.MSeleniumException;
import trace.ExecutionTracer;
import trace.TracedAction;

/**
 * This class contains various methods that are common for all Selenium controls. It provide method to perform
 * operation on list of controlled object and return as Elements List.
 *
 * Created by NiTesH bharDWAJ
 */

public class ControlledList extends ControlledObject<List<WebElement>>
{

    /**
     * Initializes a new default instance of the ControlledList class.
     *
     * @param by By class object
     */
    public ControlledList(WebDriver webDriver, By by) {
        super(webDriver, by, ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    /**
     * Initializes a new default instance of the ControlledList class with friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param friendlyName Friendly name for the ControlledList
     */
    public ControlledList(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, ExpectedConditions.presenceOfAllElementsLocatedBy(by), friendlyName);
    }

    /**
     * Initializes a new default instance of the ControlledList class having expected condition
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param condition Expected Condition
     */
    protected ControlledList(WebDriver webDriver, By by, ExpectedCondition<List<WebElement>> condition) {
        super(webDriver, by, condition);
    }

    /**
     * Method to get Element by text
     *
     * @param objectText text of item to get
     * @return item found or not
     */
    public WebElement getItemByText(final String objectText) {
        return performAction(new ManagedActionWithReturn<WebElement>() {
            @Override
            public WebElement execute() {
                retrieveControlledObject();
                String items = "";
                for (WebElement element : controlledObject) {
                    items = items + "," + element.getText();
                    if (element.getText().contains(objectText)) {
                        return element;
                    }
                }
                println("WARN: items in list are: [" + items + "]");
                throw new MSeleniumException("item " + objectText + " cannot be found in " + by, webDriver);
            }
        });
    }

    /**
     * Method to click on all displayed elements in list
     */
    public void clickDisplayedElements() {
        TracedAction action = ExecutionTracer.traceClickAllAction(webDriver, this);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                int hiddenElements = 0;
                println("there are " + controlledObject.size() + " in the controlled list");
                for (WebElement element : controlledObject) {
                    try {
                        if (element.isDisplayed()) {
                            println("clicking element " + by + "[" + controlledObject.indexOf(element) + "]");
                            element.click();
                            ExecutionTracer.incrementNumberOfClickAction();
                        } else {
                            hiddenElements = hiddenElements + 1;
                        }
                    } catch (StaleElementReferenceException exc) {
                        println("WARN: element " + by + "[" + controlledObject.indexOf(element) + "] was stale");

                    }
                }
                if (hiddenElements > 0) {
                    println("WARN: " + hiddenElements + " elements for " + by
                            + " were not visible - check why scripts only use subset of elements specified by locator");
                }
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Method to get the size of displayed elements.
     *
     * @return size of web elements.
     */
    public int getSizeOfDisplayedElements() {
        int size = 0;
        retrieveControlledObject();
        for (WebElement element : controlledObject) {
            if (element.isDisplayed()) {
                size++;
            }
        }
        return size;
    }
}
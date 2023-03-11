package controls;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import exception.MSeleniumException;
import trace.ExecutionTracer;
import trace.TracedAction;
import util.CommonUtil;

/**
 * This class contains the overridden methods of selenium webdriver and customized them. Also contain the
 * custom methods for different verifications and operations
 *
 * Created by NiTesH bharDWAJ
 */

public class ControlledElement extends ControlledObject<WebElement>
{
    public static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ControlledElement.class);
    // ==============
    // Constructors	=
    // ==============
    /**
     * Initializes a new default instance of the ControlledElement class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public ControlledElement(WebDriver webDriver, By by) {
        super(webDriver, by, ExpectedConditions.elementToBeClickable(by));
    }

    /**
     * Initializes a new default instance of the ControlledElement class with friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param friendlyName Friendly name for the ControlledElement
     */
    public ControlledElement(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, ExpectedConditions.elementToBeClickable(by), friendlyName);
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
                highlightElement();
                if (!controlledObject.isEnabled()) {
                    throw new MSeleniumException("Element " + by + " is defined on the page but is hidden", webDriver);
                }
            }
        });
    }

    /**
     * Method to highlight an element
     *
     */
    public void highlightElement() {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                JavascriptExecutor executor = (JavascriptExecutor) webDriver;
                executor.executeScript("arguments[0].style.border='3px solid red'", controlledObject);
            }
        });
        ExecutionTracer.incrementNumberOfClickAction();
    }

    /**
     * Clicks on element
     */
    public void click() {
        TracedAction action = ExecutionTracer.traceClickAction(webDriver, this);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.click();
            }
        });
        if(webDriver instanceof FirefoxDriver) {
            wait("necessary wait to load the page", 1);
        }
        ExecutionTracer.add(action);
    }

    /**
     * Clicks and check display of given element
     *
     * @param element element to check
     */
    public void clickAndCheck(final ControlledElement element) {
        TracedAction action = ExecutionTracer.traceClickAction(webDriver, this);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.click();
                element.checkDisplay();
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Clicks and check display of given list
     *
     * @param element element list to check
     */
    public void clickAndCheck(final ControlledList element) {
        TracedAction action = ExecutionTracer.traceClickAction(webDriver, this);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.click();
                element.shouldBeDefined();
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Clicks and check other element is displayed as result
     *
     * @param clickFrame frame containing this controlled element
     * @param elementFrame frame of element to check
     * @param element element to check
     */
    public void clickAndCheck(final Frame clickFrame, final Frame elementFrame, final ControlledElement element) {
        TracedAction action = ExecutionTracer.traceClickAction(webDriver, this);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.click();
                elementFrame.retrieveControlledObject();
                element.checkDisplay();
            }

            @Override
            public void reset() {
                clickFrame.retrieveControlledObject();
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Click and check elements in default content
     *
     * @param clickFrame frame containing this controlled element
     * @param elementFrame frame of element to check
     * @param element element to check
     */
    public void clickAndCheckInDefaultContent(final Frame clickFrame, final Frame elementFrame, final ControlledElement element) {
        TracedAction action = ExecutionTracer.traceClickAction(webDriver, this);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.click();
                webDriver.switchTo().defaultContent();
                elementFrame.retrieveControlledObject();
                element.checkDisplay();
            }

            @Override
            public void reset() {
                clickFrame.retrieveControlledObject();
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Click the controlled element in default content frame and check the given element
     *
     * @param elementFrame frame of element to check
     * @param element element to check
     */
    public void clickInDefaultContentAndCheck(final Frame elementFrame, final ControlledElement element) {
        TracedAction action = ExecutionTracer.traceClickAction(webDriver, this);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.click();
                elementFrame.retrieveControlledObject();
                element.checkDisplay();
            }

            @Override
            public void reset() {
                webDriver.switchTo().defaultContent();
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Click element from default frame and then switch to other frame
     *
     * @param frames frames to switch to
     */
    public void clickFromDefaultContentAndSwitch(final Frame... frames) {
        TracedAction action = ExecutionTracer.traceClickAction(webDriver, this);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.click();
                for (Frame frame : frames) {
                    frame.retrieveControlledObject();
                }
            }

            @Override
            public void reset() {
                webDriver.switchTo().defaultContent();
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Execute javaScript script on an element
     *
     * @param script script to execute
     */
    public void executeScript(final String script) {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                JavascriptExecutor executor = (JavascriptExecutor) webDriver;
                executor.executeScript(script, controlledObject);
            }
        });
        ExecutionTracer.incrementNumberOfClickAction();
    }

    /**
     * Execute javascript and check for the display of an element
     *
     * @param script javascript to execute
     * @param element element to check
     */
    public void executeScriptAndCheck(final String script, final ControlledElement element) {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                JavascriptExecutor executor = (JavascriptExecutor) webDriver;
                executor.executeScript(script, controlledObject);
                element.shouldBeDisplayed();
            }
        });
        ExecutionTracer.incrementNumberOfClickAction();
    }

    /**
     * Get text of given element
     *
     * @return text of the element
     */
    public String getText() {
        return performAction(new ManagedActionWithReturn<String>() {
            @Override
            public String execute() {
                retrieveControlledObject();
                return controlledObject.getText();
            }
        });
    }

    /**
     * Get value of the given element
     *
     * @return value of the element
     */
    public String getValue() {
        return performAction(new ManagedActionWithReturn<String>() {
            @Override
            public String execute() {
                retrieveControlledObject();
                return controlledObject.getAttribute("value");
            }
        });
    }

    /**
     * Method to drag and drop an element
     *
     * @param to element to move
     */
    public void dragAndDropTo(final ControlledElement to) {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                Actions action = new Actions(webDriver);
                retrieveControlledObject();
                to.retrieveControlledObject();

                println("Dragging " + controlledObject + " into " + to.getControlledObject());

                Action dragAndDrop = action.clickAndHold(controlledObject).moveToElement(to.getControlledObject()).release().build();
                dragAndDrop.perform();
            }
        });
    }

    /**
     * Method to hover over mouse
     *
     */
    public void hoverOver() {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                Actions action = new Actions(webDriver);
                action.moveToElement(controlledObject).build().perform();
            }
        });
    }

    /**
     * When an element is behind a selected drop-down, the native click can be used
     */
    public void nativeClick() {
        TracedAction action = ExecutionTracer.traceClickAction(webDriver, this);
        executeScript("arguments[0].click();");
        ExecutionTracer.add(action);
    }

    /**
     * Click and check for an element
     *
     * @param element element to check
     */
    public void nativeClickAndCheck(ControlledElement element) {
        TracedAction action = ExecutionTracer.traceClickAction(webDriver, this);
        executeScriptAndCheck("arguments[0].click();", element);
        ExecutionTracer.add(action);
    }

    // ==================
    // Utility commands	=
    // ==================

    /**
     * Checks that text is equal to given value
     *
     * @param expectedText expected value
     */
    public void textShouldEqual(final String expectedText) {
        String actualText = getText();
        if (actualText == null || !actualText.equals(expectedText)) {
            throw new MSeleniumException("Element " + by + " is not equal to " + expectedText + ", actual text is " + actualText, webDriver);
        }
    }

    /**
     * Checks that title is equal to given value
     *
     * @param expectedText expected value
     */
    public void titleShouldEqual(final String expectedText) {
        if (getAttribute("title") == null || !getAttribute("title").equals(expectedText)) {
            throw new MSeleniumException("Title of element " + by + " is not equal to " + expectedText, webDriver);
        }
    }

    /**
     * Checks that value equals to given text
     *
     * @param expectedValue expected text
     */
    public void valueShouldEqual(final String expectedValue) {
        String actualValue = getAttribute("value");
        if (actualValue == null || !actualValue.equals(expectedValue)) {
            throw new MSeleniumException("Title of element " + by + " is not equal to " + expectedValue + ", actual value is " + actualValue, webDriver);
        }
    }

    /**
     * Checks if element text display contains a value
     *
     * @param text expected value
     */
    public void textShouldContain(final String text) {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                TextInElement textInElement = new TextInElement(webDriver, by, text);
                textInElement.shouldBeDefined();
            }
        });
    }

    /**
     * Checks if element text display does not contain a value
     *
     * @param text expected value
     */
    public void textShouldNotContain(String text) {
        retrieveControlledObject();
        if (getText().contains(text)) {
            throw new MSeleniumException("Element " + by + " does not contain " + text, webDriver);
        }
    }

    // ==================
    // Internal methods	=
    // ==================

    /**
     * Checks that element is defined and visible on page
     */
    protected void checkDisplay() {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                if (!controlledObject.isEnabled()) {
                    throw new MSeleniumException("Element " + by + " is defined on the page but is hidden", webDriver);
                }
            }
        });
    }

    /**
     * Get attribute as per given name of attribute
     *
     * @param attribute name of attribute
     * @return value of attribute
     */
    private String getAttribute(final String attribute) {
        return performAction(new ManagedActionWithReturn<String>() {
            @Override
            public String execute() {
                retrieveControlledObject();
                return controlledObject.getAttribute(attribute);
            }
        });
    }

    /**
     * Find element in opened window and if not found then switch to frame and find the element
     *
     * @return true or false
     */
    @Override
    protected boolean findElementInContent() {
        String initialWindowHandle = webDriver.getWindowHandle();
        for (String windowHandle : webDriver.getWindowHandles()) {
            webDriver.switchTo().window(windowHandle);
            boolean isFound = findElementInWindowContent();
            if (isFound) {
                return true;
            }
        }

        println("switching back to initial window " + initialWindowHandle);
        webDriver.switchTo().window(initialWindowHandle);
        println("switching back to initial window successful");

        if (Frame.currentFrameIndentifier.get() != null) {
            final List<WebElement> iframes = webDriver.findElements(By.tagName("iframe"));
            for (WebElement iframe : iframes) {
                String iFrameName = iframe.getAttribute("name");
                String iFrameId = iframe.getAttribute("id");
                if (iFrameName != null && iFrameName.equals(Frame.currentFrameIndentifier.get())) {
                    println("switching back to frame " + iFrameName);
                    webDriver.switchTo().frame(iframe);
                    println("switching back to frame " + iFrameName + " is successful");
                    return false;
                }
                if (iFrameId != null && iFrameId.equals(Frame.currentFrameIndentifier.get())) {
                    println("switching back to frame " + iFrameId);
                    webDriver.switchTo().frame(iframe);
                    println("switching back to frame " + iFrameId + " is successful");
                    return false;
                }
            }
        }

        println("switching to default content");
        webDriver.switchTo().defaultContent();
        println("switching back to default content successful");
        return false;
    }

    /**
     * Find element in window content and return back to initial window
     *
     * @return true or false
     */
    private boolean findElementInWindowContent() {
        String currentWindowHandle = webDriver.getWindowHandle();
        return findElementInWindowContent(currentWindowHandle);
    }

    /**
     * Switch to given window and find element in all iframes
     *
     * @return true or false
     */
    private boolean findElementInWindowContent(String currentHandle) {
//		LOGGER.info(CommonUtil.logPrefix() + currentHandle
//				+ "======================================================================================================================================================================================================================================================================================================================================================================================================================");
//		LOGGER.info(CommonUtil.logPrefix() + webDriver.getPageSource());
//		LOGGER.info(CommonUtil.logPrefix() + currentHandle
//				+ "======================================================================================================================================================================================================================================================================================================================================================================================================================");

        final List<WebElement> iframes = webDriver.findElements(By.tagName("iframe"));
        for (WebElement iframe : iframes) {
            try {
                String iFrameName = iframe.getAttribute("name");
                webDriver.switchTo().frame(iframe);

                if (!by.findElements(webDriver).isEmpty()) {
                    controlledObject = by.findElements(webDriver).get(0);
                    println("ELEMENT " + controlledObject + " FOUND IN CONTENT [" + iFrameName + "]" + " !!!");
                    return true;
                } else {
                    LOGGER.info(CommonUtil.logPrefix() + "by.findElements(webDriver) = " + by.findElements(webDriver));
                    LOGGER.info(CommonUtil.logPrefix() + "by = " + by);

//					LOGGER.info(CommonUtil.logPrefix() + currentHandle + "." + iFrameName
//							+ "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    boolean isFound = findElementInWindowContent(currentHandle + "." + iFrameName);
                    if (isFound) {
                        return true;
                    }
//					LOGGER.info(CommonUtil.logPrefix() + currentHandle + "." + iFrameName
//							+ "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    webDriver.switchTo().parentFrame();
                }
            } catch (StaleElementReferenceException exc) {
                println("WARN: frame " + iframe + " is stale");
            }
        }
        return false;
    }

    /**
     * Click and check that element is not displayed
     *
     * @param element element to check
     */
    public void clickAndCheckNotDisplay(final ControlledElement element) {
        TracedAction action = ExecutionTracer.traceClickAction(webDriver, this);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.click();
                if (element.isFound()) {
                    throw new MSeleniumException("Element was found on the page", webDriver);
                }
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Verify that element is displayed in DOM
     */
    @Override
    public boolean isFound() {
        long findStart = System.currentTimeMillis();
        try {
            webDriver.findElement(by);
            long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
            println(by.toString() + " was found in " + timeToRetrieveElement + "ms");
            return true;
        } catch (WebDriverException exc) {
            long timeToLookForElement = (System.currentTimeMillis() - findStart);
            println("Element " + by + " was not found in " + timeToLookForElement + "ms");
            return false;
        }
    }

    /**
     * Verify disabled element using JavaScriptExecutor for cssSelector locator
     *
     * @return Element disabled or not(true or false)
     */
    public Boolean isElementDisabled() {
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        String object = by.toString().replace("By.cssSelector: ", "");
        WebElement ele = (WebElement) executor.executeScript("return document.querySelector(\"" + object + "\");");
        if (ele.isDisplayed()) {
            return true;
        }
        return false;
    }

}

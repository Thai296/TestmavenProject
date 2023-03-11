package com.simplicia.executor;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import controls.CheckBox;
import controls.Link;
import controls.SearchableDropDown;
import exception.MSeleniumException;
import util.CommonUtil;
import util.CustomWebDriverWait;


public class PageObjects extends SeleniumTestSupport
{

    // ==============
    // Locator
    // ==============
    public static final Logger LOGGER = Logger.getLogger(SeleniumTestSupport.class);
    private final static String TOOLTIP_XPATH = "//div[@role='tooltip']/div/span";

    private Actions actions;

    public PageObjects(WebDriver browser) {
        this.browser = browser;
        actions = new Actions(browser);
    }

    /**
     * Assert that the specified text pattern appears somewhere on the rendered page shown to the user. Throws
     * an AssertionError if it is not present. Does nothing if the text is present.
     *
     * @param pattern - a pattern to match with the text of the page
     */
    @Override
    public void assertTextPresent(String pattern) {
        if (!isTextPresent(pattern)) {
            throw new MSeleniumException("Text '" + pattern + "' not found on page", browser);
        }

    }

    /**
     * Assert that the specified string is present in the given string Throws an AssertionError if
     * stringSearchedFor is not present in the stringBeingSearched. Does nothing if stringSearchedFor is
     * present in the stringBeingSearched
     *
     * @param stringBeingSearched - String expression being searched.
     * @param stringSearchedFor   - String expression searched for.
     * @throws AssertionError if stringSearchedFor is not present in the stringBeingSearched
     */
    public void assertTextContains(String stringBeingSearched, String stringSearchedFor) {

        if (!stringBeingSearched.contains(stringSearchedFor)) {
            throw new MSeleniumException(
                    "String expression: '" + stringSearchedFor + "' is not found in String expression:" + stringBeingSearched + "'",
                    browser);
        }

    }

    /**
     * Assert that the specified string is not present in the given string Throws an AssertionError if
     * stringSearchedFor is present in the stringBeingSearched. Does nothing if stringSearchedFor is not
     * present in the stringBeingSearched
     *
     * @param stringBeingSearched - String expression being searched.
     * @param stringSearchedFor   - String expression searched for.
     * @throws AssertionError if stringSearchedFor is present in the stringBeingSearched
     */

    public void assertTextNotContains(String stringBeingSearched, String stringSearchedFor) {
        if (stringBeingSearched.contains(stringSearchedFor)) {
            throw new AssertionError(
                    "String expression: '" + stringSearchedFor + "' is found in String expression:" + stringBeingSearched + "'");
        }

    }

    @Override
    public boolean isTextPresent(String text) {
        try {
            return browser.getPageSource().contains(text);
        } catch (Exception exc) {
            throw new MSeleniumException(exc.getMessage(), browser);
        }
    }

    /**
     * Waits for a new page to be displayed
     */
    public void waitForNewPage() {
        try {
            Thread.sleep(SeleniumTestSupport.PAGE_LOAD_TIME_IN_SECONDS * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Take a screenshot
     */
    public void addScreenshot() {
        SeleniumTestSupport.addScreenshot(browser);
    }

    /**
     * Check if given values are the same
     *
     * @param expected expected values
     * @param value    computed value
     */
    public void assertTextEquals(String expected, String value) {
        if (!expected.equals(value)) {
            throw new MSeleniumException("Text from page '" + value + "' is not equal to '" + expected + "'", browser);
        }
    }

    /**
     * Check if given values are not same
     *
     * @param expected expected values
     * @param value    computed value
     */
    public void assertTextNotEquals(String expected, String value) {
        if (expected.equals(value)) {
            throw new AssertionError("Text '" + value + "' is equal to " + expected);
        }
    }

    /**
     * Click on a link.
     *
     * @param element
     */
    public void clickLink(String element) {
        Link link = new Link(browser, By.linkText(element));
        link.shouldBeDisplayed();
        link.click();
    }

    /**
     * Click on a link using java script.
     *
     * @param element
     */
    public void clickNativeLink(String element) {
        Link link = new Link(browser, By.linkText(element));
        link.shouldBeDisplayed();
        link.nativeClick();
    }

    /**
     * Method to check presence of link.
     *
     * @param element Element to be present
     * @return boolean return true if link present.
     */
    public boolean isLinkPresent(String element) {
        Link link = new Link(browser, By.linkText(element));
        return link.isFound();
    }

    /**
     * Click on partial link.
     *
     * @param element
     */
    public void clickPartialLink(String element) {
        Link link = new Link(browser, By.partialLinkText(element));
        link.shouldBeDisplayed();
        link.click();
    }

    /**
     * Click on a link.
     *
     * @param element
     */
    @Deprecated
    public void clickAndWait(String element) {
        clickLink(element);
    }

    /**
     * wait for the default wait time
     * <p>
     * IMPORTANT: IN ORDER TO ONLY WAIT NECESSARY TIME, THIS SHOULD BE USED ONLY WHEN THERE IS NO EXPECTED
     * CONTROLLEDELEMENT THAT CAN BE CHECKED
     *
     * @param reason reason for waiting
     */
    public void wait(String reason) {
        wait(reason, 1);
    }

    /**
     * wait for the default wait time
     * <p>
     * IMPORTANT: IN ORDER TO ONLY WAIT NECESSARY TIME, THIS SHOULD BE USED ONLY WHEN THERE IS NO EXPECTED
     * CONTROLLEDELEMENT THAT CAN BE CHECKED
     *
     * @param reason reason for waiting
     * @param factor amplitude of wait
     */
    public void wait(String reason, int factor) {
        try {
            LOGGER.info(CommonUtil.logPrefix() + "WARN: necessary waiting for " + factor * PAGE_LOAD_TIME_IN_SECONDS
                    + " seconds " + reason);
            Thread.sleep(factor * PAGE_LOAD_TIME_IN_SECONDS * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks whether a tooltip contains given text
     *
     * @param xpath        - Xpath to the webpage element to be hovered over
     * @param requiredText - Text checked if exists in tooltip
     */
    public void checkSearchViewTooltipForText(String xpath, String... requiredText) {
        // Finds and hovers over the element
        WebElement link = this.findElement(By.xpath(xpath));
        actions.moveToElement(link).perform();

        // Finds the tooltip and gets all the text within it
        List<WebElement> hoverOver = this.findElements(By.xpath(TOOLTIP_XPATH));
        StringBuilder tooltipTextBuilder = new StringBuilder();
        String tooltipText;
        for (int i = 0; i < hoverOver.size(); i++) {
            String info = hoverOver.get(i).getText().trim();
            tooltipTextBuilder.append(info);
        }
        tooltipText = tooltipTextBuilder.toString();

        // Checks the tooltip text for the required text
        for (String requirement : requiredText) {
            if (!tooltipText.contains(requirement)) {
                throw new MSeleniumException("Text '" + requirement + "' not found in tooltip", browser);
            }
        }
    }

    /**
     * Sets a checkbox's status
     *
     * @param checkbox - Checkbox to change
     * @param checked  - Checkbox's intended value
     */
    public void setCheckboxStatus(CheckBox checkbox, boolean checked) {
        checkbox.shouldBeDisplayed();
        if (checkbox.isChecked() != checked) {
            checkbox.click();
        }
    }

    /**
     * Wait for element to be invisible
     *
     * @param xpath xpath of the element
     */
    public void waitForElementToBeInVisible(String xpath) {
        waitForElementToBeInVisible(By.xpath(xpath));
    }

    public void waitForElementToBeInVisible(By by) {
        CustomWebDriverWait wait = new CustomWebDriverWait(browser, 15);
        wait.until(ExpectedConditions.visibilityOf(browserFindElement(by)));
    }
    public void waitForElementToBeVisible(String xpath) {
        waitForElementToBeVisible(By.xpath(xpath));
    }

    public void waitForElementToBeVisible(By by) {
        WebElement webElement = this.findElement(by);
        CustomWebDriverWait wait = new CustomWebDriverWait(browser, 15);
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void waitForElementToBePresence(By by) {
        CustomWebDriverWait wait = new CustomWebDriverWait(browser, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
    
    public void waitForElementVisibilityOfElementLocated(String locator) {
        CustomWebDriverWait wait = new CustomWebDriverWait(browser, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byXpath(locator)));
    }

    public void waitForElementToBeClickable(By by) {
        WebElement webElement = this.findElement(by);
        CustomWebDriverWait wait = new CustomWebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    /**
     * Method to select value in the searchable drop down when id of element is there
     *
     * @param list_div_id
     * @param value
     */
    public void selectSearchableDropDownListByTyping(String list_div_id, String value) {
        new SearchableDropDown(browser, By.id(list_div_id),
                By.xpath("//div[@id='" + list_div_id + "']//div[@class='chosen-drop']//div[@class='chosen-search']//input[@type='text']"),
                "Selecting value in searchable dropdown").selectByTyping(value);
    }

    /**
     * Method to navigate to fields using actions.
     *
     * @param str_Element element need to be set
     */
    public void navigateToFieldsUsingActions(String str_Element) {
        Actions actions = new Actions(browser);
        actions.moveToElement(this.findElement(By.xpath(str_Element))).perform();
    }

    /**
     * Method to perform scroll to element
     *
     * @param str_Element element need to be set
     */
    public void performScrollToElement(WebElement str_Element) {

        // This will scroll the page till the element is found
        JavascriptExecutor js = (JavascriptExecutor) browser;
        js.executeScript("arguments[0].scrollIntoView();", str_Element);
    }

    public void performScrollToTopOfElement(WebElement str_Element) {
        JavascriptExecutor js = (JavascriptExecutor) browser;
        js.executeScript("arguments[0].scrollTo(0, 0);", str_Element);
    }

    /**
     * Method to perform scroll
     */
    public void performHorizontalScroll() {
        JavascriptExecutor jse = (JavascriptExecutor) browser;
        jse.executeScript("document.querySelector('table th:last-child').scrollIntoView();");
    }

    public void performHorizontalBackScroll() {
        JavascriptExecutor jse = (JavascriptExecutor) browser;
        jse.executeScript("document.querySelector('table th:first-child').scrollIntoView();");
    }


    /**
     * Method to perform scroll to element
     */
    public void performScrollToELementJS() {
        JavascriptExecutor jse = (JavascriptExecutor) browser;
        jse.executeScript("document.querySelector(\"input[aria-label='ATMP']\").scrollIntoView();");
    }


    /**
     * Method to perform scroll vertically down
     */
    public void performVerticalDownScroll() {
        JavascriptExecutor jse = (JavascriptExecutor) browser;
        jse.executeScript("window.scrollBy(0,1000)");
    }

    /**
     *
     * Scroll to element by xpath
     */
    public  void performScrollByXpath(String xpath) {
        WebElement element = this.findElement(By.xpath(xpath));
        if (!element.isDisplayed()) {
            performScrollToElement(element);
        }
    }

    public void ScrollToElementByTab(String str_Xpath) {
        tabKey(By.xpath(str_Xpath));
    }

    protected void tabKey(By xpath) {
        retry(() -> {
            WebElement element = this.findElement(xpath);
            performScrollToElement(element);
            element.sendKeys(Keys.TAB);
        });
    }


    protected void downKey(By xpath) {
        retry(() -> {
            WebElement element = this.findElement(xpath);
            performScrollToElement(element);
            element.sendKeys(Keys.ARROW_DOWN);
        });
    }

    protected void closeTheLeftNavigation() {
        retry(() -> {
            try {
                Actions actions = new Actions(browser);
                WebElement body = this.findElement(By.xpath("//body"));
                actions.moveToElement(body, 400, 400).build().perform();
            } catch (Exception e) {
                Actions actions = new Actions(browser);
                WebElement nameBoxTopRight = this.findElement(By.xpath("//header//div[text() = 'P']"));
                actions.moveToElement(nameBoxTopRight).build().perform();
            }
        });
    }

    protected WebElement findElement(By by) {
        WebElement ele = browserFindElement(by);
        try {
            ele.isDisplayed(); // test if the element is detached and retry
        } catch (StaleElementReferenceException e) {
            LOGGER.info("Warning: StaleElementReferenceException finding " + by.toString() + ", error: " + e.getMessage());
            sleepSilently(1000);
            ele = browserFindElement(by);
        }
        return ele;
    }

    protected List<WebElement> findElements(By by) {
        List<WebElement> elements = browserFindElements(by);
        try {
            for (WebElement element : elements) {
                element.isDisplayed(); // test if the element is detached and retry
            }
        } catch (StaleElementReferenceException e) {
            LOGGER.info("Warning: StaleElementReferenceException finding " + by.toString() + ", error: " + e.getMessage());
            sleepSilently(2000);
            elements = browserFindElements(by);
        }
        return elements;
    }
    protected By byXpath(String locator) {
		return By.xpath(locator) ;
    }

    protected WebElement browserFindElement(By by) {
        try {
            return browser.findElement(by);
        } catch (Exception e) {
            throw new MSeleniumException("Unable to find element for " + by, e, browser);
        }
    }

    public boolean isElementPresent(WebElement element, int time)
	{
		boolean flag = false;
		int i = 0;
		while (i < time) {
			if (element.isDisplayed() || element.isEnabled()) {
				LOGGER.info("Web element is found: "+ element);
				flag = true;
				break;
			} else {
				LOGGER.error("Web element is not found: " + element);
				i++;
			}
		}
		return flag;
	}

    private List<WebElement> browserFindElements(By by) {
        try {
            return browser.findElements(by);
        } catch (Exception e) {
            throw new MSeleniumException("Unable to find elements for " + by, e, browser);
        }
    }
}

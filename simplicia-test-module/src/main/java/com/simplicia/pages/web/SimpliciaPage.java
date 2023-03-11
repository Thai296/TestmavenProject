package com.simplicia.pages.web;

import com.simplicia.executor.PageObjects;
import com.simplicia.functions.Utility;
import controls.ControlledElement;
import controls.Header;
import controls.TextField;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.MessageFormat;
import java.util.List;

public class SimpliciaPage extends PageObjects {
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(SimpliciaPage.class);

    //==========
    // Locator =
    //==========
    final static protected String FOOTER_TEXT = "//p[text()='Copyright © 2022 Simplicia, All rights reserved.']";
//    final static protected String XPATH_OPT_LIST_OPTION = "//ul[@class=''MuiList-root MuiMenu-list MuiList-padding'']/li[contains(text(),''{0}'')]";
    final static protected String XPATH_OPT_LIST_OPTION = "//ul[contains(@class, 'MuiMenu-list')]/li[contains(text(),''{0}'')]";
    final static protected String XPATH_OPT_LIST_FILTER_OPTION = "//ul[contains(@class, 'MuiMenu-list')]/li[contains(text(),''{0}'')]";

    public SimpliciaPage(WebDriver browser) {
        super(browser);
    }

    public void checkPresence(ControlledElement ele) {
        sleepSilently(500);
        scrollToIfNotDisplayed(ele.by);
        ele.shouldBeDisplayed();
    }

    public void checkPresence(By by) {
        checkPresence(by, null);
    }

    public void checkPresence(By by, String friendlyName) {
        ControlledElement ele = new ControlledElement(browser, by, friendlyName);
        checkPresence(ele);
    }

    public void checkPresenceWait(ControlledElement ele) {
        checkPresenceWait(ele.by);
    }

    public void checkPresenceWait(By by) {
        LOGGER.info("finding controlled object " + by);
        long findStart = System.currentTimeMillis();
        Utility.waitForElement(browser, 5, ExpectedConditions.presenceOfElementLocated(by));
        long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        LOGGER.info(by.toString() + " was found in " + timeToRetrieveElement + "ms");
    }


    public <V> void waitForCondition(ExpectedCondition<V> condition) {
        LOGGER.info("waiting for condition: " + condition.toString());
        long findStart = System.currentTimeMillis();
        Utility.waitForElement(browser, 5, condition);
        long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        LOGGER.info("condition met: " + condition.toString() + ", in " + timeToRetrieveElement + "ms");
    }

    public void click(By by) {
        ControlledElement ele = new ControlledElement(browser, by);
        click(ele);
    }

    public void click(ControlledElement ele) {
        LOGGER.info("Clicking element " + ele.by);
        ele.click();
        LOGGER.info("Clicked element " + ele.by);
    }

    public void fillWithText(By by, String text) {
        checkPresence(by);
        TextField txt = new TextField(browser, by);
        LOGGER.info("Filling '" + text + "' in element " + by);
        txt.clearAndTypeKeys(text);
        LOGGER.info("Filled '" + text + "' in element " + by);
    }

    public void expectText(By by, String expectedText) {
        ControlledElement ele = new ControlledElement(browser, by);
        ele.textShouldEqual(expectedText);
    }

    public void expectTextContain(By by, String expectedText) {
        ControlledElement ele = new ControlledElement(browser, by);
        ele.textShouldContain(expectedText);
    }

    public void waitForElement(By by, String friendlyName) {
        waitForCondition(new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return _apply();
                } catch (StaleElementReferenceException e) {
                    sleepSilently(1000);
                    // retry because of DOM update
                    return _apply();
                }
            }

            private Boolean _apply() {
                List<WebElement> eles = browser.findElements(by);
                if (eles.size() > 0) {
                    for (WebElement ele : eles) {
                        if (!ele.isDisplayed()) {
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            }

            @Override
            public String toString() {
                return "Wait for '" + friendlyName + "': " + by;
            }
        });
    }

    public void waitForElementToDisappear(By by, String friendlyName) {
        waitForCondition(new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return _apply();
                } catch (StaleElementReferenceException e) {
                    sleepSilently(1000);
                    // retry because of DOM update
                    return _apply();
                }
            }

            private Boolean _apply() {
                List<WebElement> eles = browser.findElements(by);
                if (eles.size() == 0) {
                    return true;
                }
                for (WebElement ele : eles) {
                    if (ele.isDisplayed()) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public String toString() {
                return "Wait for '" + friendlyName + "' to disappear: " + by;
            }
        });
    }

    public void scrollToIfNotDisplayed(By by) {
        WebElement ele = findElement(by);
        if (!ele.isDisplayed()) {
            performScrollToElement(ele);
        }
    }

    /** select an item in the dropdown list that is being active. */
    public void selectItemFromDropDownList(String str_ListOption) {
        String sOption = str_ListOption;
        boolean c = str_ListOption.contains("\'");
        if (c) {
            String[] s = str_ListOption.split("\'");
            sOption = s[1];
        }

        By xpath = By.xpath(MessageFormat.format(XPATH_OPT_LIST_OPTION, sOption));
        ControlledElement listOption = new Header(browser, xpath);

        checkPresence(listOption);
        click(listOption);
    }

    /** select an item in the dropdown list that is being active. */
    public void selectItemFromDropDownFilter(String str_ListOption) {
        String sOption = str_ListOption;
        boolean c = str_ListOption.contains("\'");
        if (c) {
            String[] s = str_ListOption.split("\'");
            sOption = s[1];
        }

        By xpath = By.xpath(MessageFormat.format(XPATH_OPT_LIST_FILTER_OPTION, sOption));
        ControlledElement listOption = new Header(browser, xpath);

        checkPresence(listOption);
        click(listOption);
    }
}
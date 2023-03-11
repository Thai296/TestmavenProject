package com.simplicia.pages.web;

import com.simplicia.executor.PageObjects;
import controls.TableElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.CustomWebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SmartPage extends PageObjects {

    private ArrayList<By> arrBy;
    public ArrayList<By> getArrBy() {
        return arrBy;
    }

    public void setArrBy(ArrayList<By> arrBy) {
        this.arrBy = arrBy;
    }

    private String [] tags = {"h1", "a", "input", "select", "textarea", "ul/li", "table", "label", "img", "p", "span", "h2"};

    final static String TABLE = "//table";
    public SmartPage(WebDriver browser) {
        super(browser);
        arrBy = new ArrayList<>();

    }

    private String upper1stChar(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private String removeOtherChars(String str) {
        return str.replaceAll("(?U)(?<=[0-9])[\\W_]+(?=[0-9])", "").trim();
    }

    private boolean isUniqueElement(By by) {
        List<WebElement> elements = this.browser.findElements(by);
        return elements.size() == 1;
    }

    public void dumpElementInfo() {

    }

    public void waitForPageLoadSuccess() {

    }

    public void waitForEleXPathContainsText(String xpathExpression, String text) {
        By locator = By.xpath(xpathExpression + "[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqorstuvwxyz')" + "," + text + ")]");
        new CustomWebDriverWait(this.browser, TIME_OUT_IN_SECONDS).
                until(ExpectedConditions.textToBePresentInElementValue(locator, text));
    }

    public void waitForEleAttribute(By xpath, String attribute, String value) {

    }

    public void waitForEleDisappear() {

    }

    public void navigateURL(String url) {
        this.browser.get(url);
    }

    // rootElement = empty mean from anywhere
    public HashMap scanElements(String rootElement) {
        HashMap<String, String> map = new HashMap<String, String>();
        for(int i = 0; i < tags.length; i++) {
            List<WebElement> elements = this.browser.findElements(By.xpath(rootElement + "//" + tags[i]));
            for (int j=0; j<elements.size(); j++){
                WebElement element = elements.get(j);
                String id = element.getAttribute("id");
                String title = element.getAttribute("title");
                String value = element.getAttribute("value");
                String tagName = element.getTagName();
                String text = element.getText();
                boolean display = element.isDisplayed();
                if (display == false) continue;
                String key = "";
                String xpath = "";
                if (id!=null && !id.isEmpty()) {
                    key = tagName + id;
                    xpath = rootElement + "//" + tagName + "[contains(@id,\"" + id + "\")]";
                } else if (title!=null && !title.isEmpty()) {
                    key = tagName + this.upper1stChar(this.removeOtherChars(title));
                    xpath = rootElement + "//" + tagName + "[contains(translate(@title,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqorstuvwxyz')" + ",\"" + title.toLowerCase().trim() + "\")]";
                } else if (text!=null && !text.isEmpty() && text.length()<50) {
                    key = tagName + this.upper1stChar(this.removeOtherChars(text));
                    xpath = rootElement + "//" + tagName + "[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqorstuvwxyz')" + ",\"" + text.toLowerCase().trim() + "\")]";
                } else if (value!=null && !value.isEmpty()) {
                    key = tagName + this.upper1stChar(this.removeOtherChars(value));
                    xpath = rootElement + "//" + tagName + "[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqorstuvwxyz')" + ",\"" + value.toLowerCase().trim() + "\")]";
                }
                if (!xpath.isEmpty() && this.isUniqueElement(By.xpath(xpath))) {
                    map.put(key, xpath);
                    LOGGER.info("Found: " + xpath);
                } else {
                    //LOGGER.info("Ignore because not unique." + xpath);
                }
            }
        }
        return map;
    }

    public void generatePOM(String filePath, HashMap<String, String> map) {

    }

    public void testTable() {
        try {
            TableElement tableElement = new TableElement(browser, By.xpath(TABLE));
           // int rowCount = tableElement.getRowCount();
            //int colCount = tableElement.getColumnCount();
            //WebElement rowEle = tableElement.findRowByText("200000000111112");
            WebElement btnDashboard = tableElement.findButtonInRow("200000000111112","dashboard");
            btnDashboard.click();
            LOGGER.info("haha");
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }

    public WebElement getBtnInContextPopup(String text) {
        String xpath = "//button[contains(.,\"" + text +" \")]";
        this.waitForElementToBeVisible(By.xpath(xpath));
        WebElement btn = this.findElement(By.xpath(xpath));
        return btn;
    }
}

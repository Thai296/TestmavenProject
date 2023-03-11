package com.simplicia.pages.web.leftnavigationmenu;

import com.simplicia.pages.web.SimpliciaPage;
import com.simplicia.pages.web.utils.TestData;

import controls.ControlledElement;
import controls.Icon;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.MessageFormat;


public class LeftNavigationMenuPage extends SimpliciaPage
{
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(LeftNavigationMenuPage.class);

    //==========
    // Locator =
    //==========
    final static String HEADER_CLIENT_NAME = "//header/p[contains(text(),''{0}'')]";
    final static String SUB_MENU_ITEM = "//span[contains(text(),''{0}'')]";
    final static String EXPAND_LESS_ICON = "//span[text()=''{0}'']/../..//span[text()=''expand_less'']";
//    final static String EXPAND_MORE_ICON = "//span[text()=''{0}'']/../..//span[text()=''expand_more'']";
    final static String EXPAND_MORE_ICON = "//span[contains(text(),''{0}'')]//following::span[1]//span[text()=''expand_more'']";
    final static String EXPAND_MORE_LESS_SPAN = "//span[text()=''{0}'']/../..//span[text()=''expand_more''] | //span[text()=''{0}'']/../..//span[text()=''expand_less'']";
    final static String SIMPLICIA_IMG = "//img[@src='assets/images/logos/simplicia_logo.png']";
    final static String SAAS_TEXT = "//p[contains(text(),'SaaS')]"; //"//p[text()='SaaS']";
    final static String WEB_TEXT = "//p[text()=''{0}'']";
    final static String USER_IMAGE = "//img[@src='assets/images/avatars/profile.jpg']";

    final static String MAIN_MENU_DAT = "//*[@id=\"fuse-navbar\"]/div/div/div/ul/ul[1]/li";

    final static String MAIN_MENU_LOGOUT = "//*[@id=\"fuse-navbar\"]/div/div/div/ul/a[8]";
    final static String MAIN_MENU_HOME= "//img[@alt='logo']";

    //====================
    // Selenium Controls =
    //====================
    private final ControlledElement simpliciaImage;
    private final ControlledElement saasText;
    private final ControlledElement userImage;
    public final ControlledElement menuDAT;

    public final ControlledElement menuHome;

    public LeftNavigationMenuPage(WebDriver browser) {
        super(browser);
        simpliciaImage = new ControlledElement(browser, By.xpath(SIMPLICIA_IMG));
        saasText = new ControlledElement(browser, By.xpath(SAAS_TEXT));
        userImage = new ControlledElement(browser, By.xpath(USER_IMAGE));
        menuDAT = new ControlledElement(browser, By.xpath(MAIN_MENU_DAT));
        menuHome = new ControlledElement(browser, By.xpath(MAIN_MENU_HOME));
    }

    public void clickToOpenMenu() {
        menuHome.click();
        sleepSilently(1000);
    }

    // =============
    // Check actions
    // =============

    /**
     * Check Presence of Expand More Icon of Menu Item
     *
     * @param str_MenuItem menu item name
     */
    public void checkPresenceOfExpandMoreIcon(String str_MenuItem) {
        Icon expandMoreIcon = new Icon(browser, By.xpath(MessageFormat.format(EXPAND_MORE_ICON, str_MenuItem)),"Expand More Icon");
        expandMoreIcon.shouldBeDisplayed();
    }

    /**
     * Check Presence of Expand less Icon of Menu Item
     *
     * @param str_MenuItem menu item name
     */
    public void checkPresenceOfExpandLessIcon(String str_MenuItem) {
        Icon expandLessIcon = new Icon(browser, By.xpath(MessageFormat.format(EXPAND_LESS_ICON, str_MenuItem)),"Expand Less Icon");
        expandLessIcon.shouldBeDisplayed();
    }

    // ================
    // navigate actions
    // ================

    /**
     * Method to check presence of Simplicia Logo
     */
    public void checkPresenceSimpliciaImage(){
        simpliciaImage.shouldBeDisplayed();
        navigateToFieldsUsingActions(SIMPLICIA_IMG);
    }

    /**
     * Method to check presence of Saas Text
     */
    public void CheckPresenceOfSaasText(){
        saasText.shouldBeDisplayed();
    }

    /**
     * Method to check presence of web Text
     *
     * @param str_Text need to be set
     */
    public void checkPresenceWebText(String str_Text){
        ControlledElement webText = new ControlledElement(browser, By.xpath(MessageFormat.format(WEB_TEXT ,str_Text)));
        webText.shouldBeDisplayed();
    }

    /**
     * Method to check presence of User image
     */
    public void CheckPresenceOfUserImage(){
        userImage.shouldBeDisplayed();
    }

    public void checkPresenceOfLeftNavigationHeader(String headerName) {
        By xpath = By.xpath(MessageFormat.format(HEADER_CLIENT_NAME, headerName));
        ControlledElement header = new ControlledElement(browser, xpath);
        WebElement element = this.findElement(xpath);
        if (!element.isDisplayed()) {
            performScrollToElement(element);
        }
        header.shouldBeDisplayed();
    }

    /**
     * Check presence of menu option in left navigation menu
     *
     * @param str_Item menu item name
     */
    public void checkPresenceOfLeftNavigationMenuOption(String str_Item) {
        ControlledElement menuItem = new ControlledElement(browser, By.xpath(MessageFormat.format(SUB_MENU_ITEM, str_Item)), "Menu Item");
        WebElement element = this.findElement(By.xpath(MessageFormat.format(SUB_MENU_ITEM, str_Item)));
        if (!element.isDisplayed()) {
            performScrollToElement(element);
        }
        sleepSilently(3000);
        menuItem.shouldBeDisplayed();
    }

    public void checkPresenceOfMenuOption(String str_Item) {
        performScrollToElement(this.findElement(By.xpath(MessageFormat.format(SUB_MENU_ITEM, str_Item))));
    }

    /**
     * Check absence of menu option in left navigation menu
     *
     * @param str_Item menu item name
     */
    public void checkAbsenceOfLeftNavigationMenuOption(String str_Item) {
        ControlledElement menuItem = new ControlledElement(browser, By.xpath(MessageFormat.format(SUB_MENU_ITEM, str_Item)),"Menu Item");
        menuItem.shouldNotBeDisplayed();
    }

    // =============
    // Click actions
    // =============

    /**
     * Click Expand more Icon of Menu Item
     *
     * @param str_MenuItem menu item name
     */
    public void clickExpandIconToOpenMenu(String str_MenuItem) {
        this.waitForElementToBeClickable(By.xpath(MessageFormat.format(EXPAND_MORE_ICON, str_MenuItem)));
//        if (!isExpandMore(str_MenuItem)) {
//            println("Skipping clicking explain_more on " + str_MenuItem);
//            return;
//        }
        //this.checkPresence(By.xpath(MessageFormat.format(EXPAND_MORE_ICON, str_MenuItem)));
        Icon expandMoreIcon = new Icon(browser, By.xpath(MessageFormat.format(EXPAND_MORE_ICON, str_MenuItem)),"Expand More Icon");
        expandMoreIcon.waitForDisplayed();
        expandMoreIcon.shouldBeDisplayed();
        expandMoreIcon.click();
        sleepSilently(2000);

    }

    /**
     * Click Expand less Icon of Menu Item
     *
     * @param str_MenuItem menu item name
     */
    public void clickExpandLessIconToCloseMenu(String str_MenuItem) {
        if (!isExpandLess(str_MenuItem)) {
            LOGGER.info("Skipping clicking expand_less on " + str_MenuItem);
            return;
        }
        Icon expandLessIcon = new Icon(browser, By.xpath(MessageFormat.format(EXPAND_LESS_ICON, str_MenuItem)),"Expand Less Icon");
        expandLessIcon.shouldBeDisplayed();
        expandLessIcon.click();
    }

    public boolean isExpandMore(String str_MenuItem) {
        return isExpand(str_MenuItem, "expand_more");
    }

    public boolean isExpandLess(String str_MenuItem) {
        return isExpand(str_MenuItem, "expand_less");
    }

    private boolean isExpand(String str_MenuItem, String moreOrLess) {
        String xpath = MessageFormat.format(EXPAND_MORE_LESS_SPAN, str_MenuItem);
        WebElement element = this.findElement(By.xpath(xpath));
        if (element == null) {
            LOGGER.info(xpath + " --> Element null");
            return false;
        }
        String eleText = element.getAttribute("innerText");
        LOGGER.info(xpath + " --> Element text " + eleText);
        return element != null && moreOrLess.equals(eleText);
    }

    /**
     * Click sub menu Item of opened menu
     *
     * @param str_SubItem menu item name
     */
    public void clickMenuItemInLeftNavigation(String str_SubItem) {
        ControlledElement atmpMenuSubItem = new ControlledElement(browser, By.xpath(MessageFormat.format(SUB_MENU_ITEM, str_SubItem)),"Sub Menu Item");
        atmpMenuSubItem.waitForDisplayed();
        atmpMenuSubItem.shouldBeDisplayed();
        atmpMenuSubItem.click();
        sleepSilently(1000);
    }
    public void openMyDATSPage(){
        browser.get(TestData.MY_DATS_URL);
        sleepSilently(5000);
    }

    /**
     * Close left nav menu
     */
    public void closeLeftNavigation() {
        closeTheLeftNavigation();
    }
}

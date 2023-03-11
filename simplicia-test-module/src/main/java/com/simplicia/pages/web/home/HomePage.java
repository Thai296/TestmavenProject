package com.simplicia.pages.web.home;

import com.simplicia.executor.PageObjects;
import com.simplicia.functions.Utility;
import controls.ControlledElement;
import controls.DisabledElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.CommonUtil;
import util.CustomWebDriverWait;

import java.text.MessageFormat;

public class HomePage extends PageObjects {

    //==========
    // Locator =
    //==========
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(HomePage.class);

    final static String SIMPLICIA_IMG = "//img[@src='assets/images/logos/simplicia.svg']";
    final static String ACCEUIL_HEADING = "//h5[text()='Accueil']";
//    final static String ACCEUIL_HEADING = "//h5[text()='Home']";
    final static String ACCEUIL_MENU_HOME_ICON = "//span[text()='home']";

    final static String FUSE_NAVBAR = "//div[@id='fuse-navbar']";
    final static String PAGE = "//a[@aria-current='page']";
    final static String ACCEUIL_ROOT_MENU_ICON = "//ul/li/span[contains(text(),'Menu')]";
    final static String MY_ACCOUNT_MENU_ICON = "//span[text()='account_circle']";
    final static String DE_CONNECTER_BUTTON = "//span[text()='Se Déconnecter']";
//    final static String DE_CONNECTER_BUTTON = "//a[@href='/logout']";
    final static String BLOCK_ON_HOME = "//p[contains(text(),''{0}'')]";

    final static String BLOCK_ON_HOME_NEW = "//h2[contains(text(),\"{0}\")]";
    final static String WEB_FOOTER = "//p[contains(text(),'Copyright ©')]";
    final static String SELECTED_YEAR = "//p[text()=''{0}'']/../..//button/span[text()=''{1}'']";

    //====================
    // Selenium Controls =
    //====================
    private final ControlledElement simpliciaImage;
    private final ControlledElement acceuilHeading;
    private final ControlledElement acceuilMenuHomeIcon;
    private final ControlledElement myAccountMenuIcon;
    private final ControlledElement deConnecterButton;
    private final ControlledElement webFooter;

    public HomePage(WebDriver browser){
        super(browser);
        simpliciaImage = new ControlledElement(browser, By.xpath(SIMPLICIA_IMG));
        acceuilHeading = new ControlledElement(browser, By.xpath(ACCEUIL_HEADING));
        acceuilMenuHomeIcon = new ControlledElement(browser, By.xpath(ACCEUIL_MENU_HOME_ICON));
        myAccountMenuIcon = new ControlledElement(browser, By.xpath(MY_ACCOUNT_MENU_ICON));
        deConnecterButton = new ControlledElement(browser, By.xpath(DE_CONNECTER_BUTTON));
        webFooter = new ControlledElement(browser, By.xpath(WEB_FOOTER));
    }

    //================
    // Check actions =
    //================

    /**
     * Method to check presence of Simplicia Logo Text
     */
    public void checkPresenceOfAcceuilHeading(){
    	long findStart = System.currentTimeMillis();
        Utility.waitForElement(browser, 10, ExpectedConditions.presenceOfElementLocated(By.xpath(ACCEUIL_HEADING))).isDisplayed();
    	long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
    	LOGGER.info(By.xpath(ACCEUIL_HEADING) + " was found in " + timeToRetrieveElement + "ms");
    	JavascriptExecutor executor = (JavascriptExecutor) browser;
        executor.executeScript("arguments[0].style.border='3px solid red'", this.findElement(By.xpath(ACCEUIL_HEADING)));
    }

    /**
     * Method to check presence of web Footer Text
     */
    public void checkPresenceOfFooterText(){
        webFooter.shouldBeDisplayed();
    }

    /**
     * Method to check presence of Block on Home
     *
     * @param str_BlockName block name need to be set
     */
    public void checkPresenceOfBlockOnHome(String str_BlockName){
        ControlledElement blockOnHomePage = new ControlledElement(browser, By.xpath(MessageFormat.format(BLOCK_ON_HOME,str_BlockName)));
        WebElement element = this.findElement(By.xpath(MessageFormat.format(BLOCK_ON_HOME, str_BlockName)));
        if(!(element.isDisplayed())) {
            performScrollToElement(element);
        }
        blockOnHomePage.shouldBeDisplayed();
    }

    /**
     * Method to check presence of Block on Home by h2 tag
     *
     * @param str_BlockName block name need to be set
     */
    public void checkPresenceOfBlockOnHomeNew(String str_BlockName){
        ControlledElement blockOnHomePage = new ControlledElement(browser, By.xpath(MessageFormat.format(BLOCK_ON_HOME_NEW,str_BlockName)));
        WebElement element = this.findElement(By.xpath(MessageFormat.format(BLOCK_ON_HOME_NEW, str_BlockName)));
        if(!(element.isDisplayed())) {
            performScrollToElement(element);
        }
        blockOnHomePage.shouldBeDisplayed();
    }

    /**
     * Method to check presence of Selected Year
     *
     * @param str_Block block name need to be set
     * @param str_Year year need to be set
     */
    public void checkPresenceOfSelectedYear(String str_Block, String str_Year){
        DisabledElement selectedYear = new DisabledElement(browser, By.xpath(MessageFormat.format(SELECTED_YEAR, str_Block, str_Year)));
        selectedYear.shouldBeDisplayed();
    }

    //================
    // Click actions =
    //================

    public void openLeftNavigation() {
        navigateToSimpliciaImage();
    }

    public void openLeftPanel() {
        navigateToSimpliciaImage();
    }

    public void closeLeftPanel() {
        _closeLeftNavigation();
        _navigateToSimpliciaImage();
        _closeLeftNavigation();
    }
    /**
     * Method to check presence of Simplicia Logo Text
     */
    public void navigateToSimpliciaImage(){
        // sometimes the left menu just doesn't open up, we need to try a few times
        try {
            this.clickLeftMenu();
        } catch (Exception e) {
            CommonUtil.takeScreenshot(browser);
            LOGGER.info("Couldn't click the left Menu, fallback to moving mouse");
            _navigateToSimpliciaImage();
            _closeLeftNavigation();
            _navigateToSimpliciaImage();
        }
    }

    public void moveToLeftMenu() {
    	Actions actions = new Actions(browser);
    	actions.moveToElement(this.findElement(By.xpath(FUSE_NAVBAR))).perform();
    }
    
    private void clickLeftMenu() {
        WebElement element = this.findElement(By.xpath(ACCEUIL_ROOT_MENU_ICON));
        element.click();
    }

    private void _navigateToSimpliciaImage(){
/*
        retry(() -> {
           simpliciaImage.shouldBeDisplayed();
           navigateToFieldsUsingActions(SIMPLICIA_IMG);
*/
    /* retry(() -> {
//            simpliciaImage.shouldBeDisplayed();
//            navigateToFieldsUsingActions(SIMPLICIA_IMG);
>>>>>>> origin/master
            try {
                WebElement element = this.findElement(By.xpath(ACCEUIL_MENU_HOME_ICON));
                if (!element.isDisplayed()) {
                    performScrollToElement(element);
                }
                acceuilMenuHomeIcon.shouldBeDisplayed();
                navigateToFieldsUsingActions(ACCEUIL_MENU_HOME_ICON);
            } catch (Exception e) {
                CommonUtil.screenshot(browser);
                e.printStackTrace();
                LOGGER.info("Home menu couldn't be seen, fallback to my account menu");
                myAccountMenuIcon.shouldBeDisplayed();
                navigateToFieldsUsingActions(MY_ACCOUNT_MENU_ICON);
            }
        });*/
    }

    private void _closeLeftNavigation() {
        closeTheLeftNavigation();
    }


    /**
     * Method to click on De Connecter Button
     */
    public void clickSeDeconnecterButton(){
    	JavascriptExecutor executor = (JavascriptExecutor) browser;
    	long findStart = System.currentTimeMillis();
    	WebElement btnDeConnector = new CustomWebDriverWait(browser, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath(DE_CONNECTER_BUTTON)));
    	long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        LOGGER.info(By.xpath(DE_CONNECTER_BUTTON) + " was found in " + timeToRetrieveElement + "ms");
    	executor.executeScript("arguments[0].click();", btnDeConnector);
    	LOGGER.info("Clicked on "+By.xpath(DE_CONNECTER_BUTTON));
    	//try {
    	//	 deConnecterButton.shouldBeDisplayed();
    	//	 executor.executeScript("arguments[0].click();", deConnecterButton);
		/*} catch (Exception e) {
			WebElement atmpCheck = new CustomWebDriverWait(browser, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath(CHK_ATMP)));
            WebElement bpijCheck = new CustomWebDriverWait(browser, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath(CHK_BPIJ)));
            executor.executeScript("arguments[0].click();", atmpCheck);
            executor.executeScript("arguments[0].click();", bpijCheck);
		}*/
        //deConnecterButton.shouldBeDisplayed();
        //deConnecterButton.click();
    }

    /**
     * Method to click Year in block
     *
     * @param str_Block block name need to be set
     * @param str_Year year need to be set
     */
    public void clickYearInBlock(String str_Block, String str_Year){
        ControlledElement selectedYear = new ControlledElement(browser, By.xpath(MessageFormat.format(SELECTED_YEAR, str_Block, str_Year)));
        selectedYear.shouldBeDisplayed();
        selectedYear.click();
    }


}

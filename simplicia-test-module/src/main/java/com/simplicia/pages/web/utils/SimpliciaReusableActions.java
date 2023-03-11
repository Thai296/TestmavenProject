package com.simplicia.pages.web.utils;

import com.simplicia.executor.PageObjects;
import com.simplicia.pages.web.home.HomePage;
import com.simplicia.pages.web.login.LoginPage;
import controls.Icon;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.WebStorage;
import util.CommonUtil;

import java.text.MessageFormat;

public class SimpliciaReusableActions extends PageObjects
{
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SimpliciaReusableActions.class);


    final static String MATERIAL_ICONS = "//span[contains(@class,''material-icons'') and text()=''{0}'']";

    LoginPage loginPage = new LoginPage(browser);
    HomePage homePage = new HomePage(browser);

    public SimpliciaReusableActions(WebDriver browser)
    {
        super(browser);
    }

    public void checkPresenceOfIcon(String str_Icon) {
        By xpath = By.xpath(MessageFormat.format(MATERIAL_ICONS, str_Icon));
        WebElement element = this.findElement(xpath);
        if (!element.isDisplayed()) {
            performScrollToElement(element);
        }
        Icon icon = getIcon(str_Icon);
        icon.shouldBeDisplayed();
    }

    public void clickIcon(String str_Icon) {
        Icon icon = getIcon(str_Icon);
        icon.click();
    }

    private Icon getIcon(String str_Icon) {
        return new Icon(browser, By.xpath(MessageFormat.format(MATERIAL_ICONS, str_Icon)), "Material Icon " + str_Icon);
    }

    public void logIn() {
        logIn(TestData.EMAIL, TestData.PASSWORD);
    }

    public void logInAsLawyer() {
        LOGGER.info("Logging in as lawyer");
        logIn(TestData.LAWYEREMAIL, TestData.PASSWORD);
    }

    /**
     *
     * Overridden login method to , in case login in with different user like lawyer.
     * @param sEmail
     */
    public void logIn(String sEmail, String sPassword)
    {
        LOGGER.info("Logging in");
        disableSupportTechnique();

        // Set 'email' Text Field
        loginPage.setEmailTextField(sEmail);

        // Set 'Mot de passe' Text Field
        loginPage.setPasswordTextField(sPassword/*TestData.PASSWORD*/);

        // Click on 'Se Connecter' Button
        loginPage.clickSeConnecterButton();
        LOGGER.info("Logged in as " + sEmail);

        // Check presence of 'Acceuil' page heading
        int attempts = 0;
        while (attempts < 2) {
        	try {
        		Thread.sleep(1000);
        		homePage.checkPresenceOfAcceuilHeading();
        		break;
        	} catch (Exception e) {
        		LOGGER.info(CommonUtil.logPrefix() + e.toString());
        		e.printStackTrace();
        	}
        	attempts++;
        }
        sleepSilently(2000);
    }

    public void logOut()
    {
        try {
            LOGGER.info("Logging out");
            // Navigate to left nav bar 'Simlicia' logo
            homePage.navigateToSimpliciaImage();

            //Scroll to logout button in leftnaviagtion menu
            homePage.performScrollByXpath("//span[text()='Se Déconnecter']");

            // Click on 'Se Déconnecter' Button
            homePage.clickSeDeconnecterButton();
        } catch (Exception e) {
            // skip
        }

        forceLogout();

        // Click presence of 'Simplicia' logo
        loginPage.checkPresenceOfSimpliciaLogo();
        LOGGER.info("Logged out");
    }

    private void forceLogout() {
        if (browser instanceof WebStorage) {
            LOGGER.info("browser is a WebStorage, clearing session and local storage");
            WebStorage webStorage = (WebStorage) browser;
            webStorage.getSessionStorage().clear();
            webStorage.getLocalStorage().clear();
            LOGGER.info("session and local storage cleared");
            browser.navigate().refresh(); // refresh and go back to login page
        } else {
            LOGGER.info("browser is NOT a WebStorage, it is " + browser.getClass().getName());
        }
    }

    /**
     *
     * This method is to logout from top logout link
     */
    public void logoutFromTopNavigation() {


    }

}
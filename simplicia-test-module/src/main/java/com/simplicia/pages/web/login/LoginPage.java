package com.simplicia.pages.web.login;

import com.simplicia.executor.PageObjects;
import controls.Button;
import controls.ControlledElement;
import controls.DisabledElement;
import controls.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.text.MessageFormat;


public class LoginPage extends PageObjects {

    //==========
    // Locator =
    //==========
    final static String EMAIL_TEXT = "email";
    final static String EMAIL_TEXT_FIELD = "//input[@name='email']";
    final static String PASSWORD_TEXT = "password";
    final static String PASSWORD_TEXT_FIELD = "//input[@name='password']";
    final static String SIMPLICIA_LOGO = "//img[@src='assets/images/logos/simplicia_logo.png']";
    final static String BIENVENUE_TEXT = "//h3[text()='Bienvenue']";
    //final static String BIENVENUE_TEXT = "//h3[text()='Welcome']";
    final static String SE_CONNECTER_TEXT = "//h6[text()='Se connecter à votre compte']";
//    final static String SE_CONNECTER_TEXT = "//h6[text()='Login to your account']";
    final static String SE_CONNECTER_BUTTON = "//button[@type='submit']";
    final static String CONNECTER_BUTTON = "//span[text()='Se Connecter']";
//    final static String CONNECTER_BUTTON = "//button[@type='submit']/span[.='LOG IN']";
    final static String HELP_SCOUT_BEACON_BUTTON = "//button[@aria-controls='HSBeaconContainerFrame']";
    final static String HELP_SCOUT_BEACON_FRAME = "//iframe[@title='Help Scout Beacon']";
    final static String ICONS = "//span[contains(@class,''material-icons'') and text()=''{0}'']";

    //====================
    // Selenium Controls =
    //====================
    private final ControlledElement simpliciaLogo;
    private final ControlledElement bienvenueText;
    private final ControlledElement seConnecterText;
    private final TextField emailTextField;
    private final TextField passwordTextField;
    private final DisabledElement seConnecterButton;
    private final Button connecterButton;
    private final Button helpScoutBeaconButton;
    private final ControlledElement supportTechnique;

    public LoginPage(WebDriver browser){
        super(browser);
        simpliciaLogo = new ControlledElement(browser, By.xpath(SIMPLICIA_LOGO));
        bienvenueText = new ControlledElement(browser, By.xpath(BIENVENUE_TEXT));
        seConnecterText = new ControlledElement(browser, By.xpath(SE_CONNECTER_TEXT));
        emailTextField = new TextField(browser, By.name(EMAIL_TEXT));
        passwordTextField = new TextField(browser, By.name(PASSWORD_TEXT));
        seConnecterButton = new DisabledElement(browser, By.xpath(SE_CONNECTER_BUTTON));
        connecterButton = new Button(browser, By.xpath(CONNECTER_BUTTON));
        helpScoutBeaconButton = new Button(browser, By.xpath(HELP_SCOUT_BEACON_BUTTON));
        supportTechnique = new ControlledElement(browser, By.cssSelector("div#beacon-container"));
    }

    //================
    // Check actions =
    //================

    /**
     * Method to check presence of Simplicia Logo Text
     */
    public void checkPresenceOfSimpliciaLogo(){
        simpliciaLogo.shouldBeDisplayed();
    }

    /**
     * Method to check presence of Bienvenue Text
     */
    public void checkPresenceOfBienvenueText(){
        bienvenueText.shouldBeDisplayed();
    }

    /**
     * Method to check presence of Se Connecter Text
     */
    public void checkPresenceOfSeConnecterText(){
        seConnecterText.shouldBeDisplayed();
    }

    /**
     * Method to check presence of Email Text filed
     */
    public void checkPresenceOfEmailTextField(){
        emailTextField.shouldBeDisplayed();
    }

    /**
     * Method to check presence of Password Text filed
     */
    public void checkPresenceOfPasswordTextField(){
        passwordTextField.shouldBeDisplayed();
    }

    /**
     * Method to check presence of Se Connecter Button
     */
    public void checkPresenceOfSeConnecterButton(){
        seConnecterButton.shouldBeDisplayed();
    }

    /**
     * Method to check presence of Icons
     *
     * @param str_Icon icon need to be set
     */
    public void checkPresenceOfIcons(String str_Icon){
        ControlledElement icons = new ControlledElement(browser, By.xpath(MessageFormat.format(ICONS,str_Icon)));
        icons.shouldBeDisplayed();
    }

    /**
     * Method to check presence of Help Scout Beacon Button
     */
    public void checkPresenceOfHelpScoutBeaconButton(){
        helpScoutBeaconButton.shouldBeDisplayed();
    }

    // =============
    // Set actions =
    // =============

    /**
     * Method to set email in Email Text field
     * @param str_Email email need to be set
     */
    public void setEmailTextField(String str_Email){
        emailTextField.shouldBeDisplayed();
        emailTextField.typeKeys(str_Email);
    }

    /**
     * Method to set password in password field
     * @param str_Password password need to be set
     */
    public void setPasswordTextField(String str_Password){
        passwordTextField.shouldBeDisplayed();
        passwordTextField.typeKeys(str_Password);

    }

    /**
     * Method to click on Se Connecter Button
     */
    public void clickSeConnecterButton(){
        connecterButton.shouldBeDisplayed();
        connecterButton.click();
    }


    // =============
    // Get actions =
    // =============

    /**
     * Method to get Email Text Field text
     *
     * @return email text
     */
    public String getEmailTextFieldText(){
        emailTextField.shouldBeDisplayed();
        return emailTextField.getValue();
    }

    /**
     * Method to get Email Text Field text
     *
     * @return password text
     */
    public String getPasswordTextFieldText(){
        passwordTextField.shouldBeDisplayed();
        return passwordTextField.getValue();
    }

    // ===============
    // Hover actions =
    // ===============

    /**
     * Method to click on Se Connecter Button
     */
    public void navigateToEmailTextField(){
        connecterButton.shouldBeDisplayed();
        navigateToFieldsUsingActions(EMAIL_TEXT_FIELD);
        wait("Necessary wait to tool tip to display", 1);
        assertTextPresent("Please fill out this field.");
    }

    /**
     * Method to click on Se Connecter Button
     */
    public void navigateToPasswordTextField(){
        connecterButton.shouldBeDisplayed();
        navigateToFieldsUsingActions(PASSWORD_TEXT_FIELD);
        wait("Necessary wait to tool tip to display");
        assertTextPresent("Please fill out this field.");
    }
}

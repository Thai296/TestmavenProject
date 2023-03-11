package com.simplicia.pages.web.newUser;

import com.simplicia.executor.PageObjects;
import com.simplicia.executor.SeleniumTestSupport;
import com.simplicia.functions.Utility;
import controls.Button;

import controls.ControlledElement;
import controls.TextField;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NewUserRegPage extends PageObjects {
    private static final Logger LOGGER = Logger.getLogger(NewUserRegPage.class);
    String sMessage = "";

    //==========
    // Locator =
    //==========
    final static String EMAIL_INPUT = "//input[@name='email']";
    final static String PASSWORD_INPUT = "//input[@name='password']";
    final static String CONFIRM_PASSWORD = "//input[@name='password-confirm']";
    final static String NOM = "//input[@name='nom']";
    final static String PRENOM = "//input[@name='prenom']";
    final static String PHONE_NUMBER = "//input[@name='phoneNumber']";
    final static String SOCIETE = "//input[@name='societe']";
    final static String S_INSCRIRE = "//button[@value='legacy']";
    final static String ERROR_MESSAGE = "/following::p[1]";
    final static String INVALID_EMAIL_ERROR = EMAIL_INPUT + ERROR_MESSAGE;
    //p[text()='Veuillez saisir un e-mail valide']";
    final static String PASSWORD_NOT_MATCH = CONFIRM_PASSWORD + ERROR_MESSAGE;
        //"//p[text()='Les mots de passe ne correspondent pas']";
    final static String NAME_LENGTH_ERROR = NOM + ERROR_MESSAGE;
        //"//p[text()='La longueur minimale des caractères est de 4']";
    final static String PHONE_NUMBER_ERROR = PHONE_NUMBER + ERROR_MESSAGE;
    //"//p[text()='Le numéro de téléphone doit comporter 10 numéros']";
    final static String getNameFieldError(String sField) {
        String sXpath = "";
        if(sField.equals("nom")){

           sXpath =  NOM + ERROR_MESSAGE;
        } else if (sField.equals("prenom")){

            sXpath = PRENOM + ERROR_MESSAGE;
        } else {

            sXpath = SOCIETE + ERROR_MESSAGE;
        }

        return sXpath;
    };
    final static String NEW_USER_CREATE_SUCCESS_MESSAGE = "//p[contains(.,'Enregistré avec succès. Veuillez vous connecter pour continuer')]";
    final static String LOGIN_HEADING_TEXT = "//h6[normalize-space()='Se connecter à votre compte']";
    final static String ADD_VALID_BUSINESS_ID = "//p[contains(normalize-space(),'Veuillez ajouter un identifiant')]";

    //====================
    // Selenium Controls =
    //====================
    private final TextField email;
    private final TextField password;
    private final TextField confirmPassword;
    private final TextField nom;
    private final TextField preNom;
    private final TextField phoneNumber;
    private final TextField societe;
    private final Button submitButton;
    private final ControlledElement invalidEmailError;
    private final ControlledElement passwordMismatch;
    private final ControlledElement nameFieldLengthError;
    private final ControlledElement phoneNumberError;
    private final ControlledElement newUserSuccessMessage;
    private final ControlledElement loginHeadingText;
    private final ControlledElement validUserErrorText;
    private final Button closeErrorButton;

    public NewUserRegPage(WebDriver browser) {
        super(browser);
        email = new TextField(browser, By.xpath(EMAIL_INPUT), "Enter User's Email ID");
        password = new TextField(browser, By.xpath(PASSWORD_INPUT), "Enter password");
        confirmPassword = new TextField(browser, By.xpath(CONFIRM_PASSWORD), "Enter confirm password");
        nom = new TextField(browser, By.xpath(NOM), "Enter last name");
        preNom = new TextField(browser, By.xpath(PRENOM), "Enter first name");
        phoneNumber = new TextField(browser, By.xpath(PHONE_NUMBER), "Enter phone number");
        societe = new TextField(browser, By.xpath(SOCIETE), "Enter company name");
        submitButton = new Button(browser, By.xpath(S_INSCRIRE), "Click submit button");
        invalidEmailError = new ControlledElement(browser, By.xpath(INVALID_EMAIL_ERROR), "Wrong format email");
        passwordMismatch =  new ControlledElement(browser, By.xpath(PASSWORD_NOT_MATCH),"Password not matched");
        nameFieldLengthError = new ControlledElement(browser, By.xpath(NAME_LENGTH_ERROR), "name less than 4 chars");
        phoneNumberError = new ControlledElement(browser, By.xpath(PHONE_NUMBER_ERROR), "Phone number less than 10");
        newUserSuccessMessage =  new ControlledElement(browser, By.xpath(NEW_USER_CREATE_SUCCESS_MESSAGE));
        loginHeadingText =  new ControlledElement(browser, By.xpath(LOGIN_HEADING_TEXT));
        validUserErrorText = new ControlledElement(browser, By.xpath(ADD_VALID_BUSINESS_ID));
        closeErrorButton = new Button(browser, By.xpath("//span[contains(text(),'close')]"));

    }

    //================
    // actions =
    //================

    /**
     *
     * Method to create random emailID and enter
     */
    public String enterEmailID(){

        String sChar = ".-_";
        Random r =new Random();
        String sNumber = "123456789";
        ArrayList<String> sDomain = new ArrayList<>(Arrays.asList("gmail.com","simplicia.co", "yahoo.com", "outlook.com"));
        String sEmailId =
                "demo" +
                sChar.charAt(r.nextInt(sChar.length())) +
                sNumber.charAt(r.nextInt(sNumber.length())) +
                    sNumber.charAt(r.nextInt(sNumber.length())) +
                    sNumber.charAt(r.nextInt(sNumber.length())) +
                    sNumber.charAt(r.nextInt(sNumber.length())) +
                    sNumber.charAt(r.nextInt(sNumber.length())) +
                "@" +
                sDomain.get(r.nextInt(sDomain.size()));
        LOGGER.info("Entering email: " + sEmailId);
        email.typeKeys(sEmailId);
        return sEmailId;
    }

    /**
     *
     * Method to enter email ID
     * @param sEmail
     */
    public void enterEmailID(String sEmail){

        email.typeKeys(sEmail);
    }

    /**
     *
     * Enter password
     * @param sPassword
     */
    public String enterPassword(String sPassword){

        password.typeKeys(sPassword);
        return sPassword;
    }

    /**
     *
     * Enter confirm password
     * @param sPassword
     */
    public void enterConfirmPassword(String sPassword){

        confirmPassword.typeKeys(sPassword);
    }

    /**
     *
     * Enter Nom
     * @param sNom
     */
    public void enterNom(String sNom) {

        nom.typeKeys(sNom);

    }

    /**
     *
     * Enter prenom
     * @param sPreNom
     */
    public void enterPreNom(String sPreNom){

        ScrollToElementByTab(PRENOM);
        preNom.typeKeys(sPreNom);
    }

    /**
     * Enter phone number
     * @param sPhoneNumber
     */
    public  void enterPhoneNumber(String sPhoneNumber) {

        ScrollToElementByTab(PHONE_NUMBER);
        phoneNumber.typeKeys(sPhoneNumber);

    }

    /**
     * Enter company name
     */
    public void enterSociete(String sSociete) {

        ScrollToElementByTab(SOCIETE);
        societe.typeKeys(sSociete);
    }

    /**
     * Click on submit button
     */
    public void clickSubmitButton() {

        //performVerticalDownScroll();
        submitButton.click();

    }

    /**
     *
     * Validate the success message
     * @return
     */
    public boolean isCreated(){

        boolean bCreated = false;

        ExpectedCondition<WebElement> condition = ExpectedConditions.visibilityOfElementLocated(By.xpath(NEW_USER_CREATE_SUCCESS_MESSAGE));
        Utility.waitForElement(browser, 7, condition);

        if(loginHeadingText.isFound())
        {

            loginHeadingText.highlightElement();
            bCreated = true;
        }

        return bCreated;
    }

    @Override
    public boolean isTextPresent(String text) {
        return super.isTextPresent(text);
    }

    public void closeFirstTimeLoginErrorMessage(){
        validUserErrorText.highlightElement();
        closeErrorButton.click();
    }

    //=================================
    // Field validation error
    //==================================

    /**
     * Validate invalid formatted email error
     * @return
     */
    public String inValidEmailError(){


        if(invalidEmailError.isFound()){

            invalidEmailError.highlightElement();
            sMessage = invalidEmailError.getText();

        }

        return sMessage;
    }

    /**
     * Check password mismatch error
     * @return
     */
    public String passwordMismatchError(){

        if(passwordMismatch.isFound()){

            passwordMismatch.highlightElement();
            sMessage = passwordMismatch.getText();
        }

        return sMessage;
    }

    /**
     * Check error message when name length is less than 4 chars
     * @return
     */
    public String inputFieldLengthError(String sField){

        String sXpath = getNameFieldError(sField);
        final ControlledElement fieldLengthError = new ControlledElement(browser, By.xpath(sXpath));

        if(fieldLengthError.isFound()) {

            fieldLengthError.highlightElement();
            sMessage = fieldLengthError.getText();
        }

        return sMessage;
    }

    /**
     * Check phone number error less that 10 digit
     * @return
     */
    public String phoneNumberError() {

        if(phoneNumberError.isFound()){

            phoneNumberError.highlightElement();
            sMessage = phoneNumberError.getText();
        }

        return sMessage;
    }



}

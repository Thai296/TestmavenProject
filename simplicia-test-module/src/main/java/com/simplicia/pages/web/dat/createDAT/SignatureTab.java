package com.simplicia.pages.web.dat.createDAT;

import com.simplicia.pages.web.dat.DATMainPage;
import controls.Button;
import controls.TextField;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.CommonUtil;
import util.CustomWebDriverWait;

import java.util.Map;

public class SignatureTab extends DATMainPage {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SignatureTab.class);
    //################
    // locators
    //###############
    //##### Signature #####
    final static String TXT_FIELD_SIGN_NOM = "input[name='signataireNomDusage']";
    final static String TXT_FIELD_SIGN_PRENOM = "input[name='signatairePrenom']";
    final static String TXT_FIELD_SIGN_QUALITE = "input[name='signataireQualite']";
    final static String TXT_FIELD_SIGN_FAIT_A = "input[name='signataireSignature']";
    final static String TXT_FIELD_DATE = "#date-of-constat";
    final static String TXT_FIELD_UPLOAD_FILE = ".style_dropZone__1ubO- > input";// "//input[@type='file']";
    final static String BTN_SAVE_EXIT = "//span[normalize-space()='Sauvegarder et quitter']/parent::button";
    final static String BTN_SAVE = "//span[normalize-space()='SAUVEGARDER']/parent::button";
    final static String BTN_AFFICHER_TOUS_LES_CHAMPS_MANQUANTS = "//span[normalize-space()='Afficher tous les champs manquants']/parent::button";
    final static String UPLOAD_FILE_SUCCESS_MESSAGE = "//span[text()='Document ajouté à la DAT']";
    final static String BTN_DAT_DOC_UPLOADED_SUCCESS_MESSAGE_CLOSE = "//span[normalize-space(text()) = 'Document ajouté à la DAT']/../following-sibling::div/button";

    //###################
    // Selenium Controls
    //##################
    private final TextField signNomTF;
    private final TextField signPreNomTF;
    private final TextField signQualiteTF;
    private final TextField signFaitATF;
    private final TextField dateTF;
    private final TextField uploadFileTF;
    private final Button saveExitButton;

    public SignatureTab(WebDriver browser) {
        super(browser);
        signNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_SIGN_NOM), "signataire Nom D usage");
        signPreNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_SIGN_PRENOM), "signataire Prenom");
        signQualiteTF = new TextField(browser, By.cssSelector(TXT_FIELD_SIGN_QUALITE), "signataire Qualite");
        signFaitATF = new TextField(browser, By.cssSelector(TXT_FIELD_SIGN_FAIT_A), "signataire Signature");
        dateTF = new TextField(browser, By.cssSelector(TXT_FIELD_DATE), "date of constat");
        uploadFileTF = new TextField(browser, By.cssSelector(TXT_FIELD_UPLOAD_FILE), "upload file");
        saveExitButton = new Button(browser, By.xpath(BTN_SAVE_EXIT), "Sauvegarder et quitter");
    }

    public void fillSignatureTabDetails(Map<String, String> mSignatureDetails) throws Exception{

        fillSignatureTab(mSignatureDetails, false);


    }

    public void fillSignatureTab(Map<String, String> mSignatureDetails, boolean bUpdate) throws Exception{
            // enter name details
        signNomTF.typeKeys(mSignatureDetails.get("TXT_FIELD_SIGN_NOM"));
        signPreNomTF.typeKeys(mSignatureDetails.get("TXT_FIELD_SIGN_PRENOM"));

        //enter QUalite
        signQualiteTF.typeKeys(mSignatureDetails.get("TXT_FIELD_SIGN_QUALITE"));

        //Enter Fait a
        signFaitATF.typeKeys(mSignatureDetails.get("TXT_FIELD_SIGN_FAIT_A"));

        //fix date format
        //String sDate = mSignatureDetails.get("TXT_FIELD_DATE");
        //Date d = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
        //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTF.typeKeys("02/10/2021");
    }

    /**
     * Upload file
     *
     * @param sPath
     * @param sFileName
     * @return
     */
    public void uploadFile(String sPath, String sFileName) throws Exception{


        uploadFileTF.typeKeys(FilenameUtils.separatorsToSystem(sPath + "/" + sFileName));

        retry(() -> {
            //wait for file to upload
            new CustomWebDriverWait(browser, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(UPLOAD_FILE_SUCCESS_MESSAGE)));
        });
        println("---- Wait to close success message");
        //Thread.sleep(1000);
        clickCloseMessageAfterUpload();
        Thread.sleep(3000);
    }

    public void clickOnSaveAndExit() {
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
        //click on save and exit button
    	println("--- CLICKING on Save-Exit button");
    	JavascriptExecutor executor = (JavascriptExecutor) browser;
    	WebElement saveExitBtn = new CustomWebDriverWait(browser, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath(BTN_SAVE_EXIT)));
    	executor.executeScript("arguments[0].click();", saveExitBtn);
    	println("--- Clicked on Save-Exit button");
        //saveExitButton.click();
    }



    /**
     * close success message when clicking on next on dat form, sometimes radio button are not clicked and using javascript click sometime intercept the success message
     */
    public void clickCloseMessageAfterUpload() {
        try {
            By xpath = By.xpath(BTN_DAT_DOC_UPLOADED_SUCCESS_MESSAGE_CLOSE);
            checkPresence(xpath);
            click(xpath);
        } catch (Exception e) {
            String screenshot = CommonUtil.takeScreenshot(browser);
            LOGGER.info(CommonUtil.logPrefix() + "Couldn't close the doc upload successful message, screenshot taken: " + screenshot + ", " + e.toString());
            e.printStackTrace();
        }
    }
}

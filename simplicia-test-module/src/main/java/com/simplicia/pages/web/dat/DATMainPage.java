package com.simplicia.pages.web.dat;

import static com.simplicia.pages.web.utils.TestData.MY_DATS_URL;
import static org.openqa.selenium.By.xpath;
import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.simplicia.functions.Utility;
import com.simplicia.pages.web.SimpliciaPage;
import com.simplicia.pages.web.utils.ZipUtils;

import controls.Button;
import controls.ControlledElement;
import controls.DropDown;
import controls.Header;
import controls.Link;
import controls.SystemFileSelectorButton;
import controls.TextField;
import exception.MSeleniumException;
import util.CommonUtil;
import util.CustomWebDriverWait;

public class DATMainPage extends SimpliciaPage {
	private static final Logger LOGGER = Logger.getLogger(DATMainPage.class);
	//============
	// Locators
	//============
	final static String BTN_CREATE_NEW_DAT = "#create-dat";
	final static String BTN_IMPORT_DAT = "#import-dat";
	final static String TXT_FILED_IMPORT_FILE = "upload-file";
	final static String BTN_ACTION_EXPAND = "//button[@aria-label='Cliquer pour voir les options']";
	final static String BTN_CHAT_TO_LAWYER = "//button[contains(@title,'Cliquez ici pour échanger avec')]";
	final static String BTN_CHAT_TO_CLIENT = "//button[contains(@title,'Cliquez ici pour échanger avec le client')]";
	final static String BTN_ACTION_EDIT_DAT = " //span[contains(text(),'edit')]"; //"button[title='Edition d'une DAT : ']";
	final static String BTN_ACTION_DELETE_ACTION = "button[title='Supprimer la DAT']";
	final static String BTN_ACTION_PDF_DOWNLOAD = "button[title='Télécharger le PDF']";
	final static String BTN_ACTION_ZIP_DOWNLOAD = "button[title='Télécharger la DAT au format ZIP']";
	final static String BTN_COMMENT = "button[title='Cliquez pour démarrer la conversation']";
	final static String DROPDOWN_SELECT_TAG = "select[class='MuiSelect-root MuiSelect-select MuiInputBase-input MuiInput-input']";
	final static String TXT_FIELD_COMMENT_BOX = "message";
	final static String BTN_COMMENT_SEND = "button[title='Envoyer']";
	final static String BTN_CLOSE_COMMENT = "//span[contains(text(),'close')]";
	final static String BTN_EMAIL_OUTLINE = "//span[text()='mail_outline']";
	final static String BTN_ENVOYER_POUR_REVISION = "//span[normalize-space()='Envoyer pour révision']";
	final static String BTN_LOCK = "//button[@title='Approuver la DAT']";
	final static String BTN_APPROUVER_PAR_AVOCAT = "//button[@title='Approuvée par avocat']";
	final static String OPT_LIST_OPTION = "//ul[@class=''MuiList-root MuiMenu-list MuiList-padding'']/li[contains(text(),''{0}'')]";
	final static String DAT_SAVED_SUCCESS_MESSAGE = "//p[text()='DAT sauvegardée avec succés']";
	public final static String DAT_IMPORT_SUCCESS_MESSAGE = "//p[contains(text(), 'DATs marquées pour envoi: 1')]";
	public final static String DAT_IMPORT_SUCCESS_MESSAGE_2 = "//p[contains(text(), 'DATs marquées pour envoi: 2')]";
	final static String BTN_MESSAGE_CLOSE = "//span[text()='close']";
	final static String TXT_FIELD_SEARCH_DAT = "//input[@placeholder='Rechercher']";
	final static String DAT_DELETE_CONFIRM = "//h2[normalize-space()='Confirmation de suppression de la DAT']";
	final static String BTN_DAT_DELETE_CONFIRM_OUI = "//span[normalize-space()='Oui']";
	final static String BTN_DAT_DELETE_CONFIRM_NON = "//span[@class='MuiButton-label'][normalize-space()='Non']";
	final static String DAT_DELETE_SUCCESS_MESSAGE = "//p[contains(text(),'DAT supprim') and contains(text(),'e avec succ')]";
	final static String BTN_DAT_SAVE_MESSAGE_CLOSE = "button[aria-label='Close']";
	final static String BTN_CLOSE_DAT = "button[aria-label='close']";
	final static String TXT_FIELD_TV_REGISTRATION = "//input[@name='victimeNir']";
	final static String IMPORT_DAT = "//button[@id='import-dat']//preceding-sibling::input";
	final static String BTN_DAT_INCOMPLETE = "//span[text()='error_outline']";
	final static String BTN_DAT_ENVOI = "//button[@title='Envoi Net-entreprises']";
	final static String HEALTH_INSURANCE_CONFIRM_MESS = "//p[text()='Voulez-vous vraiment soumettre cette DAT?']";
	final static String BTN_HEALTH_INSURANCE_CONFIRM_OK = HEALTH_INSURANCE_CONFIRM_MESS + "/following::button[1]";
	final static String ENVOI_SUCCESS_MESSAGE = "//p[text()='Statut de soumission de la DAT à jour']";
	final static String BTN_DAT_ENVOI_DEMANDE = "//span[contains(text(),'settings_backup_restore')]";
	final static String TXT_ENVOI_DEMANDE = "//span[contains(normalize-space(),'Envoi demandé')]";

	// Create New DAT
	final static String BTN_SUIVANT = "//span[normalize-space()='Suivant']/parent::button";
	final static String BTN_SAVE = "//span[normalize-space()='SAUVEGARDER']/parent::button";
	final static String BTN_AFFICHER_TOUS_LES_CHAMPS_MANQUANTS = "//span[normalize-space()='Afficher tous les champs manquants']/parent::button";

	// steps link
	final static String LINK_STEP_1 = "//div[contains(@class, 'MuiDialog-paperFullScreen')]//span[text()='1']";
	final static String LINK_STEP_2 = "//div[contains(@class, 'MuiDialog-paperFullScreen')]//span[text()='2']";

	// lawyer portals
	final static String BTN_DASHBOARD_SWITCH = "button[aria-label='switch']";
	final static String TXT_FILED_CLIENT = "//p[normalize-space()='Société : Paul Bedoucha']";
	final static String BTN_ACCEDER = TXT_FILED_CLIENT + "/following::span[text()='Accéder'][1]";
	final static  String ACCEDER_MESSAGE = "//p[text()='Modification du client actif effectué avec succès']";
	final static String BTN_APPROUVER_LA_DAT = "//button[@title='Approuver la DAT']";
	final static String BTN_APPROUVER_LA_DAT_CONFIRM = "//span[normalize-space()='APPROUVER LA DAT']";
	final static String APPOUVER_LA_DAT_SUCCESS_MESSAGE = "//p[text()='Statut de révision de la DAT mis à jour']";
	final static String BTN_APPROUVEE_PAR_AVOCAT = "//button[@title='Approuvée par avocat']";
	final static String TXT_FIELD_CLIENT_MESSAGE = "//p[@class='MuiTypography-root mx-8 MuiTypography-body1']";

	//=============
	// Selenium Controls
	//=============
	private final Button createNewDATButton;
	private final SystemFileSelectorButton importDATButton;
	private final TextField importFile;
	private final Button expandDATOptionButton;
	private final Button editDATButton;
	private final Button deleteDATButton;
	private final Button downloadPDFButton;
	private final Button downloadZIPButton;
	private final Button commentButton;
	private final Button nextButton;
	private final Button saveButton;
	private final Button afficherTousLesChamps;
	private final ControlledElement datSavedSuccessMessage;
	private final ControlledElement datImportSuccessMessage;
	private final TextField searchDATTF;
	private final ControlledElement datDeleteConfirm;
	private final Button deleteConfirmOuiButton;
	private final ControlledElement datDeleteSuccessMessage;
	private final Button closeSaveMessageDialog;
	private final Link step2Link;
	private final Link step1Link;
	private final Button closeDATButton;
	private final Button datConfirmNonButton;
	private final DropDown selectTag;
	private final Button chatToLawyer;
	private final TextField messageToLawyer;
	private  final Button sendMessage;
	private final Button closeComment;
	private final Button clickEmalOutlineIcon;
	private final Button envoyerPourRevision;
	private final Button lockButton;
	private final TextField clientMessage;
	private final Button approuverLaDAT;
	private final Button approuverLaDATConfirm;
	private final Button approuveeParAvocate;
	private final Button approuverParAvocat;
	private final Button datCompleteIcon;
	private final Button datEnvoiIcon;
	private final TextField envoiConfirmMessage;
	private final Button envoiOK;
	private final TextField envoiSeccessMessage;
	private final Button restoreEnvoiDAT;
	private final TextField envoiRequestText;
	private JavascriptExecutor executor;
	public static void println(String s){
		LOGGER.info(s);
	}
	public DATMainPage(WebDriver browser) {
		super(browser);
		executor = (JavascriptExecutor) browser;
		createNewDATButton = new Button(browser, By.cssSelector(BTN_CREATE_NEW_DAT), "Create new DAT Button");
		importDATButton = new SystemFileSelectorButton(browser, By.cssSelector(BTN_IMPORT_DAT), "Import DAT Button");
		importFile = new TextField(browser, By.id(TXT_FILED_IMPORT_FILE));
		expandDATOptionButton = new Button(browser, By.xpath(BTN_ACTION_EXPAND), "Expand DAT option Button");
		editDATButton = new Button(browser, By.xpath(BTN_ACTION_EDIT_DAT), "Edit DAT Button");
		deleteDATButton = new Button(browser, By.cssSelector(BTN_ACTION_DELETE_ACTION), "Delete DAT Button");
		downloadPDFButton = new Button(browser, By.cssSelector(BTN_ACTION_PDF_DOWNLOAD), "Download PDF Button");
		downloadZIPButton = new Button(browser, By.cssSelector(BTN_ACTION_ZIP_DOWNLOAD), "Download ZIP format");
		commentButton = new Button(browser, By.cssSelector(BTN_COMMENT), "Enter comment");
		nextButton = new Button(browser, By.xpath(BTN_SUIVANT), "Click next button");
		saveButton = new Button(browser, By.xpath(BTN_SAVE));
		afficherTousLesChamps = new Button(browser, By.xpath(BTN_AFFICHER_TOUS_LES_CHAMPS_MANQUANTS));
		datSavedSuccessMessage = new ControlledElement(browser, By.xpath(DAT_SAVED_SUCCESS_MESSAGE));
		datImportSuccessMessage = new ControlledElement(browser, By.xpath(DAT_IMPORT_SUCCESS_MESSAGE));
		searchDATTF = new TextField(browser, By.xpath(TXT_FIELD_SEARCH_DAT));
		datDeleteConfirm = new ControlledElement(browser, By.xpath(DAT_DELETE_CONFIRM));
		deleteConfirmOuiButton = new Button(browser, By.xpath(BTN_DAT_DELETE_CONFIRM_OUI));
		datDeleteSuccessMessage = new ControlledElement(browser, By.xpath(DAT_DELETE_SUCCESS_MESSAGE));
		closeSaveMessageDialog = new Button(browser, By.cssSelector(BTN_DAT_SAVE_MESSAGE_CLOSE));
		step1Link = new Link(browser, By.xpath(LINK_STEP_1));
		step2Link = new Link(browser, By.xpath(LINK_STEP_2));
		closeDATButton = new Button(browser, By.cssSelector(BTN_CLOSE_DAT));
		datConfirmNonButton =  new Button(browser, By.xpath(BTN_DAT_DELETE_CONFIRM_NON));
		selectTag =  new DropDown(browser, By.cssSelector(DROPDOWN_SELECT_TAG));
		chatToLawyer = new Button(browser, By.xpath(BTN_CHAT_TO_LAWYER));
		messageToLawyer = new TextField(browser, By.name(TXT_FIELD_COMMENT_BOX));
		sendMessage = new Button(browser, By.cssSelector(BTN_COMMENT_SEND));
		closeComment = new Button(browser, By.xpath(BTN_CLOSE_COMMENT));
		clickEmalOutlineIcon = new Button(browser, By.xpath(BTN_EMAIL_OUTLINE));
		envoyerPourRevision = new Button(browser, By.xpath(BTN_ENVOYER_POUR_REVISION));
		lockButton = new Button(browser, By.xpath(BTN_LOCK));
		clientMessage = new TextField(browser, By.xpath(TXT_FIELD_CLIENT_MESSAGE));
		approuverLaDAT = new Button(browser, By.xpath(BTN_APPROUVER_LA_DAT));
		approuverLaDATConfirm = new Button(browser, By.xpath(BTN_APPROUVER_LA_DAT_CONFIRM));
		approuveeParAvocate = new Button(browser, By.xpath(BTN_APPROUVEE_PAR_AVOCAT));
		approuverParAvocat = new Button(browser, By.xpath(BTN_APPROUVER_PAR_AVOCAT));
		datCompleteIcon = new Button(browser, By.xpath(BTN_DAT_INCOMPLETE));
		datEnvoiIcon = new Button(browser, By.xpath(BTN_DAT_ENVOI));
		envoiConfirmMessage = new TextField(browser, By.xpath(HEALTH_INSURANCE_CONFIRM_MESS));
		envoiOK = new Button(browser, By.xpath(BTN_HEALTH_INSURANCE_CONFIRM_OK));
		envoiSeccessMessage = new TextField(browser, By.xpath(ENVOI_SUCCESS_MESSAGE));
		restoreEnvoiDAT =  new Button(browser, By.xpath(BTN_DAT_ENVOI_DEMANDE));
		envoiRequestText = new TextField(browser, By.xpath(TXT_ENVOI_DEMANDE));

	}

	//================
	// Check actions =
	//================

	/**
	 * Method to check presence of create new DAT button.
	 */
	public void checkPresenceOfCreateNewDATButton() {
		createNewDATButton.shouldBeDisplayed();
	}

	/**
	 * Method to check presence of import DAT button.
	 */
	public void checkPresenceOfImportDATButton() {
		importDATButton.shouldBeDisplayed();
	}

	/**
	 * Method to check presence of next button on create DAT pages.
	 */
	public void checkPresenceOfNextButton() {
		nextButton.shouldBeDisplayed();
	}

	/**
	 * Method to click on create DAT button
	 */
	public void clickCreateNewDATButton() {
		checkPresenceOfCreateNewDATButton();
		try{
			createNewDATButton.click();
			Thread.sleep(3000);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * Method to click on Import DAT button
	 */
	public void clickImportDATButton() {
		checkPresenceOfImportDATButton();
		importDATButton.click();

	}

	/**
	 * Method to click on next button
	 */
	public void clickNextButton() {
		checkPresenceOfNextButton();
		nextButton.click();
		// wait for the previous tab to save
		sleepSilently(3000);
	}

	/**
	 * Method to click on action button to see the options
	 */
	public void clickOnActionDATButton() {

		expandDATOptionButton.click();
	}

	/**
	 * Method to click on edit dat button
	 */
	public void clickOnEditDATButton() {

		editDATButton.click();
	}

	/**
	 * Method to click on delete dat button
	 */
	public void clickOnDeleteDATButton() {
		deleteDATButton.click();
	}

	/**
	 * Method to click on download pdf dat button
	 */
	public void clickOnDownloadPDFButton() {
		downloadPDFButton.click();
	}

	/**
	 * Method to click on comment button
	 */
	public void clickOnCommentButton() {
		commentButton.click();
	}

	/**
	 * Method to click on save button to save the DAT details
	 */
	public void clickOnSaveButton() {

		saveButton.click();
	}

	/**
	 * This method is click on Show all missing items
	 */
	public void clickOnAffiecherTousLesChamps() {

		afficherTousLesChamps.click();
	}

	/**
	 * This method will elect from drop down list by text
	 */
	public void selectItemFromDropDownList(String str_ListOption) {

		String sOption = str_ListOption;
		boolean c = str_ListOption.contains("\'");
		if(c){

			String[] s = str_ListOption.split("\'");
			sOption = s[1];

		}
		/*Pattern p = Pattern.compile("[^a-z0-9]");
        Matcher m = p.matcher(str_ListOption);
        boolean b = m.find();
        if(b){

            String[] s = str_ListOption.split("\'");
            sOption = s[1];

        }
        else {

            sOption = str_ListOption;
        }*/
		ControlledElement listOption = new Header(browser, By.xpath(MessageFormat.format(OPT_LIST_OPTION, sOption)), "Drop down List Option");
		listOption.shouldBeDisplayed();
		listOption.click();
	}

	/**
	 * This method is to scroll to element using Tab, especially drop down list
	 *
	 * @param str_Xpath
	 */
	public void ScrollToElementUsingTab(String str_Xpath) {
		tabKey(By.xpath(str_Xpath));
	}

	/**
	 * Verify that success message is displayed and close the toast message.
	 */
	public void validateDATSaved() {
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			Thread.currentThread().interrupt();
//		}
		long findStart = System.currentTimeMillis();
		Utility.waitForElement(browser, 10, ExpectedConditions.presenceOfElementLocated(By.xpath(DAT_SAVED_SUCCESS_MESSAGE))).isDisplayed();
		long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
		LOGGER.info(DAT_SAVED_SUCCESS_MESSAGE + " was found in " + timeToRetrieveElement + "ms");
		//datSavedSuccessMessage.shouldBeDisplayed();
		clickCloseMessage();
	}

	/**
	 * validate the created DAT on DAT dashboard
	 *
	 * @param sNumeroSS
	 * @return
	 */
	public boolean isDatPresentOnDashboard(String sNumeroSS) {
		AtomicBoolean found = new AtomicBoolean(false);
		retrySilently(() -> {
			LOGGER.info("Checking if DAT is found on table: " + sNumeroSS);
			// Enter registration number / Numero SS in the search input field
			new CustomWebDriverWait(browser, 50).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TXT_FIELD_SEARCH_DAT)));
			searchDATTF.shouldBeDisplayed();
			searchDATTF.typeKeys(selectAllAndDeleteKeys());
			searchDATTF.typeKeys(sNumeroSS);
			// wait for record to filter and display
			String xpathNew = ".//th//span//mark[contains(text(),'" + sNumeroSS + "')]";
			new CustomWebDriverWait(browser, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathNew)));
			ControlledElement sNumeroSSEle = new ControlledElement(browser, By.xpath(xpathNew));
			found.set(sNumeroSSEle.isFound());

			if (!found.get()) {
				throw new MSeleniumException(sNumeroSS + " not found, let's try again.", browser);
			}
			LOGGER.info("Dat found: " + sNumeroSS);
		});
		return found.get();

	}
		/**
	 * Wait for sNumeroSSEle to be displayed
	 * @param sNumeroSS
	 * @return boolean
	 */
	public boolean waitForSNumeroSSEle(String sNumeroSS) {
		String xpathNew = ".//th//span//mark[contains(text(),'" + sNumeroSS + "')]";
		waitForElementToBeVisible(xpathNew);
		return isElementPresent(browserFindElement(byXpath(xpathNew)), 3);
	}

	/**
	 * Delete the created DAT
	 */
	public void DeleteDAT() {

		
//		retry(() -> clickDatActionButton());
		clickDatActionButton();
		deleteDATButton.click();

		// Check if confirm message is displayed
		if (isTextPresent(datDeleteConfirm.getText())) {

			// click on yes/ OUI
			deleteConfirmOuiButton.wait("Wait for Oui button to display", 3);
			deleteConfirmOuiButton.click();
			LOGGER.info("Click Oui to delete DAT");

		}

	}

	/**
	 * click on edit button to open the DAT form
	 */
	public void editDAT() {
		performHorizontalScroll();
		clickDatActionButton();
		editDATButton.click();
	}

	/**
	 * Method to click on action button to expand the DAT options
	 */
	private void clickDatActionButton() {
		if(!browser.getCurrentUrl().equals(MY_DATS_URL)){
			browser.get(MY_DATS_URL);
			sleepSilently(4000);
		}
		// Click in expand action button of DAT
//		expandDATOptionButton.shouldBeDisplayed();
		clickOnRadioButtonUsingJavaScript(BTN_ACTION_EXPAND);
		deleteDATButton.wait("Wait for delete button to display", 2);
	}


	/**
	 * Click on radio button using java script
	 *
	 * @param xpath
	 */
	public void clickOnRadioButtonUsingJavaScript(String xpath) {
		WebElement element = this.findElement(By.xpath(xpath));
		executor.executeScript("arguments[0].click();", element);
	}

	/**
	 * Verify the delete DAT success message.
	 */
	public void validateDeleteDAT() {
		waitForElementToBePresence(By.xpath(DAT_DELETE_SUCCESS_MESSAGE));
		checkPresence(By.xpath(DAT_DELETE_SUCCESS_MESSAGE), "Dat deleted successfully message");
		clickCloseMessage();
		LOGGER.info("DAT is deleted successfully");
	}

	/**
	 * This method is just created to click on search input field on DAT dashboard
	 * while deleting without search horizontal scroll not work, so first clicking to activate the section
	 */
	public void clickSearch() {
		searchDATTF.click();
	}

	/**
	 * close success message when clicking on next on dat form, sometimes radio button are not clicked and using javascript click sometime intercept the success message
	 */
	public void clickCloseMessage() {
		int attempts = 0;
		while (attempts < 2) {
			try {
				// try to click close
				LOGGER.info("finding controlled object " + By.xpath(BTN_MESSAGE_CLOSE));
				long findStart = System.currentTimeMillis();
				List<WebElement> successMsgCloseBtn = this.findElements(By.xpath(BTN_MESSAGE_CLOSE));
				if(!successMsgCloseBtn.isEmpty()) {
					long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
					LOGGER.info(BTN_MESSAGE_CLOSE + " was found in " + timeToRetrieveElement + "ms");
					executor.executeScript("arguments[0].click();", this.findElement(By.xpath(BTN_MESSAGE_CLOSE)));
					LOGGER.info("---Clicked on "+BTN_MESSAGE_CLOSE);
					Thread.sleep(1000);
				}
				break;
			} catch (Exception e) {
				LOGGER.info(CommonUtil.logPrefix() + e.toString());
				e.printStackTrace();
			}
			attempts++;
		}
	}

	public void clickCloseMessage1 () throws Exception{

		//let  toast message closes itself
		Thread.sleep(5000);
	}

	/**
	 * Click on step number link
	 *
	 * @param iStepNumber
	 */
	public void clickOnStepLink(int iStepNumber) {
		switch (iStepNumber) {

		case 1:
			performScrollToTopOfElement(this.findElement(By.cssSelector(".MuiDialog-paperFullScreen")));
			tryToClick(step1Link);
			// don't check this anymore, DAT form is now auto-saved
//			Utility.waitForElement(browser, 10, ExpectedConditions.invisibilityOfElementLocated(By.xpath(DAT_SAVED_SUCCESS_MESSAGE)));
			break;

		case 2:
			performScrollToTopOfElement(this.findElement(By.cssSelector(".MuiDialog-paperFullScreen")));
			tryToClick(step2Link);
			new CustomWebDriverWait(browser, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TXT_FIELD_TV_REGISTRATION)));
			break;
		case 3:

			//click on 3rd step

		default:

			//do nothing

		}

	}

	public boolean downloadDATZIP(String sFileName) {
		boolean flag = false; 
		LOGGER.info("flag before downloading is: "+ flag);
		try {
			flag = (boolean) downloadDATZIPWithFiles(sFileName).get("downloaded");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("flag after downloading is: "+ flag);
		return flag;
	}

	/**
	 *
	 * Download the DAT as ZIP
	 *
	 */
	public Map<String, Object> downloadDATZIPWithFiles(String sFileName) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		boolean bDownload = false;
		performHorizontalScroll();
//		clickDatActionButton();
		retry(() -> clickDatActionButton());
		downloadZIPButton.click();

		// wait for file to download
		sleepSilently(5000);

		// validate download
		//String resourcePath = getResourcePath("");
		String resourcePath = downloadFilePath;
		LOGGER.info("*** resourcePath *** " + resourcePath);
		listFilesInDir(resourcePath);
		File file = new File(resourcePath);
		LOGGER.info("file is: " + file);
		File[] files =  file.listFiles();
		LOGGER.info("files length is: " + files.length);
		for(File f: files){
			if(f.getName().equals(sFileName)) {
				LOGGER.info("########## DAT is downloaded with name " + f.getName() + " #########");
				if (f.getName().endsWith(".zip")) {
					List<File> unzippedFiles = ZipUtils.exportZipFile(f);
					for (File unzippedFile : unzippedFiles) {
						if (unzippedFile.getName().endsWith(".xml")) {
							resultMap.put(unzippedFile.getName(), FileUtils.readFileToString(unzippedFile, "UTF-8"));
						}
					}
				}
				bDownload =  true;
			}
		}

		resultMap.put("downloaded", bDownload);
		return resultMap;
	}

	/**
	 *
	 * import dat.zip using javascript executor
	 */

	public void importDATF(String sFileName, String succVerificationXPath) {

		LOGGER.info("Importing dat from " + sFileName);
		LOGGER.info("Sending keys to upload-file element: " + sFileName);
		browserFindElement(xpath(IMPORT_DAT)).sendKeys(sFileName);
		LOGGER.info("Sent keys to upload-file element: " + sFileName);

		// let file upload
		// during DAT import, the company login will be tested, so it will take more time
		new CustomWebDriverWait(browser, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(succVerificationXPath)));
		//importFile.typeKeys(sFileName);
		LOGGER.info("Dat imported from " + sFileName);

	}



	/**
	 *
	 * Close DAT without saving
	 *
	 */
	public boolean closeDATNoChange() throws Exception{

		boolean bClose = false;

		if(closeDATButton.isFound()) {

			//clickHiddenElementUsingJavaScript(BTN_CLOSE_DAT);
			Actions action = new Actions(browser);
			action.sendKeys(Keys.ESCAPE).build().perform();
			Thread.sleep(5000);
			//closeDATButton.click();
			//Thread.sleep(3000);
			clickHiddenElementUsingJavaScript(BTN_DAT_DELETE_CONFIRM_NON);
			//datConfirmNonButton.click();
			Thread.sleep(8000);
			//browser.manage().
			bClose = true;
		}

		return bClose;

	}

	/**
	 *
	 * This method will click on chat icon , type message, send and close chat dialog
	 */
	public String echangerAvecAvocat(String sMessage){

		performHorizontalScroll();
		//click on chat icon
		clickOnChatToLawyer();

		// select option from dropdown
		selectTag.selectByVisibleText("Lettre de cloture");

		new CustomWebDriverWait(browser, 3).until(ExpectedConditions.visibilityOfElementLocated(By.name(TXT_FIELD_COMMENT_BOX)));

		// write message
		messageToLawyer.typeKeys(sMessage);
		sendMessage.click();

		// close the message dialog.
		closeComment.click();


		return sMessage;
	}

	/**
	 * This method will click on send for review icon, click on envoyer pour revision
	 * validate success message, validate lock icon
	 */
	public boolean sendDATForLawyerReview() {

		performHorizontalScroll();

		// click on send to review icon
		clickHiddenElementUsingJavaScript(BTN_EMAIL_OUTLINE);
		waitForElementVisibilityOfElementLocated(BTN_ENVOYER_POUR_REVISION);
		clickHiddenElementUsingJavaScript(BTN_ENVOYER_POUR_REVISION);

		//close the success popup
		waitForElementToBePresence(By.xpath(BTN_LOCK));
		return isElementPresent(browser.findElement(By.xpath(BTN_LOCK)), 3);

	}

	/**
	 *
	 * This method will get the text in lawyer portal for comment sent by client.
	 *
	 * @return
	 */
	public String clientMessage(){
		performHorizontalScroll();
		clickOnChatToLawyer();
		//String sComment =  clientMessage.getText();
		closeComment.click();
		return /*sComment*/"";
	}
	
	public void clickOnClientMessage() {
		By locator = By.xpath(BTN_CHAT_TO_CLIENT);
    	LOGGER.info("finding controlled object " + locator);
    	long findStart = System.currentTimeMillis();
    	WebElement btnChatToLawyer = new CustomWebDriverWait(browser, 5).until(ExpectedConditions.presenceOfElementLocated(locator));
		long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        LOGGER.info(locator + " was found in " + timeToRetrieveElement + "ms");
    	executor.executeScript("arguments[0].click();", btnChatToLawyer);
    	LOGGER.info("Clicked on "+locator);
	}

	public void clickOnChatToLawyer() {
		By locator = By.xpath(BTN_CHAT_TO_LAWYER);
    	LOGGER.info("finding controlled object " + locator);
    	long findStart = System.currentTimeMillis();
    	WebElement btnChatToLawyer = new CustomWebDriverWait(browser, 5).until(ExpectedConditions.presenceOfElementLocated(locator));
		long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        LOGGER.info(locator + " was found in " + timeToRetrieveElement + "ms");
    	executor.executeScript("arguments[0].click();", btnChatToLawyer);
    	LOGGER.info("Clicked on "+locator);
	}

	/**
	 *
	 * This method will approve the DAT sent for review
	 */
	public boolean approveDATReview() {

		boolean bApproved = false;

		waitForElementToBePresence(byXpath(BTN_APPROUVER_LA_DAT));
		clickHiddenElementUsingJavaScript(BTN_APPROUVER_LA_DAT);
		LOGGER.info("Clicked on "+By.xpath(BTN_APPROUVER_LA_DAT));

		waitForElementToBePresence(byXpath(BTN_APPROUVER_LA_DAT_CONFIRM));
		clickHiddenElementUsingJavaScript(BTN_APPROUVER_LA_DAT_CONFIRM);
		LOGGER.info("Clicked on "+By.xpath(BTN_APPROUVER_LA_DAT_CONFIRM));

		// Check if Approved By Lawyer button is displayed or not
		performHorizontalScroll();
		if(approuveeParAvocate.isFound()){

			bApproved = isElementPresent(browserFindElement(byXpath(BTN_APPROUVEE_PAR_AVOCAT)), 4);
		}

		return bApproved;


	}

	/**
	 *
	 * Validate is approved by lawyer button present on user portal
	 */
	public boolean isDATApproved() {

		performHorizontalScroll();
		return isElementPresent(browserFindElement(byXpath(BTN_APPROUVER_PAR_AVOCAT)), 5);
	}

	/**
	 * Validate is DAT is complete or not
	 * @return
	 */
	public boolean isDATInComplete() {
		boolean bComplete = false;


		performHorizontalScroll();

		boolean bApproved = datCompleteIcon.isFound();

		if(bApproved){

			bComplete= true;

		}

		return bComplete;

	}

	public boolean sendDATToHealthInsurance(String sNumeroSS) throws Exception{

		boolean bSent = false;
		searchDATTF.typeKeys(selectAllAndDeleteKeys());
		searchDATTF.typeKeys(sNumeroSS);

		// wait for record to filter and display
		waitForSNumeroSSEle(sNumeroSS);
		performHorizontalScroll();

		datEnvoiIcon.shouldBeDisplayed();
		clickHiddenElementUsingJavaScript(BTN_DAT_ENVOI);
		waitForElementToBePresence(By.xpath(HEALTH_INSURANCE_CONFIRM_MESS));

		if(envoiConfirmMessage.isFound()) {

			envoiOK.click();
			waitForElementToBePresence(By.xpath(ENVOI_SUCCESS_MESSAGE));
			bSent = isElementPresent(browserFindElement(byXpath(ENVOI_SUCCESS_MESSAGE)), 3);;

		}

		return bSent;
	}

	public boolean validateDATSentToHealthInsuranceSuccesfuly(String sNumeroSS) {
		LOGGER.info("validateDATSentToHealthInsuranceSuccesfuly of " + sNumeroSS);

		boolean bVerified = false;
		searchDATTF.typeKeys(selectAllAndDeleteKeys());
		searchDATTF.typeKeys(sNumeroSS);
		sleepSilently(10000);
		performHorizontalScroll();

		if(restoreEnvoiDAT.isFound()){
			bVerified = isElementPresent(browser.findElement(By.xpath(TXT_ENVOI_DEMANDE)), 3);;
		}

		return bVerified;
	}

	public void clickHiddenElementUsingJavaScript(String sLocator){

		WebElement element = this.findElement(By.xpath(sLocator));
		JavascriptExecutor js = (JavascriptExecutor) browser;
		js.executeScript("(arguments[0]).click();", element);
	}
}

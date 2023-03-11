package com.simplicia.pages.web.netEntreprises;

import com.simplicia.functions.Utility;
import com.simplicia.pages.web.SimpliciaPage;
import controls.Button;
import controls.CheckBox;
import controls.TextField;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.CommonUtil;

import java.util.Map;

public class MesAccesNetEentreprises extends SimpliciaPage {
	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(MesAccesNetEentreprises.class);

	//============
	// Locators
	//============
	final static String BTN_ADD_NEW_LOGIN = "button[aria-label='add']";
	final static String TXT_FIELD_RECHERCHER = "//input[@placeholder='Rechercher']";
	final static String BTN_TESTER_L_ACCESS = "//span[contains(text(),'play_circle_outline')]";
	final static String BTN_CLEAR_SEARCH = "span[aria-label='clear']";
	final static String BTN_ASSOCIATE_EMAIL = "//span[contains(text(),'mail_outline')]";
	final static String BTN_DELETE = "//span[contains(text(),'delete')]";
	final static String BTN_DELETE_OUI = "//span[normalize-space()='Oui']";
	final static String BTN_EDIT = "//span[contains(text(),'edit')]";
	final static String TXT_FIELD_COMPANY_ID = "//input[@placeholder='Company ID']";
	final static String TXT_FIELD_NOM = "input[placeholder='Nom']";
	final static String TXT_FIELD_PRENOM = "input[placeholder='Prénom']";
	final static String TXT_FIELD_MOT_DE_PASS = "input[placeholder='Mot de passe']";
	final static String TXT_FIELD_NOM_SOCIETE = "//input[@placeholder='Nom Société']";
	final static String CHK_ATMP = TXT_FIELD_NOM_SOCIETE + "/following::input[1]"; //"//input[@aria-label='ATMP']";
	final static String CHK_BPIJ = TXT_FIELD_NOM_SOCIETE + "/following::input[2]";; //"//input[@aria-label='BPIJ']";
	final static String BTN_SAVE = "button[title='Sauvegarder']";
	final static String BTN_CANCEL = "button[title='Annuler']";
	final static String TXT_FIELD_EMAIL_TEXT = "//input[not(contains(@placeholder,'Rechercher'))]";
	final static String BTN_SEND = "//button[@value='legacy']";
	final static String TXT_FILED_EMAIL = BTN_SEND + "/following::div";
	final static String EMAIL_DELETE_SUCCESS_MESSAGE = "//p[text()='Email supprimé avec succès']";
	final static String EMAIL_ADD_SUCCESS_MESSAGE = "//p[text()='Email ajouté avec succès']";
	final static String TXT_FIELD_INVALID_EMAIL_MESSAGE = "//p[text()='Please Enter only Email']";
	final static String TEXT_SIRET = "//table/tbody/tr[1]/td[1]";
	final static String TEXT_NOM = "//table/tbody/tr[1]/td[2]";
	final static String TEXT_PRENOM = "//table/tbody/tr[1]/td[3]";
	final static String TEXT_COMPANY = "//table/tbody/tr[1]/td[5]";
	final static String TEXT_PASSWORD = "//table/tbody/tr[1]/td[4]";
	final static String DELETE_LOGIN_SUCCESS_MESSAGE = "//p[text()='Supprimé avec succès']";
	final static String BTN_DELETE_SUCCESS_CLOSE = "//span[contains(text(),'close')]";


	//=============
	// Selenium Controls
	//=============
	private final Button addNewLogin;
	private final TextField rechercher;
	private final Button testerLAcess;
	private final Button clearSearch;
	private final Button associateEmailWithEnterprise;
	private final Button deleteLogin;
	private final Button deleteOui;
	private final Button editLogin;
	private final TextField companyID;
	private final TextField nom;
	private final TextField prenom;
	private final TextField motDePass;
	private final TextField nomSociete;
	private final CheckBox atmp;
	private final CheckBox bpij;
	private final Button save;
	private final Button cancel;
	private final TextField emailText;
	private final Button send;
	private final TextField email;
	private final TextField companyIDText;
	private final TextField nomText;
	private final TextField prenomText;
	private final TextField companyText;
	private final TextField passwordText;
	private final Button closePopUp;

	public MesAccesNetEentreprises(WebDriver browser) {
		super(browser);

		addNewLogin = new Button(browser, By.cssSelector(BTN_ADD_NEW_LOGIN));
		rechercher = new TextField(browser, By.xpath(TXT_FIELD_RECHERCHER));
		testerLAcess = new Button(browser, By.xpath(BTN_TESTER_L_ACCESS));
		clearSearch =  new Button(browser, By.cssSelector(BTN_CLEAR_SEARCH));
		associateEmailWithEnterprise =  new Button(browser, By.xpath(BTN_ASSOCIATE_EMAIL));
		deleteLogin = new Button(browser, By.xpath(BTN_DELETE));
		deleteOui = new Button(browser, By.xpath(BTN_DELETE_OUI));
		editLogin = new Button(browser, By.xpath(BTN_EDIT));
		companyID = new TextField(browser, By.xpath(TXT_FIELD_COMPANY_ID));
		nom = new TextField(browser, By.cssSelector(TXT_FIELD_NOM));
		prenom = new TextField(browser, By.cssSelector(TXT_FIELD_PRENOM));
		motDePass = new TextField(browser, By.cssSelector(TXT_FIELD_MOT_DE_PASS));
		nomSociete = new TextField(browser, By.xpath(TXT_FIELD_NOM_SOCIETE));
		atmp = new CheckBox(browser, By.xpath(CHK_ATMP));
		bpij = new CheckBox(browser, By.xpath(CHK_BPIJ));
		save = new Button(browser, By.cssSelector(BTN_SAVE));
		cancel = new Button(browser, By.cssSelector(BTN_CANCEL));
		emailText = new TextField(browser, By.xpath(TXT_FIELD_EMAIL_TEXT));
		send = new Button(browser, By.xpath(BTN_SEND));
		email = new TextField(browser, By.xpath(TXT_FILED_EMAIL));
		companyIDText = new TextField(browser, By.xpath(TEXT_SIRET));
		nomText = new TextField(browser, By.xpath(TEXT_NOM));
		prenomText = new TextField(browser, By.xpath(TEXT_PRENOM));
		passwordText = new TextField(browser, By.xpath(TEXT_PASSWORD));
		companyText = new TextField(browser, By.xpath(TEXT_COMPANY));
		closePopUp = new Button(browser, By.xpath(BTN_DELETE_SUCCESS_CLOSE));


	}


	//================
	// actions =
	//================


	public String createNetEnterpriseLogin(Map<String, String> mEnterpriseLoginDetails) throws Exception{

		// click om search field to scroll horizontally to display add button
		/*   performScrollToElement(this.findElement(By.xpath(TXT_FIELD_RECHERCHER)));
        //performHorizontalScroll();
        this.findElement(By.xpath(TXT_FIELD_RECHERCHER)).sendKeys(Keys.TAB);
        rechercher.click();*/


		//performHorizontalScroll();

		// click on add button
		JavascriptExecutor executor = (JavascriptExecutor)browser;
		executor.executeScript("document.body.style.zoom = '0.8'");
		//addNewLogin.shouldBeDisplayed();
		//addNewLogin.click();
		WebElement eADD = this.findElement(By.cssSelector(BTN_ADD_NEW_LOGIN));
		executor.executeScript("arguments[0].click();", eADD);
		Thread.sleep(5000);

		// fill the details
		//String sID = "3"+TestDataEngine.generateRandomNumber(13);

		//performScrollToElement(this.findElement(By.xpath(TXT_FIELD_COMPANY_ID)));
		tabKey(By.xpath(TXT_FIELD_COMPANY_ID));
		Thread.sleep(1000);
		//mEnterpriseLoginDetails.put("TXT_FIELD_COMPANY_ID",sID);
		companyID.typeKeys(mEnterpriseLoginDetails.get("TXT_FIELD_COMPANY_ID"));
		Thread.sleep(1000);
		nom.typeKeys(mEnterpriseLoginDetails.get("TXT_FIELD_NOM"));
		Thread.sleep(1000);
		prenom.typeKeys(mEnterpriseLoginDetails.get("TXT_FIELD_PRENOM"));
		Thread.sleep(1000);
		motDePass.typeKeys(mEnterpriseLoginDetails.get("TXT_FIELD_MOT_DE_PASS"));
		Thread.sleep(1000);
		nomSociete.typeKeys(mEnterpriseLoginDetails.get("TXT_FIELD_NOM_SOCIETE"));
		Thread.sleep(1000);
		// Check the atmp and bpij checkboxes

		performClick(executor, By.xpath(CHK_ATMP));
		performClick(executor, By.xpath(CHK_BPIJ));
		performClick(executor, By.cssSelector(BTN_SAVE));
		Thread.sleep(5000);
		//save.click();
		executor.executeScript("document.body.style.zoom = '1.0'");
		return mEnterpriseLoginDetails.get("TXT_FIELD_COMPANY_ID");

	}

	private void clickElementWithJavaScript(String sScript) {
		JavascriptExecutor js = (JavascriptExecutor)browser;
		Object atmp1 = js.executeScript(sScript);
		((WebElement)atmp1).click();
	}


	public boolean searchForNetEnterpriseLogin(String sCompanyID) throws Exception{

		boolean b = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				performScrollToElement(this.findElement(By.xpath(TXT_FIELD_RECHERCHER)));
				Thread.sleep(3000);
				break;
			} catch (Exception e) {
				LOGGER.info(CommonUtil.logPrefix() + e.toString());
				e.printStackTrace();
			}
			attempts++;
		}
		//performHorizontalScroll();
		Thread.sleep(3000);
		tabKey(By.xpath(TXT_FIELD_RECHERCHER));
		Thread.sleep(2000);
		rechercher.typeKeys(selectAllAndDeleteKeys());
		Thread.sleep(1000);
		rechercher.typeKeys(sCompanyID);
		Thread.sleep(6000);
		performHorizontalBackScroll();
		Thread.sleep(2000);
		if(companyIDText.getText().equals(sCompanyID)) {

			b= true;
		}


		return b;
	}


	public void deleteAccount()throws Exception{
		performHorizontalScroll();
		Thread.sleep(2000);
		deleteLogin.shouldBeDisplayed();
		deleteLogin.click();
		Thread.sleep(2000);
		deleteOui.shouldBeDisplayed();
		deleteOui.click();
		Utility.waitForElement(browser, 5, ExpectedConditions.visibilityOfElementLocated(By.xpath(DELETE_LOGIN_SUCCESS_MESSAGE)));
	}

	public String editAccount(Map<String, String> mUpdateNetEnterprise, String sCompanyID, String sFiledToUpdate) throws Exception{

		String sField ="";
		performScrollToElement(this.findElement(By.xpath(TXT_FIELD_RECHERCHER)));
		Thread.sleep(1000);
		tabKey(By.xpath(TXT_FIELD_RECHERCHER));
		Thread.sleep(3000);
		rechercher.typeKeys(selectAllAndDeleteKeys());
		Thread.sleep(1000);
		rechercher.typeKeys(sCompanyID);
		Thread.sleep(1000);
		performHorizontalScroll();
		editLogin.click();
		Thread.sleep(3000);
		performHorizontalBackScroll();
		Thread.sleep(1000);
		switch (sFiledToUpdate) {
		case "Nom":
			nom.typeKeys(selectAllAndDeleteKeys());;
			Thread.sleep(1000);
			sField = mUpdateNetEnterprise.get("NOM");
			nom.typeKeys(sField);
			Thread.sleep(2000);
			break;
		case "Prenom":
			prenom.typeKeys(selectAllAndDeleteKeys());
			Thread.sleep(1000);
			sField = mUpdateNetEnterprise.get("PRENOM");
			prenom.typeKeys(sField);
			Thread.sleep(2000);
			break;
		case "password":
			motDePass.typeKeys(selectAllAndDeleteKeys());
			Thread.sleep(1000);
			sField = mUpdateNetEnterprise.get("MOT_DE_PASS");
			motDePass.typeKeys(sField);
			Thread.sleep(2000);
			break;
		case "sociate":
			nomSociete.typeKeys(selectAllAndDeleteKeys());
			Thread.sleep(1000);
			sField = mUpdateNetEnterprise.get("NOM_SOCIETE");
			nomSociete.typeKeys(sField);
			Thread.sleep(2000);
			break;

		default:
			LOGGER.info("No Option");
		}

		performHorizontalScroll();
		save.click();

		return sField;
	}

	public boolean performClick(JavascriptExecutor executor, By locator){
		boolean result = false;
		int attempts = 0;
		while(attempts < 2) {
			try {
				Thread.sleep(1000);
				WebElement element = this.findElement(locator);
				executor.executeScript("arguments[0].click();", element);
				result = true;
				break;
			} catch (Exception e) {
				LOGGER.info(CommonUtil.logPrefix() + e.toString());
				e.printStackTrace();
			}
			attempts++;
		}
		return result;
	}
}

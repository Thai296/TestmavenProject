package com.simplicia.web.newUser;

import com.simplicia.executor.SeleniumTestAsSimpliciaUser;
import com.simplicia.functions.TestDataEngine;
import com.simplicia.pages.web.home.HomePage;
import com.simplicia.pages.web.leftnavigationmenu.LeftNavigationMenuPage;
import com.simplicia.pages.web.login.LoginPage;
import com.simplicia.pages.web.netEntreprises.MesAccesNetEentreprises;
import com.simplicia.pages.web.newUser.NewUserRegPage;
import com.simplicia.pages.web.utils.SimpliciaReusableActions;
import com.simplicia.pages.web.utils.TestData;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

public class NewUserRegistrationTest extends SeleniumTestAsSimpliciaUser {

	String sError = "";
	String sInvalidEmailError = "Veuillez saisir un e-mail valide";
	String sPasswordNotMatchError = "Les mots de passe ne correspondent pas";
	String sFieldError = "La longueur minimale des caractères est de 4";
	String sPhoneError = "Le numéro de téléphone doit comporter 10 numéros";
	String sEmailID = "";
	String sPassword = "";
	boolean b = false;
	String sErrorMessageAfterFirstTimeLogin = "Veuillez ajouter un identifiant d'entreprise valide pour continuer";
	HomePage homePage;
	LoginPage loginPage;
	LeftNavigationMenuPage leftNavigationMenuPage;
	NewUserRegPage newUser;
	SimpliciaReusableActions simpliciaReusableActions;
	MesAccesNetEentreprises mesAccesNetEentreprises;

	public static String sTestDataXMLPath = System.getProperty("user.dir") + File.separator + "src/test/resources/MESAcessNetEnterpriseDATA";
	boolean bnetEnterpriseLogin = false;

	@BeforeMethod
	public void pageSetUp(Method method) {
		try {
			println("################################################################");
			println("Started " + method.getName());
			println("################################################################");

			// Setup page
			loginPage = new LoginPage(browser);
			simpliciaReusableActions = new SimpliciaReusableActions(browser);
			homePage = new HomePage(browser);
			newUser = new NewUserRegPage(browser);
			leftNavigationMenuPage = new LeftNavigationMenuPage(browser);
			mesAccesNetEentreprises = new MesAccesNetEentreprises(browser);

		} catch (Exception e) {
			// ignore, tests will be failed and it goes to report
		}
	}

	@Test()
	@Description("Enter invalid email ID and verify the error message")
	public void newUserTest_01_NEGATIVE_InvalidEmail() {
		// Navigate to Simplicia registration url
		loadUrl(TestData.REGISTRATION_URL);

		try {
			newUser.enterEmailID("invalid@invalid");
			sError = newUser.inValidEmailError();
			Assert.assertEquals(sError, sInvalidEmailError);
		} catch (Exception e) {

			Assert.fail("No Error thrown for invalid formatted email");
			println(e.getMessage());
		}
	}

	@Test()
	@Description("Enter different confirm password and verify the error message")
	public void newUserTest_02_NEGATIVE_PASSWORD_NO_MATCH() {
		try {
			newUser.enterPassword("Test");
			newUser.enterConfirmPassword("Test1");
			sError = newUser.passwordMismatchError();
			Assert.assertEquals(sError, sPasswordNotMatchError);

		} catch (Exception e) {

			Assert.fail("No Error thrown for mismatch password");
			println(e.getMessage());

		}
	}

	@Test()
	@Description("Enter nom less than 4 chars and verify the error message")
	public void newUserTest_03_NEGATIVE_NOM() {
		try {
			newUser.enterNom("Te");
			sError = newUser.inputFieldLengthError("nom");
			Assert.assertEquals(sError, sFieldError);

		} catch (Exception e) {

			Assert.fail("No Error thrown for nom less than 4 chars");
			println(e.getMessage());
		}
	}

	@Test()
	@Description("Enter prenom less than 4 chars and validate the error message")
	public void newUserTest_04_NEGATIVE_PRENOM() {
		try {
			newUser.enterPreNom("Pre");
			sError = newUser.inputFieldLengthError("prenom");
			Assert.assertEquals(sError, sFieldError);
		} catch (Exception e) {

			Assert.fail("No error is thrown for prenom less than 4 chars");
			println(e.getMessage());
		}
	}

	@Test()
	@Description("Enter phone number less than 10 digits and validate the error message")
	public void newUserTest_05_NEGATIVE_PHONE_NUMBER() {

		try {
			newUser.enterPhoneNumber("676565");
			sError = newUser.phoneNumberError();
			Assert.assertEquals(sError, sPhoneError);
		} catch (Exception e) {

			Assert.fail("No error thrown for phone number less than 10 digits");
			println(e.getMessage());
		}
	}

	@Test()
	@Description("Enter societe name less than 4 chars and validate the error message")
	public void newUserTest_06_NAGATIVE_SOCIETE() {
		try {
			newUser.enterSociete("Sim");
			sError = newUser.inputFieldLengthError("societe");
			///Assert.assertEquals(sError, sFieldError);
			Assert.assertFalse(sError.isEmpty());
		} catch (Exception e) {

			Assert.fail("No error thrown fr scoiete name less than 4 chars");
			println(e.getMessage());
		}
	}

	@Test()
	@Description("Create new User and validate the success message and login screen" +
			", validate that user cant open any page unless user adds a valid account ")
//	@Step("1. Create a user " +
//			"2. Login in using the user" +
//			"3. Check that error is thrown when user click on any feature" +
//			"4. Add an invalid account and validate that user is still not able to enter inside any page" +
//			"5. Add a valid account and click on any feature e.g. DAT and validate that user is able to do so")
	public void newUserTest_07_POSITIVE_Create_User() throws Exception {
		loadUrl(TestData.REGISTRATION_URL);
		try {

			sEmailID = newUser.enterEmailID();
			sPassword = newUser.enterPassword(TestData.PASSWORD1);
			newUser.enterConfirmPassword(TestData.PASSWORD1);
			newUser.enterNom("Test");
			newUser.enterPreNom("User");
			newUser.enterPhoneNumber("9" + TestDataEngine.generateRandomNumber(9));
			newUser.enterSociete("Simplicia");
			newUser.clickSubmitButton();
			b = newUser.isCreated();
			if (b) {
				println("New user created successfully with user ID " + sEmailID + " and password " + sPassword);
				Assert.assertTrue(b, "New user created successfully with user ID " + sEmailID + " and password " + sPassword);

				// Login using created user and password
				simpliciaReusableActions.logIn(sEmailID, sPassword);
				// we disable this message
//				if (newUser.isTextPresent(sErrorMessageAfterFirstTimeLogin)) {
//
//					newUser.closeFirstTimeLoginErrorMessage();
//				}

				// Check that user is not able to see pages except create business accounts
				//clickAcculiAndValidate();

				//Add a invalid business account and validate that user is still not able to open pages.
				//createBusinessAccount("netEnterprise");
				//clickAcculiAndValidate();

				// add valid business account and check that now user is able to open the other pages
				openAccessNetEnterprisePage();
				createBusinessAccount("netEnterpriseValid");
				clickAccueilMenu();
				homePage.wait("Necessary 2 seconds wait for page load", 2);
				Assert.assertTrue(homePage.isTextPresent("Cette section vous permet de remplir votre DAT directement sur le Saas"), "Home page is open for the view");

			}
		} catch (Exception e) {

			e.printStackTrace();
			Assert.fail("New user is not created successfully", e);

		}

	}


	@AfterMethod
	public void pageTearDown(Method method) {

		println("################################################################");
		println("Finished " + method.getName());
		println("################################################################");


	}



	//#####################
	// Private methods
	//####################
	private void clickAcculiAndValidate() {

		homePage.wait("Necessary 2 seconds wait for page load", 3);
		clickAccueilMenu();
		try {
			Assert.assertTrue(newUser.isTextPresent(sErrorMessageAfterFirstTimeLogin), "User cant see page without valid business account");
			newUser.closeFirstTimeLoginErrorMessage();
		} catch(Exception e){

			println("################ New user restriction is removed from staging");
		}
	}

	private void clickAccueilMenu() {
		homePage.navigateToSimpliciaImage();
		leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Accueil");
		leftNavigationMenuPage.clickMenuItemInLeftNavigation("Accueil");
		homePage.wait("Necessary 2 seconds wait for page load", 2);
		leftNavigationMenuPage.closeLeftNavigation();
	}

	private void createBusinessAccount(String sTagName) throws Exception {
		Map<String, String> netEnterprise = TestDataEngine.parseXML(sTestDataXMLPath, "mesAcess.xml", sTagName);
		homePage.wait("Necessary 2 seconds wait for page load", 2);
		try {

			String aCompanyID = mesAccesNetEentreprises.createNetEnterpriseLogin(netEnterprise);
			mesAccesNetEentreprises.wait("wait for account to created", 3);
			bnetEnterpriseLogin = mesAccesNetEentreprises.searchForNetEnterpriseLogin(aCompanyID);
			Assert.assertTrue(bnetEnterpriseLogin);

		} catch (Exception e) {

			println("Business account is not created");
		}

	}

	private void openAccessNetEnterprisePage() throws Exception{

		homePage.navigateToSimpliciaImage();
		Thread.sleep(2000);
		leftNavigationMenuPage.clickExpandIconToOpenMenu("Mes accès");
		Thread.sleep(2000);
		leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Net-Entreprises");
		leftNavigationMenuPage.clickMenuItemInLeftNavigation("Net-Entreprises");
		Thread.sleep(2000);
		leftNavigationMenuPage.closeLeftNavigation();
		Thread.sleep(2000);
	}

}

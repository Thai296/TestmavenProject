package com.simplicia.web.login;

import com.simplicia.executor.SeleniumTestAsSimpliciaUser;
import com.simplicia.pages.web.home.HomePage;
import com.simplicia.pages.web.login.LoginPage;
import com.simplicia.pages.web.utils.SimpliciaReusableActions;
import com.simplicia.pages.web.utils.TestData;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test Cases
 *
 * 1. Navigate to Simlipicia Url “https://docker.simplicia.co:7443/” and verify presence of Simplicia logo.
 * 2. Verify that 'Se connecter à votre compte' and 'Bienvenue' texts are displayed correctly.
 * 3. Verify that Email and password fields should be present.
 * 4. Verify that 'Se Connecter' is disabled if no input is given.
 * 5. Verify the ''Help Scout Beacon'' button is displayed at the bottom of page
 * 6. Verify the presence of 'email' and 'vpnkey' icons along with email and password fields respectively.
 * 7. User is able to fill data into email and password text fields.
 * 8. After adding the values into email and password verify that ''Se Connecter'' button is enabled.
 * 9. User is able to login into the application and verify that 'Accueil' page is displayed.
 *
 */

public class LoginUIValidationTest extends SeleniumTestAsSimpliciaUser
{
    LoginPage loginPage;
    HomePage homePage;
    SimpliciaReusableActions simpliciaReusableActions;

    @BeforeMethod
    public void pageSetUp()
    {
        try {
            // Setup page
            loginPage = new LoginPage(browser);
            homePage = new HomePage(browser);
            simpliciaReusableActions = new SimpliciaReusableActions(browser);

        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
    }

    @Test
    @Description("Navigate to Simlipicia Url “https://docker.simplicia.co:7443/” and verify presence of Simplicia logo.")
    public void loginUIValidationTest_01()
    {
        // Navigate to Simplicia url
        loadUrl(TestData.APPLICATION_URL);

        // Check presence of 'Simpicia' logo
        loginPage.checkPresenceOfSimpliciaLogo();
    }

    @Test
    @Description("Verify that 'Se connecter à votre compte' and 'Bienvenue' texts are displayed correctly.")
    public void loginUIValidationTest_02()
    {
        // Check Presence of 'Bienvenue' Text
        loginPage.checkPresenceOfBienvenueText();

        // Check presence of 'Se connecter à votre compte' Text
        loginPage.checkPresenceOfSeConnecterText();
    }

    @Test
    @Description("Verify that Email and password fields should be present")
    public void loginUIValidationTest_03()
    {
        // Check Presence of 'Email' Text
        loginPage.checkPresenceOfEmailTextField();

        // Check presence of 'Password' Text
        loginPage.checkPresenceOfPasswordTextField();
    }


    @Test
    @Description("Verify that 'Se Connecter' is disabled if no input is given.")
    public void loginUIValidationTest_04()
    {
        // Check Presence of 'Se Connecter' Button is Disabled
        loginPage.checkPresenceOfSeConnecterButton();
    }

    // Todo Need to correct the test case
    /*
    @Test
    @Description("Verify the 'Help Scout Beacon' button is displayed at the bottom of page")
    public void loginUIValidationTest_05()
    {
        // Check Presence of 'Help Scout Beacon' Button
        loginPage.checkPresenceOfHelpScoutBeaconButton();
    }*/

    @Test
    @Description("Verify the presence of 'email' and 'vpnkey' icons along with email and password fields respectively.")
    public void loginUIValidationTest_06()
    {
        // Check presence of 'email' Icon
        loginPage.checkPresenceOfIcons("email");

        // Check presence of 'vpn_key' Icon
        loginPage.checkPresenceOfIcons("vpn_key");
    }

    @Test
    @Description("Verify that user is able to fill data into email and password text fields.")
    public void loginUIValidationTest_07()
    {
        // Set 'email' Text Field
        loginPage.setEmailTextField(TestData.EMAIL);

        /*
         Check point to verify Email Text Field Text
         */
        loginPage.assertTextEquals(TestData.EMAIL, loginPage.getEmailTextFieldText());

        // Set 'Mot de passe' Text Field
        loginPage.setPasswordTextField(TestData.PASSWORD);

        /*
         Check point to verify password Text Field Text
         */
        loginPage.assertTextEquals(TestData.PASSWORD, loginPage.getPasswordTextFieldText());
    }

    @Test
    @Description("After adding the values into email and password verify that 'Se Connecter' button is enabled.")
    public void loginUIValidationTest_08()
    {
        // Check Presence of 'Se Connecter' Button
        loginPage.checkPresenceOfSeConnecterButton();
    }

    @Test
    @Description("Verify that user is able to login into the application and verify that 'Accueil' page is displayed.")
    public void loginUIValidationTest_09()
    {
        // Click on 'Se Connecter' Button
        loginPage.clickSeConnecterButton();

        // Check presence of 'Acceuil' page heading
        homePage.checkPresenceOfAcceuilHeading();
    }

}

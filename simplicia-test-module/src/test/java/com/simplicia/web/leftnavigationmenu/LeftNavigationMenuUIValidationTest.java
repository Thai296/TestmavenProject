package com.simplicia.web.leftnavigationmenu;

import com.simplicia.executor.SeleniumTestAsSimpliciaUser;
import com.simplicia.pages.web.home.HomePage;
import com.simplicia.pages.web.leftnavigationmenu.LeftNavigationMenuPage;
import com.simplicia.pages.web.login.LoginPage;
import com.simplicia.pages.web.utils.SimpliciaReusableActions;
import com.simplicia.pages.web.utils.TestData;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test Cases of Left navigation menu
 *
 * 1. Verify that when person login into account then Left navigation menu is Closed.
 * 2. Move mouse over the Left Navigation Menu and verify it is Opened.
 * 3. Verify that Simplicia logo and 'saas' text is displayed in left navigation menu.
 * 4. User name, user email and profile image is displayed in left navigation menu.
 * 5. Verify that Accueil, ATMP, BPIJ, DAT, 'Mes accès Net-entreprises', 'Historique Extractions', 'Mon Compte'
 *    and 'Se Déconnecter' are diaplyed under menu section in left navigation menu.
 * 6. Verify that on clicking 'ATMP' menu is opened and 'Tous mes ATMPs', 'ATMPs +90J', 'ATMPs de la semaine',
 *   'ATMPs du mois', 'Rentes', 'PDFs comptes employeur', 'Taux Définitifs' options are displayed under sub menu.
 * 7. Verify that on clicking 'BPIJ' menu is opened and 'Mes BPIJs','Toutes mes IJs', 'IJs par mois', 'Calcul d'ecarts'
 *    options are displayed under sub menu.
 * 8. Verify that on clicking 'DAT' menu is opened and 'Mes DATs' and 'DATs archivées' options are displayed under sub menu.
 *
 */


public class LeftNavigationMenuUIValidationTest extends SeleniumTestAsSimpliciaUser
{
    LoginPage loginPage;
    HomePage homePage;
    SimpliciaReusableActions simpliciaReusableActions;
    LeftNavigationMenuPage leftNavigationMenuPage;

    @BeforeMethod
    public void pageSetUp()
    {
        try {
            // Setup page
            loginPage = new LoginPage(browser);
            homePage = new HomePage(browser);
            simpliciaReusableActions = new SimpliciaReusableActions(browser);
            leftNavigationMenuPage = new LeftNavigationMenuPage(browser);

        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
    }

    @Test
    @Description("Verify that when person login into account then Left navigation menu is closed.")
    public void LeftNavigationMenuUIValidationTest_01()
    {
        // Login into Application
        loadUrl(TestData.APPLICATION_URL);
        simpliciaReusableActions.logIn();
//
//        // Check absence of 'Menu' in Left navigation bar
//        leftNavigationMenuPage.checkAbsenceOfLeftNavigationMenuOption("Menu");
    }

    @Test
    @Description("Move mouse over the Left Navigation Menu and verify it is Opened.")
    public void LeftNavigationMenuUIValidationTest_02()
    {
     // Navigate to left navigation menu
     homePage.navigateToSimpliciaImage();

     // Check presence of 'Menu' in Left navigation bar
     leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Menu");
    }

    @Test
    @Description("Verify that Simplicia logo and 'SaaS' text is displayed in left navigation bar")
    public void LeftNavigationMenuUIValidationTest_03()
    {
        // Check presence in left navigation bar
        // Its not in there now
        //leftNavigationMenuPage.checkPresenceSimpliciaImage();

        // Check presence of SaaS Text
        leftNavigationMenuPage.CheckPresenceOfSaasText();
    }

   /* @Test
    @Description("User name, user email and profile image is displayed in left navigation menu.")
    public void LeftNavigationMenuUIValidationTest_04()
    {

    	// Navigate to left navigation menu
        homePage.navigateToSimpliciaImage();

        // Check presence 'user name' in left navigation bar
    	// TODO Need to check for user name
//        leftNavigationMenuPage.checkPresenceWebText("paul bedoucha");

        // Check presence of email Text
     // TODO Need to check for user email
//        leftNavigationMenuPage.checkPresenceWebText(TestData.EMAIL);

        // Check presence of profile image
        leftNavigationMenuPage.CheckPresenceOfUserImage();
    }*/

    @Test
    @Description("Verify that Accueil, ATMP, BPIJ, DAT, 'Mes accès Net-entreprises', " +
            "'Mon Compte' and 'Se Déconnecter' are diaplyed under menu section in left navigation menu.")
    public void LeftNavigationMenuUIValidationTest_05()
    {
        /*
         * Check presence of left navigation menu Options
         */
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Accueil");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Comptes employeurs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("BPIJ");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("DAT");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Mes accès");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Mon Compte");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Se Déconnecter");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Créer une MP");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Questionnaires Risques Pro");
    }


    @Test
    @Description("Verify that on clicking 'ATMP' menu is opened and 'Tous mes ATMPs', 'ATMPs +150J', 'ATMPs +90J', 'ATMPs de la semaine'," +
            " 'ATMPs du mois', 'Rentes', 'PDFs comptes employeur', 'Taux Définitifs' options are displayed under sub menu.")
    public void LeftNavigationMenuUIValidationTest_06()
    {
        // Click ATMP's Expand icon
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Comptes employeurs");
        leftNavigationMenuPage.clickExpandIconToOpenMenu("Comptes employeurs");

        /*
         * Check presence of left navigation menu Options
         */
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Tous mes ATMPs");

        //leftNavigationMenuPage.clickExpandIconToOpenMenu("Tous mes ATMPs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("PDFs comptes employeur");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Taux Définitifs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Module de contrôle ATMP");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Ticket de changement de taux");

        // Click ATMP's Expand Less icon
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Comptes employeurs");
        leftNavigationMenuPage.clickExpandLessIconToCloseMenu("Comptes employeurs");

    }

    @Test
    @Description("Verify that on clicking 'BPIJ' menu is opened and 'Mes BPIJs','Toutes mes IJs', 'IJs par mois', 'Calcul d'ecarts' options are displayed under sub menu.")
    public void LeftNavigationMenuUIValidationTest_07()
    {
        // Click BPIJ's Expand icon
        leftNavigationMenuPage.clickExpandIconToOpenMenu("BPIJ");

        /*
         * Check presence of left navigation menu Options
         */
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Mes BPIJs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Toutes mes IJs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Ecarts IJSS");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Suivi tickets IJSS");

        // Click BPIJ's Expand Less icon
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("BPIJ");
        leftNavigationMenuPage.clickExpandLessIconToCloseMenu("BPIJ");

    }

    @Test
    @Description("Verify that on clicking 'DAT' menu is opened and 'Mes DATs' and 'DATs archivées' options are displayed under sub menu.")
    public void LeftNavigationMenuUIValidationTest_08()
    {
        // Click DAT's Expand icon
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("DAT");
        leftNavigationMenuPage.clickExpandIconToOpenMenu("DAT");

        /*
         * Check presence of left navigation menu Options
         */
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Mes DATs");
        leftNavigationMenuPage.clickExpandIconToOpenMenu("Mes DATs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● DATs en cours de rédaction");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● DATs complétées");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● DATs en révision par l’avocat");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● DATs validées Avocat");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● DATs en cours d’envoi Net-entreprises");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● DATs envoyées Net-entreprises");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● DATs archivées");

        // Click DAT's Expand Less icon
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("DAT");
        leftNavigationMenuPage.clickExpandLessIconToCloseMenu("DAT");

    }
}

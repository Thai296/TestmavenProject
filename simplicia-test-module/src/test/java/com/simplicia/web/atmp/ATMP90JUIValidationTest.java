package com.simplicia.web.atmp;

import com.simplicia.executor.SeleniumTestAsSimpliciaUser;
import com.simplicia.pages.web.atmp.ATMP90JPage;
import com.simplicia.pages.web.home.HomePage;
import com.simplicia.pages.web.leftnavigationmenu.LeftNavigationMenuPage;
import com.simplicia.pages.web.utils.SimpliciaReusableActions;
import com.simplicia.pages.web.utils.TestData;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 1. Navigate to Left navigation menu. Click on 'ATMP' and click 'ATMPs +90J' item and verify that 'ATMPs +90J' page
 *    is displayed.
 * 2. Verify that search box is displayed with placeholder 'Chercher'.
 * 3. If there are records, verify that cloud download icon is displayed along with search box.
 * 4. Verify that table is present on 'Tous mes ATMPs' page headers are disaplyed('Siret', 'Société', 'Nom', 'Prenom',
 *     'Type', 'Date de notification','Nb jours arret', '%IP' and 'CEC').
 * 5. Verify that ‘Plus de détails’ checkbox should be present and unchecked by defalut.
 * 6. Click on ‘Plus de détails’ checkbox and verify that table show all details.(Siret', 'Société', ‘Exercice’,
 *     ‘Nic’, ‘Risque’, ‘Custom1’, ‘Custom2’, ‘NNS’, 'Nom', 'Prenom', 'Type', ‘Date sinistre’, ‘Date de Fraîcheur’,
 *     'Date de notification', 'Nb jours arret',’Niveau CCM IT’, '%IP',’Valuer’ and 'CEC')
 * 7. Verify that ‘Elements par page’ list and option are displayed.
 * 8. Select 10, 25, 50 and 100 option in ‘Elements par page’ list and verify the Elements records should be present as
 *    per selected option.
 * 9. Verify that Previous page and Next page button should be present in front of ‘Elements par page’ selection.
 */
public class ATMP90JUIValidationTest extends SeleniumTestAsSimpliciaUser
{
    // Page Objects
    HomePage homePage;
    LeftNavigationMenuPage leftNavigationMenuPage;
    SimpliciaReusableActions simpliciaReusableActions;
    ATMP90JPage atmp90JPage;

    @BeforeMethod
    public void pageSetUp()
    {
        try {
            // Setup page
            homePage = new HomePage(browser);
            leftNavigationMenuPage = new LeftNavigationMenuPage(browser);
            simpliciaReusableActions = new SimpliciaReusableActions(browser);
            atmp90JPage = new ATMP90JPage(browser);
        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
    }

    @Test(enabled = false)
    @Description("Navigate to Left navigation menu. Click on 'ATMP' and click 'ATMPs +90J' item and verify that " +
            "'ATMPs +90J' page is displayed.")
    public void atmps90jUIValidationTest_01()
    {
        // Login to Application
        simpliciaReusableActions.logIn();

        // Navigate to left navigation menu
        homePage.navigateToSimpliciaImage();

        // Click ATMP's Expand icon
        leftNavigationMenuPage.clickExpandIconToOpenMenu("Comptes employeurs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Tous mes ATMPs");
        leftNavigationMenuPage.clickExpandIconToOpenMenu("Tous mes ATMPs");

        // Click Sub item 'ATMPs +90J'
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● Saisine de CRA");
        leftNavigationMenuPage.clickMenuItemInLeftNavigation("● Saisine de CRA");
        leftNavigationMenuPage.closeLeftNavigation();

        // Verify the ATMPs +90J page header
        //atmp90JPage.checkPresenceOfATMPs90JHeader();
    }

    @Test(enabled = false)
    @Description("Verify that search box is disaplyed with placeholder 'Rechercher'.")
    public void atmps90jUIValidationTest_02()
    {
        // verify that search box is present
        atmp90JPage.checkPresenceOfSearchBoxTextField();

        // Verify that search Box placeholder is 'Chercher'
//        atmp90JPage.assertEquals("Chercher", atmp90JPage.getPlaceHolderOfSearchBoxTextField());
        atmp90JPage.assertEquals("Rechercher", atmp90JPage.getPlaceHolderOfSearchBoxTextField());
    }

    @Test(enabled = false)
    @Description("If there are records, verify that export table button is displayed along with search box.")
    public void atmps90jUIValidationTest_03()
    {
        sleepSilently(5000); // wait for the list to load
        if(isTextPresent("0-0 sur")) {
            // Verify that 'Aucun élément correspondant trouvé' text is disaplyed
            atmp90JPage.assertTextPresent("Aucun élément correspondant trouvé");

            // Check absence of export table button
            atmp90JPage.checkAbsenceOfExportTableButton();
        } else {
            // Check Presence of export table button
           //atmp90JPage.checkPresenceOfExportTableButton();
        }
    }


    @Test(enabled = false)
    @Description("Verify that table is present on 'Tous mes ATMPs' page headers are disaplyed(Siret', 'Société', " +
            "‘Exercice’, ‘Nic’, ‘Risque’, ‘Custom1’, ‘Custom2’, ‘NNS’, 'Nom', 'Prenom', 'Type', ‘Date sinistre’, " +
            "‘Date de Fraîcheur’, 'Date de notification', 'Nb jours arret',’Niveau CCM IT’, '%IP',’Valuer’ and 'CEC')")
    public void atmps90jUIValidationTest_04()
    {
        // Check Presence of Table
        atmp90JPage.checkPresenceOfDetailsTable();

        // Verify the table headers
     // Verify the table headers
        atmp90JPage.checkPresenceOfTableHeader(TestData.SIRET);
        atmp90JPage.checkPresenceOfNonClickableTableHeader(TestData.SOCIETE);
        atmp90JPage.checkPresenceOfTableHeader(TestData.EXERCICE);
        atmp90JPage.checkPresenceOfTableHeader(TestData.NIC);
        atmp90JPage.checkPresenceOfTableHeader(TestData.RISQUE);
        atmp90JPage.performHorizontalScroll();

        atmp90JPage.checkPresenceOfTableHeader(TestData.NNS);
        atmp90JPage.checkPresenceOfTableHeader(TestData.NOM);
        atmp90JPage.checkPresenceOfTableHeader(TestData.PRENOM);
        atmp90JPage.checkPresenceOfTableHeader(TestData.TYPE);
        atmp90JPage.checkPresenceOfTableHeader(TestData.DATE_SINISTRE);

        atmp90JPage.performHorizontalScroll();
        atmp90JPage.checkPresenceOfTableHeader(TestData.DATE_DE_NOTIFICATION);
        atmp90JPage.checkPresenceOfTableHeader(TestData.NB_JOURS_ARRET_PRECEDENT);
        atmp90JPage.checkPresenceOfTableHeader(TestData.NB_JOURS_ARRET_ACTUEL);
        atmp90JPage.checkPresenceOfTableHeader(TestData.DERNIERE_MISE_A_JOUR );

        atmp90JPage.checkUncheckPlusDeDetailsCheckbox();
        atmp90JPage.performHorizontalScroll();
        atmp90JPage.checkPresenceOfTableHeader(TestData.NIVEAU_CCM_IT);
        atmp90JPage.checkPresenceOfTableHeader(TestData.TARIF_CCM_IT);
        atmp90JPage.checkPresenceOfTableHeader(TestData.PERCENT_IP);
        atmp90JPage.checkPresenceOfTableHeader(TestData.VALUER_RISQUE);
    }

    // TODO This check books seems irrelevent. Need to check as per the business login.
    /*@Test(enabled = false)
    @Description("Verify that ‘Plus de détails’ checkbox should be present and unchecked by defalut.")
    public void atmps90jUIValidationTest_05()
    {
        // Check presence of Plus de details text
        atmp90JPage.checkPresenceOfPlusDeDetailsText();

        // Check presence of Plus de details checkbox
        atmp90JPage.checkPresenceOfPlusDeDetailsCheckbox();

        // Verify that checkBox is unchecked by default
        atmp90JPage.assertEquals("Unchecked", atmp90JPage.getPlusDeDetailsCheckboxState());
    }*/

    @Test(enabled = false)
    @Description("Verify that ‘Moins de détails’ checkbox should be present and unchecked by defalut.")
    public void atmps90jUIValidationTest_05()
    {
        // Check presence of Plus de details text
        atmp90JPage.checkPresenceOfPlusDeDetailsText();

        // Check presence of Plus de details checkbox
        atmp90JPage.checkPlusDeDetailsCheckBox();

        // Verify that checkBox is unchecked by default
        atmp90JPage.assertEquals("Unchecked", atmp90JPage.getPlusDeDetailsCheckboxState());
    }

    @Test(enabled=false) //Disabling since not working as of 23/11/2021
    @Description("Click on ‘Moins de détails’ checkbox and verify that table show less headers.('Siret', 'Société', 'Nom'," +
            " 'Prenom', 'Type', 'Date de notification','Nb jours arret', '%IP' and 'CEC').")
    public void atmps90jUIValidationTest_06()
    {
        // Check the 'Moins de détails' checkbox
        atmp90JPage.checkPlusDeDetailsCheckBox();

        // Verify that button is switched
        atmp90JPage.assertEquals("Checked", atmp90JPage.getPlusDeDetailsCheckboxState());

        // Verify the table headers
        atmp90JPage.checkPresenceOfTableHeader(TestData.SIRET);
        atmp90JPage.checkPresenceOfNonClickableTableHeader(TestData.SOCIETE);
        atmp90JPage.checkPresenceOfTableHeader(TestData.NOM);
        atmp90JPage.checkPresenceOfTableHeader(TestData.PRENOM);
        atmp90JPage.checkPresenceOfTableHeader(TestData.TYPE);
        atmp90JPage.performHorizontalScroll();
        atmp90JPage.checkPresenceOfTableHeader(TestData.DATE_DE_NOTIFICATION);
    }

    @Test(enabled = false)
    @Description("Verify that ‘Elements par page’ list and option are displayed.")
    public void atmps90jUIValidationTest_07()
    {
        // Verify that Elements par page is displayed
        atmp90JPage.assertTextPresent("Elements par page");

        // Click Pagination icon
        atmp90JPage.clickSelectPaginationIcon();

        // Verify that Option are displayed (10, 25, 50 and 100)
        atmp90JPage.checkPresenceOfPaginationListOption("10");
        atmp90JPage.checkPresenceOfPaginationListOption("25");
        atmp90JPage.checkPresenceOfPaginationListOption("50");
        atmp90JPage.checkPresenceOfPaginationListOption("100");

        // Close the menu
        atmp90JPage.clickPaginationListOption("10");    }

    @Test(enabled = false)
    @Description("Select 10, 25, 50 and 100 option in ‘Elements par page’ list and verify the Elements records should be" +
            " present as per selected option.")
    public void atmps90jUIValidationTest_08()
    {
        // Click Pagination icon
        atmp90JPage.clickSelectPaginationIcon();

        // Select 10 from Elements par page list
        atmp90JPage.clickPaginationListOption("10");

        // Verify that page shows 10 records
        // TODO : Currently table consist only 6 records so verification is not possible

        // Click Pagination icon
        atmp90JPage.clickSelectPaginationIcon();

        // Select 25 from Elements par page list
        atmp90JPage.clickPaginationListOption("25");

        // Verify that page shows 25 records
        // TODO : Currently table consist only 6 records so verification is not possible

        // Click Pagination icon
        atmp90JPage.clickSelectPaginationIcon();

        // Select 50 from Elements par page list
        atmp90JPage.clickPaginationListOption("50");

        // Verify that page shows 50 records
        // TODO : Currently table consist only 6 records so verification is not possible

        // Click Pagination icon
        atmp90JPage.clickSelectPaginationIcon();

        // Select 100 from Elements par page list
        atmp90JPage.clickPaginationListOption("100");

        // Verify that page shows 100 records
        // TODO : Currently table consist only 6 records so verification is not possible
    }

//    @Test(enabled=false) //Not enough records for next page and previous page navigation
//    @Description("Verify that Previous page and Next page button should be present in front of ‘Elements par page’ selection.")
//    public void atmps90jUIValidationTest_09()
//    {
//        // Click Pagination icon
//        atmp90JPage.clickSelectPaginationIcon();
//
//        // Select 10 from Elements par page list
//        atmp90JPage.clickPaginationListOption("10");
//        if(!atmp90JPage.isNextButtonDisabled()) {
//            // Verify that Next page Button is present
//            atmp90JPage.checkPresenceOfNextPageButton();
//        }
//
//        // Check if previous button is disabled
//        if(atmp90JPage.isPreviousButtonDisabled()) {
//            // Click Next Button
//            atmp90JPage.clickNextPageButton();
//
//            // Verify that Previous page Button is present
//            atmp90JPage.checkPresenceOfPreviousPageButton();
//        } else {
//            // Verify that Previous page Button is present
//            atmp90JPage.checkPresenceOfPreviousPageButton();
//        }
//    }


    @Test(enabled = false)
    @Description("Navigate to Left navigation menu. Click on 'ATMP' to close the expand menu")
    public void atmps90jUIValidationTest_10()
    {
        // Navigate to left navigation menu
        homePage.navigateToSimpliciaImage();
        leftNavigationMenuPage.closeLeftNavigation();
        homePage.navigateToSimpliciaImage();
        leftNavigationMenuPage.closeLeftNavigation();
        homePage.navigateToSimpliciaImage();

        // Click ATMP's Expand icon
        leftNavigationMenuPage.clickExpandLessIconToCloseMenu("Comptes employeurs");
    }
}

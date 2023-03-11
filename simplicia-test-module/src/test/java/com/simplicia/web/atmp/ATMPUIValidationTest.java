package com.simplicia.web.atmp;

import com.simplicia.executor.SeleniumTestAsSimpliciaUser;
import com.simplicia.pages.web.atmp.TousMesATMPsPage;
import com.simplicia.pages.web.home.HomePage;
import com.simplicia.pages.web.leftnavigationmenu.LeftNavigationMenuPage;
import com.simplicia.pages.web.utils.SimpliciaReusableActions;
import com.simplicia.pages.web.utils.TestData;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test Cases:
 *
 * 1. Navigate to Left navigation menu. Click on 'ATMP' and click 'Tous mes ATMPs' item and verify that 'Tous mes ATMPs'
 *    page is displayed.
 * 2. Verify that search box is disaplyed with placeholder 'Chercher'.
 * 3. If there are records, verify that export table button is displayed along with search box.
 * 4. Verify that filters text is displayed along with filters buttons('Tous', '+90J', '+45J', 'Rentes', 'Cette semaine',
 *   'Ce mois-ci', 'AT' and 'MP').
 * 5. Verify that table is present on 'Tous mes ATMPs' page headers are disaplyed('Siret', 'Société', 'Nom', 'Prenom',
 *    'Type', 'Date de notification','Nb jours arret', '%IP' and 'CEC').
 * 6. Verify that ‘Plus de détails’ checkbox should be present and unchecked by defalut.
 * 7. Click on ‘Plus de détails’ checkbox and verify that table show all details.(Siret', 'Société', ‘Exercice’, ‘Nic’,
 *    ‘Risque’, ‘Custom1’, ‘Custom2’, ‘NNS’, 'Nom', 'Prenom', 'Type',‘Date sinistre’, ‘Date de Fraîcheur’, 'Date de notification',
 *    'Nb jours arret',’Niveau CCM IT’, '%IP',’Valuer’ and 'CEC')
 * 8. Verify that ‘Elements par page’ list and option are displayed.
 * 9. Select 10 in ‘Elements par page’ list and verify the Elements records should be 10 per page.
 * 10. Select 25 in ‘Elements par page’ list and verify the Elements records should be 25 per page.
 * 11. Select 50 in ‘Elements par page’ list and verify the Elements records should be 50 per page.
 * 12. Select 100 in ‘Elements par page’ list and verify the Elements records should be 100 per page.
 * 13. Verify that Previous page and Next page button should be present in front of ‘Elements par page’ selection.
 *
 */

public class ATMPUIValidationTest extends SeleniumTestAsSimpliciaUser
{
    // Page Objects
    HomePage homePage;
    LeftNavigationMenuPage leftNavigationMenuPage;
    SimpliciaReusableActions simpliciaReusableActions;
    TousMesATMPsPage tousMesATMPsPage;

    @BeforeMethod
    public void pageSetUp()
    {
        try {
            // Setup pages
            homePage = new HomePage(browser);
            leftNavigationMenuPage = new LeftNavigationMenuPage(browser);
            simpliciaReusableActions = new SimpliciaReusableActions(browser);
            tousMesATMPsPage = new TousMesATMPsPage(browser);
        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
    }

    @Test(enabled=true)
    @Description("Navigate to Left navigation menu. Click on 'ATMP' and click 'Tous mes ATMPs' item and verify that " +
            "'Tous mes ATMPs' page is displayed.")
    public void atmpUIValidationTest_01()
    {
        // Login to Application
        simpliciaReusableActions.logIn();

        // Navigate to left navigation menu
        homePage.navigateToSimpliciaImage();

        // Click ATMP's Expand icon
        leftNavigationMenuPage.clickExpandIconToOpenMenu("Comptes employeurs");

        // Click Tous mes ATMPs menu item
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Tous mes ATMPs");
        leftNavigationMenuPage.clickMenuItemInLeftNavigation("Tous mes ATMPs");
        leftNavigationMenuPage.closeLeftNavigation();

        // Verify the Tous mes ATMPs page header
        //tousMesATMPsPage.checkPresenceOfTousMesATMPHeader(); // No heading for the pages now, commenting till validated
    }

    @Test(enabled=true)
    @Description("Verify that search box is disaplyed with placeholder 'Chercher'.")
    public void atmpUIValidationTest_02()
    {
        // verify that search box is present
        tousMesATMPsPage.checkPresenceOfSearchBoxTextField();

        // Verify that search Box placeholder is 'Chercher'
        tousMesATMPsPage.assertEquals("Rechercher", tousMesATMPsPage.getPlaceHolderOfSearchBoxTextField());
    }

    // No export button- comment for a while -Rakesh
    //@Test
    @Description("If there are records, verify that export table button is displayed along with search box.")
    public void atmpUIValidationTest_03()
    {
        if(isTextPresent("0-0 sur")) {
            // Verify that 'Aucun élément correspondant trouvé' text is disaplyed
            tousMesATMPsPage.assertTextPresent("Aucun élément correspondant trouvé");

            // Check absence of export table button
            tousMesATMPsPage.checkAbsenceOfExportTableButton();
        } else {
            // Check Presence of export table button
            tousMesATMPsPage.checkPresenceOfExportTableButton();
        }
    }

    /*@Test
    @Description("Verify that filters text is displayed along with filters buttons('Tous', '+90J', '+45J', 'Rentes'," +
            " 'Cette semaine', 'Ce mois-ci', 'AT' and 'MP').")
    public void atmpUIValidationTest_04()
    {
        // Verify the Presence of the different filters button present on the page
        tousMesATMPsPage.checkPresenceOfTableFilterButton(TestData.TOUS);
        tousMesATMPsPage.checkPresenceOfTableFilterButton(TestData.NINETY_J);
        tousMesATMPsPage.checkPresenceOfTableFilterButton(TestData.FOURTY_FIVE_J);
        tousMesATMPsPage.checkPresenceOfTableFilterButton(TestData.RENTES);
        tousMesATMPsPage.checkPresenceOfTableFilterButton(TestData.CETTE_SEMAINE);
        tousMesATMPsPage.checkPresenceOfTableFilterButton(TestData.CE_MOIS_CI);
        tousMesATMPsPage.checkPresenceOfTableFilterButton(TestData.AT);
        tousMesATMPsPage.checkPresenceOfTableFilterButton(TestData.MP);
    }*/
    
    //@Test(enabled=false)
    @Test
    @Description("Verify that table is present on 'Tous mes ATMPs' page headers are disaplyed(Siret', 'Société', " +
            "‘Exercice’, ‘Nic’, ‘Risque’, ‘Custom1’, ‘Custom2’, ‘NNS’, 'Nom', 'Prenom', 'Type', ‘Date sinistre’, " +
            "‘Date de Fraîcheur’, 'Date de notification', 'Nb jours arret',’Niveau CCM IT’, '%IP',’Valuer’ and 'CEC')")
    public void atmpUIValidationTest_04()
    {
        try {
			// Check Presence of Table
			tousMesATMPsPage.checkPresenceOfDetailsTable();

			// Verify the table headers
    // Verify the table headers
			// No custome 1 or 2 column - Rakesh
			tousMesATMPsPage.checkPresenceOfTableHeader(TestData.SIRET);
			tousMesATMPsPage.checkPresenceOfBoldTableHeader(TestData.SOCIETE);
			tousMesATMPsPage.checkPresenceOfTableHeader(TestData.EXERCICE);
			tousMesATMPsPage.checkPresenceOfTableHeader(TestData.NIC);
			tousMesATMPsPage.checkPresenceOfTableHeader(TestData.RISQUE);
			tousMesATMPsPage.performHorizontalScroll();
			tousMesATMPsPage.checkPresenceOfTableHeader(TestData.NNS);
			tousMesATMPsPage.checkPresenceOfTableHeader(TestData.NOM);
			tousMesATMPsPage.checkPresenceOfTableHeader(TestData.PRENOM);
			tousMesATMPsPage.checkPresenceOfTableHeader(TestData.TYPE);
			tousMesATMPsPage.checkPresenceOfTableHeader(TestData.DATE_SINISTRE);
			tousMesATMPsPage.checkPresenceOfBoldTableHeader(TestData.DATE_DE_NOTIFICATION);
			tousMesATMPsPage.checkPresenceOfTableHeader(TestData.DERNIERE_MISE_A_JOUR );
			tousMesATMPsPage.checkPresenceOfBoldTableHeader(TestData.NB_JOURS_ARRET_PRECEDENT);
			tousMesATMPsPage.checkPresenceOfBoldTableHeader(TestData.NB_JOURS_ARRET_ACTUEL);

            tousMesATMPsPage.checkNotPresenceOfTableHeader(TestData.NIVEAU_CCM_IT);
            tousMesATMPsPage.checkNotPresenceOfTableHeader(TestData.TARIF_CCM_IT);
            tousMesATMPsPage.checkNotPresenceOfTableHeader(TestData.PERCENT_IP);
            tousMesATMPsPage.checkNotPresenceOfTableHeader(TestData.VALUER_RISQUE);


            tousMesATMPsPage.checkUncheckPlusDeDetailsCheckbox();
            tousMesATMPsPage.performHorizontalScroll();

            tousMesATMPsPage.checkPresenceOfTableHeader(TestData.NIVEAU_CCM_IT);
            tousMesATMPsPage.checkPresenceOfTableHeader(TestData.TARIF_CCM_IT);
            tousMesATMPsPage.checkPresenceOfTableHeader(TestData.PERCENT_IP);
            tousMesATMPsPage.checkPresenceOfTableHeader(TestData.VALUER_RISQUE);

		} finally {
            try {
                tousMesATMPsPage.checkUncheckPlusDeDetailsCheckbox();
            } finally {
                simpliciaReusableActions.logOut();
            }
		}
    }

    /*@Test
    @Description("Verify that table is present on 'Tous mes ATMPs' page headers are disaplyed('Siret', 'Société', 'Nom', " +
            "'Prenom', 'Type', 'Date de notification','Nb jours arret', '%IP' and 'CEC').")
    public void atmpUIValidationTest_05()
    {
        // Check Presence of Table
        tousMesATMPsPage.checkPresenceOfDetailsTable();

        // Verify the table headers
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.SIRET);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.SOCIETE);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.NOM);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.PRENOM);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.TYPE);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.DATE_DE_NOTIFICATION);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.NB_JOURS_ARRET);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.PERCENT_IP);
        tousMesATMPsPage.checkPresenceOfNonClickableTableHeader(TestData.CEC);
    }*/
    
    @Test(enabled=true)
    @Description("Verify that ‘Plus de détails’ checkbox should be present and unchecked by defalut.")
    public void atmpUIValidationTest_05()
    {
    	// Login to Application
        simpliciaReusableActions.logIn();

        // Navigate to left navigation menu
        homePage.navigateToSimpliciaImage();

        // Click ATMP's Expand icon
        leftNavigationMenuPage.clickExpandIconToOpenMenu("Comptes employeurs");

        // Click Tous mes ATMPs menu item
        leftNavigationMenuPage.clickMenuItemInLeftNavigation("Tous mes ATMPs");
        leftNavigationMenuPage.closeLeftNavigation();
        
        // Check presence of ‘Plus de détails’ text
    	tousMesATMPsPage.checkPresenceOfPlusDeDetailsText();

        // Check presence of ‘Plus de détails’ checkbox
    	tousMesATMPsPage.checkPresenceOfPlusDeDetailsCheckbox();

        // Verify that checkBox is unchecked by default
    	tousMesATMPsPage.assertEquals("Unchecked", tousMesATMPsPage.getPlusDeDetailsCheckboxState());
    }

    /*@Test
    @Description("Verify that ‘Plus de détails’ checkbox should be present and unchecked by defalut.")
    public void atmpUIValidationTest_06()
    {
        // Check presence of Plus de details text
        tousMesATMPsPage.checkPresenceOfPlusDeDetailsText();

        // Check presence of Plus de details checkbox
        tousMesATMPsPage.checkPresenceOfPlusDeDetailsCheckbox();

        // Verify that checkBox is unchecked by default
        tousMesATMPsPage.assertEquals("Unchecked", tousMesATMPsPage.getPlusDeDetailsCheckboxState());
    }*/
    
    @Test(enabled=true)
    @Description("Click on ‘Plus de détails’ checkbox and verify that table show less headers.('Siret', 'Société', 'Nom'," +
            " 'Prenom', 'Type', 'Date de notification','Nb jours arret', '%IP' and 'CEC').")
    public void atmpUIValidationTest_06()
    {
        // Check the 'Plus de détails' checkbox
    	tousMesATMPsPage.checkPlusDeDetailsCheckBox();

        // Verify that button is switched
    	tousMesATMPsPage.assertEquals("Checked", tousMesATMPsPage.getPlusDeDetailsCheckboxState());

        // Verify the table headers
    	tousMesATMPsPage.checkPresenceOfTableHeader(TestData.SIRET);
    	tousMesATMPsPage.checkPresenceOfBoldTableHeader(TestData.SOCIETE);
    	tousMesATMPsPage.checkPresenceOfTableHeader(TestData.NOM);
    	tousMesATMPsPage.checkPresenceOfTableHeader(TestData.PRENOM);
    	tousMesATMPsPage.checkPresenceOfTableHeader(TestData.TYPE);
    	tousMesATMPsPage.checkPresenceOfBoldTableHeader(TestData.DATE_DE_NOTIFICATION);
    	tousMesATMPsPage.checkPresenceOfTableHeader(TestData.PERCENT_IP);
    }

    @Test(enabled=true)
    @Description("Click on ‘Plus de détails’ checkbox and verify that table show all details.(Siret', 'Société', " +
            "‘Exercice’, ‘Nic’, ‘Risque’, ‘Custom1’, ‘Custom2’, ‘NNS’, 'Nom', 'Prenom', 'Type', ‘Date sinistre’, " +
            "‘Date de Fraîcheur’, 'Date de notification', 'Nb jours arret',’Niveau CCM IT’, '%IP',’Valuer’ and 'CEC')")
    public void atmpUIValidationTest_07()
    {
        // Check the 'Plus de détails' checkbox
    	// TODO check the lable in frontend and change here accordingly
        tousMesATMPsPage.checkPlusDeDetailsCheckBox();
        // Verify that button is switched
//        tousMesATMPsPage.assertEquals("Checked", tousMesATMPsPage.getPlusDeDetailsCheckboxState());

        // Verify the table headers
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.SIRET);
        tousMesATMPsPage.checkPresenceOfBoldTableHeader(TestData.SOCIETE);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.EXERCICE);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.NIC);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.RISQUE);
        tousMesATMPsPage.performHorizontalScroll();
        //tousMesATMPsPage.checkPresenceOfTableHeader(TestData.CUSTOM1);
        //tousMesATMPsPage.checkPresenceOfTableHeader(TestData.CUSTOM2);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.NNS);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.NOM);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.PRENOM);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.TYPE);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.DATE_SINISTRE);
//        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.DATE_DE_FRAICHEUR);
        tousMesATMPsPage.performHorizontalScroll();
        tousMesATMPsPage.checkPresenceOfBoldTableHeader(TestData.DATE_DE_NOTIFICATION);
        tousMesATMPsPage.checkPresenceOfBoldTableHeader(TestData.NB_JOURS_ARRET_PRECEDENT);
        tousMesATMPsPage.checkPresenceOfBoldTableHeader(TestData.NB_JOURS_ARRET_ACTUEL);
        tousMesATMPsPage.checkPresenceOfTableHeader(TestData.DERNIERE_MISE_A_JOUR );

    }

    @Test(enabled=true)
    @Description("Verify that ‘Elements par page’ list and option are displayed.")
    public void atmpUIValidationTest_08()
    {
        // Verify that Elements par page is displayed
        tousMesATMPsPage.assertTextPresent("Elements par page");

        // Click Pagination icon
        tousMesATMPsPage.clickSelectPaginationIcon();

        // Verify that Option are displayed (10, 25, 50 and 100)
        tousMesATMPsPage.checkPresenceOfPaginationListOption("10");
        tousMesATMPsPage.checkPresenceOfPaginationListOption("25");
        tousMesATMPsPage.checkPresenceOfPaginationListOption("50");
        tousMesATMPsPage.checkPresenceOfPaginationListOption("100");

        // Close the menu
        tousMesATMPsPage.clickPaginationListOption("10");    }

    @Test(enabled=true)
    @Description("Select 10 in ‘Elements par page’ list and verify the Elements records should be 10 per page.")
    public void atmpUIValidationTest_09()
    {
        // Click Pagination icon
        tousMesATMPsPage.clickSelectPaginationIcon();

        // Select 10 from Elements par page list
        tousMesATMPsPage.clickPaginationListOption("10");

        // Verify that page shows 10 records
        // TODO : Currently table consist only 1 record so verification is not possible
        //tousMesATMPsPage.assertTextPresent("1-10 sur");
    }

    @Test(enabled=true)
    @Description("Select 25 in ‘Elements par page’ list and verify the Elements records should be 25 per page.")
    public void atmpUIValidationTest_10()
    {
        // Click Pagination icon
        tousMesATMPsPage.clickSelectPaginationIcon();

        // Select 25 from Elements par page list
        tousMesATMPsPage.clickPaginationListOption("25");

        // Verify that page shows 25 records
        // TODO : Currently table consist only 1 record so verification is not possible
        //tousMesATMPsPage.assertTextPresent("1-25 sur");
    }

    @Test(enabled=true)
    @Description("Select 50 in ‘Elements par page’ list and verify the Elements records should be 50 per page.")
    public void atmpUIValidationTest_11()
    {
        // Click Pagination icon
        tousMesATMPsPage.clickSelectPaginationIcon();

        // Select 50 from Elements par page list
        tousMesATMPsPage.clickPaginationListOption("50");

        // Verify that page shows 50 records 
        // TODO : Currently table consist only 1 record so verification is not possible
        //tousMesATMPsPage.assertTextPresent("1-50 sur");
        
    }

    @Test(enabled=true)
    @Description("Select 100 in ‘Elements par page’ list and verify the Elements records should be 100 per page.")
    public void atmpUIValidationTest_12()
    {
        // Click Pagination icon
        tousMesATMPsPage.clickSelectPaginationIcon();

        // Select 100 from Elements par page list
        tousMesATMPsPage.clickPaginationListOption("100");

        // Verify that page shows 100 records
        // TODO : Currently table consist only 62 records so verification is not possible

    }

//    @Test(enabled=false) //currently Next button not implemented
//    @Description("Verify that Previous page and Next page button should be present in front of ‘Elements par page’ selection.")
//    public void atmpUIValidationTest_13()
//    {
//        // Click Pagination icon
//        tousMesATMPsPage.clickSelectPaginationIcon();
//
//        // Select 10 from Elements par page list
//        tousMesATMPsPage.clickPaginationListOption("10");
//
//        // Verify that Next page Button is present
//        tousMesATMPsPage.checkPresenceOfNextPageButton();
//
//        // Check if previous button is disabled
//        if(tousMesATMPsPage.isPreviousButtonDisabled()) {
//            // Click Next Button
//            tousMesATMPsPage.clickNextPageButton();
//
//            // Verify that Previous page Button is present
//            tousMesATMPsPage.checkPresenceOfPreviousPageButton();
//        } else {
//            // Verify that Previous page Button is present
//            tousMesATMPsPage.checkPresenceOfPreviousPageButton();
//        }
//    }
    
    @Test(enabled=true)
    @Description("Navigate to Left navigation menu. Click on 'ATMP' to close the expand menu")
    public void atmps90jUIValidationTest_14()
    {
        // Navigate to left navigation menu
        homePage.navigateToSimpliciaImage();

        // Click ATMP's Expand icon
        leftNavigationMenuPage.clickExpandLessIconToCloseMenu("Comptes employeurs");
    }
}
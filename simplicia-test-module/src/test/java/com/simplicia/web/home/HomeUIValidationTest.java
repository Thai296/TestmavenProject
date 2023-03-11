package com.simplicia.web.home;

import com.simplicia.executor.SeleniumTestAsSimpliciaUser;
import com.simplicia.functions.Utility;
import com.simplicia.pages.web.home.HomePage;
import com.simplicia.pages.web.utils.SimpliciaReusableActions;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test Cases Of Home Page
 *
 * 1. Verify that 'ATMPs', 'ATMPs +45J', 'ATMPs +90J' and 'ATMPs +150J' blocks are displayed.
 * 2. Verify that 'ATMPs de la semaine', 'ATMPs du mois', 'Rentes' and 'Accès Net Entreprises' blocks are displayed.
 * 3. Verify that 'Mes ATMPs' and 'Mes BPIJs' blocks are displayed.
 * 4. Verify that current year is selected and disable by default in 'Mes ATMPs' block
 * 5. Verify that previous years of 'Mes ATMPs' block get disabled after clicking.
 * 6. Verify that current year is selected and disable by default in 'Mes BPIJs' block.
 * 7. Verify that previous years of 'Mes BPIJs' block get disabled after clicking..
 * 8. Verify that 'ATMPs par Siret' and 'ATMPs par number de jours d'arrêt' blocks are displayed..
 * 9. Verify that footer is displayed correctly.
 *
 */

/**
 * This class is failing , all these checks are in left navigation panel
 * Keeping this as it is, update after clarification --Rakesh
 */

public class HomeUIValidationTest extends SeleniumTestAsSimpliciaUser
{
    HomePage homePage;
    SimpliciaReusableActions simpliciaReusableActions;

    @BeforeMethod
    public void pageSetUp()
    {
        try {
            // Setup page
            homePage = new HomePage(browser);
            simpliciaReusableActions = new SimpliciaReusableActions(browser);
            //simpliciaReusableActions.logIn();

        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
    }

    @Test
    @Description("Verify that 'ATMPs', 'ATMPs +45J', 'ATMPs +90J' and 'ATMPs +150J' blocks are displayed.")
    public void homeUIValidationTest_01()
    {
        // Login to Application
        simpliciaReusableActions.logIn();

        // Necessary 2 seconds wait for page load
        homePage.wait("Necessary 2 seconds wait for page load", 2);

        /*
         Check presence of 'ATMPs', 'ATMPs +45J', 'ATMPs +90J' and 'ATMPs +150J' blocks
         */
        homePage.checkPresenceOfBlockOnHomeNew("Mes déclarations d\'accident de travail");
        //homePage.checkPresenceOfBlockOnHome("ATMPs");
        // homePage.checkPresenceOfBlockOnHome("ATMPs +45J");
        //homePage.checkPresenceOfBlockOnHome("ATMPs +90J");
        //homePage.checkPresenceOfBlockOnHome("ATMPs +150J");
    }

    /**
     *
     * These elements re not present in Home UI - uncomment after clarification
     */

    //@Test
    @Description("Verify that 'ATMPs de la semaine', 'ATMPs du mois', 'Rentes' and 'Accès Net Entreprises' blocks are displayed.")
    public void homeUIValidationTest_02()
    {
        /*
         Check presence of 'ATMPs de la semaine', 'ATMPs du mois', 'Rentes' and 'Accès Net Entreprises' blocks
         */
        homePage.checkPresenceOfBlockOnHome("ATMPs de la semaine");
        homePage.checkPresenceOfBlockOnHome("ATMPs du mois");
        homePage.checkPresenceOfBlockOnHome("Rentes");
        homePage.checkPresenceOfBlockOnHome("Accès Net Entreprises");
    }

    //@Test
    @Description("Verify that 'Mes ATMPs' and 'Mes BPIJs' blocks are displayed.")
    public void homeUIValidationTest_03()
    {
        /*
         Check presence of 'Mes ATMPs' and 'Mes BPIJs' blocks
         */
        homePage.checkPresenceOfBlockOnHome("Mes ATMPs");
        homePage.checkPresenceOfBlockOnHome("Mes BPIJs");
    }

    //@Test
    @Description("Verify that current year is selected and disable by default in 'Mes ATMPs' block")
    public void homeUIValidationTest_04()
    {
        // Check current year is selected in 'Mes ATMPs' block
        homePage.checkPresenceOfSelectedYear("Mes ATMPs", Utility.getCurrentYear());
    }

    //@Test
    @Description("Verify that previous years of 'Mes ATMPs' block get disabled after clicking.")
    public void homeUIValidationTest_05()
    {
        /*
         Check point to verify that years get disabled after clicking
         */
        homePage.clickYearInBlock("Mes ATMPs", Utility.getPastYear(1));
        homePage.checkPresenceOfSelectedYear("Mes ATMPs", Utility.getPastYear(1));
        homePage.clickYearInBlock("Mes ATMPs", Utility.getPastYear(2));
        homePage.checkPresenceOfSelectedYear("Mes ATMPs", Utility.getPastYear(2));
        homePage.clickYearInBlock("Mes ATMPs", Utility.getPastYear(3));
        homePage.checkPresenceOfSelectedYear("Mes ATMPs", Utility.getPastYear(3));
        homePage.clickYearInBlock("Mes ATMPs", Utility.getPastYear(4));
        homePage.checkPresenceOfSelectedYear("Mes ATMPs", Utility.getPastYear(4));
//        homePage.clickYearInBlock("Mes ATMPs", Utility.getPastYear(5));
//        homePage.checkPresenceOfSelectedYear("Mes ATMPs", Utility.getPastYear(5));
    }

    //@Test
    @Description("Verify that current year is slected and disable by default in 'Mes BPIJs' block.")
    public void homeUIValidationTest_06()
    {
        // Check current year is selected in 'Mes BPIJs' block
        homePage.checkPresenceOfSelectedYear("Mes BPIJs", Utility.getCurrentYear());
    }

    //@Test
    @Description("Verify that previous years of 'Mes BPIJs' block get disabled after clicking.")
    public void homeUIValidationTest_07()
    {
        /*
         Check point to verify that years get disabled after clicking
         */
        homePage.clickYearInBlock("Mes BPIJs", Utility.getPastYear(1));
        homePage.checkPresenceOfSelectedYear("Mes BPIJs", Utility.getPastYear(1));
        homePage.clickYearInBlock("Mes BPIJs", Utility.getPastYear(2));
        homePage.checkPresenceOfSelectedYear("Mes ATMPs", Utility.getPastYear(2));
//        homePage.clickYearInBlock("Mes BPIJs", Utility.getPastYear(3));
//        homePage.checkPresenceOfSelectedYear("Mes BPIJs", Utility.getPastYear(3));
//        homePage.clickYearInBlock("Mes BPIJs", Utility.getPastYear(4));
//        homePage.checkPresenceOfSelectedYear("Mes BPIJs", Utility.getPastYear(4));
//        homePage.clickYearInBlock("Mes BPIJs", Utility.getPastYear(5));
//        homePage.checkPresenceOfSelectedYear("Mes BPIJs", Utility.getPastYear(5));
    }

    //@Test
    @Description("Verify that 'ATMPs par Siret' and 'ATMPs par nombre de jours d'arrêt' blocks are displayed.")
    public void homeUIValidationTest_08()
    {
        /*
         Check presence of 'Mes ATMPs' and 'Mes BPIJs' blocks
         */
        homePage.checkPresenceOfBlockOnHome("ATMPs par Siret");
//        homePage.checkPresenceOfBlockOnHome("ATMPs par nombre de jours");
        homePage.checkPresenceOfBlockOnHome("ATMPs par nombre de jours d'arrêt");
        
    }

    @Test
    @Description("Verify that footer is disaplyed correctly.")
    public void homeUIValidationTest_09()
    {
        // Check presence of Web Footer Text
        homePage.checkPresenceOfFooterText();
    }

}

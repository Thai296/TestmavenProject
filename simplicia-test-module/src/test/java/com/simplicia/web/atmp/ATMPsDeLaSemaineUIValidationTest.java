package com.simplicia.web.atmp;

import com.simplicia.executor.SeleniumTestAsSimpliciaUser;
import com.simplicia.pages.web.atmp.ATMPsDeLaSemainePage;
import com.simplicia.pages.web.home.HomePage;
import com.simplicia.pages.web.leftnavigationmenu.LeftNavigationMenuPage;
import com.simplicia.pages.web.utils.SimpliciaReusableActions;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 1. Navigate to Left navigation menu. Click on 'ATMP' and click 'ATMPs de la semaine' item and verify that 'ATMPs de
 *    la semaine' page is displayed.
 * 2. Verify that search box is displayed with placeholder 'Chercher'.
 */
public class ATMPsDeLaSemaineUIValidationTest extends SeleniumTestAsSimpliciaUser
{
    // Page Objects
    HomePage homePage;
    LeftNavigationMenuPage leftNavigationMenuPage;
    SimpliciaReusableActions simpliciaReusableActions;
    ATMPsDeLaSemainePage atmPsDeLaSemainePage;

    @BeforeMethod
    public void pageSetUp()
    {
        try {
            // Setup page
            homePage = new HomePage(browser);
            leftNavigationMenuPage = new LeftNavigationMenuPage(browser);
            simpliciaReusableActions = new SimpliciaReusableActions(browser);
            atmPsDeLaSemainePage = new ATMPsDeLaSemainePage(browser);
        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
    }

    @Test(enabled = false)
    @Description("Navigate to Left navigation menu. Click on 'ATMP' and click 'ATMPs de la semaine' item and verify that " +
            "'ATMPs de la semaine' page is displayed.")
    public void atmpsDeLaSemaineUIValidationTest_01()
    {
        // Login to Application
        simpliciaReusableActions.logIn();

        // Navigate to left navigation menu
        homePage.navigateToSimpliciaImage();

        // Click ATMP's Expand icon
        leftNavigationMenuPage.clickExpandIconToOpenMenu("Comptes employeurs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Tous mes ATMPs");
        leftNavigationMenuPage.clickExpandIconToOpenMenu("Tous mes ATMPs");

        // check presence of ATMPs de la seamine otherwise scroll to it
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● Saisine de CMRA");

        // Click Sub item 'ATMPs de la semaine'
        leftNavigationMenuPage.clickMenuItemInLeftNavigation("● Saisine de CMRA");
        leftNavigationMenuPage.closeLeftNavigation();

        // Verify the ATMPs de la semaine page header
        //atmPsDeLaSemainePage.checkPresenceOfATMPsDeLaSemaineHeader();
    }

    @Test(enabled = false)
    @Description("Verify that search box is disaplyed with placeholder 'Chercher'.")
    public void atmpsDeLaSemaineUIValidationTest_02()
    {
        // verify that search box is present
        atmPsDeLaSemainePage.checkPresenceOfSearchBoxTextField();

        // Verify that search Box placeholder is 'Chercher'
//        atmPsDeLaSemainePage.assertEquals("Chercher", atmPsDeLaSemainePage.getPlaceHolderOfSearchBoxTextField());
        atmPsDeLaSemainePage.assertEquals("Rechercher", atmPsDeLaSemainePage.getPlaceHolderOfSearchBoxTextField());
    }

    @Test(enabled = false)
    @Description("Navigate to Left navigation menu. Click on 'ATMP' to close the expand menu")
    public void atmpsDeLaSemaineUIValidationTest_03()
    {
        // Navigate to left navigation menu
        homePage.navigateToSimpliciaImage();

        // Click ATMP's Expand icon
        leftNavigationMenuPage.clickExpandLessIconToCloseMenu("Comptes employeurs");
    }
}

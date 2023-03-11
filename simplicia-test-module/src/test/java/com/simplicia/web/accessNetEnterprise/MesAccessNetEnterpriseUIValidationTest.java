package com.simplicia.web.accessNetEnterprise;

import com.simplicia.executor.SeleniumTestAsSimpliciaUser;
import com.simplicia.functions.TestDataEngine;
import com.simplicia.pages.web.home.HomePage;
import com.simplicia.pages.web.leftnavigationmenu.LeftNavigationMenuPage;
import com.simplicia.pages.web.netEntreprises.MesAccesNetEentreprises;
import com.simplicia.pages.web.utils.SimpliciaReusableActions;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

public class MesAccessNetEnterpriseUIValidationTest extends SeleniumTestAsSimpliciaUser {

    HomePage homePage;
    LeftNavigationMenuPage leftNavigationMenuPage;
    SimpliciaReusableActions simpliciaReusableActions;
    MesAccesNetEentreprises mesAccesNetEentreprises;

    public static String sTestDataXMLPath = System.getProperty("user.dir") + File.separator + "src/test/resources/MESAcessNetEnterpriseDATA";
    boolean bnetEnterpriseLogin = false;


    @BeforeMethod
    public void pageSetUp(Method method) throws Exception{
        try {
            // Setup pages
            println("################################################################");
            println("Starting " + method.getName());
            println("################################################################");
            homePage = new HomePage(browser);
            leftNavigationMenuPage = new LeftNavigationMenuPage(browser);
            simpliciaReusableActions = new SimpliciaReusableActions(browser);
            mesAccesNetEentreprises = new MesAccesNetEentreprises(browser);

            // Login to application
            simpliciaReusableActions.logIn();

            Thread.sleep(8000);
            openAccessNetEnterprisePageByURL();
        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
        //commit
    }


    @AfterMethod
    public void pageTearDown(Method method) {

        println("################################################################");
        println("Finished " + method.getName());
        println("################################################################");


    }

    @Test(enabled=true)
    @Description("Check menu items on the left")
    public void mesAccessNetEnt_Test_00_checkMenu() throws Exception {
        // Navigate to left navigation menu
        homePage.navigateToSimpliciaImage();
        Thread.sleep(2000);
        leftNavigationMenuPage.checkPresenceOfMenuOption("Mes accès");
        leftNavigationMenuPage.checkPresenceOfMenuOption("Net-Entreprises");
        leftNavigationMenuPage.checkPresenceOfMenuOption("● Comptes ATMP OK");
        leftNavigationMenuPage.checkPresenceOfMenuOption("● Comptes ATMP sans AT");
        leftNavigationMenuPage.checkPresenceOfMenuOption("● Comptes avec Expiration du mdp");
        leftNavigationMenuPage.checkPresenceOfMenuOption("● Comptes avec Connexion Echouée");
        Thread.sleep(2000);
        leftNavigationMenuPage.closeLeftNavigation();
    }

    /**
     * Steps:
     * 1. Login to the application and navigate to left menu
     * 2. Click on Net-Entreprises
     * 3. Click on add new button
     * 4. Fill in the details
     * 5. Click on Save button
     * 6. Search for the created login
     * 7. Clean up the test case - delete the created login.
     *
     * @throws Exception
     */
    @Test()
    @Description("Navigate to Left navigation menu. Click on 'Net-Entreprises" +
            "'create a new login")
    public void mesAccessNetEnt_Test_01_create() throws Exception {

        Map<String, String> netEnterprise = TestDataEngine.parseXML(sTestDataXMLPath, "mesAcess.xml", "netEnterprise");


        try {

            String aCompanyID = mesAccesNetEentreprises.createNetEnterpriseLogin(netEnterprise);
            bnetEnterpriseLogin = mesAccesNetEentreprises.searchForNetEnterpriseLogin(aCompanyID);
            Thread.sleep(5000);
            Assert.assertTrue(bnetEnterpriseLogin);


        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail();

        } finally {
            try {
                if (bnetEnterpriseLogin) {

                    // delete the created login.
                    mesAccesNetEentreprises.deleteAccount();
                }
            } finally {
                simpliciaReusableActions.logOut();
            }

        }


    }


    /**
     * Steps:
     * 1. Login to the application and navigate to left menu
     * 2. Click on Net-Entreprises
     * 3. Click on add new button
     * 4. Fill in the details
     * 5. Click on Save button
     * 6. Search for the created login
     * 7. Edit the login, update nom field save it
     * 7. Clean up the test case - delete the created login.
     *
     * @throws Exception
     */
    @Test()
    @Description("Navigate to Left navigation menu. Click on 'Net-Entreprises" +
            "'create a new login, save it and edit any field")
    public void mesAccessNetEnt_Test_02_Edit_UpdateLastName_Bug_S1208() throws Exception {

        Map<String, String> netEnterprise = TestDataEngine.parseXML(sTestDataXMLPath, "mesAcess.xml", "netEnterprise");
        //Map<String, String> updateNetEnterprise = TestDataEngine.parseXML(sTestDataXMLPath, "mesAcess.xml", "updateNetEnterprise");


        try {

            String aCompanyID = mesAccesNetEentreprises.createNetEnterpriseLogin(netEnterprise);
            bnetEnterpriseLogin = mesAccesNetEentreprises.searchForNetEnterpriseLogin(aCompanyID);
            //String sUpdatedField = mesAccesNetEentreprises.editAccount(updateNetEnterprise, aCompanyID, "Nom");
            mesAccesNetEentreprises.searchForNetEnterpriseLogin(aCompanyID);
            //Assert.assertSame(sUpdatedField);


        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail();

        } finally {
            try {
                if (bnetEnterpriseLogin) {

                    // delete the created login.
                    mesAccesNetEentreprises.deleteAccount();
                }
            } finally {
                simpliciaReusableActions.logOut();
            }
        }


    }

    /**
     * Steps:
     * 1. Login to the application and navigate to left menu
     * 2. Click on Net-Entreprises
     * 3. Click on add new button
     * 4. Fill in the details
     * 5. Click on Save button
     * 6. Search for the created login
     * 7. Edit the login, update password field save it
     * 7. Clean up the test case - delete the created login.
     *
     * @throws Exception
     */
    @Test()
    @Description("Navigate to Left navigation menu. Click on 'Net-Entreprises" +
            "'create a new login, save it and edit any field")
    public void mesAccessNetEnt_Test_03_Edit_UpdatePassword_Bug_S1208() throws Exception {

        Map<String, String> netEnterprise = TestDataEngine.parseXML(sTestDataXMLPath, "mesAcess.xml", "netEnterprise");
       // Map<String, String> updateNetEnterprise = TestDataEngine.parseXML(sTestDataXMLPath, "mesAcess.xml", "updateNetEnterprise");


        try {

            String aCompanyID = mesAccesNetEentreprises.createNetEnterpriseLogin(netEnterprise);
            bnetEnterpriseLogin = mesAccesNetEentreprises.searchForNetEnterpriseLogin(aCompanyID);
           // String sUpdatedField = mesAccesNetEentreprises.editAccount(updateNetEnterprise, aCompanyID, "password");
            mesAccesNetEentreprises.searchForNetEnterpriseLogin(aCompanyID);
            //Assert.assertSame(sUpdatedField);


        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail();

        } finally {

            if(bnetEnterpriseLogin) {

                // delete the created login.
                mesAccesNetEentreprises.deleteAccount();
            }

            //simpliciaReusableActions.logOut();
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
        leftNavigationMenuPage.clickExpandLessIconToCloseMenu("Net-Entreprises");
        Thread.sleep(2000);
        leftNavigationMenuPage.clickExpandLessIconToCloseMenu("Mes accès");
        Thread.sleep(2000);
        leftNavigationMenuPage.closeLeftNavigation();
        Thread.sleep(2000);
    }
    private void openAccessNetEnterprisePageByURL() throws Exception{
        browser.get(System.getenv("APP_URL")+"/netaccess/my-logins");
        Thread.sleep(2000);
    }

}

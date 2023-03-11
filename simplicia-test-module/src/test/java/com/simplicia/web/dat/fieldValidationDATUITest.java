package com.simplicia.web.dat;

import com.simplicia.executor.SeleniumTestAsSimpliciaUser;
import com.simplicia.pages.web.dat.DATMainPage;
import com.simplicia.pages.web.dat.createDAT.*;
import com.simplicia.pages.web.home.HomePage;
import com.simplicia.pages.web.leftnavigationmenu.LeftNavigationMenuPage;
import com.simplicia.pages.web.utils.SimpliciaReusableActions;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class fieldValidationDATUITest extends SeleniumTestAsSimpliciaUser {

    HomePage homePage;
    LeftNavigationMenuPage leftNavigationMenuPage;
    SimpliciaReusableActions simpliciaReusableActions;
    DATMainPage datMainPage;
    MyProfileTab myProfileTab;
    LaVictimeTab laVictimeTab;
    Accident1Tab accident1Tab;
    Accident2Tab accident2Tab;
    LesTemoinsTab lesTemoinsTab;
    LeTiersTab leTiersTab;
    SignatureTab signatureTab;


    @BeforeMethod
    public void pageSetUp(Method method) {
        try {
            // Setup pages
            println("################################################################");
            println("Starting " + method.getName());
            println("################################################################");
            homePage = new HomePage(browser);
            leftNavigationMenuPage = new LeftNavigationMenuPage(browser);
            simpliciaReusableActions = new SimpliciaReusableActions(browser);
            datMainPage = new DATMainPage(browser);
            myProfileTab = new MyProfileTab(browser);
            laVictimeTab = new LaVictimeTab(browser);
            accident1Tab = new Accident1Tab(browser);
            accident2Tab = new Accident2Tab(browser);
            lesTemoinsTab = new LesTemoinsTab(browser);
            leTiersTab = new LeTiersTab(browser);
            signatureTab = new SignatureTab(browser);


            // Login to application
            simpliciaReusableActions.logIn();

            // Navigate to left navigation menu
            navigateToDatOption("Mes DATs", true);
            datMainPage.clickCreateNewDATButton();
        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
    }

    @Test()
    @Description("Navigate to Left navigation menu. Click on 'DAT' and click 'Mes DATs" +
            "' and validate the step 1 fields constraints")
    public void dat_Filed_Validation_Test_01_Step1() throws Exception {


        try {

            //myProfileTab.selectCompanyIcon.shouldBeDisplayed();
            //myProfileTab.selectCompanyIcon.click();
           // myProfileTab.selectItemFromDropDownList(mDATMyProfile.get("ACCESS_NET_ENTERPRISE"));



        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail();

        } finally {


        }


    }



    @AfterMethod
    public void pageTearDown(Method method) {

        println("################################################################");
        println("Finished " + method.getName());
        println("################################################################");


    }



    /// private methods
    private void navigateToDatOption(String sMenu, boolean b) {
        homePage.navigateToSimpliciaImage();
        if (b) {

            leftNavigationMenuPage.clickExpandIconToOpenMenu("DAT");
        }
        leftNavigationMenuPage.clickMenuItemInLeftNavigation(sMenu);
        leftNavigationMenuPage.closeLeftNavigation();
    }



}

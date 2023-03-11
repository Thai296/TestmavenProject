package com.simplicia.web.debugger;

import com.simplicia.executor.SeleniumTestAsSimpliciaUser;
import com.simplicia.pages.web.SmartPage;
import com.simplicia.pages.web.home.HomePage;
import com.simplicia.pages.web.leftnavigationmenu.LeftNavigationMenuPage;
import com.simplicia.pages.web.login.LoginPage;
import com.simplicia.pages.web.utils.SimpliciaReusableActions;
import com.simplicia.pages.web.utils.TestData;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

public class DebuggerUITest extends SeleniumTestAsSimpliciaUser {

    LoginPage loginPage;
    HomePage homePage;
    SimpliciaReusableActions simpliciaReusableActions;
    SmartPage smartPage;

    LeftNavigationMenuPage leftNavigationMenuPage;

    @BeforeMethod
    public void pageSetUp()
    {
        try {
            // Setup page
            loginPage = new LoginPage(browser);
            homePage = new HomePage(browser);
            smartPage = new SmartPage(browser);
            simpliciaReusableActions = new SimpliciaReusableActions(browser);
            leftNavigationMenuPage = new LeftNavigationMenuPage(browser);
            simpliciaReusableActions.logIn();
        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
    }

    //@Test
    @Description("Verify something in a script test")
    public void JustSimpleUIValidationTest_01()
    {
        // Login into Application
        loadUrl(TestData.APPLICATION_URL);
        simpliciaReusableActions.logIn();
        homePage.wait("Necessary 2 seconds wait for page load", 2);
        HashMap<String, String> map = smartPage.scanElements("");
        smartPage.generatePOM("~/Workspace/generated.java", map);
    }

    @Test
    @Description("Verify menu open")
    public  void VerifyMenuOpenTest_02() {
        boolean b = leftNavigationMenuPage.menuHome.isFound();
        if (b) {
            leftNavigationMenuPage.clickToOpenMenu(); // to open menu
            leftNavigationMenuPage.clickMenuItemInLeftNavigation("DAT");
            leftNavigationMenuPage.clickExpandIconToOpenMenu("DAT");
            leftNavigationMenuPage.clickMenuItemInLeftNavigation("Mes DATs");
            //addScreenshot(this.browser);
            this.waitForPageLoadCompleted();

            //loadUrl("https://qa2.simplicia.co:7443/dat/in-progress");
            //this.waitForPageLoadCompleted();
            // //button[contains(.,"NON MERCI")]
        }
        smartPage.testTable();
        LOGGER.info("yeah");
    }
}

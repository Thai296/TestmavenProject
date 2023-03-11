package com.simplicia.web;

import com.simplicia.pages.web.utils.TestData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class LoginOutLawyerPerMethodBaseTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void testSetup() {
        println(">>>> LoginOutLawyerPerMethodBaseTest@BeforeMethod: " + this.getClass().getName());
        try {
            // Login into Application
            loadUrl(TestData.APPLICATION_URL);
            simpliciaReusableActions.logInAsLawyer();
            logoutFlag = false;
        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
    }

    @AfterMethod(alwaysRun = true)
    public void testFinish() {
        println(">>>> LoginOutLawyerPerMethodBaseTest@AfterMethod: " + this.getClass().getName());
        try {
            simpliciaReusableActions.logOut();
        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
    }

    protected void lawyerSwitchClient(String clientName) {
        homePage.openLeftPanel();
        // go to dashboard to switch client
        goToDashboard();
        // switch to new client
        dashboardPage.checkPresenceButton(clientName);
        dashboardPage.switchClient(clientName);
        dashboardPage.checkPresenceOfSuccessfulMessage();
        leftNavigationMenuPage.checkPresenceOfLeftNavigationHeader(clientName);
        homePage.closeLeftPanel();
    }

    private void goToDashboard() {
        simpliciaReusableActions.checkPresenceOfIcon("dashboard");
        simpliciaReusableActions.clickIcon("dashboard");
    }
}

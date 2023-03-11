package com.simplicia.web;

import com.simplicia.pages.web.utils.TestData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class LoginOutClientPerMethodBaseTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void testSetup() {
        println(">>>> LoginOutClientPerMethodBaseTest@BeforeMethod: " + this.getClass().getName());
        try {
            // Login into Application
            loadUrl(TestData.APPLICATION_URL);
            simpliciaReusableActions.logIn();
            logoutFlag = false;
        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
    }

    @AfterMethod(alwaysRun = true)
    public void testFinish() {
        println(">>>> LoginOutClientPerMethodBaseTest@AfterMethod: " + this.getClass().getName());
        try {
            simpliciaReusableActions.logOut();
        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
    }
}

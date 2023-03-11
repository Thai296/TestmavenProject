package com.simplicia.web;

import com.simplicia.executor.SeleniumTestAsSimpliciaUser;
import com.simplicia.pages.web.atmp.TousMesATMPsPage;
import com.simplicia.pages.web.dashboard.DashboardPage;
import com.simplicia.pages.web.dat.DATMainPage;
import com.simplicia.pages.web.home.HomePage;
import com.simplicia.pages.web.leftnavigationmenu.LeftNavigationMenuPage;
import com.simplicia.pages.web.login.LoginPage;
import com.simplicia.pages.web.utils.SimpliciaReusableActions;
import org.testng.annotations.*;

public class BaseTest extends SeleniumTestAsSimpliciaUser {
    protected DashboardPage dashboardPage;
    protected LoginPage loginPage;
    protected HomePage homePage;
    protected SimpliciaReusableActions simpliciaReusableActions;
    protected LeftNavigationMenuPage leftNavigationMenuPage;
    protected TousMesATMPsPage tousMesATMPsPage;
    protected DATMainPage datMainPage;

    @BeforeMethod(alwaysRun = true)
    public void baseBeforeMethod() {
        println(">>>> BaseTest@BeforeMethod: " + this.getClass().getName());
        try {
            // Setup page
            dashboardPage = new DashboardPage(browser);
            loginPage = new LoginPage(browser);
            homePage = new HomePage(browser);
            simpliciaReusableActions = new SimpliciaReusableActions(browser);
            leftNavigationMenuPage = new LeftNavigationMenuPage(browser);
            tousMesATMPsPage = new TousMesATMPsPage(browser);
            datMainPage = new DATMainPage(browser);
        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
    }
}

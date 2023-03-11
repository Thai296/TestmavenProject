package com.simplicia.executor;

import com.simplicia.pages.web.login.LoginPage;
import com.simplicia.pages.web.utils.SimpliciaReusableActions;
import com.simplicia.pages.web.utils.TestData;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class SeleniumTestAsSimpliciaUser extends SeleniumTestSupport
{
	public static final org.apache.log4j.Logger LOGGER = Logger.getLogger(SeleniumTestAsSimpliciaUser.class);

	protected boolean logoutFlag = true;
	public static void println(String s){
		LOGGER.info(s);
	}
	@BeforeClass(alwaysRun = true)
	public void launchBrowserAndOpenSimpliciaUrl() {
		LOGGER.info(">>>> SeleniumTestAsSimpliciaUser@BeforeClass: " + this.getClass().getName());
		retrySilently(() -> {
			loadUrl(TestData.APPLICATION_URL);
			sleepSilently(2000);
			LoginPage loginPage = new LoginPage(browser);
			loginPage.checkPresenceOfEmailTextField();
		});
		LOGGER.info("<<<< SeleniumTestAsSimpliciaUser@BeforeClass: " + this.getClass().getName());
	}

	@AfterClass(alwaysRun = true)
	public void logOutFromSimplicia() {
		LOGGER.info(">>>> SeleniumTestAsSimpliciaUser@AfterClass: " + this.getClass().getName());
		LOGGER.info("logoutFlag=" + logoutFlag);
		if(logoutFlag) {
			retrySilently(() -> {
				LOGGER.info("Logging out");
				SimpliciaReusableActions simpliciaReusableActions = new SimpliciaReusableActions(browser);
				simpliciaReusableActions.logOut();
				sleepSilently(2000);
			});
		} else {
			LOGGER.info("Logging out DISABLED");
		}
		LOGGER.info("<<<< SeleniumTestAsSimpliciaUser@AfterClass: " + this.getClass().getName());
	}
}

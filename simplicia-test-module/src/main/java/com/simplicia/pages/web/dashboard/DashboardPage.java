package com.simplicia.pages.web.dashboard;

import com.simplicia.pages.web.SimpliciaPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.text.MessageFormat;

public class DashboardPage extends SimpliciaPage {

	//============
	// Locators
	//============
	final static String BTN_SELECT_CLIENT = "//h2[contains(text(),''{0}'')]/../..//button";
	final static By XPATH_SWITCHED_CLIENT_SUCCESSFULLY = By.xpath("//p[text()='Modification du client actif effectué avec succès']");


	//=============
	// Selenium Controls
	//=============
	private JavascriptExecutor executor;

	public DashboardPage(WebDriver browser) {
		super(browser);
		executor = (JavascriptExecutor) browser;
	}

	public void checkPresenceButton(String clientName) {
		By xpath = By.xpath(MessageFormat.format(BTN_SELECT_CLIENT, clientName));
		checkPresence(xpath);
	}

	public void switchClient(String clientName) {
		By xpath = By.xpath(MessageFormat.format(BTN_SELECT_CLIENT, clientName));
		click(xpath);
	}

	public void checkPresenceOfSuccessfulMessage() {
		checkPresence(XPATH_SWITCHED_CLIENT_SUCCESSFULLY, "Switching client successful message");
	}

}

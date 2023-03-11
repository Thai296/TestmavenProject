package driver;

import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This class instantiate Remote WebDriver, initialize & launch browsers and set the capabilities based on
 * browser instance
 */

public class Driver
{

    protected RemoteWebDriver driver;
    protected String appURL;
    protected String mainWindowHandle;

    /**
     * This method in used to initialize the webdriver, desired capabilities, type and size of browser on the
     * basis of choices (like firefox and chrome etc) to run the test case.
     *
     * @param host localhost server
     * @param browser name of browser
     * @param firefoxLocation location of firefox driver
     * @param url url of the application
     * @param maxWaitTimeToFindElement maximum time to find an element
     * @param iTestContext to store the object
     */
//	@BeforeClass
//	@Parameters({ "host", "port", "browser", "chrome.driver", "ie32.driver", "ie64.driver", "firefox.location", "url", "subStringURL",
//			"proxyHost", "proxyPort", "maxWaitTimeToFindElement" })
//	protected void initialize(@Optional("localhost") String host, @Optional("") String browser, @Optional("") String firefoxLocation,
//			@Optional("") String url, @Optional("30") Long maxWaitTimeToFindElement, ITestContext iTestContext) {
//
//		Util.maxWaitTimeToFindElement = maxWaitTimeToFindElement;
//		this.appURL = url;
//
//		/*Desired Capabilities for RemoteWebDriver instance*/
//		DesiredCapabilities capabilities = new DesiredCapabilities();
//
//		/*Firefox browser profile*/
//		FirefoxProfile firefoxProfile = new FirefoxProfile();
//
//		/*If tests are triggered on local machine using firefox/Chrome/IE drivers*/
//		if (host.equalsIgnoreCase("localhost")) {
//			if (browser.equalsIgnoreCase("firefox")) {
//				FirefoxBinary firefoxBinary = null;
//				if (!firefoxLocation.isEmpty()) {
//					capabilities.setCapability(FirefoxDriver.BINARY, firefoxLocation);
//					firefoxBinary = new FirefoxBinary(new File(firefoxLocation));
//				}
//				try {
//					if (firefoxBinary != null) {
//						driver = new FirefoxDriver(firefoxBinary, firefoxProfile);
//						LOGGER.info("Initiating Firefox driver on Host:  " + host);
//
//					} else {
//						driver = new FirefoxDriver(firefoxProfile);
//						LOGGER.info("Initiating Firefox driver on Host:  " + host);
//
//					}
//				} catch (WebDriverException exception) {
//					System.out.print("ERROR : Firefox is not installed on host machine." + exception.getMessage());
//					System.exit(1);
//				}
//			} else if (browser.equalsIgnoreCase("chrome")) {
//				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator
//						+ "selenium-framework\\src\\main\\java\\com\\mitratech\\selenium\\resources\\chromedriver_win32\\chromedriver.exe");
//				try {
//					driver = new ChromeDriver(capabilities);
//					LOGGER.info("Initiating Chrome driver on Host:  " + host);
//
//				} catch (WebDriverException exception) {
//					System.out.print("ERROR : Chrome is not installed on host machine." + exception.getMessage());
//					System.exit(1);
//				}
//			} else if (browser.equalsIgnoreCase("ie")) {
//				if (System.getProperty("os.arch").equals("x64")) {
//					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + File.separator
//							+ "src\\main\\java\\com\\mitratech\\selenium\\resources\\IEdriverServer_Win64\\IEDriverServer.exe");
//				} else if (System.getProperty("os.arch").equals("x86")) {
//					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + File.separator
//							+ "src\\main\\java\\com\\mitratech\\selenium\\resources\\IEdriverServer_Win32\\IEDriverServer.exe");
//				}
//				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
//				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//				ieCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
//				driver = new InternetExplorerDriver(ieCapabilities);
//				LOGGER.info("Initiating Internet Explorer driver on Host:  " + host);
//
//			}
//		}
//		/*If tests are triggered on remote machine using firefox/Chrome/IE drivers*/
//		else {
//			if (browser.equalsIgnoreCase("firefox")) {
//				capabilities.setBrowserName("firefox");
//				if (!firefoxLocation.isEmpty() || !firefoxLocation.equals("")) {
//					capabilities.setCapability(FirefoxDriver.BINARY, firefoxLocation);
//				}
//				capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
//			} else if (browser.equalsIgnoreCase("chrome")) {
//				capabilities.setBrowserName("chrome");
//			} else if (browser.equalsIgnoreCase("ie")) {
//				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//				capabilities.setBrowserName("internet explorer");
//			}
//		}
//		capabilities.setCapability(ForSeleniumServer.PROXYING_EVERYTHING, true);
//		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//		capabilities.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
//		capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
//		capabilities.setCapability(ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
//
//		Dimension windowDimensions = new Dimension(1000, 650);
//
//		// Instantiate the driver
//
//		driver.manage().timeouts().implicitlyWait(maxWaitTimeToFindElement, TimeUnit.SECONDS);
//		driver.manage().window().maximize();
//		driver.manage().deleteAllCookies();
//		driver.manage().window().setSize(windowDimensions);
//
//		mainWindowHandle = driver.getWindowHandle();
//		LOGGER.info("Launched new Window with handle:  " + mainWindowHandle);
//
//		iTestContext.setAttribute("driver", driver);
//		driver.get(appURL);
//		PageSetup();
//	}

    public void PageSetup() {
    }

    /**
     * Method to wait for execution
     *
     * @param timeInMicroSeconds time unit in micro seconds
     */
    public void WaitForExecution(Long timeInMicroSeconds) {
        try {
            Thread.sleep(timeInMicroSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }
    }

    /**
     * Method to close all browsers(driver) instance
     */
//	@AfterClass(alwaysRun = true)
//	protected void drop() {
//		driver.quit();
//	}
}



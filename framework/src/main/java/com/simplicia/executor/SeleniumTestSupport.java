package com.simplicia.executor;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.simplicia.functions.Utility;

import controls.Alert;
import controls.ControlledElement;
import exception.MSeleniumException;
import trace.ExecutionTracer;
import trace.TraceReporter;
import util.CommonUtil;

/**
 * Provides Webdriver, browsers initialization and TestNG support to test classes skip to add
 */
public class SeleniumTestSupport {
    public static final Logger LOGGER = Logger.getLogger(SeleniumTestSupport.class);
    private static final String OS = System.getProperty("os.name").toLowerCase();
    public static final List<String> urlcount = new ArrayList<String>();
    public final static ThreadLocal<WebDriver> singleSharedBrowser = new ThreadLocal<WebDriver>();
    private static final AtomicInteger numberOfClassesExecuted = new AtomicInteger();
    private static final long startTime = System.currentTimeMillis();
    private static final List<Long> runningThreadIDList = new ArrayList<Long>();
    private static final Map<Long, WebDriver> runningBrowsers = new HashMap<Long, WebDriver>();
    private static final String CHROMEDRIVER_PATH = System.getenv("CHROMEDRIVER_PATH")!=null?System.getenv("CHROMEDRIVER_PATH"):"chromedriver";
    public static Integer numberOfTests;
    protected static Properties singleSharedProperties;
    protected static boolean isMultiTenant;
    protected static String domain;
    protected static int PAGE_LOAD_TIME_IN_SECONDS;
    protected static int ROW_LOAD_TIME_IN_SECONDS;
    protected static int TIME_OUT_IN_SECONDS;
    protected static int TRACE_SIZE;
    protected static String TIME_ZONE;
    protected static ThreadLocal<String> className = new ThreadLocal<String>();
    protected static String clientIpAddress;
    static boolean USE_BROWSER_SCREENSHOT;
    private static boolean isDriverUsed = false;
    private static List<String> uniqueValues = new ArrayList<String>();
    protected WebDriver browser;
    protected List<String> screenshots = new ArrayList<String>();
    private int testStep = 1;
    private long testLastStepCompletion = System.currentTimeMillis();
    public static String downloadFilePath;

    public static String takeScreenshot(WebDriver webDriver) {
        CommonUtil.printConsoleLog(webDriver);
        if (USE_BROWSER_SCREENSHOT) {
            return takeBrowserScreenshot(webDriver);
        } else {
            return takeAllScreenshot();
        }
    }

    public static String takeBrowserScreenshot(WebDriver webDriver) {
        if (webDriver instanceof TakesScreenshot) {
            File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            new File("./target/images").mkdirs();
            try {
                CommonUtil.copyScreenshot(screenshot);
                FileUtils.copyFileToDirectory(screenshot, new File("./target/images/"));
                return screenshot.getName();
            } catch (IOException exd) {
                exd.printStackTrace();
            }
        }
        return "";
    }

    public static String takeAllScreenshot() {
        try {
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            new File("./target/images").mkdirs();
            File screenshot = new File("./target/images/screenshot" + System.currentTimeMillis() + ".png");
            ImageIO.write(image, "png", screenshot);
            return screenshot.getName();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return "";
    }

    /**
     * @param webDriver browser being used
     * @return screenshot name
     */
    public static String addScreenshot(WebDriver webDriver) {
        if (webDriver instanceof TakesScreenshot) {
            File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            new File("./target/images").mkdirs();
            try {
                CommonUtil.copyScreenshot(screenshot);
                FileUtils.copyFileToDirectory(screenshot, new File("./target/images/"));

                LOGGER.info(CommonUtil.logPrefix() + "screenshot taken " + screenshot.getName());

                return screenshot.getName();
            } catch (IOException exd) {
                exd.printStackTrace();
            }
        }
        return "";
    }

    @BeforeSuite(alwaysRun = true)
    public void startSeleniumClient() {
        singleSharedProperties = new Properties(System.getProperties());
        InputStream seleniumPropertyFile = this.getClass().getClassLoader().getResourceAsStream("selenium.properties");
        if (seleniumPropertyFile != null) {
            try {
                singleSharedProperties.load(seleniumPropertyFile);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String isMultiTenantValue = singleSharedProperties.getProperty("webapp.isMultiTenant", "NO");
        if (isMultiTenantValue != null && isMultiTenantValue.equals("YES")) {
            isMultiTenant = true;
        }

        String pageLoadTimeInSeconds = singleSharedProperties.getProperty("selenium.pageloadtime", "2");
        PAGE_LOAD_TIME_IN_SECONDS = Integer.parseInt(pageLoadTimeInSeconds);

        String rowTimeInSeconds = singleSharedProperties.getProperty("selenium.rowloadtime", "3");
        ROW_LOAD_TIME_IN_SECONDS = Integer.parseInt(rowTimeInSeconds);

        String timeOutInSeconds = singleSharedProperties.getProperty("selenium.timeout", "30");
        TIME_OUT_IN_SECONDS = Integer.parseInt(timeOutInSeconds);

        String useBrowserScreenshot = singleSharedProperties.getProperty("selenium.useBrowserScreenshot", "true");
        LOGGER.info(CommonUtil.logPrefix() + "property selenium.useBrowserScreenshot is " + useBrowserScreenshot);
        USE_BROWSER_SCREENSHOT = Boolean.parseBoolean(useBrowserScreenshot);

        String traceSize = singleSharedProperties.getProperty("selenium.traceSize", "20");
        LOGGER.info(CommonUtil.logPrefix() + "property selenium.traceSize is " + traceSize);
        TRACE_SIZE = Integer.parseInt(traceSize);
        ExecutionTracer.traceSize = TRACE_SIZE;

        String numberOfTestRetry = singleSharedProperties.getProperty("selenium.numberOfTestRetry", "2");
        TestCaseRunner.NUMBER_OF_TEST_RETRY = Integer.parseInt(numberOfTestRetry);

        LOGGER.info(CommonUtil.logPrefix() + "WEBAPP URL : " + singleSharedProperties.getProperty("webapp.url"));
        if (singleSharedProperties.getProperty("webapp.url.2") != null) {
            LOGGER.info(CommonUtil.logPrefix() + "WEBAPP URL 2: " + singleSharedProperties.getProperty("webapp.url.2"));
        }
        if (singleSharedProperties.getProperty("webapp.url.3") != null) {
            LOGGER.info(CommonUtil.logPrefix() + "WEBAPP URL 3: " + singleSharedProperties.getProperty("webapp.url.3"));
        }
        if (singleSharedProperties.getProperty("webapp.url.4") != null) {
            LOGGER.info(CommonUtil.logPrefix() + "WEBAPP URL 4: " + singleSharedProperties.getProperty("webapp.url.4"));
        }

        LOGGER.info(CommonUtil.logPrefix() + "PAGE_LOAD_TIME_IN_SECONDS : " + PAGE_LOAD_TIME_IN_SECONDS + " secs");
        LOGGER.info(CommonUtil.logPrefix() + "ROW_LOAD_TIME_IN_SECONDS : " + ROW_LOAD_TIME_IN_SECONDS + " secs");
        LOGGER.info(CommonUtil.logPrefix() + "TIME_OUT_IN_SECONDS : " + TIME_OUT_IN_SECONDS + " secs");
        LOGGER.info(CommonUtil.logPrefix() + "USE_BROWSER_SCREENSHOT : " + USE_BROWSER_SCREENSHOT);
        LOGGER.info(CommonUtil.logPrefix() + "TRACE_SIZE : " + TRACE_SIZE);

        // Delete allure reports before starting the tests
        LOGGER.info(CommonUtil.logPrefix() + " Delete existing allure reports to create fresh reports");
        Utility.deleteFile(new File("./allure-results"));
    }

    @AfterSuite(alwaysRun = true)
    public void stopSeleniumClient() {
        TraceReporter.printStatistics(startTime, System.currentTimeMillis());
        TraceReporter.generateDefectReports();

        List<WebDriver> browsersToKill = new ArrayList<WebDriver>();
        browsersToKill.addAll(runningBrowsers.values());
        if (!isDriverUsed && !browsersToKill.isEmpty()) {
            LOGGER.info(
                    CommonUtil.logPrefix() + "closing all browsers (but one because main thread will kill last one):");
            browsersToKill.remove(0);
        }
        LOGGER.info(CommonUtil.logPrefix() + browsersToKill.size() + " will be manually closed");

        try {
            for (WebDriver browserToKill : browsersToKill) {
                if (isDriverUsed) {
                    LOGGER.info(CommonUtil.logPrefix() + "closing browser " + browserToKill);
                    browserToKill.close();
                }
                LOGGER.info(CommonUtil.logPrefix() + "quiting browser " + browserToKill);
                browserToKill.quit();
            }
        } catch (Throwable exc) {
            exc.printStackTrace();
        }

    }

    @BeforeClass(alwaysRun = true)
    @Parameters("browserValue")
    public void captureExecution(@Optional("") String browserValue) {
        className.set(this.getClass().getSimpleName());
        ExecutionTracer.className.set(className.get());
        numberOfClassesExecuted.incrementAndGet();
        if (numberOfTests != null) {
            LOGGER.info(CommonUtil.logPrefix() + "Executing " + numberOfClassesExecuted.get() + "/" + numberOfTests
                    + " : " + className.get());
        } else {
            LOGGER.info(CommonUtil.logPrefix() + "Executing " + (numberOfClassesExecuted.get()) + " : " + className.get());
        }
        LOGGER.info(CommonUtil.logPrefix() + "singleSharedBrowser.get() = " + singleSharedBrowser.get());
        LOGGER.info(CommonUtil.logPrefix() + "browserValue = " + browserValue);
        if (singleSharedBrowser.get() == null) {
//            String browserStartCommand = singleSharedProperties.getProperty("selenium.browserStartCommand");
        	String browserStartCommand = "chrome";
            if (browserValue.equalsIgnoreCase("firefox") || browserStartCommand.contains("firefox")) {
                String driverPath = singleSharedProperties.getProperty("selenium.driver.path", "geckodriver/geckodriver.run");
                String firefoxDriver = getDriverPath(driverPath);
                System.setProperty("webdriver.gecko.driver", firefoxDriver);
                singleSharedBrowser.set(new FirefoxDriver());
                LOGGER.info(CommonUtil.logPrefix() + "browser time out will be " + TIME_OUT_IN_SECONDS + " seconds");
                singleSharedBrowser.get().manage().timeouts().pageLoadTimeout(TIME_OUT_IN_SECONDS, TimeUnit.SECONDS);
                singleSharedBrowser.get().manage().timeouts().implicitlyWait(PAGE_LOAD_TIME_IN_SECONDS, TimeUnit.SECONDS);
                //singleSharedBrowser.get().manage().window().setSize(new Dimension(1900, 1080));
                runningBrowsers.put(Thread.currentThread().getId(), singleSharedBrowser.get());
                isDriverUsed = true;
            }
            else if (browserValue.equalsIgnoreCase("chrome") || browserStartCommand.contains("chrome")) {
                int count=0;
                LOGGER.info("here "+count++);//0
                LOGGER.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  set driver " + System.getProperty("os.name") + " set driver >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                String chromeDriver = "";
                LOGGER.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  " + System.getProperty("os.name") + "  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
               // chromeDriver = System.getProperty("user.dir") + "/" + CHROMEDRIVER_PATH;
                String driverPath = singleSharedProperties.getProperty("selenium.driver.path", getChromeDriverPath());
                chromeDriver = getDriverPath(driverPath);
                if (isUnix() || isMac()) {
                    LOGGER.info("here "+count++);//1
                    boolean f = new File(chromeDriver).setExecutable(true);
                }
                LOGGER.info("<<<<<<<<<<<<<<<<<<"+ chromeDriver);
                downloadFilePath = getResourcePath("");
                LOGGER.info("<<<<<<<<<<<<<<<<<< download path: "+ downloadFilePath);
                System.setProperty("webdriver.chrome.driver", chromeDriver);
                System.setProperty("webdriver.chrome.whitelistedIps", "");
                LOGGER.info("here "+count++);//2
                // LOGGER.info("here "+count++);//3
                // chromePrefs.put("profile.default_content_settings.popups", 0);
                // LOGGER.info("here "+count++);//4
                // chromePrefs.put("safebrowsing.enabled", "true");
                // LOGGER.info("here "+count++);//5
               

                
                // LOGGER.info("here "+count++);//6
                
                // //options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                // LOGGER.info("here "+count++);//7
                // options.addArguments("--no-sandbox");
                // LOGGER.info("here "+count++);//8
                // options.addArguments("--disable-dev-shm-usage");
                // LOGGER.info("here "+count++);//9
                // options.addArguments("--verbose");
                // if("TRUE".equalsIgnoreCase(System.getenv("CHROME_HEADLESS"))){
                     //options.addArguments("--headless");
                // }
                // DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                // LOGGER.info("here "+count++);//10
                // capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                // LOGGER.info("here "+count++);//11
                // WebDriver d=new ChromeDriver(options);
                // d.manage().window().setSize(new Dimension(1920, 1440));
                // LOGGER.info("here "+count++);
                // singleSharedBrowser.set(d);
                // LOGGER.info("here "+count++);
                // isDriverUsed = true;
                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("download.default_directory", downloadFilePath);
                ChromeOptions options = new ChromeOptions();
//                options.setBinary("C:\\Users\\Candy\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
//                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1440");
                options.setExperimentalOption("prefs", chromePrefs);
        		options.addArguments("--enable-javascript");
                options.setExperimentalOption("prefs", chromePrefs);
                WebDriver d = new ChromeDriver(options); // could not run successfully
                // d.manage().window().setSize(new Dimension(1920, 1440));
                d.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                d.manage().window().maximize();
                singleSharedBrowser.set(d);
                runningBrowsers.put(Thread.currentThread().getId(), singleSharedBrowser.get());
                isDriverUsed = true;

                /// br/wser=new ChromeDriver(); // could not run successfully
                // browser.manage().window().setSize(new Dimension(1920, 1440));
                // isDriverUsed = true;
            } else if (browserStartCommand.contains("headlessChrome")) {
                String driverPath = singleSharedProperties.getProperty("selenium.driver.path", getChromeDriverPath());
                String chromeDriver = getDriverPath(driverPath);
                System.setProperty("webdriver.chrome.driver", chromeDriver);
                ChromeOptions chromeOptions = new ChromeOptions();
                //chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                isDriverUsed = true;
            } else if (browserStartCommand.contains("safari") || browserStartCommand.contains("safari")) {
                singleSharedBrowser.set(new SafariDriver());
                isDriverUsed = true;
            }
            // else if (browserValue.equalsIgnoreCase("edge") || browserStartCommand.contains("edge")) {
            //     String driverPath = singleSharedProperties.getProperty("selenium.driver.path", "edgedriver\\msedgedriver.run");
            //     String edgeDriver = getDriverPath(driverPath);
            //     System.setProperty("webdriver.edge.driver", edgeDriver);
            //     DesiredCapabilities capabilities = DesiredCapabilities.edge();
            //     capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            //     singleSharedBrowser.set(new EdgeDriver(capabilities));
            //     isDriverUsed = true;
            // }
            // LOGGER.info(CommonUtil.logPrefix() + "browser time out will be " + TIME_OUT_IN_SECONDS + " seconds");
            // singleSharedBrowser.get().manage().timeouts().pageLoadTimeout(TIME_OUT_IN_SECONDS, TimeUnit.SECONDS);
            // singleSharedBrowser.get().manage().timeouts().implicitlyWait(PAGE_LOAD_TIME_IN_SECONDS, TimeUnit.SECONDS);
            // singleSharedBrowser.get().manage().window().maximize();
            // singleSharedBrowser.get().manage().deleteAllCookies();

            // if (isDriverUsed) {
            //     ControlledObject.TIME_OUT_IN_SECONDS = TIME_OUT_IN_SECONDS;
            // }
            // runningBrowsers.put(Thread.currentThread().getId(), singleSharedBrowser.get());

        }

        browser = singleSharedBrowser.get();

        LOGGER.info("Current windows are " + browser.getWindowHandles());

        synchronized (runningThreadIDList) {
            if (!runningThreadIDList.contains(Thread.currentThread().getId())) {
                LOGGER.info(CommonUtil.logPrefix() + "Adding " + Thread.currentThread().getId() + " in list of running threads");
                runningThreadIDList.add(Thread.currentThread().getId());
            }
        }

    }
    public static void main(String[] args){
        LOGGER.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  " + System.getProperty("os.name") + "  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        String chromedriverPath = System.getProperty("user.dir") + "/" + CHROMEDRIVER_PATH;
        if (isUnix() || isMac()) {
            boolean f = new File(chromedriverPath).setExecutable(true);
        }
        ChromeOptions options = new ChromeOptions();
        if("TRUE".equalsIgnoreCase(System.getenv("CHROME_HEADLESS"))){
            options.addArguments("--headless");
        }
        System.setProperty("webdriver.chrome.driver",
                System.getProperty("user.dir") + "/" + CHROMEDRIVER_PATH);
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups", 0);

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.setExperimentalOption("prefs", prefs);
        if (System.getenv("SMPL_IS_CONTAINER") != null) {
            options.addArguments("--user-agent=" + System.getenv("SMPL_USER_AGENT"));
        }
        WebDriver d=new ChromeDriver(options);
        d.manage().window().setSize(new Dimension(1920, 1440));
    }
    @AfterClass(alwaysRun = true)
    public void computeRemainingTime() {
        long currentTime = System.currentTimeMillis();
        if (numberOfTests != null) {
            double remainingTime = ((currentTime - startTime) / numberOfClassesExecuted.get())
                    * (numberOfTests - numberOfClassesExecuted.get()) / (1000 * 60);
            LOGGER.info(CommonUtil.logPrefix() + "Remaining time: " + remainingTime + " mins");
            Calendar end = Calendar.getInstance();
            end.add(Calendar.MINUTE, (int)remainingTime);
            LOGGER.info(CommonUtil.logPrefix() + "Expected end time: " + end.getTime());
        }
    }

    @AfterMethod(alwaysRun = true)
    public void finishExecution() {
        if (browser != null) {
            closeAdditionalWindow();
            switchToInitialWindow();
        }
        ExecutionTracer.getTestTracer().stopRecordingActions();
        ExecutionTracer.getTestTracer().setEndTime(System.currentTimeMillis());
    }

    /**
     * Assert that the specified text pattern appears somewhere on the rendered page shown to the user. Throws
     * an AssertionError if it is not present. Does nothing if the text is present.
     *
     * @param pattern - a pattern to match with the text of the page
     * @throws AssertionError if pattern is not present
     */
    public void assertTextPresent(String pattern) throws AssertionError {
        if (!isTextPresent(pattern)) {
            throw new AssertionError("Text '" + pattern + "' not found on page");
        }
    }

    /**
     * Assert that the specified text pattern does not appear anywhere on the rendered page shown to the user.
     * Throws an AssertionError if it is present. Does nothing if the text is not present.
     *
     * @param pattern - a pattern to match with the text of the page
     * @throws AssertionError if pattern is present
     */
    public void assertTextNotPresent(String pattern) {
        if (isTextPresent(pattern)) {
            throw new MSeleniumException("Text '" + pattern + "' found on page", browser);
        }
    }

    /**
     * Method to verify Text present
     *
     * @param pattern text to be verified
     * @return true/false
     */
    public boolean isTextPresent(String pattern) {
        try {
            return browser.getPageSource().contains(pattern);
        } catch (WebDriverException exc) {
            LOGGER.info(CommonUtil.logPrefix() + "WARN - unable to get page source");
            exc.printStackTrace();
            return false;
        }
    }

    /**
     * Method to get file path
     *
     * @param str_FileName filename
     * @return file
     */
    public String getFilePath(String str_FileName) throws AssertionError {
        String url = this.getClass().getResource("/testFiles/" + str_FileName).getFile();
        String folderUrl = url.substring(0, url.lastIndexOf('/'));
        folderUrl = folderUrl.replace('/', File.separatorChar);
        if (folderUrl.charAt(2) == ':') {
            return folderUrl.substring(1) + File.separatorChar + str_FileName;
        } else {
            return folderUrl + File.separatorChar + str_FileName;
        }
    }

    /**
     * Method to get file path for DMS test files.
     *
     * @param str_Path     path of test files under 'testFiles'
     * @param str_FileName file name
     * @return file location
     */
    public String getFilePath(String str_Path, String str_FileName) throws AssertionError {
        String url = this.getClass().getResource("/testFiles/" + str_Path + "/" + str_FileName).getFile();
        String folderUrl = url.substring(0, url.lastIndexOf('/'));
        folderUrl = folderUrl.replace('/', File.separatorChar);
        if (folderUrl.charAt(2) == ':') {
            return folderUrl.substring(1) + File.separatorChar + str_FileName;
        } else {
            return folderUrl + File.separatorChar + str_FileName;
        }
    }

    public String getDriverPath(String driverPath) throws AssertionError {
        String currentDirectory = System.getProperty("user.dir");
        Path path = Paths.get(currentDirectory).getParent().toAbsolutePath();
        String pathOfClassesFolder = path + "\\framework\\target\\classes\\";
        LOGGER.info("Wed driver link "+ FilenameUtils.separatorsToSystem(pathOfClassesFolder + driverPath));
//        return pathOfClassesFolder + driverPath;
        return FilenameUtils.separatorsToSystem(pathOfClassesFolder + driverPath);
    }

    public static void listFilesInDir(String dir) {
        Set<String> names = Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
        LOGGER.info("Files in dir: " + dir);
        LOGGER.info("names.length" + names.size());
        for (String name : names) {
            LOGGER.info("- " + name);
        }
    }

    public String getResourcePath(String resourcePath) throws AssertionError {
    	URL resourceUrl = this.getClass().getResource(FilenameUtils.separatorsToSystem("/" + resourcePath));
//        URL resourceUrl = this.getClass().getResource(FilenameUtils.separatorsToSystem(resourcePath)); // Keep this for execution in windows
        if (resourceUrl == null) {
            throw new AssertionError(resourceUrl + " not found");
        }
        String path = resourceUrl.getPath();

        if (path.charAt(2) == ':') {
            path = path.replaceAll("/", "\\\\");
            path = FilenameUtils.separatorsToSystem(path);
            LOGGER.info("path.substring: " + path.substring(1));
            return path.substring(1);
        } else {
        	LOGGER.info("path: " + path);
            return path;
        }

    }

    protected void measureStep() {
        LOGGER.info(CommonUtil.logPrefix() + "step " + testStep + ", duration: " + (System.currentTimeMillis() - testLastStepCompletion) + " ms");
        testStep = testStep + 1;
        testLastStepCompletion = System.currentTimeMillis();
    }

    /**
     * Method to get URL
     *
     * @return url
     */
    protected String getWebAppURL() {
        int index = runningThreadIDList.indexOf(Thread.currentThread().getId()) + 1;
        if (index == 1) {
            return singleSharedProperties.getProperty("webapp.url");
        } else {
            String url = singleSharedProperties.getProperty("webapp.url." + index);
            if (url == null) {
                throw new RuntimeException("url for thread " + Thread.currentThread().getId() + " not specified");
            } else {
                return url;
            }
        }
    }

    protected String getShortUSFormat(Calendar date) {
        Integer month = date.get(Calendar.MONTH);
        Integer day = date.get(Calendar.DATE);
        Integer year = date.get(Calendar.YEAR);
        month = month + 1;
        return month + "/" + day + "/" + year;
    }

    /**
     * Method to generate random number
     *
     * @return random number
     */
    protected synchronized String generateUnique() {
        String value = "" + System.currentTimeMillis();
        if (uniqueValues.contains(value)) {
            LOGGER.info(CommonUtil.logPrefix() + value + " is already taken, generating another one");
            return generateUnique();
        } else {
            uniqueValues.add(value);
            LOGGER.info(CommonUtil.logPrefix() + "generated unique is " + value);
            return value;
        }
    }

    /**
     * Method to generate random number
     *
     * @return random number
     */
    protected synchronized String generateShortUnique() {
        String value = "" + System.currentTimeMillis();
        value = value.substring(value.length() - 8, value.length());
        if (uniqueValues.contains(value)) {
            LOGGER.info(CommonUtil.logPrefix() + value + " is already taken, generating another one");
            return generateUnique();
        } else {
            uniqueValues.add(value);
            LOGGER.info(CommonUtil.logPrefix() + "generated unique is " + value);
            return value;
        }
    }

    /**
     * Generates random number with length 1 to 12 digit
     *
     * @param length length of random number
     * @return random number
     */
    protected synchronized String generateUnique(int length) {

        if (length <= 0 || length > 12) {
            throw new IllegalArgumentException("Length must be between 1 and 12");
        }

        String value = "" + System.currentTimeMillis();
        Random random = new Random();

        value = value.substring((value.length() - length)).replaceFirst("0", String.valueOf(random.nextInt(8) + 1));
        if (uniqueValues.contains(value)) {
            LOGGER.info(CommonUtil.logPrefix() + value + " is already taken, generating another one");
            return generateUnique(length);
        } else {
            uniqueValues.add(value);
            LOGGER.info(CommonUtil.logPrefix() + "generated unique is " + value);
            return value;
        }
    }

    /**
     * Generates random alphanumeric string with customized length
     *
     * @param length length of the string to be generated
     * @return alphanumeric string
     */
    protected synchronized String generateAlphaNumericString(int length) {
        String value = "ABCDEFGHIJKLMNOPQRSTUVWXYZ@!#$%&*" + System.currentTimeMillis();
        SecureRandom secureRandom = new SecureRandom();

        StringBuilder generatedString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randonSequence = secureRandom.nextInt(value.length());
            generatedString.append(value.charAt(randonSequence));
        }
        return generatedString.toString();
    }

    /**
     * Check if given values are the same
     *
     * @param expected expected values
     * @param value    computed value
     */
    public void assertEquals(Object expected, Object value) {
        if (!expected.equals(value)) {
            throw new MSeleniumException("Value '" + value + "' is not equal to " + expected, browser);
        }
    }

    /**
     * Check if given values are the same
     *
     * @param expected expected values
     * @param value    computed value
     */
    public void assertEquals(String expected, String value) {
        if (!expected.equals(value)) {
            throw new MSeleniumException("Value '" + value + "' is not equal to " + expected, browser);
        }
    }

    /**
     * Check if given values are not same
     *
     * @param expected expected values
     * @param value    computed value
     */
    public void assertNotEquals(String expected, String value) {
        if (expected.equals(value)) {
            throw new MSeleniumException("Text '" + value + "' is equal to " + expected, browser);
        }
    }

    /**
     * check if a condition is verified
     *
     * @param conditionDescription description of condition being checked
     * @param conditionResult      condition value
     */
    // public void assertTrue(String conditionDescription, boolean conditionResult) {
    //     if (!conditionResult) {
    //         throw new MSeleniumException(conditionDescription + " is not verified", browser);
    //     }
    // }

    /**
     * check if a condition is verified
     *
     * @param conditionDescription description of condition being checked
     * @param conditionResult      condition value
     */
    public void assertFalse(String conditionDescription, boolean conditionResult) {
        if (conditionResult) {
            throw new MSeleniumException(conditionDescription + " is not verified", browser);
        }
    }

    /**
     * Method to get url of the current window
     *
     * @return the url of the current window
     */
    public String getCurrentUrl() {
        try {
            return browser.getCurrentUrl();
        } catch (WebDriverException exc) {
            throw new MSeleniumException("Unable to get current window", exc, browser);
        }
    }

    /**
     * Switch to initial window. Typically the first window open to start the test
     */
    public void switchToInitialWindow() {
        switchToWindow(0);
    }

    /**
     * Switch to a specific window based on its index. Initial window having 0 as index
     *
     * @param windowIndex index of window to switch to
     */
    public void switchToWindow(int windowIndex) {
        try {
            LOGGER.info("Windows are " + StringUtils.join(browser.getWindowHandles().toArray(), ", "));
            LOGGER.info("Switching to window number " + windowIndex + " which is " + browser.getWindowHandles().toArray()[windowIndex]);
            browser.switchTo().window((String) browser.getWindowHandles().toArray()[windowIndex]);
        } catch (WebDriverException exc) {
            throw new MSeleniumException("Unable to switch to default content", exc, browser);
        }
    }

    /**
     * Switch to the last opened window
     */
    public void switchToLastOpenedWindow() {
        try {
            int lastOpenedWindowIndex = browser.getWindowHandles().size() - 1;
            switchToWindow(lastOpenedWindowIndex);
        } catch (WebDriverException exc) {
            throw new MSeleniumException("Unable to switch to default content", exc, browser);
        }
    }

    /**
     * Close the Window
     */
    public void closeWindow() {
        try {

            // Accept alert
            if (isAlertPresent()) {
                acceptAlert();
            }

            browser.close();
        } catch (WebDriverException exc) {
            throw new MSeleniumException("Unable to close current window", exc, browser);
        }
    }

    /**
     * Clicking OK on alert to continue
     */
    public void acceptAlert() {
        new Alert(browser).shouldBeDisplayed();
        try {
            System.out.print("Accepting alert :" + browser.switchTo().alert().getText());
            browser.switchTo().alert().accept();
        } catch (WebDriverException exc) {
            throw new MSeleniumException("Unable to accept alert", exc, browser);
        }
    }

    /**
     * Method to check presence if alert present.
     *
     * @return Return true if alert is present.
     */
    public boolean isAlertPresent() {
        try {
            browser.switchTo().alert();
            return true;
        } catch (NoAlertPresentException exc) {
            return false;
        }
    }

    /**
     * Get Browser Type
     *
     * @return browser
     */
    public String getBrowserType() {
        String browser = singleSharedProperties.getProperty("selenium.browserStartCommand");
        return browser;
    }

    /**
     * Send keys and accept alert
     *
     * @param data
     */
    public void setAlertTextAndAccept(String data) {
        try {
            browser.switchTo().alert().sendKeys(data);
            browser.switchTo().alert().accept();
        } catch (WebDriverException exc) {
            throw new MSeleniumException("Unable to Enter data or accept alert", exc, browser);
        }
    }

    /**
     * Get the text of Alert dialog box
     *
     * @return the text of Alert box
     */
    public String getAlertText() {
        try {
            Alert alert = new Alert(browser);
            return alert.getText();
        } catch (WebDriverException exc) {
            throw new MSeleniumException("Unable to get alert text", exc, browser);
        }
    }

    /**
     * Clicking on Cancel to Dismiss alert
     */
    public void dismissAlert() {
        try {
            browser.switchTo().alert().dismiss();
        } catch (WebDriverException exc) {
            throw new MSeleniumException("Unable to dismiss alert", exc, browser);
        }
    }

    /**
     * Method to open new tab in browser.
     */
    public void openNewTab(WebDriver browser) {
        browser.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
    }

    /**
     * Method to open new tab in browser.
     *
     * @param browser driver object
     */
    public void openNewTabUsingJavascript(WebDriver browser) {
        ((JavascriptExecutor) browser).executeScript("window.open()");
    }

    /**
     * Method to close current tab
     *
     * @param browser
     */
    public void closeCurrentTab(WebDriver browser) {
        browser.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "w");
    }

    /**
     * Open New Url
     *
     * @param url
     */
    public void openUrl(String url) {
        browser.get(url);
    }

    /**
     * Enter Keyboard shortcut
     *
     * @param keys
     */
    private void type(String keys) {
        browser.findElement(By.cssSelector("body")).sendKeys(keys);
    }

    public void type(Keys key) {
        type("" + key);
    }

    public void type(Keys key, char letter) {
        type(key + "" + letter);
    }

//    public void typeChord(Keys key1, Keys key2) {
//        type(Keys.chord(key1, key2));
//    }


    public String getSubject(MimeMessage message) {
        try {
            if (message != null) {
                return message.getSubject();
            } else {
                return null;
            }
        } catch (MessagingException exc) {
            return null;
        }
    }

    public String getContent(MimeMessage message) {
        try {
            if (message != null) {
                return "" + message.getContent();
            } else {
                return null;
            }
        } catch (IOException exc) {
            return null;
        } catch (MessagingException exc) {
            return null;
        }
    }

    /**
     * Close Unexpected pop up while logging out and close another window
     */
    public void closeAdditionalWindow() {
        int windowCount = browser.getWindowHandles().size() - 1;
        if (windowCount >= 0) {
            if (isAlertPresent()) {
                acceptAlert();
            }

            while (windowCount > 0) {
                switchToWindow(windowCount--);
                LOGGER.info("Closing  window number " + windowCount + 1);
                browser.close();
            }
        }
    }

    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }
    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0
                || OS.indexOf("nux") >= 0
                || OS.indexOf("aix") > 0);
    }

    private String getChromeDriverPath() {
        String folder = "chromedriver" + File.separator;
        if (isMac()) {
            return folder + "macos" + File.separator + "chromedriver";
        }
        if (isUnix()) {
            return folder + "chromedriver";
        }
        return folder + "chromedriver.exe";

    }

    protected static void sleep(long millis) throws InterruptedException {
        float SLEEP_DELAY = 1.0f;
        Float time = (millis * SLEEP_DELAY);
        long t = time.longValue();
        LOGGER.info("sleeping for: " + t + "ms");
        Thread.sleep(t);
    }

    protected static void sleepSilently(long millis) {
        try {
            sleep(millis);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    protected static String selectAllAndDeleteKeys() {
        if (isMac()) {
            return Keys.chord(Keys.COMMAND, "a", Keys.DELETE);
        }
        return Keys.chord(Keys.CONTROL, "a", Keys.DELETE);
    }

    protected void retrySilently(Retriable r) {
        retry(r, true);
    }

    protected void retry(Retriable r) {
        retry(r, false);
    }

    public void waitForPageLoadCompleted() {
        ExpectedCondition<Boolean> javascriptDone = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                try {
                    return ((JavascriptExecutor)browser).executeScript("return document.readyState").equals("complete");
                } catch (Exception e) {
                    return Boolean.FALSE;
                }
            }
        };
        WebDriverWait wait = new WebDriverWait(browser, TIME_OUT_IN_SECONDS * 2);
        wait.until(javascriptDone);
    }

    protected void tryToClick(ControlledElement ele) {
        try {
            retry(() -> ele.click());
        } catch (Exception e) {
            LOGGER.info("Element " + ele.by + " is not clickable, trying native click...");
            e.printStackTrace();
            retry(() -> ele.nativeClick());
        }
    }

    private void retry(Retriable r, boolean silent) {
        int retryCount = 0;
        while (true) {
            try {
                retryCount++;
                LOGGER.info("[Retriable] retry " + retryCount);
                r.retry();
                break;
            } catch (Exception e) {
                if (retryCount >= r.getTimes()) {
                    if (silent) {
                        e.printStackTrace();
                        break;
                    } else {
                        throw new MSeleniumException(CommonUtil.logPrefix() + e.getMessage(), e, browser);
                    }
                }
            }

            long ms = 1000;
            LOGGER.info("[Retriable] Retrying in " + ms + " ms");
            sleepSilently(ms);
        }
    }

    protected interface Retriable {
        void retry() throws Exception;
        default int getTimes() {
            return 3;
        }
    }

    protected void loadUrl(String url) {
        LOGGER.info("Loading url: " + url);
        sleepSilently(1000);
        LOGGER.info("URL is: " + url);
        retry(() -> browser.get(url!=null?url:"https://qa2.simplicia.co:7443/login"));

        disableSupportTechnique();
    }

    public void disableSupportTechnique() {

        try {
            LOGGER.info("############################### Disabling the support technique PopUP");
            sleep(5000);
            retrySilently(() -> ((JavascriptExecutor) browser).executeScript("document.getElementById(\"beacon-container\").style.display=\"none\";"));
            sleep(1000);
        } catch (Exception e) {
            // ignore
        }
    }
}

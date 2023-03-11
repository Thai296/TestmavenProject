package com.simplicia.report.listeners;

import java.util.Set;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
//import org.testng.*;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.simplicia.executor.SeleniumTestSupport;
import com.simplicia.functions.retrytest.RetryTest;

import io.qameta.allure.Attachment;
import trace.ExecutionTracer;
import util.CommonUtil;


public class TestAllureListener implements ITestListener
{
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(TestAllureListener.class);

    //private List<String> testsToBeRemoved = new ArrayList<>();

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    // Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    // HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    // Text attachments for Allure
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        LOGGER.info(CommonUtil.logPrefix() + "I am in onStart method " + iTestContext.getName());
        iTestContext.setAttribute("WebDriver", SeleniumTestSupport.singleSharedBrowser.get());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        LOGGER.info(CommonUtil.logPrefix() + "I am in onFinish method " + iTestContext.getName());
        Set<ITestResult> failedTests = iTestContext.getFailedTests().getAllResults();
        for (ITestResult temp : failedTests) {
            ITestNGMethod method = temp.getMethod();
            if (iTestContext.getFailedTests().getResults(method).size() > 1) {
                failedTests.remove(temp);
            } else {
                if (iTestContext.getPassedTests().getResults(method).size() > 0) {
                    failedTests.remove(temp);
                }
            }
        }
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LOGGER.info(CommonUtil.logPrefix() + "I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
        IRetryAnalyzer retry = iTestResult.getMethod().getRetryAnalyzer(iTestResult);
        if (retry instanceof RetryTest) {
            ExecutionTracer.getTestTracer().clearActions();
            ExecutionTracer.getTestTracer().setRecordingActionsStatus(true);
        }
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LOGGER.info(CommonUtil.logPrefix() + iTestResult.getTestClass().getName() + " has succeeded");
        LOGGER.info(
                CommonUtil.logPrefix() + iTestResult.getTestClass().getName() + " - clearing actions to save memory");
        ExecutionTracer.getTestTracer().clearActions();
        printMemoryStatus();
    }

//    @Override
//    public void onTestFailure(ITestResult iTestResult) {
//        LOGGER.info(CommonUtil.logPrefix() + "I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
//        Object testClass = iTestResult.getInstance();
//        WebDriver driver = SeleniumTestSupport.singleSharedBrowser.get();
//        // Allure ScreenShotRobot and SaveTestLog
//        if (driver instanceof WebDriver) {
//            LOGGER.info("Screenshot captured for test case:" + getTestMethodName(iTestResult));
//            saveScreenshotPNG(driver);
//        }
//        // Save a log on allure.
//        saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");
//    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        LOGGER.info(CommonUtil.logPrefix() + "I am in onTestFailure method " + getTestMethodName(iTestResult) + "Test Failed");
        LOGGER.info(CommonUtil.logPrefix() + iTestResult.getTestClass().getName() + " has failed");
        ExecutionTracer.fail(iTestResult.getThrowable());
        WebDriver driver = SeleniumTestSupport.singleSharedBrowser.get();
        if (driver instanceof WebDriver) {
            String screenshotFileName = "screenshot" + System.currentTimeMillis() + ".png";
            LOGGER.info(CommonUtil.logPrefix() + "Screenshot " + screenshotFileName + " captured for test case: " + getTestMethodName(iTestResult));
            byte[] screenshot = saveScreenshotPNG(driver);
            CommonUtil.copyScreenshot(screenshotFileName, screenshot);
        }
        // Save a log on allure.
        saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");
        attachHtml(getTestMethodName(iTestResult) + " failed and screenshot taken!");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        LOGGER.info(CommonUtil.logPrefix() + "I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
        Throwable t = iTestResult.getThrowable();
        if (t != null) {
            t.printStackTrace();
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        LOGGER.info(CommonUtil.logPrefix() + "Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

    private void printMemoryStatus() {
        int mb = 1024 * 1024;
        Runtime runtime = Runtime.getRuntime();
        System.out.print(CommonUtil.logPrefix() + "Memory [Used:" + (runtime.totalMemory() - runtime.freeMemory()) / mb + "MB");
        System.out.print(", Free:" + runtime.freeMemory() / mb + "MB");
        System.out.print(", Total:" + runtime.totalMemory() / mb + "MB");
        LOGGER.info(", Max:" + runtime.maxMemory() / mb + "MB");
    }

}
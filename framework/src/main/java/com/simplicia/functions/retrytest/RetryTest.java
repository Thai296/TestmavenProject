package com.simplicia.functions.retrytest;

import exception.DefectSeleniumException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import exception.DefectSeleniumException;
import util.CommonUtil;

/**
 * This class implements the IRetryAnalyzer interface to implement retry failed test cases once.
 */
public class RetryTest implements IRetryAnalyzer {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(RetryTest.class);
    private int retryCount = 0;
    private int maxRetryCount = 1;

    /**
     * Below method returns 'true' if the test method has to be retried else 'false' and it takes the 'Result'
     * as parameter of the test method that just ran
     *
     * @param result result of TestCases
     * @return returns 'true' if the test method has to be retried else 'false'
     */
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount && !(result.getThrowable() instanceof DefectSeleniumException)
                && result.getStatus() == ITestResult.FAILURE) {
            LOGGER.info(CommonUtil.logPrefix() + "Retrying test " + result.getTestClass().getName() + " with status "
                    + getResultStatusName(result.getStatus()) + " for the " + (retryCount + 1) + " time(s).");
            retryCount++;
            return true;
        }
        return false;
    }

    /**
     * Get Result Status Name
     *
     * @param status status of test result
     * @return result name 'SUCCESS' or 'FAILURE' or 'SKIP'
     */
    public String getResultStatusName(int status) {
        String resultName = null;
        if (status == 1) {
            resultName = "SUCCESS";
        }
        if (status == 2) {
            resultName = "FAILURE";
        }
        if (status == 3) {
            resultName = "SKIP";
        }
        return resultName;
    }
}
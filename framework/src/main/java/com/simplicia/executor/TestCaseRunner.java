package com.simplicia.executor;

import com.simplicia.functions.retrytest.RetryableSection;
import controls.Frame;
import exception.DefectSeleniumException;
import exception.MSeleniumException;
import util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class TestCaseRunner {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(TestCaseRunner.class);
    public static int MAXIMUM_NUMBER_OF_FAILURE;
    public static int NUMBER_OF_TEST_RETRY;
    public static List<String> failedTests = new ArrayList<String>();
    public static List<String> successFullTestsAfterRetry = new ArrayList<String>();

    protected int numberOfFailure = 0;
    private List<RuntimeException> failures = new ArrayList<RuntimeException>();

    /**
     * Executes a section
     *
     * @param section section to execute
     */
    public void executeSection(RetryableSection section) {
        executeSection(section, null);
    }

    /**
     * Executes a section
     *
     * @param section      section to execute
     * @param initialFrame frame on which the section should start on
     */
    public void executeSection(RetryableSection section, Frame initialFrame) {
        try {
            section.execute();
        } catch (RuntimeException exc) {
            numberOfFailure = numberOfFailure + 1;
            failures.add(exc);
            LOGGER.info(CommonUtil.logPrefix() + "section FAILED (" + numberOfFailure + ")");
            exc.printStackTrace();
            if (exc instanceof DefectSeleniumException) {

                if (numberOfFailure > 1) {
                    LOGGER.info(CommonUtil.logPrefix() + "EXPECTED DEFECT IDENTIFICATION AFTER RETRY!!!!!");
                }

                LOGGER.info(CommonUtil.logPrefix() + "Section has failed due to a defect, no retry");
                throw exc;
            }
            if (exc instanceof MSeleniumException) {
                MSeleniumException mexc = (MSeleniumException) exc;
                if (mexc.getMessage().contains("Defect")) {

                    if (numberOfFailure > 1) {
                        LOGGER.info(CommonUtil.logPrefix() + "Test " + this.getClass().getSimpleName()
                                + " EXPECTED DEFECT IDENTIFICATION AFTER RETRY!!!!!");
                    }

                    LOGGER.info(CommonUtil.logPrefix() + "Test " + this.getClass().getSimpleName()
                            + " has failed due to a defect, no retry");
                    throw mexc;
                }
            }

            boolean sameCause = false;
            if (numberOfFailure > 1) {
                if (getSimplifiedCause(exc.getMessage()).equals(getSimplifiedCause(failures.get(numberOfFailure - 2).getMessage()))) {
                    sameCause = true;
                }
            }

            if (sameCause || numberOfFailure > NUMBER_OF_TEST_RETRY) {
                if (sameCause) {
                    LOGGER.info(CommonUtil.logPrefix() + "SAME CAUSE DETECTED - FAILURES PROBABLY NOT DUE TO SELENIUM");
                }

                LOGGER.info(CommonUtil.logPrefix() + "Test " + this.getClass().getSimpleName()
                        + " has failed due to the following issues: ");
                for (RuntimeException exd : failures) {
                    LOGGER.info(CommonUtil.logPrefix() + "FAILURE " + (failures.lastIndexOf(exd) + 1) + ": " + exd.getMessage());

                }
                throw exc;
            } else {
                LOGGER.info(CommonUtil.logPrefix() + "Test " + this.getClass().getSimpleName() + " has failed due to : "
                        + exc.getMessage());
                LOGGER.info(CommonUtil.logPrefix() + "BUT RETRYING");
                if (initialFrame != null) {
                    initialFrame.switchToFrame();
                }
                executeSection(section, initialFrame);
                LOGGER.info(CommonUtil.logPrefix() + "Section SUCCESSFUL AFTER RETRY!!!!!");
            }
        }
    }

    private String getSimplifiedCause(String exceptionMessage) {
        String simplifiedCause = "";
        if (exceptionMessage != null && exceptionMessage.length() > 75) {
            simplifiedCause = exceptionMessage.substring(0, 75);
        }
        return simplifiedCause;
    }
}

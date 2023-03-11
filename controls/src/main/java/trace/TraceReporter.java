package trace;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import controls.ControlledElement;
import controls.ControlledList;
import controls.DropDown;
import controls.TextField;
import exception.MSeleniumException;
import util.AsyncFileSaver;
import util.CommonUtil;

/**
 * This class contains method to generate reports and print statistics of actions
 *
 * Created by NiTesH bharDWAJ
 */
public class TraceReporter
{
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(TraceReporter.class);
    public static long CLICK_ACTION_HUMAN = 4000;
    public static long TYPE_ACTION_HUMAN = 5000;
    public static long LIST_SELECTION_HUMAN = 4000;

    private static String reportHtmlFooter = "</table></body></html>";

    // ==========
    // Commands	=
    // ==========

    /**
     * Method to print statistics like number of actions and time taken to execute.
     */
    public static void printStatistics() {
        LOGGER.info(CommonUtil.logPrefix() + "INFO: Number of click actions performed: " + ExecutionTracer.numberOfClickActions());
        LOGGER.info(CommonUtil.logPrefix() + "INFO: Number of typing actions performed: " + ExecutionTracer.numberOfTypingActions());
        LOGGER.info(CommonUtil.logPrefix() + "INFO: Number of list selections performed: " + ExecutionTracer.numberOfListSelections());

        long estimatedManualExecution = getEstimatedManualExecution();
        LOGGER.info(CommonUtil.logPrefix() + "INFO: Estimated virtual manual execution: " + getFriendlyDuration(estimatedManualExecution));
        LOGGER.info(CommonUtil.logPrefix() + "INFO: Estimated real-time execution: " + getFriendlyDuration(2 * estimatedManualExecution));

        LOGGER.info(CommonUtil.logPrefix() + "INFO: Tests executed sorted by duration:");
        Map<String, TestTracer> testTracerMap = ExecutionTracer.getTestTracers();
        List<TestTracer> testTracerList = new ArrayList<TestTracer>();
        testTracerList.addAll(testTracerMap.values());
        Collections.sort(testTracerList, new Comparator<TestTracer>() {
            @Override
            public int compare(TestTracer left, TestTracer right) {
                return (((Long)right.duration())).compareTo(left.duration());
            }
        });

        for (TestTracer testTracer : testTracerList) {
            Map.Entry<String, TestTracer> mapKey = getTestTracerMapEntry(testTracer);
            LOGGER.info(CommonUtil.logPrefix() + "INFO:     " + mapKey.getKey() + " -> " + mapKey.getValue().duration() + " ms [clicks: "
                    + mapKey.getValue().numberOfClickActions() + ", typing: " + mapKey.getValue().numberOfTypingActions() + ", selections: "
                    + mapKey.getValue().numberOfListSelections() + ", idle: " + mapKey.getValue().idleTime() + " ms)");
        }

        LOGGER.info(CommonUtil.logPrefix() + "INFO: maximum retrieval time: " + ExecutionTracer.maxRetrievalTime() / 1000 + " secs");
        LOGGER.info(CommonUtil.logPrefix() + "INFO: number of retried actions: " + ExecutionTracer.numberOfRetriedActions());
        if (ExecutionTracer.numberOfActions() > 0) {
            LOGGER.info(CommonUtil.logPrefix() + "INFO: framework robustness score: "
                + (ExecutionTracer.numberOfActions() - ExecutionTracer.numberOfRetriedActions()) * 100 / ExecutionTracer.numberOfActions());
        }
        LOGGER.info(CommonUtil.logPrefix() + "INFO: Consider checking usage of following elements:");
        for (Map.Entry<String, Integer> entry : ExecutionTracer.retriedLocators().entrySet()) {
            LOGGER.info(CommonUtil.logPrefix() + "INFO: -> " + entry.getKey() + " [" + entry.getValue() + " retries]");
        }
    }

    /**
     * Method to print statistics like number of different types of action performed.
     *
     * @param startTime when execution starts.
     * @param endTime when execution ends.
     */
    public static void printStatistics(long startTime, long endTime) {
        LOGGER.info(CommonUtil.logPrefix() + "INFO: Number of click actions performed: " + ExecutionTracer.numberOfClickActions());
        LOGGER.info(CommonUtil.logPrefix() + "INFO: Number of typing actions performed: " + ExecutionTracer.numberOfTypingActions());
        LOGGER.info(CommonUtil.logPrefix() + "INFO: Number of list selections performed: " + ExecutionTracer.numberOfListSelections());
        long durationOfExecution = endTime - startTime;
        LOGGER.info(CommonUtil.logPrefix() + "INFO: Duration of this run: " + getFriendlyDuration(durationOfExecution));
        long estimatedManualExecution = getEstimatedManualExecution();
        LOGGER.info(CommonUtil.logPrefix() + "INFO: Estimated virtual manual execution: " + getFriendlyDuration(estimatedManualExecution));
        LOGGER.info(CommonUtil.logPrefix() + "INFO: Estimated real-time execution: " + getFriendlyDuration(4 * estimatedManualExecution));
        LOGGER.info(CommonUtil.logPrefix() + "INFO: Effort saved by this run: " + getFriendlyDuration(4 * estimatedManualExecution - durationOfExecution));

        printStatistics();
    }

    /**
     * Method to generate html description for 'Click' type of action.
     *
     * @param className test class name.
     * @param element element to click.
     * @return html description of 'Click' element.
     */
    public static String generateClickElementHtmlDescription(String className, ControlledElement element) {
        if (element.friendlyName == null) {
            return "<b>CLICKING " + element.by + "<br>[" + className + "]";
        } else {
            return "<b>Clicking " + element.friendlyName + "</b><br>(" + element.by + ")<br>[" + className + "]";
        }
    }

    /**
     * Method to generate html description for 'Click All' type of action.
     *
     * @param className test class name.
     * @param element element to click.
     * @return html description of 'Click All' element.
     */
    public static String generateClickAllElementHtmlDescription(String className, ControlledList element) {
        if (element.friendlyName == null) {
            return "<b>CLICKING ALL " + element.by + "<br>[" + className + "]";
        } else {
            return "<b>Clicking all " + element.friendlyName + "</b><br>(" + element.by + ")<br>[" + className + "]";
        }
    }

    /**
     * Method to generate html description for 'Check' type of action.
     *
     * @param className test class name.
     * @param element element to check.
     * @return html description of 'Check' element.
     */
    public static String generateCheckElementHtmlDescription(String className, ControlledElement element) {
        if (element.friendlyName == null) {
            return "<b>CHECKING " + element.by + "<br>[" + className + "]";
        } else {
            return "<b>Checking " + element.friendlyName + "</b><br>(" + element.by + ")<br>[" + className + "]";
        }
    }

    /**
     * Method to generate html description for 'Select' type of action.
     *
     * @param className test class name.
     * @param element dropdown element.
     * @param value to be select from dropdown.
     * @return html description of 'Select' element.
     */
    public static String generateSelectElementHtmlDescription(String className, DropDown element, String value) {
        if (element.friendlyName == null) {
            return "<b>SELECTING " + value + " IN " + element.by + "<br>[" + className + "]";
        } else {
            return "<b>Selecting " + value + " in " + element.friendlyName + "</b><br>(" + element.by + ")<br>[" + className + "]";
        }
    }

    /**
     * Method to generate html description for 'Type' in Text Field type of action.
     *
     * @param className test class name.
     * @param element textField element.
     * @param value to enter in text field.
     * @return html description of 'Type' element.
     */
    public static String generateTypeElementHtmlDescription(String className, TextField element, String value) {
        if (element.friendlyName == null) {
            return "<b>TYPING " + value + " IN " + element.by + "<br>[" + className + "]";
        } else {
            return "<b>Typing " + value + " in " + element.friendlyName + "</b><br>(" + element.by + ")<br>[" + className + "]";
        }
    }

    /**
     * Method to generate the Defect Report.
     */
    public static void generateDefectReports() {
        for (Map.Entry<String, TestTracer> entry : ExecutionTracer.getTestTracers().entrySet()) {
            if (entry.getValue().isFailed()) {
                LOGGER.info(CommonUtil.logPrefix() + "Generating failure report for " + entry.getKey());
                new File("./target/defects/" + entry.getKey()).mkdirs();

                String errorMessage = entry.getValue().getErrorMessage();

                if (errorMessage != null && errorMessage.length() > 150) {
                    errorMessage = errorMessage.substring(0, 149) + "...";
                }

                String htmlReport = generateReportHtmlHeader(entry.getKey(), errorMessage, entry.getValue().getRecordedActions());

                TracedAction[] actions = entry.getValue().getRecordedActions().toArray(
                        new TracedAction[entry.getValue().getRecordedActions().size()]);

                for (int i = 0; i < actions.length; i++) {
                    if (i == actions.length - 1) {
                        htmlReport = htmlReport + generateReportHtmlRow("./target/defects/", entry.getValue(), actions[i], null);
                    } else {
                        htmlReport = htmlReport + generateReportHtmlRow("./target/defects/", entry.getValue(), actions[i], actions[i + 1]);

                    }
                }

                if (entry.getValue().getException() instanceof MSeleniumException) {
                    MSeleniumException exception = (MSeleniumException) entry.getValue().getException();
                    htmlReport = htmlReport + generateReportHtmlRow(exception);
                }

                htmlReport = htmlReport + reportHtmlFooter;
                generateDefectReportFile(entry.getKey(), htmlReport);
            } else {
                LOGGER.info(CommonUtil.logPrefix() + entry.getKey() + " was successful");

            }

        }
    }

    // ==========================
    // Private Static Methods	=
    // ==========================

    /**
     * Method to get friendly duration.
     *
     * @param duration total number of time taken to perform different actions.
     * @return duration in minutes, hours, days based on generated duration.
     */
    public static String getFriendlyDuration(long duration) {
        if (duration < 2 * 60 * 60 * 1000) {
            return TimeUnit.MILLISECONDS.toMinutes(duration) + " mins";
        } else if (duration < 8 * 60 * 60 * 1000) {
            long hours = TimeUnit.MILLISECONDS.toHours(duration);
            long mins = TimeUnit.MILLISECONDS.toMinutes(duration) % TimeUnit.HOURS.toMinutes(1);
            return hours + " hours, " + mins + " mins";
        } else {
            long wdays = TimeUnit.MILLISECONDS.toHours(duration) / 8;
            long hours = TimeUnit.MILLISECONDS.toHours(duration) % TimeUnit.HOURS.toHours(8);
            return wdays + " work-days, " + hours + " hours";
        }
    }

    /**
     * Method to get estimated manual execution time.
     *
     * @return sum of click action, type actions and list selections time.
     */
    private static long getEstimatedManualExecution() {

        return ExecutionTracer.numberOfClickActions() * CLICK_ACTION_HUMAN + ExecutionTracer.numberOfTypingActions() * TYPE_ACTION_HUMAN
                + ExecutionTracer.numberOfListSelections() * LIST_SELECTION_HUMAN;
    }

    /**
     * Method to generate the Report file for an defect.
     *
     * @param className test class name.
     * @param htmlReport html report of Defect.
     */
    private static void generateDefectReportFile(String className, String htmlReport) {
        File defect = new File("./target/defects/3-" + className + ".html");
        try {
            LOGGER.info(CommonUtil.logPrefix() + "Generating " + defect.getName());
            FileUtils.writeByteArrayToFile(defect, htmlReport.getBytes());
        } catch (IOException exc) {
            exc.printStackTrace();
        }

    }

    /**
     * Method to generate html header for Test Report.
     *
     * @param className test case name
     * @param errorMessage failure message of test case.
     * @param actions list of TracedAction
     * @return html header for test report
     */
    private static String generateReportHtmlHeader(String className, String errorMessage, List<TracedAction> actions) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /><style type=\"text/css\" media=\"all\">@import url(\"./maven-base.css\");@import url(\"./maven-theme.css\");@import url(\"./site.css\");</style></head><body class=\"composite\"><div id=\"banner\"><div id=\"bannerLeft\">"
                + className
                + "</div><div class=\"clear\"><hr/></div></div><div id=\"breadcrumbs\"><div class=\"xleft\"><span id=\"publishDate\">Generated on: "
                + new Date() + "</span></div><div class=\"clear\"><hr/></div></div><h2>Last " + actions.size() + " steps to repro "
                + errorMessage
                + "</h2><table border=\"0\" class=\"bodyTable\"><tr class=\"a\"><th style=\"text-align:center\">Initial Screen</th><th style=\"text-align:center\">Action</th><th style=\"text-align:center\">Result</th></tr>";
    }

    /**
     * Method to generate row for Html Test Report.
     *
     * @param folderPath path where report to be generated.
     * @param testTracer testTracer Value.
     * @param firstAction first tracedAction.
     * @param secondAction second tracedAction.
     * @return html row for test report.
     */
    private static String generateReportHtmlRow(String folderPath, TestTracer testTracer, TracedAction firstAction,
                                                TracedAction secondAction) {
        return "<tr class=\"b\">" + generateReportHtmlImageCell(folderPath, testTracer, firstAction)
                + " <td style=\"text-align:center;vertical-align:middle\">" + firstAction.actionIndex() + " - " + firstAction.description()
                + "</td>" + generateReportHtmlImageCell(folderPath, testTracer, secondAction) + "</tr>";
    }

    /**
     * Method to generate Html row for Html Test Report when exception occur.
     *
     * @param exception exception occur while test execution.
     * @return html row for test report.
     */
    private static String generateReportHtmlRow(MSeleniumException exception) {
        String errorMessage = exception.getSimpleMessage();
        if (errorMessage.length() > 150) {
            errorMessage = errorMessage.substring(0, 149) + "...";
        }

        return "<tr class=\"b\">" + generateReportHtmlImageCell(exception) + " <td style=\"text-align:center;vertical-align:middle\"><h2>"
                + errorMessage + "</h2></td>" + generateReportHtmlImageCell(exception) + "</tr>";
    }

    /**
     * Method to generate image cell by getting the screenshot file name for Html Test Report.
     *
     * @param folderPath path of the screenshot file.
     * @param testTracer testTracer Value.
     * @param action tracedAction
     * @return html image cell for test Report.
     */
    private static String generateReportHtmlImageCell(String folderPath, TestTracer testTracer, TracedAction action) {
        if (action != null) {
            if (action.screenshot() != null) {
                String screenshotFileName = action.getScreenshotFileName(folderPath).getName();
                return "<td style=\"width:15%\"><a href=\"./" + screenshotFileName
                        + "\"><div style=\"max-height: 300px; overflow: hidden;\"><img src=\"./" + screenshotFileName
                        + "\" width=\"100%\" height=\"100%\"/></div></a></td>";
            } else {
                return "<td style=\"text-align:center;vertical-align:middle\"><h1>OK</h1></td>";
            }
        } else {
            if (testTracer.getException() instanceof MSeleniumException) {
                return generateReportHtmlImageCell((MSeleniumException) testTracer.getException());
            } else {
                return "<td style=\"text-align:center;vertical-align:middle\"><h2>" + testTracer.getErrorMessage() + "</h2></td>";
            }
        }
    }

    /**
     * Method to generate image cell by getting the screenshot file name for Html Test Report when exception
     * occur.
     *
     * @param exception exception occur while test execution.
     * @return html image cell for test Report.
     */
    private static String generateReportHtmlImageCell(MSeleniumException exception) {
        String screenshotFileName = exception.getScreenshotFileName();

        return "<td style=\"width:15%\"><a href=\"./" + screenshotFileName
                + "\"><div style=\"max-height: 300px; overflow: hidden;\"><img src=\"./" + screenshotFileName
                + "\" width=\"100%\" height=\"100%\"/></div></a></td>";
    }

    /**
     * Method to get mapped value of test tracer.
     *
     * @param testTracer testTracer
     * @return mapped value of testTracer or null
     */
    private static Map.Entry<String, TestTracer> getTestTracerMapEntry(TestTracer testTracer) {
        for (Map.Entry<String, TestTracer> mapEntry : ExecutionTracer.getTestTracers().entrySet()) {
            if (mapEntry.getValue() == testTracer) {
                return mapEntry;
            }
        }
        return null;
    }
}
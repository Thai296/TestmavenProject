package com.simplicia.functions;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.TimeZone;

public class Utility {

    public static final String DATE_FORMAT_VIEW_MODE_SHORT = "M/d/yy";
    public static final String DATE_FORMAT_VIEW_MODE_INTERNATIONAL = "yyyy-MM-dd";
    public static final String DATE_FORMAT_EDIT_MODE_SHORT = "M/d/yyyy";
    public static final String DATE_FORMAT_EDIT_MODE_INTERNATIONAL = "yyyy-MM-dd";
    public static final String TIME_FORMAT_EDIT_MODE_SHORT = "h:mm a";

    // ======================
    // Date Utility Methods	=
    // ======================

    /**
     * Get View Mode Current Date
     *
     * @return the current date using the default view mode out of the box format
     */
    public static String getViewModeCurrentDate() {
        Calendar cal = Calendar.getInstance();
        return getViewModeDate(cal);
    }

    /**
     * Get Edit Mode Current Date
     *
     * @return the current date using the default edit mode out of the box format
     */
    public static String getEditModeCurrentDate() {
        Calendar cal = Calendar.getInstance();
        return getEditModeDate(cal);
    }

    /**
     * Get Log File Current Date
     *
     * @return the current date using the log file date format
     */
    public static String getLogFileCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        dateFormat.setLenient(false);
        Calendar cal = Calendar.getInstance();
        dateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
        return dateFormat.format(cal.getTime());
    }

    /**
     * Get Current Date
     *
     * @param dateFormatPattern Pattern of date format
     * @return the current date using the default view mode out of the box format
     */
    public static String getCurrentDate(String dateFormatPattern) {
        DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
        dateFormat.setLenient(false);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    /**
     * Get Current Time
     *
     * @return the current time
     */
    public static String getEditModeCurrentTime() {
        Calendar cal = Calendar.getInstance();
        return getEditModeTime(cal);
    }

    /**
     * Get Server Current Date
     *
     * @param dateFormatPattern Pattern of date format
     * @return the current date using the default view mode out of the box format
     */
    public static String getServerCurrentDate(String dateFormatPattern) {
        DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
        dateFormat.setLenient(false);
        Calendar cal = Calendar.getInstance();
        dateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
        return dateFormat.format(cal.getTime());
    }

    /**
     * Get Current Month
     *
     * @return the current month
     */
    public static String getCurrentMonth() {
        DateFormat dateFormat = new SimpleDateFormat("MMMMMMMMM");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    /**
     * Get the future date of the year.
     *
     * @param x Number of days for the future date.
     * @return Future date.
     */
    public static String getDateXDaysFromNow(int x) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, x);
        return dateFormat.format(cal.getTime());
    }

    /**
     * Get the future date of the year with required date pattern.
     *
     * @param x                 Number of days for the future date
     * @param dateFormatPattern Date Format Pattern.
     * @return Future date.
     */
    public static String getDateXDaysFromNow(int x, String dateFormatPattern) {
        DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, x);
        return dateFormat.format(cal.getTime());
    }

    /**
     * Get Edit Mode Date
     *
     * @param date date to return in edit mode format
     * @return Future date.
     */
    public static String getEditModeDate(Calendar date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_EDIT_MODE_SHORT);
        dateFormat.setLenient(false);
        return dateFormat.format(date.getTime());
    }

    /**
     * Get Current Time
     *
     * @return the current time
     */
    public static String getEditModeTime(Calendar time) {
        DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT_EDIT_MODE_SHORT);
        return dateFormat.format(time.getTime());
    }

    /**
     * Get View Mode Date
     *
     * @param date date to return in view mode format
     * @return Future date.
     */
    public static String getViewModeDate(Calendar date) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        return dateFormat.format(date.getTime());
    }

    /**
     * Get Date X Years From Now
     *
     * @param x the number of years
     * @return the date x years from now
     */
    public static String getDateXYearsFromNow(int x) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, x);
        return dateFormat.format(cal.getTime());
    }

    /**
     * Get time x minutes from now.
     *
     * @param x      minutes from now
     * @param format time format
     * @return time x minutes from now
     */
    public static String getDateXMinutesFromNow(int x, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, x);
        return dateFormat.format(cal.getTime());
    }

    /**
     * Get Month From Now.
     *
     * @return Future Month.
     */
    public static String getMonthFromNow(int monthOffset) {
        DateFormat dateFormat = new SimpleDateFormat("M/d/yy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, monthOffset);
        return dateFormat.format(cal.getTime());
    }

    /**
     * Get the current year.
     *
     * @return Current Year.
     */
    public static String getCurrentYear() {
        Calendar now = Calendar.getInstance();
        Integer year = now.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * Get the past year.
     *
     * @param pastYearValue Value of past year
     * @return Past Year.
     */
    public static String getPastYear(int pastYearValue) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -pastYearValue);
        Integer year = cal.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * Get the past year.
     *
     * @param yearOffset Off set year
     * @return Future Year.
     */
    public static String getYearFromNow(int yearOffset) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, yearOffset);
        return dateFormat.format(cal.getTime());
    }

    /**
     * Get time difference in minutes between two times
     *
     * @param firstTime  String value
     * @param secondTime String value
     * @return long value minutes
     */
    public static long getTimeDifferenceInMinutes(String firstTime, String secondTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime t1 = LocalTime.parse(firstTime, fmt);
        LocalTime t2 = LocalTime.parse(secondTime, fmt);
        long minutes = ChronoUnit.MINUTES.between(t1, t2);
        return minutes;
    }

    /**
     * Get time difference in days between two dates
     *
     * @param date1 String value
     * @param date2 String value
     * @return int value minutes
     */
    public static int getTimeDifferenceInDays(String date1, String date2) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate t1 = LocalDate.parse(date1, fmt);
        LocalDate t2 = LocalDate.parse(date2, fmt);
        int days = (int) ChronoUnit.DAYS.between(t1, t2);
        return days;
    }

    /**
     * Get time difference in Months between two dates
     *
     * @param date1 String value
     * @param date2 String value
     * @return int number of months between two dates
     */
    public static int getTimeDifferenceInMonths(String date1, String date2) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate t1 = LocalDate.parse(date1, fmt).withDayOfMonth(1);
        LocalDate t2 = LocalDate.parse(date2, fmt).withDayOfMonth(1);
        int numberOfMonths = (int) ChronoUnit.MONTHS.between(t1, t2);
        return numberOfMonths;
    }

    /**
     * Method to get time after minutes from current time
     *
     * @param timeFormat Format in which time is required
     * @param minutes    Minutes to be added in current time.
     * @return Return current system time
     */
    public static String getTimeAfterMinuteFromCurrentTime(String timeFormat, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minutes);
        SimpleDateFormat time = new SimpleDateFormat(timeFormat);
        return time.format(cal.getTime());
    }

    // ==============================
    // Conversion Utility Methods	=
    // ==============================

    public static Comparable getComparable(String elementValue, Class<? extends Comparable> comparableType) {
        if (comparableType.equals(String.class)) {
            return elementValue.toLowerCase();
        }
        if (comparableType.equals(BigDecimal.class)) {
            // most of deciamls are numbers starting with $
            String value = elementValue;
            if (elementValue.startsWith("$")) {
                value = StringUtils.substringAfter(elementValue, "$");
            }
            if (elementValue.startsWith("(")) {
                value = '-' + StringUtils.substringBetween(elementValue, "(", ")");
            }
            if (elementValue.equals("---")) {
                value = "0.00";
            }
            return new BigDecimal(value);
        }
        return null;
    }

    // ======================
    // File Utility Methods	=
    // ======================

    /**
     * Delete test file.
     *
     * @param file name for the resulted test file.
     * @return returns the deleted status of the file
     */
    public static boolean deleteFile(File file) {
        boolean deleted = false;

        if (file.isFile()) {
            deleted = file.delete();
            return deleted;
        } else if (file.isDirectory()) {
            if (file.list().length == 0) {
                deleted = file.delete();
                return deleted;
            }
            String[] files = file.list();
            for (String temp : files) {
                File fileDelete = new File(file, temp);
                deleteFile(fileDelete);
            }

            if (file.list().length == 0) {
                deleted = file.delete();
                return deleted;
            }
        }
        return deleted;
    }

    /**
     * Creates test file.
     *
     * @param fileName name for the resulted test file.
     * @param data     text data to be written in file.
     * @return returns the absolute path of the file created.
     */
    public static String createTestFile(String fileName, String data) {
        File file = new File(fileName);
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return file.getAbsolutePath();
    }

    /**
     * Check if a file exists or not.
     *
     * @param filePath
     * @return
     */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * Get Current Month in numeric format
     *
     * @return the current month
     */
    public static String getCurrentMonthInNumericFormat() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}
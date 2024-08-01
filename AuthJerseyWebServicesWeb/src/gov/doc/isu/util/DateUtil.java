/*****************************************************************
 * FILE : DateUtils.java CLASS : DateUtils Created on October, 2008
 ******************************************************************/

package gov.doc.isu.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.ParseException;

import org.apache.log4j.Logger;

/**
 * <p>
 * Provides date utilities to classes to assist in the retrieval and manipulation of date values. The provided date utilities range from adding a time component to a date, converting a date in string format to a date object, to retrieving the year from a date object.
 * <p>
 * If new date functions are needed for a specific task, make every attempt to place the implementation in this utility, rather than putting it in a specific class. In doing so, make sure the implementation is generic enough that other classes will be able to make use of its functionality.
 *
 * @author Todd Tellman
 * @author Richard Salas
 * @version 1.0
 * @see java.util.Date
 * @see java.util.GregorianCalendar
 */
public class DateUtil implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -4409381298917221501L;

    private static Logger myLogger = Logger.getLogger("gov.doc.isu.util.DateUtil");

    /**
     * A static representation of the length for a long date. This is used when determining if the length of the date integer to be converted is equal to the long date length requirement.
     */
    private static final int LONG_DATE_LEN = 8;

    /**
     * A static representation of the MMDDYYYY date pattern. This is used to specify a pattern when converting a date string from mmddyyyy to yyyymmdd.
     */
    private static final String MMDDYYYY_PATTERN = "MM/dd/yyyy";

    /**
     * A static representation of the YYYYMMDD date pattern. This is used to specify a pattern when converting a date string from mmddyyyy to yyyymmdd.
     */
    private static final String YYYYMMDD_PATTERN = "yyyy-MM-dd";

    /**
     * A static representation of a date to default new date to. This is used when converting a date string from mmddyyyy to yyyymmdd.
     */
    private static final String DEFAULT_DATE = "1900-01-01";

    /**
     * Converts a numeric value in the form YYYYMMDD to a <code>Date</code> object.
     * <p>
     * OPII stores these dates in the following format:<br>
     * YYYY - Four digit year<br>
     * MM - Two digit month<br>
     * DD - Two digit day<br>
     *
     * @param inDate
     *        a numeric date value to be converted to a date object
     * @return <code>Date</code> the date object converted from the integer value representation. Returns null if the input paramter is null, the value is 0, or the length isn't the equal to a long date length. Also returns null if an error is encountered in the conversion process.
     */
    public static Date formatLongDate(Integer inDate) {
        myLogger.debug("Entering formatLongDate(Integer inDate).");

        if(inDate == null || inDate.intValue() == 0 || inDate.toString().length() != LONG_DATE_LEN){
            return null;
        }else{
            try{
                String strDate = inDate.toString();
                strDate = strDate.trim();

                String year = strDate.substring(0, 4);
                String month = strDate.substring(4, 6);
                String day = strDate.substring(6, 8);

                GregorianCalendar gCal = new GregorianCalendar(new Integer(year).intValue(), new Integer(month).intValue() - 1, new Integer(day).intValue(), 0, 0, 0);
                return gCal.getTime();
            }catch(Exception e){
                myLogger.error(" Error formatting date. Error msg: ", e);
                return null;
            }
        }
    }

    /**
     * Utility method to get the standard default timestamp.
     *
     * @return Timestamp with the value of '7799-12-31 00:00:00.0'
     */
    @SuppressWarnings("deprecation")
    public static java.sql.Timestamp getDefaultTimestamp() {
        return new java.sql.Timestamp(new java.util.Date("12/31/7799").getTime());
    }

    /**
     * Gets the current java.sql.Timestamp
     *
     * @return java.sql.Timestamp
     */
    public static java.sql.Timestamp getSQLTimestamp() {
        myLogger.debug("Entering getSQLTimestamp.");
        return new java.sql.Timestamp(new java.util.Date().getTime());
    }// end method

    /**
     * Returns the Current Date as a MM/dd/yyyy formatted string.
     *
     * @return String String
     */
    public static String getCurrentDateAsString() {
        return new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
    }

    /**
     * Converts a <code>Date</code> object to a long integer format.
     *
     * @param inDate
     *        the <code>Date</code> object to be converted
     * @return <code>Integer</code> - numeric value in the format YYYYMMDD.
     */
    public static Integer toLong(Date inDate) {
        myLogger.debug("Entering toLong(Date inDate).");

        Integer returnInt = null;
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(inDate);
        int year = gCal.get(Calendar.YEAR);
        int month = gCal.get(Calendar.MONTH) + 1;
        int day = gCal.get(Calendar.DAY_OF_MONTH);
        String strYr = String.valueOf(year);
        String strMon = String.valueOf(month);
        String strDay = String.valueOf(day);
        if(month < 10 || day < 10){
            if(month < 10){
                strMon = "0".concat(strMon);
            }
            if(day < 10){
                strDay = "0".concat(strDay);
            }
        }
        returnInt = new Integer(strYr + strMon + strDay);
        return returnInt;
    }

    /**
     * Converts a BigDecimal value in the form HHMMSS to a java.sql.Time object. See formatTime (Integer time) for details.
     *
     * @param time
     *        <code>BigDecimal</code> value to be converted from BigDecimal to Time
     * @return <code>Time</code> - the BigDecimal time value in a time format. Returns null if the input parameter is null. Returns 12:00 AM if parameter int value is 0
     */
    public static Time formatTime(BigDecimal time) {
        myLogger.debug("Entering formatTime (BigDecimal time).");

        if(time == null){
            return null;
        }else{
            int intValue = time.intValue();
            if(intValue != 0){
                return formatTime(new Integer(intValue));
            }else{
                Calendar cal = Calendar.getInstance();
                cal.clear();
                return new Time(cal.getTime().getTime());
            }

        }
    }

    /**
     * Converts an integer value in the form HHMMSS to a java.sql.Time object In the following format:<br>
     * HH - Two digit hour<br>
     * MM - Two digit minute<br>
     * SS - Two digit second<br>
     *
     * @param time
     *        an <code>Integer</code> value to be converted.
     * @return <code>Time</code> - a Time object representing the formatted time. Returns null if the input parameter is null or equal to 0. Otherwise returns an Time object in the form HHMMSS.
     */
    public static Time formatTime(Integer time) {
        myLogger.debug("Entering formatTime (Integer time).");

        String strHour;
        String strMin;
        String strSec;
        Time newTime = null;
        if(time == null || time.intValue() == 0){
            newTime = null;
        }else{
            String strTime = String.valueOf(time.intValue());
            if(strTime.length() < 6){
                for(int i = strTime.length();i < 6;i++){
                    strTime = "0" + strTime;
                }//end for
            }//end if
            strHour = strTime.substring(0, 2);
            strMin = strTime.substring(2, 4);
            strSec = strTime.substring(4, 6);
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(strHour).intValue());
            cal.set(Calendar.MINUTE, Integer.valueOf(strMin).intValue());
            cal.set(Calendar.SECOND, Integer.valueOf(strSec).intValue());
            newTime = new Time(cal.getTime().getTime());
        }//end if...else
        return newTime;
    }//end method

    /**
     * Converts a <code>Date</code> object to an integer value in the form HHMMSS.
     *
     * @param inDate
     *        the <code>Date</code> object to be converted.
     * @return <code>Integer</code> - a numeric value in the form HHMMSS. Returns null if the input paramater is null. Otherwise returns an integer value in the form HHMMSS.
     */
    public static Integer returnTime(Date inDate) {
        myLogger.debug("Entering returnTime(Date inDate).");

        Integer returnTime = null;
        if(inDate != null){
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.setTime(inDate);
            String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
            String min = String.valueOf(cal.get(Calendar.MINUTE));
            if(min.length() == 1){
                min = "0" + min;
            }//end if
            String sec = String.valueOf(cal.get(Calendar.SECOND));
            if(sec.length() == 1){
                sec = "0" + sec;
            }//end if
            String time = hour.concat(min).concat(sec);
            returnTime = new Integer(time);
        }//end if
        return returnTime;
    }//end method

    /**
     * Adds a <code>Time</code> component of a Date to a Date object.
     *
     * @param dt
     *        a <code>Date</code> Object the time component will be added to
     * @param tm
     *        the Time component of the Date to add to the date object.
     * @return <code>Date</code> - the input Date with a time component added. If the dt component is null it will return a null value.
     */
    public static Date addTime(Date dt, Date tm) {
        myLogger.debug("Entering addTime(Date dt, Date tm).");

        int hrs = 0;
        int mins = 0;
        int secs = 0;
        if(dt == null){
            return null;
        }//end if

        if(tm != null){
            GregorianCalendar tgc = new GregorianCalendar();
            tgc.setTime(tm);
            hrs = tgc.get(Calendar.HOUR_OF_DAY);
            mins = tgc.get(Calendar.MINUTE);
            secs = tgc.get(Calendar.SECOND);
        }

        GregorianCalendar dgc = new GregorianCalendar();
        dgc.setTime(dt);

        GregorianCalendar gc = new GregorianCalendar(dgc.get(Calendar.YEAR), dgc.get(Calendar.MONTH), dgc.get(Calendar.DATE), hrs, mins, secs);

        return new Date(gc.getTime().getTime());
    }

    /**
     * Truncates the time component from a date/time value.
     *
     * @param dt
     *        a <code>Date</code> object with time
     * @return <code>Date</code> - The truncated date component without time. Returns null if the dt input parameter is null.
     */
    public static Date truncateDate(Date dt) {
        myLogger.debug("Entering truncateDate(Date dt).");
        if(dt == null){
            return null;
        }//end if
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dt);
        int year = gc.get(Calendar.YEAR), month = gc.get(Calendar.MONTH), day = gc.get(Calendar.DAY_OF_MONTH);
        GregorianCalendar gc1 = new GregorianCalendar(year, month, day, 0, 0, 0);
        return gc1.getTime();
    }

    /**
     * Get a <code>Time</code> value from a Date value.
     *
     * @param dt
     *        a <code>Date</code> object with time.
     * @return <code>Time</code> - The extracted time component from the date object. Returns null if the dt input parameter is null.
     */
    public static Time getTime(Date dt) {
        myLogger.debug("Entering getTime(Date dt).");

        if(dt == null){
            return null;
        }//end if
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dt);
        GregorianCalendar gc1 = new GregorianCalendar();
        gc1.clear();
        gc1.set(Calendar.HOUR_OF_DAY, gc.get(Calendar.HOUR_OF_DAY));
        gc1.set(Calendar.MINUTE, gc.get(Calendar.MINUTE));
        gc1.set(Calendar.SECOND, gc.get(Calendar.SECOND));
        long time = gc1.getTime().getTime();
        return new Time(time);
    }

    /**
     * Adds the specified (signed) number of days to the given date object. If the numOfDays parameter has a negative indicator associated it will in effect subtract those days from the date object. Otherwise if the numOfDays parameter has a positive indicator the days will be added to the date object.
     *
     * @param date
     *        the base <code>Date</code> to add days to.
     * @param numOfDays
     *        number of days to add (or to subtract if numOfDays < 0)
     * @return a <code>Date</code> - the date object with days added/subtracted. Returns a null date value if the date input parameter is null. If the numOfDays parameter is zero, then this date object is returned untouched.
     */
    public static Date addDays(Date date, int numOfDays) {
        myLogger.debug("Entering addDays(Date date, int numOfDays).");

        if(date == null){
            return null;
        }//end if
        if(numOfDays == 0){
            return date;
        }//end if

        GregorianCalendar newCalendarDate = new GregorianCalendar();
        newCalendarDate.setTime(date);
        newCalendarDate.add(Calendar.DAY_OF_MONTH, numOfDays);

        return newCalendarDate.getTime();
    }//end method

    /**
     * Adds the specified (signed) number of years to the given date object. If the numOfYears parameter has a negative indicator associated it will in effect subtract those years from the date object. Otherwise if the numOfYears parameter has a positive indicator the years will be added to the date object.
     *
     * @param date
     *        the base <code>Date</code> to add years to.
     * @param numOfYears
     *        number of years to add (or to subtract if numOfYears < 0)
     * @return a <code>Date</code> - the date object with years added/subtracted. Returns a null date value if the date input parameter is null. If the numOfYears parameter is zero, then this date object is returned untouched.
     */
    public static Date addYears(Date date, int numOfYears) {
        myLogger.debug("Entering addYears(Date date, int numOfYears).");

        if(date == null){
            return null;
        }//end if
        if(numOfYears == 0){
            return date;
        }//end if

        Calendar newCalendarDate = new GregorianCalendar();
        newCalendarDate.setTime(date);
        newCalendarDate.add(Calendar.YEAR, numOfYears);

        return newCalendarDate.getTime();
    }

    /**
     * Calculates the date of the first day of previous month relative to the date object from the input parameters.
     *
     * @param dt
     *        The <code>Date</code> to which the first day of the month calculated from.
     * @return <code>Date</code> - the date object with the first day of the month. Returns a null date value if the date input parameter is null.
     */
    public static Date getFirstDayOfPreviousMonth(Date dt) {
        myLogger.debug("Entering getFirstDayOfPreviousMonth(Date dt).");

        if(dt == null){
            return null;
        }//end if
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dt);
        gc.add(Calendar.MONTH, -1);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        return gc.getTime();
    }

    /**
     * Calculates the date of the last day of the next month relative to the date object from the input parameters.
     *
     * @param dt
     *        the <code>Date</code> to which the last day of the month calculated from
     * @return <code>Date</code> - the date object with the last day of the month. Returns a null date value if the date input parameter is null.
     */
    public static Date getLastDayOfNextMonth(Date dt) {
        myLogger.debug("Entering getLastDayOfNextMonth(Date dt).");

        if(dt == null){
            return null;
        }//end if
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dt);
        gc.add(Calendar.MONTH, 1);
        gc.set(Calendar.DAY_OF_MONTH, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
        return gc.getTime();
    }

    /**
     * Returns a <code>Date</code> object value from a date string value using the formatMask specified from the input parameters. If a formatMask is not specified, this date object will be returned in a "yyyy-MM-dd" format.
     *
     * @param date
     *        the date string to be formatted
     * @param formatMask
     *        the format to apply to the date string for converting to the date object. If this is null the format will be set to yyyy-MM-dd
     * @return <code>Date</code> - the date object formatted according to the formatMask input parameter. Returns a null date value if the date input parameter is null. If the formatMask is not specified, this date object will be returned in a "yyyy-MM-dd" format.
     * @throws ParseException
     *         the exception encountered when converting a SimpleDateFormat to a Date object
     */
    public static Date toDate(String date, String formatMask) throws ParseException {
        myLogger.debug("Entering  toDate(String date, String formatMask).");

        if(date == null){
            return null;
        }

        if(formatMask == null){
            formatMask = "yyyy-MM-dd";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(formatMask);

        return dateFormat.parse(date);
    }

    /**
     * Returns the current date in a <code>Date</code> object according to the formatMask specified in the input parameters. If a formatMask is not specified, this date object will be returned in a "yyyy-MM-dd" format.
     *
     * @param formatMask
     *        the format to apply to the current date object. If formatMask is null the date object will be formatted to "yyyy-MM-dd".
     * @return a <code>Date</code> object set to the current date in the format specified from the formatMask input parameter.
     * @throws ParseException
     *         the exception encountered when converting a SimpleDateFormat to a Date object
     */
    public static Date getCurrentDate(String formatMask) throws ParseException {
        myLogger.debug("Entering  getCurrentDate(String formatMask).");

        if(formatMask == null){
            formatMask = "yyyy-MM-dd";
        }

        DateFormat dateFormat = new SimpleDateFormat(formatMask);

        return dateFormat.parse(dateFormat.format(new Date()).toString());
    }

    /**
     * Formats and returns a date object using a format mask and returns the value in a String format. If a formatMask is not specified, this date object will be returned in a "MM/dd/yyyy hh:mm a" format.
     *
     * @param dt
     *        the <code>Date</code> object to format
     * @param formatMask
     *        The mask to be applied to the date object. If formatMask is null the date object will be formatted to "MM/dd/yyyy hh:mm a".
     * @return <code>String</code> the formatted date string representation of the date object input value in the format specified from the formatMask input parameter.
     */
    public static String toString(Date dt, String formatMask) {
        myLogger.debug("Entering toString(Date dt, String formatMask).");

        if(dt == null){
            return "null";
        }//end if

        if(formatMask == null){
            return toString(dt, "MM/dd/yyyy hh:mm a");
        }//end if

        SimpleDateFormat sdFormat = new SimpleDateFormat(formatMask);
        return sdFormat.format(dt);
    }

    /**
     * Returns the year integer value from a java.util.Date object.
     *
     * @param date
     *        The <code>Date</code> object to format
     * @return <code>int</code> - the year of the Date object represented as an int.
     */
    public static int getYear(Date date) {
        myLogger.debug("Entering  getYear(Date date).");

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        int year = gc.get(Calendar.YEAR);
        return year;
    }

    /**
     * Formats and returns a date string using format mask and returns the date in a sql date format. If a formatMask is not specified, this date object will be returned in a "yyyy-MM-dd" format.
     *
     * @param dateString
     *        The date string to be formatted.
     * @param formatMask
     *        the format to apply to the date string. If formatMask is null the date object will be formatted to "yyyy-MM-dd".
     * @return <code>java.sql.Date</code> - the formatted date in a sql date format. Returns a null date value if the date string input parameter is null.
     */
    public static java.sql.Date toSqlDate(String dateString, String formatMask) {
        myLogger.debug("Entering toSqlDate(String dateString, String formatMask).");

        if(dateString == null){
            return null;
        }

        if(formatMask == null){
            return toSqlDate(dateString, "yyyy-MM-dd");
        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

        java.util.Date date = new Date();

        try{
            date = sdf.parse(dateString);
        }catch(Exception exc){
            myLogger.error(" Error parsing date. Error msg: ", exc);
        }

        java.text.SimpleDateFormat sdfsql = new java.text.SimpleDateFormat(formatMask);

        return java.sql.Date.valueOf(sdfsql.format(date));
    }

    /**
     * Formats a date string from mmddyyyy to yyyymmdd using <code>SimpleDateFormat</code>. If the date string for the input parameter is not present the formatted date will default to "1900-01-01".
     *
     * @param oldDate
     *        The date string to be formatted.
     * @return <code>String</code> - the formatted date in a yyyymmdd format.
     */
    public static String mmddyyyyToyyyymmdd(String oldDate) {
        myLogger.debug("Entering mmddyyyyToyyyymmdd( String oldDate ) .");

        String newDate = DEFAULT_DATE;

        SimpleDateFormat mmddyyyy = new SimpleDateFormat();
        SimpleDateFormat yyyymmdd = new SimpleDateFormat();

        mmddyyyy.applyPattern(MMDDYYYY_PATTERN);
        yyyymmdd.applyPattern(YYYYMMDD_PATTERN);

        try{
            newDate = yyyymmdd.format(mmddyyyy.parse(oldDate));
        }catch(ParseException e){
            myLogger.error(" Error formatting date. Error msg: ", e);
        }catch(NullPointerException ne){
            myLogger.error(" Null pointer exception formatting date. Error msg: ", ne);
        }

        return newDate;

    }

    /**
     * <p>
     * This method will return the system timestamp as a custom formatted string
     * </p>
     * <b>Pattern: </b><code>[MM/dd/yyyy hh:mm:ss.SSS]</code>
     *
     * @return date - custom formatted system timestamp string
     */
    public static String getSystemTimestampString() {
        SimpleDateFormat sd = new SimpleDateFormat("[MM/dd/yyyy hh:mm:ss.SSS]");
        String date = "";
        try{
            Date dt = new Date();
            date = sd.format(dt);
        }catch(Exception e){
            myLogger.error("Exception getting system timestamp as a string: " + e.getMessage());
        }
        return date;
    }





}

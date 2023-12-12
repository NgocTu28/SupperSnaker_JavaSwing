/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author manhnt
 */
public class XDate2 {
    private static final String DEFAULT_DATE_PATTERN = "dd-MM-yyyy";

    // Convert java.sql.Date to java.util.Date
    public static java.util.Date convertSqlDateToUtilDate(java.sql.Date sqlDate) {
        return new java.util.Date(sqlDate.getTime());
    }
     public static java.sql.Date convertUtilDateToSqlDate(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }
    // Convert String to Date
    public static java.util.Date convertStringToDate(String dateString, String pattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(dateString);
    }

    // Convert Date to String
    public static String convertDateToString(java.util.Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    // Get current Date
    public static java.util.Date getCurrentDate() {
        return new java.util.Date();
    }

    // Convert Date to String with default pattern
    public static String convertDateToString(java.util.Date date) {
        return convertDateToString(date, DEFAULT_DATE_PATTERN);
    }

    public static void main(String[] args) {
        // Example usage
        java.sql.Date sqlDate = java.sql.Date.valueOf("2023-11-16");
        java.util.Date utilDate = convertSqlDateToUtilDate(sqlDate);
        System.out.println("Converted SQL Date to Util Date: " + utilDate);

        String dateString = "2023-11-16 12:30:00";
        try {
           java.util.Date dateFromString = convertStringToDate(dateString, DEFAULT_DATE_PATTERN);
            System.out.println("Converted String to Date: " + dateFromString);

            String dateToString = convertDateToString(dateFromString);
            System.out.println("Converted Date to String: " + dateToString);

            java.util.Date currentDate = getCurrentDate();
            System.out.println("Current Date: " + currentDate);

        } catch (ParseException e) {
        }
    }
}

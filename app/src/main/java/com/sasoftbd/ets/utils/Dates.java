package com.sasoftbd.ets.utils;

import org.threeten.bp.Duration;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Dates {

    public static long getDaysBetween(String startDate, String endDate) {
        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Parse the input dates using the formatter
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        // Calculate the difference in days
        return ChronoUnit.DAYS.between(start, end);
    }

    public static long getDaysBetweenWithOutZero(String startDate, String endDate) {
        // Define the date format to handle single-digit day and month
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        // Parse the input dates using the formatter
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        // Calculate the difference in days
        return ChronoUnit.DAYS.between(start, end);
    }

    public static String getMonthNameShort(int monthInt) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        // Ensure the monthInt is valid (between 1 and 12)
        if (monthInt >= 1 && monthInt <= 12) {
            return months[monthInt - 1]; // Index is monthInt - 1 because array index starts from 0
        } else {
            return "Invalid month"; // Return this if the input integer is not a valid month
        }
    }

    public static String getMonthNameFull(int monthInt) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        // Ensure the monthInt is valid (between 1 and 12)
        if (monthInt >= 1 && monthInt <= 12) {
            return months[monthInt - 1]; // Index is monthInt - 1 because array index starts from 0
        } else {
            return "Invalid month"; // Return this if the input integer is not a valid month
        }
    }


    public int getMonthIntID(String month) {
        String[] mon = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        for (int i = 0; i < mon.length; i++) {
            if (mon[i].equalsIgnoreCase(month)) {
                return i + 1; // Return month number (index + 1)
            }
        }

        // Return -1 if the input month is invalid
        return -1;
    }


    public static List<LocalDate> getDatesBetween(String fromDate, String toDate) {
        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Parse the start and end date strings
        LocalDate startDate = LocalDate.parse(fromDate, formatter);
        LocalDate endDate = LocalDate.parse(toDate, formatter);

        // Create a list to hold the dates
        List<LocalDate> dates = new ArrayList<>();

        // Loop through each day between startDate and endDate, inclusive
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        for (long i = 0; i <= numOfDaysBetween; i++) {
            dates.add(startDate.plusDays(i));
        }

        return dates;
    }


    public static String firstDateOfThisMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String firstDayOfMonth = dateFormat.format(calendar.getTime());
        System.out.println("First day of this month: " + firstDayOfMonth);
        return firstDayOfMonth;
    }

    public static String lastDateOfThisMonth(){

        // Get an instance of Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String lastDayOfMonth = dateFormat.format(calendar.getTime());

        return lastDayOfMonth;
    }

    public static String getCurrentTime() {
        // Create a SimpleDateFormat instance with the desired time format
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        // Get the current time from the Calendar
        Calendar calendar = Calendar.getInstance();
        // Format the current time into the desired format
        return timeFormat.format(calendar.getTime());
    }


    public static String convertMillisecondsToMinutesAndSeconds(long milliseconds) {
        // Convert to minutes
        long minutes = milliseconds / (1000 * 60);
        // Get the remaining milliseconds after extracting minutes
        long remainingMilliseconds = milliseconds % (1000 * 60);
        // Convert remaining milliseconds to seconds
        long seconds = remainingMilliseconds / 1000;

        return minutes + " minutes, " + seconds + " seconds";
    }


    // Radius of the Earth in kilometers
    private static final double EARTH_RADIUS = 6371;

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // Result in kilometers
    }

    public static double convertKmToMeters(double kilometers) {
        return kilometers * 1000; // Convert km to meters
    }


//    // Method to calculate the time difference
//    public static String calculateTimeDifference(String startTime, String endTime) {
//        // Define a formatter to handle the 12-hour format with AM/PM
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
//
//        // Parse the input time strings into LocalTime objects using the formatter
//        LocalTime start = LocalTime.parse(startTime, formatter);
//        LocalTime end = LocalTime.parse(endTime, formatter);
//
//        // Calculate the duration between the two times
//        Duration duration = Duration.between(start, end);
//
//        // Extract hours, minutes, and seconds from the duration
//        long hours = duration.toHours();
//        long minutes = duration.toMinutes() % 60; // Get remaining minutes after extracting hours
//        long seconds = duration.getSeconds() % 60; // Get remaining seconds after extracting minutes
//
//        // Return the time difference as a formatted string
//        return hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
//    }

    // Method to calculate the time difference
    public static String calculateTimeDifference(String startTime, String endTime) {

        // Define a formatter to handle the 12-hour format with AM/PM
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        // Parse the input time strings into LocalTime objects using the formatter
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = LocalTime.parse(endTime, formatter);
        // If the end time is before or equals to the start time, assume it is on the next day
        if (end.isBefore(start) || end.equals(start)) {
            end = end.plusHours(24);
        }
        // Calculate the duration between the two times
        Duration duration = Duration.between(start, end);
        // Extract hours, minutes, and seconds from the duration
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60; // Get remaining minutes after extracting hours
        long seconds = duration.getSeconds() % 60; // Get remaining seconds after extracting minutes
        // Return the time difference as a formatted string
        return hours + " hours, " + minutes + " minutes, " + seconds + " seconds";

    }


    public static String getCurrentTime10PM() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(new Date());
    }

}

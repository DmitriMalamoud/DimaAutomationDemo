package org.testinfra;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class Logger {

    private static void baseLog(String message, String separator){
        System.out.printf("%s --- %s%n", getCurrentTimestamp(), message);
        System.out.println(separator);
    }

    public static void log(String message){
        String separator = String.join("", Collections.nCopies(12, "_-")) + "\n";
        separator += separator;
        baseLog(message, separator);
    }

    public static void fail(String message){
        String separator = String.join("", Collections.nCopies(5, "-FAIL")) + "\n";
        baseLog("FAILURE: " + message, separator);
    }

    private static String getCurrentTimestamp(){
        String format = "dd/MM/yyyy hh:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedPattern(format);
        return LocalDateTime.now().format(dateTimeFormatter);
    }
}

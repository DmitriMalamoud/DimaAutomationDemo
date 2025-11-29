package org.testinfra;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class Logger {

    public static void log(String text){
        String format = "dd/MM/yyyy hh:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedPattern(format);
        String timestamp = LocalDateTime.now().format(dateTimeFormatter);
        System.out.printf("%s --- %s%n", timestamp, text);
        final String separator = String.join("", Collections.nCopies(10, "_-"));
        System.out.printf(MessageFormat.format("{0}}%n{0}%n", separator));
    }
}

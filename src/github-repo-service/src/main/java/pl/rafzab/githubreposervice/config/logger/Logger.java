package pl.rafzab.githubreposervice.config.logger;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class Logger {

    public static void info(String message, Object... args) {
        log.info(
                templateLogMessage(message), args
        );
    }

    public static void debug(String message, Object... args) {
        log.debug(
                templateLogMessage(message), args
        );
    }

    public static void warn(String message, Object... args) {

        log.warn(
                templateLogMessage(message), args
        );
    }

    public static void error(String message, Object... args) {
        log.error(
                templateLogMessage(message), args
        );
    }

    private static String templateLogMessage(String message) {
        StackTraceElement caller = getCaller();
        String fileName = caller.getFileName();
        String methodName = caller.getMethodName();
        String line = String.valueOf(caller.getLineNumber());
        return String.format("%s [%s:%s] %s", fileName, methodName, line, message);
    }

    private static StackTraceElement getCaller() {
        return Arrays.stream(Thread.currentThread().getStackTrace())
                .filter(element -> !element.getClassName().equals(Thread.class.getName()))
                .filter(element -> !element.getClassName().contains(Logger.class.getName()))
                .findFirst()
                .orElse(null);
    }
}

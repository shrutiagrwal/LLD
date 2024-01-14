package practice;

abstract class LogProcessor {
    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;
    LogProcessor nextLogProcessor;

    public LogProcessor(LogProcessor logProcessor) {
        this.nextLogProcessor = logProcessor;
    }

    public void log(int logLevel, String message) {
        if (nextLogProcessor != null)
            nextLogProcessor.log(logLevel, message);
    }
}

class InfoLogger extends LogProcessor {
    public InfoLogger(LogProcessor logProcessor) {
        super(logProcessor);
    }

    public void log(int logLevel, String message) {
        if (logLevel == LogProcessor.INFO)
            System.out.println(message);
        else {
            super.log(logLevel, message);
        }
    }
}

class Debuglogger extends LogProcessor {
    public Debuglogger(LogProcessor nextLogProcessor) {
        super(nextLogProcessor);
    }

    public void log(int logLevel, String message) {
        if (logLevel == LogProcessor.DEBUG)
            System.out.println(message);
        else
            super.log(logLevel, message);
    }
}

class ErrorLogger extends LogProcessor {
    ErrorLogger(LogProcessor nextLogProcessor) {
        super(nextLogProcessor);
    }

    public void log(int loglevel, String message) {
        if (loglevel == LogProcessor.ERROR)
            System.out.println(message);
        super.log(loglevel, message);
    }
}

public class Logger{
    public static void main(String args[])
    {
        LogProcessor logObject=new InfoLogger(new ErrorLogger(new Debuglogger(null)));

        logObject.log(LogProcessor.INFO,"info log");
        logObject.log(LogProcessor.DEBUG,"debug log");
        logObject.log(LogProcessor.ERROR,"error log");
    }
}

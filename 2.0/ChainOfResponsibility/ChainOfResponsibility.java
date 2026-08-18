package designPatterns;

abstract class LogProcessor{
    int level;
    LogProcessor nextLogProcessor;
    public static final int DEBUG = 1;
    public static final int INFO = 2;
    public static final int ERROR = 3;
    public static final int FATAL = 4;
    LogProcessor(int level, LogProcessor logProcessor){
        this.level= level;
        this.nextLogProcessor= logProcessor;
    }

    public void log(String message, int level){
        if(this.level == level) {
            write(message);
            return;
        }
        if(this.nextLogProcessor!=null)
                this.nextLogProcessor.log(message, level);


    }
    public abstract void write(String message);
}

class DebugLog extends LogProcessor{

    DebugLog(int level, LogProcessor logProcessor) {
        super(level, logProcessor);
    }

    @Override
    public void write(String message) {
        System.out.println("DEBUG: "+ message);
    }
}

class InfoLog extends LogProcessor{
    InfoLog(int level, LogProcessor logProcessor) {
        super(level, logProcessor);
    }

    @Override
    public void write(String message) {
        System.out.println("INFO:" + message);
    }
}

class ErrorLog extends LogProcessor{

    ErrorLog(int level, LogProcessor logProcessor) {
        super(level, logProcessor);
    }

    @Override
    public void write(String message) {
        System.out.println("ERROR: "+ message);
    }
}

public class ChainOfResponsibility {
    private static LogProcessor chainOfProcessors(){
        LogProcessor errorLog= new ErrorLog(LogProcessor.ERROR,null);
        LogProcessor infoLog = new InfoLog(LogProcessor.INFO, errorLog);
        LogProcessor debugLog = new DebugLog(LogProcessor.DEBUG, infoLog);
        return debugLog;
    }
    static void main(String[] args) {
        LogProcessor chainOfLogProcessor = chainOfProcessors();
        chainOfLogProcessor.log("test error log", LogProcessor.ERROR);
        chainOfLogProcessor.log("debug log", LogProcessor.DEBUG);
        chainOfLogProcessor.log("info log", LogProcessor.INFO);
    }
}

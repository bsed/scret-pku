package easyJ.common;

public class EasyJException extends Exception {
    private String location;

    private String logMessage;

    private Exception originalException;

    private String clientMessage;

    public EasyJException() {}

    public EasyJException(Exception oe, String location, String logMessage) {
        this.location = location;
        this.originalException = oe;
        this.logMessage = logMessage;
    }

    public EasyJException(Exception oe, String location, String logMessage,
            String clientMessage) {
        this.location = location;
        this.originalException = oe;
        this.logMessage = logMessage;
        this.clientMessage = clientMessage;
    }
    public String toString(){
    	return location+"  "+logMessage+" "+clientMessage;
    }

    public String getLocation() {
        return location;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public Exception getOriginalException() {
        return originalException;
    }

    public String getClientMessage() {
        return clientMessage;
    }

}

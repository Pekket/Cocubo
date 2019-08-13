package be.pekket.cocubo.exception;

public class ConnectorException extends Exception {

    public ConnectorException( final String message ) {
        super(message);
    }

    public ConnectorException( final String message, final Exception exception ) {
        super(message, exception);
    }
}

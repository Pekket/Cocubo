package be.pekket.cocubo.exception;

public class CocuboException extends Exception {

    public CocuboException( final String message ) {
        super(message);
    }

    public CocuboException( final String message, final Exception exception ) {
        super(message, exception);
    }
}

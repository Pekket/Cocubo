package be.pekket.cocubo.exception;

public class ImageProcessingException extends Exception {

    public ImageProcessingException( final String message ) {
        super(message);
    }

    public ImageProcessingException( final String message, final Exception exception ) {
        super(message, exception);
    }
}
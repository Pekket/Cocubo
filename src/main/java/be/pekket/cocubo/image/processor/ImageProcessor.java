package be.pekket.cocubo.image.processor;

import be.pekket.cocubo.exception.ImageProcessingException;

import java.util.List;

public interface ImageProcessor {

    List<String> process( String imagPath ) throws ImageProcessingException;
}

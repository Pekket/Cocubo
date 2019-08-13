package be.pekket.cocubo.image.processor.impl;

import be.pekket.cocubo.exception.ImageProcessingException;
import be.pekket.cocubo.image.processor.ImageProcessor;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static be.pekket.cocubo.image.constant.ImageConstant.*;

@Service
public class DefaultImageProcessorImpl implements ImageProcessor {

    private Tesseract tesseract;

    @Override
    public List<String> process( String imagePath ) throws ImageProcessingException {
        List<String> result = new LinkedList<>();

        if ( StringUtils.isEmpty(imagePath) )
            throw new ImageProcessingException("Image path cannot be empty");

        try {
            File imageFile = new File(imagePath);
            splitIntoSubImages(imageFile);

            for ( int i = 0; i < DAYS_TO_PROCESS; i++ ) {
                File subImage = new File(IMAGE_PREFIX + i + IMAGE_SUFFIX);
                String processedImageText = readTextFromImage(subImage);
                result.add(StringUtils.trimTrailingWhitespace(processedImageText));
            }
        } catch ( IOException e ) {
            throw new ImageProcessingException("Error trying to process image " + e.getMessage());
        }

        return result;
    }

    private void splitIntoSubImages( File image ) throws IOException {
        final BufferedImage bufferedImage = ImageIO.read(image);

        for ( int i = 0; i < DAY_COORD.length; i++ ) {
            int x = Integer.parseInt(DAY_COORD[i].split(",")[0]);
            int x2 = Integer.parseInt(DAY_COORD[i].split(",")[1]);
            BufferedImage subImage = bufferedImage.getSubimage(x, DEFAULT_Y_COORD, x2 - x, HEIGHT);
            ImageIO.write(subImage, "jpg", new File(IMAGE_PREFIX + i + IMAGE_SUFFIX));
        }
    }

    private String readTextFromImage( File imageFile ) throws ImageProcessingException {

        String result;
        try {
            result = getTesseractInstance().doOCR(imageFile);
        } catch ( TesseractException e ) {
            throw new ImageProcessingException("Error trying to convert image to text " + e.getMessage());
        }

        return result;
    }

    private Tesseract getTesseractInstance() {
        if ( this.tesseract == null ) {
            this.tesseract = new Tesseract();
            //this.tesseract.setDatapath("/usr/local/Cellar/tesseract/4.1.0/share/tessdata");
            this.tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata");
            this.tesseract.setLanguage("nld");
        }
        return this.tesseract;
    }
}

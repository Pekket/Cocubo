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

import static be.pekket.cocubo.constant.CocuboConstant.DATA_PATH;
import static be.pekket.cocubo.constant.CocuboConstant.MENU_DELIMITER;
import static be.pekket.cocubo.image.constant.ImageConstant.*;

@Service
public class DefaultImageProcessorImpl implements ImageProcessor {

    private static final String IGNORE_WORLD_CORNER_TEXT = "corner";
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
                StringBuilder dayMenu = new StringBuilder();
                for ( String courseName : COURSE_NAMES ) {
                    File subImage = new File(DATA_PATH + IMAGE_PREFIX + i + "_" + courseName + IMAGE_SUFFIX);
                    String processedText = StringUtils.trimWhitespace(readTextFromImage(subImage));

                    processedText = processedText.toLowerCase().contains(IGNORE_WORLD_CORNER_TEXT) ? "/" : processedText;
                    dayMenu.append(processedText).append(MENU_DELIMITER);
                }
                result.add(StringUtils.trimTrailingWhitespace(dayMenu.toString()));
            }
        } catch ( IOException e ) {
            throw new ImageProcessingException("Error trying to process image " + e.getMessage());
        }

        return result;
    }

    private void splitIntoSubImages( File image ) throws IOException {
        final BufferedImage bufferedImage = ImageIO.read(image);

        for ( int i = 0; i < DAYS_TO_PROCESS; i++ ) {
            int x = Integer.parseInt(DAY_COORD[i].split(",")[0]);
            int x2 = Integer.parseInt(DAY_COORD[i].split(",")[1]);

            for ( int j = 0; j < COURSE_NAMES.length; j++ ) {
                BufferedImage subImage = bufferedImage.getSubimage(x, Y_COORD[j], x2 - x, HEIGHTS[j]);
                ImageIO.write(subImage, "jpg", new File(DATA_PATH + IMAGE_PREFIX + i + "_" + COURSE_NAMES[j] + IMAGE_SUFFIX));
            }
        }
    }

    private String readTextFromImage( File imageFile ) throws ImageProcessingException {
        String result = "";

        if ( imageFile != null ) {
            try {
                result = getTesseractInstance().doOCR(imageFile);
                result = StringUtils.replace(result, "\n", " - ");
            } catch ( TesseractException e ) {
                throw new ImageProcessingException("Error trying to convert image to text " + e.getMessage());
            }
        }
        return StringUtils.isEmpty(result) ? "" : result.substring(0, result.length() - 3);
    }

    private Tesseract getTesseractInstance() {
        if ( this.tesseract == null ) {
            this.tesseract = new Tesseract();

            //Local
//            this.tesseract.setDatapath("/usr/local/Cellar/tesseract/4.1.0/share/tessdata");

            //Remote
            this.tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata");
            this.tesseract.setLanguage("nld");
        }
        return this.tesseract;
    }
}

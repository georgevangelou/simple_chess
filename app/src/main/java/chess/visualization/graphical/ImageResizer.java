package chess.visualization.graphical;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author George Evangelou
 * @author www.codejava.net
 * <p>
 * Created on: 2021-12-29
 * <p>
 * This program demonstrates how to resize an image.
 */
public class ImageResizer {

    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     *
     * @param inputImagePath Path of the original image
     * @param scaledWidth    absolute width in pixels
     * @param scaledHeight   absolute height in pixels
     * @return
     * @throws IOException
     */
    public BufferedImage resize(
            String inputImagePath,
            int scaledWidth,
            int scaledHeight
    ) throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        return outputImage;
    }

    /**
     * Resizes an image by a percentage of original size (proportional).
     *
     * @param inputImagePath Path of the original image
     * @param percent        a double number specifies percentage of the output image
     *                       over the input image.
     * @throws IOException
     */
    public void resize(String inputImagePath, double percent) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, scaledWidth, scaledHeight);
    }
}
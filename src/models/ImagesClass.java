package models;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by volkswagen1 on 06.04.2017.
 */
public class ImagesClass {
    private BufferedImage image1;
    private BufferedImage image2;

    public void putImage(File file, int imgNumber) {
        try {
            if (imgNumber == 1) {
                image1 = ImageIO.read(file);
            } else {
                image2 = ImageIO.read(file);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public BufferedImage getFirstImage() {
        return image1;
    }

    public BufferedImage getSecondImage() {
        return image2;
    }

    public boolean isNotNull() {
        return image1 != null && image2 != null;
    }
}

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * This interface represents a drawable object.
 * It includes methods for loading images and drawing the object.
 */
public interface IDrawable {

    /**
     * The path to the directory where images are stored.
     */
//    final static String PICTURE_PATH = ""; //todo upload image ang get a path

    /**
     * Loads images for the drawable object.
     *
     * @param imageName the name of the image file to load.
     */
    void loadImage(String imageName);

    /**
     * Draws the object using the provided Graphics context.
     *
     * @param g the Graphics context to use for drawing the object.
     */
    void drawObject(Graphics g);

    BufferedImage getImage();

    void setImage(BufferedImage image);

    TilePanel getPanel();

}


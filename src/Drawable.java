import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Drawable implements IDrawable{

    private BufferedImage image;
    private int height;
    private int width;
    private TilePanel panel;
    private Point location;

    public Drawable(String path, int width, int height, TilePanel panel, Point location) {
        loadImage(path);
        this.height = height;
        this.width = width;
        this.panel = panel;
        this.location = location;
    }

    public void setPanel(TilePanel panel) {
        this.panel = panel;
    }

    /**
     * Loads the image for the object based on the given image name.
     * If the image cannot be loaded, it prints an error message.
     *
     * @param imageName the name of the image file to load (excluding file extension)
     */
    public void loadImage(String imageName) {
        try {
            image = ImageIO.read(new File(PICTURE_PATH + imageName));
        }
        catch (IOException e) {
            System.out.println("Cannot load images for " + imageName); }
    }

    /**
     * Draws the object on the given graphics context.
     * The image will only be drawn if it has been loaded successfully.
     *
     * @param g the graphics context to draw on
     */
    public void drawObject (Graphics g) {
        if(image != null)
            g.drawImage(image, 1, 1, height, width, panel);
    }

    public void setSize(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;

    }

    public TilePanel getPanel() {
        return panel;
    }
}

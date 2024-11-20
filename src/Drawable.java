import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Drawable implements IDrawable{

    private BufferedImage image;
    private int size;
    private TilePanel panel;
//    private JPanel panel;//todo add panel

    public Drawable(String path, int size, TilePanel panel){
//        this.image = image;
        loadImage(path);
        this.size = size;
        this.panel = panel;
//        this.panel = panel;
    }

    /**
     * Loads the image for the object based on the given image name.
     * If the image cannot be loaded, it prints an error message.
     *
     * @param imageName the name of the image file to load (excluding file extension)
     */
    public void loadImage(String imageName) {
        try {
            image = ImageIO.read(new File("C:\\Users\\Student\\IdeaProjects\\Pack-man-game\\Ghost.png"));
//            image = ImageIO.read(new File(PICTURE_PATH + imageName));
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
        //todo implement
        if(image != null)
            g.drawImage(image, 2, 60, size, size, panel);
    }

    public void setSize(int size) {
        this.size = size;
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

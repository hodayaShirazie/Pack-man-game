import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Drawable implements IDrawable{

    private Image image;
    private int size;
    private JPanel panel;//todo add panel

    public Drawable(Image image, int size, JPanel panel){
        this.image = image;
        this.size = size;
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
        //todo implement
//        if(image != null)
//            g.drawImage(image, super.getLocationX(), super.getLocationY(), size, size, panel);
    }

    public void setSize(int size) {
        this.size = size;
    }


}

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import static java.lang.Thread.sleep;


public class Ghost implements IDrawable, Runnable, Cloneable, ILocatable, IMovable {

    private IDrawable drawable;
    private ILocatable locatable;
    private IMovable movable;
    private Frame frame;

    public Ghost(TilePanel panel, Point point, Frame frame) {
        locatable = new Locatable(point);
        movable = new Movable(locatable.getLocation());
        drawable = new Drawable("Ghost.png", 17, 28, panel, locatable.getLocation());
        this.frame = frame;
    }

    public void drawObject(Graphics g) {
        drawable.drawObject(g);
    }

    public void loadImage(String path) {
        drawable.loadImage(path);
    }

    protected Object clone()  {
        return new Ghost(this.getPanel(), this.getLocation(), this.frame);
    }

    public BufferedImage changeColor() {

        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        Color newColor = new Color(red, green, blue, 255);

        float alpha = 0.5f;

        BufferedImage coloredImage = new BufferedImage(
                drawable.getImage().getWidth(),
                drawable.getImage().getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        for (int x = 0; x < drawable.getImage().getWidth(); x++) {
            for (int y = 0; y < drawable.getImage().getHeight(); y++) {
                int pixel = drawable.getImage().getRGB(x, y);
                if ((pixel >> 24) != 0x00) {
                    Color originalColor = new Color(pixel, true);

                    red = (int) (alpha * newColor.getRed() + (1 - alpha) * originalColor.getRed());
                    green = (int) (alpha * newColor.getGreen() + (1 - alpha) * originalColor.getGreen());
                    blue = (int) (alpha * newColor.getBlue() + (1 - alpha) * originalColor.getBlue());
                    int alphaValue = originalColor.getAlpha();

                    int blendedColor = new Color(red, green, blue, alphaValue).getRGB();
                    coloredImage.setRGB(x, y, blendedColor);
                }
            }
        }
        return coloredImage;
    }

    public BufferedImage changeColorToWhite() {

        Random random = new Random();
        int red = 255;
        int green = 255;
        int blue = 255;

        Color white = new Color(red, green, blue, 255);

        BufferedImage coloredImage = new BufferedImage(
                drawable.getImage().getWidth(),
                drawable.getImage().getHeight(),
                BufferedImage.TYPE_INT_ARGB // תמיכה בשקיפות
        );

        for (int x = 0; x < drawable.getImage().getWidth(); x++) {
            for (int y = 0; y < drawable.getImage().getHeight(); y++) {
                int pixel = drawable.getImage().getRGB(x, y);
                if ((pixel >> 24) != 0x00) { // רק פיקסלים שאינם שקופים
                    coloredImage.setRGB(x, y, white.getRGB());
                }
            }
        }
        return coloredImage;

    }

    public BufferedImage getImage() {
        return drawable.getImage();
    }

    public IDrawable getDrawable() {
        return drawable;
    }

    public void setImage(BufferedImage image) {
        drawable.setImage(image);
    }

    public TilePanel getPanel() {
        return drawable.getPanel();
    }

    public void run() {

        Random random = new Random();
        int timeToLive = random.nextInt(7000, 15000);
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeToLive) {

            this.getDrawable().getPanel().moveGhost(this.frame);

            try {
                sleep(400);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        getPanel().setIsGhost(false);
        getPanel().setGhost(null);
        getPanel().repaint();

        frame.decreaseGhost();
    }

    private void updatePosition(int dx, int dy) {
        TilePanel currentPanel = getPanel();
    }

    public Point getLocation() {
        return locatable.getLocation();
    }

    public void setLocation(Point point) {
        locatable.setLocation(point);

    }

    public void move(Point destination) {
        movable.move(destination);
    }

    public void setPanel(TilePanel panel) {
        drawable.setPanel(panel);
    }
}

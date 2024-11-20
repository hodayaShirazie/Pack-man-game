import java.awt.*;

public class Dot implements ILocatable{

    private final ILocatable position;

    public Dot(Point point) {
        position = new Locatable(point);
    }

    public void draw(Graphics g, int width, int height) {
        g.setColor(Color.YELLOW);
        int dotSize = width / 4;
        g.fillOval(width / 2 - dotSize / 2, height / 2 - dotSize / 2, dotSize, dotSize);
    }

    public Point getLocation(){
        return position.getLocation();
    }
    public void setLocation(Point location){
        position.setLocation(location);
    }
}
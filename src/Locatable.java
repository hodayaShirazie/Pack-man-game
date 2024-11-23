import java.awt.*;

public class Locatable implements ILocatable{

    Point point;

    public Locatable(Point location) {
        point = location;

    }

    public Point getLocation(){
        return point;
    }

    public void setLocation(Point point) {
        this.point.x = point.x;
        this.point.y = point.y;
    }
}

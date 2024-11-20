import java.awt.*;

public class Movable implements IMovable {

    private Point location;

    public Movable(Point location) {
        this.location = location;
    }

    public void move(Point destination){
        location.setLocation(destination);
    }
}

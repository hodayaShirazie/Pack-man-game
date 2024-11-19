import java.awt.*;

public class Dot {
    private final int x;
    private final int y;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g, int width, int height) {
        g.setColor(Color.YELLOW);
        int dotSize = width / 4;
        g.fillOval(width / 2 - dotSize / 2, height / 2 - dotSize / 2, dotSize, dotSize);
    }
}
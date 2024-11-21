import javax.swing.*;
import java.awt.*;

/**
 * Represents a single tile in the Pac-Man game.
 * Each tile can contain a player, a dot, or be a specific type (e.g., wall, path).
 */
public class TilePanel extends JPanel{

    private boolean isPlayer;
    private Dot dot;
    private final int tileType;
    private String direction; // "UP", "DOWN", "LEFT", "RIGHT"
    private boolean isGhost;
    private Ghost ghost;



    public void setGhost(Ghost ghost) {
        this.isGhost = true;
        repaint();  // יצייר מחדש את ה-Panel כדי להציג את רוח הרפאים
    }

    public TilePanel(int tileType, Dot dot) {
        this.tileType = tileType;
        this.dot = dot;
        this.isPlayer = false;
        this.isGhost = false;
        this.direction = "RIGHT";

//        Thread ghostThread = new Thread(ghost);
//        ghostThread.start();
    }

    public void setPlayer(boolean isPlayer, String direction) {//todo maybe add a field isAGhost
        this.isPlayer = isPlayer;
        this.direction = direction; // Update player's direction
        repaint();
    }

    public void setGhost(boolean isGhost, Point point) {
        ghost = new Ghost(this, point);
        ghost.setImage(ghost.changeColor());
        this.isGhost = isGhost;
        repaint();
    }

    public boolean hasDot() {
        return dot != null;
    }

    public void removeDot() {
        dot = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

//        ghost.drawObject(g);

        if (tileType == 1) {
            setBackground(Color.BLUE); // Wall
        } else {
            setBackground(Color.BLACK); // Path
        }

        if (dot != null) {
            dot.draw(g, getWidth(), getHeight());
        }

        if (isGhost) {
            ghost.drawObject(g);
        }

//            if (isPlayer) {
//                Graphics2D g2d = (Graphics2D) g;
//                g2d.setColor(Color.YELLOW); // Pac-Man color
//
//                // Draw a circular arc representing Pac-Man
//                // Parameters: x, y, width, height, startAngle, arcAngle
//                int size = Math.min(getWidth(), getHeight()) - 10; // Keep it proportional to the tile
//                int x = (getWidth() - size) / 2;
//                int y = (getHeight() - size) / 2;
//                g2d.fillArc(x, y, size, size, 30, 300); // 30 to 330 degrees creates the "mouth" shape
//            }

        if (isPlayer) {
            g.setColor(Color.YELLOW);
            int x = 5, y = 5, width = getWidth() - 10, height = getHeight() - 10;

            int startAngle = 0;
            int arcAngle = 270;

            switch (direction) {
                case "UP" -> startAngle = 135; // Facing up
                case "DOWN" -> startAngle = 315; // Facing down
                case "LEFT" -> startAngle = 225; // Facing left
                case "RIGHT" -> startAngle = 45; // Facing right
            }

            g.fillArc(x, y, width, height, startAngle, arcAngle);
        }
    }

}


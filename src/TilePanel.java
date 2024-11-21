import javax.swing.*;
import java.awt.*;
import java.util.Random;

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

    private Point positionInBoard;

    public Point getPositionInBoard() {
        return positionInBoard;
    }

    public TilePanel(int tileType, Dot dot, Point positionInBoard) {
        this.tileType = tileType;
        this.dot = dot;
        this.isPlayer = false;
        this.isGhost = false;
        this.direction = "RIGHT";
        this.positionInBoard = positionInBoard;

//        Thread ghostThread = new Thread(ghost);
//        ghostThread.start();
    }




    public void setPlayer(boolean isPlayer, String direction) {
        this.isPlayer = isPlayer;
        this.direction = direction; // Update player's direction
        repaint();
    }

    public void addGhost(boolean isGhost, Point point,Frame frame) {
        ghost = new Ghost(this, point, frame);
        ghost.setImage(ghost.changeColor());
        this.isGhost = isGhost;
//        System.out.println(ghost.getLocation().toString());
        repaint();
//        while (ghost.getLocation().x < board.length)
//            moveGhost(board, maze);

    }

    public void setGhost(Ghost ghost){
        if(ghost != null) {
            ghost.setPanel(this);
            this.isGhost = true;
        }
        this.ghost = ghost;
        repaint();

    }

    public void moveGhost(Frame frame) {

        int [][] maze = frame.getMaze();
        Random random = new Random();
        int nextStep = random.nextInt(1, 5);

        boolean foundDirection = false;


        while (!foundDirection) {
            switch (nextStep) {
                case 1:
                    if (frame.isLocationNotWall(positionInBoard.x + 1, positionInBoard.y)) {
                        TilePanel right = frame.getBoard()[positionInBoard.x + 1][positionInBoard.y];
                        frame.getBoard()[right.positionInBoard.x][right.positionInBoard.y].setGhost(this.getGhost());
                        frame.getBoard()[positionInBoard.x][positionInBoard.y].setIsGhost(false);
                        frame.getBoard()[positionInBoard.x][positionInBoard.y].ghost = null;
//                        ghost.move(right);
                        foundDirection = true;
                    }
                    break;
                case 2:
                    if (frame.isLocationNotWall(positionInBoard.x - 1, positionInBoard.y)){
                        TilePanel left = frame.getBoard()[positionInBoard.x - 1][positionInBoard.y];
                        frame.getBoard()[left.positionInBoard.x][left.positionInBoard.y].setGhost(this.getGhost());
                        frame.getBoard()[positionInBoard.x][positionInBoard.y].setIsGhost(false);
                        frame.getBoard()[positionInBoard.x][positionInBoard.y].ghost = null;

//                        ghost.move(left);
                        foundDirection = true;
                    }
                    break;
                case 3:
                    if (frame.isLocationNotWall(positionInBoard.x, positionInBoard.y + 1)) {
                        TilePanel up = frame.getBoard()[positionInBoard.x][ positionInBoard.y + 1];
                        frame.getBoard()[up.positionInBoard.x][up.positionInBoard.y].setGhost(this.getGhost());
                        frame.getBoard()[positionInBoard.x][positionInBoard.y].setIsGhost(false);
                        frame.getBoard()[positionInBoard.x][positionInBoard.y].ghost = null;


//                        ghost.move(up);
                        foundDirection = true;
                    }
                    break;
                case 4:
                    if (frame.isLocationNotWall(positionInBoard.x, positionInBoard.y -1)) {
                        TilePanel down = frame.getBoard()[positionInBoard.x][ positionInBoard.y - 1];
                        frame.getBoard()[down.positionInBoard.x][down.positionInBoard.y].setGhost(this.getGhost());
                        frame.getBoard()[positionInBoard.x][positionInBoard.y].setIsGhost(false);
                        frame.getBoard()[positionInBoard.x][positionInBoard.y].ghost = null;


//                        ghost.move(down);
                        foundDirection = true;
                    }
                    break;
            }

            if (!foundDirection) {
                nextStep = random.nextInt(1, 5);
            }
        }
        repaint();
        if(playerTouchedGhost()) {
            frame.updatePlayer();
        }
    }

    public void setIsGhost(boolean isGhost){
        this.isGhost = isGhost;
    }

    public boolean hasDot() {
        return dot != null;
    }

    public void removeDot() {
        dot = null;
        repaint();
    }

    public Ghost getGhost() {
        return ghost;
    }

    public boolean playerTouchedGhost(){
        return (isGhost && isPlayer);
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

        if (ghost != null) {
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


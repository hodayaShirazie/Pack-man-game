import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Panel extends JFrame  {

    private static final int TILE_SIZE = 50; // Tile size for better visibility
    private static final int BOARD_SIZE = 20; // Board size. IF IS SIZE CHANGED THEN MATRIX IN generateMaze() SHOULD BE CHANGED ACCORDINGLY
    private TilePanel[][] board;
    private int[][] maze;

    private int playerX = 1;
    private int playerY = 1;
    private int score = 0;

    public Panel() {
        board = new TilePanel[BOARD_SIZE][BOARD_SIZE];
        maze = new int[BOARD_SIZE][BOARD_SIZE];
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        generateMaze();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Dot dot = maze[i][j] == 2 ? new Dot(i, j) : null; //get maze[i][j] if equals to 2 and null else
                board[i][j] = new TilePanel(maze[i][j], dot);
                board[i][j].setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
                add(board[i][j]);
            }
        }

        board[playerX][playerY].setPlayer(true,"RIGHT"); // Mark initial Pac-Man position
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                movePlayer(e.getKeyCode());
            }
        });

        setTitle("Pac-Man-Game");
        setSize(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setFocusable(true);
    }

    private void generateMaze() {
        int[][] predefinedMaze = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
                {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1},
                {1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1},
                {1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1},
                {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        // Copy predefinedMaze to the maze variable, converting paths to dots
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (predefinedMaze[i][j] == 0) {
                    maze[i][j] = 2; // Change all paths to dots
                } else {
                    maze[i][j] = predefinedMaze[i][j];
                }
            }
        }
    }

    private void movePlayer(int keyCode) {
        int nextX = playerX;
        int nextY = playerY;
        String direction = "RIGHT"; // Default direction

        switch (keyCode) {
            case KeyEvent.VK_UP -> {
                nextX--;
                direction = "UP";
            }
            case KeyEvent.VK_DOWN -> {
                nextX++;
                direction = "DOWN";
            }
            case KeyEvent.VK_LEFT -> {
                nextY--;
                direction = "LEFT";
            }
            case KeyEvent.VK_RIGHT -> {
                nextY++;
                direction = "RIGHT";
            }
        }

        if (isValidMove(nextX, nextY)) {
            board[playerX][playerY].setPlayer(false, ""); // Clear previous position

            playerX = nextX;
            playerY = nextY;

            TilePanel currentTile = board[playerX][playerY];
            currentTile.setPlayer(true, direction); // Update with new position and direction

            if (currentTile.hasDot()) {
                score++;
                currentTile.removeDot();
                System.out.println("Score: " + score);
            }
        }
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && maze[x][y] != 1;
    }

    public static void main(String[] args) {
        new Panel();
    }





//BASIC FUNCTION TO GENERATE MAZE
//    private void generateRandomMaze() {
//        Random rand = new Random();
//        for (int i = 0; i < BOARD_SIZE; i++) {
//            for (int j = 0; j < BOARD_SIZE; j++) {
//                if (i == 0 || j == 0 || i == BOARD_SIZE - 1 || j == BOARD_SIZE - 1) {
//                    maze[i][j] = 1; // Border wall
//                } else if (rand.nextDouble() < 0.3) {
//                    maze[i][j] = 1; // Random wall
//                } else if (rand.nextDouble() < 0.1) {
//                    maze[i][j] = 2; // Dot
//                } else {
//                    maze[i][j] = 0; // Path
//                }
//            }
//        }
//        maze[playerX][playerY] = 0; // Ensure initial position is clear
//    }

}
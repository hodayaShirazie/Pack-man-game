import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

//1-wall, 0-path--(2 is the old 0)
//196 points, 980 worth

public class Frame extends JFrame {

    private static final int TILE_SIZE = 30; // Tile size for better visibility
    private static final int BOARD_SIZE = 20; // Board size. IF IS SIZE CHANGED THEN MATRIX IN generateMaze() SHOULD BE CHANGED ACCORDINGLY
    private TilePanel[][] board;
    private int[][] maze;
    private Player player;
    private int numOfGhosts;
    private static int maxScoreWorth;
    private static final int scoreWorth = 5;


    public Frame() {

        player = Player.getInstance();
        player.setLocation(new Point(1, 1));

        board = new TilePanel[BOARD_SIZE][BOARD_SIZE];


        maze = new int[BOARD_SIZE][BOARD_SIZE];

        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        generateMaze();

        maxScoreWorth = calcMaxScoreWorth();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Dot dot = null;

                if (maze[i][j] == 0)
                    dot = new Dot(new Point(i, j));

                board[i][j] = new TilePanel(maze[i][j], dot, new Point(i, j));
                board[i][j].setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
                add(board[i][j]);
            }
        }

        board[player.getLocation().x][player.getLocation().y].setPlayer(true, "RIGHT"); // Mark initial Pac-Man position
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                movePlayer(e.getKeyCode());
            }
        });

        setTitle("Pac-Man-Game | Lives: " + player.getLives());
        setSize(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        startGame();


    }

    private int calcMaxScoreWorth() {
        int sum = 0;
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                if (maze[i][j] == 0)
                    ++sum;
        System.out.println("sum = " + sum*scoreWorth);
        return sum*scoreWorth;
    }

    private void startGame() {
        addGhosts(3);
        setVisible(true);
        setFocusable(true);
    }

    private void generateMaze() {
        int[][] predefinedMaze = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1},
                {1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1},
                {1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        // Copy predefinedMaze to the maze variable, converting paths to dots
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                maze[i][j] = predefinedMaze[i][j];

    }

    private void movePlayer(int keyCode) {

        int nextX = player.getLocation().x;
        int nextY = player.getLocation().y;
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

        if (isLocationNotWall(nextX, nextY)) {
            board[player.getLocation().x][player.getLocation().y].setPlayer(false, ""); // Clear previous position

            player.move(new Point(nextX, nextY));

            TilePanel currentTile = board[player.getLocation().x][player.getLocation().y];
            currentTile.setPlayer(true, direction); // Update with new position and direction

            if (currentTile.hasDot()) {
                player.incrementScore();
                currentTile.removeDot();
                isPlayerWon();
                System.out.println("Score: " + player.getScore());
            }

            if(currentTile.playerTouchedGhost())
                updatePlayer();
        }
    }

    private void isPlayerWon() {

        if(player.getScore() == maxScoreWorth) {
            System.out.println("player won !! congrats !!!!");
            declareWining();

        }
    }

    private void declareWining() {
        JFrame frame = new JFrame("Game Over");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null); // Center the window

        // Create a panel with a vertical layout for components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Add congratulatory text
        JLabel text = new JLabel("Congratulations! You won with " + maxScoreWorth + " points.", JLabel.CENTER);
        text.setFont(new Font("Arial", Font.PLAIN, 14));

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        JButton leaveButton = new JButton("End Game");
        JButton playAgainButton = new JButton("Play Again");
        buttonPanel.add(leaveButton);
        buttonPanel.add(playAgainButton);

        // Add components to the main panel
        mainPanel.add(text, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        frame.add(mainPanel);
        frame.setVisible(true);

        // Leave button functionality
        leaveButton.addActionListener(e -> System.exit(0)); // Exit the program

        // Play again button functionality
        playAgainButton.addActionListener(e -> {
            resetGame();
            System.out.println("Play Again was clicked");
            frame.dispose(); // Close the current frame
            // Logic to restart the game goes here
        });
    }

    private void resetGame() {
        player.resetPlayer();

        dispose();
        new Frame();

    }

    public boolean isLocationNotWall(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && maze[x][y] != 1;
    }

    private void addGhosts() {
        Random rand = new Random();
        int ghostsAmount = rand.nextInt(1, 6), i = 0;
        Point point;
        do {
            point = new Point(rand.nextInt(BOARD_SIZE), rand.nextInt(BOARD_SIZE));
            if (isLocationNotWall(point.x, point.y)) {
                board[point.x][point.y].addGhost(true, point,this);
                i++;
                Thread thread = new Thread(board[point.x][point.y].getGhost());
                thread.start();
            }


        }
        while (i < ghostsAmount);
    }

    private void addGhosts(int num) {
        Random rand = new Random();
        int ghostsAmount = num, i = 0;
        Point point;
        do {
            point = new Point(rand.nextInt(BOARD_SIZE), rand.nextInt(BOARD_SIZE));
            if (isLocationNotWall(point.x, point.y)) {
                board[point.x][point.y].addGhost(true, point,this);
                i++;
                Thread thread = new Thread(board[point.x][point.y].getGhost());
                thread.start();
                increaseGhost();
            }


        }
        while (i < ghostsAmount);
    }

    public int[][] getMaze() {
        return maze;
    }

    public Player getPlayer() {
        return player;
    }

    public TilePanel[][] getBoard() {
        return board;
    }

    public void updatePlayer(){
        player.update();
        setTitle("Pac-Man-Game | Lives: " + player.getLives());
        //todo break at the game for a second
    }

    public void increaseGhost(){
        numOfGhosts++;
    }

    public void decreaseGhost(){
        numOfGhosts--;
    }

    public static void main(String[] args) {

        new Frame();

    }


    //todo implement run function
    //todo implement observer


}





















//import javax.swing.*;
//        import java.awt.*;
//        import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.util.Random;
//
////1-wall, 0-path--(2 is the old 0)
//
//
//public class Frame extends JFrame  {
//
//    private static final int TILE_SIZE = 30; // Tile size for better visibility
//    private static final int BOARD_SIZE = 20; // Board size. IF IS SIZE CHANGED THEN MATRIX IN generateMaze() SHOULD BE CHANGED ACCORDINGLY
//    private TilePanel[][] board;
//    private int[][] maze;
//
//    private Player player;
//
//    public Frame() {
//
//        player = Player.getInstance();
//        player.setLocation(new Point(1, 1));
//
//        board = new TilePanel[BOARD_SIZE][BOARD_SIZE];
//
//        maze = new int[BOARD_SIZE][BOARD_SIZE];
//
//        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
//
//        generateMaze();
//
//        for (int i = 0; i < BOARD_SIZE; i++) {
//            for (int j = 0; j < BOARD_SIZE; j++) {
//                Dot dot;
//
//                if(maze[i][j] == 0){
//                    dot = new Dot(new Point(i,j));
//                }
//                else{
//                    dot = null;
//                }
//                board[i][j] = new TilePanel(maze[i][j], dot);
//                board[i][j].setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
//                add(board[i][j]);
//            }
//        }
//
//        addGhosts();
//
//        board[player.getLocation().x][player.getLocation().y].setPlayer(true,"RIGHT"); // Mark initial Pac-Man position
//        addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                movePlayer(e.getKeyCode());
//            }
//        });
//
//        setTitle("Pac-Man-Game");
//        setSize(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setVisible(true);
//        setFocusable(true);
//
//
//    }
//
//    private void addGhosts() {
//        Random rand = new Random();
//        int ghostsAmount = rand.nextInt(1,6), i=0;
//        Point point;
//        do {
//            point = new Point(rand.nextInt(BOARD_SIZE), rand.nextInt(BOARD_SIZE));
//            if(isLocationNotWall(point.x,point.y)) {
//                board[point.x][point.y].setGhost(true, point);
//                i++;
//            }
//
//
//        }
//        while(i<ghostsAmount);
//    }
//
//    public int getBoardSize() {
//        return BOARD_SIZE;
//    }
//
//    private void generateMaze() {
//        int[][] predefinedMaze = {
//                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1},
//                {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1},
//                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
//                {1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1},
//                {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1},
//                {1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1},
//                {1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1},
//                {1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1},
//                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
//                {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
//                {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//                {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0},
//                {1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
//                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
//                {1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0},
//                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
//                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
//        };
//
//        // Copy predefinedMaze to maze variable
//        for (int i = 0; i < BOARD_SIZE; i++)
//            for (int j = 0; j < BOARD_SIZE; j++)
//                maze[i][j] = predefinedMaze[i][j];
//
//    }
//
//    private void movePlayer(int keyCode) {
//        int nextX = player.getLocation().x;
//        int nextY = player.getLocation().y;
//        String direction = "RIGHT"; // Default direction
//
//        switch (keyCode) {
//            case KeyEvent.VK_UP -> {
//                nextX--;
//                direction = "UP";
//            }
//            case KeyEvent.VK_DOWN -> {
//                nextX++;
//                direction = "DOWN";
//            }
//            case KeyEvent.VK_LEFT -> {
//                nextY--;
//                direction = "LEFT";
//            }
//            case KeyEvent.VK_RIGHT -> {
//                nextY++;
//                direction = "RIGHT";
//            }
//        }
//
//        if (isLocationNotWall(nextX, nextY)) {
//            board[player.getLocation().x][player.getLocation().y].setPlayer(false, ""); // Clear previous position
//
//            player.move(new Point(nextX, nextY));
//
//            TilePanel currentTile = board[player.getLocation().x][player.getLocation().y];
//            currentTile.setPlayer(true, direction); // Update with new position and direction
//
//            if (currentTile.hasDot()) {
//                player.incrementScore();
//                currentTile.removeDot();
//                System.out.println("Score: " + player.getScore());
//            }
//        }
//    }
//
//    /**
//     *
//     * @param x
//     * @param y
//     * @return true if location x,y is nit a wall
//     */
//    public boolean isLocationNotWall(int x, int y) {
//        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && maze[x][y] != 1;
//    }
//
//    public static void main(String[] args) {
//
//        new Frame();
//
//    }
//
//
//    //todo add Ghost to board with moving
//    //todo implement run function
//    //todo implement observer
//
//
//
//}

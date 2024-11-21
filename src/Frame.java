import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class Frame extends JFrame  {

    private static final int TILE_SIZE = 30; // Tile size for better visibility
//    private static final int BOARD_SIZE = 34; // Board size. IF IS SIZE CHANGED THEN MATRIX IN generateMaze() SHOULD BE CHANGED ACCORDINGLY
    private TilePanel[][] board;
    private int[][] maze;
    private Player player;
    private int numOfGhosts;
    private boolean isGameActive;
    private static int maxScoreWorth;
    private static final int scoreWorth = 5;

    private static final int BOARD_SIZE_WIDTH = 29; // Board size. IF IS SIZE CHANGED THEN MATRIX IN generateMaze() SHOULD BE CHANGED ACCORDINGLY
    private static final int BOARD_SIZE_HEIGHT = 25; // Board size. IF IS SIZE CHANGED THEN MATRIX IN generateMaze() SHOULD BE CHANGED ACCORDINGLY


//    private int playerX = 1;
//    private int playerY = 1;
//    private int score = 0;

    public Frame() {

        isGameActive = true;
        player = Player.getInstance();
        player.setLocation(new Point(9, 10));

        board = new TilePanel[BOARD_SIZE_WIDTH][BOARD_SIZE_HEIGHT];

        maze = new int[BOARD_SIZE_WIDTH][BOARD_SIZE_HEIGHT];

        setLayout(new GridLayout(BOARD_SIZE_WIDTH, BOARD_SIZE_HEIGHT));

        generateMaze();
        maxScoreWorth = calcMaxScoreWorth();

        for (int i = 0; i < BOARD_SIZE_WIDTH; i++) {
            for (int j = 0; j < BOARD_SIZE_HEIGHT; j++) {
                Dot dot;

                if(maze[i][j] == 0){
                    dot = new Dot(new Point(i,j));
                }
                else{
                    dot = null;
                }

                board[i][j] = new TilePanel(maze[i][j], dot, new Point(i,j));
                board[i][j].setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
                add(board[i][j]);
            }
        }

        board[player.getLocation().x][player.getLocation().y].setPlayer(true,"RIGHT"); // Mark initial Pac-Man position
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                movePlayer(e.getKeyCode());
            }
        });

        setTitle("Pac-Man-Game | Lives: " + player.getLives());
        setSize(BOARD_SIZE_WIDTH * TILE_SIZE, BOARD_SIZE_HEIGHT * TILE_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        startGame();

        setLocationRelativeTo(null);
    }

    private int calcMaxScoreWorth() {
        int sum = 0;
        for (int i = 0; i < BOARD_SIZE_WIDTH; i++)
            for (int j = 0; j < BOARD_SIZE_HEIGHT; j++)
                if (maze[i][j] == 0)
                    ++sum;
        System.out.println("sum = " + sum*scoreWorth);
        return sum*scoreWorth;
    }

    private void startGame() {

        Thread thread = new Thread(player);
        thread.start();

        addGhosts(3);
        setVisible(true);
        setFocusable(true);

        Timer timer = new Timer(1 / 120, e -> {
//            System.out.println(numOfGhosts);
            if (numOfGhosts < 3){
                Random rand = new Random();
                int addGhosts = rand.nextInt(1,5 - numOfGhosts + 1);
                addGhosts(addGhosts);
            }
        });
        timer.start();
//        while (true){
////            System.out.println(isGameActive);
//            if (!isGameActive){
//                JOptionPane.showMessageDialog(this, "Game Paused! Resuming in 3 seconds...");
//                new Timer(3000, ev -> isGameActive = true).start();
//            }
//        }
    }

    private void generateMaze() {
        int[][] predefinedMaze = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0},
                {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0},
                {1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0},
                {1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0},
                {1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0},
                {1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0},
                {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0},
                {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0},
                {1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0},
                {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0},
                {1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0},
                {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0},
                {1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0},
                {1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0},
                {1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        // Copy predefinedMaze to the maze variable, converting paths to dots
        for (int i = 0; i < BOARD_SIZE_WIDTH; i++)
            for (int j = 0; j < BOARD_SIZE_HEIGHT; j++)
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

            if(currentTile.playerTouchedGhost()) {
                updatePlayer();
                if(player.getState().getLives() == 0){
                    informLose();
                }
            }
        }
    }

    public boolean isLocationNotWall(int x, int y) {
        return x >= 0 && x < BOARD_SIZE_WIDTH && y >= 0 && y < BOARD_SIZE_HEIGHT && maze[x][y] != 1;
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
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); // Center the window

        // Create a panel with a vertical layout for components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Add congratulatory text
        JLabel text = new JLabel("Congratulations! You won with " + maxScoreWorth + " points.", JLabel.CENTER);
        text.setFont(new Font("Arial", Font.PLAIN, 20));

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

    private void informLose(){
        JFrame frame = new JFrame("Game Over");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); // Center the window

        // Create a panel with a vertical layout for components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Add congratulatory text
        JLabel text = new JLabel("You are a loser ;( \n\r you only got " + player.getScore() + " points.", JLabel.CENTER);
        text.setFont(new Font("Arial", Font.PLAIN, 20));

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        JButton leaveButton = new JButton("I give up");
        JButton playAgainButton = new JButton("I want to try Again");
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

//    private void addGhosts() {
//        Random rand = new Random();
//        int ghostsAmount = rand.nextInt(1, 6), i = 0;
//        Point point;
//        do {
//            point = new Point(rand.nextInt(BOARD_SIZE), rand.nextInt(BOARD_SIZE));
//            if (isLocationNotWall(point.x, point.y)) {
//                board[point.x][point.y].addGhost(true, point,this);
//                i++;
//                Thread thread = new Thread(board[point.x][point.y].getGhost());
//                thread.start();
//            }
//
//
//        }
//        while (i < ghostsAmount);
//    }

    private void addGhosts(int num) {
        Random rand = new Random();
        int ghostsAmount = num, i = 0;
        Point point;
        do {
            point = new Point(rand.nextInt(BOARD_SIZE_WIDTH), rand.nextInt(BOARD_SIZE_HEIGHT));
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

    public TilePanel[][] getBoard() {
        return board;
    }

    public void updatePlayer(){
        player.update();
        isGameActive = false;
        setTitle("Pac-Man-Game | Lives: " + player.getLives());
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
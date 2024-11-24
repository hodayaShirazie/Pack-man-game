import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observer;

class Player implements State, Runnable, ILocatable, IMovable {

    private static Player instance = null;
    private int score;
    private State state;
    private ILocatable position;
    private IMovable movable;
    private boolean win;

    private Player() {
        win = false;
        score = 0;
        state = new KeepPlayingState(3);
        position = new Locatable(new Point(0, 0));
        movable = new Movable(position.getLocation());
    }

    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public State getState() {
        return state;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score += 5;
    }

    public void fail() {
        if (state.getLives() == 1) {
            state = new GameOverState();
        }
        state.fail();
    }

    public void update() {
        fail();
    }

    public int getLives() {
        return state.getLives();
    }

    public void run() {

        while (state.getLives() >= 0 || !win){

        }
    }

    public Point getLocation() {
        return position.getLocation();
    }

    public void setLocation(Point location) {
        position.setLocation(location);
    }

    public void move(Point destination) {

        movable.move(destination);
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    private void resetScores() {
        score = 0;
    }

    public void resetPlayer() {
        setLocation(new Point(1, 1));
        resetScores();
        state = new KeepPlayingState(3);
    }


}
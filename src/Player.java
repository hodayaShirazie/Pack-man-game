import java.awt.*;

public class Player implements State{

    private static Player instance = null;

    private int score;
    private State state;
    private Point location;

    private Player(){
        score = 0;
        state = new KeepPlayingState(3);
        location = new Point(0,0);
    }

    public static Player getInstance(){
        if(instance == null){
            instance = new Player();
        }
        return instance;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore(){
        score += 1;
    }

    public void fail(){
        if(state.getLives() == 1){
            state = new GameOverState();
        }
        state.fail();
    }

    public int getLives(){
        return state.getLives();
    }


}

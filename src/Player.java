import java.awt.*;


// todo add locateable or/and movable interface and drawable
public class Player implements State, Runnable, IDrawable{

    private static Player instance = null;

    private int score;
    private State state;
    private Point location;
    private IDrawable drawable;

    private Player(){
        score = 0;
        state = new KeepPlayingState(3);
        location = new Point(0,0);
//        drawable = new Drawable();
        //todo send constructor parameters
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
        score += 5;
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

    public void run(){
        //todo
    }

    public void drawObject(Graphics g){
        drawable.drawObject(g);
    }

    public void loadImage(String path){
        drawable.loadImage(path);
    }


}

public class GameOverState implements State{

    private int lives;

    public GameOverState(){
        this.lives = 0;
    }

    public int getLives() {
        return lives;
    }
    public void decreaseLives() {
        lives--;
    }
    public void fail(){
        //todo callback
    }
}

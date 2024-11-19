public class KeepPlayingState implements State {

    private int lives;

    public KeepPlayingState(int lives){
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }
    public void decreaseLives() {
        lives--;
    }
    public void fail(){
        decreaseLives();
    }

}

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
        System.out.println("from gameOver" + lives);
        //todo callback - show screen of gameOver and the scores/playAgain
    }
}

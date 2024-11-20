import java.awt.*;

public class Ghost implements IDrawable, Runnable, Cloneable {

    private IDrawable drawable;

    public void drawObject(Graphics g){
        drawable.drawObject(g);
    }

    public void loadImage(String path){
        drawable.loadImage(path);
    }

    public void run() {
        //todo implement
    }

    protected Object clone()  {
        return new Ghost();
    }

    public void changeColor() {



    }

}

import java.awt.*;

public class Ghost implements IDrawable, Runnable, Cloneable {

    private IDrawable drawable;

    public void drawObject(Graphics g){
        drawable.drawObject(g);
    }

    public void loadImage(String path){
        drawable.loadImage(path);
    }

    protected Object clone() throws CloneNotSupportedException {
        try{
            Ghost clonedGhost = (Ghost)super.clone();
            clonedGhost.drawable = (IDrawable)drawable;
            return clonedGhost;
        }
        catch(CloneNotSupportedException e){
            e.printStackTrace();
            return null;
        }
    }

    public void addColor(){




    }

    public void run() {
        //todo implement
    }

}

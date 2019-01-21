package asteroids;
import javafx.scene.shape.Polygon;

public class Player extends SpaceObject {

    private long lastShot;
    private double reloadTime = 0.5;
    private static long NANO = 1000000000L;

    public Player(int x, int y) {
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);
    }

    public Missle shootMissle(long time) {
        return new Missle(this);
    }

    public boolean reloaded(Long time) {
        double interval = (double) ((time - lastShot) / NANO);
        if (interval > reloadTime) {
            lastShot = time;
            return true;
        }
        return false;
    }
}
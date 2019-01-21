package asteroids;

import javafx.scene.shape.Polygon;

public class Missle extends SpaceObject {

    public Missle(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
    }

    public Missle(Player player) {
        this(
            (int) player.getPolygon().getTranslateX(),
            (int) player.getPolygon().getTranslateY()
        );
        this.getPolygon().setRotate(player.getPolygon().getRotate());
        this.accelerate();
        this.setMovement(this.getMovement().normalize().multiply(3));
    }

}
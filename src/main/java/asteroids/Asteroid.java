package asteroids;

import java.util.Random;

import javafx.scene.shape.Polygon;

public class Asteroid extends SpaceObject {
    private double spin;

    public Asteroid(int x, int y) {
        super(new PolygonFactory().create(), x, y);
        Random rnd = new Random();

        super.getPolygon().setRotate(rnd.nextInt(360));

        int count = 1 + rnd.nextInt(10);
        for (int i = 0; i < count; i++) {
            accelerate();
        }

        this.spin = 0.5 - rnd.nextDouble();
    }

    @Override
    public void move() {
        super.move();
        super.getPolygon().setRotate(super.getPolygon().getRotate() + spin);
    }
}
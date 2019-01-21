package asteroids;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class SpaceObject {

    private Polygon polygon;
    private Point2D movement;
    private boolean active;

    public SpaceObject(Polygon polygon, int x, int y) {
        this.polygon = polygon;
        this.polygon.setTranslateX(x);
        this.polygon.setTranslateY(y);
        this.movement = new Point2D(0, 0);
        this.active = true;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void left() {
        this.polygon.setRotate(this.polygon.getRotate() - 5);
    }

    public void right() {
        this.polygon.setRotate(this.polygon.getRotate() + 5);
    }

    public void move() {
        this.polygon.setTranslateX(this.polygon.getTranslateX() + this.movement.getX());
        this.polygon.setTranslateY(this.polygon.getTranslateY() + this.movement.getY());

        if (this.polygon.getTranslateX() < 0) {
            this.polygon.setTranslateX(this.polygon.getTranslateX() + AsteroidsGame.WIDTH);
        }

        if (this.polygon.getTranslateX() > AsteroidsGame.WIDTH) {
            this.polygon.setTranslateX(this.polygon.getTranslateX() % AsteroidsGame.WIDTH);
        }

        if (this.polygon.getTranslateY() < 0) {
            this.polygon.setTranslateY(this.polygon.getTranslateY() + AsteroidsGame.HEIGHT);
        }

        if (this.polygon.getTranslateY() > AsteroidsGame.HEIGHT) {
            this.polygon.setTranslateY(this.polygon.getTranslateY() % AsteroidsGame.HEIGHT);
        }
    }

    public void accelerate() {
        double deltaX = Math.cos(Math.toRadians(this.polygon.getRotate()));
        double deltaY = Math.sin(Math.toRadians(this.polygon.getRotate()));

        deltaX *= 0.05;
        deltaY *= 0.05;
    
        this.movement = this.movement.add(deltaX, deltaY);
    }

    public boolean collision(SpaceObject object) {
        Shape area = Shape.intersect(this.polygon, object.getPolygon());
        return area.getBoundsInLocal().getWidth() != -1;
    }

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
package asteroids;


import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import asteroids.Asteroid;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;
import javafx.animation.AnimationTimer;

public class AsteroidsGame extends Application  {

    public static int WIDTH = 600;
    public static int HEIGHT = 400;

    @Override
    public void start(Stage stage) throws Exception {
        Pane window = new Pane();
        Text text = new Text(10, 20, "Points: 0");
        window.getChildren().add(text);

        AtomicInteger points = new AtomicInteger();

        window.setPrefSize(WIDTH, HEIGHT);

        Player player = new Player(WIDTH/2, HEIGHT/2);

        ArrayList<Asteroid> asteroids = new ArrayList<>();
        Random rnd = new Random();
        for (int i=0; i<10; i++) {
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH), rnd.nextInt(HEIGHT));
            asteroids.add(asteroid);
            window.getChildren().add(asteroid.getPolygon());
        }

        
  
        window.getChildren().add(player.getPolygon());
        Scene scene = new Scene(window);
        Map<KeyCode, Boolean> pressed = new HashMap<>();

        scene.setOnKeyPressed(event -> {
            pressed.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressed.put(event.getCode(), Boolean.FALSE);
        });
        
        ArrayList<Missle> missles = new ArrayList<>();

        new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (pressed.getOrDefault(KeyCode.SPACE, false) && player.reloaded(now)) {
                    Missle missle = player.shootMissle(now);
                    missles.add(missle);
                    window.getChildren().add(missle.getPolygon());
                }

                if(pressed.getOrDefault(KeyCode.LEFT, false)) {
                    player.left();
                }
          
                if(pressed.getOrDefault(KeyCode.RIGHT, false)) {
                    player.right();
                }

                if(pressed.getOrDefault(KeyCode.UP, false)) {
                    player.accelerate();
                }
                player.move();
                asteroids.forEach(asteroid -> {
                    asteroid.move();
                    if (player.collision(asteroid)) {
                        stop();
                    }
                });
                missles.forEach(missle -> missle.move());


                missles.forEach(missle -> {
                    asteroids.forEach(asteroid -> {
                        if(missle.collision(asteroid)) {
                            missle.setActive(false);
                            asteroid.setActive(false);
                            text.setText("Points: " + (points.incrementAndGet() * 1000));
                        }
                    });
                });
                
                missles.stream()
                    .filter(missle -> !missle.isActive())
                    .forEach(missle -> window.getChildren().remove(missle.getPolygon()));
                missles.removeAll(missles.stream()
                    .filter(missle -> !missle.isActive())
                    .collect(Collectors.toList())
                );
                  
                asteroids.stream()
                    .filter(asteroid -> !asteroid.isActive())
                    .forEach(asteroid -> window.getChildren().remove(asteroid.getPolygon()));
                asteroids.removeAll(asteroids.stream()
                    .filter(asteroid -> !asteroid.isActive())
                    .collect(Collectors.toList())
                );

                if(Math.random() < 0.005) {
                    Asteroid asteroid = new Asteroid(WIDTH, HEIGHT);
                    if(!asteroid.collision(player)) {
                        asteroids.add(asteroid);
                        window.getChildren().add(asteroid.getPolygon());
                    }
                }

            }
        }.start();
        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
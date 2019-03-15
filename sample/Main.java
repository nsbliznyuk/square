package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage ps) {
        ps.setTitle("Animation example");
        Group root = new Group();
        Scene myScene = new Scene(root);
        ps.setScene(myScene);

        Canvas canvas = new Canvas(512, 512);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image sun = new Image("sun.jpg");
        Image space = new Image("space.jpg");
        Image earth = new Image("earth.jpg");

        final long startTime = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long t) {
                double diff = (t - startTime) / 1000000000.0;

                double x = Math.cos(diff);
                double y = Math.sin(diff);

                Point2D point2D = ellipticalDiscToSquare(x, y);

                gc.drawImage(space, 0 , 0);
                gc.drawImage(earth, point2D.getX()* 96, point2D.getY() * 96);
                gc.drawImage(sun, 196, 196);
            }
        }.start();

        ps.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Point2D ellipticalDiscToSquare(double u, double v) {
        double u2 = u * u;
        double v2 = v * v;
        double twosqrt2 = 2.0 * Math.sqrt(2.0);
        double subtermx = 2.0 + u2 - v2;
        double subtermy = 2.0 - u2 + v2;
        double termx1 = subtermx + u * twosqrt2;
        double termx2 = subtermx - u * twosqrt2;
        double termy1 = subtermy + v * twosqrt2;
        double termy2 = subtermy - v * twosqrt2;
        double x = 0.5 * Math.sqrt(termx1) - 0.5 * Math.sqrt(termx2);
        double y = 0.5 * Math.sqrt(termy1) - 0.5 * Math.sqrt(termy2);
        return new Point2D(x, y);
    }
}
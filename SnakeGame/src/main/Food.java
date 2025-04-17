package main;

import java.awt.*;
import java.util.Random;

public class Food {
    private Point position;
    private Random random;

    public Food() {
        random = new Random();
        spawn();
    }

    public void spawn() {
        int x = random.nextInt(Constants.COLS);
        int y = random.nextInt(Constants.ROWS);
        position = new Point(x, y);
    }

    public Point getPosition() {
        return position;
    }
}

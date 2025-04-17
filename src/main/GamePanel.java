package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private Snake snake;
    private Food food;
    private GameState gameState;
    private Timer timer;

    public GamePanel() {
        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        snake = new Snake();
        food = new Food();
        gameState = new GameState();

        timer = new Timer(Constants.GAME_SPEED, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawSnake(g);
        drawFood(g);
        drawScore(g);

        if (gameState.isGameOver()) {
            drawGameOver(g);
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int x = 0; x < Constants.WIDTH; x += Constants.TILE_SIZE) {
            g.drawLine(x, 0, x, Constants.HEIGHT);
        }
        for (int y = 0; y < Constants.HEIGHT; y += Constants.TILE_SIZE) {
            g.drawLine(0, y, Constants.WIDTH, y);
        }
    }

    private void drawSnake(Graphics g) {
        g.setColor(Color.GREEN);
        for (Point p : snake.getBody()) {
            g.fillRect(p.x * Constants.TILE_SIZE, p.y * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE);
        }
    }

    private void drawFood(Graphics g) {
        g.setColor(Color.RED);
        Point f = food.getPosition();
        g.fillOval(f.x * Constants.TILE_SIZE, f.y * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Score: " + gameState.getScore(), 10, 20);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("GAME OVER", Constants.WIDTH / 2 - 110, Constants.HEIGHT / 2);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Press R to Restart", Constants.WIDTH / 2 - 85, Constants.HEIGHT / 2 + 40);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameState.isRunning()) {
            Point head = snake.getHead();
            boolean grow = head.equals(food.getPosition());

            snake.move(grow);

            if (grow) {
                food.spawn();
                gameState.incrementScore();
            }

            Point newHead = snake.getHead();

            boolean wallCollision = newHead.x < 0 || newHead.x >= Constants.COLS || newHead.y < 0 || newHead.y >= Constants.ROWS;
            boolean selfCollision = snake.checkCollision();

            if (wallCollision || selfCollision) {
                gameState.setRunning(false);
                gameState.setGameOver(true);
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> snake.setDirection(Direction.UP);
            case KeyEvent.VK_DOWN -> snake.setDirection(Direction.DOWN);
            case KeyEvent.VK_LEFT -> snake.setDirection(Direction.LEFT);
            case KeyEvent.VK_RIGHT -> snake.setDirection(Direction.RIGHT);
            case KeyEvent.VK_R -> {
                if (gameState.isGameOver()) {
                    restartGame();
                }
            }
        }
    }

    private void restartGame() {
        snake = new Snake();
        food = new Food();
        gameState.reset();
        timer.restart();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}

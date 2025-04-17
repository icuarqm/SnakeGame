package main;

public class GameState {
    private boolean running;
    private boolean gameOver;
    private int score;

    public GameState() {
        reset();
    }

    public void reset() {
        running = true;
        gameOver = false;
        score = 0;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }
}

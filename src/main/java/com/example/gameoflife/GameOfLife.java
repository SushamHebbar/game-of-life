package com.example.gameoflife;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class GameOfLife {

    private static final int[][] DIRS = new int[][] {
            {0, -1},     // left
            {-1, -1},    // top left
            {-1, 0},     // top
            {-1, 1},     // top right
            {0, 1},      // right
            {1, 1},      // bottom right
            {1, 0},      // bottom
            {1, -1}      // bottom left
    };

    private final List<GameObserver> observers = new ArrayList<>();

    private boolean[][] universe;
    private final int rows;
    private final int cols;

    public GameOfLife(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.universe = new boolean[rows][cols];
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(int generation) {
        for (GameObserver observer : observers) {
            observer.onUniverseUpdated(universe, generation);
        }
    }

    public void initializeGlider() {
        int row = (rows / 2) - 1;
        int col = (cols / 2) - 1;

        updateCell(row - 1, col, true);
        updateCell(row, col + 1, true);
        updateCell(row + 1, col - 1, true);
        updateCell(row + 1, col, true);
        updateCell(row + 1, col + 1, true);

        for(GameObserver observer : observers) {
            observer.onUniverseUpdated(universe, 0);
        }
    }

    public void start(int generations) {
        for (GameObserver observer : observers) {
            observer.onGameStarted();
        }

        final int[] gen = {1};
        Timer timer = new Timer(300, null);
        timer.addActionListener(e -> {
            notifyObservers(gen[0]);
//            printUniverse(gen[0]);

            boolean[][] newUniverse = new boolean[rows][cols];
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < cols; j++) {
                    int live = countLiveCells(i, j);

                    if(universe[i][j] && live < 2) {
                        newUniverse[i][j] = false;
                    } else if(universe[i][j] && live <= 3) {
                        newUniverse[i][j] = true;
                    } else if(universe[i][j] && live > 3) {
                        newUniverse[i][j] = false;
                    } else if(!universe[i][j] && live == 3) {
                        newUniverse[i][j] = true;
                    }
                }
            }

            universe = newUniverse;
            gen[0]++;
            if(gen[0] > generations) {
                timer.stop();

                for(GameObserver observer : observers) {
                    observer.onGameEnded();
                }
            }
        });
        timer.start();
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private void updateCell(int row, int col, boolean isAlive) {
        if(isValidCell(row, col)) {
            universe[row][col] = isAlive;
        }
    }

    private void printUniverse(int gen) {
        System.out.println("============[ " + "Generation " + gen + " ]============");
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                System.out.print(universe[i][j] ? " █ " : " . ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    private int countLiveCells(int row, int col) {
        int live = 0;

        for(int[] dir : DIRS) {
            int x = row + dir[0];
            int y = col + dir[1];

            if(isValidCell(x, y)) {
                live += (universe[x][y] ? 1 : 0);
            }
        }

        return live;
    }
}

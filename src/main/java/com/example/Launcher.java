package com.example;

import com.example.gameoflife.GameOfLife;

public class Launcher {
    public static void main(String[] args) {
        int rows = 25;
        int cols = 25;

        GameOfLife gameOfLife = new GameOfLife(rows, cols);
        gameOfLife.initializeGlider();

        gameOfLife.start(80);
    }
}


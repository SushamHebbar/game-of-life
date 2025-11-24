package com.example;

import com.example.ui.GameOfLifeBoard;
import javax.swing.JFrame;

public class UiLauncher {

    public static void main(String[] args) {
        JFrame mainFrame = new GameOfLifeBoard();
        mainFrame.setVisible(true);
    }
}

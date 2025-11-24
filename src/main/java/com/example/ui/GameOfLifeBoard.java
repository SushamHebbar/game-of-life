package com.example.ui;

import com.example.gameoflife.GameObserver;
import com.example.gameoflife.GameOfLife;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.net.URL;

public class GameOfLifeBoard extends JFrame implements GameObserver {

    private final JButton btnStart;
    private final JButton btnReset;
    private final JLabel lblGeneration;
    private final JLabel lblStatusValue;
    private final JSpinner spinnerGenerations;
    private final JSpinner spinnerBoardSize;

    private JPanel panelBoard;
    private int boardSize = 25;
    private int generations = 10;
    private JPanel[][] board;

    public GameOfLifeBoard() {
        setTitle("Conway's Game of Life");
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());
        setContentPane(contentPanel);

        JPanel panelNorth = new JPanel(new GridLayout(1, 2));
        contentPanel.add(panelNorth, BorderLayout.NORTH);

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNorth.add(panel1);

        JLabel lblTitle = new JLabel("Generations:");
        panel1.add(lblTitle);

        lblGeneration = new JLabel("0");
        panel1.add(lblGeneration);

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelNorth.add(panel2);

        JLabel lblStatus = new JLabel("Status:");
        panel2.add(lblStatus);

        lblStatusValue = new JLabel("Click Start to begin");
        panel2.add(lblStatusValue);

        JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSouth.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        contentPanel.add(panelSouth, BorderLayout.SOUTH);

        JPanel panelGenerations = new JPanel();
        panelSouth.add(panelGenerations);
        JLabel lblGenerations = new JLabel("Generations:");
        panelGenerations.add(lblGenerations);
        spinnerGenerations = new JSpinner(new SpinnerNumberModel(10, 5, 100, 1));
        ((JSpinner.DefaultEditor) spinnerGenerations.getEditor()).getTextField().setEditable(false);
        spinnerGenerations.addChangeListener(e -> generations = (int) spinnerGenerations.getValue());
        panelGenerations.add(spinnerGenerations);

        JPanel panelSize = new JPanel();
        panelSouth.add(panelSize);
        JLabel lblSize = new JLabel("Board Size:");
        panelSize.add(lblSize);
        spinnerBoardSize = new JSpinner(new SpinnerNumberModel(25, 15, 100, 1));
        ((JSpinner.DefaultEditor) spinnerBoardSize.getEditor()).getTextField().setEditable(false);
        spinnerBoardSize.addChangeListener(e -> boardSize = (int) spinnerBoardSize.getValue());
        panelSize.add(spinnerBoardSize);

        btnReset = getButtonWithIcon("reset", "Reset");
        btnReset.addActionListener(e -> {
            spinnerBoardSize.setValue(25);
            spinnerGenerations.setValue(10);

            renderBoard();
        });
        panelSouth.add(btnReset);

        btnStart = getButtonWithIcon("play", "Start");
        btnStart.addActionListener(e -> {
            GameOfLife gameOfLife = new GameOfLife(boardSize, boardSize);
            gameOfLife.addObserver(this);

            gameOfLife.initializeGlider();
            gameOfLife.start(generations);
        });
        panelSouth.add(btnStart);

        panelBoard = new JPanel(new GridLayout(boardSize, boardSize, 1, 1));
        contentPanel.add(panelBoard, BorderLayout.CENTER);

    }

    @Override
    public void onUniverseUpdated(boolean[][] universe, int generation) {
        lblGeneration.setText("" + generation);

        if(board == null) {
            renderBoard();
        }

        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                if (universe[i][j]) {
                    board[i][j].setBackground(Color.BLACK);
                } else {
                    board[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    private void renderBoard() {
        board = new JPanel[boardSize][boardSize];

        getContentPane().remove(panelBoard);
        panelBoard = new JPanel(new GridLayout(boardSize, boardSize, 1, 1));
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                JPanel cellPanel = new JPanel();
                cellPanel.setBackground(Color.WHITE);
                panelBoard.add(cellPanel);

                board[i][j] = cellPanel;
            }
        }
        getContentPane().add(panelBoard, BorderLayout.CENTER);
    }

    @Override
    public void onGameStarted() {
        spinnerGenerations.setEnabled(false);
        spinnerBoardSize.setEnabled(false);
        btnStart.setEnabled(false);
        btnReset.setEnabled(false);

        lblStatusValue.setText("Game in progress...");
    }

    @Override
    public void onGameEnded() {
        spinnerGenerations.setEnabled(true);
        spinnerBoardSize.setEnabled(true);
        btnStart.setEnabled(true);
        btnReset.setEnabled(true);

        lblStatusValue.setText("Game ended.");

        Timer timer = new Timer(3000, null);
        timer.addActionListener(e -> {
            lblGeneration.setText("0");
            lblStatusValue.setText("Click Start to begin");
            timer.stop();
        });
        timer.start();

        board = null;
        panelBoard = new JPanel();
    }

    private JButton getButtonWithIcon(String iconFileName, String title) {
        URL iconResource = getClass().getResource("/static/" + iconFileName + ".png");
        JButton button;
        if (iconResource != null) {
            ImageIcon icon = new ImageIcon(iconResource);
            button = new JButton(title, icon);
        } else {
            button = new JButton(title);
        }

        button.setPreferredSize(new Dimension(100, 40));
        return button;
    }
}

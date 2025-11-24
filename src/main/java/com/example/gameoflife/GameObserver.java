package com.example.gameoflife;

public interface GameObserver {
    void onUniverseUpdated(boolean[][] universe, int generation);
    void onGameStarted();
    void onGameEnded();
}

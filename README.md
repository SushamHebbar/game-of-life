# Game of Life

A Java implementation of **Conway's Game of Life**, a cellular automaton devised by mathematician John Horton Conway in 1970.

## About Conway's Game of Life

Conway's Game of Life is a zero-player game that evolves based on its initial state. It consists of a 2D grid of cells, each of which can be either alive or dead. The state of each cell evolves over discrete time steps according to the following rules:

1. **Birth**: A dead cell with exactly 3 live neighbors becomes alive
2. **Survival**: A live cell with 2 or 3 live neighbors survives
3. **Death by underpopulation**: A live cell with fewer than 2 live neighbors dies
4. **Death by overpopulation**: A live cell with more than 3 live neighbors dies

## Features

- **Conway's Game of Life Simulation**: Full implementation of the cellular automaton rules
- **Glider Pattern**: Demonstrates the classic "glider" oscillator pattern
- **Console Output**: Visual representation using text characters:
  - `█` represents a live cell
  - `.` represents a dead cell

## Project Structure

```
game-of-life/
├── src/main/java/com/example/
│   ├── Launcher.java              # Entry point of the application
│   └── gameoflife/
│       └── GameOfLife.java        # Core Game of Life implementation
├── pom.xml                        # Maven configuration
└── README.md                      # This file
```

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

## Building the Project

### Using Maven

```bash
# Compile the project
mvn clean compile

# Run the project
mvn exec:java -Dexec.mainClass="com.example.Launcher"

# Package the project
mvn clean package
```

### Using Maven Wrapper (Windows)

```bash
# Compile the project
mvnw.cmd clean compile

# Run the project
mvnw.cmd exec:java -Dexec.mainClass="com.example.Launcher"

# Package the project
mvnw.cmd clean package
```

## Running the Application

The application initializes a 10x10 grid with a glider pattern and simulates 30 generations of evolution.

```bash
mvn exec:java -Dexec.mainClass="com.example.Launcher"
```

### Customizing the Simulation

Edit `Launcher.java` to change the grid size and number of generations:

```java
int rows = 10;        // Number of rows
int cols = 10;        // Number of columns
gameOfLife.start(30); // Number of generations to simulate
```

## Example Output

The console displays each generation as a grid:

```
 .  .  .  █  . 
 █  .  █  .  . 
 .  █  █  .  . 
 .  .  .  .  . 
 .  .  .  .  . 
```

## Code Overview

### GameOfLife.java

- `GameOfLife(int rows, int cols)`: Constructor to initialize the universe
- `initializeGlider()`: Sets up the initial glider pattern
- `start(int generations)`: Runs the simulation for a specified number of generations
- `countLiveCells(int row, int col)`: Counts live neighbors for a cell
- `printUniverse()`: Displays the current state of the universe

### Launcher.java

Entry point that sets up and starts the simulation.

## Algorithm Details

The simulation uses a neighbor-counting algorithm that checks all 8 adjacent cells (including diagonals) for each cell in the grid. The rules are applied to calculate the next generation based on the current state.



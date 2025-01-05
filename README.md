# Sudoku Game

A feature-rich Sudoku game implementation in Java with a graphical user interface. This game offers multiple difficulty levels, an intuitive interface, and helpful features to enhance your Sudoku-solving experience.

## 🎮 Features

- **Game Mechanics**
  - Classic 9x9 Sudoku grid
  - Three difficulty levels (Easy, Medium, Hard)
  - Real-time input validation
  - Win condition checking
  - Timer to track solving time

- **User Interface**
  - Clean, modern design
  - Light and Dark themes
  - Visual feedback for moves
  - Cell highlighting
  - Original numbers in black, user inputs in blue

- **Helpful Tools**
  - Smart hint system
  - Undo/Redo functionality
  - Save/Load game progress
  - Game state persistence

## 🚀 Prerequisites

- **Java Development Kit (JDK) 11 or higher**
  - Required for running and compiling the game
  - Download from [Adoptium](https://adoptium.net/)

- **Maven (Optional)**
  - Required only if building with Maven
  - Download from [Maven](https://maven.apache.org/download.cgi)

## 💻 Installation

### Method 1: Using Java Directly

1. **Compile the project**:
   ```bash
   # Create output directory
   mkdir target\classes

   # Compile Java files
   javac -d target\classes src\main\java\com\sudoku\**\*.java

   # Copy resources
   xcopy /Y /E /I src\main\resources target\classes
   ```

2. **Run the game**:
   ```bash
   java -cp target\classes com.sudoku.main.SudokuGame
   ```

### Method 2: Using Maven

1. **Build and run**:
   ```bash
   mvn clean install
   mvn exec:java
   ```

## 🎯 How to Play

1. **Starting a Game**
   - Launch the application
   - Click "New Game" or go to Game → New Game
   - Choose difficulty from Game → Difficulty menu

2. **Game Controls**
   - Left-click a cell to select it
   - Type numbers 1-9 to fill cells
   - Invalid moves are automatically prevented
   - Original puzzle numbers appear in black
   - Your inputs appear in blue

3. **Using Features**
   - **Hint**: Click "Hint" button for suggestions
   - **Undo/Redo**: Use buttons or Ctrl+Z/Ctrl+Y
   - **Save Game**: Game → Save or Ctrl+S
   - **Load Game**: Game → Load or Ctrl+L
   - **Check Solution**: Click "Check Solution"
   - **Change Theme**: Game → Theme

## 🏗️ Project Structure

```
sudoku-game/
├── pom.xml                 # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── sudoku/
│   │   │           ├── controller/    # Game logic control
│   │   │           │   └── GameController.java
│   │   │           ├── model/         # Data models
│   │   │           │   ├── DifficultyLevel.java
│   │   │           │   ├── GameState.java
│   │   │           │   ├── Hint.java
│   │   │           │   ├── SudokuGrid.java
│   │   │           │   └── TimerLogic.java
│   │   │           ├── view/          # UI components
│   │   │           │   ├── GridPanel.java
│   │   │           │   └── SudokuGUI.java
│   │   │           └── main/          # Application entry
│   │   │               └── SudokuGame.java
│   │   └── resources/
│   │       └── themes/               # Theme configurations
│   │           ├── light-theme.properties
│   │           └── dark-theme.properties
│   └── test/                        # Unit tests
│       └── java/
│           └── com/
│               └── sudoku/
│                   └── test/
│                       └── SudokuGridTest.java
```

## 🔧 Technical Details

- **Design Pattern**: MVC (Model-View-Controller)
- **UI Framework**: Java Swing
- **Build Tool**: Maven/Manual compilation
- **Testing**: JUnit 5
- **Persistence**: File-based serialization
- **Java Version**: JDK 11+

# Data Structures and Algorithms

## 1. Data Structures Used

### 1.1 Two-Dimensional Arrays
- Used for representing the Sudoku grid (`int[][] grid`)
- Used for tracking original cells (`boolean[][] originalCells`)
- Efficient access and modification with O(1) time complexity
- Memory efficient for fixed-size 9x9 grid

### 1.2 Stack
- Used for Undo/Redo functionality (`Stack<GameState>`)
- Maintains game state history
- LIFO (Last In, First Out) operations
- O(1) time complexity for push and pop operations

### 1.3 ArrayList
- Used in hint system for storing valid numbers
- Dynamic sizing for varying number of possibilities
- O(1) time complexity for add and get operations

### 1.4 Optional
- Used for safe handling of hints and loaded games
- Prevents null pointer exceptions
- Functional programming approach

## 2. Algorithms

### 2.1 Puzzle Generation
```java
// Backtracking algorithm for generating valid Sudoku puzzles
private boolean generateSolution(int row, int col) {
    if (col >= size) {
        row++;
        col = 0;
    }
    if (row >= size) {
        return true;
    }

    List<Integer> numbers = getShuffledNumbers();
    for (int num : numbers) {
        if (isValidMove(row, col, num)) {
            grid[row][col] = num;
            if (generateSolution(row, col + 1)) {
                return true;
            }
            grid[row][col] = 0;
        }
    }
    return false;
}
```

### 2.2 Move Validation
- Row checking: O(n) time complexity
- Column checking: O(n) time complexity
- Subgrid checking: O(√n × √n) time complexity
- Combined validation ensures puzzle rules are maintained

### 2.3 Hint System
- Finds empty cells
- Calculates valid numbers for each cell
- Uses constraint satisfaction to suggest moves
- Prioritizes cells with fewer possibilities

### 2.4 Win Condition Checking
- Verifies grid completeness
- Validates all rows, columns, and subgrids
- O(n²) time complexity for full grid validation

## 3. Design Patterns

### 3.1 Model-View-Controller (MVC)
- **Model**: `SudokuGrid`, `GameState`, etc.
- **View**: `SudokuGUI`, `GridPanel`
- **Controller**: `GameController`
- Separates concerns and improves maintainability

### 3.2 Observer Pattern
- Used in timer implementation
- GUI updates automatically when timer changes
- Loose coupling between components

### 3.3 State Pattern
- Manages game state transitions
- Handles save/load functionality
- Maintains undo/redo history

## 4. Performance Considerations

### 4.1 Time Complexity
- Grid Operations: O(1) for access and modification
- Validation: O(n) for each move
- Puzzle Generation: O(n³) worst case
- Hint Generation: O(n²) worst case

### 4.2 Space Complexity
- Grid Storage: O(n²)
- Game State: O(n²)
- Undo/Redo Stacks: O(m × n²) where m is number of moves

### 4.3 Optimizations
- Efficient array access
- Minimal object creation
- Cached validations where possible
- Memory-efficient state storage


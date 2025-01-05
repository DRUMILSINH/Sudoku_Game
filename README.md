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


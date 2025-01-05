# Sudoku Game

A modern, feature-rich Sudoku game implementation in Java. This application offers multiple grid sizes, difficulty levels, and an intuitive user interface with various helpful features to enhance the gaming experience.

![Sudoku Game Screenshot](screenshots/game.png)

## 🎮 Features

- **Multiple Grid Sizes**
  - Classic 9x9 grid
  - Advanced 16x16 grid with hexadecimal numbers

- **Game Mechanics**
  - Three difficulty levels (Easy, Medium, Hard)
  - Intelligent hint system
  - Undo/Redo functionality
  - Real-time validation
  - Game timer

- **User Interface**
  - Clean, modern design
  - Light and Dark themes
  - Visual feedback for moves
  - Keyboard and mouse support

- **Data Management**
  - Save/Load game progress
  - Game state persistence
  - Progress tracking

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Gradle 7.0 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/drumilsinh/sudoku-game.git
   cd sudoku-game
   ```

2. **Build the project**
   ```bash
   ./gradlew build
   ```

3. **Run the application**
   ```bash
   ./gradlew run
   ```

### Alternative Installation Methods

#### Using IntelliJ IDEA
1. Open IntelliJ IDEA
2. Select `File → Open`
3. Navigate to the project directory
4. Click `Open`
5. Wait for the IDE to import and index the project
6. Run `main.SudokuGame`

#### Using Eclipse
1. Open Eclipse
2. Select `File → Import`
3. Choose `Gradle → Existing Gradle Project`
4. Navigate to the project directory
5. Click `Finish`
6. Run `main.SudokuGame`

## 🎯 How to Play

### Starting a Game
1. Launch the application
2. Choose `Game → New Game` from the menu
3. Select your preferred difficulty level
4. Start solving!

### Game Controls
- **Number Input**
  - 9x9 grid: Use numbers 1-9
  - 16x16 grid: Use numbers 1-9 and letters A-F
- **Navigation**
  - Mouse: Click cells to select
  - Keyboard: Arrow keys to move
- **Special Keys**
  - Delete/Backspace: Clear cell
  - Ctrl+Z: Undo
  - Ctrl+Y: Redo

### Features Usage
- **Hint**: Click the "Hint" button for suggestions
- **Save Game**: `Game → Save` or Ctrl+S
- **Load Game**: `Game → Load` or Ctrl+L
- **Check Solution**: Click "Check Solution"
- **Change Theme**: `View → Theme`

## 🛠️ Development

### Project Structure 
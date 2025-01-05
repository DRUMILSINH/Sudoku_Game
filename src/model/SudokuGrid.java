public Optional<Hint> getHint() {
    List<Hint> possibleHints = new ArrayList<>();
    
    // Look for cells where only one number is possible
    for (int row = 0; row < size; row++) {
        for (int col = 0; col < size; col++) {
            if (grid[row][col] == 0) {
                List<Integer> validNumbers = findValidNumbers(row, col);
                if (!validNumbers.isEmpty()) {
                    possibleHints.add(new Hint(row, col, validNumbers));
                }
            }
        }
    }
    
    // Sort hints by number of possibilities (easier hints first)
    possibleHints.sort(Comparator.comparingInt(h -> h.getValidNumbers().size()));
    
    return possibleHints.isEmpty() ? Optional.empty() : Optional.of(possibleHints.get(0));
}

private List<Integer> findValidNumbers(int row, int col) {
    List<Integer> valid = new ArrayList<>();
    for (int num = 1; num <= size; num++) {
        if (isValidMove(row, col, num)) {
            valid.add(num);
        }
    }
    return valid;
}

public void setValue(int row, int col, int value) {
    if (row < 0 || row >= size || col < 0 || col >= size) {
        throw new IllegalArgumentException("Invalid cell position");
    }
    grid[row][col] = value;
}

public void loadState(GameState state) {
    int[][] newGrid = state.getGridState();
    for (int i = 0; i < size; i++) {
        System.arraycopy(newGrid[i], 0, grid[i], 0, size);
    }
} 
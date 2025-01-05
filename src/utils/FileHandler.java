package utils;

import model.GameState;
import java.io.*;
import java.nio.file.*;
import java.util.Optional;

public class FileHandler {
    private static final String SAVE_FILE = "game_save.dat";
    
    public static void saveGame(GameState state) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(Files.newOutputStream(Paths.get(SAVE_FILE))))) {
            oos.writeObject(state);
        } catch (IOException e) {
            Logger.error("Failed to save game", e);
            throw new IOException("Failed to save game: " + e.getMessage(), e);
        }
    }
    
    public static Optional<GameState> loadGame() {
        if (!Files.exists(Paths.get(SAVE_FILE))) {
            return Optional.empty();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(Files.newInputStream(Paths.get(SAVE_FILE))))) {
            return Optional.of((GameState) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            Logger.error("Failed to load game", e);
            return Optional.empty();
        }
    }
    
    public static void deleteSaveFile() {
        try {
            Files.deleteIfExists(Paths.get(SAVE_FILE));
        } catch (IOException e) {
            Logger.error("Failed to delete save file", e);
        }
    }
} 
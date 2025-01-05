package model;

import controller.GameController;
import javax.swing.Timer;
import java.awt.event.ActionListener;

public class TimerLogic {
    private final Timer timer;
    private int seconds;
    private final GameController controller;
    
    public TimerLogic(GameController controller) {
        this.controller = controller;
        this.seconds = 0;
        
        ActionListener timerAction = e -> {
            seconds++;
            updateDisplay();
        };
        
        this.timer = new Timer(1000, timerAction);
    }
    
    public void start() {
        timer.start();
    }
    
    public void stop() {
        timer.stop();
    }
    
    public void reset() {
        timer.stop();
        seconds = 0;
        updateDisplay();
    }
    
    private void updateDisplay() {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        String time = String.format("%02d:%02d", minutes, secs);
        controller.updateTimer(time);
    }
    
    public int getElapsedTime() {
        return seconds;
    }
} 
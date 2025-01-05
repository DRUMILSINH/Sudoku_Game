package com.sudoku.model;

import javax.swing.Timer;
import java.awt.event.ActionListener;

public class TimerLogic {
    private Timer timer;
    private int seconds;
    private final ActionListener timerListener;

    public TimerLogic(ActionListener timerListener) {
        this.seconds = 0;
        this.timerListener = timerListener;
        initTimer();
    }

    private void initTimer() {
        timer = new Timer(1000, e -> {
            seconds++;
            timerListener.actionPerformed(e);
        });
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
        timerListener.actionPerformed(null);
    }

    public String getFormattedTime() {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    public int getElapsedSeconds() {
        return seconds;
    }

    public void setElapsedSeconds(int seconds) {
        this.seconds = seconds;
        timerListener.actionPerformed(null);
    }
} 
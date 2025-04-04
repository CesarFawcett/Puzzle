package controller;

import javax.swing.*;

public class TimerController {
    private final JLabel timerLabel;
    private javax.swing.Timer timer;
    private int seconds;
    
    public TimerController(JLabel timerLabel) {
        this.timerLabel = timerLabel;
        this.seconds = 0;
        setupTimer();
    }
    
    private void setupTimer() {
        timer = new javax.swing.Timer(1000, e -> {
            seconds++;
            timerLabel.setText("Tiempo: " + seconds + " segundos");
        });
    }
    
    public void startTimer() {
        seconds = 0;
        timerLabel.setText("Tiempo: 0 segundos");
        timer.start();
    }
    
    public void stopTimer() {
        timer.stop();
    }
    
    public int getSeconds() {
        return seconds;
    }
}
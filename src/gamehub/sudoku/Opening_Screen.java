package gamehub.sudoku;

import gamehub.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Opening_Screen {

    JFrame frame;

    public Opening_Screen() {
        SoundManager Sound = new SoundManager();
        Sound.playBackgroundMusic();  
        showOpeningScreen();
    }

    public void showOpeningScreen() {
        if (frame != null) frame.dispose();

        frame = new JFrame("🧠  Welcome to Sudoku! 🧩");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JLabel title = new JLabel("🧠  Welcome to Sudoku! 🧩", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        frame.add(title, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setFocusable(false);
        startButton.setBounds(220, 300, 150, 40);
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 18));
        mainMenuButton.setFocusable(false);
        mainMenuButton.setBounds(220, 300, 150, 40);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(startButton);
        buttonPanel.add(mainMenuButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
            SoundManager s = new SoundManager();
            s.playClickSound();
            frame.dispose();
            new Sudoku(Opening_Screen.this);
        });

        mainMenuButton.addActionListener(e -> {
            SoundManager s = new SoundManager();
            s.playClickSound();
            frame.dispose();
            s.stopAllSounds();
            new gamehub.GAME_HUB();
        });

        frame.setVisible(true);
    }
}

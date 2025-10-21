package gamehub.BlackJack;
import gamehub.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Opening_Screen {
    JFrame frame;
    JPanel panel;
    JButton startButton;
    JButton menuButton; // 👈 New button for Main Menu

    public Opening_Screen() {
        Sound_Manager.playBackgroundMusic();

        frame = new JFrame("Welcome to BlackJack");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(40, 40, 40));
                g.fillRect(0, 0, getWidth(), getHeight());

                g.setFont(new Font("Serif", Font.BOLD, 36));
                g.setColor(Color.WHITE);
                g.drawString("Welcome to BlackJack!", 120, 200);
            }
        };
        panel.setLayout(null);

        // Start Game Button
        startButton = new JButton("Start Game");
        startButton.setBounds(220, 300, 150, 40);
        startButton.setFocusable(false);
        panel.add(startButton);

        // 🔥 Main Menu Button
        menuButton = new JButton("Main Menu");
        menuButton.setBounds(220, 360, 150, 40);
        menuButton.setFocusable(false);
        panel.add(menuButton);

        frame.add(panel);
        frame.setVisible(true);

        // 👉 Start Game Logic
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Sound_Manager.playClick();
                new BlackJack(); // Launch the game
            }
        });

        
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Sound_Manager.stopAllSounds();
                
                new gamehub.GAME_HUB();
                
            }
        });
    }

    public static void main(String[] args) {
        new Opening_Screen();
    }
}

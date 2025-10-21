package gamehub;

import javax.swing.*;
import java.awt.*;

public class GAME_HUB extends JFrame {

    // Define constants for better maintenance
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 200;

    public GAME_HUB() {
        super("Game Hub - Welcome!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Use a 2x2 GridLayout for a cleaner button arrangement
        setLayout(new GridLayout(2, 2, 10, 10)); // Rows, Cols, H-Gap, V-Gap
        
        // Create Buttons
        JButton btnSudoku = createGameButton("Play Sudoku", gamehub.sudoku.App.class);
        JButton btnBlackjack = createGameButton("Play BlackJack", gamehub.BlackJack.App.class);
        
        // Add a button for the new AI Riddle Game
        JButton btnAIRiddle = createGameButton("AI Riddle Game", gamehub.airiddle.App.class); 
        
        // Add the Exit button
        JButton btnExit = new JButton("Exit Game Hub");
        btnExit.setFont(new Font("Arial", Font.BOLD, 16));
        btnExit.setBackground(new Color(255, 100, 100)); // Red color for exit
        btnExit.setForeground(Color.WHITE);
        btnExit.addActionListener(e -> {
            int confirmed = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit the Game Hub?", 
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Add components to the frame
        add(btnSudoku);
        add(btnBlackjack);
        add(btnAIRiddle); // Placeholder for your new game
        add(btnExit);

        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }
    
    // Helper method to create consistent game buttons and attach listeners
    private JButton createGameButton(String text, Class<?> mainClass) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.addActionListener(e -> {
            try {
                // Use reflection to call the main method of the target game
                mainClass.getMethod("main", String[].class).invoke(null, (Object) new String[]{});
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Could not launch " + text + ". Check console for details.", 
                    "Launch Error", 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        return button;
    }

    public static void main(String[] args) {
        // Use invokeLater to ensure all GUI updates are performed on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new GAME_HUB();
        });
    }
}
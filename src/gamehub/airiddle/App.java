package gamehub.airiddle;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AIRiddleGame::new);
    }
}
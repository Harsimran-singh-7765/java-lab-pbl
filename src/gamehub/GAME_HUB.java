package gamehub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.HashMap;
import java.util.Map;

public class GAME_HUB extends JFrame {

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    
    // Prismarine and White color palette
    private static final Color PRIMARY_COLOR = new Color(42, 187, 155); // Prismarine
    private static final Color SECONDARY_COLOR = new Color(35, 155, 130); // Dark Prismarine
    private static final Color ACCENT_COLOR = new Color(58, 211, 176); // Light Prismarine
    private static final Color BG_COLOR = Color.WHITE;
    private static final Color CARD_BG = new Color(248, 250, 252);
    private static final Color TEXT_PRIMARY = new Color(30, 30, 46);
    private static final Color TEXT_SECONDARY = new Color(100, 100, 120);
    private static final Color RED = new Color(255, 0, 0);
    private static Font pressStart2P;
    
    static {
        try {
            // Load Press Start 2P font from resources
            pressStart2P = Font.createFont(Font.TRUETYPE_FONT, 
                GAME_HUB.class.getResourceAsStream("/Press_Start_2P/PressStart2P-Regular.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pressStart2P);
        } catch (Exception e) {
            System.err.println("Could not load Press Start 2P font, using Monospaced as fallback");
            pressStart2P = new Font("Monospaced", Font.PLAIN, 12);
        }
    }
    
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Map<String, GameInfo> gameDescriptions;
    
    // Game info class
    private static class GameInfo {
        String name;
        String description;
        Class<?> mainClass;
        Color accentColor;
        
        GameInfo(String name, String description, Class<?> mainClass, Color accentColor) {
            this.name = name;
            this.description = description;
            this.mainClass = mainClass;
            this.accentColor = accentColor;
        }
    }

    public GAME_HUB() {
        super("MindEscape");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setBackground(BG_COLOR);
        
     
        initGameDescriptions();
        
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BG_COLOR);
        
       
        mainPanel.add(createWelcomeScreen(), "welcome");
        mainPanel.add(createGameScreen(), "games");
        
        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initGameDescriptions() {
        gameDescriptions = new HashMap<>();
        
        gameDescriptions.put("sudoku", new GameInfo(
            "Sudoku Master",
            "Exercise your logic with classic number puzzles. Fill the 9×9 grid with strategic thinking and concentration.",
            gamehub.sudoku.App.class,
            new Color(42, 187, 155) 
        ));
        
        gameDescriptions.put("blackjack", new GameInfo(
            "BlackJack Elite",
            "Test your luck and strategy in this classic card game. Beat the dealer and master the art of 21.",
            gamehub.BlackJack.App.class,
            new Color(35, 155, 130) 
        ));
        
        gameDescriptions.put("airiddle", new GameInfo(
            "AI Riddle Challenge",
            "Engage your mind with AI-powered riddles. Solve clever puzzles and unlock new levels of thinking.",
            gamehub.airiddle.App.class,
            new Color(58, 211, 176)
        ));
        
        gameDescriptions.put("highstakes", new GameInfo(
            "High Stakes Showdown",
            "Experience the thrill of high-stakes competition. Make bold moves and claim victory in intense matches.",
            gamehub.highstakesshowdown.HighStakesShowdown.class,
            new Color(25, 135, 110) 
        ));
    }
    
    private JPanel createWelcomeScreen() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, PRIMARY_COLOR,
                    getWidth(), getHeight(), SECONDARY_COLOR
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
                g2.setColor(Color.WHITE);
                g2.fillOval(-100, -100, 400, 400);
                g2.fillOval(getWidth() - 300, getHeight() - 300, 400, 400);
                
               
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }
        };
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        
        JLabel title = new JLabel("MindEscape") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
                
                g2.setColor(new Color(0, 0, 0, 80));
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = fm.getAscent();
                g2.drawString(getText(), x + 4, y + 4);
                
                g2.setColor(getForeground());
                g2.drawString(getText(), x, y);
            }
        };
        title.setFont(pressStart2P.deriveFont(64f));
        title.setForeground(Color.WHITE);
        title.setPreferredSize(new Dimension(650, 90));
        gbc.gridy = 0;
        panel.add(title, gbc);
        
        
        JLabel subtitle = new JLabel("Your Gateway to Premium Gaming");
        subtitle.setFont(pressStart2P.deriveFont(14f));
        subtitle.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 60, 0);
        panel.add(subtitle, gbc);
        
        
        JButton startBtn = createPremiumButton("Start Your Journey", 280, 60);
        startBtn.addActionListener(e -> cardLayout.show(mainPanel, "games"));
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(startBtn, gbc);
        
        return panel;
    }
    
    private JPanel createGameScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Header
        JPanel header = createHeader();
        panel.add(header, BorderLayout.NORTH);
        
        // Games grid
        JPanel gamesPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        gamesPanel.setBackground(BG_COLOR);
        gamesPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        
        // Add game cards
        gamesPanel.add(createGameCard("sudoku"));
        gamesPanel.add(createGameCard("blackjack"));
        gamesPanel.add(createGameCard("airiddle"));
        gamesPanel.add(createGameCard("highstakes"));
        
        panel.add(gamesPanel, BorderLayout.CENTER);
        
        // Footer with exit button
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(BG_COLOR);
        JButton exitBtn = createSecondaryButton("Exit", 200, 45);
        exitBtn.setBackground(RED);
        exitBtn.setForeground(RED);
        exitBtn.addActionListener(e -> {
            int confirmed = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit MindEscape?", 
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        footer.add(exitBtn);
        panel.add(footer, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel title = new JLabel("Choose Your Game");
        title.setFont(pressStart2P.deriveFont(28f));
        title.setForeground(TEXT_PRIMARY);
        header.add(title, BorderLayout.WEST);
        
        return header;
    }
    
    private JPanel createGameCard(String gameKey) {
        GameInfo game = gameDescriptions.get(gameKey);
        
        JPanel card = new JPanel() {
            private boolean hovered = false;
            
            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        hovered = true;
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        repaint();
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        hovered = false;
                        setCursor(Cursor.getDefaultCursor());
                        repaint();
                    }
                });
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Card shadow
                if (hovered) {
                    g2.setColor(new Color(0, 0, 0, 30));
                    g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
                }
                
                // Card background
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Accent bar
                g2.setColor(game.accentColor);
                g2.fillRoundRect(0, 0, getWidth(), 6, 20, 20);
            }
        };
        
        card.setLayout(new BorderLayout(0, 15));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Game icon/title area
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        
        JLabel gameTitle = new JLabel(game.name);
        gameTitle.setFont(pressStart2P.deriveFont(16f));
        gameTitle.setForeground(TEXT_PRIMARY);
        titlePanel.add(gameTitle, BorderLayout.CENTER);
        
        card.add(titlePanel, BorderLayout.NORTH);
        
        // Description
        JTextArea description = new JTextArea(game.description);
        description.setFont(new Font("SansSerif", Font.PLAIN, 13));
        description.setForeground(TEXT_SECONDARY);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setOpaque(false);
        description.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        card.add(description, BorderLayout.CENTER);
        
        // Play button
        JButton playBtn = createSmallButton("Play Now", PRIMARY_COLOR); 
        playBtn.addActionListener(e -> launchGame(game));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(playBtn);
        card.add(btnPanel, BorderLayout.SOUTH);
        
        // Click on card to launch
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                launchGame(game);
            }
        });
        
        return card;
    }
    
    private JButton createPremiumButton(String text, int width, int height) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(PRIMARY_COLOR.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(PRIMARY_COLOR.brighter());
                } else {
                    g2.setColor(Color.WHITE);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                g2.setColor(getModel().isRollover() ? Color.WHITE : PRIMARY_COLOR);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
            }
        };
        
        btn.setPreferredSize(new Dimension(width, height));
        btn.setFont(pressStart2P.deriveFont(14f));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        return btn;
    }
    
    private JButton createSecondaryButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setFont(pressStart2P.deriveFont(12f));
        btn.setForeground(TEXT_SECONDARY);
        btn.setBackground(new Color(240, 240, 245));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(230, 230, 240));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(240, 240, 245));
            }
        });
        
        return btn;
    }
    
    private JButton createSmallButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(pressStart2P.deriveFont(10f));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        
        // --- YEH HAI FIX ---
        // Force the button to be opaque (draw its background)
        // System L&F sometimes ignores setBackground() without this
        btn.setOpaque(true);
        // ------------------

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 35));
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(color.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }
        });
        
        return btn;
    }
    
    private void launchGame(GameInfo game) {
        try {
            game.mainClass.getMethod("main", String[].class).invoke(null, (Object) new String[]{});
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Could not launch " + game.name + ". Check console for details.", 
                "Launch Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new GAME_HUB();
        });
    }
}
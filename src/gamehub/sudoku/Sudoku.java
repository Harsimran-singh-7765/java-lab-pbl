package gamehub.sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Sudoku {

    class Tile extends JButton {
        int r, c;

        Tile(int a, int b) {
            this.r = a;
            this.c = b;
        }
    }
    SoundManager Sound = new SoundManager();
    int board_width = 1000;
    int board_height = 705;
    JFrame frame = new JFrame("SUDOKU");
    JLabel textlabel = new JLabel();
    JPanel textpanel = new JPanel();
    JPanel BoardPanel = new JPanel();
    JPanel ButtonsPanel = new JPanel();
    JPanel winPanel = new JPanel();
    JLayeredPane layeredPane = new JLayeredPane();

    JButton numSelected = null;
    int errors = 0;
    Opening_Screen openingRef; 
    boolean isWin = false;
    Puzzles P =  new Puzzles();
    String[] puzzle = Puzzles.getPuzzle();
    String[] solution = Puzzles.getSolution(puzzle);


    int Dash;

    public Sudoku(Opening_Screen ref) {
        Dash = countDash();
        this.openingRef = ref;

        frame.setSize(board_width, board_height);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        textlabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        textlabel.setHorizontalAlignment(JLabel.CENTER);
        textlabel.setText(" 🧠 SUDOKU: 0 🧩");

         
        textpanel.add(textlabel);

        frame.add(textpanel, BorderLayout.NORTH);


        ButtonsPanel.setLayout(new GridLayout(1, 9));
        setupButtons();
        frame.add(ButtonsPanel, BorderLayout.SOUTH);

        layeredPane.setPreferredSize(new Dimension(board_width, board_height - 100));
        frame.add(layeredPane, BorderLayout.CENTER);

        BoardPanel.setLayout(new GridLayout(9, 9));
        BoardPanel.setBounds(0, 0, board_width, board_height - 100);
        setupTiles();

        winPanel.setBounds(0, 0, board_width, board_height - 100);
        setupWinPanel();
        winPanel.setVisible(false);

        layeredPane.add(BoardPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(winPanel, JLayeredPane.PALETTE_LAYER);

        frame.setVisible(true);
    }

    void setupButtons() {
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton(String.valueOf(i + 1));
            button.setFont(new Font("Arial", Font.BOLD, 30));
            button.setBackground(Color.white);
            button.setFocusable(false);
            button.setBorder(BorderFactory.createMatteBorder(5, 3, 5, 3, Color.black));
            ButtonsPanel.add(button);

            button.addActionListener(e -> {
                
                JButton B = (JButton) e.getSource();
                Sound.playClickSound();
                if (numSelected != null) {
                    numSelected.setBackground(Color.white);
                }
                numSelected = B;
                numSelected.setBackground(Color.lightGray);
            });
        }
    }

    int countDash() {
        int d = 0;
        for (String row : puzzle) {
            for (char ch : row.toCharArray()) {
                if (ch == '-') d++;
            }
        }
        return d;
    }

    void setupTiles() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Tile tile = new Tile(i, j);
                char ch = puzzle[i].charAt(j);

                if (ch != '-') {
                    tile.setText(String.valueOf(ch));
                    tile.setEnabled(false);
                    tile.setBackground(Color.lightGray);
                } else {
                    tile.setText("");
                    tile.setBackground(Color.white);
                }

                tile.setFont(new Font("Arial", Font.BOLD, 20));

                if ((i == 2 && j == 2) || (i == 2 && j == 5) || (i == 5 && j == 2) || (i == 5 && j == 5)) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, Color.black));
                } else if (i == 2 || i == 5) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 5, 1, Color.black));
                } else if (j == 2 || j == 5) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 5, Color.BLACK));
                } else {
                    tile.setBorder(BorderFactory.createLineBorder(Color.black));
                }

                tile.setFocusable(false);
                BoardPanel.add(tile);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Tile t = (Tile) e.getSource();
                        if (numSelected != null && t.getText().equals("") && !isWin) {
                            
                            String selectedText = numSelected.getText();
                            String correct = String.valueOf(solution[t.r].charAt(t.c));
                            if (selectedText.equals(correct)) {
                                Sound.playClickSound(); 
                                t.setText(selectedText);
                                Dash--;
                                if (Dash == 0) {
                                    ButtonsPanel.setVisible(false);
                                    winPanel.setVisible(true);
                                    isWin = true;
                                    Sound.playWinSound();
                                    frame.revalidate();
                                    frame.repaint();


                                }
                            } else {
                                Sound.playErrorSound(); 
                                errors++;
                                textlabel.setText("🧠 SUDOKU: " + errors + "  🧩");
                            }
                        }
                    }
                });
            }
        }
    }

    void setupWinPanel() {

        winPanel.setLayout(new BoxLayout(winPanel, BoxLayout.Y_AXIS));
        winPanel.setBackground(new Color(0, 0, 0, 180));

        JLabel winLabel = new JLabel("🎉 YOU WON! 🎉", SwingConstants.CENTER);
        winLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 30));
        winLabel.setForeground(Color.WHITE);
        winLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        winLabel.setMaximumSize(new Dimension(600, 30)); 

        JButton homeButton = new JButton("Back to Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 20));
        homeButton.setFocusable(false);
        homeButton.setBackground(Color.WHITE);
        homeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeButton.setMaximumSize(new Dimension(250, 50)); // LIMIT WIDTH & HEIGHT

        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sound.playClickSound();
                Sound.playBackgroundMusic();
                frame.dispose();
                openingRef.showOpeningScreen();
            }
        });

        winPanel.add(Box.createVerticalGlue());      // Pushes everything to center
        winPanel.add(winLabel);
        winPanel.add(Box.createVerticalStrut(20));
        winPanel.add(homeButton);
        winPanel.add(Box.createVerticalGlue());   
           

    }



}

package gamehub.airiddle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// NOTE: Replace 'gamehub.GAME_HUB' with the actual class that launches your main menu (e.g., gamehub.GAME_HUB or gamehub.Opening_Screen).
// import gamehub.GAME_HUB; 

public class AIRiddleGame extends JFrame {

    // Assuming you have a GeminiClient class for API calls
    private final GeminiClient client = new GeminiClient(); 
    
    private JTextArea riddleArea;
    private JTextField answerField;
    private JLabel statusLabel;
    private JButton submitButton;
    private JButton nextRiddleButton;
    private JButton mainMenuButton; // <--- NEW BUTTON
    private String currentRiddle = ""; 

    public AIRiddleGame() {
        super("AI Oracle Riddle Challenge");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setSize(600, 450);
        setLayout(new BorderLayout(10, 10));

        // --- START BACKGROUND MUSIC ---
        SoundManager.playBackgroundMusic(); 

        // --- North: Title and Status ---
        JPanel northPanel = new JPanel(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel("The AI Oracle's Riddle", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        
        statusLabel = new JLabel("Click 'New Riddle' to begin!", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        statusLabel.setForeground(Color.BLUE);
        
        northPanel.add(titleLabel);
        northPanel.add(statusLabel);
        add(northPanel, BorderLayout.NORTH);

        // --- Center: Riddle Display ---
        riddleArea = new JTextArea("Your mind-bending riddle will appear here...");
        riddleArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
        riddleArea.setLineWrap(true);
        riddleArea.setWrapStyleWord(true);
        riddleArea.setEditable(false);
        riddleArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(riddleArea);
        add(scrollPane, BorderLayout.CENTER);

        // --- South: Input and Buttons ---
        JPanel southPanel = new JPanel(new BorderLayout(10, 10));
        
        answerField = new JTextField();
        answerField.setFont(new Font("Arial", Font.PLAIN, 16));
        southPanel.add(answerField, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        
        // Submit Button Action
        submitButton = new JButton("Submit Answer");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setEnabled(false);
        submitButton.addActionListener(e -> {
            SoundManager.playClickSound(); // <-- Click Sound Added
            submitAnswer();
        });

        // New Riddle Button Action
        nextRiddleButton = new JButton("New Riddle");
        nextRiddleButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextRiddleButton.addActionListener(e -> {
            SoundManager.playClickSound(); // <-- Click Sound Added
            generateNewRiddle();
        });
        
        // Main Menu Button Action
        mainMenuButton = new JButton("Main Menu"); 
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 16));
        mainMenuButton.addActionListener(e -> {
            SoundManager.playClickSound(); // <-- Click Sound Added
            returnToMainMenu();
        });

        // --- Add all buttons to the panel ---
        buttonPanel.add(submitButton);
        buttonPanel.add(nextRiddleButton);
        buttonPanel.add(mainMenuButton); 
        
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Disposes the current frame and launches the main GAME_HUB screen.
     */
    private void returnToMainMenu() {
        // --- STOP BACKGROUND MUSIC BEFORE CLOSING ---
        SoundManager.stopBackgroundMusic();
        
        this.dispose(); 
        
        // --- PLACE YOUR ACTUAL MAIN MENU LAUNCH CODE HERE ---
        System.out.println("Returning to Main Hub...");
        // ---------------------------------------------------
        
    }

    /**
     * Handles the asynchronous call to the Gemini API for generating a riddle.
     */
    private void generateNewRiddle() {
        riddleArea.setText("Generating a riddle from the AI Oracle... Please wait...");
        statusLabel.setText("Thinking...");
        submitButton.setEnabled(false);
        nextRiddleButton.setEnabled(false);
        mainMenuButton.setEnabled(false); 
        answerField.setEnabled(false);

        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return client.generateRiddle();
            }

            @Override
            protected void done() {
                try {
                    String result = get();
                    riddleArea.setText(result);
                    
                    if (result.startsWith("Error")) { 
                        statusLabel.setText(result + ". Check console for details.");
                        currentRiddle = ""; 
                    } else {
                        currentRiddle = result; 
                        statusLabel.setText("Riddle is ready. Answer below!");
                        submitButton.setEnabled(true);
                        answerField.setEnabled(true);
                        answerField.setText("");
                    }
                    
                } catch (Exception e) {
                    riddleArea.setText("An unexpected error occurred: " + e.getMessage());
                    statusLabel.setText("Fatal Error.");
                    currentRiddle = "";
                } finally {
                    nextRiddleButton.setEnabled(true);
                    mainMenuButton.setEnabled(true); 
                }
            }
        }.execute();
    }
    
    /**
     * Handles the user submitting an answer and calls the Gemini API to check it.
     */
    private void submitAnswer() {
        String answer = answerField.getText().trim();
        if (currentRiddle.isEmpty() || answer.isEmpty()) {
            statusLabel.setText("Please enter an answer and ensure a riddle is loaded.");
            return;
        }

        statusLabel.setText("Checking answer with the AI Oracle... Please wait...");
        submitButton.setEnabled(false);
        nextRiddleButton.setEnabled(false);
        mainMenuButton.setEnabled(false); 
        answerField.setEnabled(false);

        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return client.checkAnswer(currentRiddle, answer);
            }

            @Override
            protected void done() {
                try {
                    String result = get();
                    
                    if (result.startsWith("API Error")) {
                        statusLabel.setText(result);
                    } else {
                        String[] parts = result.split(":", 2);
                        String status = parts[0].trim();
                        String message = parts.length > 1 ? parts[1].trim() : "AI response was unclear.";

                        if (status.equals("CORRECT")) {
                            statusLabel.setText("CORRECT! You solved it. " + message);
                            answerField.setEnabled(false);
                            submitButton.setEnabled(false);
                            SoundManager.playCorrectSound();
                        } else if (status.equals("CLOSE")) {
                            statusLabel.setText("CLOSE. You're almost there! " + message);
                            answerField.setEnabled(true);
                        } else { // INCORRECT or unclear
                            statusLabel.setText("INCORRECT. Try again or get a new riddle. " + message);
                            answerField.setEnabled(true);
                            SoundManager.playIncorrectSound();
                        }
                    }

                } catch (Exception e) {
                    statusLabel.setText("An unexpected error occurred during answer check: " + e.getMessage());
                } finally {
                    if (!statusLabel.getText().startsWith("CORRECT")) {
                        submitButton.setEnabled(true);
                        answerField.setEnabled(true);
                    }
                    nextRiddleButton.setEnabled(true);
                    mainMenuButton.setEnabled(true); 
                }
            }
        }.execute();
    }
}
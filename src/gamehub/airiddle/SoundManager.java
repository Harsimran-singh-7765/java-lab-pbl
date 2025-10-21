package gamehub.airiddle;

import javax.sound.sampled.*;
import java.io.InputStream;
import java.io.IOException;

public class SoundManager {

    private static Clip backgroundClip; // Dedicated Clip for continuous background music

    // Helper method to get the audio input stream from the classpath
    private static AudioInputStream getAudioStream(String filepath) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        // Path assumes files are in src/main/resources/sounds/airiddle/
        InputStream is = SoundManager.class.getResourceAsStream(filepath);
        if (is == null) {
            // Throw an exception if the file isn't found
            throw new java.io.FileNotFoundException("Resource not found: " + filepath);
        }
        // AudioSystem.getAudioInputStream needs a markable stream, which getResourceAsStream provides.
        return AudioSystem.getAudioInputStream(is);
    }

    // --- Background Music Method ---
    public static void playBackgroundMusic() {
        // Stop current BGM if it's playing before starting a new one
        stopBackgroundMusic(); 
        
        try {
            AudioInputStream audioStream = getAudioStream("/sounds/airiddle/background.wav");
            
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioStream);

            // Adjust volume (optional, but often good for BGM)
            if (backgroundClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                 FloatControl volumeControl = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
                 volumeControl.setValue(-12.0f); // Set to a moderate volume
            }
            
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundClip.start();
            
        } catch (java.io.FileNotFoundException fnfe) {
            System.err.println("Background music file missing: " + fnfe.getMessage());
        } catch (Exception e) {
            System.err.println("Error playing background music.");
            e.printStackTrace();
        }
    }
    
    // Method to stop BGM
    public static void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
            backgroundClip = null; // Clear the reference
        }
    }
    
    // --- CONSOLIDATED playSoundOnce HELPER METHOD (NO DUPLICATE) ---
    private static void playSoundOnce(String filepath) {
        try {
            AudioInputStream audioStream = getAudioStream(filepath);
            
            // Do NOT use try-with-resources on Clip, as it closes immediately.
            final Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // CRITICAL FIX: Add a listener to close the clip only after it stops
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    event.getLine().close();
                    // Close the audio stream associated with the clip after it's done
                    try {
                        audioStream.close(); 
                    } catch (IOException ignored) {}
                }
            });
            
            clip.start();
            
        } catch (java.io.FileNotFoundException fnfe) {
            System.err.println("Sound file missing for AI Riddle: " + fnfe.getMessage());
        } catch (Exception e) {
            System.err.println("Error playing sound for AI Riddle: " + filepath);
            e.printStackTrace();
        }
    }
    // ------------------------------------------------------------------

    // --- NEW: Play Click Sound ---
    public static void playClickSound() {
        // Uses the single playSoundOnce helper method
        playSoundOnce("/sounds/airiddle/click.wav"); 
    }
    
    public static void playCorrectSound() {
        // FIX: Assumes /sounds/airiddle/celebration.wav exists 
        playSoundOnce("/sounds/airiddle/celebration.wav"); 
    }

    public static void playIncorrectSound() {
        // FIX: Assumes /sounds/airiddle/loss.wav exists 
        playSoundOnce("/sounds/airiddle/loss.wav");
    }
}
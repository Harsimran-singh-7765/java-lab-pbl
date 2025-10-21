package gamehub.BlackJack;

import javax.sound.sampled.*;
// import java.io.File; // Removed, as it's not used
// import java.io.IOException; // Removed, as it's not used

public class Sound_Manager {

    private static Clip backgroundClip;
    private static Clip celebrationClip;

    public static void playBackgroundMusic() {
        stopClip(celebrationClip);  
        // UPDATED path to reflect move to src/main/resources/sounds/blackjack
        backgroundClip = playLoopingSound("/sounds/blackjack/background.wav"); 
    }

    public static void playCardHit() {
        // UPDATED path
        playSoundOnce("/sounds/blackjack/hit.wav"); 
    }

    public static void playCelebration() {
        stopClip(backgroundClip);  
        // UPDATED path
        celebrationClip = playSoundOnce("/sounds/blackjack/celebration.wav");
    }

    public static void playloss() {
        stopClip(backgroundClip);  
        // UPDATED path
        celebrationClip = playSoundOnce("/sounds/blackjack/loss.wav");
    }

    public static void playClick() {
        // UPDATED path
        playSoundOnce("/sounds/blackjack/shuffle.wav");
    }
    
    public static void stopAllSounds() {
        stopClip(backgroundClip);
        stopClip(celebrationClip);
    }

    private static Clip playLoopingSound(String filepath) {
        try {
            // Sound_Manager.class.getResourceAsStream(filepath) will now correctly look 
            // inside the JAR/classpath at the /sounds/blackjack/ location.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
            Sound_Manager.class.getResourceAsStream(filepath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            return clip;
        } catch (Exception e) {
            System.err.println("Error loading sound: " + filepath);
            e.printStackTrace();
            return null;
        }
    }

    private static Clip playSoundOnce(String filepath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
            Sound_Manager.class.getResourceAsStream(filepath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            return clip;
        } catch (Exception e) {
            System.err.println("Error loading sound: " + filepath);
            e.printStackTrace();
            return null;
        }
    }

    private static void stopClip(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
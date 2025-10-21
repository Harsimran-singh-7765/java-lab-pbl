package gamehub.sudoku;

import javax.sound.sampled.*;

public class SoundManager {
    private static Clip backgroundClip;  
    private static Clip winClip; 
    
    public void stopAllSounds() {
        stopClip(backgroundClip);
        stopClip(winClip);
    }
    
    public static void playBackgroundMusic() {
        try {
            // UPDATED path to /sounds/sudoku/bgm.wav
            System.out.println("Trying to load: " + SoundManager.class.getResource("/sounds/sudoku/bgm.wav"));
            
            if (winClip != null && winClip.isRunning()) {
                winClip.stop();
                winClip.close();
                System.out.println("winning music stopped.");
            } 
            
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                SoundManager.class.getResource("/sounds/sudoku/bgm.wav")
            );

            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audio);
            FloatControl volumeControl = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-15.0f); // Lower volume
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.err.println("Error loading background music: /sounds/sudoku/bgm.wav");
            e.printStackTrace();
        }
    }

    private static void stopClip(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    public void playClickSound() {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                // UPDATED path to /sounds/sudoku/click.wav
                SoundManager.class.getResource("/sounds/sudoku/click.wav")
            );
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
            System.out.println("CLICKED");
        } catch (Exception e) {
            System.err.println("Error loading click sound: /sounds/sudoku/click.wav");
            e.printStackTrace();
        }
    }

    public void playErrorSound() {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                // UPDATED path to /sounds/sudoku/error.wav
                SoundManager.class.getResource("/sounds/sudoku/error.wav")
            );
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
            System.out.println("ERROR");
        } catch (Exception e) {
            System.err.println("Error loading error sound: /sounds/sudoku/error.wav");
            e.printStackTrace();
        }
    }

    public void playWinSound() {
        try {
            
            if (backgroundClip != null && backgroundClip.isRunning()) {
                backgroundClip.stop();
                backgroundClip.close();
                System.out.println("Background music stopped.");
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(
                // UPDATED path to /sounds/sudoku/win.wav
                SoundManager.class.getResource("/sounds/sudoku/win.wav")
            );
            winClip = AudioSystem.getClip();
            winClip.open(audio);
            winClip.start();
            System.out.println("WIN sound played.");
        } catch (Exception e) {
            System.err.println("Error loading win sound: /sounds/sudoku/win.wav");
            e.printStackTrace();
        }
    }
}
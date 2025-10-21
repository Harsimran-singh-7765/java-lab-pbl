# BlackJack Java Game

## Overview
This is a simple BlackJack card game built using Java Swing. It simulates classic BlackJack gameplay including dealer and player hands, dynamic Ace value adjustment, card image rendering, and sound effects for immersive play. The game displays messages like "YOU WIN!!!" or "YOU LOSE!!!" and automatically resets after the game ends.

## Project Structure
/src  
├── BlackJack.java  
├── Sound_Manager.java   
├── App.java
├── Opening_Screen.java    
├── sounds  
│   ├── background.wav  
│   ├── card_hit.wav  
│   ├── celebration.wav  
│   └── click.wav  
/cards  
├── (card images such as A-C.png, 10-H.png, BACK.png, etc.)  

- `src/` contains all the Java source files.  
- `/sounds/` contains all required sound `.wav` files.  
- `cards/` folder contains all the card image PNG files

---
## Features
- Full standard 52-card deck implementation with suits and values.  
- Dynamic handling of Ace cards (counts as 1 or 11 depending on hand sum).  
- Player controls: "Hit" and "Stay" buttons.  
- Dealer logic: dealer draws cards until reaching sum >= 17.  
- Graphical rendering of cards using PNG images for both dealer and player hands.  
- Displays game results with large font messages on screen.  
- Sound effects: background music, card hit sound, celebration, and click sound on buttons.  
- Auto resets to opening screen 2 seconds after game ends.
---
## How to Run

### Step 1: Clone the Project Repository

Open your terminal or command prompt and run:
1. Clone the repo
```bash
git clone <repository-url>
```
1. double click  run.bat OR
```bash 
    java APP
```
---


## Sound_Manager Class Details
- `play_Background_music()`: Plays looping background music and stops celebration if playing.  
- `play_card_hit()`: Plays card hit sound effect.  
- `play_celebration()`: Plays celebration sound and stops background music.  
- `play_click()`: Plays button click sound effect.  

All sounds are loaded as resources from `/resources/sounds/`.

## Notes
- Fixed window size: 600x600 pixels.  
- Make sure all sound and image resources are accessible via classpath.  
- The game auto-disposes and resets the frame 2 seconds after finishing a game.  
- Sound playback uses Java `Clip` API managed in the `Sound_Manager` class.

## Dependencies
- Java Development Kit (JDK) 8 or higher.  
- No external dependencies or libraries required.

---
## MADE WITH ❤ Harsimran SIngh
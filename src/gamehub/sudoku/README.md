# 🧩 Sudoku Java Game

Welcome to **Sudokuku** – a clean and engaging Sudoku game built in Java with Swing! This project is built with care, featuring cool background music 🎶, sound effects 🔊, and an intuitive UI.

## 🎮 Features

- ✅ Simple, user-friendly interface
- 🔊 Background music & click/win/error sound effects
- 💡 Visual feedback when a number is selected
- 🏆 Win detection with animation and sound
- 🎯 “Go Back” resets music & game smoothly

---

## 🗂️ Project Structure


📁 Sudokuku/  
│  
├── 📁 src/  
│   ├── 📄 App.java              # Entry point  
│   ├── 📄 Opening_Screen.java   # Welcome screen with animations  
│   ├── 📄 Sudoku.java           # Core game logic and grid  
│   ├── 📄 SoundManager.java     # Handles all sound effects & music  
│   └── 📁 sounds/               # Sound files (.wav)  
│       ├── 🎵 bgm.wav  
│       ├── 🎵 click.wav  
│       └── 🎵 win.wav  
│  
└── 📄 README.md


---

## 🔧 Requirements

- Java 8 or above
- No external libraries – everything is standard Java!

---

## ▶️ How to Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/sudokuku.git
   cd sudokuku/src
   ```
2. **Compile and Run**:
    ```bash
    javac App.java
    java App
    ```

## 🔉 Sound Features

- **Background Music**: Loops continuously in the background.
- **Click Sound**: Plays when a number is selected.
- **Win Sound**: Plays upon successfully solving the puzzle.
- **Sound Manager**: Handles volume and prevents overlap.

🎧 Make sure your `/sounds` folder is inside the `src` directory and contains:
- `bgm.wav`
- `click.wav`
- `win.wav`

> 🔔 Tip: Use `.wav` files for best compatibility with `javax.sound.sampled`.

---

## 👩‍💻 Developer Notes

- Developed with ❤️ using Java Swing.
- Ideal for beginners to explore **GUI**, **OOP**, and **multimedia** in Java.
- Inspired by the classic Sudoku experience – with a modern twist!

---

## 📸 Screenshots

_(Add screenshots here by dragging them into your repo or using the Markdown image syntax)_

```markdown
![Game Screenshot](screenshots/game.png)



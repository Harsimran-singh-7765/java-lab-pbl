# MindEscape: A Java Swing Game Hub

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=apachemaven)
![Gemini](https://img.shields.io/badge/Google%20Gemini-4285F4?style=for-the-badge&logo=google%20gemini)

A robust and user-friendly desktop application built with Java Swing. MindEscape serves as a cohesive "Game Hub" that consolidates four unique, standalone Java games into a single, visually appealing, and easy-to-navigate launcher.

![MindEscape Game Hub](https://i.imgur.com/your-screenshot-url.png)
*(Recommended: Replace the URL above with a screenshot of your main game selection screen)*

---

## 🎮 Core Features

* **Centralized Game Hub:** A main menu built with `CardLayout` to provide a seamless flow from the welcome screen to game selection.
* **Modern "Prismarine" UI:** A custom-themed interface using the `PressStart2P` font and custom-painted components, bypassing the default Java look-and-feel.
* **Four Unique Games:** Includes BlackJack, Sudoku, an AI-powered Riddle game, and a High-Stakes card game.
* **AI Integration:** The "AI Riddle Challenge" uses the Google Gemini API to generate and judge riddles in real-time.
* **Asynchronous Loading:** Implements `SwingWorker` in the AI Riddle game to call the external API without freezing the UI.
* **Dynamic Game Launching:** Uses a `HashMap` to store game metadata and Reflection to launch each game module.
* **Build Automation:** Managed by Apache Maven for dependency handling and a simple `run.sh` script for easy compilation and execution.

## 🕹️ The Games

### 🃏 BlackJack Elite
A classic game of 21.
* **Features:** Custom-painted graphics using `paintComponent` to render cards, a dedicated `Sound_Manager` for background music and effects, and an `Opening_Screen` that acts as a sub-menu for the game.

### 🧠 AI Riddle Challenge
A text-based riddle game against a live AI.
* **Features:** Connects directly to the **Google Gemini API**. A `GeminiClient` wrapper class handles all API communication. The UI uses `SwingWorker` to make asynchronous API calls, ensuring the application remains responsive while waiting for the AI to "think."

### 💎 High Stakes Showdown
A fast-paced, high-stakes card comparison game.
* **Features:** Uses interactive `JButtons` to represent the player's hand. The game flow is managed by `javax.swing.Timer` to create dramatic pauses and auto-close the window on game over, returning the user to the main hub.

### 🔢 Sudoku Master
The classic 9x9 logic puzzle.
* **Features:** The core logic is powered by a recursive **backtracking algorithm** in the `Puzzles.java` utility class. This algorithm can solve any valid Sudoku board, which is used to provide solutions.

---

## 🛠️ Tech Stack & Architecture

* **Core:** Java (JDK 17+)
* **UI:** Java Swing (using `JFrame`, `JPanel`, `CardLayout`, and custom painting)
* **Build/Dependency:** Apache Maven
* **AI:** Google Gemini API (`google-genai` SDK)
* **Scripting:** Bash (`run.sh`)

The application is structured around a central `GAME_HUB.java` class that acts as the main entry point. It uses a `HashMap` to store `GameInfo` objects, which hold the metadata for each game (title, description, and main class). When a user clicks "Play," the hub uses **Java Reflection** to dynamically find and execute the `main` method of the chosen game.

## 🌊 Application Flowchart

This flowchart illustrates the navigation flow from launch to playing a game and returning to the hub.

```mermaid
graph TD
    A[Start: User runs ./run.sh] --> B{Maven Build};
    B -- Success --> C[Run mvn exec:java];
    B -- Fail --> D[Show Error in Terminal];
    C --> E["GAME_HUB.main() launches"];
    E --> F(MindEscape Welcome Screen);
    F -- Click 'Start Journey' --> G(Game Selection Screen);
    G -- Click 'Play Sudoku' --> H[Launch gamehub.sudoku.App];
    G -- Click 'Play BlackJack' --> I[Launch gamehub.BlackJack.App];
    G -- Click 'AI Riddle' --> J[Launch gamehub.airiddle.App];
    G -- Click 'High Stakes' --> K[Launch gamehub.highstakesshowdown.HighStakesShowdown];
    G -- Click 'Exit MindEscape' --> L[Confirm Exit];
    L -- Yes --> M["System.exit(0)"];
    L -- No --> G;
    
    subgraph Game Loop
        I --> I_Game[BlackJack Game Window Opens];
        J --> J_Game[AI Riddle Game Window Opens];
        K --> K_Game[High Stakes Window Opens];
        H --> H_Game[Sudoku Game Window Opens];
    end

    subgraph Return to Hub
        I_Game -- Click 'Main Menu' --> G;
        J_Game -- Click 'Main Menu' --> G;
        K_Game -- Game Over / Auto-Dispose --> G;
        H_Game -- Click 'Main Menu' or 'X' --> G;
    end
````

-----

## 🚀 How to Run

### Prerequisites

  * Java (JDK 17 or higher)
  * Apache Maven

### Installation & Launch

1.  **Clone the repository:**

    ```sh
    git clone [https://github.com/Harsimran-singh-7765/java-lab-pbl.git](https://github.com/Harsimran-singh-7765/java-lab-pbl.git)
    ```

2.  **Navigate to the project directory:**

    ```sh
    cd java-lab-pbl
    ```

3.  **Make the run script executable** (Only need to do this once):

    ```sh
    chmod +x run.sh
    ```

4.  **Run the application:**

    ```sh
    ./run.sh
    ```

    The script will first run `mvn compile` to build the project and download dependencies, then run `mvn exec:java` to launch the application.

> **Note on AI Riddle Game:**
> The project uses a hard-coded API key in `src/main/java/gamehub/airiddle/GeminiClient.java`. This key may be expired or disabled. If the AI Riddle game fails, you will need to replace `HARDCODED_API_KEY` with your own Google Gemini API key.

##  Future Enhancements

  * **Persistent High Scores:** Implement a system to save scores to a local file (JSON) or a lightweight database (SQLite).
  * **Central Sound Engine:** Create a sound manager for the main hub to add background music and UI sound effects.
  * **Embedded Game Panels:** Refactor the games from `JFrames` to `JPanels` so they can be rendered *inside* the main hub window, creating a more seamless, single-window experience.

## Authors

  * **Harsimran Singh** - 2401030148
  * **Aryan Sharma** - 2401030157
  * **Hardik Chauhan** - 2401030162

##  License

This project is licensed under the MIT License - see the `LICENSE` file for details.


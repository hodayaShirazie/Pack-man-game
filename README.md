# Pac-Man Game

A Java-based implementation of the classic **Pac-Man** game, showcasing object-oriented principles, multi-threading, desighn patterns and GUI.

---

## Features

1. **Game Mechanics:**
   - Control Pac-Man using arrow keys to navigate the board.
   - Ghosts move independently as threads, appearing and disappearing randomly.
   - Dynamic game states, including active gameplay, pause, and game over.

2. **Design Patterns:**
   - **Singleton:** Ensures a single instance of the game manager to control the game logic.
   - **Prototype:** Allows cloning of game objects, such as ghosts, to enable efficient object creation.
   - **Callback:** Implements event-driven logic for player interactions and game updates.
   - **State:** Manages different game states (e.g., play, pause, end) dynamically.

3. **Multi-threading:**
   - Each ghost operates as a separate thread, ensuring non-blocking movement.
   - Smooth player and ghost animations for an engaging user experience.

4. **Graphical User Interface:**
   - A user-friendly interface built with Java Swing.
   - Supports resizing and dynamic rendering of game elements.

---

## Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/hodayaShirazie/Pac-Man-Game.git
   ```

2. **Compile the Code**
   Ensure you have Java JDK installed (version 11 or higher).
   ```bash
   javac -d bin src/*.java
   ```

3. **Run the Game**
   ```bash
   java -cp bin Main
   ```

---

## How It Works

1. **Game Manager (Singleton):**
   - Centralized control for managing game states, player scores, and interactions.
   - Ensures only one instance handles all logic, preventing conflicts.

2. **Prototype for Ghosts:**
   - Enables efficient creation of new ghost instances during the game.
   - Avoids the overhead of initializing from scratch by cloning existing templates.

3. **Callback for Interactions:**
   - Player movement and ghost interactions trigger callbacks for real-time updates.

4. **State Management:**
   - The game dynamically transitions between states (e.g., "playing", "paused") using a State pattern implementation.

5. **Multi-threading:**
   - Ghosts move independently on the screen as threads.
   - Prevents UI freezing and ensures a smooth gameplay experience.

---

## Screenshot

![Pac-Man Screenshot](src/Images/Screenshot%202024-11-23%20191442.png)

---

## Contributions

Contributions are welcome! Feel free to open issues or submit pull requests to improve the game.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Screenshots

![Pac-Man Screenshot]("https://github.com/hodayaShirazie/Pack-man-game/blob/main/TheGameBoard.png")


---

Enjoy playing Pac-Man while exploring software design principles! 🎮

1. **Clone the Repository**
   ```bash
   git clone https://github.com/hodayaShirazie/Pac-Man-Game.git
   cd Pac-Man-Game

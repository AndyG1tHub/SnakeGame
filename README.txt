Snake Game – Assignment 1
Course: 159.261 Games Programming
Author：Mingqi Guo 24009196

----------------------------------------
1. Project Description
----------------------------------------
This project is a Java implementation of the classic Snake game using the provided GameEngine.

The player controls a snake that moves continuously on a grid. The objective is to collect apples to grow longer while avoiding collisions with the walls or itself.

This version of the game also includes additional features such as multiple apples, a bad apple, and a menu system.

----------------------------------------
2. How to Run the Program
----------------------------------------
1. Open the project in IntelliJ IDEA (or any Java IDE).
2. Make sure all resource images are located in the "resources" folder:
   - apple.png
   - badapple.png
   - head.png
   - dot.png
3. Run the Snake.java file.
4. The game window will appear with a start menu.

----------------------------------------
3. Controls
----------------------------------------
Menu:
- ENTER: Start the game
- H: Open Help screen
- B: Back to menu (from Help)
- Q: Quit the game

In Game:
- Arrow Keys:
  ↑ : Move up
  ↓ : Move down
  ← : Move left
  → : Move right

Game Over:
- R: Restart the game
- Q: Quit the game

----------------------------------------
4. Game Rules
----------------------------------------
- The snake moves continuously in the current direction.
- The player cannot reverse direction directly.
- The game ends if:
  - The snake hits the wall
  - The snake hits itself
  - The snake becomes too short after eating a bad apple

----------------------------------------
5. Game Features
----------------------------------------
Core Features:
- Snake movement using keyboard input
- Collision detection (walls and self)
- Snake grows when eating apples
- Grid-based game world
- Maximum snake length of 20

Additional Features:
- Start menu (Start / Help / Quit)
- Help screen with instructions
- Two normal apples (green):
  → Increase snake length
- One bad apple (blue):
  → Decreases snake length by 1
  → If the snake becomes too short, the game ends
- Restart option after game over
- Quit option from menu and game over screen
- Game Grid easier to see where the snake will move to
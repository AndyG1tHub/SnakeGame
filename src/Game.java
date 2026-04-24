import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game extends GameEngine {

    public static void main(String[] args) {
        createGame(new Game());
    }

    private boolean gameOver;
    private boolean inMenu;
    private boolean inHelp;

    private boolean[][] grid = new boolean[50][50];

    private Body snake;
    ArrayList<Food> foods = new ArrayList<>();

    private Image apple = loadImage("resources/apple.png");
    private Image dot = loadImage("resources/dot.png");
    private Image head = loadImage("resources/head.png");
    private Image badApple = loadImage("resources/badapple.png");

    private double timer = 0;
    private double moveDelay = 0.2;
    private int lives;

    public void init() {
        snake = new Body(grid);

        gameOver = false;
        inMenu = true;
        inHelp = false;
    }

    public void startGame() {
        gameOver = false;
        inMenu = false;
        inHelp = false;
        timer = 0;
        lives = 4;

        snake.reset();

        foods.clear();
        foods.add(new Food(false));
        foods.add(new Food(false));
        foods.add(new Food(true));

        for (Food food : foods) {
            food.randomFood(grid, foods);
        }
    }

    public void drawMap() {
        changeColor(white);
        for (int i = 0; i <= 50; i++) {
            drawLine(0, i * 10, 500, i * 10, 0.05);
            drawLine(i * 10, 0, i * 10, 500, 0.05);
        }
    }

    public Food foodAt(int x, int y) {
        for (Food food : foods) {
            if (food.isAt(x, y)) {
                return food;
            }
        }
        return null;
    }

    public void updateSnake() {
        Point next = snake.getNextHead();

        Food eatenFood = foodAt(next.getX(), next.getY());
        boolean grow = eatenFood != null && !eatenFood.isBad();

        snake.move(grow);

        if (snake.isGameOver()) {
            lives--;

            if (lives > 0) {
                snake.reset();
                timer = 0;
            } else {
                gameOver = true;
            }
            return;
        }

        if (eatenFood != null) {
            eatenFood.randomFood(grid, foods);

            if (eatenFood.isBad()) {
                snake.shorten();

                if (snake.isGameOver()) {
                    lives--;

                    if (lives > 0) {
                        snake.reset();
                        timer = 0;
                    } else {
                        gameOver = true;
                    }
                }
            }
        }
    }

    public void drawSnake() {
        drawImage(head,
                snake.getBody().getFirst().getX() * 10,
                snake.getBody().getFirst().getY() * 10,
                9, 9);

        for (int i = 1; i < snake.getBody().size(); i++) {
            Point point = snake.getBody().get(i);
            drawImage(dot, point.getX() * 10, point.getY() * 10, 9, 9);
        }
    }

    public void drawFoods() {
        for (Food food : foods) {
            if (food.isBad()) {
                drawImage(badApple, food.getX() * 10 + 0.5, food.getY() * 10 + 0.5, 9, 9);
            } else {
                drawImage(apple, food.getX() * 10 + 0.5, food.getY() * 10 + 0.5, 9, 9);
            }
        }
    }
    public void drawMenu() {
        changeBackgroundColor(black);
        clearBackground(500, 500);
        changeColor(white);
        drawText(140, 120, "SNAKE GAME", "Arial", 35);
        drawText(140, 220, "Press ENTER to Start", "Arial", 24);
        drawText(140, 270, "Press H for Help", "Arial", 24);
        drawText(140, 320, "Press Q to Quit", "Arial", 24);
    }

    public void drawHelp() {
        changeBackgroundColor(black);
        clearBackground(500, 500);
        changeColor(white);
        drawText(180, 80, "HELP", "Arial", 30);
        drawText(60, 150, "Use Arrow Keys to control the snake", "Arial", 20);
        drawText(60, 190, "Green apple: Grow longer", "Arial", 20);
        drawText(60, 230, "Blue apple: Be shortened", "Arial", 20);
        drawText(60, 270, "Do not hit the wall or yourself", "Arial", 20);
        drawText(60, 310, "You have 4 lives before the game is over", "Arial", 20);
        drawText(60, 350, "Press B to go back", "Arial", 20);
    }

    public void drawGameOver() {
        changeColor(white);
        drawText(85, 250, "GAME OVER!", "Arial", 50);
        drawText(120, 300, "Press R to Restart", "Arial", 22);
        drawText(120, 340, "Press Q to Quit", "Arial", 24);
    }

    public void drawLives() {
        changeColor(white);
        drawText(10, 20, "Lives: " + lives, "Arial", 20);
    }

    public void keyPressed(KeyEvent e) {
        if (inMenu) {
            if (!inHelp) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    startGame();
                }
                if (e.getKeyCode() == KeyEvent.VK_H) {
                    inHelp = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    System.exit(0);
                }
            } else {
                if (e.getKeyCode() == KeyEvent.VK_B) {
                    inHelp = false;
                }
            }
        } else if (gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                startGame();
            }
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                System.exit(0);
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                snake.setDirection(2);
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                snake.setDirection(3);
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                snake.setDirection(0);
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                snake.setDirection(1);
            }
        }
    }

    @Override
    public void update(double dt) {
        if (!gameOver && !inMenu) {
            timer += dt;
            if (timer >= moveDelay) {
                updateSnake();
                timer = 0;
            }
        }
    }

    @Override
    public void paintComponent() {
        if (inMenu) {
            if (inHelp) {
                drawHelp();
            } else {
                drawMenu();
            }
            return;
        }

        changeBackgroundColor(black);
        clearBackground(500, 500);
        drawMap();
        drawLives();

        if (!gameOver) {
            drawSnake();
            drawFoods();
        } else {
            drawGameOver();
        }
    }
}
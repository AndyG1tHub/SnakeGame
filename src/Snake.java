import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Snake extends GameEngine {
    public static void main(String[] args) {
        createGame(new Snake());
    }

    class Point {
        private int x, y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        void setPoint(int x, int y){
            this.x = x;
            this.y = y;
        }
        void take(){
            grid[x][y] = true;
        }
        void release(){
            grid[x][y] = false;
        }
        int getX(){
            return x;
        }
        int getY(){
            return y;
        }
    }

    boolean gameOver, inMenu, inHelp;
    boolean[][] grid = new boolean[50][50];
    int direction;  //up,down,left,right  0,1,2,3
    ArrayList<Point> body = new ArrayList<>();
    int applePointX1, applePointY1;
    int applePointX2, applePointY2;
    int badApplePointX, badApplePointY;

    Image apple = loadImage("resources/apple.png");
    Image dot = loadImage("resources/dot.png");
    Image head = loadImage("resources/head.png");
    Image badApple = loadImage("resources/badapple.png");

    public void randomApple1(){
        do{
            applePointX1 = rand(50);
            applePointY1 = rand(50);
        }while (grid[applePointX1][applePointY1]
                || (applePointX1 == applePointX2 && applePointY1 == applePointY2)
                || (applePointX1 == badApplePointX && applePointY1 == badApplePointY));
    }

    public void randomApple2(){
        do{
            applePointX2 = rand(50);
            applePointY2 = rand(50);
        }while (grid[applePointX2][applePointY2]
                || (applePointX2 == applePointX1 && applePointY2 == applePointY1)
                || (applePointX2 == badApplePointX && applePointY2 == badApplePointY));
    }

    public void randomBadApple(){
        do{
            badApplePointX = rand(50);
            badApplePointY = rand(50);
        }while (grid[badApplePointX][badApplePointY]
                || (badApplePointX == applePointX1 && badApplePointY == applePointY1)
                || (badApplePointX == applePointX2 && badApplePointY == applePointY2));
    }


    public void init() {
        gameOver = false;
        inMenu = true;
        inHelp = false;
    }
    public void startGame(){
        gameOver = false;
        inMenu = false;
        inHelp = false;

        body.clear();
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                grid[i][j] = false;
            }
        }

        body.add(new Point(25, 25));
        body.add(new Point(25, 26));
        body.add(new Point(25, 27));
        grid[25][25]=true;
        grid[25][26]=true;
        grid[25][27]=true;

        randomApple1();
        randomApple2();
        randomBadApple();
        direction = 0;
    }


    public void drawMap(){
        changeColor(white);
        for(int i=0; i<= 50; i++){
            drawLine(0,i*10,500,i*10,0.05);
            drawLine(i*10,0,i*10,500,0.05);
        }
    }


    public void upDataSnake(){
        Point temp = new Point(body.getFirst().getX(), body.getFirst().getY());

        switch (direction){
            case 0://up
                temp.setPoint(temp.getX(), temp.getY() - 1);
                break;
            case 1://down
                temp.setPoint(temp.getX(), temp.getY() + 1);
                break;
            case 2://left
                temp.setPoint(temp.getX() - 1, temp.getY());
                break;
            case 3://right
                temp.setPoint(temp.getX() + 1, temp.getY());
                break;
        }

        if (temp.getX() < 0 || temp.getX() >= 50 || temp.getY() < 0 || temp.getY() >= 50) {
            gameOver = true;
            return;
        }

        boolean eatApple1 = (temp.getX() == applePointX1 && temp.getY() == applePointY1);
        boolean eatApple2 = (temp.getX() == applePointX2 && temp.getY() == applePointY2);
        boolean eatBadApple = (temp.getX() == badApplePointX && temp.getY() == badApplePointY);
        boolean eatNormalApple = eatApple1 || eatApple2;

        if (!eatNormalApple) {
            Point tail = body.getLast();
            tail.release();
        }

        if (grid[temp.getX()][temp.getY()]) {
            gameOver = true;
            return;
        }
        body.addFirst(temp);
        temp.take();

        if (eatApple1) {
            randomApple1();
        }
        else if (eatApple2) {
            randomApple2();
        }
        else {
            body.getLast().release();
            body.removeLast();
        }
        if (eatBadApple) {
            randomBadApple();
            if (body.size() <= 1) {
                gameOver = true;
                return;
            }
            Point tail = body.getLast();
            tail.release();
            body.removeLast();
            if (body.size()  <= 1) {
                gameOver = true;
                return;
            }
        }
        if (body.size() > 20) {
            Point tail = body.getLast();
            tail.release();
            body.removeLast();
        }
    }


    public void drawSnake(){
        drawImage(head, body.getFirst().getX() * 10, body.getFirst().getY() * 10, 9, 9);
        for (int i = 1; i < body.size(); i++) {
            Point point = body.get(i);
            drawImage(dot, point.getX() * 10, point.getY() * 10, 9, 9);
        }
    }

    public void drawApple(){
        drawImage(apple, applePointX1 * 10 + 0.5, applePointY1 * 10 + 0.5, 9, 9);
        drawImage(apple, applePointX2 * 10 + 0.5, applePointY2 * 10 + 0.5, 9, 9);
        drawImage(badApple, badApplePointX * 10 + 0.5, badApplePointY * 10 + 0.5, 9, 9);
    }

    //Menu
    public void drawMenu(){
        changeBackgroundColor(black);
        clearBackground(500, 500);
        changeColor(white);
        drawText(140, 120, "SNAKE GAME", "Arial", 35);
        drawText(140, 220, "Press ENTER to Start", "Arial", 24);
        drawText(140, 270, "Press H for Help", "Arial", 24);
        drawText(140, 320, "Press Q to Quit", "Arial", 24);
    }
    public void drawHelp(){
        changeBackgroundColor(black);
        clearBackground(500, 500);
        changeColor(white);
        drawText(180, 80, "HELP", "Arial", 30);
        drawText(60, 150, "Use Arrow Keys to control the snake", "Arial", 20);
        drawText(60, 190, "Green apple: Grow longer", "Arial", 20);
        drawText(60, 230, "Blue apple: Shrink (lose one block)", "Arial", 20);
        drawText(60, 270, "Do not hit the wall or yourself", "Arial", 20);
        drawText(60, 310, "Press B to go back", "Arial", 20);
    }
    public void drawGameOver(){
        changeColor(white);
        drawText(85, 250, "GAME OVER!", "Arial", 50);
        drawText(120, 300, "Press R to Restart", "Arial", 22);
        drawText(120, 340, "Press Q to Quit", "Arial", 24);
    }

    public void keyPressed(KeyEvent e){
        if(inMenu){
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

        }
        else if (gameOver){
            if (e.getKeyCode() == KeyEvent.VK_R) {
                startGame();
            }
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                System.exit(0);
            }
        }
        else{
            if (e.getKeyCode() == KeyEvent.VK_LEFT && direction!=3){
                direction = 2;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT && direction!=2){
                direction = 3;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP && direction!=1){
                direction = 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN && direction!=0){
                direction = 1;
            }
        }

    }

    double timer = 0;
    double moveDelay = 0.2;
    @Override
    public void update(double dt) {
        if(!gameOver&&!inMenu){
            timer += dt;
            if (timer >= moveDelay) {
                upDataSnake();
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
        clearBackground(500,500);
        drawMap();
        if (!gameOver){
            drawSnake();
            drawApple();
        } else {
            drawGameOver();
        }
    }
}
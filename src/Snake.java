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

    boolean gameOver;
    boolean[][] grid = new boolean[50][50];
    int direction;  //up,down,left,right  0,1,2,3
    ArrayList<Point> body = new ArrayList<>();
    int applePointX, applePointY;

    Image apple = loadImage("resources/apple.png");
    Image dot = loadImage("resources/dot.png");
    Image head = loadImage("resources/head.png");

    public void randomApple(){
        do{
            applePointX = rand(50);
            applePointY = rand(50);
        }while (grid[applePointX][applePointY]);
    }


    public void init(){
        gameOver = false;
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

        randomApple();
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
        Point temp = new Point(body.getFirst().getX(),body.getFirst().getY());
        switch (direction){
            case 0://up
                temp.setPoint(temp.getX(),temp.getY()-1);
                break;
            case 1://down
                temp.setPoint(temp.getX(),temp.getY()+1);
                break;
            case 2://left
                temp.setPoint(temp.getX()-1,temp.getY());
                break;
            case 3://right
                temp.setPoint(temp.getX()+1,temp.getY());
                break;
        }
        if (temp.getX() < 0 || temp.getX() >= 50 || temp.getY() < 0 || temp.getY() >= 50) {
            gameOver = true;
            return;
        }

        boolean eatApple = (temp.getX() == applePointX && temp.getY() == applePointY);
        //release first
        if (!eatApple) {
            Point tail = body.getLast();
            tail.release();
        }
        if (grid[temp.getX()][temp.getY()]) {
            gameOver = true;
            return;
        }

        body.addFirst(temp);
        temp.take();

        if (eatApple) {
            randomApple();
            if (body.size() > 20) {
                Point tail = body.getLast();
                tail.release();
                body.removeLast();
            }
        } else {
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
        drawImage(apple,applePointX*10+0.5,applePointY*10+0.5,9,9);
    }

    public void keyPressed(KeyEvent e){
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

    double timer = 0;
    double moveDelay = 0.2;
    @Override
    public void update(double dt) {
        if(!gameOver){
            timer += dt;
            if (timer >= moveDelay) {
                upDataSnake();
                timer = 0;
            }
        }
    }

    @Override
    public void paintComponent() {
        changeBackgroundColor(black);
        clearBackground(500,500);
        drawMap();
        if (!gameOver){
            drawSnake();
            drawApple();
        } else {
            changeColor(white);
            drawText(85, 250, "GAME OVER!", "Arial", 50);
        }
    }
}
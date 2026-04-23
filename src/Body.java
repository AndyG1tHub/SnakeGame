import java.util.ArrayList;

public class Body {
    private ArrayList<Point> body = new ArrayList<>();
    private boolean[][] grid;
    private int direction; // 0 up, 1 down, 2 left, 3 right
    private boolean gameOver;

    public Body(boolean[][] grid) {
        this.grid = grid;
        reset();
    }

    public void reset() {
        body.clear();

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                grid[i][j] = false;
            }
        }

        body.add(new Point(25, 25));
        body.add(new Point(25, 26));
        body.add(new Point(25, 27));

        grid[25][25] = true;
        grid[25][26] = true;
        grid[25][27] = true;

        direction = 0;
        gameOver = false;
    }

    public ArrayList<Point> getBody() {
        return body;
    }


    public boolean isGameOver() {
        return gameOver;
    }

    public void setDirection(int newDirection) {
        if (newDirection == 2 && direction != 3) direction = 2; // left
        if (newDirection == 3 && direction != 2) direction = 3; // right
        if (newDirection == 0 && direction != 1) direction = 0; // up
        if (newDirection == 1 && direction != 0) direction = 1; // down
    }

    public Point getNextHead() {
        Point head = body.getFirst();
        int x = head.getX();
        int y = head.getY();

        switch (direction) {
            case 0:
                y--;
                break;
            case 1:
                y++;
                break;
            case 2:
                x--;
                break;
            case 3:
                x++;
                break;
        }

        return new Point(x, y);
    }

    public boolean willHitWall(Point next) {
        return next.getX() < 0 || next.getX() >= 50 || next.getY() < 0 || next.getY() >= 50;
    }

    public boolean willHitSelf(Point next, boolean grow) {
        if (!grow) {
            Point tail = body.getLast();
            grid[tail.getX()][tail.getY()] = false;
            boolean hit = grid[next.getX()][next.getY()];
            grid[tail.getX()][tail.getY()] = true;
            return hit;
        }
        return grid[next.getX()][next.getY()];
    }

    public void move(boolean grow) {
        Point next = getNextHead();

        if (willHitWall(next)) {
            gameOver = true;
            return;
        }

        if (willHitSelf(next, grow)) {
            gameOver = true;
            return;
        }

        body.addFirst(next);
        grid[next.getX()][next.getY()] = true;

        if (!grow) {
            Point tail = body.removeLast();
            grid[tail.getX()][tail.getY()] = false;
        }

        if (body.size() > 20) {
            Point tail = body.removeLast();
            grid[tail.getX()][tail.getY()] = false;
        }
    }

    public void shorten() {
        if (body.size() <= 1) {
            gameOver = true;
            return;
        }

        Point tail = body.removeLast();
        grid[tail.getX()][tail.getY()] = false;

        if (body.size() <= 1) {
            gameOver = true;
        }
    }
}
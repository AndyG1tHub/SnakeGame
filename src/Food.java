import java.util.ArrayList;
import java.util.Random;

public class Food {
    private int x;
    private int y;
    private boolean bad;
    Random random = new Random();
    
    public Food(boolean bad) {
        this.bad = bad;
    }

    public void randomFood(boolean[][] grid, ArrayList<Food> foods) {
        do {
            x = random.nextInt(50);
            y = random.nextInt(50);
        } while (isOccupied(grid, foods));
    }

    private boolean isOccupied(boolean[][] grid, ArrayList<Food> foods) {
        if (grid[x][y]) {
            return true;
        }

        for (Food food : foods) {
            if (food != this && food.getX() == x && food.getY() == y) {
                return true;
            }
        }

        return false;
    }

    public boolean isAt(int x, int y) {
        return this.x == x && this.y == y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isBad() {
        return bad;
    }
}
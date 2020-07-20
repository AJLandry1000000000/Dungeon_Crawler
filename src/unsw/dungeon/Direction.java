package unsw.dungeon;

/**
 * Direction Enumeration based on x and y coordinates
 * @author Sean Smith
 * @author Austin Landry
 */
public enum Direction {
    
    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

    private final int x;
    private final int y;

    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    /**
     * Determine the Direction that the given x and y coordinates make
     * @param x coordinate offset
     * @param y coordinate offset
     * @return If the Direction exists return it, otherwise the direction is invalid
     */
    public static Direction getDirection(int x, int y) {
        for (Direction d : Direction.values()) {
            if (d.getX() == x && d.getY() == y) {
                return d;
            }
        }
        return null;
    }
}
/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;

import unsw.dungeon.Entity;
import unsw.dungeon.Goal;
import unsw.dungeon.Enemy;
import unsw.dungeon.Player;
import unsw.dungeon.Portal;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Sean Smith & Austin Landry
 *
 */
public class Dungeon {

    private int width, height;
    private ArrayList<Entity> entities;
    private Player player;
    private boolean exitReached;
    private Goal goal;
    private Boolean gameOver;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<Entity>();
        this.player = null;
        this.exitReached = false;
        this.gameOver = false;
    }

    // Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public Goal getGoal() {
        return this.goal;
    }

    /**
     * 
     * @param entity
     * @return
     */
    public Boolean findEntity(Entity entity) {
        for (Entity e : entities) {
            if (e == entity) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public Entity getEntity(int x, int y) {
        for (Entity e : entities) {
            if (e.getX() == x && e.getY() == y) {
                return e;
            }
        }
        return null;
    }

    /**
     * Returns a list of all enemies in the dungeon.
     * @return
     */
    public ArrayList<Entity> getEnemies() {
        ArrayList<Entity> enemies = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e instanceof Enemy) {
                enemies.add(e);
            }
        }
        return enemies;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public ArrayList<Entity> getEntities(int x, int y) {
        ArrayList<Entity> check = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e.getX() == x && e.getY() == y) {
                check.add(e);
            }
        }
        return check;
    }

    public ArrayList<Entity> getEntities() {
        return this.entities;
    }

    /**
     * 
     * @param portal
     * @return
     */
    public Portal getPortal(Portal portal) {
        for (Entity e : entities) {
            if (e instanceof Portal) {
                Portal p = ((Portal)e);
                if (p.getID() == portal.getID() && p != portal) {
                    return p;
                }
            }
        }
        return null;
    }

    // Setters
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }

    public void reachExit() {
        this.exitReached = true;
    }

    public void leaveExit() {
        this.exitReached = false;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public void setGameOver() {
        this.gameOver = true;
    }

    public boolean getGameOver() {
        return this.gameOver;
    }


    // Checkers
    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public boolean checkBoundaries(int x, int y) {
        return ((x >= 0 && x < getWidth()) && (y >= 0 && y < getHeight()));
    }

    /**
     * 
     * @return
     */
    public boolean checkExitReached() {
        return this.exitReached;
    }

    /**
     * 
     */
    public Boolean goalsCompleted() {
        if (this.goal.isCompleted()) {
            System.out.println("All Goals completed, Level is Complete");
            return true;
        }
        return false;
    }

    /**
     * 
     * @return
     */
    public Boolean isGameOver() {
        return this.gameOver;
    }
}

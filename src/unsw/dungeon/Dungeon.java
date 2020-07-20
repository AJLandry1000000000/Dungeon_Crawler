/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;

import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.goals.Goal;
import unsw.dungeon.entity.model.Enemy;
import unsw.dungeon.entity.model.Player;
import unsw.dungeon.entity.model.Portal;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }

    public Boolean findEntity(Entity entity) {
        for (Entity e : entities) {
            if (e == entity) {
                return true;
            }
        }
        return false;
    }

    public Entity getEntity(int x, int y) {
        for (Entity e : entities) {
            if (e.getX() == x && e.getY() == y) {
                return e;
            }
        }
        return null;
    }

    // Returns a list of all enemies in the dungeon.
    public ArrayList<Entity> getEnemies() {
        ArrayList<Entity> enemies = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e instanceof Enemy) {
                enemies.add(e);
            }
        }
        return enemies;
    }
    
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

    public boolean checkBoundaries(int x, int y) {
        return ((x >= 0 && x < getWidth()) && (y >= 0 && y < getHeight()));
    }

    public boolean checkExitReached() {
        return this.exitReached;
    }

    public void setExitReached() {
        this.exitReached = true;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Goal getGoal() {
        return this.goal;
    }

    public Boolean goalsCompleted() {
        if (this.goal.isCompleted()) {
            System.out.println("All Goals completed, Level is Complete");
            return true;
        }
        return false;
    }

    public void setGameOver() {
        this.gameOver = true;
    }

    public Boolean isGameOver() {
        return this.gameOver;
    }
}

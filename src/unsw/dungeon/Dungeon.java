/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.entity.Entity;
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
    private List<Entity> entities;
    private Player player;
    private boolean levelCompleted;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.levelCompleted = false;
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

    public boolean checkLevelCompleted() {
        return this.levelCompleted;
    }

    public void setLevelCompleted() {
        this.levelCompleted = true;
    }
}

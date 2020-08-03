package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon houses many entities, each occupy a square. More than one
 * entity can occupy the same square. The Dungeon houses the Entities, the 
 * Player and the Goal condition.
 *
 * @author Sean Smith
 * @author Austin Landry
 *
 */
public class Dungeon {

    private int width, height;
    private ArrayList<Entity> entities;
    private Player player;
    private Boolean exitReached;
    private Goal goal;
    private ArrayList<Goal> goalTypes;
    private BooleanProperty gameOver;
    private StringProperty consoleText;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<Entity>();
        this.player = null;
        this.exitReached = false;
        this.goalTypes = new ArrayList<Goal>();
        this.gameOver = new SimpleBooleanProperty(false);
        this.consoleText = new SimpleStringProperty("");
    }

    /* ----------------------------- JAVAFX --------------------------------- */
    public StringProperty getConsoleText() {
        return this.consoleText;
    }

    public BooleanProperty gameOver() {
        return this.gameOver;
    }

    /* ----------------------------- GETTERS -------------------------------- */
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

    public ArrayList<Entity> getEntities() {
        return this.entities;
    }

    /**
     * Given an Entity as an argument, find whether the Entity still exists on the level
     * @param entity the Entity to check if it still exists 
     * @return either True if it exists, or false if it no longer exists
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
     * Given the x and y coordinates of the level, check if an Entity exists at that location
     * @param x coordinate location
     * @param y coordinate location
     * @return either the Entity if one exists, otherwise null if no Entity is present
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
     * @return an ArrayList of Enemies
     */
    public ArrayList<Entity> getEnemies() {
        ArrayList<Entity> enemies = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e.getClass().equals(Enemy.class)) {
                enemies.add(e);
            } else if (e.getClass().equals(Ghost.class)) {
                enemies.add(e);
            }
        }
        return enemies;
    }
    
    /**
     * Gather all Entities at a given location
     * @param x coordinate location
     * @param y coordinate location
     * @return an ArrayList of 0 or more Entities
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

    /**
     * Find the corresponding Portal linked to the given Portal
     * @param portal Portal given to find corresponding
     * @return the corresponding Portal with the same ID
     */
    public Portal getPortal(Portal portal) {
        for (Entity e : entities) {
            if (e.getClass().equals(Portal.class)) {
                Portal p = ((Portal)e);
                if (p.getID() == portal.getID() && p != portal) {
                    return p;
                }
            }
        }
        return null;
    }

    /* ----------------------------- SETTERS -------------------------------- */
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void addGoalType(Goal goal) {
        this.goalTypes.add(goal);
    }

    public void removeEntity(Entity entity) {
        entity.visible().set(false);
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
        this.gameOver.set(true);
    }

    public boolean getGameOver() {
        return this.gameOver.get();
    }

    public void setConsoleText(String message) {
        this.consoleText.set(message);
    }

    public void clearConsoleText() {
        this.consoleText.set("");
    }

    /**
     * Find the corresponding Door and change its image
     * @param id
     */
    public void alertDoor(int id) {
        for (Entity e : entities) {
            if (e.getClass().equals(Door.class)) {
                if (((Door)e).getID() == id) {
                    ((Door)e).alertDoor();
                    break;
                }
            }
        }
    }

    /* ----------------------------- CHECKERS ------------------------------- */
    /**
     * Check if the x and y coordinates are in the boundaries of the level
     * @param x coordinate location
     * @param y coordinate location
     * @return either true if location is valid, otherwise false
     */
    public boolean checkBoundaries(int x, int y) {
        return ((x >= 0 && x < getWidth()) && (y >= 0 && y < getHeight()));
    }

    /**
     * Check if the Player is currently interacting with an exit
     * @return either true if the player is at the exit, otherwise false
     */
    public boolean checkExitReached() {
        return this.exitReached;
    }

    /**
     * Determine if the Goal(s) has been completed
     * @return either true if goal is complete, otherwise false
     */
    public Boolean goalsCompleted() {
        if (this.goal.isCompleted(this)) {
            setGameOver();
            return true;
        }
        return false;
    }

    /**
     * Check if the game is at a game over state (e.g. when the player dies)
     * @return either true if the game is over, otherwise false
     */
    public Boolean isGameOver() {
        return this.gameOver.get();
    }

    public ArrayList<Goal> getGoalTypes() {
        return this.goalTypes;
    }
}

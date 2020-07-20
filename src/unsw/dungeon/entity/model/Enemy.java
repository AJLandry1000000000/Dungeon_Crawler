package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;
import unsw.dungeon.entity.Moveable;

public class Enemy extends Entity implements Moveable, Interactable {

    private Dungeon dungeon;
    
    public Enemy(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public Entity entityAtPosition(int x, int y) {
        return getDungeon().getEntity(x, y);
    }

    /**
     * 
     */
    @Override
    public Boolean move(Direction direction) {
        // Determine the new position based on the provided Direction
        int newX = getX() + direction.getX();
        int newY = getY() + direction.getY();

        // Determine if there is an entity at the new position
        Entity checkEntity = getDungeon().getEntity(newX, newY);

        // Assume that the enemy cannot interact with the new entity
        Boolean canAttack = false;
        // If this entity is a Player
        if (checkEntity instanceof Player) {
            // If this Enemy can destroy the player, return true
            // Otherwise return false (e.g. if the player has an Invincibility potion).
            canAttack = ((Interactable)checkEntity).interact(this);
        }

        // If the Enemy can attack the Player or If the Enemy is moving onto an empty space
        // And the new position must be within the dungeon boundaries
        if ((canAttack || checkEntity == null) && getDungeon().checkBoundaries(newX, newY)) {
            // Allow the Enemy to change its coordinates to the new X & Y
            x().set(newX);
            y().set(newY);
            return true;
        }
        return false;
     }

    // This method is used by player to interact with this enemy (Player attacks the Enemy).
    @Override
    public Boolean interact(Entity entity) {
        // If the player does not exist 
        if (!getDungeon().findEntity(entity)) {
            return true;
        }
        Player player = (Player) entity;
        // If the Player has an Invincibility potion
        if (player.hasPotion()) {
            // Remove the Enemy from the level and check if goal conditions are complete
            System.out.println("Player has killed an Enemy");
            getDungeon().removeEntity(this);
            getDungeon().goalsCompleted();
        }
        // If the Player has a Sword
        else if (player.hasSword()) {
            // Remove the Enemy from the Level, decrement the Sword hits, check the goal conditions
            System.out.println("Player has killed an Enemy");
            getDungeon().removeEntity(this);
            player.getSword().decrementHits();
            player.checkSword();
            getDungeon().goalsCompleted();
        }
        // Otherwise, the Player is destroyed and game is set to Game Over
        else {
            System.out.println("Player has been killed by an Enemy");
            getDungeon().removeEntity(player);
            getDungeon().setGameOver();
        }
        return true;
    }
}
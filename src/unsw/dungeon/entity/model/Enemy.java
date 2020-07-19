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

    public Entity entityAtPosition(int x, int y) {
        return this.getDungeon().getEntity(x, y);
    }

    public Boolean move(Direction direction) {
        int newX = getX() + direction.getX();
        int newY = getY() + direction.getY();

        // Get the entity at the new X & Y coordinates.
        Entity checkEntity = this.getDungeon().getEntity(newX, newY);

        // Assume that the enemy cannot interact with the new entity.
        Boolean canAttack = false;
        // If this entity is a Player, interact with it.
        if (checkEntity instanceof Player) {
            // If this Enemy can destroy the player, return true. Otherwise return false (Like if the player has an Invincibility potion). 
            canAttack = ((Interactable)checkEntity).interact(this);
        }

        // If the Enemy can Attack the Player, it can move onto the Player's coordinates, so allow the Enemy to change its coordinates to the new X & Y.
        // If the Enemy cannot Attack the Player, maybe its because there is no Player to attack, and the Enemy is moving to an empty space. If the space is empty, allow the Enemy to move their.
        // Always check that the new spot is within the dungeon boundaries.
        else if ((canAttack || checkEntity == null) && this.dungeon.checkBoundaries(newX, newY)) {
            x().set(newX);
            y().set(newY);
            return true;
        }
        return false;
     }

    // This method is used by player to interact with this enemy (Player attacks the Enemy).
    @Override
    public Boolean interact(Entity entity) {
        Player player = (Player) entity;

        // If the Player has an Invincibility potion, destroy the Enemy, and return false.
        if (player.hasPotion()) {
            this.dungeon.removeEntity(this);
            return false;
        }

        // If the Player has a Sword, destroy the Enemy, change the Sword hits, and return false.
        if (player.hasSword()) {
            this.dungeon.removeEntity(this);
            player.getSword().decrementHits();
            player.checkSword();
            return false;
        }

        // Otherwise, destroy the Player, and return true.
        this.dungeon.removeEntity(player);
        return true;
    }
}
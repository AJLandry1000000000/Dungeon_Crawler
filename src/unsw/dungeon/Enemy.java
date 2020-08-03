package unsw.dungeon;

/**
 * An Enemy Entity that tracks down, and attacks the Player. 
 * Behaviour changes to fleeing if the Player has a Potion.
 * @author Sean Smith
 * @author Austin Landry
 */
public class Enemy extends Entity implements Moveable, Interactable {
    
    public Enemy(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
    }

    /**
     * Process Enemy's movement towards the Player
     * Enemy attacks Player if its new position has the Player on it
     */
    @Override
    public Boolean move(Direction direction) {
        // Determine the new position based on the provided Direction
        int newX = getX() + direction.getX();
        int newY = getY() + direction.getY();

        // Determine if there is an entity at the new position
        Entity checkEntity = dungeon.getEntity(newX, newY);

        // If the Enemy wants to walk on a square that has more than two entities, it is disallowed
        if (dungeon.getEntities(newX, newY).size() > 1) {
            return false;
        }

        // Assume that the enemy cannot interact with the new entity
        Boolean canAttack = false;
        // If this entity is a Player
        if (checkEntity != null && checkEntity.getClass().equals(Player.class)) {
            // If this Enemy can destroy the player, return true
            // Otherwise return false (e.g. if the player has an Invincibility potion).
            canAttack = ((Interactable)checkEntity).interact(this);
        }

        // Check if the Entity is an open Doorway
        Boolean isDoorOpen = false;
        if (checkEntity != null && checkEntity.getClass().equals(Door.class)) {
            if (((Door)checkEntity).isOpen()) isDoorOpen = true;
        }

        // If the Enemy can attack the Player or If the Enemy is moving onto an empty space
        // And the new position must be within the dungeon boundaries
        if ((canAttack || isDoorOpen || checkEntity == null) && dungeon.checkBoundaries(newX, newY)) {
            // Allow the Enemy to change its coordinates to the new X & Y
            x().set(newX);
            y().set(newY);
            return true;
        }
        return false;
     }

    /**
     * This method is used by player to interact with this enemy (Player attacks the Enemy)
     */
    @Override
    public Boolean interact(Entity entity) {
        // If the player does not exist 
        if (!dungeon.findEntity(entity)) {
            return true;
        }
        Player player = (Player)entity;
        // If the Player has an Invincibility potion
        if (player.hasPotion()) {
            // Remove the Enemy from the level and check if goal conditions are complete
            dungeon.removeEntity(this);
            dungeon.setConsoleText("Player has killed an Enemy");
            dungeon.goalsCompleted();
        }
        // If the Player has a Sword
        else if (player.hasSword()) {
            // Remove the Enemy from the Level, decrement the Sword hits, check the goal conditions
            player.useSword();
            dungeon.removeEntity(this);
            dungeon.setConsoleText("Player has killed an Enemy");
            dungeon.goalsCompleted();
        }
        // Otherwise, the Player is destroyed and game is set to Game Over
        else {
            dungeon.removeEntity(player);
            player.actionTaken().set("Player has been killed by an Enemy");
            dungeon.setGameOver();
            dungeon.goalsCompleted();
        }
        return true;
    }
}
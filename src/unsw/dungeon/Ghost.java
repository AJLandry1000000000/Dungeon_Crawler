package unsw.dungeon;

/**
 * A Ghost Entity that tracks down, and attacks the Player. 
 * Behaviour changes to fleeing if the Player has a Potion.
 * @author Sean Smith
 * @author Austin Landry
 */
public class Ghost extends Enemy {
    
    public Ghost(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
    }

    @Override
    public Boolean move(Direction direction) {
        // Determine the new position based on the provided Direction
        int newX = getX() + direction.getX();
        int newY = getY() + direction.getY();

        // Determine if there is an entity at the new position
        Entity checkEntity = dungeon.getEntity(newX, newY);

        // If this entity is a Player
        if (checkEntity != null && checkEntity.getClass().equals(Player.class)) {
            // If this Enemy can destroy the player, return true
            // Otherwise return false (e.g. if the player has an Invincibility potion).
            ((Interactable)checkEntity).interact(this);
        }

        // Disallow a Ghost to move onto another Ghost
        if (dungeon.getEntities(newX, newY).stream()
            .filter(o -> o.getClass().equals(Ghost.class)).findFirst().isPresent()) {
            return false;
        }

        // If the Ghost can attack the Player or If the Ghost is moving onto an empty space
        // And the new position must be within the dungeon boundaries
        if (dungeon.checkBoundaries(newX, newY)) {
            // Allow the Ghost to change its coordinates to the new X & Y
            x().set(newX);
            y().set(newY);
            return true;
        }
        return false;
     }
}
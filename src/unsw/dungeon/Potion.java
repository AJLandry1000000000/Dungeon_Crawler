package unsw.dungeon;

/**
 * Grants the Player 10 steps of invincibility and creates fleeing Enemy behaviour.
 * @author Sean Smith
 * @author Austin Landry
 */
public class Potion extends Entity implements Interactable {

    public Potion(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
    }

    /**
     * If the Player doesn't have a Potion, give them one. Otherwise, don't let the Player interact with the Potion.
     * @param entity - This is a Player. If it is anything else, don't let it interact with the Potion.
     */
    @Override
    public Boolean interact(Entity entity) {
        // Disallow any non-Player from interacting with a Potion
        if (!(entity.getClass().equals(Player.class))) {
            return false;
        }

        // If the Player does not have a current active Potion
        Player player = (Player)entity;
        if (!player.hasPotion()) {
            // Give the Player the Potion and activate it
            player.drinkPotion(this);
            // Remove the Potion from the map
            dungeon.removeEntity(this);
            // Set the Console message
            dungeon.setConsoleText("Played has drank a Potion. 10 Steps left of invincibility");
            return true;
        } 
        // Otherwise, disallow the Player from picking up the Potion
        dungeon.setConsoleText("Played is already has a Potion active");
        return false;
    }
}
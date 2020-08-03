package unsw.dungeon;

/**
 * Wall Entities act as a blocking mechanism for paths in the Dungeons
 * @author Sean Smith
 * @author Austin Landry
 */
public class Wall extends Entity implements Interactable {

    public Wall(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
    }

    /**
     * Allow for a Player wielding a Hammer to destroy a wall
     * @param entity - The Player interacting with the Wall
     */
    @Override
    public Boolean interact(Entity entity) {
        // Disallow any non-Player to interact with the Treasure
        if (!(entity.getClass().equals(Player.class))) {
            return false;
        }
        
        // If the Player is current wielding a hammer
        Player player = (Player)entity;
        if (player.hasHammer()) {
            // Decrement the number of hits of the Hammer and check it's durability
            player.useHammer();
            // Remove the wall from the map
            dungeon.removeEntity(this);
            // Set the Console message
            dungeon.setConsoleText("Player has broken a Wall");
            return true;
        }
        return false;
    }

}

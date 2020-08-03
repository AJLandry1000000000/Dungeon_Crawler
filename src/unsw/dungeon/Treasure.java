package unsw.dungeon;

/**
 * Treasure is a collectable item that the Player interacts with. 
 * An interaction from the Player updates the dungeon and the goals.
 * @author Sean Smith
 * @author Austin Landry
 */
public class Treasure extends Entity implements Interactable {

    public Treasure(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
    }

    /**
     * Allow for a Player to collect a Treasure
     * @param entity The Player interacting with the Treasure
     */
    @Override
    public Boolean interact(Entity entity) {
        // Disallow any non-Player to interact with the Treasure
        if (!(entity.getClass().equals(Player.class))) {
            return false;
        }
        
        // Increase the Treasure counter the Player has collected
        ((Player)entity).addTreasure();
        // Remove the treasure from the map
        dungeon.removeEntity(this);
        // Update the console
        dungeon.setConsoleText("Played has found a Treasure");
        // Check and determine if all goal conditions have been completed
        dungeon.goalsCompleted();
        return true;
    }

}

package unsw.dungeon;

/**
 * Key is held by the Player to open a Door. Key is essentially just an ID to be compared to Door IDs.
 * @author Sean Smith & Austin Landry
 */
public class Key extends Entity implements Interactable {

    private Dungeon dungeon;
    private int id;
    
    public Key(Dungeon dungeon, int x, int y, int id) {
        super(x, y);
        this.dungeon = dungeon;
        this.id = id;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }
    
    public int getId() {
        return id;
    }
    
    /**
     * If the Player doesn't have a Key, give the Player the Key. Otherwise don't let the Player interact with the Key.
     * @param entity - This is a Player. If it is anything else, don't let it interact with the Key.
     */
    @Override
    public Boolean interact(Entity entity) {
        // Only a Player is allowed to interact with a Key
        if (!(entity instanceof Player)) {
            return false;
        }
        Player player = (Player) entity;
        // If a player is not currently holding a Key
        if (!player.hasKey()) {
            // Player is given the Key which is also removed from the level
            player.giveKey(this);
            System.out.println("Player has picked up a key of ID: " + this.id);
            return true;
        }
        // Otherwise disallow the Player from acquiring the key as the player already is holding a current key
        else {
            return false;
        }  
    }
}
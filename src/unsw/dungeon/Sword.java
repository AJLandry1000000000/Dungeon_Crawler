package unsw.dungeon;

/**
 * Sword is collected by Player to defeat Enemy. Each Sword only has 5 hits. 
 * After the 5th hit it disappears from the Players possession. Players can only hold one Sword at a time.
 * @author Sean Smith
 * @author Austin Landry
 */
public class Sword extends Entity implements Interactable {

    private int hits;

    public Sword(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        this.hits = 5;
    }

    public int getHits() {
        return this.hits;
    }

    public void decrementHits() {
        this.hits--;
    }

    /**
     * If the entity is a Player, and that Player doesn't have a Sword, give this Sword to them. 
     * If the Player does have a Sword already, do not let them interact with this Sword.
     * @param entity - This is a Player. If it is anything else, don't let it interact with the Sword.
     */
    @Override
    public Boolean interact(Entity entity) {
        // Only a Player is allowed to interact with a Sword
        if (!(entity.getClass().equals(Player.class))) {
            return false;
        }

        Player player = (Player)entity;
        // Determine if the Player does not have a current Sword
        if (!player.hasSword()) {
            // Player acquires the Sword which is then removed from the level
            player.giveSword(this);
            return true;
        } else {
            // Otherwise disallow the interaction as the Player has a current Sword
            return false;
        }  
    }
}
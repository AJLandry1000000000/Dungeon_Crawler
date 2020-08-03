package unsw.dungeon;

/**
 * Sword is collected by Player to defeat Enemy. Each Sword only has 5 hits. 
 * After the 5th hit it disappears from the Players possession. Players can only hold one Sword at a time.
 * @author Sean Smith
 * @author Austin Landry
 */
public class Sword extends Entity implements Interactable {

    private int hits;
    private final int numHits = 5;

    public Sword(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        this.hits = numHits;
    }

    public int getHits() {
        return this.hits;
    }

    public void decrementHits() {
        this.hits--;
    }

    /**
     * Allow for a Player to equip a Sword only when they do not have one already equipped.
     * @param entity Player interacting with the Sword
     */
    @Override
    public Boolean interact(Entity entity) {
        // Disallow any non-Player from interacting with a Sword
        if (!(entity.getClass().equals(Player.class))) {
            return false;
        }

        // Determine if the Player does not have a current Sword
        Player player = (Player)entity;
        if (!player.hasSword()) {
            // Player acquires the Sword which is then removed from the level
            player.equipSword(this);
            // Remove the Sword from the map
            dungeon.removeEntity(this);
            // Update the console
            dungeon.setConsoleText("Played has equipped a Sword");
            return true;
        } 
        // Otherwise disallow the interaction as the Player has a current Sword and update console
        dungeon.setConsoleText("Played is already wielding a Sword");
        return false;
    }
}
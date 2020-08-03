package unsw.dungeon;

public class Hammer extends Entity implements Interactable {

    private int hits;

    public Hammer(int x, int y, Dungeon dungeon) {
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
     * If the entity is a Player, and that Player doesn't have a hammer, give this hammer to them. 
     * If the Player does have a hammer already, do not let them interact with this hammer.
     * @param entity - This is a Player. If it is anything else, don't let it interact with the hammer.
     */
    @Override
    public Boolean interact(Entity entity) {
        // Disallow any non-Player from interacting with a Hammer
        if (!(entity.getClass().equals(Player.class))) {
            return false;
        }

        // Determine if the Player does not have a current Hammer
        Player player = (Player)entity;
        if (!player.hasHammer()) {
            // Player acquires the Hammer which is then removed from the level
            player.equipHammer(this);
            // Remove the Hammer from the map
            dungeon.removeEntity(this);
            // Update the console
            dungeon.setConsoleText("Played has equipped a Hammer");
            return true;
        } 
        // Otherwise disallow the interaction as the Player has a current Hammer and update console
        dungeon.setConsoleText("Played is already wielding a Hammer");
        return false;
    }
}
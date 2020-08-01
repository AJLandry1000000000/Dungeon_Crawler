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
        // Only a Player is allowed to interact with a hammer
        if (!(entity.getClass().equals(Player.class))) {
            return false;
        }

        Player player = (Player)entity;
        // Determine if the Player does not have a current hammer
        if (!player.hasHammer()) {
            // Player acquires the hammer which is then removed from the level
            player.giveHammer(this);
            return true;
        } else {
            // Otherwise disallow the interaction as the Player has a current hammer
            return false;
        }  
    }
}
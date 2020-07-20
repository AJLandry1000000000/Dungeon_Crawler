package unsw.dungeon;

/**
 * Treasure is a collectable item that the Player interacts with. An interaction from the Player updates the dungeon and the goals.
 */
public class Treasure extends Entity implements Interactable {

    private Dungeon dungeon;

    public Treasure(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    /**
     * When the Player interacts with the Treasure, the Treasure is removed from the map, and goals is updated.
     * @param entity - This should be the Player interacting with the Treasure.
     */
    @Override
    public Boolean interact(Entity entity) {
        // Only a Player is allowed to interact with a Treasure
        if (!(entity instanceof Player)) {
            return false;
        }
        // Player acquires the treasure which is removed from the level
        getDungeon().removeEntity(this);
        System.out.println("Player has picked up a treasure");
        // Check is all goal conditions have been met
        getDungeon().goalsCompleted();
        return true;
    }

}

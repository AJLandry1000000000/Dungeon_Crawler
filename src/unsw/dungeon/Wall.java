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
     * 
     */
    @Override
    public Boolean interact(Entity entity) {
        Dungeon dungeon = getDungeon();
        // If the player does not exist 
        if (!dungeon.findEntity(entity)) {
            return true;
        }
        Player player = (Player)entity;
        // If the Player has a hammer
        if (player.hasHammer()) {
            System.out.println("Player has broken a Wall");
            dungeon.removeEntity(this);
            player.getHammer().decrementHits();
            player.checkHammer();
            return true;
        }
        return false;
    }

}

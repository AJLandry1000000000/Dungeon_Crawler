package unsw.dungeon;

/**
 * Portal moves the Player (and only the Player) to the partner Portal.
 * @author Sean Smith
 * @author Austin Landry
 */
public class Portal extends Entity implements Interactable {

    private int id;
    
    public Portal(int x, int y, Dungeon dungeon, int id) {
        super(x, y, dungeon);
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    /**
     * If the entity is a Player, find the partner Portal and teleport the Player there.
     * @param entity - This is a Player. If it is anything else, don't let it interact with the Portal.
     */
    @Override
    public Boolean interact(Entity entity) {
        // Only a Player can interact with a Portal.
        if (!(entity instanceof Player)) {
            return false;
        }

        // Find the corresponding Portal based on its ID.
        Portal portal = getDungeon().getPortal(this);
        if (portal == null) {
            return false;
        }
        Player player = (Player) entity;

        // Process the movement of the Player to the corresponding Portal.
        player.teleport(portal.getX(), portal.getY());
        return true;
    }
}
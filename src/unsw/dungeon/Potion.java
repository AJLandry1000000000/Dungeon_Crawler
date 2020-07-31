package unsw.dungeon;

/**
 * Grants the Player 10 steps of invincibility and creates fleeing Enemy behaviour.
 * @author Sean Smith
 * @author Austin Landry
 */
public class Potion extends Entity implements Interactable {

    public Potion(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
    }

    /**
     * If the Player doesn't have a Potion, give them one. Otherwise, don't let the Player interact with the Potion.
     * @param entity - This is a Player. If it is anything else, don't let it interact with the Potion.
     */
    @Override
    public Boolean interact(Entity entity) {
        // Only a Player is allowed to interact with a Potion
        if (!(entity.getClass().equals(Player.class))) {
            return false;
        }

        Player player = (Player)entity;
        // If the Player does not have a current active Potion
        if (!player.hasPotion()) {
            // Give the Player the Potion and activate it
            player.givePotion(this);
            System.out.println("Player has picked up a Potion");
            return true;
        } 
        // Otherwise, disallow the Player from picking up the Potion
        else {
            System.out.println("Player already has a Potion activated");
           return false;
        }
    }
}
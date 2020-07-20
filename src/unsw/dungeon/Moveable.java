package unsw.dungeon;

/**
 * Interface representing the an Entity that can be moved
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public interface Moveable {

    public Boolean move(Direction direction);
}
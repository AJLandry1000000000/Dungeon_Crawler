package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.CheckBox;

/**
 * Interface representing the Goal condition
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public interface Goal {
    public Boolean isCompleted(Dungeon dungeon);
    public BooleanProperty checkCompleted();
    public CheckBox getCheckBox();
}
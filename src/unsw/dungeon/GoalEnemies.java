package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

/**
 * Leaf Goal that allows for specific checking if all Enemies have been killed
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalEnemies implements Goal {
        

    private BooleanProperty completed;
    private CheckBox check;

    public GoalEnemies(CheckBox check) {
        this.check = check;
        this.completed = new SimpleBooleanProperty(false);
        this.completed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                Boolean oldValue, Boolean newValue) {
                getCheckBox().setSelected(newValue);
            }
        }); 
    }

    public CheckBox getCheckBox() {
        return this.check;
    }

    public BooleanProperty checkCompleted() {
        return this.completed;
    }
    /**
     * Determine if all enemies have been killed
     * @return true if no enemies active, otherwise false
     */
    @Override
    public Boolean isCompleted(Dungeon dungeon) {
        // Gather the count of all current Enemy entities
        long totalEnemies = dungeon.getEntities()
            .stream()
            .filter(x -> x.getClass().equals(Enemy.class))
            .count();
        // Check if there no Enemies remaining
        if (totalEnemies == 0) {
            checkCompleted().set(true);
            return true;
        }
        return false;
    }
}
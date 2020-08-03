package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

/**
 * Leaf Goal that allows for specific checking if all Treasure has been
 * collected
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalTreasure implements Goal {

    private BooleanProperty completed;
    private CheckBox check;

    public GoalTreasure(CheckBox check) {
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

    public BooleanProperty checkCompleted() {
        return this.completed;
    }    

    public CheckBox getCheckBox() {
        return this.check;
    }
    
    /**
     * Determine if all Treasures have been acquired
     * @return true if no Treasures left, otherwise false
     */
    @Override
    public Boolean isCompleted(Dungeon dungeon) {
        // Gather the count of all Treasure entities
        long totalTreasures = dungeon.getEntities()
            .stream()
            .filter(x -> x.getClass().equals(Treasure.class))
            .count();
        // Check if there are no Treasure entities remaining
        if (totalTreasures == 0) {
            Player player = dungeon.getPlayer();
            player.actionTaken().set("Player has completed a Goal");
            checkCompleted().set(true);
            return true;
        }
        return false;
    }
}
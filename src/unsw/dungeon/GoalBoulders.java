package unsw.dungeon;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

/**
 * Leaf Goal that allows for specific checking if all floor switches are activated
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalBoulders implements Goal {

    private BooleanProperty completed;
    private CheckBox check;

    public GoalBoulders(CheckBox check) {
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
     * Determine if every Floor Switch is activated (i.e. with a Boulder on top of it)
     * @return true if all Switches are activated, otherwise false
     */
    @Override
    public Boolean isCompleted(Dungeon dungeon) {
        // Gather all switches
        List<Entity> switches = dungeon.getEntities()
            .stream()
            .filter(x -> x.getClass().equals(Switch.class))
            .collect(Collectors.toList());   
        // Check if all switches are activated
        for (Entity e : switches) {
            if (!((Switch)e).isActivated()) {
                checkCompleted().set(false);
                return false;
            }
        }
        Player player = dungeon.getPlayer();
        player.actionTaken().set("Player has completed a Goal");
        checkCompleted().set(true);
        return true;
    }
}
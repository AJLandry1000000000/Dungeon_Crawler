package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

/**
 * Leaf Goal that allows for specific checking if exit has been reached
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalExit implements Goal {

    private BooleanProperty completed;
    private CheckBox check;

    public GoalExit(CheckBox check) {
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
     * @return true if the exit has been reached, otherwise false
     */
    @Override
    public Boolean isCompleted(Dungeon dungeon) {
        if (dungeon.checkExitReached()) {
            checkCompleted().set(true);
            return true;
        }
        checkCompleted().set(false);
        return false;
    }
}
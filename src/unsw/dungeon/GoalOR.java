package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

/**
 * Composite Goal that allows for OR checking of sub goals
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalOR implements GoalComposite {

    private ArrayList<Goal> goals;
    private BooleanProperty completed;
    private CheckBox check;

    public GoalOR(CheckBox check) {
        this.check = check;
        this.goals = new ArrayList<Goal>();
        this.completed = new SimpleBooleanProperty(false);
        this.completed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                Boolean oldValue, Boolean newValue) {
                getCheckBox().setSelected(newValue);
            }
        }); 
    }

    public void add(Goal goal) {
        this.goals.add(goal);
    }

    public ArrayList<Goal> getGoals() {
        return this.goals;
    }

    public BooleanProperty checkCompleted() {
        return this.completed;
    }

    public CheckBox getCheckBox() {
        return this.check;
    }

    /**
     * @return true if any sub-goals are completed, otherwise false if all sub goals are incomplete
     */
    @Override
    public Boolean isCompleted(Dungeon dungeon) {
        Boolean flag = null;
        for (Goal g : goals) {
            if (g.isCompleted(dungeon)) {
                Player player = dungeon.getPlayer();
                player.actionTaken().set("Player has completed a Goal");
                checkCompleted().set(true);
                flag = true;
            }
        }
        if (flag == null) {
            return false;
        }
        return true;
    }
}
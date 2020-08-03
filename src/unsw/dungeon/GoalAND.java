package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

/**
 * Composite Goal that allows for AND checking of sub goals
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalAND implements GoalComposite {
    
    private ArrayList<Goal> goals;
    private BooleanProperty completed;
    private CheckBox check;


    public GoalAND(CheckBox check) {
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
     * @return true if any sub-goal is completed, otherwise false if no goals have been completed
     */
    @Override
    public Boolean isCompleted(Dungeon dungeon) {
        Boolean flag = null;
        for (Goal g : goals) {
            if (!g.isCompleted(dungeon)) {
                flag = false;
            }
        }
        if (flag == null) {
            checkCompleted().set(true);
            return true;
        }
        checkCompleted().set(false);
        return false;
    }
}
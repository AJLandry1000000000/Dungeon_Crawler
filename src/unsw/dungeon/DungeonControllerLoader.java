package unsw.dungeon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.model.Boulder;
import unsw.dungeon.entity.model.Door;
import unsw.dungeon.entity.model.Enemy;
import unsw.dungeon.entity.model.Exit;
import unsw.dungeon.entity.model.Key;
import unsw.dungeon.entity.model.Portal;
import unsw.dungeon.entity.model.Potion;
import unsw.dungeon.entity.model.Switch;
import unsw.dungeon.entity.model.Sword;
import unsw.dungeon.entity.model.Treasure;
import unsw.dungeon.entity.model.Wall;


/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;

    //Images
    private Image playerImage;
    private Image wallImage;

    private Image boulderImage;
    private Image doorOpenImage;
    private Image doorClosedImage;
    private Image enemyImage;
    private Image exitImage;
    private Image keyImage;
    private Image portalImage;
    private Image potionImage;
    private Image switchImage;
    private Image swordImage;
    private Image treasureImage;

    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image((new File("images/human_new.png")).toURI().toString());
        wallImage = new Image((new File("images/brick_brown_0.png")).toURI().toString());

        boulderImage = new Image((new File("images/boulder.png")).toURI().toString());
        doorOpenImage = new Image((new File("images/open_door.png")).toURI().toString());
        doorClosedImage = new Image((new File("images/closed_door.png")).toURI().toString());
        enemyImage = new Image((new File("images/deep_elf_master_archer.png")).toURI().toString());
        exitImage = new Image((new File("images/exit.png")).toURI().toString());
        keyImage = new Image((new File("images/key.png")).toURI().toString());
        portalImage = new Image((new File("images/portal.png")).toURI().toString());
        potionImage = new Image((new File("images/brilliant_blue_new.png")).toURI().toString());
        switchImage = new Image((new File("images/pressure_plate.png")).toURI().toString());
        swordImage = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        treasureImage = new Image((new File("images/gold_pile.png")).toURI().toString());
    }

    public DungeonControllerLoader(JSONObject json) {
        super(json);
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorClosedImage);
        addEntity(door, view);
    }
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
    }
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
    }
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }
    public void onLoad(Potion potion) {
        ImageView view = new ImageView(potionImage);
        addEntity(potion, view);
    }
    public void onLoad(Switch floor) {
        ImageView view = new ImageView(switchImage);
        addEntity(floor, view);
    }
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
    }
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
        return new DungeonController(load(), entities);
    }


}

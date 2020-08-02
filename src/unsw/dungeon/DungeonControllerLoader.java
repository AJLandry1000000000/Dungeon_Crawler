package unsw.dungeon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Sean Smith
 * @author Austin Landry
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> baseLayer;
    private List<ImageView> collectableLayer;
    private List<ImageView> moveableLayer;

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
    private Image doorAlertImage;
    private Image playerSwordImage;
    private Image playerPotionImage;
    private Image playerSwordPotionImage;
    private Image hammerImage;
    private Image playerHammerImage;
    private Image playerHammerPotionImage;
    private Image playerSwordHammerPotionImage;
    private Image playerSwordHammerImage;
    private Image ghostImage;

    private GridPane inventory;

    public DungeonControllerLoader(String filename, GridPane inventory) throws FileNotFoundException {
        super(filename);
        this.inventory = inventory;

        baseLayer = new ArrayList<>();
        collectableLayer = new ArrayList<>();
        moveableLayer = new ArrayList<>();

        playerImage = new Image((new File("images/human_new.png")).toURI().toString());
        wallImage = new Image((new File("images/brick_brown_0.png")).toURI().toString());
        boulderImage = new Image((new File("images/boulder.png")).toURI().toString());
        doorOpenImage = new Image((new File("images/open_door.png")).toURI().toString());
        doorAlertImage = new Image((new File("images/door_alert.png")).toURI().toString());
        doorClosedImage = new Image((new File("images/closed_door.png")).toURI().toString());
        enemyImage = new Image((new File("images/deep_elf_master_archer.png")).toURI().toString());
        exitImage = new Image((new File("images/exit.png")).toURI().toString());
        keyImage = new Image((new File("images/key.png")).toURI().toString());
        portalImage = new Image((new File("images/portal.png")).toURI().toString());
        potionImage = new Image((new File("images/brilliant_blue_new.png")).toURI().toString());
        switchImage = new Image((new File("images/pressure_plate.png")).toURI().toString());
        swordImage = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        treasureImage = new Image((new File("images/gold_pile.png")).toURI().toString());

        playerSwordImage = new Image((new File("images/player_w_sword.png")).toURI().toString());
        playerPotionImage = new Image((new File("images/player_w_potion.png")).toURI().toString());
        playerHammerImage = new Image((new File("images/player_w_hammer.png")).toURI().toString());
        playerSwordPotionImage = new Image((new File("images/player_w_potion_sword.png")).toURI().toString());
        playerHammerPotionImage =new Image((new File("images/player_w_potion_hammer.png")).toURI().toString());
        playerSwordHammerPotionImage = new Image((new File("images/player_w_potion_sword_hammer.png")).toURI().toString());
        playerSwordHammerImage = new Image((new File("images/player_w_sword_hammer.png")).toURI().toString());

        hammerImage = new Image((new File("images/hammer.png")).toURI().toString());
        ghostImage = new Image((new File("images/ghost_new.png")).toURI().toString());

    }

    public GridPane getInventory() {
        return this.inventory;
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view, 3);
    }
    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view, 1);
    }
    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view, 3);
    }
    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorClosedImage);
        addEntity(door, view, 2);
    }
    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view, 3);
    }
    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view, 2);
    }
    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view, 2);
    }
    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);

        //Instantiating the ColorAdjust class 
        ColorAdjust colorAdjust = new ColorAdjust(); 

        if (portal.getID() % 2 == 0) colorAdjust.setHue((portal.getID() % 10) * 0.1);     
        else colorAdjust.setHue((portal.getID() % 10) * -0.1);     

        view.setEffect(colorAdjust);
        addEntity(portal, view, 2);
    }
    @Override
    public void onLoad(Potion potion) {
        ImageView view = new ImageView(potionImage);
        addEntity(potion, view, 2);
    }
    @Override
    public void onLoad(Switch floor) {
        ImageView view = new ImageView(switchImage);
        addEntity(floor, view, 1);
    }
    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view, 2);
    }
    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view, 2);
    }
    @Override
    public void onLoad(Hammer hammer) {
        ImageView view = new ImageView(hammerImage);
        addEntity(hammer, view, 2);
    }
    @Override
    public void onLoad(Ghost ghost) {
        ImageView view = new ImageView(ghostImage);
        addEntity(ghost, view, 3);
    }

    private void addEntity(Entity entity, ImageView view, int level) {
        switch (level) {
            case 1:
                baseLayer.add(view);
                break;
            case 2:
                collectableLayer.add(view);
                break;
            case 3:
                moveableLayer.add(view);
                break;
        }
        trackMoveable(entity, view);
        trackInteractable(entity, view);
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
    private void trackMoveable(Entity entity, Node node) {
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

    private void trackInteractable(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        entity.visible().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                node.setVisible(false);
            }
        });


        if (entity.getClass().equals(Door.class)) {
            Door door = ((Door)entity);
            door.doorStatus().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable,
                        Boolean oldValue, Boolean newValue) {
                    ((ImageView)node).setImage(doorOpenImage);
                }
            });
            door.keyStatus().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable,
                Boolean oldValue, Boolean newValue) {
                    ((ImageView)node).setImage(doorAlertImage);  
                }
            });
        }

        else if (entity.getClass().equals(Player.class)) {
            Player player = ((Player)entity);
            player.potionSteps().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                    if (newValue.intValue() > 0) {
                        if (player.hasSword() && player.hasHammer()) {
                            ((ImageView)node).setImage(playerSwordHammerPotionImage);
                        } else if (player.hasSword()) {
                            ((ImageView)node).setImage(playerSwordPotionImage);
                        } else if (player.hasHammer()) {
                            ((ImageView)node).setImage(playerHammerPotionImage);
                        } else {
                            ((ImageView)node).setImage(playerPotionImage);
                        }
                    } else {
                        if (player.hasSword() && player.hasHammer()) {
                            ((ImageView)node).setImage(playerSwordHammerImage);
                        } else if (player.hasSword()) {
                            ((ImageView)node).setImage(playerSwordImage);
                        } else if (player.hasHammer()) {
                            ((ImageView)node).setImage(playerHammerImage);
                        }  else {
                            ((ImageView)node).setImage(playerImage);
                        }
                    }
                }
            });
            player.isSwordEquipped().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        if (player.hasPotion() && player.hasHammer()) {
                            ((ImageView)node).setImage(playerSwordHammerPotionImage);
                        } else if (player.hasPotion()) {
                            ((ImageView)node).setImage(playerSwordPotionImage);
                        } else if (player.hasHammer()) {
                            ((ImageView)node).setImage(playerSwordHammerImage);
                        } else {

                            // HEEERREEEE
                            // GridPane.add(new ImageView(swordImage), 0, 0, 2, 1);


                            ((ImageView)node).setImage(playerSwordImage);
                        }
                    } else {
                        if (player.hasPotion() && player.hasHammer()) {
                            ((ImageView)node).setImage(playerHammerPotionImage);
                        } else if (player.hasPotion()) {
                            ((ImageView)node).setImage(playerPotionImage);
                        } else if (player.hasHammer()) {
                            ((ImageView)node).setImage(playerHammerImage);
                        }  else {
                            ((ImageView)node).setImage(playerImage);
                        }
                    }
                }
            });
            player.isHammerEquipped().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        if (player.hasPotion() && player.hasSword()) {
                            ((ImageView)node).setImage(playerSwordHammerPotionImage);
                        } else if (player.hasPotion()) {
                            ((ImageView)node).setImage(playerHammerPotionImage);
                        } else if (player.hasSword()) {
                            ((ImageView)node).setImage(playerSwordHammerImage);
                        } else {
                            ((ImageView)node).setImage(playerHammerImage);
                        }
                    } else {
                        if (player.hasSword() && player.hasPotion()) {
                            ((ImageView)node).setImage(playerSwordPotionImage);
                        } else if (player.hasSword()) {
                            ((ImageView)node).setImage(playerSwordImage);
                        } else if (player.hasPotion()) {
                            ((ImageView)node).setImage(playerPotionImage);
                        }  else {
                            ((ImageView)node).setImage(playerImage);
                        }
                    }
                }
            });
        }
    }
    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
        
        return new DungeonController(load(), Stream.of(baseLayer, collectableLayer, moveableLayer)
            .flatMap(x -> x.stream())
            .collect(Collectors.toList())
        );
    }


}

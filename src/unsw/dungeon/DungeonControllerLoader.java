package unsw.dungeon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Sean Smith
 * @author Austin Landry
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    // ImageView Entities
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

    private VBox inventory;
    private ArrayList<EntityData> inventoryEntities;
    private Player player;

    public DungeonControllerLoader(String filename, VBox inventory) throws FileNotFoundException {
        super(filename);

        this.inventory = inventory;
        baseLayer = new ArrayList<>();
        collectableLayer = new ArrayList<>();
        moveableLayer = new ArrayList<>();
        inventoryEntities = new ArrayList<EntityData>();

        // Process Image files
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
        playerHammerPotionImage = new Image((new File("images/player_w_potion_hammer.png")).toURI().toString());
        playerSwordHammerPotionImage = new Image((new File("images/player_w_potion_sword_hammer.png")).toURI().toString());
        playerSwordHammerImage = new Image((new File("images/player_w_sword_hammer.png")).toURI().toString());
        hammerImage = new Image((new File("images/hammer.png")).toURI().toString());
        ghostImage = new Image((new File("images/ghost_new.png")).toURI().toString());
    }

    /**
     * Theme of the Dungeon is altered according to provided Theme string
     * @param theme String of the theme
     */
    public void changeTheme(String theme) {
        changeImages(baseLayer, theme);
        changeImages(collectableLayer, theme);
        changeImages(moveableLayer, theme); 
    }

    /**
     * Method that reads from a theme folder and sets new images for Entities
     * @param images
     * @param theme
     */
    public void changeImages(List<ImageView> images, String theme) {
        Image newImage = null;
        for (Node n : images) {
            if (n.getId() != null) {
                newImage = new Image((new File("images/" + theme + "/" + n.getId() + ".png")).toURI().toString());
                ((ImageView)n).setImage(newImage);
            }
        }
    }


    /**
     * @return The inventory display
     */
    public GridPane getInventory() {
        return ((GridPane)this.inventory.getChildren().get(2));
    }

    /**
     * Entities that are shown on the inventory display
     */
    public ArrayList<EntityData> inventoryEntities() {
        return this.inventoryEntities;
    }

    /**
     * Remove an entity from the inventory display
     */
    public void removeInventoryEntity(Entity entity) {
        for (EntityData e : inventoryEntities()) {
            if (e.getEntity() != null && e.getEntity().equals(entity)) {
                inventoryEntities().remove(e);
                return;
            }
        }
    }

    /**
     * Render the inventory with the Player's current items
     */
    public void renderInventory() {
        GridPane inventory = getInventory();
        ArrayList<EntityData> entities = inventoryEntities();
        // Reset the previous inventory
        inventory.getChildren().clear();
        for (int i = 0; i < entities.size(); i++) {
            EntityData entity = entities.get(i);
            inventory.add(entity.getImage(), i, 0);
            // Set a bubble of the count for an Entity
            Label counter = new Label();
            if (entity.getEntity() == null) {
                counter.setText(String.valueOf(this.player.getNumTreasures()));
            } else if (entity.getEntity().getClass().equals(Sword.class)) {
                Sword sword = ((Sword)entity.getEntity());
                counter.setText(String.valueOf(sword.getHits()));
            } else if (entity.getEntity().getClass().equals(Hammer.class)) {
                Hammer hammer = ((Hammer)entity.getEntity());
                counter.setText(String.valueOf(hammer.getHits()));
            } else if (entity.getEntity().getClass().equals(Potion.class)) {
                counter.setText(String.valueOf(this.player.potionStepsLeft().get() - 1));
            } else if (entity.getEntity().getClass().equals(Treasure.class)) {
                counter.setText(String.valueOf(1));
            } else if (entity.getEntity().getClass().equals(Key.class)) {
                continue;
            }
            counter.setStyle(
                "-fx-background-radius: 5em; " +
                "-fx-background-size: 50;" +
                "-fx-min-width: 16;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: black;"
            );
            counter.setAlignment(Pos.CENTER);
            counter.setTranslateX(1);
            counter.setTranslateY(-6);
            inventory.add(counter, i, 0);
        }
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        this.player = ((Player)player);
        addEntity(player, view, 3);
    }
    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        ((Node)view).setId("wall");
        addEntity(wall, view, 1);
    }
    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        ((Node)view).setId("boulder");
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
        ((Node)view).setId("enemy");
        addEntity(enemy, view, 3);
    }
    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        ((Node)view).setId("exit");
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
        // Colour a Portal Pair
        ColorAdjust colorAdjust = new ColorAdjust(); 
        if (portal.getID() % 2 == 0) colorAdjust.setHue((portal.getID() % 15) * 0.075);     
        else colorAdjust.setHue((portal.getID() % 10) * -0.1);     
        view.setEffect(colorAdjust);
        ((Node)view).setId("portal");
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
        ((Node)view).setId("floor");
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
        ((Node)view).setId("ghost");
        addEntity(ghost, view, 3);
    }

    /**
     * Processing the split the entities into their different layers
     * @param entity The Dungeon Entity
     * @param view The Image
     * @param level The given layer level
     */
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
        // Listeners for when the player's position changes
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
                // On every step render the inventory
                if (entity.getClass().equals(Player.class)) {
                    renderInventory();
                }
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
                // On every step render the inventory
                if (entity.getClass().equals(Player.class)) {
                    renderInventory();
                }            
            }
        });
    }

    /**
     * Track the interaction of entities with event listeners
     */
    private void trackInteractable(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        // When an entity is collected or destroyed remove its image from the map
        entity.visible().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                node.setVisible(false);
            }
        });

        if (entity.getClass().equals(Door.class)) {
            Door door = ((Door)entity);
            // If a Player has opened a Door, switch its image to an Opened Door
            door.doorStatus().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable,
                        Boolean oldValue, Boolean newValue) {
                    ((ImageView)node).setImage(doorOpenImage);
                }
            });
            // If the Player is holding a key, switch the Door image to an Alerted Door
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
            // If the Player is holding a Sword
            player.isSwordEquipped().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        // Set a new Image of the Player wielding currently held items
                        // Check whether a player has a potion or hammer already
                        if (player.hasPotion() && player.hasHammer()) {
                            ((ImageView)node).setImage(playerSwordHammerPotionImage);
                        } else if (player.hasPotion()) {
                            ((ImageView)node).setImage(playerSwordPotionImage);
                        } else if (player.hasHammer()) {
                            ((ImageView)node).setImage(playerSwordHammerImage);
                        } else {
                            ((ImageView)node).setImage(playerSwordImage);
                        }
                        // Add the Sword to inventory display
                        inventoryEntities().add(new EntityData(player.getSword(), new ImageView(swordImage)));

                    } else {
                        // Reset the Player's image wielding a Sword
                        // Check whether a player has a potion or hammer already
                        if (player.hasPotion() && player.hasHammer()) {
                            ((ImageView)node).setImage(playerHammerPotionImage);
                        } else if (player.hasPotion()) {
                            ((ImageView)node).setImage(playerPotionImage);
                        } else if (player.hasHammer()) {
                            ((ImageView)node).setImage(playerHammerImage);
                        }  else {
                            ((ImageView)node).setImage(playerImage);
                        }
                        // Remove the Sword from the inventory display
                        removeInventoryEntity(player.getSword());
                    }
                }
            });
            // If the Player is holding a Hammer
            player.isHammerEquipped().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                    // Set a new Image of the Player wielding currently held items
                    // Check whether a player has a potion or sword already
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
                        inventoryEntities().add(new EntityData(player.getHammer(), new ImageView(hammerImage)));
                    } 
                    // Reset the Player's image wielding a Hammer
                    // Check whether a player has a potion or sword already
                    else {
                        if (player.hasSword() && player.hasPotion()) {
                            ((ImageView)node).setImage(playerSwordPotionImage);
                        } else if (player.hasSword()) {
                            ((ImageView)node).setImage(playerSwordImage);
                        } else if (player.hasPotion()) {
                            ((ImageView)node).setImage(playerPotionImage);
                        }  else {
                            ((ImageView)node).setImage(playerImage);
                        }
                        // Remove the Hammer from the inventory display
                        removeInventoryEntity(player.getHammer()); 
                    }
                }
            });

            // If the Player has a Potion active
            player.potionStepsLeft().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                    // If a Player drinks a potion show the potion image effect on the player
                    if (newValue.intValue() == 11) {
                        inventoryEntities().add(new EntityData(player.getPotion(), new ImageView(potionImage)));
                    }
                    // Process potion effect and any currently equipped items
                    if (newValue.intValue() > 1) {
                        if (player.hasSword() && player.hasHammer()) {
                            ((ImageView)node).setImage(playerSwordHammerPotionImage);
                        } else if (player.hasSword()) {
                            ((ImageView)node).setImage(playerSwordPotionImage);
                        } else if (player.hasHammer()) {
                            ((ImageView)node).setImage(playerHammerPotionImage);
                        } else {
                            ((ImageView)node).setImage(playerPotionImage);
                        }
                    } 
                    // If the Potion has run out, remove effect from player image
                    else {
                        if (player.hasSword() && player.hasHammer()) {
                            ((ImageView)node).setImage(playerSwordHammerImage);
                        } else if (player.hasSword()) {
                            ((ImageView)node).setImage(playerSwordImage);
                        } else if (player.hasHammer()) {
                            ((ImageView)node).setImage(playerHammerImage);
                        }  else {
                            ((ImageView)node).setImage(playerImage);
                        }
                        removeInventoryEntity(player.getPotion());
                    }
                }
            });

            // Add a treasure and it's count to the inventory display when picked up
            player.numTreasures().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                        if (newValue.intValue() == 1) {
                            inventoryEntities().add(new EntityData(null, new ImageView(treasureImage)));
                        }
                }
            });

            // Add a key to the inventory display when picked up
            player.isHoldingKey().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            inventoryEntities().add(new EntityData(player.getKey(), new ImageView(keyImage)));
                        } else {
                            removeInventoryEntity(player.getKey());
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
    public DungeonController loadController(VBox goals, Label console) throws FileNotFoundException {
        // Feed the Entities as sorted by their given layers
        return new DungeonController(load(goals), console, Stream.of(baseLayer, collectableLayer, moveableLayer)
            .flatMap(x -> x.stream())
            .collect(Collectors.toList())
        );
    }


}

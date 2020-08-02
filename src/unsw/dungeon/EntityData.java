package unsw.dungeon;

import javafx.scene.image.ImageView;

public class EntityData {
    private Entity entity;
    private ImageView image;

    public EntityData(Entity entity, ImageView image) {
        this.entity = entity;
        this.image = image;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public ImageView getImage() {
        return this.image;
    }
}
package test;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;

import javafx.scene.image.ImageView;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonController;
import unsw.dungeon.DungeonMockLoader;
import unsw.dungeon.entity.model.Player;

public class GoalLeafTreasureTest {
    private Dungeon dungeon;
    private Player player;
    private DungeonController controller;

    @BeforeAll
    public void initialise() {
        DungeonMockLoader dungeonLoader = new DungeonMockLoader(JSONDungeons.boulders());
        this.dungeon = dungeonLoader.load();
        this.controller = new DungeonController(dungeon, new ArrayList<ImageView>());
        this.player = dungeon.getPlayer();
    }
}
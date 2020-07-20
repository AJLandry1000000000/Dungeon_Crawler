package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javafx.scene.image.ImageView;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonController;
import unsw.dungeon.DungeonMockLoader;

public class MattTest {
    @Test
    public void blahTest(){
        assertEquals("a", "a");
    }
    
    @Test
    public void blahTest2(){
        Dungeon d = new Dungeon(1, 2);
        assertEquals(d.getWidth(), 1);
    }

    @Test
    public void sanity() {
        DungeonMockLoader dungeonLoader = new DungeonMockLoader(JSONDungeons.advanced());

        Dungeon dungeon = dungeonLoader.load();

        DungeonController dc = new DungeonController(dungeon, new ArrayList<ImageView>());

        System.out.println(dungeon.getEnemies());
    }
}


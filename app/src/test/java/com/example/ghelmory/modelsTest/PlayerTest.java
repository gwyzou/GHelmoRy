package com.example.ghelmory.modelsTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.example.ghelmory.model.Player;

public class PlayerTest {
    private Player player;

    @Before
    public void setup() {
        player = new Player("Test Player", 10);
    }

    @Test
    public void testGetName() {
        assertEquals("Test Player", player.getName());
    }

    @Test
    public void testGetScore() {
        assertEquals(10, player.getScore());
    }

    @Test
    public void testSetName() {
        player.setName("New Player");
        assertEquals("New Player", player.getName());
    }

    @Test
    public void testSetScore() {
        player.setScore(20);
        assertEquals(20, player.getScore());
    }
}

package com.example.ghelmory.modelsTest;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

import com.example.ghelmory.model.Game;

public class GameTest {
    private Game game;

    private final UUID id = UUID.randomUUID();

    @Before
    public void setup() {
        game = new Game();
        game.setId(id);
        game.setName("Test Game");
        game.setDescription("This is a test game.");
        game.setNumberOfPlayersMax(4);
        game.setNumberOfPlayersMin(2);
        game.setEstimatedTime(60);
        game.setPhoto("https://example.com/game.jpg");
    }

    @Test
    public void testGetId() {
        assertEquals(game.getId(), id);
    }

    @Test
    public void testGetName() {
        assertEquals("Test Game", game.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("This is a test game.", game.getDescription());
    }

    @Test
    public void testGetNumberOfPlayersMax() {
        assertEquals(4, game.getNumberOfPlayersMax());
    }

    @Test
    public void testGetNumberOfPlayersMin() {
        assertEquals(2, game.getNumberOfPlayersMin());
    }

    @Test
    public void testGetEstimatedTime() {
        assertEquals(60, game.getEstimatedTime());
    }

    @Test
    public void testGetPhoto() {
        assertEquals("https://example.com/game.jpg", game.getPhoto());
    }

    @Test
    public void testSetName() {
        game.setName("New Game");
        assertEquals("New Game", game.getName());
    }

    @Test
    public void testSetDescription() {
        game.setDescription("New description");
        assertEquals("New description", game.getDescription());
    }

    @Test
    public void testSetNumberOfPlayersMax() {
        game.setNumberOfPlayersMax(6);
        assertEquals(6, game.getNumberOfPlayersMax());
    }

    @Test
    public void testSetNumberOfPlayersMin() {
        game.setNumberOfPlayersMin(3);
        assertEquals(3, game.getNumberOfPlayersMin());
    }

    @Test
    public void testSetEstimatedTime() {
        game.setEstimatedTime(90);
        assertEquals(90, game.getEstimatedTime());
    }

    @Test
    public void testSetPhoto() {
        game.setPhoto("https://example.com/new-game.jpg");
        assertEquals("https://example.com/new-game.jpg", game.getPhoto());
    }
}

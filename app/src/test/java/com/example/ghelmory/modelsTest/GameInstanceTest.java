package com.example.ghelmory.modelsTest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

import com.example.ghelmory.model.GameInstance;
import com.example.ghelmory.model.Player;

public class GameInstanceTest {

    private GameInstance gameInstance;

    private final UUID id = UUID.randomUUID();
    private final UUID gameId = UUID.randomUUID();

    @Before
    public void setup() {
        gameInstance = new GameInstance();
        gameInstance.setId(id);
        gameInstance.setGameId(gameId);
        gameInstance.setTitle("Test Game Instance");

        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1", 10));
        players.add(new Player("Player 2", 20));
        gameInstance.setPlayers(players);

        gameInstance.setDescription("This is a test game instance.");
        gameInstance.setPlaytime(60);
        gameInstance.setLocation("Test Location");
        gameInstance.setPhoto("https://example.com/game-instance.jpg");
    }

    @Test
    public void testGetId() {
        assertEquals(gameInstance.getId(), id);
    }

    @Test
    public void testGetGameId() {
        assertEquals(gameInstance.getGameId(), gameId);
    }

    @Test
    public void testGetTitle() {
        assertEquals("Test Game Instance", gameInstance.getTitle());
    }

    @Test
    public void testGetPlayers() {
        List<Player> players = gameInstance.getPlayers();
        assertEquals(2, players.size());
        assertEquals("Player 1", players.get(0).getName());
        assertEquals(10, players.get(0).getScore());
        assertEquals("Player 2", players.get(1).getName());
        assertEquals(20, players.get(1).getScore());
    }

    @Test
    public void testGetDescription() {
        assertEquals("This is a test game instance.", gameInstance.getDescription());
    }

    @Test
    public void testGetPlaytime() {
        assertEquals(60, gameInstance.getPlaytime());
    }

    @Test
    public void testGetLocation() {
        assertEquals("Test Location", gameInstance.getLocation());
    }

    @Test
    public void testGetPhoto() {
        assertEquals("https://example.com/game-instance.jpg", gameInstance.getPhoto());
    }

    @Test
    public void testSetId() {
        UUID newId = UUID.randomUUID();
        gameInstance.setId(newId);
        assertEquals(newId, gameInstance.getId());
    }

    @Test
    public void testSetGameId() {
        UUID newGameId = UUID.randomUUID();
        gameInstance.setGameId(newGameId);
        assertEquals(newGameId, gameInstance.getGameId());
    }
    @Test
    public void testSetTitle() {
        gameInstance.setTitle("New Title");
        assertEquals("New Title", gameInstance.getTitle());
    }

    @Test
    public void testSetPlayers() {
        List<Player> newPlayers = new ArrayList<>();
        newPlayers.add(new Player("Player 3", 30));
        newPlayers.add(new Player("Player 4", 40));
        gameInstance.setPlayers(newPlayers);

        List<Player> players = gameInstance.getPlayers();
        assertEquals(2, players.size());
        assertEquals("Player 3", players.get(0).getName());
        assertEquals(30, players.get(0).getScore());
        assertEquals("Player 4", players.get(1).getName());
        assertEquals(40, players.get(1).getScore());
    }

    @Test
    public void testSetDescription() {
        gameInstance.setDescription("New Description");
        assertEquals("New Description", gameInstance.getDescription());
    }

    @Test
    public void testSetPlaytime() {
        gameInstance.setPlaytime(90);
        assertEquals(90, gameInstance.getPlaytime());
    }

    @Test
    public void testSetLocation() {
        gameInstance.setLocation("New Location");
        assertEquals("New Location", gameInstance.getLocation());
    }

    @Test
    public void testSetPhoto() {
        gameInstance.setPhoto("https://example.com/new-game-instance.jpg");
        assertEquals("https://example.com/new-game-instance.jpg", gameInstance.getPhoto());
    }




}

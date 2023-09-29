package com.example.ghelmory.ViewItemTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.example.ghelmory.model.GameInstance;
import com.example.ghelmory.model.Player;
import com.example.ghelmory.viewItem.GameInstanceViewItem;
import com.example.ghelmory.viewItem.PlayerViewItem;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameInstanceViewItemTest {

    @Mock
    private GameInstance mockGameInstance;

    @Mock
    private Player mockPlayer;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(mockGameInstance.getId()).thenReturn(UUID.randomUUID());
        when(mockGameInstance.getTitle()).thenReturn("Test GameInstance");
        when(mockGameInstance.getDescription()).thenReturn("This is a test game instance.");
        when(mockGameInstance.getPhoto()).thenReturn("https://example.com/image.jpg");
        when(mockGameInstance.getGameId()).thenReturn(UUID.randomUUID());
        when(mockGameInstance.getPlaytime()).thenReturn(60);
        when(mockGameInstance.getLocation()).thenReturn("Test Location");

        List<Player> players = new ArrayList<>();
        players.add(mockPlayer);
        when(mockGameInstance.getPlayers()).thenReturn(players);
    }

    @Test
    public void testGetGameInstanceGameId() {
        GameInstanceViewItem viewItem = new GameInstanceViewItem(mockGameInstance);
        assertEquals(mockGameInstance.getGameId(), viewItem.getGameInstanceGameId());
    }

    @Test
    public void testGetGameInstanceTitle() {
        GameInstanceViewItem viewItem = new GameInstanceViewItem(mockGameInstance);
        assertEquals("Test GameInstance", viewItem.getGameInstanceTitle());
    }

    @Test
    public void testGetGameInstanceDescription() {
        GameInstanceViewItem viewItem = new GameInstanceViewItem(mockGameInstance);
        assertEquals("This is a test game instance.", viewItem.getGameInstanceDescription());
    }
    @Test
    public void testGetGameInstanceImage() {
        GameInstanceViewItem viewItem = new GameInstanceViewItem(mockGameInstance);
        assertEquals("https://example.com/image.jpg", viewItem.getGameInstanceImage());
    }
    @Test
    public void testGetGameInstanceId() {
        GameInstanceViewItem viewItem = new GameInstanceViewItem(mockGameInstance);
        assertEquals(mockGameInstance.getId(), viewItem.getGameInstanceId());
    }

    @Test
    public void testGetGameInstancePlaytime() {
        GameInstanceViewItem viewItem = new GameInstanceViewItem(mockGameInstance);
        assertEquals(60, viewItem.getGameInstancePlaytime());
    }

    @Test
    public void testGetGameInstanceLocation() {
        GameInstanceViewItem viewItem = new GameInstanceViewItem(mockGameInstance);
        assertEquals("Test Location", viewItem.getGameInstanceLocation());
    }

    @Test
    public void testGetGameInstancePlayers() {
        GameInstanceViewItem viewItem = new GameInstanceViewItem(mockGameInstance);
        List<PlayerViewItem> playerViewModelsList = viewItem.getGameInstancePlayers();
        assertEquals(1, playerViewModelsList.size());
    }
}

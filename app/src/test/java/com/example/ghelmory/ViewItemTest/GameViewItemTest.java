package com.example.ghelmory.ViewItemTest;

import com.example.ghelmory.model.Game;
import com.example.ghelmory.viewItem.GameViewItem;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
public class GameViewItemTest {
    @Mock
    private Game mockGame;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(mockGame.getId()).thenReturn(UUID.randomUUID());
        when(mockGame.getName()).thenReturn("Test Game");
        when(mockGame.getDescription()).thenReturn("This is a test game.");
        when(mockGame.getPhoto()).thenReturn("https://example.com/game.jpg");
        when(mockGame.getNumberOfPlayersMin()).thenReturn(2);
        when(mockGame.getNumberOfPlayersMax()).thenReturn(4);
        when(mockGame.getEstimatedTime()).thenReturn(60);
    }

    @Test
    public void testGetGameId() {
        GameViewItem viewItem = new GameViewItem(mockGame);
        assertEquals(mockGame.getId(), viewItem.getGameId());
    }
    @Test
    public void testGetGameName() {
        GameViewItem viewItem = new GameViewItem(mockGame);
        assertEquals("Test Game", viewItem.getGameName());
    }

    @Test
    public void testGetGameDescription() {
        GameViewItem viewItem = new GameViewItem(mockGame);
        assertEquals("This is a test game.", viewItem.getGameDescription());
    }

    @Test
    public void testGetGameImage() {
        GameViewItem viewItem = new GameViewItem(mockGame);
        assertEquals("https://example.com/game.jpg", viewItem.getGameImage());
    }

    @Test
    public void testGetGameMinPlayers() {
        GameViewItem viewItem = new GameViewItem(mockGame);
        assertEquals(2, viewItem.getGameMinPlayers());
    }

    @Test
    public void testGetGameMaxPlayers() {
        GameViewItem viewItem = new GameViewItem(mockGame);
        assertEquals(4, viewItem.getGameMaxPlayers());
    }

    @Test
    public void testGetGameEstimatedTime() {
        GameViewItem viewItem = new GameViewItem(mockGame);
        assertEquals(60, viewItem.getGameEstimatedTime());
    }


    @Test
    public void testToString() {
        GameViewItem viewItem = new GameViewItem(mockGame);
        assertEquals("Test Game", viewItem.toString());
    }
}

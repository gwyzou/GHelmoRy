package com.example.ghelmory.ViewItemTest;


import com.example.ghelmory.model.Player;
import com.example.ghelmory.viewItem.PlayerViewItem;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class PlayerViewItemTest {

    @Mock
    private Player mockPlayer;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(mockPlayer.getName()).thenReturn("Test Player");
        when(mockPlayer.getScore()).thenReturn(10);
    }

    @Test
    public void testGetPlayerName() {
        PlayerViewItem viewItem = new PlayerViewItem(mockPlayer);
        assertEquals("Test Player", viewItem.getPlayerName());
    }

    @Test
    public void testGetPlayerScore() {
        PlayerViewItem viewItem = new PlayerViewItem(mockPlayer);
        assertEquals(10, viewItem.getPlayerScore());
    }

    @Test
    public void testToStringWithPlayerName() {
        PlayerViewItem viewItem = new PlayerViewItem(mockPlayer);
        assertEquals("Test Player 10", viewItem.toString());
    }

    @Test
    public void testToStringWithoutPlayerName() {
        when(mockPlayer.getName()).thenReturn(null);
        PlayerViewItem viewItem = new PlayerViewItem(mockPlayer);
        assertEquals("Player 10", viewItem.toString());
    }
}


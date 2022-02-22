package com.revature.chinook;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    public void givenArtistName_ThenReturnArtist() {
        List<Artist> lines = App.loadArtists();

        String givenArtist = "Kiss";
        String result = App.searchByName(givenArtist, lines);
        assertEquals(givenArtist, result);
    }

}
package com.revature.chinook;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    static List<Artist> artists;

    @BeforeAll
    public static void setup() throws IOException, CsvException {
        artists = App.parseArtists(App.loadCSV("artist.csv"));
    }

    @Test
    public void givenArtistName_ThenReturnArtist() {
        String givenArtist = "Kiss";
        String result = App.searchByName(givenArtist, artists);
        assertEquals(givenArtist, result);
    }

    @Test
    public void givenNonCSVFile_ThenThrowsIllegalArgumentException() {
        String notCSV = "artist.txt";
        assertThrows(IllegalArgumentException.class, () -> App.loadCSV(notCSV));
    }

}
package com.revature.chinook;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class App {
    public static void main(String[] args) {
        // Load data
        List<Artist> artists = loadArtists();

        // Search queary
        String searchQuery = "Kiss";
        System.out.println(searchByName(searchQuery, artists));

        // Save results
    }

    public static List<Artist> loadArtists() {
        // Reading CSV data
        URI uri = null;
        try {
            uri = Objects.requireNonNull(App.class.getClassLoader().getResource("artist.csv")).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Path filepath = Paths.get(Objects.requireNonNull(uri));
        List<Artist> artists = new ArrayList<>();
        try {
            BufferedReader br = Files.newBufferedReader(filepath);

            //read using OpenCSV library
            CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
            CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser).withSkipLines(1).build();
            List<String[]> lines = reader.readAll();
            reader.close();

            // Parse lines into Artist array
            for (String[] columns : lines) {
                artists.add(new Artist(Integer.parseInt(columns[0]), columns[1]));
            }
        } catch (IOException e) {
            System.err.println("Couldn't load file!");
        } catch (CsvException e) {
            System.err.println("OpenCSV Failed to parse!");
        }
        return artists;
    }

    public static String searchByName(String searchQuery, List<Artist> artists) {
        for (Artist artist : artists) {
            if (artist.getName().equalsIgnoreCase(searchQuery))
                return artist.getName();
        }
        return null;
    }
}

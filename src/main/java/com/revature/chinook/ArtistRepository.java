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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArtistRepository {
    private List<Artist> artists;
    private Connection connection;

    public ArtistRepository(String csvFile) {
        artists = new ArrayList<>();
        parseArtists(loadCSV(csvFile));
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void save(Artist artist) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO ARTISTS VALUES(" + artist.getArtistId() + ", '" + artist.getName() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private BufferedReader loadCSV(String csvFile) {
        if (!csvFile.endsWith(".csv"))
            throw new IllegalArgumentException("Must be a CSV File!");
        // Reading CSV data
        try {
            URI uri = Objects.requireNonNull(App.class.getClassLoader().getResource(csvFile)).toURI();
            Path filepath = Paths.get(Objects.requireNonNull(uri));
            return Files.newBufferedReader(filepath);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Couldn't load file!");
        }
        return null;
    }

    private void parseArtists(BufferedReader br) {
        //read using OpenCSV library
        CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
        CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser).withSkipLines(1).build();
        List<String[]> lines = null;
        try {
            lines = reader.readAll();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

        // Parse lines into Artist array
        for (String[] columns : lines) {
            artists.add(new Artist(Integer.parseInt(columns[0]), columns[1]));
        }
    }
}

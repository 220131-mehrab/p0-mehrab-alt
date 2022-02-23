package com.revature.chinook;

import org.apache.catalina.startup.Tomcat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AppContext {
    private static final String artistCSVFile = "artist.csv";
    private static ArtistRepository artistRepository;
    private static ArtistService artistService;
    private static Tomcat server;
    private static ArtistController artistController;
    private static Connection connection;

    public static void build() {
        artistRepository = new ArtistRepository(artistCSVFile);
        artistService = new ArtistService(artistRepository);
        artistController = new ArtistController();
        server = new Tomcat();
        server.getConnector();
        server.addContext("", null);
        server.addServlet("", "artistServlet", artistController).addMapping("/artists");
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:", "chinook", "chinook");
            connection.createStatement().execute("CREATE TABLE ARTISTS(id int primary key, name varchar)");
            artistRepository.setConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArtistRepository getArtistRepository() {
        return artistRepository;
    }

    public static ArtistService getArtistService() {
        return artistService;
    }

    public static Tomcat getTomcat() {
        return server;
    }
}

package com.revature.chinook;

public class ArtistService {
    private ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public String searchByName(String searchQuery) {
        for (Artist artist : artistRepository.getArtists()) {
            if (artist.getName().equalsIgnoreCase(searchQuery))
                return artist.getName();
        }
        return null;
    }

    public void saveArtist(Artist artist) {
        artistRepository.save(artist);
    }
}

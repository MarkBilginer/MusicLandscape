package MusicLandscape.entities;

/**
 * This class represents an artist of performing arts, like a band.
 */
public class Artist implements Comparable<Artist> {

    /**
     * holds the name of the artist initial value should be unchanged
     */
    private String name;

    /**
     * creates a default artist a default artists name is the String "unknown" (without quotes)
     */
    public Artist() {
        this.name = "unknown";
    }

    /**
     * creates a copy of an artist
     *
     * @param a the original artist to be copied
     */
    public Artist(Artist a) {
        this.name = a.getName();
    }

    /**
     * creates an artist with a certain name
     *
     * @param name the name of this artist
     */
    public Artist(String name) {
        this.name = name;
    }

    /**
     * gets the name of this artist
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * sets the name of this artist. the name of an artist cannot be null or empty.
     * if an invalid argument is passed to the method the state of the object remains unchanged
     *
     * @param name the new name of the artist
     */
    public void setName(String name) {
        if (name == null) {
            return;
        } else if (name.isBlank()) {
            return;
        }
        this.name = name;
    }

    /**
     * Naturally, artists are lexicographically compared by name.
     * It is assumed that this artist is only compared to non-null artists.
     *
     * @param o artist to be compared to
     * @return An int value: 0 if the string is equal to the other string.
     * < 0 if the string is lexicographically less than the other string
     * > 0 if the string is lexicographically greater than the other string (more characters)
     */
    @Override
    public int compareTo(Artist o) {
        return this.name.compareTo(o.getName());
    }

    /**
     * returns a String representation of this Artist
     * This should be either the name of the Artist, or "unknown" if the name is not available
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return (this.name == null ? "unknown" : this.name);
    }
}

package MusicLandscape.entities;

/**
 * This class represents a release of a specific artist, like an album or a music video.
 */
public abstract class Release {

    /**
     * The artist that released this release.
     * A null artist represents an unknown artist.
     */
    private Artist artist;

    /**
     * The title of this release.
     * A null title represents an unknown title.
     */
    private String title;

    /**
     * The year in which this release was released.
     * The year cannot be negative, value 0 (zero) representing unknown year.
     */
    private int year;

    /**
     * Creates a default release.
     * A default release has default values for all fields (see there).
     */
    public Release() {
        this.artist = new Artist();
        this.title = null;
        this.year = 1900;
    }

    /**
     * Creates a copy of a release.
     * This release is a deep copy of an existing release - the original.
     *
     * @param orig the release to be copied
     */
    public Release(Release orig) {
        this.artist = new Artist(orig.artist);
        this.title = orig.getTitle();
        this.year = orig.getYear();
    }

    /**
     * Create a release with a specific title of a specific artist in a specific year.
     *
     * @param title  the title of the new release
     * @param artist the artist of the new release
     * @param year   the year of the new release
     */
    public Release(String title, Artist artist, int year) {
        this.setArtist(artist);
        this.setTitle(title);
        this.setYear(year);
    }

    /**
     * Get the title of this release.
     * If the title is unknown a null String is returned.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set the title of this release.
     * This method accepts null Strings.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the artist of this release.
     *
     * @return the artist of this release.
     */
    public Artist getArtist() {
        return this.artist;
    }

    /**
     * Set the artist of this release.
     * This method accepts null arguments.
     *
     * @param artist the artist to set.
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * Get the year in which this release was released.
     *
     * @return the year of this release.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Set the year of this release.
     * If the argument is outside the allowed range it is ignored.
     *
     * @param year the year to set.
     */
    public void setYear(int year) {
        if (year >= 0) {
            this.year = year;
        } else {
            //if year is negative
            this.year = 1900;
        }
    }

    /**
     * Get the total time of this release.
     * The implementation of this method is left to concrete subclasses.
     *
     * @return the duration of this release in seconds.
     */
    public abstract int totalTime();

    /**
     * Get a textual representation of this release.
     * The string representation of a release is "title-artist-year-totaltime" (without quotes and all names substituted
     * with their respective values) unknown field are represented by the string "unknown" and unknown title by "unkown title"(without quotes) *
     *
     * @return a string representation of this release
     */
    @Override
    public String toString() {

        String unk = "unknown";

        return ((this.title == null ? unk : this.title) + "-"
                + (this.artist == null ? unk : this.artist) + "-"
                + (this.year == 0 ? unk : this.year) + "-"
                + this.totalTime());
    }

}

package MusicLandscape.util.formatters;

import MusicLandscape.entities.Track;
import MusicLandscape.util.MyFormatter;

/**
 * This class represents the concept of csv-formatting of a track.
 */
public class CSVTrackFormatter implements MyFormatter<Track> {

    public CSVTrackFormatter() {
    }

    /**
     * Get the headers for the table as a single string.<br>
     * <p>
     * Contains the names for all columns separated by the correct number of
     * blanks.
     * <p>
     * comma separated list of all column names in the following order:
     * Title
     * Writer
     * Performer
     * duration
     * year
     * No new line is added at the end of the String!.
     *
     * @return the header string.
     */
    @Override
    public String header() {
        return "Title, Writer, Performer, duration, year";
    }

    /**
     * Creates a String representation for an object.
     * <p>
     * Creates a CSV format of a track.
     * The csv representation of a track is "title","writer", "performer","duration", "year"; (without quotes)
     * No new line is added at the end of the String!.
     *
     * @param track the object to be formatted
     * @return the formatted representing the object
     */
    @Override
    public String format(Track track) {
        return String.format("%s, %s, %s, %d, %d", track.getTitle(), track.getWriter(),
                track.getPerformer(), track.getDuration(), track.getYear());
    }

    /**
     * A line of text to be used between header and data.
     * <p>
     * the top separator for this format is the empty string.
     * No new line is added at the end of the String!.
     *
     * @return the separator.
     */
    @Override
    public String topSeparator() {
        //TODO check later if needs a fix or not
        return "";
    }

    /**
     * the string representation is "CSV format [Title, Writer, Performer, duration, year]" (without quotes)
     * @return string representation
     */
    @Override
    public String toString() {
        //TODO might need some change
        return String.format("CSV format [%s]", this.header());
    }
}

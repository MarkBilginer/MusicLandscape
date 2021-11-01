package MusicLandscape.util.formatters;

import MusicLandscape.entities.Track;
import MusicLandscape.util.MyFormatter;

/**
 * This class represents the concept of long formatting of a track containing in addition the Writer compared to short format.
 */
public class LongTrackFormatter implements MyFormatter<Track> {

    public LongTrackFormatter() {
    }

    /**
     * Get the headers for the table as a single string.<br>
     * <p>
     * Contains the names for all columns separated by the correct number of
     * blanks.
     *
     * @return the header string.
     */
    @Override
    public String header() {
        return "Title Writer (min:sec)";
    }

    /**
     * Creates a String representation for an object.
     * <p>
     * Creates a long format of a track.
     * The long representation of a track is "title writer (duration)"; (without quotes)
     * Title is exactly ten characters wide with leading blanks (if any).
     * Duration is in the format minutes:seconds, both at least two digits wide with leading zeros.
     *
     * @param track the object to be formatted
     * @return the formatted representing the object
     */
    @Override
    public String format(Track track) {

        int min = track.getDuration() / 60;
        int sec = track.getDuration() % 60;
        String title = track.getTitle();
        return String.format("%-10s, %s, (%02d:%02d)", (title.length()>10 ? title.substring(0,10): title),
                track.getWriter(), min, sec);
    }

    /**
     * A line of text to be used between header and data.
     * <p>
     * top separator consists of dashes (-) only. It is exactly as wide as the header.
     *
     * @return the separator.
     */
    @Override
    public String topSeparator() {
        String seperator = "";
        for (int i = 0; i < this.header().length(); i++) {
            seperator += "-";
        }
        return seperator;
    }

    /**
     * the string representation of this formatter is
     * "long format [Title Writer (min:sec)]" (without quotes)
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("long format [Title Writer (min:sec)]");
    }
}
